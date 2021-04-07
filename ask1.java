/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import java.util.*;

public class ask1 {
	
	static class Node {
	    public List<Integer> data; //data for storage
	    public List<Node> children;//array will keep children
	    public Node parent;//parent to start the tree
	    public boolean found;

	    public Node(List<Integer> data) {
	        children = new ArrayList<>();
	        this.found = false;
	        this.data = data;
	    }

	    public Node addChild(Node node) {
	        children.add(node);
	        node.parent = this;
	        node.found = false;
	        return this;
	    }
	    
	    public Node getParent() {
	    	return this.parent;
	    }
	    
	    public List<Node> getChildren() {
	    	return this.children;
	    }
	    
	    public List<Integer> getData() {
	    	return this.data;
	    }
	}
	
	 Node root;

	static Scanner Scan = new Scanner(System.in);
	
	public static List<Integer> createInitList() {
		boolean invalid = true;
		boolean error;
		List<Integer> initState = null;
		
		while(invalid) {
			error = false;
			System.out.println("Enter the initial contents of the array (ex: 3,5,4,1,2):");
			
			String arrInput = Scan.nextLine();
			String[] parts = arrInput.split(",");
		
			initState = new ArrayList<Integer>();
			for(int i = 0;i < parts.length;i++) {
				try {
					int item = Integer.parseInt(parts[i]);
					if(item % 1 == 0) {
						initState.add(item);
					}
				}catch(NumberFormatException ex){
					System.out.println("Invalid list values passed, please retry.");
					error = true;
				}
			}
			
			//Check integrity
			List<Integer> sortedList = new ArrayList<Integer>(initState);
			Collections.sort(sortedList);
			
			for(int i = 0;i < sortedList.size()-1;i++) {
				if(sortedList.get(i+1) != sortedList.get(i) + 1) {
					if(!error)
						System.out.println("Invalid list values format passed, please retry.");
					error = true;
				}
			}
			
			invalid = false;
			if(error) {
				invalid = true;
			}
		}
		
		return initState;
	}
	
	public static List<Integer> operator(int k, int N, List<Integer> inputList){
		if(k > inputList.size()) {
			System.out.println("Operator input provided out of range"); 
			return null;
		}
		List<Integer> AL;
		List<Integer> AR;
		
		if(k != N) {
			AL = new ArrayList<Integer>(inputList.subList(0, k));
			AR = new ArrayList<Integer>(inputList.subList(k, inputList.size()));
		}else {
			AL = new ArrayList<Integer>(inputList);
			AR = new ArrayList<Integer>();
		}
		
		Collections.reverse(AL); // AL becomes rAL
		
		AL.addAll(AR); // rAL becomes b
		
		return AL; // return b
	}
	
	public static Node createTree(int N, Node root) {
		List<Integer> want = new ArrayList<Integer>(root.getData());
		Collections.sort(want);
		if(N == 1)
			return null;
		
		Node currNode = root;
		int layer = 0;
		int count = 0;
		while(layer < 10) {
			for(int j = 0;j < N+1;j++) { // layers
				for(int i = 0;i < N;i++) {
					List<Integer> operData = operator(i+1,N,currNode.getData());
					currNode.addChild(new Node(operData));
					if(operData.equals(want)) {
						currNode.found = true;
						System.out.println(layer + " " + j + " " + i);
					}
				}
				if(j != 0)
					currNode = currNode.parent;
				if(j <= N-1)
					currNode = currNode.getChildren().get(j);
			}
			if(count == N)
				count = 0;
			currNode = currNode.getChildren().get(count);
			count += 1;
			layer += 1;
		}
		
		currNode = root;
		
		return currNode;
	}
	
	public static void main(String[] args) {
		List<Integer> initState = createInitList();
		int N = 0; //initialize N
		if(initState != null){
			N = initState.size();
		}
		
		System.out.println("~~~~~~~~~~~~~~\nInitial State: " + initState + "\nN: " + N);
		
		
		while(true) {
			System.out.println("Please enter the index for the T operator (0 or less to exit): ");
			
			String Tindex = Scan.nextLine();
			if(Integer.parseInt(Tindex) <= 0) { // if <=0 exit
				System.out.println("İyi şanslar, güle güle.👋👋");
				break;
			}
			
			System.out.println("Calling T(" + Tindex + "), returned b = " + operator(Integer.parseInt(Tindex), N, initState));
		}
		Scan.close();
		
		//Test tree
		Node root = new Node(initState);
		Node test = createTree(N, root);
		if(test!= null)
			System.out.println(test.getChildren().size());
	}
}
