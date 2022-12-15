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

}