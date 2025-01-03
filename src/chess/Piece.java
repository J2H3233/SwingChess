package chess;

import java.util.Scanner;


class Cell{
	private chessPiece piece;
	private int currentRow;
	private int currentColumn;

	Cell(chessPiece piece, int currentRow, int currentColumn){
		this.piece = piece;
		this.currentRow = currentRow;
		this.currentColumn = currentColumn;
	}
	public chessPiece getPiece() {
		return piece;
	}
	public void setPiece(chessPiece piece) {
		this.piece = piece;
	}
	public int getCurrentRow() {
		return currentRow;
	}
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}
	public int getCurrentColumn() {
		return currentColumn;
	}
	public void setCurrentColumn(int currentColumn) {
		this.currentColumn = currentColumn;
	}
	public void setPoint(int i, int j) {
		setCurrentRow(i);
		setCurrentColumn(j);
	}
	public int getPieceColor() {
		return piece.getPieceColor();
	}
	public String getPieceType() {
		return piece.getPieceType();
	}

	

}

class board{
	
	static Cell chessboard[][] = new Cell[8][8];
	boolean checkTrun = false; // false = white, true = black

	static {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				chessboard[i][j] = new Cell(new nothing(),i,j);
			}
		}
	}
	void initialize() {
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				setDefault(i,j);
			}
		}
	}
	void printBoard() {
		System.out.println("======================================================================================================================================");
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
					printOne(i,j);
			}
			System.out.println(); // 줄바꿈을 명확히 하기 위해 println 사용
		}
		System.out.println("======================================================================================================================================");
	}
	static void setDefault(int i, int j) {setPiece(new nothing(), i,j);}
	static void setPiece(chessPiece piece, int i, int j) {chessboard[i][j].setPiece(piece);}
	void printOne(int i, int j) {
        if(chessboard[i][j].getPiece() instanceof nothing) {
            System.out.printf("%-12s",chessboard[i][j].getCurrentRow()+""+chessboard[i][j].getCurrentColumn());
        }
        else{

			switch (chessboard[i][j].getPiece().getPieceColor()) {
				case 0:
					System.out.printf("%-12s",chessboard[i][j].getCurrentRow()+""+chessboard[i][j].getCurrentColumn()+" W."+chessboard[i][j].getPiece().getPieceType());
					break;
				case 1:
					System.out.printf("%-12s",chessboard[i][j].getCurrentRow()+""+chessboard[i][j].getCurrentColumn()+" B."+chessboard[i][j].getPiece().getPieceType());
					break;

			}
        }
    }
	
	boolean changeTurn() {
		checkTrun = !checkTrun;
		return checkTrun;
	}
	

	void setGame() {
		for(int i = 0; i<8; i++) {
			setPiece(new Pawn(1),1,i);
			setPiece(new Pawn(0),6,i);
		}
		setPiece(new Rook(0),7,0);
		setPiece(new Rook(0),7,7);
		setPiece(new Knight(0),7,1);
		setPiece(new Knight(0),7,6);
		setPiece(new Bishop(0),7,2);
		setPiece(new Bishop(0),7,5);
		setPiece(new Queen(0),7,3);
		setPiece(new King(0),7,4);

		setPiece(new Rook(1),0,0);
		setPiece(new Rook(1),0,7);
		setPiece(new Knight(1),0,1);
		setPiece(new Knight(1),0,6);
		setPiece(new Bishop(1),0,2);
		setPiece(new Bishop(1),0,5);
		setPiece(new Queen(1),0,3);
		setPiece(new King(1),0,4);
        
	}
	boolean MovePiece(int currentRow, int currentCol, int targetRow, int targetCol) {

        if(!chessboard[currentRow][currentCol].getPiece().rowMagic(currentRow, currentCol, targetRow, targetCol)) {
            System.out.print("rowMagic에서 false를 반환했습니다.");
            return false;
        }
		// if(chessboard[currentRow][currentCol].getPieceColor() == 1) {
        //     changeTurn();
        //     return false;
        // }
			
		if(chessboard[targetRow][targetCol].getPiece() instanceof nothing) {
			chessPiece temp = chessboard[currentRow][currentCol].getPiece();
			chessboard[currentRow][currentCol].setPiece(chessboard[targetRow][targetCol].getPiece());
			chessboard[targetRow][targetCol].setPiece(temp);
		}else {
            chessboard[targetRow][targetCol].setPiece(chessboard[currentRow][currentCol].getPiece());
            chessboard[currentRow][currentCol].setPiece(new nothing());
        }
			
		return true;
	}
}


