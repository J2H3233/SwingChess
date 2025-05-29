package chess.piece.MoveCheck;

public interface rookMoveCheck {
    default boolean isRookMove(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (currentRow == targetRow && targetCol != currentCol)
            return true;
        else return currentRow != targetRow && targetCol == currentCol;
    }
}
