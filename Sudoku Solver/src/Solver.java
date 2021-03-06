import java.awt.event.KeyEvent;
import edu.princeton.cs.introcs.StdDraw;
//
public class Solver {

	private static void printBoard(int[][] board) {
		for (int i = 0; i <9; ++i) {
			for (int j = 0; j < 9; ++j) {
				if(j%3 == 2) {
					System.out.print(board[i][j] + " | ");
				}
				else {
					System.out.print(board[i][j] + " ");
				}

				if(j>0 && j%8 == 0) {
					if(i%3 ==2) {
						System.out.println("\n" + "-----------------------");
					}
					else {
						System.out.print("\n");
					}
				}
			}
		}
	}

	private static void drawBoard(int[][] board) {
		for (int i = 0; i <9; ++i) {
			for (int j = 0; j < 9; ++j) {
				double y = i/9.0 + 0.05;
				double x = j/9.0 + 0.05;
				String draw = "" + board[8-i][j];
				StdDraw.text(x, y, draw);
			}
		}
	}

	private static boolean rowDupe(int[][] board, int number, int row) {
		for (int i=0; i<9; ++i) {
			if(board[row][i] == number) {
				return true;
			}
		}
		return false;
	}

	private static boolean colDupe(int[][] board, int number, int col) {
		for (int i=0; i<9; ++i) {
			if(board[i][col] == number) {
				return true;
			}
		}
		return false;
	}

	private static boolean gridDupe(int[][] board, int number, int row, int col) {
		int localGridRow = row - row % 3;
		int localGridColumn = col - col % 3;
		for (int i = localGridRow; i < localGridRow + 3; ++i) {
			for (int j = localGridColumn; j < localGridColumn + 3; ++j) {
				if (board[i][j] == number) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean placeable(int[][] board, int number, int row, int col) {
		if (rowDupe(board, number, row) == false) {
			if(colDupe(board, number, col) == false) {
				if(gridDupe(board, number, row, col) == false) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean solve(int[][] board) {
		for (int i = 0; i < board.length; ++i) {
			for (int j = 0; j < board[0].length; ++j) {
				if(board[i][j] == 0) {
					for(int k = 1; k<=9; ++k) {
						if(placeable(board, k, i, j) == true) {
							board[i][j] = k;

							if(solve(board) == true) {
								return true;
							}
							else {
								board[i][j] = 0;
							}
						}
					}
					return false;
				}
			}
		}
		return true;
	}

	private static void drawOutline() {
		StdDraw.setPenRadius(0.008);
		StdDraw.line(0, 0, 1, 0);
		StdDraw.line(0, 0.33333, 1, 0.33333);
		StdDraw.line(1, 0, 1, 1);
		StdDraw.line(0, 0.66666, 1, 0.66666);
		StdDraw.line(1, 1, 0, 1);
		StdDraw.line(0.33333, 0, 0.33333,1);
		StdDraw.line(0, 1, 0, 0);
		StdDraw.line(0.66666, 0, 0.66666,1);

		StdDraw.setPenRadius(0.004);
		StdDraw.line(0, 0.11111, 1, 0.11111);
		StdDraw.line(0, 0.22222, 1, 0.22222);
		StdDraw.line(0, 0.44444, 1, 0.44444);
		StdDraw.line(0, 0.55555, 1, 0.55555);
		StdDraw.line(0, 0.77777, 1, 0.77777);
		StdDraw.line(0, 0.88888, 1, 0.88888);
		StdDraw.line(0.11111, 0, 0.11111,1);
		StdDraw.line(0.22222, 0, 0.22222,1);
		StdDraw.line(0.44444, 0, 0.44444,1);
		StdDraw.line(0.55555, 0, 0.55555,1);
		StdDraw.line(0.77777, 0, 0.77777,1);
		StdDraw.line(0.88888, 0, 0.88888,1);
	}

	public static double[] getMousePosition() {
		double [] mouseXY = new double [2];
		mouseXY[0] = StdDraw.mouseX();
		mouseXY[1] = StdDraw.mouseY();
		return mouseXY;
	}

	public static void mousePositionConversion(int number) {
		double[] positions = getMousePosition();
		double x = 0.0;
		double y = 0.0;
		for(int i = 0; i< 9 ; ++i) {
			if(positions[0] >= i/9.0 && positions[0] < (i+1)/9.0) {
				x = i/9.0 + 0.05;
			}
			if(positions[1] >= i/9.0 && positions[1] < (i+1)/9.0) {
				y = i/9.0 + 0.05;
			}

		}
		StdDraw.text(x, y, "" + number);
	}

	public static int[] mouseToBoard() {
		int[] boardXY = new int[2];
		double[] positions = getMousePosition();
		for(int i = 0; i<9; ++i) {
			if(positions[0] >= i/9.0 && positions[0] < (i+1)/9.0) {
				boardXY[1] = i;
			}
			if(positions[1] >= i/9.0 && positions[1] < (i+1)/9.0) {
				boardXY[0] = 8-i;
			}
		}

		return boardXY;
	}

	public static void main(String[] args) {
		drawOutline();
		int[][]board = new int[9][9];

		while (!StdDraw.isKeyPressed(KeyEvent.VK_ESCAPE)) {

			while(!StdDraw.hasNextKeyTyped()) {
				StdDraw.pause(10);
			}
			char gotkey = StdDraw.nextKeyTyped();
			int [] positions = mouseToBoard();
			if(gotkey == '1') {
				mousePositionConversion(1);
				board[positions[0]][positions[1]] = 1;
			}
			if(gotkey == '2') {
				mousePositionConversion(2);
				board[positions[0]][positions[1]] = 2;
			}
			if(gotkey == '3') {
				mousePositionConversion(3);
				board[positions[0]][positions[1]] = 3;
			}
			if(gotkey == '4') {
				mousePositionConversion(4);
				board[positions[0]][positions[1]] = 4;
			}
			if(gotkey == '5') {
				mousePositionConversion(5);
				board[positions[0]][positions[1]] = 5;
			}
			if(gotkey == '6') {
				mousePositionConversion(6);
				board[positions[0]][positions[1]] = 6;
			}
			if(gotkey == '7') {
				mousePositionConversion(7);
				board[positions[0]][positions[1]] = 7;
			}
			if(gotkey == '8') {
				mousePositionConversion(8);
				board[positions[0]][positions[1]] = 8;
			}
			if(gotkey == '9') {
				mousePositionConversion(9);
				board[positions[0]][positions[1]] = 9;
			}
			if(gotkey == KeyEvent.VK_DELETE) {
				double[] positionHolder = getMousePosition();
				double x = 0.0;
				double y = 0.0;
				for(int i = 0; i< 9 ; ++i) {
					if(positionHolder[0] >= i/9.0 && positionHolder[0] < (i+1)/9.0) {
						x = i/9.0 + 0.05;
					}
					if(positionHolder[1] >= i/9.0 && positionHolder[1] < (i+1)/9.0) {
						y = i/9.0 + 0.05;
					}
				}
				StdDraw.setPenColor(255, 255, 255);
				StdDraw.filledCircle(x, y, 0.03);
				board[positions[0]][positions[1]] = 0;
				StdDraw.setPenColor(0, 0, 0);
			}
		}

		System.out.println("Inputed Board:");
		printBoard(board);

		if(solve(board)) {
			System.out.println("Successful :)");
		}else {
			System.out.println("Unsolvable :(");
		}

		drawBoard(board);
		printBoard(board);

		//		}

	}	
}




