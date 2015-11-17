package MainPackage;
//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
///////THIS CLASS IS IN CHARGED OF DOING THE DF SEARCH//////
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DephtFirstSearch {
	//LOCAL VARIABLES
	ViewController viewController = new ViewController();
	BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
	int[][] workingMatrix = new int[4][4];
	List<Integer[][]> arrayOfMatrixes = new ArrayList<Integer[][]>();
	List<Integer[]> arrayOfMovements = new ArrayList<Integer[]>();
	IntelligentMovementClass move = new IntelligentMovementClass();
	boolean solution = false;
	
	
//THIS METHOD DO DEPTH FIRST SEARCH, HAS THE INITIAL MATRIX AS INPUT AND RETURNS A LIST WITH THE SOLUTION
public List<Integer[]> DephtFirstSearchMethod(int[][] matrixToStart){
	//INITIALIZE VARIABLES
	long startTime = System.nanoTime();
	workingMatrix = matrixToStart;
	File file = new File("DFSFix.txt");
	FileWriter fw = null;
	BufferedWriter bw = null;
	try {
	fw = new FileWriter(file.getAbsoluteFile());
	bw = new BufferedWriter(fw);
	bw.write("#STARTING FILE \n TIME NODES(SAME A TREE SIZE)");
	
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	//WHILE SOLUTION HAS NOT BEEN FOUND
	while(!solution){
		
		Integer[] temporalToMove = new Integer[2];
		Integer[][] temporalMatrix = new Integer[4][4];
		for (int i = 0; i < temporalMatrix.length; i++)
			for (int j = 0; j < temporalMatrix.length; j++)
				temporalMatrix[i][j] = workingMatrix[i][j]; //COPY MATRIX
		
		arrayOfMatrixes.add(temporalMatrix);//ADD MATRIX TO LIST WITH SOLUTION
		workingMatrix = move.nextMove(workingMatrix); //GET NEXT MOVE RANDOMDLY
		temporalToMove[0] = move.agentPosition; //STORE GENT POSITION
		temporalToMove[1] = move.movingNumber; //GET MOVE
		arrayOfMovements.add(temporalToMove); //STORE MOVEMENTS IN ARRAY
		solution = move.CheckSolution(workingMatrix); //CHECK IF NEW NODE IS THE SOLUTION
		System.out.println((System.nanoTime() - startTime)/1000 + " " + arrayOfMatrixes.size());
		try {
			bw.write((System.nanoTime() - startTime)/1000 + " " + arrayOfMatrixes.size() + "\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//UNCOMMENT TO DO LIMITED DFS
		// System.out.println(maxMovements);
		/*if (!solution && move.movementsCounts == maxMovements) { // ERASING IN CASE
														// SOLUTION TO FAR
			arrayOfMatrixes.clear();
			arrayOfMovements.clear();
			solution = false;
			move.movementsCounts = 0;
			for (int i = 0; i < workingMatrix.length; i++) {
				for (int j = 0; j < workingMatrix.length; j++) {

					workingMatrix[i][j] = 0;
				}
			}
			workingMatrix[0][1] = 3;
			workingMatrix[2][2] = 4;
			workingMatrix[3][0] = 5;
			workingMatrix[3][2] = 2;

		} // ERASING IN CASE SOLUTION TO FAR */
	}
	
	try {
		bw.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return arrayOfMovements;
	}

}


