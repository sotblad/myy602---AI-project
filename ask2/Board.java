/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Board {
	private JPanel gui;
	private JPanel board;
	JButton[][] squares;
	Boolean blackOut = true;
	Boolean setPlayerStart1 = false;
	Boolean setPlayerStart2 = false;
    
    Board(JPanel gui, int N) {
    	this.gui = gui;
    	squares =new JButton[N][N];
    	board = new JPanel(new GridLayout(0, N));
        initializeGui(gui, N);
    }

    public final void initializeGui(JPanel gui, int N) {
        gui.add(board);

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
            	JButton b = new JButton();
                b.setPreferredSize(new Dimension(100, 100));
                b.setForeground(Color.BLACK);
                b.setBackground(Color.WHITE);
                b.setBorderPainted(true);
                b.setBorder(new LineBorder(Color.BLACK));
                b.setOpaque(true);
                
                squares[i][j]= b;
                board.add(squares[i][j]);
                
                squares[i][j].addActionListener(
                	new ActionListener(){
	                	public void actionPerformed(ActionEvent e) {
	                		if(blackOut) {
		                		Object source = e.getSource();
		                		
		                		for(int i = 0;i < N; i++) {
		                			for(int j = 0;j < N; j++) {
		                				if(source == squares[i][j]) {
		                					changeColor(i,j,0);
		                					return;
		                				}
		                			}
		                		}
	                		}else if(setPlayerStart1) {
								Object source = e.getSource();
		                		for(int i = 0;i < N; i++) {
		                			for(int j = 0;j < N; j++) {
		                				if(source == squares[i][j]) {
		                					if(squares[i][j].getBackground() != Color.BLACK) {
			                					changeColor(i,j,1);
			                					setPlayerStart1 = false;
			                					setPlayerStart2 = true; // set Player2 start point
			                					return;
		                					}
		                				}
		                			}
		                		}
	                		}else if(setPlayerStart2) {
								Object source = e.getSource();
		                		for(int i = 0;i < N; i++) {
		                			for(int j = 0;j < N; j++) {
		                				if(source == squares[i][j]) {
		                					if(squares[i][j].getBackground() != Color.BLACK) {
			                					changeColor(i,j,2);
			                					setPlayerStart2 = false; // finish placings
			                					return;
		                					}
		                				}
		                			}
		                		}
	                		}
	                	}
                	});
            }
        }
    }
    public void blackOut(){
    	JOptionPane.showMessageDialog(null, "Please select which boxes you want initially blacked out.\nPress ESC when finished.");
    	KeyStroke stroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    	 
    	ActionListener action = new ActionListener() {
    	    public void actionPerformed(ActionEvent e) {
    	        blackOut = false;
    	    }
    	};
    	
    	board.registerKeyboardAction(action, stroke, JComponent.WHEN_IN_FOCUSED_WINDOW);
    	while(blackOut == true) {
    		System.out.print(""); // makes the wait volatile
    		if(blackOut == false) {  // blackOut ended, continue
    			break;
    		}
    		continue;
    	}
    	setPlayerStart1 = true; // set Player1 start point
    }
    
    public final JComponent getGui() {
        return gui;
    }
    
    public void changeColor(int i, int j, int color) {
    	if(color == 0) {
    	squares[i][j].setBackground(Color.BLACK);
    	}else if(color == 1) {
    		squares[i][j].setBackground(Color.CYAN);
    	}else if(color == 2) {
    		squares[i][j].setBackground(Color.RED);
    	}
    }
}
