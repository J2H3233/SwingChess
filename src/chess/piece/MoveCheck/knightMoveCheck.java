package chess.piece.MoveCheck;

public interface knightMoveCheck {
    default boolean isKnightMove(int currentRow, int currentCol, int targetRow, int targetCol) {
        int distanceRow = currentRow - targetRow;
        int distanceCol = currentCol - targetCol;
        if (Math.abs(distanceRow) == 1 && Math.abs(distanceCol) == 2)
            return true;
        return Math.abs(distanceRow) == 2 && Math.abs(distanceCol) == 1;
    }
}
