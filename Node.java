
import java.util.ArrayList;


public class Node {
	
	public int height;
	public double value;
	public OthelloBoard OthelloBoard;
	public OthelloBoard secOthelloBoard;
	public ArrayList<Node> children;
	public double alpha;
	public double beta;
	public String move;
	public String player;
	public boolean isMax;

	public Node(String move){
		this.move=move;
	}
	
	public OthelloBoard getOthelloBoard() {
		return OthelloBoard;
	}

	public void setOthelloBoard(OthelloBoard OthelloBoard) {
		this.OthelloBoard = OthelloBoard;
	}
	
	public boolean isMax() {
		return isMax;
	}

	public void setMax(boolean isMax) {
		this.isMax = isMax;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public ArrayList<Node> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	public double getBeta() {
		return beta;
	}

	public void setBeta(double beta) {
		this.beta = beta;
	}

	public String getMove() {
		return move;
	}

	public void setMove(String move) {
		this.move = move;
	}

}
