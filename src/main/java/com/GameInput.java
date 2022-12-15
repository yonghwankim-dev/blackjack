package com;

import com.exception.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.GameInputValidate.validatePlayerChose;
import static com.GameInputValidate.validatePlayerName;

public class GameInput {
    private final BufferedReader br;

    public GameInput() {
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public String inputPlayerName(){
        String name;
        System.out.println("플레이어의 이름(3 english letters)은? ex) KYH");
        while(true){
            try {
                name = br.readLine();
                if(!validatePlayerName(name)) throw new InvalidPlayerNameException();
                break;
            }catch (InvalidPlayerNameException e){
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return name;
    }

    public int inputPlayerChose(User player) {
        String input;
        while(true){
            System.out.printf("%3s님 선택해 주세요 ex) 1%n", player.getName());
            System.out.println("1. 히트(hit)");
            System.out.println("2. 스탠드(stand)");
            try{
                input = br.readLine();
                if(!validatePlayerChose(input)) throw new InvalidPlayerChoseException();
                break;
            } catch (InvalidPlayerChoseException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return toInt(input);
    }

    private int toInt(String text){
        return Integer.parseInt(text);
    }

    public int inputPlayerPoint(String name) {
        String input;
        while(true){
            System.out.printf("%3s님의 포인트는 얼마입니까?%n", name);
            try{
                input = br.readLine();
                if(!GameInputValidate.validatePlayerPoint(input)) throw new PointNumberFormatException();
                if(toInt(input) <= 0) throw new PointNumberFormatException();
                break;
            } catch (PointNumberFormatException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return toInt(input);
    }

    public int inputPlayerPointBatting(User player) {
        String input;
        while(true){
            System.out.printf("%3s님 포인트를 얼마나 베팅하시겠습니까?%n", player.getName());
            try{
                input = br.readLine();
                if(!GameInputValidate.validatePlayerPoint(input)) throw new PointNumberFormatException();
                if(!isEnoughPointBatting(player, toInt(input))) throw new PointBattingNotEnoughException(player);
                break;
            } catch (PointNumberFormatException | PointBattingNotEnoughException e) {
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return toInt(input);
    }

    private boolean isEnoughPointBatting(User player, int point){
        return point > 0 && point <= player.getPoint();
    }

    public String inputContinueGameChose(User dealer) {
        String yn;
        System.out.printf("%s : 게임을 계속 진행하시겠습니까? (Y/N)", dealer.getName());

        while(true){
            try {
                yn = br.readLine();
                if(!GameInputValidate.validateContinueGameChose(yn)) throw new InvalidContinueGameChose();
                break;
            }catch (InvalidContinueGameChose e){
                System.out.println(e.getMessage());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return yn;
    }
}
