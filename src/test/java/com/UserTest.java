package com;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.CardStatus.OPEN;
import static com.CardValue.ACE;
import static com.CardValue.ACEONE;
import static com.Shape.CLOVER;
import static com.Shape.HEART;
import static org.assertj.core.api.Assertions.assertThat;

public class UserTest {
    @DisplayName("에이스 카드를 가지고 버스트 상태라면 에이스 카드의 가치를 1로 변경하는 테스트")
    @Test
    public void testReplaceAceToAce_whenIsBurst_thenAceToAceOne(){
        //given
        User player = new Player("KYH", 500);
        //when
        player.addCard(new Card(ACE, HEART, OPEN));
        player.addCard(new Card(ACE, CLOVER, OPEN));

        player.replaceAceToAce(ACE, ACEONE);
        //then
        assertThat(player.getScore()).isEqualTo(12);
    }


}
