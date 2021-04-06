/* Dimitrios Giannakopoulos, 4336
 * Eustratios Myritzis, 4444
 * Sotirios Panagiotou, 4456 
*/

import java.util.*;
import java.util.Scanner;

public class ask1 {

	public static List<Integer> createInitList() {
		boolean invalid = true;
		boolean error;
		List<Integer> initState = null;
		Scanner arrayContentsInput = new Scanner(System.in);
		
		while(invalid) {
			error = false;
			System.out.println("Enter the initial contents of the array:");
			
			String arrInput = arrayContentsInput.nextLine();
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
			
			if(error) {
				invalid = true;
			}else {
				invalid = false;
			}
		}
		arrayContentsInput.close();
		
		return initState;
	}
	
	public static void main(String[] args) {
		List<Integer> initState = createInitList();
		int N = 0; //initialize N
		if(initState != null){
			N = initState.size();
		}
		
		System.out.println("~~~~~~~~~~~~~~\nInitial State: " + initState + "\nN: " + N);
	}
}
