package chess;

import java.util.Scanner;

public class gameCliTest {

    public static void main(String[] args) {

        Board game = new Board();
        game.setGame();
        Scanner s = new Scanner(System.in);
        int row1, col1, row2, col2;

        while (true) {
            game.printBoard();
            System.out.print("현재 위치와 이동할 위치를 입력하십시오 (종료하려면 999를 입력하십시오): ");
            String input = s.nextLine();

            if (input.equals("999")) {
                System.out.println("게임을 종료합니다.");
                s.close();
                break;
            }

            String[] coordinates = input.split(" ");
            if (coordinates.length != 4) {
                System.out.println("유효하지 않은 입력입니다. 네 개의 정수를 공백으로 구분하여 입력하십시오.");
                continue;
            }

            try {
                row1 = Integer.parseInt(coordinates[0]);
                col1 = Integer.parseInt(coordinates[1]);
                row2 = Integer.parseInt(coordinates[2]);
                col2 = Integer.parseInt(coordinates[3]);
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 입력입니다. 유효한 정수를 입력하십시오.");
                continue;
            }

            if (game.MovePiece(row1, col1, row2, col2)) {
                System.out.println("이동 결과" + row1 + "," + col1 + " -> " + row2 + "," + col2);
            } else {
                System.out.println("유효하지 않은 이동입니다.");
            }
        }
        s.close();
    }
}
