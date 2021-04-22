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
	int N;
	Boolean blackOut = true;
	Boolean setPlayerStart1 = false;
	Boolean setPlayerStart2 = false;
	Boolean startGame = false;
	Boolean displayMoves = false;
	Boolean P1makeMove = false;
	Object P1;
	Object Dionisis;
    
    Board(JPanel getGui, int arrayLen) {
    	gui = getGui;
    	N = arrayLen;
    	squares =new JButton[N][N];
    	board = new JPanel(new GridLayout(0, N));
        initializeGui();
    }

    public final void initializeGui() {
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
			                					P1 = squares[i][j];
			                					setPlayerStart1 = false;
			                					setPlayerStart2 = true; // set Player2 start point
			                					JOptionPane.showMessageDialog(null, "Set the starting point of Player2");
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
		                					if(squares[i][j].getBackground() != Color.BLACK && squares[i][j].getBackground() != Color.CYAN) {
			                					changeColor(i,j,2);
			                					setPlayerStart2 = false; // finish placings
			                					startGame = true;
			                					displayMoves = true;
			                					JOptionPane.showMessageDialog(null, "game starts");
			                					dionisisMove();
			                					return;
		                					}
		                				}
		                			}
		                		}
	                		}else if(startGame) {
								Object source = e.getSource();
		                		for(int i = 0;i < N; i++) {
		                			for(int j = 0;j < N; j++) {
		                				if(source == squares[i][j]) {
		                					if(displayMoves) {
			                					if(squares[i][j].getBackground() == Color.CYAN) {
			                						highlightLegal(i,j);
			                						P1makeMove = true;
			                						displayMoves = false;
				                					return;
			                					}
			                				}else if(P1makeMove) {
			                					if(squares[i][j].getBackground() == Color.GRAY) {
			                						for(int k =0;k<N;k++) {
			                							for(int l =0;l< N;l++) {
			        		                				if(squares[k][l].getBackground() == Color.GRAY) {
			        		                					squares[k][l].setBackground(Color.WHITE);
			        		                				}else if(squares[k][l].getBackground() == Color.CYAN) {
			        		                					squares[k][l].setBackground(Color.BLACK);
			        		                				}
			                							}
			                						}
			                						squares[i][j].setBackground(Color.CYAN);
			                						displayMoves = true;
			                						P1makeMove = false;
			                						dionisisMove();
			                					}
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
    
    public void dionisisMove() {
    	for(int i = 0;i < N; i++) {
			for(int j = 0;j < N; j++) {
				if(squares[i][j].getBackground() == Color.RED) {
					JOptionPane.showMessageDialog(null, "eimai o dionisis kai vriskomai sto " + i + " " + j);
				}
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
    	JOptionPane.showMessageDialog(null, "Set the starting point of Player1");
    }
    
    public final JComponent getGui() {
        return gui;
    }
    
    public void highlightLegal(int i, int j) {
    	int freeBlocks = 0;
    	
    	if(j-1 < N && j-1 >= 0) { // aristera
    		if(squares[i][j-1].getBackground() != Color.BLACK && squares[i][j-1].getBackground() != Color.RED) {
    			changeColor(i,j-1,3);
    			freeBlocks += 1;
    		}
    	}
    	if(j+1 < N) { // deksia
    		if(squares[i][j+1].getBackground() != Color.BLACK && squares[i][j+1].getBackground() != Color.RED) {
    			changeColor(i,j+1,3);
    			freeBlocks += 1;
    		}
    	}
    	
    	if(i+1 < N) { // katw
    		if(squares[i+1][j].getBackground() != Color.BLACK && squares[i+1][j].getBackground() != Color.RED) {
    			changeColor(i+1,j,3);
    			freeBlocks += 1;
    		}
    	}
    	if(i+1 < N && j-1 < N && j-1 >= 0) { // katw aristera
    		if(squares[i+1][j-1].getBackground() != Color.BLACK && squares[i+1][j-1].getBackground() != Color.RED) {
    			changeColor(i+1,j-1,3);
    			freeBlocks += 1;
    		}
    	}
    	if(i+1 < N && j+1 < N) { // katw deksia
    		if(squares[i+1][j+1].getBackground() != Color.BLACK && squares[i+1][j+1].getBackground() != Color.RED) {
    			changeColor(i+1,j+1,3);
    			freeBlocks += 1;
    		}
    	}
    	if(i-1 < N && i -1 >= 0) { // panw
    		if(squares[i-1][j].getBackground() != Color.BLACK && squares[i-1][j].getBackground() != Color.RED) {
    			changeColor(i-1,j,3);
    			freeBlocks += 1;
    		}
    	}
    	if(i-1 < N && i-1 >= 0 && j+1 < N) { // panw deksia
    		if(squares[i-1][j+1].getBackground() != Color.BLACK && squares[i-1][j+1].getBackground() != Color.RED) {
    			changeColor(i-1,j+1,3);
    			freeBlocks += 1;
    		}
    	}
    	if(i-1 < N && j-1 < N && i-1 >= 0 && j-1 >= 0) { // panw aristera
    		if(squares[i-1][j-1].getBackground() != Color.BLACK && squares[i-1][j-1].getBackground() != Color.RED) {
    			changeColor(i-1,j-1,3);
    			freeBlocks += 1;
    		}
    	}
    	if(freeBlocks == 0) {
    		JOptionPane.showMessageDialog(null, "Kys noob you lost by Dionisis. nmsl");
    	}
    	
    }
    
    public void changeColor(int i, int j, int color) {
    	if(color == 0) {
    	squares[i][j].setBackground(Color.BLACK);
    	}else if(color == 1) {
    		squares[i][j].setBackground(Color.CYAN);
    	}else if(color == 2) {
    		squares[i][j].setBackground(Color.RED);
    		squares[i][j].setIcon( new ImageIcon("/Users/sotirisp/Desktop/niar.jpg") );
    	}else if(color == 3) {
    		squares[i][j].setBackground(Color.GRAY);
    		
    	}
    	
    }
}
