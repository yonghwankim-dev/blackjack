package com;

import java.util.List;
import java.util.stream.Collectors;

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

    public void showPlayerChoseResult(User player, Chose chose) {
        System.out.printf("딜러 : %3s님 %s를 선택하셨습니다.%n", player.getName(), chose.getName());
    }

    public void showHands(User player, User dealer) {
        String dealerName = dealer.getName();
        String playerName = player.getName();
        int nameLen = Math.max(dealerName.length(), playerName.length());
        String nameHeader = String.format(String.format("%%-%ds", nameLen), "NAME");

        String dealerHands = formatHands(dealer.getHands());
        String playerHands = formatHands(player.getHands());
        int handsLen = Math.max(dealerHands.length(), playerHands.length());
        String handsHeader = String.format(String.format("%%-%ds", handsLen), "HANDS");
        String dealerHandsContent = String.format(String.format("%%-%ds", handsLen), dealerHands);
        String playerHandsContent = String.format(String.format("%%-%ds", handsLen), playerHands);

        System.out.printf("|  %-6s  |  %s  |  SCORE  |%n", nameHeader, handsHeader);
        System.out.printf("|  %-6s  |  %s  |  %-5d  |%n", dealer.getName(), dealerHandsContent, dealer.getOpenedScore());
        System.out.printf("|  %-6s  |  %s  |  %-5d  |%n", player.getName(), playerHandsContent, player.getScore());
    }

    private String formatHands(List<Card> cards){
        return cards.stream().map(Card::toString).collect(Collectors.joining(","));
    }

    public void showWinner(User user){
        System.out.printf("%s 승%n", user.getName());
    }

    public void showDraw(){
        System.out.println("무승부");
    }

    public void showStartGame() {
        System.out.println("게임을 시작합니다.");
    }

    public void showGameEnd(Player player) {
        System.out.printf("%s님 포인트 : %d%n",player.getName(), player.getPoint());
        System.out.println("게임을 종료합니다.");
    }
}
