package com;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class OutputViewTest {
    @DisplayName("플레이어에게 현재 패상황을 보여주는 테스트")
    @Test
    public void testShowHands(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        OutputView view = new OutputView();
        game.dealingCardAll(2);
        //when
        view.showHands(player, dealer);
        //then
    }

    @DisplayName("플레이어와 딜러에게 카드를 2장씩 나누어줬을때 딜러의 카드는 한개를 제외한 모두 비공개인지 확인하는 테스트")
    @Test
    public void testShowHands_whenDealingCardAll_thenDealerCardIsClose(){
        //given
        User player = new Player("KYH", 500);
        User dealer = new Dealer();
        Game game = new Game(player, dealer);
        game.dealingCardAll(2);
        //when
        game.showHands();
        //then
    }

}