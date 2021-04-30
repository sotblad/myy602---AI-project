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
	    public Double h;

	    public Node(List<Integer> data) {
	        children = new ArrayList<>();
	        this.found = false;
	        this.data = data;
	        this.opNum = 0;
	        this.g = 0;
	        this.h = 0.0;
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
	    
	    public Integer getOpNum() {
	    	return this.opNum;
	    }
	    
	    public List<Node> getChildren() {
	    	return this.children;
	    }
	    
	    public List<Integer> getData() {
	    	return this.data;
	    }
	    
	    public void setOpNum(int opNum) {
	    	this.opNum = opNum;
	    }
	    
	    public void setG(int g) {
	    	this.g = g;
	    }
	    
	    public void setH(Double h) {
	    	this.h = h;
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
	
	public static Double heuristic(List<Integer> currentState, List<Integer> initState) {
		Double currStateNumber = 0.0;
		Double initStateNumber = 0.0;
		Double maxNumber = 0.0;
		Double goalNumber = 0.0;
		int prevnum = -1;
		int point = 0;
		int ace = -1;
		for (int i = 0;i <currentState.size();i++) { // turn lists to numbers. example [3,2,1] -> 321
			currStateNumber = 10*currStateNumber + currentState.get(i);
			initStateNumber = 10*initStateNumber + initState.get(i);
			goalNumber = 10*goalNumber + i+1;
			maxNumber = 10*maxNumber + currentState.size()-i;
		}
		for (int i = 0; i < currentState.size();i++) {
			if(currentState.get(i) == 1) {
				ace = i;
			}
		}
		
		for (int i = ace; i >= 0;i--) {
			if(currentState.get(i) == prevnum+1) {
				point++;
			}
			prevnum = currentState.get(i);
		}
		for (int i = ace+1; i < currentState.size();i++) {
			if(currentState.get(i) == prevnum+1) {
				point++;
			}
			
			prevnum = currentState.get(i);
		}
		
		double temp = 1.0 / (currentState.size());
		double dist = 1 - (point*temp);

		if(currentState.get(0) == 1) {
			dist = 1.0;
		}
		if(goalNumber.equals(currStateNumber)) {
			dist = 0.0;
		}
		
		dist = dist*1.5;
		return dist;
	}
	
	public static int searchMinNodeF(List<Node> listnode) {
		Double min = Double.POSITIVE_INFINITY;
		int minIndex = -1;
		
		for(int i = 0;i < listnode.size();i++) {
			if((listnode.get(i).g + listnode.get(i).h) < min) {
				min = listnode.get(i).g + listnode.get(i).h;
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
		while(nodelist.size() != 0) {
			currNode = nodelist.get(searchMinNodeG(nodelist)); //pernei to 1o pedi apo ti lista (pou doulevei san queue)
			
			for(int j = 1;j < N;j++) { //ftiaxnei 2 paidia sto currNode
				List<Integer> operData = operator(j+1,N,currNode.getData());
				Node createdChild = new Node(operData);
				
				if(j+1 != currNode.getOpNum() && !lista.contains(operData)) {
					currNode.addChild(createdChild); // eftiaksa 1 paidi
					createdChild.setOpNum(j+1);
					createdChild.setG(currNode.g+1);
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
	public static List<Integer> Astar(int N, Node root) {
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
		
//		root.setH(heuristic(root.getData(),root.getData()));
//		double distance = root.h/1.5 * root.getData().size();
//		System.out.println(distance);
		
		while(nodelist.size() != 0) {
			currNode = nodelist.get(searchMinNodeF(nodelist)); //pernei to 1o pedi apo ti lista (pou doulevei san queue)
			
			for(int j = 1;j < N;j++) { //ftiaxnei 2 paidia sto currNode
				List<Integer> operData = operator(j+1,N,currNode.getData());
				Node createdChild = new Node(operData);
				
				if(j+1 != currNode.getOpNum() && !lista.contains(operData)) {
					currNode.addChild(createdChild); // eftiaksa 1 paidi
					createdChild.setOpNum(j+1);
					createdChild.setG(currNode.g+1);
					createdChild.setH(heuristic(createdChild.getData(),root.getData()));
					
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
			nodelist.remove(searchMinNodeF(nodelist));
		}
		return Tlist;
	}
	
	public static void main(String[] args) {
		List<Integer> initState = createInitList();
		int N = 0; //initialize N
		if(initState != null){
			N = initState.size();
		}
		
		System.out.println("~~~~~~~~~~~~~~\nInitial State: " + initState + "\nN: " + N);
		
		System.out.println("~~~~~~~~~~~~~~~~~~UCS~~~~~~~~~~~~~~~~~~~");
		List<Integer> UCS = createTree(N, new Node(initState));
		
		Collections.reverse(UCS);
		for(int i = 0;i < UCS.size();i++) {
			if(i != UCS.size()-1) {
				System.out.print("T(" + UCS.get(i) + "), ");
			}else {
				System.out.println("T(" + UCS.get(i) + ")");
			}
		}
		
		System.out.println("~~~~~~~~~~~~~~~~~A-Star~~~~~~~~~~~~~~~~~");
		
		List<Integer> ASTAR = Astar(N,new Node(initState));
		Collections.reverse(ASTAR);
		for(int i = 0;i < ASTAR.size();i++) {
			if(i != ASTAR.size()-1) {
				System.out.print("T(" + ASTAR.get(i) + "), ");
			}else {
				System.out.println("T(" + ASTAR.get(i) + ")");
			}
		}

		

		// CHECK OTI TO PATH EINAI SWSTO
		// kwdikas gia testing tous telestes
	/*	while(true) {
			System.out.println("Please enter the index for the T operator (0 or less to exit): ");
			
			String Tindex = Scan.nextLine();
			if(Integer.parseInt(Tindex) <= 0) { // if <=0 exit
				System.out.println("-");
				break;
			}
			
			List<Integer> result = operator(Integer.parseInt(Tindex), N, initState);
			initState = result;
			System.out.println("Calling T(" + Tindex + "), returned b = " + result);
		}
		Scan.close();*/
		
	}
}
