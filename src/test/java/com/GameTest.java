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

import static com.CardStatus.OPEN;
import static com.CardValue.*;
import static com.Shape.*;
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
    @Test
    public void testShowPlayerInputConfirm(){
        //given
        Player player = new Player("KYH", 500);
        Game game = new Game(player, new Dealer());
        //when
        game.showUser(player);
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("입력정보를 확인해주세요");
        assertThat(outputs[1]).isEqualTo("-------------------------------");
        assertThat(outputs[2]).isEqualTo("NAME : KYH, POINT : 500");
        assertThat(outputs[3]).isEqualTo("-------------------------------");
    }

    @Test
    public void testCompareScore(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        player.addCard(new Card(THREE, HEART, OPEN));
        player.addCard(new Card(FOUR, HEART, OPEN));
        dealer.addCard(new Card(FIVE, HEART, OPEN));
        dealer.addCard(new Card(SIX, HEART, OPEN));
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
        Player player = new Player("KYH", 500);
        Dealer dealer = new Dealer();
        Game game = new Game(player, dealer);
        int battingPoint = 50;
        //when
        game.givePointByBatting(battingPoint);
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
        player.addCard(new Card(TEN, HEART, OPEN));
        player.addCard(new Card(JACK, HEART, OPEN));
        player.addCard(new Card(TWO, HEART, OPEN));
        dealer.addCard(new Card(FIVE, HEART, OPEN));
        dealer.addCard(new Card(SIX, HEART, OPEN));
        //when
        boolean actual1 = game.isBurst(player);
        boolean actual2 = game.isBurst(dealer);
        //then
        assertThat(actual1).isTrue();
        assertThat(actual2).isFalse();
    }

    @DisplayName("에이스(11)-에이스(11)를 에이스(1)-에이스(11)로 변경하여 점수 계산 테스트")
    @ParameterizedTest
    @ValueSource(strings = "2")
    public void testStartPlayerTurn(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        //when
        player.addCard(new Card(ACE, HEART, OPEN));
        player.addCard(new Card(ACE, CLOVER, OPEN));
        game.startPlayerTurn();
        //then
        assertThat(player.getScore()).isEqualTo(12);
    }

    @DisplayName("에이스 원 카드가 다시 에이스 카드로 변경하는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"2"})
    public void testStartPlayerTurn_whenIsNotBust_thenAceOneToAce(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        User player = new Player("KYH", 500);
        Game game = new Game(player, new Dealer());
        //when
        player.addCard(new Card(ACEONE, HEART, OPEN));
        player.addCard(new Card(THREE, CLOVER, OPEN));

        game.startPlayerTurn();

        //then
        assertThat(player.getScore()).isEqualTo(14);
    }

    @DisplayName("딜러와 플레이어 모두 블랙잭인 경우 무승부인지 테스트")
    @Test
    public void testPush_whenDealerAndPlayerAllBackJack_thenDraw(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        //when
        player.addCard(new Card(ACE, HEART, OPEN));
        player.addCard(new Card(JACK, CLOVER, OPEN));
        dealer.addCard(new Card(ACE, CLOVER, OPEN));
        dealer.addCard(new Card(TEN, HEART, OPEN));

        int actual = game.compareScore();

        //then
        assertThat(actual).isEqualTo(0);
    }

    @DisplayName("딜러와 플레이어 모두 버스트인 경우 딜러승인지 테스트")
    @Test
    public void testPush_whenDealerAndPlayerAllBurst_thenDealerWin(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        //when
        player.addCard(new Card(TEN, HEART, OPEN));
        player.addCard(new Card(JACK, CLOVER, OPEN));
        player.addCard(new Card(THREE, CLOVER, OPEN));
        dealer.addCard(new Card(QUEEN, CLOVER, OPEN));
        dealer.addCard(new Card(KING, HEART, OPEN));
        dealer.addCard(new Card(THREE, SPADE, OPEN));

        int actual = game.compareScore();

        //then
        assertThat(actual).isEqualTo(1);
    }
}