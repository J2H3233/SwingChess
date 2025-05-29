package chess.piece;

import chess.Board;
import chess.piece.MoveCheck.bishopMoveCheck;

public class Bishop extends ChessPiece implements bishopMoveCheck {

    public Bishop(Board board, int pieceColor) {
        super(board, pieceColor, "Bishop");
    }

    @Override
    public boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
        return isBishopMove(currentRow, currentCol, targetRow, targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}
