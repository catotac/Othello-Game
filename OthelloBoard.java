/**
 * @author Rahul Singh
 */

import java.util.ArrayList;
import java.util.HashMap;

public class OthelloBoard {
	
	
	int row, col;
	String currPlayer;
	String nextPlayer;
	String[] players;
	HashMap<String,String> pla;
	HashMap<String, String> OthelloBoard;
	ArrayList<String> legalMoves;

	public OthelloBoard(){}
	
	public OthelloBoard(int r, int c){
		this.row = r;
		this.col = c;
		this.OthelloBoard = new HashMap<String, String>();
		this.players = new String[2];
		this.pla = new HashMap<>();
		this.players[0] = "user";
		this.players[1] = "agent";
		pla.put("default",".");
		pla.put(players[0],"B");
		pla.put(players[1],"W");
		this.setCurr(players[0]);

		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				OthelloBoard.put(genIndex(i,j),".");
			}
		}
		OthelloBoard.put(genIndex(3, 3), "W");
		OthelloBoard.put(genIndex(4, 4), "W");

		OthelloBoard.put(genIndex(4, 3), "B");
		OthelloBoard.put(genIndex(3, 4), "B");
	}

	public void setCurr(String p1){
		this.currPlayer = p1;
		setNext();
	}
	
	
	public void setNext(){
		if(currPlayer.equalsIgnoreCase(players[0]))
			this.nextPlayer = players[1];
		else
			this.nextPlayer = players[0];
	}


	public String getCurr(){
		return currPlayer;
	}
	
	
	public String getNext(){
		return nextPlayer;
	}

	public void setPlayer(){
		setCurr(nextPlayer);
	}

	/**
	 * Prints current Board state
	 */
	public void printOthelloBoard(){
		System.out.println("Current State: ");
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				System.out.print(OthelloBoard.get(genIndex(i, j))+" ");
			}
			System.out.println();
		}
	}

	/**
	 * @return Available moves
	 */
	public ArrayList<String> getLegalMoves(){
		ArrayList<String> moves = new ArrayList<>();
		String index;
		for(int r = 0; r < row; r++){
			for(int c = 0; c < col; c++){
				index = genIndex(r, c);
				if(isFree(index) && isAdj(index) && 
						getPositions(index,pla.get(currPlayer), pla.get(nextPlayer)).size() > 0){
					moves.add(index);
				}
			}
		}
		legalMoves = new ArrayList<>();
		legalMoves.clear();
		legalMoves.addAll(moves);
		return moves;	
	}

	public boolean isFree(String move){
		return this.inBounds(move) && OthelloBoard.get(move).equals(pla.get("default"));
	}

	public boolean inBounds(String move){
		return inBounds(getRow(move),getCol(move));
	}

	
	public boolean inBounds(int r, int c){
		for (String key : OthelloBoard.keySet()) {
			if(key.equals(genIndex(r, c)))
				return true;
		}
		return false;
	}

	public boolean isAdj(String pos){
		int r=getRow(pos),c=getCol(pos);
		String enemy= nextPlayer;
		String index;
		for(int i=-1;i<=1;i++){
			for(int j=-1;j<=1;j++){
				if(i==0&&j==0) continue;
				index = genIndex(r+i, c+j);
				if(inBounds(index) && OthelloBoard.get(index).equals(pla.get(enemy)))
					return true;
			}
		}
		return false;
	}

	public ArrayList<String> getPositions(String pos, String marker, String enemy){
		ArrayList<String> positions = new ArrayList<>();
		int r = getRow(pos), c = getCol(pos);

		for(int i=1;i<row;i++){ 
			if (!inBounds(r+i,c+i) || (i==1 && !areMarkersEqual(enemy,r+i,c+i)) ||
				getPositionsHelper(r+i,c+i,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r+i,c+i)))) 
				break;
		}
		for(int i=1;i<row;i++){ 
			if (!inBounds(r-i,c-i) || (i==1 && !areMarkersEqual(enemy,r-i,c-i)) || 
				getPositionsHelper(r-i,c-i,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r+i,c+i)))) 
				break;
		}
		for(int i=1;i<row;i++){ 
			if (!inBounds(r+i,c-i) || (i==1 && !areMarkersEqual(enemy,r+i,c-i)) || 
				getPositionsHelper(r+i,c-i,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r+i,c-i)))) 
				break;
		}
		for(int i=1;i<row;i++){ 
			if (!inBounds(r-i,c+i) || (i==1 && !areMarkersEqual(enemy,r-i,c+i)) || 
				getPositionsHelper(r-i,c+i,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r-i,c+i)))) 
				break;
		}
		for(int i=1;i<col;i++){	
			if (!inBounds(r,c-i) || (i==1 && !areMarkersEqual(enemy,r,c-i)) || 
				getPositionsHelper(r,c-i,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r,c-i)))) 
				break;
		}
		for(int i=1;i<col;i++){ 
			if (!inBounds(r,c+i) || (i==1 && !areMarkersEqual(enemy,r,c+i)) || 
				getPositionsHelper(r,c+i,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r,c+i)))) 
				break;
		}
		for(int i=1;i<row;i++){ 
			if(!inBounds(r-i,c) || (i==1 && !areMarkersEqual(enemy,r-i,c)) || 
				getPositionsHelper(r-i,c,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r-i,c)))) 
				break; 
		}
		for(int i=1;i<row;i++){ 
			if(!inBounds(r+i,c) || (i==1 && !areMarkersEqual(enemy,r+i,c)) || 
				getPositionsHelper(r+i,c,marker,positions) || !enemy.equals(OthelloBoard.get(genIndex(r+i, c))))
				break; 
		}
		return positions;
	}
	
	public boolean areMarkersEqual(String marker, int r, int c){
		return marker.equals(OthelloBoard.get(genIndex(r, c)));
	}

	public boolean getPositionsHelper(int r,int c, String marker, ArrayList<String> v){
		String index = genIndex(r,c); 
		boolean retval = OthelloBoard.get(index).equals(marker);
		if(retval)	
			v.add(index);
		return retval;
	}
	

	public boolean hasLegalMoves(){
		if(getLegalMoves().size() > 0)
			return true;
		else return false;
	}
	
	/**
	 * @return score for each player
	 */
	public int[] getScore(){
		int countUser = 0, countAgent = 0;
		int[] result = new int[2];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				if(OthelloBoard.get(genIndex(i, j)).equals(pla.get(players[0])))
					countUser++;
				else if(OthelloBoard.get(genIndex(i, j)).equals(pla.get(players[1])))
					countAgent++;
			}
		}
		result[0] = countUser;
		result[1] = countAgent;
		return result;
	}
	
	/**
	 * @return Returns winner
	 */
	public String winner(){
		String winner = "";
		int[] scores = getScore();
		int maxScore = Math.max(scores[0], scores[1]);
		if(maxScore == scores[0] && maxScore == scores[1])
			winner = "It's a tie!!!";
		else if(maxScore == scores[0])
			winner = "Congratulations!! "+players[0] +" won the game. Scores: user:"+scores[0]+" agent:"+scores[1];
		else if(maxScore == scores[1])
			winner = "Sorry!! "+players[1] +" won the game. Scores: user:"+scores[0]+" agent:"+scores[1];
		return winner;
	}
	
	/**
	 * @param OthelloBoard
	 * Sets OthelloBoard
	 */
	public void setB(HashMap<String,String> OthelloBoard) {
		this.OthelloBoard = OthelloBoard;
	}
	
	/**
	 * @param i
	 */
	public void setRow(int i){
		this.row = i;
	}
	
	/**
	 * @param j
	 */
	public void setCol(int j){
		this.col = j;
	}
	
	/**
	 * @param markers
	 */
	public void setMarkers(HashMap<String, String> markers){
		this.pla = markers;
	}
	
	/**
	 * @return
	 */
	public HashMap<String,String> getB() {
		return OthelloBoard;
	}
	
	/**
	 * Clones a OthelloBoard
	 */
	public OthelloBoard clone(){
		OthelloBoard b = new OthelloBoard(this.row, this.col);
		b.setB((HashMap<String,String>) this.getB().clone());
		b.setRow(this.row);
		b.setCol(this.col);
		b.setMarkers(this.pla);
		b.setCurr(this.currPlayer);
		return b;
	}
	
	/**
	 * @return check for terminal condition
	 */
	public boolean termCondition(){
		OthelloBoard _b = (OthelloBoard) this.clone();
		_b.setNext();
		return isFull() || (!hasLegalMoves() && !_b.hasLegalMoves());
	}
	
	/**
	 * @return if OthelloBoard is full
	 */
	public boolean isFull(){
		int count = 0;
		for (String key : OthelloBoard.keySet()) {
			if(OthelloBoard.get(key).equals("o") || OthelloBoard.get(key).equals("x"))
				count++;
		}
		return count == row*col;
	}
	
	/**
	 * @param move
	 */
	public void makeMove(String move){
		String[] indices = move.split(",");
		int r, c;
		r = Integer.parseInt(indices[0].trim());
		c = Integer.parseInt(indices[1].trim());
		OthelloBoard.put(genIndex(r, c), pla.get(currPlayer));
		flips(move);
	}
	
	/**
	 * @param move
	 * Flips all disks as a result of the move
	 */
	public void flips(String move){
		String currMarker = pla.get(getCurr());
		String enemyMarker = pla.get(getNext());
		ArrayList<String> pos = getAllPieces(move, currMarker, enemyMarker);
		for (String indx : pos) {
			flipDisk(indx, currMarker);
		}
	}
	
	public ArrayList<String> captureVDDisks(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int k = i+1; k < row; k++) {
			if(getMarker(k, j).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, k, j) && temp.size() > 0){
				return temp;
			}
			else if(k == row -1){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, k, j)){
				temp.add(genIndex(k, j));
			}
		}
		return temp;
	}
	
	public ArrayList<String> captureVUDisks(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int k = i-1; k >= 0; k--) {
			if(getMarker(k, j).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, k, j) && temp.size() > 0){
				return temp;
			}
			else if(k == 0){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, k, j)){
				temp.add(genIndex(k, j));
			}
		}
		return temp;
	}
	
	public ArrayList<String> horizontalRight(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int k = j+1; k < col; k++) {
			if(getMarker(i, k).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, i, k) && temp.size() > 0){
				return temp;
			}
			else if(k == col - 1){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, i, k)){
				temp.add(genIndex(i, k));
			}
		}
		return temp;
	}


	public ArrayList<String> horizontalLeft(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int k = j-1; k >= 0; k--) {
			if(getMarker(i, k).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, i, k) && temp.size() > 0){
				return temp;
			}
			else if(k == 0){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, i, k)){
				temp.add(genIndex(i, k));
			}
		}
		return temp;
	}
	
	public ArrayList<String> captureTRDisks(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int m=i-1, k=j+1; m>=0 && k<col; m--,k++) {
			if(getMarker(m, k).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, m, k) && temp.size() > 0){
				return temp;
			}
			else if(m==0 || k==col-1){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, m, k)){
				temp.add(genIndex(m, k));
			}
		}
		return temp;
	}

	public ArrayList<String> bottomLeft(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int m=i+1, k=j-1; m<row && k>=0; m++,k--) {
			if(getMarker(m, k).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, m, k) && temp.size() > 0){
				return temp;
			}
			else if(m==row-1 || k==0){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, m, k)){
				temp.add(genIndex(m, k));
			}
		}
		return temp;
	}
	
	public ArrayList<String> captureTLDisks(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int m=i-1, k=j-1; m>=0 && k>=0; m--,k--) {
			if(getMarker(m, k).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, m, k) && temp.size() > 0){
				return temp;
			}
			else if(m==0 || k==0){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, m, k)){
				temp.add(genIndex(m, k));
			}
		}
		return temp;
	}
	
	public ArrayList<String> bottomRight(String currMarker, String enemyMarker, int i, int j){
		ArrayList<String> temp = new ArrayList<>();
		for (int m=i+1, k=j+1; m<row && k<col; m++,k++) {
			if(getMarker(m, k).equals(pla.get("default"))){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			else if(areMarkersEqual(currMarker, m, k) && temp.size() > 0){
				return temp;
			}
			else if(m==row-1 || k==col-1){
				temp.clear();
				temp = new ArrayList<>();
				return temp;
			}
			if(areMarkersEqual(enemyMarker, m, k)){
				temp.add(genIndex(m, k));
			}
		}
		return temp;
	}
	
	/**
	 * @return Combines all pieces captured
	 */
	public ArrayList<String> getAllPieces(String move, String currMarker, String enemyMarker){
		ArrayList<String> allPos = new ArrayList<>();
		int i = getRow(move), j = getCol(move);
		allPos.addAll(bottomLeft(currMarker, enemyMarker, i, j));
		allPos.addAll(bottomRight(currMarker, enemyMarker, i, j));
		allPos.addAll(captureTLDisks(currMarker, enemyMarker, i, j));
		allPos.addAll(captureTRDisks(currMarker, enemyMarker, i, j));
		allPos.addAll(horizontalLeft(currMarker, enemyMarker, i, j));
		allPos.addAll(horizontalRight(currMarker, enemyMarker, i, j));
		allPos.addAll(captureVUDisks(currMarker, enemyMarker, i, j));
		allPos.addAll(captureVDDisks(currMarker, enemyMarker, i, j));
		return allPos;
	}
	
	/**
	 * Flips all captured disks
	 */
	public void flipDisk(String index, String currMarker){
		OthelloBoard.put(index, currMarker);
	}
	
	public String getMarker(String move){
		int i = getRow(move), j =getCol(move);
		return getMarker(i, j);
	}
	
	public String getMarker(int i, int j){
		return OthelloBoard.get(genIndex(i, j));
	}
	

	public boolean validateInput(String move){
		if(legalMoves.contains(move))
			return true;
		else return false;
	}
	

	public String[] getPlayers(){
		return players;
	}
	
	/**
	 * @param row
	 * @param col
	 * @return
	 */
	public String genIndex(int row, int col){
		return genIndex(String.valueOf(row),String.valueOf(col));
	}

	public String genIndex(String row, String col){
		return row + "," + col;
	}

	public int getRow(String move){
		return Integer.parseInt(move.substring(0,move.indexOf(",")));
	}

	public int getCol(String move){
		int comma = move.indexOf(",");
		return Integer.parseInt(move.substring(comma+1,move.length()));
	}
	
}
