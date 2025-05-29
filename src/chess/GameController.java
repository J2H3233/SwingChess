package chess;

import chess.piece.nothing;

public class GameController {
    private Board gameboard;
    private ChessGUI gui;
    private int[] selectedRow = {-1, -1};
    private int[] selectedCol = {-1, -1};
    private int count = 0;

    GameController() {
        gameboard = new Board();
        gameboard.initialize();
        gameboard.setGame();
        gui = new ChessGUI(this);
        updateBoard();
    }

    public void selectCell(int row, int col) {
        if (count % 2 == 0) { // 첫 번째 클릭: 이동할 기물 선택
            if (!(gameboard.chessboard[row][col].getPiece() instanceof nothing)) {
                selectedRow[0] = row;
                selectedCol[0] = col;
                count++;
            } else {
                resetSelection();
            }
        } else { // 두 번째 클릭: 이동할 위치 선택
            selectedRow[1] = row;
            selectedCol[1] = col;
            count++;

            if (movePiece(selectedRow[0], selectedCol[0], selectedRow[1], selectedCol[1])) {
                System.out.println("이동 성공: " + selectedRow[0] + "," + selectedCol[0] + " -> " + selectedRow[1] + "," + selectedCol[1]);
            } else {
                System.out.println("유효하지 않은 이동입니다.");
            }
            resetSelection();
        }
    }

    private boolean movePiece(int row1, int col1, int row2, int col2) {
        boolean moved = gameboard.MovePiece(row1, col1, row2, col2);
        if (moved) {
            updateBoard();
        }
        return moved;
    }

    private void updateBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gui.BoardGUI[i][j].setState(gameboard.chessboard[i][j].getPiece());
            }
        }
    }

    private void resetSelection() {
        selectedRow[0] = -1;
        selectedCol[0] = -1;
        selectedRow[1] = -1;
        selectedCol[1] = -1;
        count = 0;
    }

    public static void main(String[] args) {
        new GameController();

    }
}

//class GamePlay extends Thread {
//    public void run() {
//        board gameboard = new board();
//        ChessGUI gui = new ChessGUI();
//        board game = new board();
//        game.setGame();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                gui.BoardGUI[i][j].setState(gameboard.chessboard[i][j].getPiece());
//            }
//        }
//        System.out.println("count: " + gui.count);
//        while (true) {
//
//            try {
//                if (gui.count % 2 == 0&& gui.row[0] != -1 &&gui.row[1] != -1) {{
//                    if(game.MovePiece(gui.row[0], gui.col[0], gui.row[1], gui.col[1])){
//                        gui.BoardGUI[gui.row[1]][gui.col[1]].setState(gameboard.chessboard[gui.row[1]][gui.col[1]].getPiece());
//                        gui.BoardGUI[gui.row[0]][gui.col[0]].setState(gameboard.chessboard[gui.row[0]][gui.col[0]].getPiece());
//                    }
//                }
//                }
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//                System.out.println("쓰레드 오류");
//            }
//        }
//    }
//    public static void main(String[] args) {
//        GamePlay play = new GamePlay();
//        play.start();
//    }
//}
