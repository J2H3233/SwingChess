package chess.piece;

import chess.Board;

public abstract class ChessPiece {

    protected String pieceType;
    protected int pieceColor;
    protected Board board;


    public ChessPiece(Board board, int pieceColor, String pieceType) {
        this.pieceType = pieceType;
        this.pieceColor = pieceColor;
        this.board = board;
    }

    public String getPieceType() {
        return pieceType;
    }

    public int getPieceColor() {
        return pieceColor;
    }

    boolean isProgressBlocked(int currentRow, int currentCol, int targetRow, int targetCol) {

        int directionRow = checkDirection(currentRow, targetRow);
        int directionCol = checkDirection(currentCol, targetCol);

        int Row = currentRow + directionRow;
        int Col = currentCol + directionCol;

        while (Row != targetRow || Col != targetCol) {
            if (!(board.chessboard[Row][Col].getPiece() instanceof nothing)) {
                return false;
            }
            Row += directionRow;
            Col += directionCol;
        }

        return true;
    }

    int checkDirection(int k1, int k2) {
        return Integer.compare(k2, k1);
    }

    protected boolean isWithinChessboard(int targetRow, int targetCol) {
        return (targetRow < 0 || targetRow >= 8) || (targetCol < 0 || targetCol >= 8);
    }

    boolean blockSameColor(int currentRow, int currentCol, int targetRow, int targetCol) {
        return (board.chessboard[targetRow][targetCol].getPieceColor() != getPieceColor());
    }
    public void move(int currentRow, int currentCol, int targetRow, int targetCol) {
        board.MovePiece(currentRow, currentCol, targetRow, targetCol);
    }
    public abstract boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol);
}
