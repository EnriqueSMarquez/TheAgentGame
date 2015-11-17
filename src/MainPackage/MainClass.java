//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
//////////////////THIS CLASS IS THE MAIN THREAD////////////
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////
package MainPackage;


import java.util.ArrayList;
import java.util.List;

public class MainClass {

	public static void main(String args[]) {
		
		//INITIALIZE VARIABLE
		//A STAR CLASS OBJECT
		AStar2 aStar2Method = new AStar2();
		//BFS CLASS VARIABLE
		BreadthFirstSearch breadthFirstSearch = new BreadthFirstSearch();
		//DFS CLASS VARIABLE
		DephtFirstSearch dephtFirstSearch = new DephtFirstSearch();
		//IDS CLASS VARIABLE
		IterativeDeepeningSearch iterativeDeepeningSearch = new IterativeDeepeningSearch();
		int[][] workingMatrix = new int[4][4];	//WORKING MATRIX
		List<int[][]> solutionForBreadthFirstSearch = new ArrayList<int[][]>();	//VARIABLE TO STORE SOLUTION BFS
		List<int[][]> solutionForIterativeDeepeningSearch = new ArrayList<int[][]>(); //VARIABLE TO STORE SOLUTION IDS
		List<int[][]> solutionForAStar = new ArrayList<int[][]>(); //VARIABLE TO STORE SOLUTION A*
		List<Integer[]> arrayOfMovementsForDepthFirstSearch = new ArrayList<Integer[]>(); //VARIABLE TO SORE SOLUTION DFS
		int option = 0;	//OPTION VARIABLE FOR SWITCH
		boolean buttonHasBeenPressed = false;	//VARIABLE TO DETECT IF ANY CHOICE HAS BEEN MADE IN THE FRAME
		
		//FILL WORKING MATRIX WITH CEROS
		for (int i = 0; i < workingMatrix.length; i++)
     		for (int j = 0; j < workingMatrix[0].length; j++)
     			workingMatrix[i][j] = 0;
		
		workingMatrix[0][1] = 3;
		workingMatrix[2][2] = 4;
		workingMatrix[3][0] = 5;
		workingMatrix[3][2] = 2;
		
		//UNCOMMENT TO CHANGE INITIAL STATE
		//workingMatrix[3][0] = 3;
		//workingMatrix[3][1] = 4;
		//workingMatrix[3][2] = 5;
		//workingMatrix[3][3] = 2;
		
		ViewController viewController = new ViewController(workingMatrix); //CREATE FRAME WITH INITIAL STATE
		
		while(!buttonHasBeenPressed){	//WAIT FOR BUTTON TO BE PRESSED
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
			}
			if(viewController.startBFS == true){	// IF THE BFS BUTTON HAS BEEN PRESSED
				option = 0;	//OPTION OF BFS
				//SOME CHANGES IN THE FRAME
				viewController.buttonForBreadthFirstSearch.setLocation(200, 600);	
				viewController.buttonForDepthFirstSearch.setVisible(false);	//LEAVE ONLY ONE BUTTON
				viewController.buttonForDeepeningSearch.setVisible(false);
				viewController.buttonForAStarSearch.setVisible(false);
				viewController.buttonForBreadthFirstSearch.setText("WAIT");	//CHANGE STRING OF THE REMAIN BUTTON
				buttonHasBeenPressed = true;
			}
			else if(viewController.startDFS == true){	//IF DFS BUTTON HAS BEEN PRESSED
				option = 1;	//OPTION OF DFS
				//SOME CHANGES IN THE FRAME
				viewController.buttonForBreadthFirstSearch.setLocation(200, 600);
				viewController.buttonForDepthFirstSearch.setVisible(false);
				viewController.buttonForDeepeningSearch.setVisible(false);
				viewController.buttonForAStarSearch.setVisible(false);
				viewController.buttonForBreadthFirstSearch.setText("WAIT");
				buttonHasBeenPressed = true;
			}else if(viewController.startAstar == true){
				option = 2; //OPTION OF A*
				
				viewController.buttonForBreadthFirstSearch.setLocation(200, 600);
				viewController.buttonForDepthFirstSearch.setVisible(false);
				viewController.buttonForDeepeningSearch.setVisible(false);
				viewController.buttonForAStarSearch.setVisible(false);
				viewController.buttonForBreadthFirstSearch.setText("WAIT");
				buttonHasBeenPressed = true;
			}else if(viewController.startIDS == true){
				option = 3; //OPTION OF IDS
				
				viewController.buttonForBreadthFirstSearch.setLocation(200, 600);
				viewController.buttonForDepthFirstSearch.setVisible(false);
				viewController.buttonForDeepeningSearch.setVisible(false);
				viewController.buttonForAStarSearch.setVisible(false);
				viewController.buttonForBreadthFirstSearch.setText("WAIT");
				buttonHasBeenPressed = true;
			}
		}
		
