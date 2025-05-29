package chess.piece;


import chess.Board;

public class nothing extends ChessPiece {

	public nothing(Board board) {
		super( board,-1,"");
	}

	@Override
    public boolean rowMagic(int currentRow, int currentCol, int targetRow, int targetCol) {
		System.out.print("잘못된 동작입니다");
		return false;
	}
}


