package com;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

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

    private InputStream generateInputStream(String text){
        return new ByteArrayInputStream(text.getBytes());
    }

}