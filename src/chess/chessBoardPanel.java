package chess;

import chess.piece.ChessPiece;
import chess.piece.nothing;

import javax.swing.*;

public class chessBoardPanel extends JButton {
    final private int row;
    final private int col;

    JButton state = new JButton();

    chessBoardPanel(int i, int j) {
        row = i;
        col = j;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public void setState(ChessPiece piece) {
        if (piece instanceof nothing) {
            setIcon(null);
        } else {
            String color = piece.getPieceColor() == 0 ? "White" : "Black";
            String path = "src\\chess\\image\\" + color + "_" + piece.getPieceType() + ".png";
            setIcon(new ImageIcon(path));
        }
    }
}
