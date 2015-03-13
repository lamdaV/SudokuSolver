import java.io.BufferedReader;
import java.io.FileReader;


public class SudokuSolver {
	
	public static void main(String[] args) {
// Empty Board for testing.
//		int[][] board = {
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0},
//				{0, 0, 0, 0, 0, 0, 0, 0, 0}};;
		int[][] board = populateBoard("resource/sudokuBoard.txt");
		System.out.println("My Sudoku Board:");
		printBoard(board);
		long startTime = System.currentTimeMillis();
		if (solve(board)) {
			System.out.println("Found a Solution:");
			printBoard(board);
		} else {
			System.out.println("No Solution Found.");
		}
		
		long endTime = System.currentTimeMillis();
		
		System.out.println("It took " + (endTime - startTime) + " ms to complete!");
	}
	
	public static void printBoard(int[][] board) {
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				System.out.print(board[r][c] + " ");
			}
			System.out.println();
		}
	}
	
	public static int[][] populateBoard(String fileName) {
		final int BOARD_WIDTH = 9;
		final int BOARD_HEIGHT = 9;
		int[][] board = null;
		
		try {
			String parser = " ";
			String currentLine = null;
			String[] currentLineValues = null;
			FileReader fileInput = new FileReader(fileName);
			BufferedReader imageReader = new BufferedReader(fileInput);			
			board = new int[BOARD_HEIGHT][BOARD_WIDTH];

			for (int r = 0; r < board.length; r++) {
				// Get line of numbers and spaces.
				currentLine = imageReader.readLine();

				// Eliminates currentLine's space and obtain a array of String
				// integers.
				currentLineValues = currentLine.split(parser);

				for (int c = 0; c < board[r].length; c++) {
					// Convert the String integers into integer values to be
					// stored in level.
					board[r][c] = Integer.parseInt(currentLineValues[c]);
				}
			}
			imageReader.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(0);
		}
		return board;
	}

	
	public static boolean solve(int[][] board) {
		int[][] status = new int[board.length][board[0].length];
		
		for (int r = 0; r < 9; r++) {
			for (int c = 0; c < 9; c++) {
				if (board[r][c] > 0) {
					status[r][c] = 2;
				} else {
					status[r][c] = 0;
				}
			}
		}
		return solve(board, status, 0, 0);
	}
	
	public static boolean solve(int[][] board, int[][] status, int r, int c) {
		if (r == 9) {
			int count = 0;
			
			for (int x = 0; x < 9; x++) {
				for (int y = 0; y < 9; y++) {
					if (status[x][y] > 0) {
						count++;
					} 
				}
			}
			
			if (count == 81) {
				return true;
			} else {
				return false;
			}
		}
		
		if (status[r][c] >= 1) {
			int nextR = r;
			int nextC = c + 1;
			if (nextC == 9) {
				nextR = r + 1;
				nextC = 0;
			}
			return solve(board, status, nextR, nextC);
		}
		
		boolean[] used = new boolean[9];
		for (int i = 0; i < 9; i++) {
			if (status[r][i] >= 1) {
				used[board[r][i] - 1] = true;
			}
		}
		
		for (int i = 0; i < 9; i++) {
			if (status[i][c] >= 1) {
				used[board[i][c] - 1] = true;
			}
		}
		
		for (int i = r - (r % 3); i < r - (r % 3) + 3; i++) {
			for (int j = c - (c % 3); j < c - (c % 3) + 3; j++) {
				if (status[i][j] >= 1) {
					used[board[i][j] - 1] = true;
				}
			}
		}
		
		for (int i = 0; i < used.length; i++) {
			if (!(used[i])) {
				status[r][c] = 1;
				board[r][c] = i + 1;
				int nextR = r;
				int nextC = c + 1;
				if (nextC == 9) {
					nextR = r + 1;
					nextC = 0;
				}
				if (solve(board, status, nextR, nextC)) {
					return true;
				}
				
				for (int m = 0; m < 9; m++) {
					for (int n = 0; n < 9; n++) {
						if (m > r || (m == r && n >= c)) {
							if (status[m][n] == 1) {
								status[m][n] = 0;
								board[m][n] = 0;
							}
						}
					}
				}
			}
		}
		return false;
	}
}
