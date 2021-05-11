/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.AbstractMap;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

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
	Boolean gameEnded = false;
	Object P1;
	Object Dionisis;
	int PlayerScore;
	int DionisisScore;
    
    Board(JPanel getGui, int arrayLen) {
    	gui = getGui;
    	N = arrayLen;
    	PlayerScore = 0;
    	DionisisScore = 0;
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
			                					SimpleEntry<Entry<Integer, Integer>, Double> test = minimax(7, 2); //no idea
			                					dionisisMoveTo(test.getKey().getKey(),test.getKey().getValue());
			                					List<Entry<Integer, Integer>> pairList = calculateLegal(test.getKey().getKey(),test.getKey().getValue(),2);
			                					if(pairList.size() == 0) {
			                						gameEnded = true;
			                						startGame = false;
			                						System.out.println("TELIKO FINISH");
			                					}
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
			                					playerMove(i,j);
			                					List<Entry<Integer, Integer>> pairList = calculateLegal(i,j,1);
			                					if(pairList.size() == 0) {
			                						gameEnded = true;
			                						startGame = false;
			                						JOptionPane.showMessageDialog(null, "TO PAIXNIDI TELEIWSE, NIKITIS O DIONISIS");
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
    
    public void playerMove(int i, int j) {
    	if(squares[i][j].getBackground() == Color.CYAN) {
			highlightLegal(calculateLegal(i,j,1));
		}
    	
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
			if(calculateLegal(i,j,1).size() == 0) {
			//	JOptionPane.showMessageDialog(null, "Kys noob you lost by Dionisis. nmsl");
				gameEnded = true;
				startGame = false;
				return;
			}

			//dionisisMove();
			SimpleEntry<Entry<Integer, Integer>, Double> test = minimax(7, 2); //no idea
			System.out.println("EDWWW " +test.getValue());
			dionisisMoveTo(test.getKey().getKey(),test.getKey().getValue());
			List<Entry<Integer, Integer>> pairList = calculateLegal(test.getKey().getKey(),test.getKey().getValue(),2);
			if(pairList.size() == 0) {
				gameEnded = true;
				startGame = false;
				JOptionPane.showMessageDialog(null, "TO PAIXNIDI TELEIWSE, NIKISES TON DIONISI");
			}
			
		}
    }
    
    public void playerMoveTo(int i, int j) {
		for(int k =0;k<N;k++) {
			for(int l =0;l< N;l++) {
   				if(squares[k][l].getBackground() == Color.CYAN) {
    				squares[k][l].setBackground(Color.BLACK);
   				}
			}
		}
		squares[i][j].setBackground(Color.CYAN);
		//if(calculateLegal(i,j,1).size() == 0) {
			//JOptionPane.showMessageDialog(null, "Kys noob you lost by Dionisis. nmslEDWWW");
			//gameEnded = true;
			//startGame = false;
			return;
		//}
    }
    
public void dionisisMoveTo(int k, int l) {
    	
    	java.util.List<java.util.Map.Entry<Integer,Integer>> pairList;
    	
    	for(int i = 0;i < N; i++) {
			for(int j = 0;j < N; j++) {
				if(squares[i][j].getBackground() == Color.RED) {
					squares[i][j].setBackground(Color.BLACK);
					squares[i][j].setIcon(null);
					changeColor(k,l,2);
					
					pairList = calculateLegal(k,l,2);
					if(pairList.size() == 0) {
					//	JOptionPane.showMessageDialog(null, "Sygxaritiria, o dionisis eksoudeterothike");
					//	startGame = false;
					//	gameEnded = true;
						return;
					}

					return;
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
    
    
    
    
    // MINIMAX UNDER CONSTRUCTION
    
    public Double evaluate(int maximizingPlayer) { // 1 -> P1, 2 -> dionisis
    	if(maximizingPlayer == 1) {
    		return (double) (PlayerScore - DionisisScore);
    	}else {
    		return (double) (DionisisScore - PlayerScore);
    	}
    }
    
    
    public SimpleEntry<Entry<Integer, Integer>, Double> minimax(int depth, int player) {
    	int x = 0;
    	int y = 0;
    	List<Entry<Integer, Integer>> moves = null;
    	Entry<Integer,Integer> best_move = null;
    		    
    	if(depth == 0 || gameEnded) {
    		//System.out.println(evaluate(maximizingColor));
    		return new AbstractMap.SimpleEntry<java.util.Map.Entry<Integer,Integer>, Double>(null,evaluate(player));
    	}
    	
    	if(player == 2) {
    		for(int i = 0;i < N; i++) {
    			for(int j = 0;j < N; j++) {
    				if(squares[i][j].getBackground() == Color.RED) {
    					x = i;
    					y = j;
    					moves = calculateLegal(i,j,2);
    					if(moves.size() == 0) {
    						return new AbstractMap.SimpleEntry<java.util.Map.Entry<Integer,Integer>, Double>(null,evaluate(player));
    					}
    				}
    			}
    		}
    	}else {
    		for(int i = 0;i < N; i++) {
    			for(int j = 0;j < N; j++) {
    				if(squares[i][j].getBackground() == Color.CYAN) {
    					x = i;
    					y = j;
    					moves = calculateLegal(i,j,1);
    					if(moves.size() == 0) {
    						return new AbstractMap.SimpleEntry<java.util.Map.Entry<Integer,Integer>, Double>(null,evaluate(player));
    					}
    				}
    			}
    		}
    	}
    	
    	if(player == 2) { //dionisis - MAX
    		Double max_eval = Double.NEGATIVE_INFINITY;
    		for(int k = 0;k < moves.size();k++) {
    			dionisisMoveTo(moves.get(k).getKey(),moves.get(k).getValue());
    			SimpleEntry<Entry<Integer, Integer>, Double> pair = minimax(depth-1,1); // minimax go to MIN

    			//undo
    			squares[moves.get(k).getKey()][moves.get(k).getValue()].setBackground(Color.WHITE);
    			squares[moves.get(k).getKey()][moves.get(k).getValue()].setIcon(null);
    			changeColor(x,y,2);
    						
    			if(pair.getValue() > max_eval) {
    				max_eval = (double) pair.getValue();
    				best_move = moves.get(k);
    			}
   			}
    		
    		return new AbstractMap.SimpleEntry<java.util.Map.Entry<Integer,Integer>, Double>(best_move,max_eval);

    	}else { //player - MIN
    		Double min_eval = Double.POSITIVE_INFINITY;
 			for(int k = 0;k < moves.size();k++) {
    			playerMoveTo(moves.get(k).getKey(),moves.get(k).getValue());
    			SimpleEntry<Entry<Integer, Integer>, Double> pair = minimax(depth-1,2); //minimax go to MAX
    						
    			//undo
    			squares[moves.get(k).getKey()][moves.get(k).getValue()].setBackground(Color.WHITE);
    			squares[moves.get(k).getKey()][moves.get(k).getValue()].setIcon(null);
    			changeColor(x,y,1);
    						
    			if(pair.getValue() < min_eval) {
    				min_eval = (double) pair.getValue();
   					best_move = moves.get(k);
    			}
 			}
 			return new AbstractMap.SimpleEntry<java.util.Map.Entry<Integer,Integer>, Double>(best_move,min_eval);
    	}
    }
    
    
    
    
    // end minimax
    
    
    public java.util.List<java.util.Map.Entry<Integer,Integer>> calculateLegal(int i, int j, int player) {
    	
    	java.util.List<java.util.Map.Entry<Integer,Integer>> pairList= new java.util.ArrayList<>();
    	
    	if(j-1 < N && j-1 >= 0) { // aristera
    		if(squares[i][j-1].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i][j-1].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i,j-1));
    			}else if(player == 2 && squares[i][j-1].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i,j-1));
    			}
    		}
    	}
    	if(j+1 < N) { // deksia
    		if(squares[i][j+1].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i][j+1].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i,j+1));
    			}else if(player == 2 && squares[i][j+1].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i,j+1));
    			}
    		}
    	}
    	
    	if(i+1 < N) { // katw
    		if(squares[i+1][j].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i+1][j].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i+1,j));
    			}else if(player == 2 && squares[i+1][j].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i+1,j));
    			}
    		}
    	}
    	if(i+1 < N && j-1 < N && j-1 >= 0) { // katw aristera
    		if(squares[i+1][j-1].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i+1][j-1].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i+1,j-1));
    			}else if(player == 2 && squares[i+1][j-1].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i+1,j-1));
    			}
    		}
    	}
    	if(i+1 < N && j+1 < N) { // katw deksia
    		if(squares[i+1][j+1].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i+1][j+1].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i+1,j+1));
    			}else if(player == 2 && squares[i+1][j+1].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i+1,j+1));
    			}
    		}
    	}
    	if(i-1 < N && i -1 >= 0) { // panw
    		if(squares[i-1][j].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i-1][j].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i-1,j));
    			}else if(player == 2 && squares[i-1][j].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i-1,j));
    			}
    		}
    	}
    	if(i-1 < N && i-1 >= 0 && j+1 < N) { // panw deksia
    		if(squares[i-1][j+1].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i-1][j+1].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i-1,j+1));
    			}else if(player == 2 && squares[i-1][j+1].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i-1,j+1));
    			}
    		}
    	}
    	if(i-1 < N && j-1 < N && i-1 >= 0 && j-1 >= 0) { // panw aristera
    		if(squares[i-1][j-1].getBackground() != Color.BLACK) {
    			if(player == 1 && squares[i-1][j-1].getBackground() != Color.RED) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i-1,j-1));
    			}else if(player == 2 && squares[i-1][j-1].getBackground() != Color.CYAN) {
    				pairList.add(new java.util.AbstractMap.SimpleEntry<>(i-1,j-1));
    			}
    		}
    	}

    	return pairList;
    }
    
    public void highlightLegal(java.util.List<java.util.Map.Entry<Integer,Integer>> list) {
    	for(int i = 0;i<list.size();i++) {
    		changeColor(list.get(i).getKey(),list.get(i).getValue(),3);
    	}
    }
    
    public Entry<Integer, Integer> getRandomElement(java.util.List<java.util.Map.Entry<Integer,Integer>> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
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
