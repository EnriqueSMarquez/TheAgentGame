//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
///////THIS CLASS IS IN CHARGED OF DOING THE BF SEARCH//////
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////
package MainPackage;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BreadthFirstSearch {
	//INITIALIZE VARIABLES
	int incrementer1 = 0;
	int incrementer2 = 0;
	int nextToLookInTree = 0;
	List<int[]> vectorToKnowParent = new ArrayList<int[]>();
	List<Integer> numberOfNodes = new LinkedList<Integer>();
	IntelligentMovementClass movementMethods = new IntelligentMovementClass(0);
	List<int[][]> initialList = new ArrayList<int[][]>();
	List<List<int[][]>> treeSearch = new ArrayList<List<int[][]>>();
	int carrier;
	int lastDepthGoalLocation = 0;
	public List<Point> fromWhoICame = new ArrayList<Point>();	//X=From who I came, Y= Who I am
	
	Integer[][] initialMatrixObject = new Integer[4][4];
	boolean solution = false;
	List<int[][]> finalSolution = new ArrayList<int[][]>();
	
	//THIS METHOD IS THE MAIN METHOD, IT HAS THE MATRIX TO FIND THE SOLUTION AS INPUT AND RETURNS
	//AN ARRAY OF MATRIXES WITH THE SOLUTION
	public List<int[][]> BreadthFirstSearchMethod(int[][] initialMatrix) {
		long startTime = System.nanoTime(); //INITIAL TIME
		File file = new File("BDFLog.txt");	//FILE TO WRITE
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
		fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		bw.write("#STARTING FILE \n #TIME (NODES IN DEPTH) TREE");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	initialList.add(initialMatrix);	//ADD INTIAL MATRIX TO LIST
	treeSearch.add(initialList);	//ADD INITIAL LIST TO ANOTHER LIST (EACH LIST REPRESENTS A DEPTH, DEPTH CERO ONLY HAS THE FIRST MATRIX)

	
	int carrier = 0;
	
	while(!solution){		//WHILE SOLUTIO HAS NOT BEEN FOUND
		
		treeSearch.add(GetPossibleMatrixes(treeSearch.get(carrier)));	//ADD NEW DEPTH
		solution = CheckSolution(treeSearch.get(treeSearch.size() - 1));		//CHECK IF IN THAT LEVEL HAS THE SOLUTION
		carrier ++;
		System.out.println((System.nanoTime() - startTime)/1000 + " " + treeSearch.get(treeSearch.size()-1).size() + " " + (treeSearch.size()-1));
		try {
			bw.write((System.nanoTime() - startTime)/1000 + " " + treeSearch.get(treeSearch.size()-1).size() + " " + (treeSearch.size()-1) + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	numberOfNodes.addAll(movementMethods.getNumberOfNodes()); //GET ALL THE NODES IN A SINGLE ARRAY
	System.out.println("OPTIMAL SOLUTION FOUND");
	try {
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	finalSolution.add(treeSearch.get(treeSearch.size()-1).get(lastDepthGoalLocation)); //ADD SOLUTION MATRIX TO SOLUTION ARRAY
	do{
		incrementer1 = 0;
		incrementer2 = 0;
		
		treeSearch.remove(treeSearch.size()-1); //REMOVE LAST DEPTH SINCE THE NODE OF THIS DEEP HAS ALREADY BEEN SAVED
		for(int i = 0;i < treeSearch.size();i++){ 	//LOOP THROUGHT THE TREE
			incrementer1 += treeSearch.get(i).size();
		}
		for(int i = 0;i < treeSearch.size() - 1;i++){
			incrementer2 += treeSearch.get(i).size();	//LOOP THROUGHT TWO DEEPS BEHIND
		}
		
		incrementer1 += lastDepthGoalLocation; 
		lastDepthGoalLocation = numberOfNodes.get(incrementer1);
		 lastDepthGoalLocation = lastDepthGoalLocation - incrementer2 - 2; //GET THE PARENT OF THE SOLUTION
		
		finalSolution.add(treeSearch.get(treeSearch.size()-1).get(lastDepthGoalLocation)); //ADD PARENT
		
	}while(treeSearch.size() > 1);
	
	System.out.println("OPTIMAL SOLUTION STORED");
	
	return finalSolution; //RETURN SOLUTION
}
	//THIS METHOD CHECK THE SOLUTION IN A FULL NEW DEPTH, IT HAS AS INPUT A LIST OF MATRIXES
	private boolean CheckSolution(List<int[][]> newMatrixes) {
		//INITIALIZE VARIABLES
		int carrierForCheck = 0; 
		boolean localSolution = false;
		List<int[][]> localNewMatrixes = new ArrayList<int[][]>();
		localNewMatrixes.clear();
		localNewMatrixes.addAll(newMatrixes);
		int[][] matrixToCheck = new int[4][4];
		
		while(carrierForCheck < localNewMatrixes.size()){ //LOOP THORUGHT ALL THE DEPTH
		matrixToCheck = localNewMatrixes.get(carrierForCheck); //GET THE NEXT MATRIX TO CHECK
		localSolution = movementMethods.CheckSolution(matrixToCheck); //CHECK NEXT MATRIX
		System.out.println("CHECKING " + carrierForCheck +" TREE SIZE " + treeSearch.size());
		lastDepthGoalLocation = carrierForCheck; //STORE LAST DEPTH LOCATION IF SOLUTION IS FOUND
		carrierForCheck ++;
		if(localSolution) localNewMatrixes.clear(); //STOP WHILE IF SOLUTION HAS BEEN FOUND
		}
		return localSolution;
	}
	//THIS METHOD RETURNS THE NEW DEPTH, IT HAS AS INPUT THE PREVIOUS DEPTH,
	public List<int[][]> GetPossibleMatrixes(List<int[][]> workingList) {
	
	List<int[][]> actualList = new ArrayList<int[][]>();
	List<int[][]> newMatrixes3 = new ArrayList<int[][]>();
	List<int[][]> allNewMatrixes = new ArrayList<int[][]>();
	
	newMatrixes3.clear();
	actualList.addAll(workingList);
	while(!actualList.isEmpty()){ //WHILE ALL THE NODES HAS NOT BEEN OPENED
	newMatrixes3.addAll(movementMethods.nextPossibleMoves(actualList.get(0))); //OPEN NEXT NODE AND ADD THE RESULTING NODES TO NEWMATRIXES3
	actualList.remove(0);//REMOVE OPENED NODE
	while(!newMatrixes3.isEmpty()){
		allNewMatrixes.add(newMatrixes3.get(0));
		newMatrixes3.remove(0);
	}
	System.out.println("GETTING NEW MATRIXES  " + actualList.size() + " TREE SIZE " + treeSearch.size());
	}
			return allNewMatrixes;
	}
	
	
	
	
}
