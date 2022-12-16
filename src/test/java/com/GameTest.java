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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

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
        Player player = new Player("KYH");
        Game game = new Game(player, new Dealer());
        //when
        int actual = game.inputPlayerPoint(player.getName());
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
        Player player = new Player("KYH");
        Game game = new Game(player, new Dealer());
        //when
        int actual = game.inputPlayerPoint(player.getName());
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
        Player player = new Player("KYH");
        Game game = new Game(player, new Dealer());
        //when
        int actual = game.inputPlayerPoint(player.getName());
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
        List<Card> expected = createCardDeckExpected();
        //when
        List<Card> actual = game.createCardDeck(4);
        //then
        assertThat(actual).containsExactlyElementsOf(expected);
    }

    private List<Card> createCardDeckExpected(){
        List<Card> result = new ArrayList<>();
        Shape[] shapes = {Shape.HEART, Shape.DIAMOND, Shape.CLOVER, Shape.SPADE};
        String[] names = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        int[] values = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};

        for(Shape shape : shapes){
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

    @DisplayName("플레이어에게 패를 나누어주었다고 테스트")
    @Test
    public void testShowHandDealingToPlayer(){
        //given
        Player player = new Player("KYH", 500);
        Game game = new Game(player, new Dealer());
        //when
        game.showHandDealingToPlayer();
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("딜러 : 패를 나누어줬습니다.");
    }

    @DisplayName("플레이어에게 어떤 옵션을 선택했다고 말해주는 테스트")
    @Test
    public void testShowPlayerChoseResult(){
        //given
        Player player = new Player("KYH", 500);
        Game game = new Game(player, new Dealer());
        int hit = 1;
        //when
        game.showPlayerChoseResult(hit);
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("딜러 : KYH님 히트를 선택하셨습니다.");
    }

    @DisplayName("딜러와 플레이어에게 카드 두장씩 나눠주는 테스트")
    @Test
    public void testDealingCardAll() throws NoSuchFieldException, IllegalAccessException {
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        //when
        game.dealingCardAll(2);
        //then
        Field playerHandsField = player.getClass().getSuperclass().getDeclaredField("hands");
        Field dealerHandsField = dealer.getClass().getSuperclass().getDeclaredField("hands");
        playerHandsField.setAccessible(true);
        dealerHandsField.setAccessible(true);

        List<Card> playerHands = (List<Card>) playerHandsField.get(player);
        List<Card> dealerHands = (List<Card>) dealerHandsField.get(dealer);

        assertThat(playerHands.size()).isEqualTo(2);
        assertThat(dealerHands.size()).isEqualTo(2);
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

    @DisplayName("플레이어가 게임을 계속 진행할것인지 입력받는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"Y"})
    public void testInputContinueGameChose(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        //when
        String actual = game.inputContinueGameChose();
        //then
        assertThat(actual).isEqualTo("Y");
    }

    @DisplayName("플레이어가 게임을 계속 진행할것인지 입력을 받는데 잘못 입력하는 경우 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"YN\nY", " \nY", "213\nY", "yn\nY"})
    public void testInputContinueGameChose_whenInvalidInput_thenOutputWarning(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        //when
        String actual = game.inputContinueGameChose();
        //then
        assertThatThrownBy(()-> {throw new InvalidPlayerChoseException();});
        assertThat(actual).isEqualTo("Y");
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

    @DisplayName("플레이어가 히트를 선택하여 카드를 1장 추가하는 테스트")
    @ParameterizedTest
    @ValueSource(strings = {"1"})
    public void testDealingCardOneToUser(String input){
        //given
        InputStream in = generateInputStream(input);
        System.setIn(in);
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        game.dealingCardAll(2);
        //when
        game.inputPlayerChose();
        game.dealingCardOneToUser(player);
        //then
        assertThat(player.getHands().size()).isEqualTo(3);
    }

}