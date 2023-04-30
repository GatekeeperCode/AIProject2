import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    //This is an int to keep it consistent with the Othello game board.
    private int playerID;

    private int heuristicType;

    private int maxSearchDepth;

    private boolean isHuman;

    //Depends on how we want to do this one

    public Player(int playerID, boolean human ,int heuristicType, int searchDepth) {
        this.playerID = playerID;
        this.isHuman = human;
        this.heuristicType = heuristicType;
        this.maxSearchDepth = searchDepth;
    }

    public Player(Player p) {
        this.playerID = p.getID();
        this.isHuman = false;
        this.heuristicType = p.heuristicType;
        this.maxSearchDepth = p.maxSearchDepth;
    }

    public int getID() {
        return playerID;
    }

    public int getHeuristicType(){
        return heuristicType;
    }

    public int getMaxSearchDepth(){
        return maxSearchDepth;
    }

    public boolean isHuman(){
        return isHuman;
    }
    public int[] getPlay(OthelloGame og){
        if(isHuman){
            return humanInput(og);
        }
        else{
            return getMiniMaxPlay(og);
        }
    }

    /**
     * The main function of this AI. Determines what the best move is for winning long-term.
     * Uses a minimax algorithm to determine the most optimal play.
     * @param og - The current state of the game-board
     * @return - The tuple of integers for this player's next move.
     */
    public int[] getMiniMaxPlay(OthelloGame og) {

        //Duplicated in miniMaxHeuristic but both structures needed here too
        ArrayList<int[]> possibles = findMoves(og.brd, playerID);
        ArrayList<OthelloBoard> boards = new ArrayList<>();

        for(int i = 0; i < possibles.size(); i++){
            OthelloBoard newBd = new OthelloBoard(og.brd);
            newBd.play(possibles.get(i)[0], possibles.get(i)[1], playerID);
            boards.add(newBd);
        }

        int[] startalbe = {-100, 100};

        int bestPlayValue = miniMaxHeuristic(boards.get(0), 0, playerID, maxSearchDepth, startalbe);
        int bestPlayIndex = 0;
        for(int i  = 1; i < boards.size(); i++){
            int val = miniMaxHeuristic(boards.get(i), 0, playerID, maxSearchDepth, startalbe);
            if (val > bestPlayValue){
                bestPlayValue = val;
                bestPlayIndex = i;
            }
        }
        return possibles.get(bestPlayIndex);
    }

    /**
     * Takes a board state and finds the best possible score for the player
     * given perfect play (checked to maxDepth moves)
     *
     * @param ob The Board State: OthelloBoard
     * @param depth The current depth (For recursion)
     * @param player The current player
     * @param maxDepth The maximum search depth
     * @return the best score for the current player to date
     */
    public int miniMaxHeuristic(OthelloBoard ob, int depth, int player, int maxDepth, int[] alphabeta) {
        if(depth >= maxDepth || ob.gameEndCheck()){
            return ob.getHeuristicEstimate(player, heuristicType);
        }

        ArrayList<int[]> possibles = findMoves(ob, player);
        ArrayList<OthelloBoard> boards = new ArrayList<>();

        for (int[] pos : possibles) {
            OthelloBoard newBd = new OthelloBoard(ob);
            newBd.play(pos[0], pos[1], player);
            boards.add(newBd);
        }

        if(possibles.size() == 0){
            return miniMaxHeuristic(ob, depth +1, Player.getOpponent(playerID), maxDepth, alphabeta);
        }

        // TODO Add some discernment about which board to take first (The highest scoring one?)
        int[] albe = {alphabeta[0], alphabeta[1]};

        int maxValue = miniMaxHeuristic(boards.get(0), depth +1, Player.getOpponent(playerID), maxDepth, albe);
        int minIndex = 0;
        albe = calculateAB(alphabeta, maxValue, (player%2)+1);
        if (albe == null) {
            return boards.get(minIndex).getHeuristicEstimate(player, heuristicType);
        }

        for(int i = 1; i < possibles.size(); i++){
            int val = miniMaxHeuristic(boards.get(i), depth +1, Player.getOpponent(playerID), maxDepth, albe);

            albe = calculateAB(albe, val, Player.getOpponent(playerID));
            if (albe == null) {
                break;
            }

            if (val > maxValue){
                maxValue = val;
                minIndex = i;
            }
        }

        return boards.get(minIndex).getHeuristicEstimate(player, heuristicType);
    }

    /**
     * Assist method for the minimaxHeuristic() method; performs calculations needed for
     * Alpha-Beta Pruning.
     *
     * Method influenced by other Alpha-Beta implementation, as seen at:
     * https://www3.ntu.edu.sg/home/ehchua/programming/java/javagame_tictactoe_ai.html#:~:text=Reference%3A%20Wiki%20%22Alpha%2Dbeta,But%20node%20B%20is%204.
     *
     * @param alphabeta - Array of alpha-beta values [alpha, beta]
     * @param val - The heuristic value of that world
     * @param playerID - The player responsible for the turn of that world
     * @return - Returns an int[] of alpha-beta values if not to be skipped, otherwise return null
     */
    public int[] calculateAB(int[] alphabeta, int val, int playerID) {
        if(playerID == 2){
            val = -val;
        }

        int[] albe = {alphabeta[0], alphabeta[1]};

        if (val > albe[0] && playerID == 1) {
            albe[0] = val;
        }
        if (val < albe[1] && playerID == 2) {
            albe[1] = val;
        }
        if (albe[0] >= albe[1]) {
            return null;
        }
        return albe;
    }

    /**
     * Checks all possible placements on the board for this turn.
     * @param ob - The current state of the board.
     * @return - All possible (viable) moves that this player can take during the next move.
     */
    public static ArrayList<int[]> findMoves(OthelloBoard ob, int player) {
        ArrayList<int[]> viableMoves = new ArrayList<>();
        for (int i=0; i<ob.board.length; i++) {
            for (int j=0; j<ob.board[i].length; j++) {
                if (ob.viablePlay(i, j, player)) {
                    int[] temp = {i, j};
                    viableMoves.add(temp);
                }
            }
        }
        return viableMoves;
    }

    /**
     * Asks the user to input the next play for that player
     *
     * @param og The othello board to interact with
     * @return an array with 2 entries [x,y] mapping to the plays they want to make
     */
    public int[] humanInput(OthelloGame og){
        while(true) {
            try {
                Scanner scn = new Scanner(System.in);
                System.out.println(og.brd);
                System.out.println("what's your move? in the form x,y");
                System.out.print(": ");
                String[] strSplit = scn.nextLine().split(",");
                System.out.println("I");
                if(!og.brd.viablePlay(Integer.parseInt(strSplit[0])-1, Integer.parseInt(strSplit[1])-1, playerID)){
                    System.out.println("XEption");
                    throw new Exception("Invalid play");
                }
                System.out.println("HI");
                return new int[]{Integer.parseInt(strSplit[0])-1, Integer.parseInt(strSplit[1]) - 1};
            }
            catch(Exception e){
                System.out.println("Invalid Play, Try again");
            }
        }
    }

    public static int getOpponent(Player player){
        return getOpponent(player.playerID);
    }

    public static int getOpponent(int player){
        return (player % 2) + 1;
    }

}
