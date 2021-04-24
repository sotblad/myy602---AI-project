/* 
 * Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import java.util.*;

public class ask1 {
	
	static class Node {
	    public List<Integer> data; //data for storage
	    public Integer opNum;
	    public List<Node> children;//array will keep children
	    public Node parent;//parent to start the tree
	    public boolean found;
	    public Integer g;

	    public Node(List<Integer> data) {
	        children = new ArrayList<>();
	        this.found = false;
	        this.data = data;
	        this.opNum = 0;
	        this.g = 0;
	    }

	    public Node addChild(int opNum, int g, Node node) {
	        children.add(node);
	        node.parent = this;
	        node.found = false;
	        node.opNum = opNum;
	        node.g = g;
	        return this;
	    }
	    
	    public Node getParent() {
	    	return this.parent;
	    }
	    
	    public Integer getOpNum() {
	    	return this.opNum;
	    }
	    
	    public List<Node> getChildren() {
	    	return this.children;
	    }
	    
	    public List<Integer> getData() {
	    	return this.data;
	    }
	}

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
			if(sortedList.get(0) == 1) {
				for(int i = 0;i < sortedList.size()-1;i++) {
					if(sortedList.get(i+1) != sortedList.get(i) + 1) {
						if(!error)
							System.out.println("Invalid list values format passed, please retry.");
						error = true;
					}
				}
			}else {
				System.out.println("Invalid list values format passed, please retry.");
				error = true;
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
	
	public static int searchMinNodeG(List<Node> listnode) {
		int min = 10000000;
		int minIndex = -1;
		
		for(int i = 0;i < listnode.size();i++) {
			if(listnode.get(i).g < min) {
				min = listnode.get(i).g;
				minIndex = i;
			}
		}
		
		return minIndex;
	}
	
	public static List<Integer> createTree(int N, Node root) {
		if(N == 1)
			return null;
		List<Integer> Tlist = new ArrayList<Integer>();
		List<Integer> want = new ArrayList<Integer>(root.getData());
		Collections.sort(want);

		if(root.getData().equals(want)) {
			System.out.println("Initial state is sorted. Exiting");
			return Tlist;
		}
		int totalExpansions = 0;
		Node currNode = root;
		
		List<Node> nodelist = new ArrayList<Node>();
		nodelist.add(root);
		
		List<List<Integer>> lista = new ArrayList<List<Integer>>();
		int g = 0;
		while(nodelist.size() != 0) {
			currNode = nodelist.get(searchMinNodeG(nodelist)); //pernei to 1o pedi apo ti lista (pou doulevei san queue)
			
			for(int j = 1;j < N;j++) { //ftiaxnei 2 paidia sto currNode
				List<Integer> operData = operator(j+1,N,currNode.getData());
				Node createdChild = new Node(operData);
				
				if(j+1 != currNode.getOpNum() && !lista.contains(operData)) {
					currNode.addChild((j+1),currNode.g+1,createdChild); // eftiaksa 1 paidi
					nodelist.add(createdChild); // vale to paidi sto "queue"
					lista.add(operData);
					totalExpansions += 1;
					
					if(operData.equals(want)) {
						createdChild.found = true;
						
						int layer = 0;
						currNode = createdChild;
						while(currNode.getParent() != null) { // count how many layers there are
							Tlist.add(currNode.getOpNum());
							currNode = currNode.getParent();
							layer += 1;
						}
						System.out.println("Cost: " + layer);
						System.out.println("Total expansions: " + totalExpansions);
						return Tlist;
					}
				}
			}
			nodelist.remove(searchMinNodeG(nodelist));
		}
		return Tlist;
	}
	
	
	/* EDW EINAI TO ALFA ASTERAKI */
	public static int Astar(int bestCost, Node root) {
		if(root.getChildren().size() == 0)
			return -1;
		List<Node> open = new ArrayList<Node>();
		List<Node> close = new ArrayList<Node>();
		List<Integer> want = new ArrayList<Integer>(root.getData());
		Collections.sort(want);
		int g = bestCost;
		
		open.add(root);
		
		System.out.println(g);
		
		/*while(open.size() != 0) {
			Node currentNode = open.remove(0); 
			
			//for(int i = 0;i<)
			
		}*/
		

		return 0;
	}
	
	public static void main(String[] args) {
		List<Integer> initState = createInitList();
		int N = 0; //initialize N
		if(initState != null){
			N = initState.size();
		}
		
		System.out.println("~~~~~~~~~~~~~~\nInitial State: " + initState + "\nN: " + N);
		
		// ftiaxnei to dentro
		Node root = new Node(initState); // ftiaxnei thn riza me contents to array pou tou dwsame
		
		List<Integer> path = createTree(N, root);
		
		Collections.reverse(path);
		for(int i = 0;i < path.size();i++) {
			if(i != path.size()-1) {
				System.out.print("T(" + path.get(i) + "), ");
			}else {
				System.out.println("T(" + path.get(i) + ")");
			}
		}
		
		Astar(path.size(),root);
	/*	
		// CHECK OTI TO PATH EINAI SWSTO
		// kwdikas gia testing tous telestes
		while(true) {
			System.out.println("Please enter the index for the T operator (0 or less to exit): ");
			
			String Tindex = Scan.nextLine();
			if(Integer.parseInt(Tindex) <= 0) { // if <=0 exit
				System.out.println("İyi şanslar, güle güle.👋👋");
				break;
			}
			
			List<Integer> result = operator(Integer.parseInt(Tindex), N, initState);
			initState = result;
			System.out.println("Calling T(" + Tindex + "), returned b = " + result);
		}
		Scan.close();
		*/
	}
}
