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
}