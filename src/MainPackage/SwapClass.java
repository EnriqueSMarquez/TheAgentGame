package MainPackage;
//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
//THIS CLASS IS IN CHARGED OF SWAPPING TWO ELEMENTS IN THE WORKING MATRIX
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////
public class SwapClass {
	int temp = 0;
	int row1 = 0;
	int row2 = 0;
	int column1 = 0;
	int column2 = 0;
	//METHOD THAT SWAPS TWO ELEMENTS, INPUTS ARE THE MATRIX TO SWAP AND THE TWO POSITIONS
	public int[][] SwapMethod(int[][] matrixToSwap, int swapNumber1, int swapNumber2){
		int[][] matrixResource = new int[4][4];
		for(int i = 0; i < matrixResource.length; i ++)
			for(int j = 0; j < matrixResource.length; j ++)
				matrixResource[i][j] = matrixToSwap[i][j]; //COPY MATRIX
		
		//GET COLUMNS AND ROWS OF BOTH NUMBERS
		row1 = GetRow(swapNumber1);
		column1 = GetColumn(swapNumber1);
		row2 = GetRow(swapNumber2);
		column2 = GetColumn(swapNumber2);
		//SWAP BOTH ELEMENTS
		temp = matrixResource[row1][column1];
		matrixResource[row1][column1] = matrixResource[row2][column2];
		matrixResource[row2][column2] = temp;
				
		return matrixResource;
		
	}
	//GET THE ROW OF A NUMBER
	public int GetRow(int x){
		int returnValue = 0;
		if(x <= 3 && x >= 0)
			returnValue = 0;
			else if(x <= 7 && x >= 4)
				returnValue = 1;
				else if(x <= 11 && x >= 8)
					returnValue = 2;
					else if(x <= 15 && x >= 12)
						returnValue = 3;
				
		return returnValue;
		
	}
	//GET THE COLUMNS OF A NUMBER
	public int GetColumn(int x){
		int returnValue = 0;
		if(x == 0 || x == 4 || x == 8 || x == 12)
			returnValue = 0;
			else if(x == 1 || x == 5 || x == 9 || x == 13)
				returnValue = 1;
				else if(x == 2 || x == 6 || x == 10 || x == 14)
					returnValue = 2;
					else if(x == 3 || x == 7 || x == 11 || x == 15)
						returnValue = 3;
				
		return returnValue;
				
	
	}
	
	

}
