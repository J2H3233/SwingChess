package chess.piece;

import chess.Board;

public class King extends ChessPiece {

	public King(Board board, int pieceColor) {
        super(board, pieceColor, "King");
    }

    @Override
    public boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (isWithinChessboard(targetRow, targetCol))
            return false;
        if (Math.abs(currentRow - targetRow) <= 1 && Math.abs(currentCol - targetCol) <= 1 && blockSameColor(currentRow, currentCol, targetRow, targetCol)) {
//			if (isAttackedKnight(targetRow, targetCol) || isAttackedRook(targetRow, targetCol) || isAttackedBishop(targetRow, targetCol)) {
//				System.out.println(targetRow + "," + targetCol + "이동은 킹이 위험합니다");
//				return false;
//			}
            return true;
        }
        return false;
    }

//	boolean isAttackedKnight(int targetRow, int targetCol) {
//		int[][] knightMoves = {
//			{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
//			{1, 2}, {1, -2}, {-1, 2}, {-1, -2}
//		};
//
//		for (int[] move : knightMoves) {
//			int newRow = targetRow + move[0];
//			int newCol = targetCol + move[1];
//
//			if (isWithinChessboard(newRow, newCol)) {
//				ChessPiece piece = board.chessboard[newRow][newCol].getPiece();
//				if (piece instanceof Knight && piece.getPieceColor() != this.getPieceColor()) {
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//	boolean isAttackedRook(int targetRow, int targetCol) {
//		int[][] directions = {
//			{1, 0}, {-1, 0}, {0, 1}, {0, -1}
//		};
//
//		for (int[] direction : directions) {
//			int newRow = targetRow + direction[0];
//			int newCol = targetCol + direction[1];
//
//			while (isWithinChessboard(newRow, newCol)) {
//				ChessPiece piece = board.chessboard[newRow][newCol].getPiece();
//				if (!(piece instanceof nothing.java)) {
//					if (piece.getPieceColor() != this.getPieceColor() && (piece instanceof Rook || piece instanceof Queen)) {
//						return true;
//					} else {
//						break;
//					}
//				}
//				newRow += direction[0];
//				newCol += direction[1];
//			}
//		}
//		return false;
//	}
//	boolean isAttackedBishop(int targetRow, int targetCol) {
//		int[][] directions = {
//			{1, 1}, {1, -1}, {-1, 1}, {-1, -1}
//		};
//
//		for (int[] direction : directions) {
//			int newRow = targetRow + direction[0];
//			int newCol = targetCol + direction[1];
//
//			while (isWithinChessboard(newRow, newCol)) {
//				ChessPiece piece = board.chessboard[newRow][newCol].getPiece();
//				if (!(piece instanceof nothing.java)) {
//					if (piece.getPieceColor() != this.getPieceColor() && (piece instanceof Bishop || piece instanceof Queen)) {
//						return true;
//					} else {
//						break;
//					}
//				}
//				newRow += direction[0];
//				newCol += direction[1];
//			}
//		}
//		return false;
//	}
}
