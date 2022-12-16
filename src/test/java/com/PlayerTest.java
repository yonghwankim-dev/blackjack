package com;

import com.exception.InvalidPlayerChoseException;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PlayerTest {
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
    public void testInputName(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player();
        //when
        String actual = player.inputName();
        //then
        assertThat(actual).isEqualTo(input);
    }

    @DisplayName("플레이어의 이름을 적절하지 않은 이름으로 입력하는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"KYHW\nKYH", "김용환\nKYH", "123\nKYH", " \nKYH", "abc\nKYH", "aBc\nKYH", "!@#\nKYH"})
    public void testInputName_whenInputInvalidPlayerName_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player();
        //when
        String actual = player.inputName();
        //then
        assertThat(actual).isEqualTo("KYH");
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("플레이어의 이름(3 english letters)은? ex) KYH");
        assertThat(outputs[1]).isEqualTo("적절하지 않은 이름입니다. ex) KYH");
    }

    private InputStream generateInputStream(String text){
        return new ByteArrayInputStream(text.getBytes());
    }

    @DisplayName("플레이어에게 포인트를 물어보는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"500"})
    public void testInputInitialPoint(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH");
        //when
        int actual = player.inputInitialPoint();
        //then
        assertThat(actual).isEqualTo(500);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님의 포인트는 얼마입니까?");
    }

    @DisplayName("플레이어가 0포인트를 입력하는 경우의 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"0\n500"})
    public void testInputInitialPoint_whenInvalidInput_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH");
        //when
        int actual = player.inputInitialPoint();
        //then
        assertThat(actual).isEqualTo(500);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님의 포인트는 얼마입니까?");
        assertThat(outputs[1]).isEqualTo("포인트는 0보다 큰 숫자여야 합니다. ex) 500");
    }

    @DisplayName("플레이어가 포인트 입력시 숫자가 아닌 것을 입력하는 경우의 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"abc\n500"})
    public void testInputInitialPoint_whenInvalidInputWithNotNumber_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH");
        //when
        int actual = player.inputInitialPoint();
        //then
        assertThat(actual).isEqualTo(500);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님의 포인트는 얼마입니까?");
        assertThat(outputs[1]).isEqualTo("포인트는 0보다 큰 숫자여야 합니다. ex) 500");
    }

    @DisplayName("100 배팅 포인트를 입력하는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"100"})
    public void testInputBatting(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH", 500);
        //when
        int actual = player.inputBatting();
        //then
        assertThat(actual).isEqualTo(100);
    }

    @DisplayName("100포인트를 가진 플레이어가 200포인트를 베팅하는 경우 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"100\n200\n100"})
    public void testInputBatting_whenBattingPointIsNotEnough(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH", 500);
        //when
        player.inputBatting();
        //then
        assertThatThrownBy(()-> {throw new PointBattingNotEnoughException(player);});
    }

    @DisplayName("플레이어가 히트를 입력하는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"1"})
    public void testInputChose(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH", 500);
        //when
        Chose actual = player.inputChose();
        //then
        assertThat(actual).isEqualTo(Chose.HIT);
    }

    @DisplayName("플레이어가 입력을 잘못하는 경우")
    @ParameterizedTest
    @ValueSource(strings = {"12\n1", "aaa\n1", " \n1", "3\n1"})
    public void testInputChose_whenInvalidInput_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH", 500);
        //when
        Chose actual = player.inputChose();
        //then
        assertThat(actual).isEqualTo(Chose.HIT);
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("KYH님 선택해 주세요 ex) 1");
        assertThat(outputs[1]).isEqualTo("1. 히트(hit)");
        assertThat(outputs[2]).isEqualTo("2. 스탠드(stand)");
        assertThat(outputs[3]).isEqualTo("잘못된 선택입니다. ex) 1");
    }


    @DisplayName("플레이어가 게임을 계속 진행할것인지 입력받는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"Y"})
    public void testInputContinueGame(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH", 500);
        Dealer dealer = new Dealer();
        //when
        String actual = player.inputContinueGame(dealer);
        //then
        assertThat(actual).isEqualTo("Y");
    }

    @DisplayName("플레이어가 게임을 계속 진행할것인지 입력을 받는데 잘못 입력하는 경우 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"YN\nY", " \nY", "213\nY", "yn\nY"})
    public void testInputContinueGame_whenInvalidInput_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        Player player = new Player("KYH", 500);
        Dealer dealer = new Dealer();
        //when
        String actual = player.inputContinueGame(dealer);
        //then
        assertThat(actual).isEqualTo("Y");
        assertThatThrownBy(()-> {throw new InvalidPlayerChoseException();});
    }

    @DisplayName("플레이어의 포인트가 증가되느지 테스트")
    @Test
    public void testAddPoint(){
        //given
        Player player = new Player("KYH", 500);
        //when
        player.addPoint(50);
        //then
        assertThat(player.getPoint()).isEqualTo(550);
    }

    @DisplayName("플레이어가 히트를 하여 카드가 추가되었는지 확인 테스트")
    @Test
    public void testHit(){
        //given
        Player player = new Player("KYH", 500);
        Dealer dealer = new Dealer();
        //when
        player.hit(dealer);
        //then
        assertThat(player.getHands().size()).isEqualTo(1);
    }
}
