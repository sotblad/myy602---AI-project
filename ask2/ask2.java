/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import javax.swing.*;

public class ask2 {

    public static void main(String[] args) {
    	int N = 5;
    	Board board = new Board(new JPanel(), N);

    	JFrame f = new JFrame("Board");
        f.add(board.getGui());
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	    f.setLocationByPlatform(true);

        f.pack();

        f.setMinimumSize(f.getSize());
        f.setVisible(true);
        
    }
}
