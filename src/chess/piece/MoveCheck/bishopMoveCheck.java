package chess.piece.MoveCheck;

public interface bishopMoveCheck {
    default boolean isBishopMove(int currentRow, int currentCol, int targetRow, int targetCol) {
        return Math.abs(currentRow - targetRow) == Math.abs(targetCol - currentCol);
    }
}
