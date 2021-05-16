/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

/*ypothetoume oti oi kinhseis pou mporei na kanei o paikths kai o ypologisths einai 1 tetragwnaki thn fora pros oles tis kateuthinseis.
Epishs gia na emfanistoun oi epitreptes kinhseis prepei na patithei to kyano koutaki ki epeita gia na ginei h kinhsh prepei na patithei kapoio gkri koutaki*/

import javax.swing.*;

public class ask2 {
	private static Integer[][] blackPreset;
	
	public static Integer[][] setBlackPreset(int N, Integer[][] blackPreset) {
		blackPreset = new Integer[N][N];
		for (int i = 0; i < N; i++) {
			for (int j = 0; j < N; j++) {
				blackPreset[i][j] = 0;
			}
		}
		blackPreset[1][1] = 1;
		blackPreset[2][1] = 1;
		blackPreset[2][2] = 1;
		return blackPreset;
	}
	
    public static void main(String[] args) {
    	
    	int N = 5;
    	
    	blackPreset = setBlackPreset(N, blackPreset);
    	
    	Board board = new Board(new JPanel(), N);

    	JFrame f = new JFrame("Board");
        f.add(board.getGui());
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    f.setLocationByPlatform(true);

        f.pack();
        f.setMinimumSize(f.getSize());
        f.setVisible(true);
        
        board.blackOut(); // blackOut squares
        
    }
}
