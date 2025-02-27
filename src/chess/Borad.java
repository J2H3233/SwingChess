package chess;


import java.util.Scanner;

class Cell{
    private chessPiece piece;
    private int currentRow;
    private int currentColumn;

    Cell(chessPiece piece, int currentRow, int currentColumn){
        this.piece = piece;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
    }
    public chessPiece getPiece() {
        return piece;
    }
    public void setPiece(chessPiece piece) {
        this.piece = piece;
    }
    public int getCurrentRow() {
        return currentRow;
    }
    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }
    public int getCurrentColumn() {
        return currentColumn;
    }
    public void setCurrentColumn(int currentColumn) {
        this.currentColumn = currentColumn;
    }
    public void setPoint(int i, int j) {
        setCurrentRow(i);
        setCurrentColumn(j);
    }
    public int getPieceColor() {
        return piece.getPieceColor();
    }
    public String getPieceType() {
        return piece.getPieceType();
    }
}

class board {

    Cell[][] chessboard = new Cell[8][8];
    boolean checkTurn = false; // false = white, true = black

    void initialize() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setDefault(i, j);
            }
        }
    }

    void printBoard() {
        System.out.println("======================================================================================================================================");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                printOne(i, j);
            }
            System.out.println(); // 줄바꿈을 명확히 하기 위해 println 사용
        }
        System.out.println("======================================================================================================================================");
    }

    void setDefault(int i, int j) {
        chessboard[i][j] = new Cell(new nothing(this), i, j);
    }

    void setPiece(chessPiece piece, int i, int j) {
        chessboard[i][j].setPiece(piece);
    }

    void printOne(int i, int j) {
        if (chessboard[i][j].getPiece() instanceof nothing) {
            System.out.printf("%-12s", chessboard[i][j].getCurrentRow() + "" + chessboard[i][j].getCurrentColumn());
        } else {

            switch (chessboard[i][j].getPiece().getPieceColor()) {
                case 0:
                    System.out.printf("%-12s", chessboard[i][j].getCurrentRow() + "" + chessboard[i][j].getCurrentColumn() + " W." + chessboard[i][j].getPiece().getPieceType());
                    break;
                case 1:
                    System.out.printf("%-12s", chessboard[i][j].getCurrentRow() + "" + chessboard[i][j].getCurrentColumn() + " B." + chessboard[i][j].getPiece().getPieceType());
                    break;
            }
        }
    }

    void changeTurn() {
        checkTurn = !checkTurn;
    }

    void setGame() {
        for (int i = 0; i < 8; i++) {
            setPiece(new Pawn(this, 1), 1, i);
            setPiece(new Pawn(this, 0), 6, i);
        }
        setPiece(new Rook(this, 0), 7, 0);
        setPiece(new Rook(this, 0), 7, 7);
        setPiece(new Knight(this, 0), 7, 1);
        setPiece(new Knight(this, 0), 7, 6);
        setPiece(new Bishop(this, 0), 7, 2);
        setPiece(new Bishop(this, 0), 7, 5);
        setPiece(new Queen(this, 0), 7, 3);
        setPiece(new King(this, 0), 7, 4);

        setPiece(new Rook(this, 1), 0, 0);
        setPiece(new Rook(this, 1), 0, 7);
        setPiece(new Knight(this, 1), 0, 1);
        setPiece(new Knight(this, 1), 0, 6);
        setPiece(new Bishop(this, 1), 0, 2);
        setPiece(new Bishop(this, 1), 0, 5);
        setPiece(new Queen(this, 1), 0, 3);
        setPiece(new King(this, 1), 0, 4);
    }

    boolean isKingInCheck(int kingRow, int kingCol, int attackerColor) {
        // 체스판 전체를 순회하며 상대 기물이 우리 킹을 공격할 수 있는지 확인
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                chessPiece piece = chessboard[i][j].getPiece();
                if (piece instanceof nothing) continue; // 빈칸은 무시
                if (piece.getPieceColor() == attackerColor) { // 적 기물만 검사
                    if (piece instanceof Pawn) {
                        if (((Pawn) piece).pawnAttack(i, j, kingRow, kingCol)) {
                            return true;
                        }
                    } else {
                        if (piece.rowMagic(i, j, kingRow, kingCol)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
//
//    boolean isCheckmate(int kingRow, int kingCol, int kingColor) {
//        if (!isKingInCheck(kingRow, kingCol, kingColor == 1 ? 0 : 1)) {
//            return false; // 킹이 체크 상태가 아니면 체크메이트가 아님
//        }
//
//        // 1️⃣ 킹이 이동할 수 있는지 확인
//        int[] dRow = {-1, -1, -1, 0, 0, 1, 1, 1};
//        int[] dCol = {-1, 0, 1, -1, 1, -1, 0, 1};
//
//        for (int d = 0; d < 8; d++) {
//            int newRow = kingRow + dRow[d];
//            int newCol = kingCol + dCol[d];
//
//            if (isValidPosition(newRow, newCol) &&
//                    (chessboard[newRow][newCol].getPiece() instanceof nothing ||
//                            chessboard[newRow][newCol].getPiece().getPieceColor() != kingColor)) {
//
//                // 이동 후 체크 상태인지 확인
//                chessPiece original = chessboard[newRow][newCol].getPiece();
//                chessboard[newRow][newCol].setPiece(chessboard[kingRow][kingCol].getPiece());
//                chessboard[kingRow][kingCol].setPiece(new nothing(this));
//
//                boolean stillInCheck = isKingInCheck(newRow, newCol, kingColor == 1 ? 0 : 1);
//
//                // 원상 복구
//                chessboard[kingRow][kingCol].setPiece(chessboard[newRow][newCol].getPiece());
//                chessboard[newRow][newCol].setPiece(original);
//
//                if (!stillInCheck) return false; // 킹이 도망칠 수 있으면 체크메이트가 아님
//            }
//        }
//
//        // 2️⃣ 공격하는 기물을 찾아서 잡을 수 있는지 확인
//        int attackerRow = -1, attackerCol = -1;
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                if (chessboard[i][j].getPiece().rowMagic(i, j, kingRow, kingCol)) {
//                    attackerRow = i;
//                    attackerCol = j;
//                    break;
//                }
//            }
//        }
//
//        if (attackerRow != -1 && attackerCol != -1) {
//            for (int i = 0; i < 8; i++) {
//                for (int j = 0; j < 8; j++) {
//                    if (chessboard[i][j].getPiece().getPieceColor() == kingColor &&
//                            chessboard[i][j].getPiece().rowMagic(i, j, attackerRow, attackerCol)) {
//                        return false; // 공격하는 기물을 잡을 수 있으면 체크메이트가 아님
//                    }
//                }
//            }
//        }
//
//        // 3️⃣ 공격하는 기물의 경로를 차단할 수 있는지 확인 (퀸, 룩, 비숍만 해당)
//        if (chessboard[attackerRow][attackerCol].getPiece() instanceof Queen ||
//                chessboard[attackerRow][attackerCol].getPiece() instanceof Rook ||
//                chessboard[attackerRow][attackerCol].getPiece() instanceof Bishop) {
//
//            int dirRow = (kingRow > attackerRow) ? 1 : (kingRow < attackerRow) ? -1 : 0;
//            int dirCol = (kingCol > attackerCol) ? 1 : (kingCol < attackerCol) ? -1 : 0;
//
//            int blockRow = attackerRow + dirRow;
//            int blockCol = attackerCol + dirCol;
//
//            while (blockRow != kingRow || blockCol != kingCol) {
//                for (int i = 0; i < 8; i++) {
//                    for (int j = 0; j < 8; j++) {
//                        if (chessboard[i][j].getPiece().getPieceColor() == kingColor &&
//                                chessboard[i][j].getPiece().rowMagic(i, j, blockRow, blockCol)) {
//                            return false; // 공격 경로를 차단할 수 있으면 체크메이트가 아님
//                        }
//                    }
//                }
//                blockRow += dirRow;
//                blockCol += dirCol;
//            }
//        }
//
//        // 🏆 위 모든 방법이 실패했다면 체크메이트!
//        return true;
//    }


    // 유효한 체스판 위치인지 확인
    boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }


    boolean MovePiece(int currentRow, int currentCol, int targetRow, int targetCol) {
        chessPiece temp = null;

        // 본인 턴 확인
        if (chessboard[currentRow][currentCol].getPieceColor() != (checkTurn ? 1 : 0)) {
            return false;
        }

        // 이동 가능 여부 확인
        if (!chessboard[currentRow][currentCol].getPiece().rowMagic(currentRow, currentCol, targetRow, targetCol)) {
            return false;
        }

        // 기물 이동
        if (chessboard[targetRow][targetCol].getPiece() instanceof nothing) {
            temp = chessboard[currentRow][currentCol].getPiece();
            chessboard[currentRow][currentCol].setPiece(chessboard[targetRow][targetCol].getPiece());
            chessboard[targetRow][targetCol].setPiece(temp);
        } else {
            chessboard[targetRow][targetCol].setPiece(chessboard[currentRow][currentCol].getPiece());
            chessboard[currentRow][currentCol].setPiece(new nothing(this));
        }

        // 내 킹 위치 찾기
        int myKingRow = -1, myKingCol = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j].getPiece() instanceof King
                        && chessboard[i][j].getPieceColor() == (checkTurn ? 1 : 0)) {
                    myKingRow = i;
                    myKingCol = j;
                }
            }
        }



        if(isKingInCheck(myKingRow,myKingCol,(checkTurn ? 0 : 1))){
            if (chessboard[currentRow][currentCol].getPiece() instanceof nothing) {
                temp = chessboard[targetRow][targetCol].getPiece();
                chessboard[targetRow][targetCol].setPiece(chessboard[currentRow][currentCol].getPiece());
                chessboard[currentRow][currentCol].setPiece(temp);
            } else {
                chessboard[currentRow][currentCol].setPiece(chessboard[targetRow][targetCol].getPiece());
                chessboard[targetRow][targetCol].setPiece(new nothing(this));
            }
            System.out.println("내 킹이 체크 입니다.");
            return true;
        }




        // 턴 변경
        changeTurn();

        int yourKingRow = -1, yourKingCol = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j].getPiece() instanceof King
                        && chessboard[i][j].getPieceColor() == (checkTurn ? 1 : 0)) {
                    yourKingRow = i;
                    yourKingCol = j;
                }
            }
        }

        if(isKingInCheck(yourKingRow,yourKingCol,(checkTurn ? 0 : 1))){

            System.out.println("상대 킹이 체크 입니다.");
        }

        return true;
    }


}





class gameCliTest{

	public static void main(String[] args) {

        board game = new board();
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