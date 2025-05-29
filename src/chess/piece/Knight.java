package chess.piece;

import chess.Board;
import chess.piece.MoveCheck.knightMoveCheck;

public class Knight extends ChessPiece implements knightMoveCheck {

    public Knight(Board board, int pieceColor) {
        super(board, pieceColor, "Knight");
    }

    @Override
    public boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
        return isKnightMove(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}