abstract class chessPiece{
	private String pieceType;
	private int pieceColor;
	
	chessPiece(int pieceColor, String pieceType){
		this.pieceType = pieceType;
		this.pieceColor = pieceColor;
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
	
	boolean isRookMove(int currentRow, int currentCol, int targetRow, int targetCol) {
		if(currentRow==targetRow && targetCol!=currentCol)
			return true;
		else if(currentRow!=targetRow && targetCol==currentCol)
			return true;
		return false;
	}
	boolean isBishopMove(int currentRow, int currentCol, int targetRow, int targetCol) {
		return Math.abs(currentRow-targetRow) == Math.abs(targetCol-currentCol);
	}
	boolean isKnightMove(int currentRow, int currentCol, int targetRow, int targetCol) {
		int distanceRow = currentRow-targetRow;
		int distanceCol = currentCol-targetCol;
		if(Math.abs(distanceRow) == 1 &&  Math.abs(distanceCol) == 2)
			return true;
		if(Math.abs(distanceRow) == 2 &&  Math.abs(distanceCol) == 1)
			return true;
		return false;
	}
	
	boolean blockSameColor(int currentRow, int currentCol, int targetRow, int targetCol) {
		return (board.chessboard[targetRow][targetCol].getPieceColor() != getPieceColor());
	}
	abstract boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol);
}

class nothing extends chessPiece{


	nothing() {
		super(-1,"");
	}

	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
		System.out.print("잘못된 동작입니다");
		return false;
	}
	
}
class Pawn extends chessPiece{
	

	private int direction = 0; // 폰은 색깔에 따라 고정된 이동방향이 존재한다 ㅅㅂ
	private boolean firstMove = true;

	Pawn(int pieceColor) {
		super(pieceColor,"Pawn");
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

	King(int pieceColor) {
		super(pieceColor, "King");
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
class Rook extends chessPiece{

	Rook(int pieceColor) {
		super(pieceColor, "Rook");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
		if(isRookMove(currentRow,currentCol, targetRow,targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol) )
			return true;
		return false;
	}
}
class Bishop extends chessPiece{

	Bishop(int pieceColor) {
		super(pieceColor, "Bishop");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
		if(isBishopMove(currentRow,currentCol, targetRow,targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol) )
			return true;
		return false;
	}
}
class Queen extends chessPiece{
    Queen(int pieceColor) {
		super(pieceColor, "Queen");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol))
            return false;

        if(isRookMove(currentRow,currentCol, targetRow,targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol) )
            return true;
		else if(isBishopMove(currentRow,currentCol, targetRow,targetCol) && isProgressBlocked(currentRow, currentCol, targetRow, targetCol) && blockSameColor(currentRow, currentCol, targetRow, targetCol) )
			return true;
		return false;
	}
}
class Knight extends chessPiece{

	Knight(int pieceColor) {
		super(pieceColor, "Knight");
	}
	@Override
	boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
        if (!isWithinChessboard(targetRow, targetCol)) {
            return false;
        }
		if(isKnightMove(currentRow,currentCol, targetRow,targetCol)&& blockSameColor(currentRow, currentCol, targetRow, targetCol) )
			return true;
		return false;
	}
}
public class Piece {

	public static void main(String[] args) {

        board game = new board();
        game.setGame();
        Scanner s = new Scanner(System.in);
        int row1, col1, row2, col2;

        while (true) {
            game.printBoard();
            System.out.print("현재 위치와 이동할 위치를 입력하십시오 (종료하려면 999를 입력하십시오): ");
            String input = s.nextLine();
            
            if (input.equals("999")) {
                System.out.println("게임을 종료합니다.");
                s.close();
                break;
            }
            
            String[] coordinates = input.split(" ");
            if (coordinates.length != 4) {
                System.out.println("유효하지 않은 입력입니다. 네 개의 정수를 공백으로 구분하여 입력하십시오.");
                continue;
            }
            
            try {
                row1 = Integer.parseInt(coordinates[0]);
                col1 = Integer.parseInt(coordinates[1]);
                row2 = Integer.parseInt(coordinates[2]);
                col2 = Integer.parseInt(coordinates[3]);
            } catch (NumberFormatException e) {
                System.out.println("유효하지 않은 입력입니다. 유효한 정수를 입력하십시오.");
                continue;
            }
            
            if (game.MovePiece(row1, col1, row2, col2)) {
                System.out.println("이동 결과" + row1 + "," + col1 + " -> " + row2 + "," + col2);
            } else {
                System.out.println("유효하지 않은 이동입니다.");
            }
        }
        s.close();
    }
}