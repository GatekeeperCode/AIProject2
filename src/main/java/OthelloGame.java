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
        if(brd.viablePlay(xPos,yPos, player))
        {
            brd.play(xPos, yPos, player);

            if(player==1)
            {
                player = 2;
            }
            else
            {
                player=1;
            }

            if(!gameEndCheck())
            {
                scoreFinalGame();
            }
        }
        else
        {
            System.out.println("Play location was invalid, please choose another spot.");
        }
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
            System.out.println(Arrays.deepToString(brd.board)); //TODO Make neat board print
        }
        else if(oneScore>twoScore)
        {
            System.out.println("Player One wins!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(Arrays.deepToString(brd.board)); //TODO Make neat board print
        }
        else
        {
            System.out.println("Tie! No Winner!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(Arrays.deepToString(brd.board)); //TODO Make neat board print
        }

        System.exit(0);
    }

    /**
     * Checks if the game ends here
     * @return True if the game ends, false otherwise
     */
    boolean gameEndCheck()
    {
        boolean ablePlay = false;

        if(player==1)
        {
            player=2;
        }
        else
        {
            player=1;
        }

        for(int i=0; i<brd.board.length; i++)
        {
            for(int j=0; j<brd.board[i].length; j++)
            {
                if(brd.board[i][j]==0)
                {
                    if(!ablePlay && brd.viablePlay(i,j,player))
                    {
                        ablePlay=true;
                    }
                }
            }
        }

        if(!ablePlay)
        {
            scoreFinalGame();
        }

        return ablePlay;
    }
}