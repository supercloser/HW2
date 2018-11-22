import java.util.Random;
import java.util.Scanner;

public class GameofLife { //First of - Life SUCKS, The game made by David Zherdenovsky.
	static int size =0;
	static int generations =0;
	static int genPick = 0;
	static boolean[][] nextBoard = new boolean[size][size];

	public static void main(String[] args) {
		Scanner scn = new Scanner(System.in);
		System.out.println("Please enter the board size you wish:");
		size = scn.nextInt();
		copiedBoard(firstBoard(size));
		convertToBinar(nextBoard);
		System.out.println("This is your board, now watch how it changes!");
		System.out.println("but before we change it, we should know how many generations you want it to pass...\nPlease enter the number of generations you want it to pass:");
		genPick = scn.nextInt();
		generationAdapter();
		
	}
	//==================creating the first board in the game==============
	public static boolean[][] firstBoard (int size){
		Random random = new Random();
		boolean[][] firstBoard = new boolean [size][size];
		for (int i=0;i<size;i++) {
			for(int j =0; j<size; j++) {
				firstBoard[i][j] = random.nextBoolean();
			}
		}
		generations++;
		return firstBoard;
	}
	//================End of the first-board creation======================
	//=====Converting the boolean board to a binary board and prints it====
	public static void convertToBinar (boolean[][] board) {
		String convertedBoard ="";
		for (boolean[] row: board) {
			for(boolean cell:row) {
				if(cell) {
					convertedBoard +="1 ";
				}else {
					convertedBoard +="0 ";
				}
			}convertedBoard +="\n";
			
		}
		System.out.print(convertedBoard + "\n");
	}
	//============End of the Conversion to binary===========================
	//=========== Creating a copy of a given board =============================
	public static void copiedBoard (boolean[][] oldboard){ // we start by copying the old board to a new one to not to harm the old one by changing it...
		for(int i=0; i<oldboard.length; i++) {
			  for(int j=0; j<oldboard[i].length; j++) {
			    nextBoard=oldboard;
			  }
		}
		
	} //===========End of copy=========================
		
		//=============== now we modify the new board we copied with the rules of the game=========
	public static void newBoard (boolean[][] oldBoard){
		
		for(int i=0; i<nextBoard.length; i++) {
			for(int j=0; j<nextBoard[i].length; j++) {
				int numOfNeighbors = countNeighbors(oldBoard, i, j); //checks for neighbors for the cell
				// under or overpopulation, cell dies
	            if ((numOfNeighbors < 2) || (numOfNeighbors > 3)) {
	                nextBoard[i][j] = false;
	            }

	            // cell lives on to next generation
	            if (numOfNeighbors == 2) {
	                nextBoard[i][j] = oldBoard[i][j];
	            }

	            // cell either stays alive, or is born
	            if (numOfNeighbors == 3) {
	                nextBoard[i][j] = true;
	            }
			}
		}generations++;
		
		
		
	}// ==================End of newGen method====================================
	//====================Here we create a method which counts the number of neighbors each cell has===================
	public static int countNeighbors (boolean[][]board, int i, int j) {
		int numOfNeighbors = 0;
		
		//=========creating 3x3 grid around the [i][j] to find the neighbors===================
		for (int k=-1; k<=1; k++) { // k will represent the row
			for(int h=-1; h<=1;h++) { // h will represent the col
				int ik = i+k;
				int jh = j+k;
				if (!(ik == i && jh == j)) {
					//==============fix OOB Exceptions============
					if(ik<0) { // if k < 0 then k + board.length-1
						ik += board.length;
					}
					if(jh<0) { // if h < 0 then h + board.length-1
						jh += board.length;
					}
					if(ik >= board.length) { // if k >= board.length then k - board.length
						ik -= board.length;
					}
					if(jh>= board.length) { // if h >= board.length then h - boarrd.length
						jh -= board.length;
					}
					if(board[ik][jh]) {
						numOfNeighbors++;
					}
				}
				
			}
		}
		
		return numOfNeighbors;
		
	}
	public static void generationAdapter () {
		while (generations<=genPick) {
			newBoard(nextBoard);
			System.out.println("Generation: "+(generations-1));
			convertToBinar(nextBoard);
			
		}
		
	}

}
