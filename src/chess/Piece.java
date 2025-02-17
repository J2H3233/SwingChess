package chess;

import java.util.Scanner;


abstract class chessPiece{

	protected String pieceType;
	protected int pieceColor;
	protected board board;


	chessPiece(board board, int pieceColor, String pieceType){
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
		this.board = board;
	}
	public String getPieceType() {
		return pieceType;
	}
	public int getPieceColor() {
		return pieceColor;
	}

	boolean isProgressBlocked(int currentRow, int currentCol, int targetRow, int targetCol) {
		
		int directionRow = checkDirection(currentRow, targetRow);
		int directionCol = checkDirection(currentCol, targetCol);

	    int Row = currentRow + directionRow;
	    int Col = currentCol + directionCol;

	    while (Row != targetRow || Col != targetCol) {
	        if (!(board.chessboard[Row][Col].getPiece() instanceof nothing)) {
	            return false; 
	        }
	        Row += directionRow;
	        Col += directionCol;
	    }
		
		return true;
	}
	
	int checkDirection(int k1, int k2){
		
		if(k1==k2) return 0;
		else if(k1>k2) return -1;
		else if(k1<k2) return 1;
		else return 0;
	}
	
	boolean isWithinChessboard(int targetRow, int targetCol) {
		return (targetRow >= 0 && targetRow < 8) && (targetCol >= 0 && targetCol < 8);
	}

	boolean blockSameColor(int currentRow, int currentCol, int targetRow, int targetCol) {
		return (board.chessboard[targetRow][targetCol].getPieceColor() != getPieceColor());
	}
	abstract boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol);
}

class nothing extends chessPiece{

	nothing(board board) {
		super( board,-1,"");
	}

	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
		System.out.print("잘못된 동작입니다");
		return false;
	}
}

interface rookMoveCheck{
	default boolean isRookMove(int currentRow, int currentCol, int targetRow, int targetCol) {
		if(currentRow==targetRow && targetCol!=currentCol)
			return true;
		else return currentRow != targetRow && targetCol == currentCol;
    }
}
interface bishopMoveCheck{
	default boolean isBishopMove(int currentRow, int currentCol, int targetRow, int targetCol) {
		return Math.abs(currentRow-targetRow) == Math.abs(targetCol-currentCol);
	}
}
interface knightMoveCheck{
	default boolean isKnightMove(int currentRow, int currentCol, int targetRow, int targetCol) {
		int distanceRow = currentRow-targetRow;
		int distanceCol = currentCol-targetCol;
		if(Math.abs(distanceRow) == 1 &&  Math.abs(distanceCol) == 2)
			return true;
        return Math.abs(distanceRow) == 2 && Math.abs(distanceCol) == 1;
    }
}


class Pawn extends chessPiece{

	private int direction = 0; // 폰은 색깔에 따라 고정된 이동방향이 존재한다 ㅅㅂ
	private boolean firstMove = true;

	Pawn(board board,int pieceColor) {
		super(board,pieceColor,"Pawn");
		if(pieceColor == 1) direction = 1;
		else if(pieceColor == 0) direction = -1;
		else System.out.print("폰 방향 지정 오류");
	}
	boolean isFirstMove() { 
		if(firstMove){
            firstMove = false;
            return true;
        }
		return false;
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol)) {
            return false;
        }

		if( isFirstMove() && targetRow - currentRow== 2*direction ) {
			if (board.chessboard[currentRow + direction][currentCol].getPiece() instanceof nothing && board.chessboard[currentRow + 2*direction][currentCol].getPiece() instanceof nothing)
					return true;
			}
	    // 대각선 이동: 대각선 방향으로 상대방 기물이 있을 경우 이동 가능
	    if ((targetRow - currentRow) == direction) { 
	    	
	    	if(Math.abs(currentCol - targetCol) == 1) {
	    		if (!(board.chessboard[targetRow][targetCol].getPiece() instanceof nothing) && board.chessboard[targetRow][targetCol].getPieceColor() != this.getPieceColor()) {
		            // 상대편 기물이 있을 때만 대각선 이동 가능
		                return true;
		        }	
	    	}else if(currentCol == targetCol && board.chessboard[targetRow][targetCol].getPiece() instanceof nothing)
	    		return true;
	    }
		return false;
	}

}
class King extends chessPiece{

