package com;

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
        int[] values = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        for(String shape : shapes){
            for(int i = 0; i < names.length; i++){
                result.add(new Card(names[i], shape, values[i]));
            }
        }
        return result;
    }
}