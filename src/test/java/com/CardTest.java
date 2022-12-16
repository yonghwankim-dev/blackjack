package com;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.CardStatus.OPEN;
import static com.CardValue.ACE;
import static com.Shape.CLOVER;

public class CardTest {

    @DisplayName("두 카드의 해시코드가 같은지 테스트")
    @Test
    public void testHashCode(){
        //given
        Card card1 = new Card(ACE, CLOVER, OPEN);
        Card card2 = new Card(ACE, CLOVER, OPEN);

        //when
        boolean actual = card1.hashCode() == card2.hashCode();
        //then
        Assertions.assertThat(actual).isTrue();
    }

    @DisplayName("카드의 상태가 CLOSE일때 toString 문자열 확인 테스트")
    @Test
    public void testToString_whenStatusIsClose(){
        //given
        Card card1 = new Card(ACE, CLOVER, OPEN);
        //when
        card1.toClose();
        String actual = card1.toString();
        //then
        Assertions.assertThat(actual).isEqualTo("   ?");
    }

}