/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Board {
	private JPanel gui;
	private JButton[][] boardSquares;
	JPanel board;
    
    Board(JPanel gui, int N) {
    	this.gui = gui;
        initializeGui(gui, N);
    }

    public final void initializeGui(JPanel gui, int N) {
    	boardSquares = new JButton[N][N];
      
        board = new JPanel(new GridLayout(0, N+1));
        gui.add(board);

        for (int i = 0; i < boardSquares.length; i++) {
            for (int j = 0; j < boardSquares[i].length; j++) {
            	JButton b = new JButton();
                b.setPreferredSize(new Dimension(100, 100));
                b.setForeground(Color.BLACK);
                b.setBackground(Color.WHITE);
                b.setBorderPainted(true);
                b.setBorder(new LineBorder(Color.BLACK));
                b.setOpaque(true);
                board.add(b);
            }
            board.add(new JLabel());
        }
    }
    
    public final JButton[][] getBoardSquares() {
        return boardSquares;
    }

    public final JComponent getGui() {
        return gui;
    }
}