		switch(option){		//CHECK WHICH BUTTON HAS BEEN PRESSED
		
		case 0:
			viewController.informationLabel.setText("PLEASE WAIT UNTIL A SOLUTION IS FOUND");	//CHANGE LABEL STRING
			viewController.informationLabel2.setText("THROUGHT BREADTH FIRST SEARCH");	//CHANGE LABEL STRING
			solutionForBreadthFirstSearch.addAll(breadthFirstSearch.BreadthFirstSearchMethod(workingMatrix));	//FIND THE SOLUTION USING BFS
			solutionForBreadthFirstSearch.remove(solutionForBreadthFirstSearch.size()-1);	//REMOVE THE LAST OBJECT OF THE ARRAY BECAUSE THE GOAL IS DUPLICATED
			viewController.informationLabel.setText("THE SOLUTION HAS BEEN FOUND IN "+ solutionForBreadthFirstSearch.size() + " MOVES");	//SET LABELS STRING
			viewController.informationLabel2.setText("PLEASE CLICK NEXT TO SEE EACH MOVE");
			viewController.buttonForBreadthFirstSearch.setText("NEXT");	//CHANGE BUTTON STRING
			
			while (!solutionForBreadthFirstSearch.isEmpty()) { //WHILE THE SOLUTION ARRAY IS NOT EMPTY
				if (viewController.nextMove) {		//WAIT FOR USER TO PRESS THE BUTTON SLEEP IF HAS NOT BEEN PRESSED
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
					}
					int[][] movementsToSend = new int[4][4];	//NEXT MOVEMENT TO SHOW IN THE FRAME
					movementsToSend = solutionForBreadthFirstSearch.get(solutionForBreadthFirstSearch.size()-1);
					viewController.ChangeFrameToNewMatrix(movementsToSend);	//CHANGE FRAME
					solutionForBreadthFirstSearch.remove(solutionForBreadthFirstSearch.size()-1); //REMOVE THE MOVEMENT THAT HAS ALREADY SHOWED
					
				}
			}
			break;
		case 1:
			//SAME AS BFS JUST THAT THE SOLUTION WAS NOT FOUND USING BFS BUT DFS
			viewController.informationLabel.setText("PLEASE WAIT UNTIL A SOLUTION IS FOUND");
			viewController.informationLabel2.setText("THROUGHT DEPTH FIRST SEARCH");
			arrayOfMovementsForDepthFirstSearch.addAll(dephtFirstSearch.DephtFirstSearchMethod(workingMatrix));
			viewController.informationLabel.setText("THE SOLUTION HAS BEEN FOUND IN "+ arrayOfMovementsForDepthFirstSearch.size() + " MOVES");
			viewController.informationLabel2.setText("PLEASE CLICK NEXT TO SEE EACH MOVE");
			viewController.buttonForBreadthFirstSearch.setText("NEXT");
			
			
			while (!arrayOfMovementsForDepthFirstSearch.isEmpty()) {
				if (viewController.nextMove) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
					}
					Integer[] movementsToSend = new Integer[2];

					movementsToSend = arrayOfMovementsForDepthFirstSearch.get(0);
					viewController.SwapSquares(movementsToSend[0],
							movementsToSend[1]);	//IN THIS CASE THE MOVEMENTS ARE STORED AS A TWO DIMENSIONAL NOT SQUARED MATRIX
					arrayOfMovementsForDepthFirstSearch.remove(0);
				}
				}
			
		case 2:
			//SAME AS BFS JUST THAT THE SOLUTION WAS NOT FOUND USING BFS BUT A STAR
			viewController.informationLabel.setText("PLEASE WAIT UNTIL A SOLUTION IS FOUND");
			viewController.informationLabel2.setText("THROUGHT A* SEARCH");
			solutionForAStar.addAll(aStar2Method.AStar2Method(workingMatrix));
			solutionForAStar.remove(0); //THE INITIAL STATE IS DUPLICATED
			viewController.informationLabel.setText("THE SOLUTION HAS BEEN FOUND IN "+ solutionForAStar.size() + " MOVES");
			viewController.informationLabel2.setText("PLEASE CLICK NEXT TO SEE EACH MOVE");
			viewController.buttonForBreadthFirstSearch.setText("NEXT");
			
						
			while (!solutionForAStar.isEmpty()) {
				if (viewController.nextMove) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
					}
					int[][] movementsToSend = new int[4][4];
					movementsToSend = solutionForAStar.get(0); //GET NEXT MOVE TO REACH THE SOLUTION
					viewController.ChangeFrameToNewMatrix(movementsToSend); //CHANGE FRAME
					solutionForAStar.remove(0); //REMOVE MOVE THAT HAS BEEN SET IN THE FRAME
					
				}
			}
			break;
		case 3:
			//SAME AS BFS JUST THAT THE SOLUTION WAS NOT FOUND USING BFS BUT IDS
			viewController.informationLabel.setText("PLEASE WAIT UNTIL A SOLUTION IS FOUND");
			viewController.informationLabel2.setText("THROUGHT ITERATIVE DEEPENING SEARCH");
			solutionForIterativeDeepeningSearch.addAll(iterativeDeepeningSearch.IterativeDeepeningSearchMethod(workingMatrix));
			solutionForIterativeDeepeningSearch.remove(0);
			viewController.informationLabel.setText("THE SOLUTION HAS BEEN FOUND IN "+ solutionForIterativeDeepeningSearch.size() + " MOVES");
			viewController.informationLabel2.setText("PLEASE CLICK NEXT TO SEE EACH MOVE");
			viewController.buttonForBreadthFirstSearch.setText("NEXT");
			
			
			
			while (!solutionForIterativeDeepeningSearch.isEmpty()) {
				if (viewController.nextMove) {
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
					}
					int[][] movementsToSend = new int[4][4];
					movementsToSend = solutionForIterativeDeepeningSearch.get(0);
					viewController.ChangeFrameToNewMatrix(movementsToSend);
					solutionForIterativeDeepeningSearch.remove(0);
					
				}
			}
			
		}
		
		
	}
}
