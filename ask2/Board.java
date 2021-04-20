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
	private JPanel board;
    
    Board(JPanel gui, int N, Integer[][] blackPreset) {
    	this.gui = gui;
    	board = new JPanel(new GridLayout(0, N));
        initializeGui(gui, N, blackPreset);
    }

    public final void initializeGui(JPanel gui, int N, Integer[][] blackPreset) {
        gui.add(board);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
            	JButton b = new JButton();
                b.setPreferredSize(new Dimension(100, 100));
                b.setForeground(Color.BLACK);
                if(blackPreset[i][j] == 1) {
                	b.setBackground(Color.BLACK);
                }else {
                	b.setBackground(Color.WHITE);
                }
                b.setBorderPainted(true);
                b.setBorder(new LineBorder(Color.BLACK));
                b.setOpaque(true);
                board.add(b);
            }
        }
    }

    public final JComponent getGui() {
        return gui;
    }
}
