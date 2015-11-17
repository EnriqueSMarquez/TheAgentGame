package MainPackage;
//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
///////THIS CLASS IS IN CHARGED OF DOING IDS SEARCH//////
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//OBJECT TO USE IN THIS CLASS
class Node2{
	//INITIALIZE LOCAL VARIABLES OF OBJECT
	int[][] goalMatrix = new int[4][4];
	Point positionOf5IntGoal = new Point();
	Point positionOf4IntGoal = new Point();
	Point positionOf3IntGoal = new Point();
	Point positionOf2IntGoal = new Point();

	
	int depth;
	int[][] matrix = new int[4][4];
	boolean openNode = false;
	int parent;
	//CONSTRUCTOR
	public Node2(int[][] matrix, int depth, int parent) {
		
		for(int i = 0;i < goalMatrix.length; i++)
			for(int j = 0;j < goalMatrix.length;j++)
				goalMatrix[i][j] = 0;
	
		goalMatrix[1][1] = 3;
		goalMatrix[2][1] = 4;
		goalMatrix[3][1] = 5;
		goalMatrix[3][3] = 2;
			
		//STORE DEPTH, MATRIX AND PARENT OF THE CORRESPONDING NODE
		this.depth = depth;
		this.matrix = matrix;
		this.parent = parent;
		
	
	}
}

public class IterativeDeepeningSearch {
	
	IntelligentMovementClass movementMethods = new IntelligentMovementClass();
	
