package chess;

import java.awt.*;
//import java.awt.event.ActionListener;
import java.awt.event.ActionListener;

import javax.swing.*;


// class ImageLoader {
//     public static ImageIcon loadImage(String path) {
//         return new ImageIcon(path);
//     }
// }

public class ChessGUI extends JFrame {
    final private GameController controller;
    int[] row = {-1,-1};
    int[] col = {-1,-1};
    int count = 0;

    chessBoardPanel[][] BoardGUI = new chessBoardPanel[8][8];
    ChessGUI(GameController controller) {
        this.setSize(800, 800); // 전체 크기를 800*800으로 변경
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        buildGUI();
        this.setVisible(true);
        this.controller = controller;
    }

    void buildGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(setChessBoard(), BorderLayout.CENTER);
        //mainPanel.add(setSidePanel(), BorderLayout.EAST);
        this.add(mainPanel);

    }

    JPanel setChessBoard() {
        JPanel chessBoard = new JPanel();
        chessBoard.setLayout(new GridLayout(8, 8));

        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                BoardGUI[i][j] = new chessBoardPanel(i, j);
                if ((i + j) % 2 == 0) {
                    BoardGUI[i][j].setBackground(Color.WHITE);
                } else {
                    BoardGUI[i][j].setBackground(new Color(100, 100, 100));
                }
                BoardGUI[i][j].addActionListener(new ActionListener() {

                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        chessBoardPanel panel = (chessBoardPanel) e.getSource();
                        // 버튼 클릭시 보드로 데이터 전송
                        controller.selectCell(panel.getRow(),panel.getCol());

//                        if (count % 2 == 0) {
//                            if (!(controller.gameboard.chessboard[panel.getRow()][panel.getCol()].getPiece() instanceof nothing.java)) {
//                                row[0] = panel.getRow();
//                                col[0] = panel.getCol();
//                                count++;
//                            }else{
//                                row[0] = -1;
//                                col[0] = -1;
//                                row[1] = -1;
//                                col[1] = -1;
//                            }
//                        } else {
//                            row[1] = panel.getRow();
//                            col[1] = panel.getCol();
//                            count++;
//                        }
//                        System.out.println("\nrow1: " + row[0] + " col1: " + col[0] + " row2: " + row[1] + " col2: " + col[1]);
                    }
                });
                chessBoard.add(BoardGUI[i][j]);
            }
        }
        return chessBoard;



    }
//    JPanel setSidePanel() {
//        JPanel sidePanel = new JPanel();
//        sidePanel.setPreferredSize(new Dimension(200, 800));
//        sidePanel.setBackground(Color.GRAY);
//        sidePanel.setLayout(new BorderLayout());
//
//        JTextArea messageArea = new JTextArea();
//        messageArea.setEditable(false);
//        sidePanel.add(new JScrollPane(messageArea), BorderLayout.CENTER);
//
//        JPanel buttonPanel = new JPanel();
//        buttonPanel.setLayout(new GridLayout(1, 2));
//        JButton button1 = new JButton("Button 1");
//        JButton button2 = new JButton("Button 2");
//        buttonPanel.add(button1);
//        buttonPanel.add(button2);
//
//        sidePanel.add(buttonPanel, BorderLayout.SOUTH);
//
//        return sidePanel;
//    }

}


