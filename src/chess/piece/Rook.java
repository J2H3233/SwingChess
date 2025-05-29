package chess.piece;

import chess.Board;
import chess.piece.MoveCheck.rookMoveCheck;

public class Rook extends ChessPiece implements rookMoveCheck {

    public Rook(Board board, int pieceColor) {
        super(board, pieceColor, "Rook");
    }

    @Override
    public boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
        return isRookMove(currentRow, currentCol, targetRow, targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}