	King(board board,int pieceColor) {
		super(board,pieceColor, "King");
	}
	@Override
    boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
		if (!isWithinChessboard(targetRow, targetCol)) 
			return false;
		if (Math.abs(currentRow - targetRow) <= 1 && Math.abs(currentCol - targetCol) <= 1 && blockSameColor(currentRow, currentCol, targetRow, targetCol)) {
			if (isAttackedKnight(targetRow, targetCol) || isAttackedRook(targetRow, targetCol) || isAttackedBishop(targetRow, targetCol)) {
				System.out.println(targetRow + "," + targetCol + "이동은 킹이 위험합니다");
				return false;
			}
			return true;
		}
		return false;
	}
	boolean isAttackedKnight(int targetRow, int targetCol) {
		int[][] knightMoves = {
			{2, 1}, {2, -1}, {-2, 1}, {-2, -1},
			{1, 2}, {1, -2}, {-1, 2}, {-1, -2}
		};

		for (int[] move : knightMoves) {
			int newRow = targetRow + move[0];
			int newCol = targetCol + move[1];

			if (isWithinChessboard(newRow, newCol)) {
				chessPiece piece = board.chessboard[newRow][newCol].getPiece();
				if (piece instanceof Knight && piece.getPieceColor() != this.getPieceColor()) {
					return true;
				}
			}
		}
		return false;
	}
	boolean isAttackedRook(int targetRow, int targetCol) {
		int[][] directions = {
			{1, 0}, {-1, 0}, {0, 1}, {0, -1}
		};

		for (int[] direction : directions) {
			int newRow = targetRow + direction[0];
			int newCol = targetCol + direction[1];

			while (isWithinChessboard(newRow, newCol)) {
				chessPiece piece = board.chessboard[newRow][newCol].getPiece();
				if (!(piece instanceof nothing)) {
					if (piece.getPieceColor() != this.getPieceColor() && (piece instanceof Rook || piece instanceof Queen)) {
						return true;
					} else {
						break;
					}
				}
				newRow += direction[0];
				newCol += direction[1];
			}
		}
		return false;
	}
	boolean isAttackedBishop(int targetRow, int targetCol) {
		int[][] directions = {
			{1, 1}, {1, -1}, {-1, 1}, {-1, -1}
		};

		for (int[] direction : directions) {
			int newRow = targetRow + direction[0];
			int newCol = targetCol + direction[1];

			while (isWithinChessboard(newRow, newCol)) {
				chessPiece piece = board.chessboard[newRow][newCol].getPiece();
				if (!(piece instanceof nothing)) {
					if (piece.getPieceColor() != this.getPieceColor() && (piece instanceof Bishop || piece instanceof Queen)) {
						return true;
					} else {
						break;
					}
				}
				newRow += direction[0];
				newCol += direction[1];
			}
		}
		return false;
	}




}

class Rook extends chessPiece implements rookMoveCheck{

	Rook(board board,int pieceColor) {
		super(board,pieceColor, "Rook");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
        return isRookMove(currentRow, currentCol, targetRow, targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}

class Bishop extends chessPiece implements bishopMoveCheck{

	Bishop(board board,int pieceColor) {
		super(board,pieceColor, "Bishop");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
        return isBishopMove(currentRow, currentCol, targetRow, targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}

class Queen extends chessPiece implements bishopMoveCheck,rookMoveCheck{
    Queen(board board,int pieceColor) {
		super(board,pieceColor, "Queen");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol))
            return false;

        if(isRookMove(currentRow,currentCol, targetRow,targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol) )
            return true;
		else return isBishopMove(currentRow, currentCol, targetRow, targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}

class Knight extends chessPiece implements knightMoveCheck{

	Knight(board board,int pieceColor) {
		super(board ,pieceColor, "Knight");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
        return isKnightMove(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol);
    }
}
