//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
///THIS CLASS IS IN CHARGED OF PERFORMING A STAR SEARCH///////
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////
package MainPackage;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
//OBJECT TO USE IN THIS CLASS
class Node{
	//INITIALIZE VARIABLES
	int[][] goalMatrix = new int[4][4];	//GOAL MATRIX
	Point positionOf5IntGoal = new Point();	//POSITION OF C IN GOAL
	Point positionOf4IntGoal = new Point(); //POSITION OF B IN GOAL
	Point positionOf3IntGoal = new Point(); //POSITION OF A IN GOAL
	Point positionOf2IntGoal = new Point(); //POSITION OF AGENT IN GOAL

	int depth , f;	//DEPTH OF THIS NODE AND F(N)
	int[][] matrix = new int[4][4];	//MATRIX OF THIS NODE
	boolean openNode = false;	//BOOLEAN TO SEE IF THE NODE HAS BEEN OPENED
	int parent;	//VARIABLE TO STORE PARENT IN ORDER TO BE ABLE TO BACK PROPAGATE THE TREE AT THE END
	//CONSTRUCTOR IT HAS THE PARENT, THE MATRIX OF THIS OBJECT, AND THE DEPTH AS INPUTS
	public Node(int[][] matrix, int depth, int parent) {		
		
		for(int i = 0;i < goalMatrix.length; i++)	//STORE GOAL MATRIX
			for(int j = 0;j < goalMatrix.length;j++)
				goalMatrix[i][j] = 0;
		
		//SET A,B,C AND AGENT
		goalMatrix[1][1] = 3;
		goalMatrix[2][1] = 4;
		goalMatrix[3][1] = 5;
		goalMatrix[3][3] = 2;
		
		//SET INITIAL LOCATIONS
		positionOf5IntGoal.setLocation(3, 1);
		positionOf4IntGoal.setLocation(2, 1);
		positionOf3IntGoal.setLocation(1, 1);
		positionOf2IntGoal.setLocation(3, 3);
	
		
		this.depth = depth;	//SET DEPTH
		this.matrix = matrix;	//SET MATRIX
		this.parent = parent; //SET PARENT
		f = ReturnF(matrix, depth); //CALCULATE F FOR THIS NODE
		
	}
	
	//METHOD THAT CALCULATES THE COST OF THE NODE
	private Integer ReturnF(int[][] matrixToCalculateF, int depth){
		//INITIALIZE VARIABLES
		int[][] workingMatrix = new int[4][4];
		Point positionOf3 = new Point();
		Point positionOf4 = new Point();
		Point positionOf5 = new Point();
		Point positionOf2 = new Point();
		int h = 0;	//H(N)
		int f = 0;	//F(N)
		workingMatrix = matrixToCalculateF;
		for(int i = 0;i < workingMatrix.length; i++){
			for(int j = 0;j < workingMatrix.length;j++){
				if(workingMatrix[i][j] == 5) positionOf5.setLocation(i, j);	//LOCATE C
				else if(workingMatrix[i][j] == 4) positionOf4.setLocation(i, j);	//LOCATE B
				else if(workingMatrix[i][j] == 3) positionOf3.setLocation(i, j);	//LOCATE A
				else if(workingMatrix[i][j] == 2) positionOf2.setLocation(i, j);	//LOCATE AGENT
				}
		}
		//CALCULATE THE VALUE OF H BASED ON THE POSITIONS OF A,B,C AND AGENT
		h = Math.abs(positionOf5IntGoal.x - positionOf5.x) + Math.abs(positionOf5IntGoal.y - positionOf5.y)
		+ Math.abs(positionOf4IntGoal.x - positionOf4.x) + Math.abs(positionOf4IntGoal.y - positionOf4.y) 
		+ Math.abs(positionOf3IntGoal.x - positionOf3.x) + Math.abs(positionOf3IntGoal.y - positionOf3.y)
		+ Math.abs(positionOf2IntGoal.x - positionOf2.x) + Math.abs(positionOf2IntGoal.y - positionOf2.y);
		//SUM THE COST TO REACH THAT NODE
		f = h + depth;
		
		return f;
	}
}

public class AStar2{
		//METHOD OF INTELLIGENTMOVEMENTCLASS
		IntelligentMovementClass movementMethods = new IntelligentMovementClass();
		
