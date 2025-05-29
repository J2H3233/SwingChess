package chess;

import chess.piece.ChessPiece;

public class Cell {
    private ChessPiece piece;
    private int currentRow;
    private int currentColumn;

    Cell(ChessPiece piece, int currentRow, int currentColumn) {
        this.piece = piece;
        this.currentRow = currentRow;
        this.currentColumn = currentColumn;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
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