	//METHOD THAT PERFORMS IDS, IT REQUIRES THE INITIAL MATRIX AND RETURNS THE OPTIMAL SOLUTION
	//IT DOES NOT WORK PROPERLY BECAUSE IT KEEPS OPENING OPEN NODES
	public List<int[][]> IterativeDeepeningSearchMethod(int [][] initialMatrix){
		//INITIALIZE VARIABLES
		long startTime = System.nanoTime();
		File file = new File("IDSLog.txt");
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
		fw = new FileWriter(file.getAbsoluteFile());
		bw = new BufferedWriter(fw);
		bw.write("#STARTING FILE TIME ITERATION NODES\n");
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Node2 initialNode = new Node2(initialMatrix,0,0);	//initial Node
		Node2 workingNode;
		List<Node2> nodeList = new ArrayList<Node2>(); 
		boolean solution = false;
		List<Node2> newNodes = new ArrayList<Node2>();
		List<Node2> newNodes2 = new ArrayList<Node2>();
		List<Node2> queue = new ArrayList<Node2>();
		List<Node2> solutionArray = new ArrayList<Node2>();
		List<int[][]> solutionArrayMatrix = new ArrayList<int[][]>();
		int depth = 0;
		int iteration = 1;
		
		//WHILE SOLUTION HAS NOT BEEN FOUND
		while(!solution){
			depth = 0;
			queue.clear(); //CLEAR THE QUEUE
			nodeList.clear();
			workingNode = initialNode; //INITIALIZE WORKING NODE
			nodeList.add(initialNode);
			int parent = 0;
			
			do{
			newNodes.clear();
			newNodes2.clear();
			newNodes.addAll(GetPossibleNodes(workingNode,parent)); //GET POSSIBLE NODES (SET THE PARENT OF THOSE NODES AS WELL)
			newNodes2.addAll(CheckDuplicates(newNodes, nodeList)); //ERASE DUPLICATES
			solution = CheckSolution(newNodes2); //CHECK IF THE SOLUTION IS IN THE NEW NODES
			nodeList.addAll(newNodes2);
			workingNode = nodeList.get(nodeList.size() - 1); //UPDATE WORKING NODE
			newNodes2.remove(newNodes2.size() - 1);
			System.out.println((System.nanoTime() - startTime)/1000 + " " + depth + " " + nodeList.size());
			try {
				bw.write((System.nanoTime() - startTime)/1000 + " " + depth + " " + nodeList.size());
			} catch (IOException e) {
		
				e.printStackTrace();
			}
			//IF DEPTH HAS NOT BEEN REACHED
			if(depth < iteration){			
			depth ++; //INCREASE DEPTH
			queue.addAll(newNodes2); //ADD REST OF NODES TO QUEUE
			parent = nodeList.size() - 1; //UPDATE PARENT
			
			}else if(!queue.isEmpty()){ //ELSE IF THE QUEUE IS NOT EMPTY
			workingNode = queue.get(0);	//WORKING NODE IS THE NEXT IN THE QUEUE
			queue.remove(0); //REMOVE NODE FROM QUEUE
			depth = workingNode.depth; //UPDATE DEPTH
			parent = FindParent(workingNode, nodeList); //UPDATE PARENT
			}
			
		}while(!queue.isEmpty() && !solution); //WHILE QUEUE IS NOT EMPTY AND SOLUTION HAS NOT BEEN FOUND
			
			iteration ++; //INCREASE DEPTH
		}
		solutionArray.add(nodeList.get(nodeList.size() - 1)); // GET LAST DEPTH
		FillFinalArray(solutionArray, nodeList); //FILL SOLUTION ARRRAY
		solutionArray.add(initialNode); //ADD INITIAL NODE TO COMPLETE THE SOLUTION ARRAY
		System.out.println("SOLUTION FOUND");
		solutionArrayMatrix.addAll(ArrayToSend(solutionArray)); //CHANGE FROM OBJECT TO MATRIX
		try {
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
		return solutionArrayMatrix;
	}
//METHOD THAT OPENS A NODE, IT ALSO ASSIGN THE PARENT TO EACH NEW NODE
public List<Node2> GetPossibleNodes(Node2 workingNode, int parent) {
		
		List<int[][]> newMatrixes3 = new ArrayList<int[][]>();
		List<Node2> newNodes = new ArrayList<Node2>();
		
		newMatrixes3.clear();
		newMatrixes3.addAll(movementMethods.nextPossibleMoves(workingNode.matrix));
		
		while(!newMatrixes3.isEmpty()){
		newNodes.add(new Node2(newMatrixes3.get(0),workingNode.depth + 1, parent));
		newMatrixes3.remove(0);
		}
		
		return newNodes;
		}
	
private boolean CheckSolution(List<Node2> newNodes){
		
		boolean solution = false;
		int i = 0;
		
		while(i < newNodes.size() && !solution){
			solution = movementMethods.CheckSolution(newNodes.get(i).matrix);
			i ++;
			
		}
		
		return solution;
	}
private List<Node2> CheckDuplicates(List<Node2> nodeToCheck, List<Node2> fullListOfNodes) {
	
	int i = 0;
	int j = 0;
	int carrier = 0;
	boolean loop = false;
	while(i < nodeToCheck.size()){
		loop = false;
		while(j < fullListOfNodes.size() && loop == false){
			for(int x = 0;x < nodeToCheck.get(i).matrix.length; x++){
				for(int y = 0;y < nodeToCheck.get(i).matrix.length;y++){
					if(nodeToCheck.get(i).matrix[x][y] == fullListOfNodes.get(j).matrix[x][y])
						carrier ++;
					if(carrier == 16){
					nodeToCheck.remove(i);
					 i = 0;
					 carrier = 0;
					 loop = true;
					}else carrier = 0;
				}
			}
		 
			 
		 
		 j ++;
		}
	i++;
		j = 0;
	}
	
	return nodeToCheck;
}

private List<Node2> FillFinalArray(List<Node2> lastArrayOfNodes, List<Node2> fullList){
	int nextParent = 0;
	while(lastArrayOfNodes.get(lastArrayOfNodes.size()-1).parent != 0){
		
		nextParent = lastArrayOfNodes.get(lastArrayOfNodes.size() - 1).parent;
		lastArrayOfNodes.add(fullList.get(nextParent));
		
	}
	
	return lastArrayOfNodes;
	
}

private List<int[][]> ArrayToSend(List<Node2> lastArrayNodes){
	
	List<int[][]> listToSend = new ArrayList<int[][]>();
	
	while(!lastArrayNodes.isEmpty()){
	
	listToSend.add(lastArrayNodes.get(lastArrayNodes.size() - 1).matrix);
	lastArrayNodes.remove(lastArrayNodes.size()-1);
	}
	return listToSend;
	
}

private int FindParent(Node2 nodeToFindParent, List<Node2> listOfNodes){
	boolean foundMatrix = false;
	int i = 0;
	loop:
	for(i = 0;i < listOfNodes.size(); i++){
		foundMatrix = Arrays.deepEquals(nodeToFindParent.matrix, listOfNodes.get(i).matrix);
		if(foundMatrix)
		break loop;
	}
	
	return i;
}


}
