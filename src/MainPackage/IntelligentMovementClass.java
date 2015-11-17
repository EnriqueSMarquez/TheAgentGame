package MainPackage;
//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
//THIS CLASS HAVE USEFUL METHODS TO SUPPORT THE OTHER CLASSES
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class IntelligentMovementClass {
	int movementsCounts = 0;
	
	SwapClass swapMethods = new SwapClass();
	Integer carrierForBreadthMethod = 1;
	Random r = new Random();
	int agentPosition = 0;
	int movingNumber = 0;
	int[][] goalMatrix = new int[4][4];
	int incrementer = 0;
	List<Integer>numberOfNodes = new ArrayList<Integer>();
	
	public IntelligentMovementClass() {
		
	}
	//CONSTRUCTOR USED BY BFS
	public IntelligentMovementClass(int i) {
		numberOfNodes.add(0);
	}
	//GETTER OF NUMBEROFNODES
	public List<Integer> getNumberOfNodes(){
		
		return numberOfNodes;
		
	}
	//THIS METHOD RETURNS A RANDOM VALID MOVE, AND HAS THE ACTUAL MATRIX AS INPUT
	public int[][] nextMove(int[][] workingMatrix){
		//INITIALIZE VARIABLES
		agentPosition = 0;
		List<Integer> posibleMovements = new ArrayList<Integer>();
		movingNumber = 0;
		agentPosition = FindAgentInMatrix(workingMatrix); //FIND AGENT IN MATRIX
		posibleMovements = FindThePossibleMoves(agentPosition); //GET POSSIBLE MOVES
		movingNumber = posibleMovements.size();
		movingNumber = r.nextInt(movingNumber);
		movingNumber = posibleMovements.get(movingNumber); //GET A RANDOM MOVE
		workingMatrix = swapMethods.SwapMethod(workingMatrix, agentPosition, movingNumber); //DO THE MOVE
		movementsCounts ++;
		System.out.println(movementsCounts);
		return workingMatrix;
		
	}
	
	//THIS METHOD CHECK IF A MATRIX IS THE SOLUTION
		public boolean CheckSolution(int[][] actualMatrix) {

			boolean returnValue;
			if(actualMatrix != null){
			
			//CREATE GOAL MATRIX
			for(int i = 0; i < goalMatrix.length; i ++)
				for(int j = 0; j < goalMatrix.length; j ++)
					goalMatrix[i][j] = 0;
		
			goalMatrix[1][1] = 3;
			goalMatrix[2][1] = 4;
			goalMatrix[3][1] = 5;
			goalMatrix[3][3] = 2;
			
			//CHECK THAT ALL THE ELEMENTS OF BOTH MATRIXES ARE THE SAME
			int carrier = 0;
			for (int i = 0; i < actualMatrix.length; i++) {
				for (int j = 0; j < actualMatrix.length; j++) {
					if (actualMatrix[i][j] == goalMatrix[i][j]) {
						carrier++;
					}
				}
			}
			//IF THERE ARE ALL THE SAME RETURN TRUE
			if (carrier == 16)
				returnValue = true;
			else
				returnValue = false;
			}
			else 
			returnValue = false;
			
			return returnValue;
			
		}
	//THIS METHOD FINDS THE ERROR IN A GIVEN MATRIX	
	private int FindAgentInMatrix(int[][] matrixToSearch){
		int agentLocation = 0;
		int i = 0;
		int j = 0;
		int x = 0;
		int y = 0;
		//LOOP THROUGH THE MATRIX
		for(i = 0; i < matrixToSearch.length; i ++){
			for(j = 0; j < matrixToSearch.length; j ++){
				//IF THE ACTUAL NUMBER IS TWO, STORE THE ROW AND COLUMN NUMBER AND BREAK THE LOOP
				if(matrixToSearch[i][j] == 2){
					x = i;
					y = j;
					break;
				}
				
			}
			
		}
		
		//GETTING AGENT LOCATION AS INT AND NOT AS A POINT
		switch(x){
		case 0:
			agentLocation = x + y;
			break;
		case 1:
			agentLocation = x + y + 3;
			break;
		case 2:
			agentLocation = x + y + 6;
			break;
		case 3:
			agentLocation = x + y + 9;
			break;
			
		}
		return agentLocation;
		
	}
	//THIS METHOD FINDS THE POSSIBLE MOVES, IT ONLY DEPENDS ON THE AGENT
	private List<Integer> FindThePossibleMoves(int agent){
		LinkedList<Integer> possibleMovements = new LinkedList<Integer>();
		possibleMovements.clear();
		//SWITCH WITH THE INFORMATION OF ALL THE VALID MOVES
		//THIS SWITCH DEPENDS ON THE AGENT POSITION
		switch(agent){
		case 0:
			possibleMovements.add(1);
			possibleMovements.add(4);
			break;
		case 1:
			possibleMovements.add(0);
			possibleMovements.add(2);
			possibleMovements.add(5);
			break;
		case 2:
			possibleMovements.add(1);
			possibleMovements.add(3);
			possibleMovements.add(6);
			break;
		case 3:
			possibleMovements.add(7);
			possibleMovements.add(2);
			break;
		case 4:
			possibleMovements.add(0);
			possibleMovements.add(5);
			possibleMovements.add(8);
			break;
		case 5:
			possibleMovements.add(1);
			possibleMovements.add(4);
			possibleMovements.add(6);
			possibleMovements.add(9);
			break;
		case 6:
			possibleMovements.add(2);
			possibleMovements.add(5);
			possibleMovements.add(10);
			possibleMovements.add(7);
			break;
		case 7:
			possibleMovements.add(6);
			possibleMovements.add(3);
			possibleMovements.add(11);
			break;
		case 8:
			possibleMovements.add(4);
			possibleMovements.add(9);
			possibleMovements.add(12);
			break;
		case 9:
			possibleMovements.add(5);
			possibleMovements.add(8);
			possibleMovements.add(13);
			possibleMovements.add(10);
			break;
		case 10:
			possibleMovements.add(6);
			possibleMovements.add(9);
			possibleMovements.add(11);
			possibleMovements.add(14);
			break;
		case 11:
			possibleMovements.add(10);
			possibleMovements.add(7);
			possibleMovements.add(15);
			break;
		case 12:
			possibleMovements.add(13);
			possibleMovements.add(8);
			break;
		case 13:
			possibleMovements.add(9);
			possibleMovements.add(12);
			possibleMovements.add(14);
			break;
		case 14:
			possibleMovements.add(10);
			possibleMovements.add(13);
			possibleMovements.add(15);
			break;
		case 15:
			possibleMovements.add(11);
			possibleMovements.add(14);
			break;
		 	
		}
		
		return possibleMovements;
		
	}
	//THIS METHOD RETURNS THE POSSIBLE MOVES (VALID MOVEMENTS) AS A LIST
	public List<int[][]> nextPossibleMoves(int[][] workingMatrix){
		
		carrierForBreadthMethod ++; //LOCAL VARIABLE LINKED TO BFS METHOD
		final int[][] staticMatrix = workingMatrix;
		agentPosition = 0;
		movingNumber = 0;
		List<Integer> possibleMovements = new ArrayList<Integer>();
		List<int[][]> nextMatrixes = new ArrayList<int[][]>();
		int[][] matrixToStore = new int[4][4];
		nextMatrixes.clear();
		agentPosition = FindAgentInMatrix(staticMatrix);
		possibleMovements = FindThePossibleMoves(agentPosition);
		
		
		while(!possibleMovements.isEmpty()){
			numberOfNodes.add(carrierForBreadthMethod);
			movingNumber = possibleMovements.get(0);
			possibleMovements.remove(0);
			matrixToStore = swapMethods.SwapMethod(staticMatrix, agentPosition, movingNumber);	//SWAP TO GET NEW MATRIX
			nextMatrixes.add(matrixToStore); // STORE NEW MATRIX
			
		}
		movementsCounts ++;
		
		return nextMatrixes;
		
	}


}
