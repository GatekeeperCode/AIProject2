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

    /**
     * Takes a play at x,y and 1) checks if it's viable, and 2) if viable, follows the procedure
     * @param xPos X postion of placed piece
     * @param yPos Y position of placed piece
     * @return True if play followed through, false if play spot was invalid.
     */
//    boolean play(int xPos, int yPos)
//    {
//        if(!viablePlay(xPos, yPos))
//        {
//            return false;
//        }
//
//        board[xPos][yPos] = player;
//
//        for(int i=-1; i<2; i++)
//        {
//            for(int j=-1; j<2; j++)
//            {
//                int tempY = yPos + j;
//                int tempX = xPos + i;
//
//                if(board[tempX][tempY] != 0 && board[tempX][tempY] != player)
//                {
//                    boolean foundFlip = false;
//
//                    while(tempX< board.length && tempY< board.length)
//                    {
//                        int opponet = 2;
//                        if(player==2)
//                        {
//                            opponet=1;
//                        }
//
//                        if(board[tempX][tempY]==opponet)
//                        {
//                            tempX += i;
//                            tempY += j;
//                        }
//                        else if(board[tempX][tempY]==player)
//                        {
//                            tempX -= i;
//                            tempY -= j;
//                            foundFlip = true;
//                            break;
//                        }
//                    }
//
//                    if(foundFlip)
//                    {
//                        int opponet = 2;
//                        if(player==2)
//                        {
//                            opponet=1;
//                        }
//
//                        while(board[tempX][tempY]!=player)
//                        {
//                            board[tempX][tempY]=opponet;
//                            tempX-=i;
//                            tempY-=j;
//                        }
//                    }
//                }
//            }
//        }
//
//        if(gameEndCheck())
//        {
//            scoreGame();
//        }
//
//        System.out.println("Player " + player + "'s turn");
//        System.out.println(Arrays.deepToString(board)); //TODO Make neat board print
//
//        return true;
//    }

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