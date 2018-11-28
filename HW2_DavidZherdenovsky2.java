import java.util.Random;
import java.util.Scanner;

public class HW2_DavidZherdenovsky2 {
	static int size=0;
	static int numOfChanges=0;
	static boolean[][] board = new boolean[size][size];
	static boolean[][] firstBoard = new boolean[size][size];
    //===========================================main===========================================
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("WELCOME TO THE GAME OF LIFE\nPlease enter the board size you wish, remember this board is X by X");
		size = scanner.nextInt();
		firstBoard = firstBoard(size); // creating the first boolean board
		printBoard(firstBoard);
		System.out.println("This is your first board, how many times whould you like it to change?");
		numOfChanges = scanner.nextInt();
		print();
		scanner.close();
	}
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ End of main ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	//=============== Creating the first board =================================================
	public static boolean[][] firstBoard (int x){ // taking the board size from the user and assign it as x
		Random random = new Random();
		boolean[][] firstBoard = new boolean [x][x]; // creating new boolean array with x as size
		for (int i =0; i < x; i ++) {
			for (int j =0; j < x; j ++) {
				firstBoard[i][j] = random.nextBoolean(); // filling the boolean array with random booleans
			}
		}
		return firstBoard;
	}
	//^^^^^^^^^^^ End of creation of the first board ^^^^^^^^
	
	//=========== Printing the board given ==================
	public static void printBoard(boolean[][] boolBoard) {
		String printBoard = "";
		for (boolean[] row: boolBoard) {
			for (boolean cell:row) {
				if (cell) {
					printBoard += "1 ";
				}else {
					printBoard += "0 ";
				}
			}printBoard += "\n";
		}
		System.out.println(printBoard);
	}
	//^^^^^^^^^^^^^^^^^ End of printing method ^^^^^^^^^^^^^^^
	
	//================ Creating a copy of one board to another================
	public static void copy (boolean[][]original ,boolean[][] paste) {
		for (int i = 0; i < original.length; i++) {
			for (int j = 0; j < original[i].length; j++) {
				paste[i][j] = original[i][j];
			}
		}
	}
	//^^^^^^^^^^^^^^^^^^ End of copy ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	//================== Counting neighbors =================================
	public static int countNeighbors (boolean[][]someboard , int i, int j) {
		int numOfNeighbors = 0;
		for (int x=-1; x<=1; x++) { // k will represent the row
			for(int y=-1; y<=1;y++) { // h will represent the col
				int ix = i+x;
				int jy = j+y;
				if (!(ix == i && jy == j)) {
					//==============fix OOB Exceptions============
					if(ix<0) { // if k < 0 then k + board.length-1
						ix += someboard.length;
					}
					if(jy<0) { // if h < 0 then h + board.length-1
						jy += someboard.length;
					}
					if(ix >= someboard.length) { // if k >= board.length then k - board.length
						ix -= someboard.length;
					}
					if(jy >= someboard.length) { // if h >= board.length then h - boarrd.length
						jy -= someboard.length;
					}
					if(someboard[ix][jy]) {
						numOfNeighbors++;
					}
				}
				
			}
		}
		
		return numOfNeighbors;
	}
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ End of counting ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	//=========================== New board creation =============================================
	public static boolean setCell(int x, int y, boolean[][] gameBoardCopy, boolean[][] gameBoardOriginal) {
		int liveNeighbors = countNeighbors(gameBoardOriginal, x, y);
		
		
		if (liveNeighbors > 4 || liveNeighbors < 2) {// game rules : if the cell has more then 4 neighbors or less the cell dies
			gameBoardCopy[x][y] = false;
		} else if (liveNeighbors == 3) {// if the cell has 3 living neighbors he's reborn or stays alive.
			gameBoardCopy[x][y] = true;
		}

		return gameBoardCopy[x][y];

	}
	//^^^^^^^^^^^^^^^^^^^^^^^^^ End of new board creation ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

	//========= checking if the next board is identical to the current board ======================
	public static boolean[][] checkBoard(boolean[][] arr) {
		boolean[][] arrCopy = new boolean[arr.length][arr.length];
		copy(arr, arrCopy);
		// making a copy to the game board array , to use for the update
		//(checking neighbors in the game board and update in the copy).
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[i].length; j++) {
				arrCopy[i][j] = setCell(i, j, arrCopy, arr);
			}
		}
		return arrCopy;

	}
	//^^^^^^^^^^^^^^^^^^^ End of checking ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	//=================== Comparing between 2 arrays ==============================================
	public static boolean compare (boolean[][] arr1, boolean[][] arr2) {
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr1[i].length; j++) {
                if (!(arr1[i][j] == arr2[i][j])) {
                	return false;
                }
                   
       }
    }
         return true;
  }
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ End of compare ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
	//============================ Print loop ======================================================
	public static void print () {
		boolean[][] forCompare = new boolean[size][size];
		int timesChanged =0;
		for (int i=1; i<=numOfChanges; i++) {
			timesChanged++;
			copy(firstBoard,forCompare);
			firstBoard = checkBoard(firstBoard);
			if (compare(firstBoard,forCompare)) {
				System.out.println("This is the last board and it won't change anymore,\nSave your time and try a new game!");
				break;
			}
			System.out.println("Step " +i);
			printBoard(firstBoard);
			System.out.println();
		}
		System.out.println("Done!");
		printBoard(firstBoard);
		System.out.println("The board has changed "+timesChanged+" times!");
	}
	//^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ End of print ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
	
}
