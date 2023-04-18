import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Player {
    //This is an int to keep it consistent with the Othello game board.
    private int playerID;
    private int score;

    //Depends on how we want to do this one
    private int heuristic;
    public Player(int playerID) {
        this.playerID = playerID;
        score = 0;
        heuristic = 0;
    }

    public Player(Player p) {
        this.playerID = p.getPlayerID();
        this.score = p.getScore();
        this.heuristic = p.getHeuristic();
    }

    public int getPlayerID() {
        return playerID;
    }

    public void setPlayerID(int playerID) {
        this.playerID = playerID;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(int heuristic) {
        this.heuristic = heuristic;
    }

    /**
     * Checks all possible placements on the board for this turn.
     * @param og - The current state of the board.
     * @return - All possible (viable) moves that this player can take during the next move.
     */
    public ArrayList<int[]> findMoves(OthelloGame og) {
        ArrayList<int[]> viableMoves = new ArrayList<int[]>();
        for (int i=0; i<og.board.length; i++) {
            for (int j=0; j<og.board[i].length; j++) {
                if (og.viablePlay(i, j)) {
                    int[] temp = {i, j};
                    viableMoves.add(temp);
                }
            }
        }
        return viableMoves;
    }

    /**
     * The main function of this AI. Determines what the best move is for winning long-term.
     * Uses a minimax algorithm to determine the most optimal play.
     * @param og - The current state of the game-board
     * @return - The tuple of integers for this player's next move.
     */
    public int[] miniMax(OthelloGame og) {
        ArrayList<int[]> possibles = this.findMoves(og);
        //This is the part that will take some doing.
        return null;
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
                System.out.println(Arrays.deepToString(og.board));
                System.out.println("what's your move? in the form x,y");
                System.out.print(": ");
                String[] strSplit = scn.nextLine().split(",");
                return new int[]{Integer.parseInt(strSplit[0]), Integer.parseInt(strSplit[1])};
            }
            catch(Exception e){
                System.out.println("Invalid Play, Try again");
            }
        }
    }


}
