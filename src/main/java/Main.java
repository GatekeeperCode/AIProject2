import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args)
    {

        //Specify the players based on the inputs that the user provided.
        Player p1 = new Player(1, false, 1, 12);
        Player p2 = new Player(2, false, 3, 6);
        OthelloGame og = new OthelloGame();

        if(!(args.length > 0 && args[0].equals("testing"))) {
            int p1SearchDepth = 1;
            int p1Heuristic = 1;
            boolean p1Player;
            Scanner scn = new Scanner(System.in);
            System.out.println("Will Player 1 be a bot or a human? (Type \"bot\" or \"human\")");

            //Decide if P1 is a bot or human player.
            while (true) {
                String[] p1Decide = scn.nextLine().split("\\s+");
                if (p1Decide[0].toUpperCase().equals("HUMAN")) {
                    p1Player = true;
                    break;
                } else if (p1Decide[0].toUpperCase().equals("BOT")) {
                    p1Player = false;
                    break;
                } else {
                    System.out.println("Invalid entry. Try \"bot\" or \"human\".");
                }
            }

            //If not a human, get heuristic and search depth
            if (!p1Player) {
                //HEURISTIC
                System.out.println("Specify the heuristic for Player 1. (Type 1, 2, or 3)");
                while (true) {
                    int chosenHeuristic = 0;
                    String[] p1Decide = scn.nextLine().split("\\s+");
                    try {
                        chosenHeuristic = Integer.parseInt(p1Decide[0]);
                        if (chosenHeuristic > 0 && chosenHeuristic < 4) {
                            p1Heuristic = chosenHeuristic;
                            break;
                        } else {
                            throw new Exception("not_valid");
                        }
                    } catch (Exception e) {
                        System.out.println("There was a problem with your input. Try 1, 2, or 3.");
                    }
                }

                //SEARCH DEPTH
                System.out.println("Specify the search depth (at least 1) for Player 1.\n(Note: Above 12 may increase time between turns significantly.)");
                while (true) {
                    int chosenDepth = 0;
                    String[] p1Decide = scn.nextLine().split("\\s+");
                    try {
                        chosenDepth = Integer.parseInt(p1Decide[0]);
                        if (chosenDepth > 0) {
                            p1SearchDepth = chosenDepth;
                            break;
                        } else {
                            throw new Exception("not_valid");
                        }
                    } catch (Exception e) {
                        System.out.println("There was a problem with your input. Try a number greater than 0.");
                    }
                }
            }

            int p2SearchDepth = 1;
            int p2Heuristic = 1;
            boolean p2Player;
            System.out.println("Will Player 2 be a bot or a human? (Type \"bot\" or \"human\")");

            //Decide whether P2 is a human or bot player.
            while (true) {
                String[] p2Decide = scn.nextLine().split("\\s+");
                if (p2Decide[0].toUpperCase().equals("HUMAN")) {
                    p2Player = true;
                    break;
                } else if (p2Decide[0].toUpperCase().equals("BOT")) {
                    p2Player = false;
                    break;
                } else {
                    System.out.println("Invalid entry. Try \"bot\" or \"human\".");
                }
            }

            //If not a human, get heuristic and search depth
            if (!p2Player) {
                //HEURISTIC
                System.out.println("Specify the heuristic for Player 2. (Type 1, 2, or 3)");
                while (true) {
                    int chosenHeuristic = 0;
                    String[] p2Decide = scn.nextLine().split("\\s+");
                    try {
                        chosenHeuristic = Integer.parseInt(p2Decide[0]);
                        if (chosenHeuristic > 0 && chosenHeuristic < 4) {
                            p2Heuristic = chosenHeuristic;
                            break;
                        } else {
                            throw new Exception("not_valid");
                        }
                    } catch (Exception e) {
                        System.out.println("There was a problem with your input. Try 1, 2, or 3.");
                    }
                }

                //SEARCH DEPTH
                System.out.println("Specify the search depth (at least 1) for Player 2.\n(Note: Above 12 may increase time between turns significantly.)");
                while (true) {
                    int chosenDepth = 0;
                    String[] p2Decide = scn.nextLine().split("\\s+");
                    try {
                        chosenDepth = Integer.parseInt(p2Decide[0]);
                        if (chosenDepth > 0) {
                            p2SearchDepth = chosenDepth;
                            break;
                        } else {
                            throw new Exception("not_valid");
                        }
                    } catch (Exception e) {
                        System.out.println("There was a problem with your input. Try a number greater than 0.");
                    }
                }
            }

            //Specify the players based on the inputs that the user provided.
            p1 = new Player(1, p1Player, p1Heuristic, p1SearchDepth);
            p2 = new Player(2, p2Player, p2Heuristic, p2SearchDepth);
            og = new OthelloGame();
        }

        //Run the game
        while(true){
//          System.out.println("Turn: " + og.player);
            System.out.println("Player " + og.player + "'s turn");
            if(og.player == 1){
                int[] play = p1.getPlay(og);
                System.out.println(Arrays.toString(play));

                og.makePlay(play[0], play[1]);

            }
            else{
                int[] play = p2.getPlay(og);
                System.out.println(Arrays.toString(play));
                og.makePlay(play[0], play[1]);

            }
            System.out.println(og.brd);
        }
    }
}
