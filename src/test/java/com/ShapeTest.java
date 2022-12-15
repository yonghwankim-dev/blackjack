package com;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class ShapeTest {
    @Test
    public void testShape(){
        //given

        //when
        Shape actual = Shape.valueOf("HEART");
        //then
        Assertions.assertThat(actual).isEqualTo(Shape.HEART);
        Assertions.assertThat(actual.getValue()).isEqualTo("♡");
    }

}