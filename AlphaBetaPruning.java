/**
 * @author Rahul Singh
 */

import java.util.ArrayList;

public class AlphaBetaPruning {

	Node alphaBeta;
	int depthMax;
	String player;
	

	/**
	 */
	public AlphaBetaPruning(String player, OthelloBoard passedOthelloBoard,int maxDepth) {
		String position = passedOthelloBoard.genIndex(0, 0);
		this.player = player;
		this.depthMax = maxDepth;
		alphaBeta = new Node(position);
		alphaBeta.setOthelloBoard(passedOthelloBoard);
	}

	/**
	 * Simulates AlphaBetaSearch and generates the tree
	 * @return best move
	 */
	public String doAlphaBeta() {
		treeWrap(player,depthMax);
		String bestMove = bestNode(alphaBeta);
		System.out.println(player.toUpperCase()+" chose: "+bestMove);
		return bestMove;
	}
	
	/**
	 * returns best move by doing alpha beta search
	 * @param root
	 * @return 
	 */
	public String bestNode(Node root) {
		String move = null;
		double resultValue = Double.NEGATIVE_INFINITY;
		for (Node child : root.getChildren()) {
			double value = minValue(child, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
			if (value > resultValue) {
				move = child.getMove();
				resultValue = value;
			}
		}
		return move;
	}

	public double maxValue(Node currNode, double alpha, double beta) {
		if (currNode.getOthelloBoard().termCondition() || currNode.getHeight() == depthMax){
			double val = evaluateFunc(currNode).getValue();
			return val;
		}
		double value = Double.NEGATIVE_INFINITY;
		for (Node child : currNode.getChildren()) {
			value = Math.max(value, minValue(child, alpha, beta));
			if (value >= beta)
				return value;
			alpha = Math.max(alpha, value);
		}
		return value;
	}


	public double minValue(Node currNode, double alpha, double beta) {
		if (currNode.getOthelloBoard().termCondition() || currNode.getHeight() == depthMax){
			double val = evaluateFunc(currNode).getValue();
			return val;
		}
		double value = Double.POSITIVE_INFINITY;
		for (Node child : currNode.getChildren()) {
			value = Math.min(value, maxValue(child, alpha, beta));
			if (value <= alpha)
				return value;
			beta = Math.min(beta, value);
		}
		return value;
	}

	/**
	 * Wrapper method for tree generation starting from root
	 * @param player
	 * @param maxDepth
	 */
	public void treeWrap(String player, int maxDepth) {
		alphaBeta.height=0;
		alphaBeta.player = player;
		alphaBeta.isMax = true;
		alphaBeta.children = new ArrayList<Node>();

		Node tree = genTree(alphaBeta, maxDepth);
		alphaBeta = tree;
	}

	/**
	 * @param node
	 * @param maxDepth
	 * @return root of the generated tree
	 */
	public Node genTree(Node node, int maxDepth){
		if(node.height > maxDepth){
			return node;
		}
		if(node.height <= maxDepth){
			if(node.height%2==0 && node.height>1){
				node.player="agent";
			}
			if(node.height%2!=0){
				node.player = "user";
			}
			ArrayList<String> posMoves = node.getOthelloBoard().getLegalMoves();
			for(int i=0; i<posMoves.size(); ++i){
				Node nextNode = new Node(posMoves.get(i));
				nextNode.height = node.height + 1;
				OthelloBoard nodeOthelloBoard = new OthelloBoard();

				nodeOthelloBoard = node.getOthelloBoard().clone();
				nodeOthelloBoard.setCurr(node.getOthelloBoard().getNext());
				nextNode.player = nodeOthelloBoard.getCurr();
				nodeOthelloBoard.makeMove(nextNode.getMove());
				nextNode.setOthelloBoard(nodeOthelloBoard);
				nextNode.children = new ArrayList<Node>();
				Node kidNode = genTree(nextNode, maxDepth);
				node.children.add(kidNode);
			}
			return node;
		}

		return node;
	}

	 
	public String evaluateWrap(Node node){
		node = evaluateFunc(node);
		for(int i=0; i<node.children.size(); ++i){
			if(node.children.get(i).value==node.value){
				return node.children.get(i).move;
			}
		}
		return null;
	}

	/**
	 * evaluates nodes in the tree
	 * @param node
	 * @return node with best move
	 */
	public Node evaluateFunc(Node node){
		if(!node.children.isEmpty()){
			double[] values = new double[node.children.size()];
			for(int i=0; i<node.children.size(); ++i){
				Node evalNode = evaluateFunc(node.children.get(i));
				values[i]=evalNode.value;
			}
			if(node.isMax){
				double max = values[0];
				for ( int j = 1; j < values.length; ++j) {
					if ( values[j] > max) {
						max = values[j];
					}
				}
				node.value=max;
				return node;
			}else if(!node.isMax) {
				double min = values[0];
				for ( int i = 1; i < values.length; ++i) {
					if ( values[i] < min) {
						min = values[i];
					}
				}
				node.value=min;
				return node;
			}
		} else {
			int indx = node.player.equals("user") ? 0 : node.player.equals("agent") ? 1 : -1;
			node.value = node.OthelloBoard.getScore()[indx];
			return node;
		}
		return null;
	}
}
