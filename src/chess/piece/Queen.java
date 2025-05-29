package chess.piece;

import chess.Board;
import chess.piece.MoveCheck.bishopMoveCheck;
import chess.piece.MoveCheck.rookMoveCheck;

public class Queen extends ChessPiece implements bishopMoveCheck, rookMoveCheck {
    public Queen(Board board, int pieceColor) {
        super(board, pieceColor, "Queen");
    }

    @Override
    public boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (isWithinChessboard(targetRow, targetCol))
            return false;

        if (isRookMove(currentRow, currentCol, targetRow, targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol))
            return true;
        else
            return isBishopMove(currentRow, currentCol, targetRow, targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}
