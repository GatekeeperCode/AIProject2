import java.util.ArrayList;
import java.util.Arrays;

public class OthelloGame {

    int[][] board;
    int player;


    public OthelloGame() {
        player = 1;

        board = new int[][]
                {       {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 2, 1, 0, 0, 0},
                        {0, 0, 0, 1, 2, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}};
    }

    public OthelloGame(int[][] oldBoard, int oldPlayer)
    {
        for(int i=0; i<oldBoard.length; i++)
        {
            for(int j=0; j<oldBoard[i].length; j++)
            {
                board[i][j] = oldBoard[i][j];
            }
        }

        player = oldPlayer;
    }

    /**
     * Takes a play at x,y and 1) checks if it's viable, and 2) if viable, follows the procedure
     * @param xPos X postion of placed piece
     * @param yPos Y position of placed piece
     * @return True if play followed through, false if play spot was invalid.
     */
    boolean play(int xPos, int yPos)
    {
        if(!viablePlay(xPos, yPos))
        {
            return false;
        }

        board[xPos][yPos] = player;

        for(int i=-1; i<2; i++)
        {
            for(int j=-1; j<2; j++)
            {
                int tempY = yPos + j;
                int tempX = xPos + i;

                if(board[tempX][tempY] != 0 && board[tempX][tempY] != player)
                {
                    boolean foundFlip = false;

                    while(tempX< board.length && tempY< board.length)
                    {
                        int opponet = 2;
                        if(player==2)
                        {
                            opponet=1;
                        }

                        if(board[tempX][tempY]==opponet)
                        {
                            tempX += i;
                            tempY += j;
                        }
                        else if(board[tempX][tempY]==player)
                        {
                            tempX -= i;
                            tempY -= j;
                            foundFlip = true;
                            break;
                        }
                    }

                    if(foundFlip)
                    {
                        int opponet = 2;
                        if(player==2)
                        {
                            opponet=1;
                        }

                        while(board[tempX][tempY]!=player)
                        {
                            board[tempX][tempY]=opponet;
                            tempX-=i;
                            tempY-=j;
                        }
                    }
                }
            }
        }

        if(gameEndCheck())
        {
            scoreGame();
        }

        System.out.println("Player " + player + "'s turn");
        System.out.println(Arrays.deepToString(board)); //TODO Make neat board print

        return true;
    }

    /**
     * Scores and Exits the game upon completion
     */
    void scoreGame()
    {
        int oneScore = 0;
        int twoScore = 0;

        for(int i=0; i< board.length; i++)
        {
            for(int j=0; j<board[i].length; j++)
            {
                if(board[i][j]==1) {oneScore++;}
                if(board[i][j]==2) {twoScore++;}
            }
        }

        if(twoScore>oneScore)
        {
            System.out.println("Player Two wins!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(Arrays.deepToString(board)); //TODO Make neat board print
        }
        else if(oneScore>twoScore)
        {
            System.out.println("Player One wins!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(Arrays.deepToString(board)); //TODO Make neat board print
        }
        else
        {
            System.out.println("Tie! No Winner!");
            System.out.println("Player One Score: " + oneScore);
            System.out.println("Player Two Score: " + twoScore);
            System.out.println();
            System.out.println(Arrays.deepToString(board)); //TODO Make neat board print
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

        for(int i=0; i<board.length; i++)
        {
            for(int j=0; j<board[i].length; j++)
            {
                if(board[i][j]==0)
                {
                    if(!ablePlay && viablePlay(i,j))
                    {
                        ablePlay=true;
                    }
                }
            }
        }

        if(ablePlay)
        {
            return true;
        }
        return false;
    }

    /**
     * Checks if move is valid
     * @param xPos X position of play location
     * @param yPos Y Position of play location
     * @return True if you can play, false otherwise
     */
    boolean viablePlay(int xPos, int yPos)
    {
        int tempX = xPos;
        int tempY = yPos;

        for(int i=-1; i<2; i++)
        {
            for(int j=-1; j<2; j++)
            {
                tempY = yPos+j;
                tempX = xPos+i;
//                if(tempX < 0 || tempY < 0 || tempY >7 || tempX > 7){
//                    continue;
//                }

                boolean clearPlay = true;
                boolean opPiecePresent = false;

                if(i==0 && j==0)
                {
                    clearPlay=false;
                }

                while(clearPlay)
                {
                    int opponet = 2;
                    if(player==2)
                    {
                        opponet=1;
                    }

                    if(!opPiecePresent && board[tempX][tempY]==opponet)
                    {
                        tempX+=i;
                        tempY+=j;
                        opPiecePresent = true;
                    }
                    else if(opPiecePresent && board[tempX][tempY]==player)
                    {
                        return true;
                    }
                    else if(board[tempX][tempY]==0)
                    {
                        clearPlay = false;
                    }
                }
            }
        }

        return false;
    }
}
