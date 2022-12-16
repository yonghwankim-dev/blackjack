package com;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ChoseTest {

    @DisplayName("정수 chose에 따른 Chose enum 객체 생성")
    @Test
    public void testValueOf(){
        //given

        //when
        Chose chose = Chose.valueOf("HIT");
        //then
        Assertions.assertThat(chose).isEqualTo(Chose.HIT);
    }

    @DisplayName("Chose가 HIT인지 테스트")
    @Test
    public void testIsHit(){
        //given

        //when
        Chose chose = Chose.HIT;
        //then
        Assertions.assertThat(chose.isHIT()).isTrue();
    }
}