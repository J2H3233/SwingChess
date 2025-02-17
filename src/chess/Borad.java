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

    boolean MovePiece(int currentRow, int currentCol, int targetRow, int targetCol) {
        // 본인턴인지 확인
        if (chessboard[currentRow][currentCol].getPieceColor() != (checkTurn ? 1 : 0)) {
            return false;
        }
        // 기물에 따른 적합한 이동인지 확인
        if (!chessboard[currentRow][currentCol].getPiece().rowMagic(currentRow, currentCol, targetRow, targetCol)) {
            return false;
        }
        // 기물이 킹일 경우 이동시 체크메이트인지 확인, 체크일 경우 이동을 중지
        if (chessboard[currentRow][currentCol].getPiece() instanceof King) {

        }

        // 기물이 킹이 아닐 경우 기물이 움직이면 같은 색 킹이 체크메이트 인지 확인
        // 이동이 적합함을 확정하고 턴을 변경
        changeTurn();
        if (chessboard[targetRow][targetCol].getPiece() instanceof nothing) {
            chessPiece temp = chessboard[currentRow][currentCol].getPiece();
            chessboard[currentRow][currentCol].setPiece(chessboard[targetRow][targetCol].getPiece());
            chessboard[targetRow][targetCol].setPiece(temp);
        } else {
            chessboard[targetRow][targetCol].setPiece(chessboard[currentRow][currentCol].getPiece());
            chessboard[currentRow][currentCol].setPiece(new nothing(this));
        }
        // 이동이 이뤄진 후 상대 킹이 체크 상태인지 확인
        // 체크 상태인 경우 체크 상태에서 벗어날 수 있는지 확인, 안될 경우 체크메이트에 의한 게임 종료

        return true;
    }

    boolean checkCheck(){

        int myKingRow=-1, myKingCol=-1;
        int yourKingRow=-1, yourKingCol=-1;
        for(int i=0; i<8;i++){
            for(int j=0; j<8;j++){
                if(chessboard[i][j].getPiece() instanceof King){
                    if(chessboard[i][j].getPieceColor() == (checkTurn ? 1:0)){
                        myKingRow = i;
                        myKingCol = j;
                    }else if(chessboard[i][j].getPieceColor() != (checkTurn ? 1:0)){
                        yourKingRow = i;
                        yourKingCol = j;
                    }
                }
            }
        }

//        for(int i=0; i<8;i++){
//            for(int j=0; j<8;j++){
//                if(chessboard[i][j].getPieceColor() != chessboard[myKingCol][myKingRow].getPieceColor()){
//                    chessboard[i][j].getPiece().rowMagic(i,j,myKingCol,myKingRow);
//                }
//            }
//        }

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