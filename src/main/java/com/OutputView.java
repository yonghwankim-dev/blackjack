package com;

import java.util.List;
import java.util.stream.Collectors;

public class OutputView {
    public static void showUser(User user) {
        System.out.println("입력정보를 확인해주세요");
        System.out.println("-------------------------------");
        System.out.printf("NAME : %-3s, POINT : %d%n", user.getName(), user.getPoint());
        System.out.println("-------------------------------");
    }

    public static void showRemainingPoint(User user) {
        System.out.printf("%3s님의 남은 포인트 : %d%n", user.getName(), user.getPoint());
    }

    public static void showHands(User player, User dealer) {
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

        System.out.printf("|  %-6s  |  %s  |  %-5s  |%n", nameHeader, handsHeader, "SCORE");
        System.out.printf("|  %-6s  |  %s  |  %-5d  |%n", dealer.getName(), dealerHandsContent, dealer.getOpenedScore());
        System.out.printf("|  %-6s  |  %s  |  %-5d  |%n", player.getName(), playerHandsContent, player.getScore());
    }

    private static String formatHands(List<Card> cards){
        return cards.stream().map(Card::toString).collect(Collectors.joining(","));
    }

    public static void showWinner(User user){
        System.out.printf("%s 승%n", user.getName());
    }

    public static void showDraw(){
        System.out.println("무승부");
    }

    public static void showStartGame() {
        System.out.println("게임을 시작합니다.");
    }

    public static void showPoint(User user){
        System.out.printf("%s님 포인트 : %d%n", user.getName(), user.getPoint());
    }

    public static void showGameEnd() {
        System.out.println("게임을 종료합니다.");
    }

    public static void alertDealing(String name) {
        System.out.printf("%s : 패를 나누어줬습니다.%n", name);
    }

    public static void alertChose(Dealer dealer, Player player, Chose chose) {
        System.out.printf("%s : %3s님 %s를 선택하셨습니다.%n", dealer.getName(), player.getName(), chose.getName());
    }
}
