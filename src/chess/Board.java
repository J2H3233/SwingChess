package chess;

import chess.piece.*;
import java.util.Scanner;

public class Board {

    public Cell[][] chessboard = new Cell[8][8];
    boolean checkTurn = false; // false = white, true = black

    // 보드를 초기화하여 모든 칸에 nothing 기물 배치
    void initialize() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                setDefault(i, j);
            }
        }
    }

    // 체스판 출력
    void printBoard() {
        System.out.println("======================================================================================================================================");
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                printOne(i, j);
            }
            System.out.println();
        }
        System.out.println("======================================================================================================================================");
    }

    // 한 칸 기본값 설정
    void setDefault(int i, int j) {
        chessboard[i][j] = new Cell(new nothing(this), i, j);
    }

    // 특정 위치에 기물 설정
    void setPiece(ChessPiece piece, int i, int j) {
        chessboard[i][j].setPiece(piece);
    }

    // 한 칸 출력 포맷
    void printOne(int i, int j) {
        if (chessboard[i][j].getPiece() instanceof nothing) {
            System.out.printf("%-12s", chessboard[i][j].getCurrentRow() + "" + chessboard[i][j].getCurrentColumn());
        } else {
            switch (chessboard[i][j].getPiece().getPieceColor()) {
                case 0:
                    System.out.printf("%-12s", chessboard[i][j].getCurrentRow() + "" + chessboard[i][j].getCurrentColumn() + " W." + chessboard[i][j].getPiece().getPieceType());
                    break;
                case 1:
                    System.out.printf("%-12s", chessboard[i][j].getCurrentRow() + "" + chessboard[i][j].getCurrentColumn() + " B." + chessboard[i][j].getPiece().getPieceType());
                    break;
            }
        }
    }

    // 턴 변경
    void changeTurn() {
        checkTurn = !checkTurn;
    }

    // 기물 배치: 폰과 주요 기물 초기 위치 설정
    void setGame() {
        // 폰 배치
        for (int i = 0; i < 8; i++) {
            setPiece(new Pawn(this, 1), 1, i);
            setPiece(new Pawn(this, 0), 6, i);
        }
        // 화이트 주력 기물
        setPiece(new Rook(this, 0), 7, 0);
        setPiece(new Rook(this, 0), 7, 7);
        setPiece(new Knight(this, 0), 7, 1);
        setPiece(new Knight(this, 0), 7, 6);
        setPiece(new Bishop(this, 0), 7, 2);
        setPiece(new Bishop(this, 0), 7, 5);
        setPiece(new Queen(this, 0), 7, 3);
        setPiece(new King(this, 0), 7, 4);
        // 블랙 주력 기물
        setPiece(new Rook(this, 1), 0, 0);
        setPiece(new Rook(this, 1), 0, 7);
        setPiece(new Knight(this, 1), 0, 1);
        setPiece(new Knight(this, 1), 0, 6);
        setPiece(new Bishop(this, 1), 0, 2);
        setPiece(new Bishop(this, 1), 0, 5);
        setPiece(new Queen(this, 1), 0, 3);
        setPiece(new King(this, 1), 0, 4);
    }

    // 킹이 체크 상태인지 확인
    boolean isKingInCheck(int kingRow, int kingCol, int attackerColor) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                ChessPiece piece = chessboard[i][j].getPiece();
                if (piece instanceof nothing) continue;
                if (piece.getPieceColor() == attackerColor) {
                    if (piece instanceof Pawn) {
                        if (((Pawn) piece).pawnAttack(i, j, kingRow, kingCol)) {
                            return true;
                        }
                    } else {
                        if (piece.rowMagic(i, j, kingRow, kingCol)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // 유효한 좌표인지 확인
    boolean isValidPosition(int row, int col) {
        return row >= 0 && row < 8 && col >= 0 && col < 8;
    }

    // 기물 이동 처리 (체크, 체크 메이트, 승진 포함)
    public boolean MovePiece(int currentRow, int currentCol, int targetRow, int targetCol) {
        ChessPiece temp = null;

        // 1) 본인 턴인지 확인
        if (chessboard[currentRow][currentCol].getPieceColor() != (checkTurn ? 1 : 0)) {
            return false;
        }
        // 2) 이동 가능 여부 확인
        if (!chessboard[currentRow][currentCol].getPiece().rowMagic(currentRow, currentCol, targetRow, targetCol)) {
            return false;
        }
        // 3) 실제 이동
        if (chessboard[targetRow][targetCol].getPiece() instanceof nothing) {
            temp = chessboard[currentRow][currentCol].getPiece();
            chessboard[currentRow][currentCol].setPiece(chessboard[targetRow][targetCol].getPiece());
            chessboard[targetRow][targetCol].setPiece(temp);
        } else {
            chessboard[targetRow][targetCol].setPiece(chessboard[currentRow][currentCol].getPiece());
            chessboard[currentRow][currentCol].setPiece(new nothing(this));
        }
        // 4) 폰 승진 처리
        ChessPiece moved = chessboard[targetRow][targetCol].getPiece();
        if (moved instanceof Pawn) {
            Pawn pawn = (Pawn) moved;
            int color = pawn.getPieceColor();
            if ((color == 0 && targetRow == 0) || (color == 1 && targetRow == 7)) {
                System.out.print("Pawn promotion! Choose (Q)ueen, (R)ook, (B)ishop or k(N)ight: ");
                Scanner sc = new Scanner(System.in);
                char choice = sc.next().toUpperCase().charAt(0);
                ChessPiece promoted;
                switch (choice) {
                    case 'R': promoted = new Rook(this, color);    break;
                    case 'B': promoted = new Bishop(this, color);  break;
                    case 'N': promoted = new Knight(this, color);  break;
                    case 'Q':
                    default:  promoted = new Queen(this, color);   break;
                }
                setPiece(promoted, targetRow, targetCol);
                System.out.println("Pawn promoted to " + promoted.getPieceType() + "!");
            }
        }
        // 5) 내 킹이 체크인지 확인 후 되돌리기
        int myKingRow = -1, myKingCol = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j].getPiece() instanceof King
                        && chessboard[i][j].getPieceColor() == (checkTurn ? 1 : 0)) {
                    myKingRow = i;
                    myKingCol = j;
                }
            }
        }
        if (isKingInCheck(myKingRow, myKingCol, (checkTurn ? 0 : 1))) {
            // 이동 전 상태로 되돌리기
            if (chessboard[currentRow][currentCol].getPiece() instanceof nothing) {
                temp = chessboard[targetRow][targetCol].getPiece();
                chessboard[targetRow][targetCol].setPiece(chessboard[currentRow][currentCol].getPiece());
                chessboard[currentRow][currentCol].setPiece(temp);
            } else {
                chessboard[currentRow][currentCol].setPiece(chessboard[targetRow][targetCol].getPiece());
                chessboard[targetRow][targetCol].setPiece(new nothing(this));
            }
            System.out.println("내 킹이 체크 입니다.");
            return true;
        }
        // 6) 턴 변경
        changeTurn();
        // 7) 상대 킹이 체크인지 확인
        int yourKingRow = -1, yourKingCol = -1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (chessboard[i][j].getPiece() instanceof King
                        && chessboard[i][j].getPieceColor() == (checkTurn ? 1 : 0)) {
                    yourKingRow = i;
                    yourKingCol = j;
                }
            }
        }
        if (isKingInCheck(yourKingRow, yourKingCol, (checkTurn ? 0 : 1))) {
            System.out.println("상대 킹이 체크 입니다.");
        }
        return true;
    }
}
