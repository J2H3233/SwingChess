package chess.piece;


import chess.Board;
import chess.piece.ChessPiece;

public class Pawn extends ChessPiece {

    private int direction = 0; // 폰은 색깔에 따라 고정된 이동방향이 존재한다 ㅅㅂ
    private boolean firstMove = true;

    public Pawn(Board board, int pieceColor) {
        super(board, pieceColor, "Pawn");
        if (pieceColor == 1) direction = 1;
        else if (pieceColor == 0) direction = -1;
        else System.out.print("폰 방향 지정 오류");
    }

    boolean isFirstMove() {
        if (firstMove) {
            firstMove = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
        // 첫 두칸 이동
        if (isFirstMove() && targetRow - currentRow == 2 * direction) {
            if (board.chessboard[currentRow + direction][currentCol].getPiece() instanceof nothing && board.chessboard[currentRow + 2 * direction][currentCol].getPiece() instanceof nothing)
                return true;
        }
        // 대각 공격
        if (pawnAttack(currentRow, currentCol, targetRow, targetCol)) {
            return true;
        }
        // 일반 이동
        return (targetRow - currentRow) == direction && currentCol == targetCol && board.chessboard[targetRow][targetCol].getPiece() instanceof nothing;
    }

    public boolean pawnAttack(int currentRow, int currentCol, int targetRow, int targetCol) {

        // 대각선 이동: 대각선 방향으로 상대방 기물이 있을 경우 이동 가능
        if ((targetRow - currentRow) == direction) {
            if (Math.abs(currentCol - targetCol) == 1) {// 상대편 기물이 있을 때만 대각선 이동 가능
                return !(board.chessboard[targetRow][targetCol].getPiece() instanceof nothing) && board.chessboard[targetRow][targetCol].getPieceColor() != this.getPieceColor();
            }
        }
        return false;
    }
}