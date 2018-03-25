/**
 * @author Rahul Singh
 */

import java.util.ArrayList;
import java.util.Scanner;


class Othello {
	
	OthelloBoard board;
	public Othello() {
		this.board = new OthelloBoard(8, 8);
		board.setCurr(board.players[0]);
	}
	
	/**
	 * @param current Player
	 * makes move for agent using AlphaBeta Search
	 */
	public void doAlphaBeta(String currPlayer, int depth){
		AlphaBetaPruning max = new AlphaBetaPruning(currPlayer, board, depth);
		board.makeMove(max.doAlphaBeta());
	}

	public void PlayOthello(){
		String move;
		int counter = 0;
		int [] count = new int[2];
		count[0] = count[1] = 0;
		boolean forceQuit = false;
		ArrayList<String> plays;
		System.out.println("1.) Enter Depth for AlphaBeta Pruning: ");
		Scanner sc = new Scanner(System.in);
		int depth = Integer.parseInt(sc.nextLine());
		board.printOthelloBoard();
		do{
			counter++;
			if(!board.hasLegalMoves()){
				System.out.println("No valid moves available. Turn Skipped!");
				if(board.termCondition()){
					String msg = board.winner();
					System.out.println(msg);
					break;
				}
				else{
					board.setNext();
				}
			}
			plays = board.getLegalMoves();
			if(board.getCurr().equals(board.players[0])){
				System.out.println("Available moves :" + plays);
				do{
					System.out.println("The User is Black. Enter a valid move(i,j):");
					move = sc.nextLine();
				}while(!board.validateInput(move));
				//move = b.generateRandomMove(moves);
				System.out.println("User chose to move Black: "+move);
				board.makeMove(move);
				board.setPlayer();
			}
			else if(board.getCurr().equals(board.players[1])){
				System.out.println("Legal Moves :" + plays);
				doAlphaBeta(board.getCurr(),depth);
				board.setPlayer();
			}
			System.out.println("After move: ");
			board.printOthelloBoard();
			if(board.termCondition()){
				String msg = board.winner();
				System.out.println(msg);
				break;
			}
			if(counter%5 == 0){
				System.out.println("Do you want to quit?(Y/N)");
				String quit = sc.nextLine();
				forceQuit = quit.equalsIgnoreCase("Y");
			}
		}while(!forceQuit);
		sc.close();
	}

}

public class PlayOthello {
	public static void main(String[] args) {
		double t1 = System.currentTimeMillis();
		System.out.println("Start Playing Othello - ");
		Othello newGame = new Othello();
		newGame.PlayOthello();
		double t2 = System.currentTimeMillis();
		System.out.println("Total time taken: "+((t2-t1)/1000)+" seconds" );
	}

}