		//MAIN METHOD IT REQUIRES THE INITIAL STATE AS INPUT
		public List<int[][]> AStar2Method(int[][] initialMatrix){
			long startTime = System.nanoTime();	//START TIME VARIABLE, USEFUL TO CREATE THE TEXT FILE
			File file = new File("aStarLogFix.txt");	//CREATE FILE
			FileWriter fw = null;
			BufferedWriter bw = null;
			try {
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write("#STARTING FILE \n");	//WRITE IN FILE
			
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			
			int level = 0;	//LEVEL OF FIRST NODE
			Node firstNode = new Node(initialMatrix,level,0);	//CREATE OBJECT WITH INITIAL MATRIX
			List<Node> listOfNodes = new ArrayList<Node>();	//	ARRAY OF NODES
			List<Node> newNodes = new ArrayList<Node>();	//ARRAY OF NODES
			List<Node> newNodesAfterDuplicates = new ArrayList<Node>();
			List<Node> arrayOfNodesWithSolution = new ArrayList<Node>();
			List<int[][]> solutionArray = new ArrayList<int[][]>();
			boolean solution = false;
			listOfNodes.add(firstNode);	//ADD TO THE LIST OF NODES THE FIRST NODE
			int lessCostNode = 0;	//VARIABLE THAT IS USED TO STORE THE NODE WITH LESS COST
			int lessCost = 9999;	//INITIAL COST (SET TO INIFINITY)
			Node actualNode;	//WORKING NODE
			
			while(!solution){	//WHILE THE SOLUTION HAS NOT BEEN FOUND
				int i = 0;	//RESET INCREMENTER
				lessCost = 9999; //RESET LESS COST NODE
				while(i < listOfNodes.size()){	//SEARCH IN ALL THE LIST OF NODES
					//IF THE CURRENT NODE HAS LESS COST THAT THE STORED NODE, AND THE NODE i 
					//HAS NOT BEEN OPENED CHANGE STORED NODE
					if(lessCost > listOfNodes.get(i).f && !listOfNodes.get(i).openNode){
						lessCostNode = i;
						lessCost = listOfNodes.get(i).f;
					}
					i++;
					
				}
				actualNode = listOfNodes.get(lessCostNode); //GET LESS COST NODE
				newNodes.addAll(GetPossibleNodes(actualNode,lessCostNode));//EXPAND WORKING NODE
				solution = CheckSolution(newNodes); //CHECK IF THE SOLUTION IS FOUND IN ONE OF THE NEW NODES
				actualNode.openNode = true; //CURRENT NODE HAS BEEN OPENED
				newNodesAfterDuplicates.addAll(CheckDuplicates(newNodes, listOfNodes)); //ERASE FOR DUPLICATED NODES
				listOfNodes.addAll(newNodesAfterDuplicates); //STORE NEW NODES IN THE LIST OF ALL NODES
				newNodes.clear(); //CLEAR NEW NODES BECAUSE THEY HAVE ALREADY BEEN STORED IN LIST OF NODES
				newNodesAfterDuplicates.clear(); //CLEAR DUPLICATED NODES
				try {
					bw.write((System.nanoTime()-startTime)/1000 + " " + listOfNodes.size()+ "\n"); //WRITE TIME AND SIZE OF THE LIST OF NODES
				} catch (IOException e) {
				
					e.printStackTrace();
				}
				
			}
			
			arrayOfNodesWithSolution.clear();	//CLEAR THE ARRAY THAT IS GOING TO BE RETURNED
			arrayOfNodesWithSolution.add(listOfNodes.get(listOfNodes.size() - 1)); //ADD ALL THE NODES WITHOUT THE LAST ONE BECAUSE IT IS THE GOAL STATE
			FillFinalArray(arrayOfNodesWithSolution, listOfNodes);//BACK PROPAGATE THE TREE AND RETURN THE SOLUTION ARRAY
			arrayOfNodesWithSolution.add(firstNode);//ADD INITIAL STATE
			solutionArray.addAll(ArrayToSend(arrayOfNodesWithSolution));//RETURN THE SOLUTION AS A LIST OF MATRIXES AND NOT AS A LIST OF OBJECTS
			System.out.println("SOLUTION FOUND");
			try {
				bw.close();	//CLOSE FILE
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return solutionArray;	//RETURN THE SOLUTION
		}
	// METHOD THAT RETURN POSSIBLE NODES FROM A PARTICULAR STATE
	//IT REQUIRES THE WORKING NODE AND THE PARENT (TO SET THE PARENT TO NEW NODES)
	//AND RETURNS THE LIST OF NODES WITH POSSIBLE MOVES
	public List<Node> GetPossibleNodes(Node workingNode, int parent) {
			//INITIALIZE VARIABLES
			List<int[][]> newMatrixes3 = new ArrayList<int[][]>();
			List<Node> newNodes = new ArrayList<Node>();
			
			newMatrixes3.clear();
			newMatrixes3.addAll(movementMethods.nextPossibleMoves(workingNode.matrix));//GET THE POSSIBLE MOVEMENTS AS MATRIXES
			
			while(!newMatrixes3.isEmpty()){
			newNodes.add(new Node(newMatrixes3.get(0),workingNode.depth + 1, parent)); //CREATE NEW NODES
			newMatrixes3.remove(0);
			}
			
			return newNodes; //RETURN NEW NODES
			}
	//THIS METHOD CHECK FOR SOLUTIONS AN THE ARRAY OF NODE
	//RETURN THE SOLUTION AND HAS AS INPUT THE ARRAY TO CHECK
	private boolean CheckSolution(List<Node> newNodes){
			//INITIALIZE VARIABLES
			boolean solution = false; //AT THE BEGGINING THE SOLUTION IS FALSE
			int i = 0;
			
			while(i < newNodes.size() && !solution){ //WHILE THERE IS STILL NODES IN THE LIST
				solution = movementMethods.CheckSolution(newNodes.get(i).matrix); //CHECK SOLUTION IN NODE i
				i ++;
			}
			
			return solution; //RETURN THE VALUE OF THE SOLUTION
		}
	//THIS METHOD HAS AS INPUT THE LIST OF ALL NODES AND THE NEW NODES
	//AND RETURN THE NEW NODES WITHOUT DUPLICATES
	private List<Node> CheckDuplicates(List<Node> nodeToCheck, List<Node> fullListOfNodes) {
		
		int i = 0;
		int j = 0;
		while(i < nodeToCheck.size()){	//CHECK ALL THE NEW NODES
			loop:
			while(j < fullListOfNodes.size()){	//CHECK ALL THE LIST OF NODES
			 if(nodeToCheck.get(i).equals(fullListOfNodes.get(j))){	//IF NODE i IS EQUAL TO NODE j REMOVE NODE
				 nodeToCheck.remove(i);
				 i = 0;
				 break loop;
			 }
			 j ++;
			}
		i++;
			j = 0;
		}
		
		return nodeToCheck; //RETURN NEW NODES WITHOUT DUPLICATES
	}
	//THIS METHOD IS USED TO BACK PROPAGATE THE TREE AND GET THE STEPS TO REACH THE SOLUTION
	//IT REQUIRES THE FULL LIST AND THE ARRAY OF THE SOLUTON
	private void FillFinalArray(List<Node> lastArrayOfNodes, List<Node> fullList){
		int nextParent = 0;
		while(lastArrayOfNodes.get(lastArrayOfNodes.size()-1).parent != 0){ //WHILE THE INITIAL STATE HAS NOT BEEN REACHED
			
			nextParent = lastArrayOfNodes.get(lastArrayOfNodes.size() - 1).parent; //GET PARENT
			lastArrayOfNodes.add(fullList.get(nextParent));//GET THE NODE WITH THE INDEX OF THE PARENT
			
		}
		
		
		
	}
	//THIS METHOD RETURNS A LIST OF MATRIXES. IT CHANGES THE LIST OB OBJECTS TO A LIST OF MATRIXES
	private List<int[][]> ArrayToSend(List<Node> lastArrayNodes){
		
		List<int[][]> listToSend = new ArrayList<int[][]>();
		
		while(!lastArrayNodes.isEmpty()){
		
		listToSend.add(lastArrayNodes.get(lastArrayNodes.size() - 1).matrix);
		lastArrayNodes.remove(lastArrayNodes.size()-1);
		}
		return listToSend;
		
	}
}
