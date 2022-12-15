package com;

public class OutputView {
    public void showPlayerInputConfirm(Player player) {
        System.out.println("입력정보를 확인해주세요");
        System.out.println("-------------------------------");
        System.out.printf("NAME : %-3s, POINT : %d%n", player.getName(), player.getPoint());
        System.out.println("-------------------------------");
    }

    public void showPlayerRemainingPoint(Player player) {
        System.out.printf("%3s님의 남은 포인트 : %d%n", player.getName(), player.getPoint());
    }
}
