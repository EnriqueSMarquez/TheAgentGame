package MainPackage;
//////////////////////ENRIQUE MARQUEZ////////////////////
///////////////ASSIGNMENT 1 FOUNDATIONS OF AI//////////////
///////THIS CLASS IS IN CHARGED OF THE VIEW////////////
//////////////////////////////////////////////////////////
//////////////////UNIVERSITY OF SOUTHAMPTON///////////////
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ViewController extends JFrame{
	
	private static final long serialVersionUID = 1L;
	//INITIALIZE ALL THE VARIABLES AND ELEMENTS OF THE FRAME
	MainClass actionL = new MainClass();
	SwapClass swaper = new SwapClass();
	JPanel panel1 = new JPanel();
	ImageIcon blankImage = new ImageIcon("res/blank.png");
	JLabel[] blankImages = new JLabel[12];
	ImageIcon aIcon = new ImageIcon("res/a.png");
	ImageIcon bIcon = new ImageIcon("res/b.png");
	ImageIcon cIcon = new ImageIcon("res/c.png");
	ImageIcon agentIcon = new ImageIcon("res/agent.png");
	JLabel aSquare = new JLabel(aIcon);
	JLabel bSquare = new JLabel(bIcon);
	JLabel cSquare = new JLabel(cIcon);
	JLabel agentSquare = new JLabel(agentIcon);
	JLabel informationLabel = new JLabel();
	JLabel informationLabel2 = new JLabel();
	JButton buttonForDepthFirstSearch = new JButton();
	JButton buttonForBreadthFirstSearch = new JButton();
	JButton buttonForAStarSearch = new JButton();
	JButton buttonForDeepeningSearch = new JButton();
	
	Point[] locationOfLabels = new Point[16];
	int [] sudokuArray = new int[81];
	int locationX = 0;
	//BOOLEAN FOR BUTTONS
	boolean startBFS = false;
	boolean startDFS = false;
	boolean startAstar = false;
	boolean startIDS = false;
	
	boolean nextMove = false;
	int[][] actualMatrix = new int[4][4];
	
	public ViewController() {
		
	}
	
	//CONSTRUCTOR, IT DISPLAYS THE INITIAL MATRIX AT THE BEGGINING
	public ViewController(int[][] initialMatrix) {
		
		
		super("FOUNDATIONS OF AI FIRST ASSIGNMENT");
		actualMatrix = initialMatrix;
		PointsInfo();
		FrameInfo();
		LabelsInfo();
		ButtonInfo();
		LocationsInfo(actualMatrix);
		PanelInfo();		
	}
	
	//SETUP THE LOCATION OF ALL THE ICONS IN THE FRAME
	private void LocationsInfo(int[][] actualMatrix2) {
		int carrierLocation = 0;
		int carrierForBlanks = 0;
		for(int i = 0;i < actualMatrix2.length;i++){
			for(int j = 0;j < actualMatrix2.length; j++){
				if(actualMatrix2[i][j] == 0) {
					blankImages[carrierForBlanks] = new JLabel(blankImage);
					blankImages[carrierForBlanks].setSize(125,125);
					blankImages[carrierForBlanks].setLocation(locationOfLabels[carrierLocation]);
					carrierForBlanks++;
				}else if(actualMatrix2[i][j] == 2) agentSquare.setLocation(locationOfLabels[carrierLocation]);
				else if(actualMatrix2[i][j] == 3) aSquare.setLocation(locationOfLabels[carrierLocation]);
				else if(actualMatrix2[i][j] == 4) bSquare.setLocation(locationOfLabels[carrierLocation]);
				else if(actualMatrix2[i][j] == 5) cSquare.setLocation(locationOfLabels[carrierLocation]);
				
				carrierLocation++;
			}
		}
		
	}

	//SETUP THE PANEL
	public void PanelInfo(){
		
		panel1.setLayout(null);
		for(int i = 0;i < blankImages.length; i++){
		panel1.add(blankImages[i]);
		}
		panel1.add(aSquare);
		panel1.add(bSquare);
		panel1.add(cSquare);
		panel1.add(agentSquare);
		panel1.add(informationLabel);
		panel1.add(informationLabel2);
		panel1.add(buttonForBreadthFirstSearch);
		panel1.add(buttonForDepthFirstSearch);
		panel1.add(buttonForAStarSearch);
		panel1.add(buttonForDeepeningSearch);
		panel1.setBackground(new Color(200,10,80));
		
	}
	//SETUP THE FRAME
	public void FrameInfo(){
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(540,800);
		setLayout(new BorderLayout());
		add(panel1, BorderLayout.CENTER);
		setResizable(false);
		
	}
	
	//SET UP THE LOCATION OF EACH ELEMENT
	public void PointsInfo(){
		for(int i = 0; i < locationOfLabels.length; i++){
			if(locationX == 4) locationX = 0;
			if(i < 4){
			locationOfLabels[i] = new Point(locationX*135,0);
			}else if(i <= 7){
			locationOfLabels[i] = new Point(locationX*135, 135);
			}else if(i <= 11){
			locationOfLabels[i] = new Point(locationX*135, 270);
			}else{
			locationOfLabels[i] = new Point(locationX*135, 405);
			}
		locationX++;
		}
	}
	
	//SETUP LABELS
	public void LabelsInfo(){
		aSquare.setSize(125,125);
		bSquare.setSize(125,125);
		cSquare.setSize(125,125);
		agentSquare.setSize(125,125);
		informationLabel.setSize(600, 75);
		informationLabel.setLocation(-20, 520);
		informationLabel.setBackground(new Color(0,0,0));
		informationLabel.setForeground(Color.WHITE);
		informationLabel.setFont(informationLabel.getFont().deriveFont(18f));
		informationLabel.setText("WELCOME TO THE AGENT GAME");
		informationLabel.setHorizontalAlignment(SwingConstants.CENTER);
		informationLabel2.setSize(600, 75);
		informationLabel2.setLocation(-20, 550);
		informationLabel2.setBackground(new Color(0,0,0));
		informationLabel2.setForeground(Color.WHITE);
		informationLabel2.setFont(informationLabel.getFont().deriveFont(18f));
		informationLabel2.setText("");
		informationLabel2.setHorizontalAlignment(SwingConstants.CENTER);
		}
//METHOD THAT SWAP TO ICONS IN THE FRAME	
public void SwapSquares(int firstToMove, int secondToMove){
	
	Point locationOfFirstToMove = new Point();
	Point locationOfSecondToMove = new Point();
	locationOfFirstToMove = locationOfLabels[firstToMove];
	locationOfSecondToMove = locationOfLabels[secondToMove];
	boolean aMove = true;
	boolean bMove = true;
	boolean cMove = true;
	boolean agentMove = true;
	boolean[] blankImagesCheck = new boolean[12];
	
	
	for(int i = 0;i < blankImages.length; i++){
		if(locationOfFirstToMove.x == blankImages[i].getLocation().x && locationOfFirstToMove.y == blankImages[i].getLocation().y){
			blankImages[i].setLocation(locationOfSecondToMove);
			blankImagesCheck[i] = true;
		}
		if(locationOfSecondToMove.x == blankImages[i].getLocation().x && locationOfSecondToMove.y == blankImages[i].getLocation().y && !blankImagesCheck[i]){
			blankImages[i].setLocation(locationOfFirstToMove);
			
		}
		
	}
	
		if(locationOfFirstToMove.x == aSquare.getLocation().x && locationOfFirstToMove.y == aSquare.getLocation().y){
			aSquare.setLocation(locationOfSecondToMove);
			aMove = false;
		}
		if(locationOfFirstToMove.x == bSquare.getLocation().x && locationOfFirstToMove.y == bSquare.getLocation().y){
			bSquare.setLocation(locationOfSecondToMove);
			bMove = false;
		}
		if(locationOfFirstToMove.x == cSquare.getLocation().x && locationOfFirstToMove.y == cSquare.getLocation().y){
			cSquare.setLocation(locationOfSecondToMove);
			cMove = false;
		}
		if(locationOfFirstToMove.x == agentSquare.getLocation().x && locationOfFirstToMove.y == agentSquare.getLocation().y){
			agentSquare.setLocation(locationOfSecondToMove);
			agentMove = false;
			
			
		}
		if(locationOfSecondToMove.x == aSquare.getLocation().x && locationOfSecondToMove.y == aSquare.getLocation().y && aMove){
			aSquare.setLocation(locationOfFirstToMove);
		}
		if(locationOfSecondToMove.x == bSquare.getLocation().x && locationOfSecondToMove.y == bSquare.getLocation().y && bMove){
			bSquare.setLocation(locationOfFirstToMove);
		}
		if(locationOfSecondToMove.x == cSquare.getLocation().x && locationOfSecondToMove.y == cSquare.getLocation().y && cMove){
			cSquare.setLocation(locationOfFirstToMove);
		}
		if(locationOfSecondToMove.x == agentSquare.getLocation().x && locationOfSecondToMove.y == agentSquare.getLocation().y && agentMove){
			agentSquare.setLocation(locationOfFirstToMove);
		}	

}
//SETUP BUTTON WITH ACTIONS
	public void ButtonInfo(){
		buttonForBreadthFirstSearch.setVisible(true);
		buttonForBreadthFirstSearch.setLocation(30,600);
		buttonForBreadthFirstSearch.setText("START BFS");
		buttonForBreadthFirstSearch.setSize(100, 40);
		buttonForBreadthFirstSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startBFS = true;
				if(buttonForBreadthFirstSearch.getText() == "NEXT"){
					nextMove = true;
				}
				try {
				       Thread.sleep(10);
				    } catch(InterruptedException e1) {
				    }
				nextMove = false;
			}
			
			
		});
		
		buttonForDepthFirstSearch.setVisible(true);
		buttonForDepthFirstSearch.setLocation(160,600);
		buttonForDepthFirstSearch.setText("START DFS");
		buttonForDepthFirstSearch.setSize(100, 40);
		buttonForDepthFirstSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startDFS = true;
				
				try {
				       Thread.sleep(10);
				    } catch(InterruptedException e1) {
				    }
				nextMove = false;
			}
			
			
		});
		
		buttonForAStarSearch.setVisible(true);
		buttonForAStarSearch.setLocation(290,600);
		buttonForAStarSearch.setText("START A*");
		buttonForAStarSearch.setSize(100, 40);
		buttonForAStarSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startAstar = true;
				
				try {
				       Thread.sleep(10);
				    } catch(InterruptedException e1) {
				    }
				nextMove = false;
			}
			
			
		});
		
		buttonForDeepeningSearch.setVisible(true);
		buttonForDeepeningSearch.setLocation(410,600);
		buttonForDeepeningSearch.setText("START IDS");
		buttonForDeepeningSearch.setSize(100, 40);
		buttonForDeepeningSearch.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				startIDS = true;
				
				try {
				       Thread.sleep(10);
				    } catch(InterruptedException e1) {
				    }
				nextMove = false;
			}
			
			
		});
	
	}
	
	
	public void ChangeFrameToNewMatrix(int[][] matrixToShow){
		
		int[][] matrix = matrixToShow;
		int toMove1 = 0; 
		int toMove2 = 0;
		int carrier = 0;
		for(int i = 0; i < matrix.length;i++){
			for(int j = 0; j < matrix.length; j++){
				if(matrix[i][j] - actualMatrix[i][j] != 0 && toMove1 == 0){
					toMove1 = carrier;
				}else if(matrix[i][j] - actualMatrix[i][j] != 0){
					toMove2 = carrier;
				}
				carrier ++;
			}
		}
		
		SwapSquares(toMove1, toMove2);
		actualMatrix = matrixToShow;
		
	}
	

}
