package com;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DealerTest {
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

    @DisplayName("플레이어에게 어떤 옵션을 선택했다고 말해주는 테스트")
    @Test
    public void testAlertChose(){
        //given
        Player player = new Player("KYH", 500);
        Dealer dealer = new Dealer();
        //when
        dealer.alertChose(player, Chose.HIT);
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("DEALER : KYH님 히트를 선택하셨습니다.");
    }

    @DisplayName("플레이어에게 패를 나누어주었다고 테스트")
    @Test
    public void testShowHandDealingToPlayer(){
        //given
        Dealer dealer = new Dealer();
        //when
        dealer.alertDealing();
        //then
        String[] outputs = output.toString().split("\r\n");
        assertThat(outputs[0]).isEqualTo("DEALER : 패를 나누어줬습니다.");
    }

    @DisplayName("게임에서 초기덱이 생성되는지 테스트")
    @Test
    public void testCreateCardDeck(){
        //given
        Dealer dealer = new Dealer();
        List<Card> expected = createCardDeckExpected();
        //when
        List<Card> actual = dealer.createCardDeck(4);
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

    @DisplayName("딜러와 플레이어에게 카드 두장씩 나눠주는 테스트")
    @Test
    public void testDealingCardAll() throws NoSuchFieldException, IllegalAccessException {
        //given
        Player player = new Player("KYH", 500);
        Dealer dealer = new Dealer();
        //when
        dealer.dealingTwoCard(player);
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

    @DisplayName("딜러가 히트를 하여 카드가 추가되는지 테스트")
    @Test
    public void testHit(){
        //given
        Dealer dealer = new Dealer();
        //when
        dealer.hit();
        //then
        assertThat(dealer.getHands().size()).isEqualTo(1);
    }

    @DisplayName("딜러가 히트가 필요한지 검사하는 테스트")
    @Test
    public void testIsNeedHit(){
        //given
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("A", Shape.CLOVER, 11));
        //when
        boolean actual = dealer.isNeedHit();
        //then
        assertThat(actual).isTrue();
    }

    @DisplayName("딜러가 딜링한 카드 2장이 첫번째는 오픈되어 있고 나머지는 덮혀있는지 테스트")
    @Test
    public void testCloseAllCardExceptOneCard(){
        //given
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("A", Shape.CLOVER, 11));
        dealer.addCard(new Card("2", Shape.CLOVER, 2));
        //when
        dealer.closeAllCardExceptOneCard();
        //then
        assertThat(dealer.getHands().get(0).getStatus()).isEqualTo(CardStatus.OPEN);
        assertThat(dealer.getHands().get(1).getStatus()).isEqualTo(CardStatus.CLOSE);
    }

    @DisplayName("딜러의 카드가 전부 오픈되어 있는지 확인하는 테스트")
    @Test
    public void testOpenAllCard(){
        //given
        Dealer dealer = new Dealer();
        dealer.addCard(new Card("A", Shape.CLOVER, 11));
        dealer.addCard(new Card("2", Shape.CLOVER, 2));
        //when
        dealer.openAllCard();
        //then
        assertThat(dealer.getHands().get(0).getStatus()).isEqualTo(CardStatus.OPEN);
        assertThat(dealer.getHands().get(1).getStatus()).isEqualTo(CardStatus.OPEN);
    }
}