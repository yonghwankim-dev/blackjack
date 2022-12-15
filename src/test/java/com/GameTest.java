package com;

import com.exception.PointBattingNotEnoughException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class GameTest {
    private final ByteArrayOutputStream output = new ByteArrayOutputStream();

    @BeforeEach
    public void setupStreams(){
        System.setOut(new PrintStream(output));
    }

    @AfterEach
    public void restoreStreams(){
        System.setOut(System.out);
        output.reset();
    }

    @DisplayName("플레이어의 이름을 물어보는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"KYH"})
    public void testInputPlayerName(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Game game = new Game(null, null);
        //when
        String actual = game.inputPlayerName();
        //then
        assertThat(actual).isEqualTo(input);
    }

    @DisplayName("플레이어의 이름을 적절하지 않은 이름으로 입력하는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"KYHW\nKYH", "김용환\nKYH", "123\nKYH", " \nKYH", "abc\nKYH", "aBc\nKYH", "!@#\nKYH"})
    public void testInputPlayerName_whenInputInvalidPlayerName_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Game game = new Game(null, null);
        //when
        String actual = game.inputPlayerName();
        //then
        assertThat(actual).isEqualTo("KYH");
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("플레이어의 이름(3 english letters)은? ex) KYH");
        assertThat(outputs[1]).isEqualTo("적절하지 않은 이름입니다. ex) KYH");
    }

    @DisplayName("플레이어에게 포인트를 물어보는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"500"})
    public void testInputPlayerPoint(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Game game = new Game(new Player("KYH"), new Dealer());
        //when
        int actual = game.inputPlayerPoint();
        //then
        assertThat(actual).isEqualTo(500);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님의 포인트는 얼마입니까?");
    }

    @DisplayName("플레이어가 0포인트를 입력하는 경우의 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"0\n500"})
    public void testInputPlayerPoint_whenInvalidInput_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Game game = new Game(new Player("KYH"), new Dealer());
        //when
        int actual = game.inputPlayerPoint();
        //then
        assertThat(actual).isEqualTo(500);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님의 포인트는 얼마입니까?");
        assertThat(outputs[1]).isEqualTo("포인트는 0보다 큰 숫자여야 합니다. ex) 500");
    }

    @DisplayName("플레이어가 포인트 입력시 숫자가 아닌 것을 입력하는 경우의 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"abc\n500"})
    public void testInputPlayerPoint_whenInvalidInputWithNotNumber_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Game game = new Game(new Player("KYH"), new Dealer());
        //when
        int actual = game.inputPlayerPoint();
        //then
        assertThat(actual).isEqualTo(500);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님의 포인트는 얼마입니까?");
        assertThat(outputs[1]).isEqualTo("포인트는 0보다 큰 숫자여야 합니다. ex) 500");
    }

    @DisplayName("100 배팅 포인트를 입력하는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"500\n100"})
    public void testInputPlayerPointBatting(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH");
        Game game = new Game(player, new Dealer());
        game.addPoint(player);
        //when
        int actual = game.inputPlayerPointBatting();
        //then
        assertThat(actual).isEqualTo(100);
    }

    @DisplayName("100포인트를 가진 플레이어가 200포인트를 베팅하는 경우 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"100\n200\n100"})
    public void testInputPlayerPointBatting_whenBattingPointIsNotEnough(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH");
        Game game = new Game(player, new Dealer());
        game.addPoint(player);
        //when
        game.inputPlayerPointBatting();
        //then
        assertThatThrownBy(()-> {throw new PointBattingNotEnoughException(player);});
    }

    @DisplayName("플레이어가 히트를 입력하는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"1"})
    public void testInputPlayerChose(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Game game = new Game(new Player("KYH"), new Dealer());
        //when
        int actual = game.inputPlayerChose();
        //then
        assertThat(actual).isEqualTo(1);
    }

    @DisplayName("플레이어가 입력을 잘못하는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"12\n1", "aaa\n1", " \n1", "3\n1"})
    public void testInputPlayerChose_whenInvalidInput_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Game game = new Game(new Player("KYH"), new Dealer());
        //when
        int actual = game.inputPlayerChose();
        //then
        assertThat(actual).isEqualTo(1);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님 선택해 주세요 ex) 1");
        assertThat(outputs[1]).isEqualTo("1. 히트(hit)");
        assertThat(outputs[2]).isEqualTo("2. 스탠드(stand)");
        assertThat(outputs[3]).isEqualTo("잘못된 선택입니다. ex) 1");
    }

    private InputStream generateInputStream(String text){
        return new ByteArrayInputStream(text.getBytes());
    }

    @DisplayName("게임에서 초기덱이 생성되는지 테스트")
    @Test
    public void testCreateCardDeck(){
        //given
        Game game = new Game(new Player("KYH"), new Dealer());
        Set<Card> expected = createCardDeckExpected();
        //when
        Set<Card> actual = game.createCardDeck(4);
        //then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    private Set<Card> createCardDeckExpected(){
        Set<Card> result = new HashSet<>();
        String[] shapes = {"CLOVER", "HEART", "DIAMOND", "SPADE"};
        String[] names = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int[] values = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        for(String shape : shapes){
            for(int i = 0; i < names.length; i++){
                result.add(new Card(names[i], shape, values[i]));
            }
        }
        return result;
    }

    @DisplayName("플레이어가 입력한 정보를 보여주는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"KYH\n500"})
    public void testShowPlayerInputConfirm(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        GameInput gameInput = new GameInput();
        String name = gameInput.inputPlayerName();
        int point = gameInput.inputPlayerPoint(name);
        Player player = new Player(name, point);
        Game game = new Game(player, new Dealer());
        //when
        game.showPlayerInputConfirm();
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[2]).isEqualTo("입력정보를 확인해주세요");
        assertThat(outputs[3]).isEqualTo("-------------------------------");
        assertThat(outputs[4]).isEqualTo("NAME : KYH, POINT : 500");
        assertThat(outputs[5]).isEqualTo("-------------------------------");
    }

    @DisplayName("플레이어에게 남은 포인트를 말해주는 테스트")
    @Test
    public void testShowPlayerRemainingPoint(){
        //given
        Player player = new Player("KYH", 500);
        Game game = new Game(player, new Dealer());
        //when
        game.showPlayerRemainingPoint();
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님의 남은 포인트 : 500");
    }

}