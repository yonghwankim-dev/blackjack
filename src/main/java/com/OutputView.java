package com;

public class OutputView {
    public void showPlayerInputConfirm(User player) {
        System.out.println("입력정보를 확인해주세요");
        System.out.println("-------------------------------");
        System.out.printf("NAME : %-3s, POINT : %d%n", player.getName(), player.getPoint());
        System.out.println("-------------------------------");
    }

    public void showPlayerRemainingPoint(User player) {
        System.out.printf("%3s님의 남은 포인트 : %d%n", player.getName(), player.getPoint());
    }

    public void showHandDealingToPlayer() {
        System.out.printf("딜러 : 패를 나누어줬습니다.%n");
    }

    public void showPlayerChoseResult(User player, String result) {
        System.out.printf("딜러 : %3s님 %s를 선택하셨습니다.%n", player.getName(), result);
    }
}
