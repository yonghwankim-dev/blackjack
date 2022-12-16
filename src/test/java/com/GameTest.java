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

    private InputStream generateInputStream(String text){
        return new ByteArrayInputStream(text.getBytes());
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
        game.showUser(player);
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[2]).isEqualTo("입력정보를 확인해주세요");
        assertThat(outputs[3]).isEqualTo("-------------------------------");
        assertThat(outputs[4]).isEqualTo("NAME : KYH, POINT : 500");
        assertThat(outputs[5]).isEqualTo("-------------------------------");
    }

    @Test
    public void testCompareScore(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        player.addCard(new Card("3", Shape.HEART, 3));
        player.addCard(new Card("4", Shape.HEART, 4));
        dealer.addCard(new Card("5", Shape.HEART, 5));
        dealer.addCard(new Card("6", Shape.HEART, 6));
        //when
        int result = game.compareScore();
        game.showCompareScoreResult(result);
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("DEALER 승");
    }

    @DisplayName("플레이어가 승리시 베팅한 금액의 2배를 돌려주는 테스트")
    @Test
    public void testGivePointToWinner(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        int battingPoint = 50;
        //when
        game.givePointToWinner(battingPoint);
        //then
        assertThat(player.getPoint()).isEqualTo(600);
    }

    @DisplayName("플레이어 또는 딜러의 점수합이 버스트(점수가 21초과)인지 테스트")
    @Test
    public void testIsBurst(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        player.addCard(new Card("10", Shape.HEART, 10));
        player.addCard(new Card("J", Shape.HEART, 10));
        player.addCard(new Card("2", Shape.HEART, 2));
        dealer.addCard(new Card("5", Shape.HEART, 5));
        dealer.addCard(new Card("6", Shape.HEART, 6));
        //when
        boolean actual1 = game.isBurst(player);
        boolean actual2 = game.isBurst(dealer);
        //then
        assertThat(actual1).isTrue();
        assertThat(actual2).isFalse();
    }
}