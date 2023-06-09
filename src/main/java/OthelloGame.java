import java.util.Arrays;

public class OthelloGame {
    /**
     * Keeps Track of:
     * Curret Player
     */

    //int[][] board;
    int player;
    OthelloBoard brd;


    public OthelloGame() {
        player = 1;
        brd = new OthelloBoard();
    }

    public OthelloGame(OthelloBoard oldBoard, int oldPlayer)
    {
        brd = new OthelloBoard(oldBoard.board, oldBoard.curr1Score,oldBoard.curr2Score);

        player = oldPlayer;
    }

    void makePlay(int xPos, int yPos)
    {
//        System.out.println("Player: " + player);
        if(brd.viablePlay(xPos,yPos, player))
        {
//            System.out.println("Viable check");
            if(brd.play(xPos, yPos, player))
            {
                playerPlayCheck();

                if(brd.gameEndCheck())
                {
                    scoreFinalGame();
                }
                return;
            }
        }

        System.out.println("Play location was invalid, please choose another spot.");
    }


    /**
     * Scores and Exits the game upon completion
     */
    void scoreFinalGame()
    {
        brd.scoreGame();
        int oneScore = brd.curr1Score;
        int twoScore = brd.curr2Score;

        if(twoScore>oneScore)
        {
            System.out.println("Player Two wins!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(brd); //TODO Make neat board print
        }
        else if(oneScore>twoScore)
        {
            System.out.println("Player One wins!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(brd); //TODO Make neat board print
        }
        else
        {
            System.out.println("Tie! No Winner!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(brd); //TODO Make neat board print
        }

        System.exit(0);
    }

    /**
     * Checks if the current player is able to make a move.
     * @return True if player is able to make a play, false otherwise
     */
    void playerPlayCheck()
    {
        player = (player % 2) + 1;

        for(int i=0; i< brd.board.length; i++)
        {
            for(int j=0; j< brd.board.length; j++)
            {
                if(brd.viablePlay(i,j,player))
                {
                    //player = (player % 2) + 1;
                    return;
                }
            }
        }

        player = (player % 2) + 1;
    }
}