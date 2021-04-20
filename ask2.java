/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import java.awt.*;
import javax.swing.*;

public class ask2 {
	private JPanel gui;
    
    ask2(JPanel gui, int N) {
    	this.gui = gui;
        initializeGui(gui, N);
    }

    public final void initializeGui(JPanel gui, int N) {
    	JButton[][] boardSquares = new JButton[N][N];
        JPanel board;

        board = new JPanel(new GridLayout(0, N+1));
        gui.add(board);

        for (int i = 0; i < boardSquares.length; i++) {
            for (int j = 0; j < boardSquares[i].length; j++) {
                JButton b = new JButton();
                b.setPreferredSize(new Dimension(100, 100));
                b.setBackground(Color.black);
                //b.setOpaque(true);
             //   b.setBorderPainted(false);
                boardSquares[j][i] = b;
            }
        }

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
            	board.add(boardSquares[j][i]);
            }
            board.add(new JLabel());
        }
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
    	JPanel gui = new JPanel();
    	ask2 cb = new ask2(gui, 5);

    	JFrame f = new JFrame("Board");
        f.add(cb.getGui());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    f.setLocationByPlatform(true);

        f.pack();

        f.setMinimumSize(f.getSize());
        f.setVisible(true);
        
        
    }
}
