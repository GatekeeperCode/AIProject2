import java.util.ArrayList;
import java.util.Arrays;

public class OthelloBoard {
    /**
     * Keeps Track of:
     * Board State
     * If a play is viable
     * Score
     */


    int[][] board;
    int curr1Score;
    int curr2Score;



    public OthelloBoard()
    {
        board = new int[][]
                {       {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 2, 1, 0, 0, 0},
                        {0, 0, 0, 1, 2, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0}};

        curr1Score = 0;
        curr2Score = 0;
    }

    public OthelloBoard(int[][] oldBoard, int score1, int score2)
    {
        board = new int[8][8];

        for(int i=0; i<oldBoard.length; i++)
        {
            for(int j=0; j<oldBoard[i].length; j++)
            {
                board[i][j] = oldBoard[i][j];
            }
        }

        curr2Score = score2;
        curr1Score = score1;
    }

    public OthelloBoard(OthelloBoard ob)
    {
        board = new int[8][8];

        for(int i=0; i<ob.board.length; i++)
        {
            for(int j=0; j<ob.board[i].length; j++)
            {
                board[i][j] = ob.board[i][j];
            }
        }

        curr2Score = ob.curr2Score;
        curr1Score = ob.curr1Score;
    }

    /**
     * Tests a play at x,y and 1) checks if it's viable, and 2) if viable, follows the procedure
     * @param xPos X postion of placed piece
     * @param yPos Y position of placed piece
     * @return True if play followed through, false if play spot was invalid.
     */
    boolean play(int xPos, int yPos, int player)
    {
        if(!viablePlay(xPos, yPos, player))
        {
            System.out.println("Bad hello");
            return false;
        }

        board[yPos][xPos] = player;

        for(int i=-1; i<2; i++)
        {
            for(int j=-1; j<2; j++)
            {
                int tempY = yPos + j;
                int tempX = xPos + i;
                if(tempX < 0 || tempY < 0 || tempY >7 || tempX > 7){
                    continue;
                }


                if(board[tempY][tempX] != 0 && board[tempY][tempX] != player)
                {
                    boolean foundFlip = false;

                    while(tempX < board.length && tempY< board.length && tempX>0 && tempY>0)
                    {
                        int opponet = 2;
                        if(player==2)
                        {
                            opponet=1;
                        }
//                        System.out.println(tempX + " " + tempY);

                        if(board[tempY][tempX]==opponet)
                        {
                            tempX += i;
                            tempY += j;
                        }
                        else if(board[tempY][tempX]==player)
                        {
                            tempX -= i;
                            tempY -= j;
                            foundFlip = true;
                            break;
                        }
                        else
                        {
                            tempX += i;
                            tempY += j;
                        }
                    }
//                    System.out.println("Escaped while 1");

                    if(foundFlip)
                    {
                        int opponet = 2;
                        if(player==2)
                        {
                            opponet=1;
                        }

                        while(board[tempY][tempX]!=player)
                        {
                            board[tempY][tempX]=player;
                            tempX-=i;
                            tempY-=j;
                        }
//                        System.out.println("Escaped while 2");

                    }
                }
            }
        }


        scoreGame();
        //System.out.println("Player " + player + "'s turn");
//        System.out.println(toString()); //TODO Make neat board print

        return true;
    }

    /**
     * Scores and Exits the game upon completion
     */
    void scoreGame()
    {
        int oneScore = 0;
        int twoScore = 0;

        for (int[] xCol : board) {
            for (int i : xCol) {
                if (i == 1) {
                    oneScore++;
                }
                if (i == 2) {
                    twoScore++;
                }
            }
        }

        curr1Score = oneScore;
        curr2Score = twoScore;
    }

    /**
     * Checks if the game ends here
     * @return True if the game ends, false otherwise
     */
    boolean gameEndCheck()
    {
        boolean ablePlay = false;

        for(int i=0; i<board.length; i++)
        {
            for(int j=0; j<board[i].length; j++)
            {
                if(board[i][j]==0)
                {
                    if(!ablePlay && viablePlay(i,j, 1) && viablePlay(i,j, 2))
                    {
                        ablePlay=true;
                    }
                }
            }
        }

//        System.out.println("Hello");
        return ablePlay;
    }

    /**
     * Checks if move is valid
     * @param xPos X position of play location
     * @param yPos Y Position of play location
     * @return True if you can play, false otherwise
     */
    boolean viablePlay(int xPos, int yPos, int player)
    {
        int tempX;
        int tempY;

        for(int i=-1; i<2; i++)
        {
            for(int j=-1; j<2; j++)
            {
                tempY = yPos+j;
                tempX = xPos+i;
                if(tempX < 0 || tempY < 0 || tempY >7 || tempX > 7){
                    continue;
                }

                boolean clearPlay = true;

                if((i==0 && j==0)||(board[tempY][tempX]==0 || board[tempY][tempX]==player))
                {
                    clearPlay=false;
                }
                else
                {
                    tempX+=i;
                    tempY+=j;
                }

                while(clearPlay && tempX<board.length && tempY<board.length && tempX>0 && tempY>0)
                {
                    int opponet = 2;
                    if(player==2)
                    {
                        opponet=1;
                    }

                    if(board[tempY][tempX]==player)
                    {
                        return true;
                    }
                    else if(board[tempY][tempX]==0)
                    {
                        clearPlay = false;
                    }
                    else
                    {
                        tempX+=i;
                        tempY+=j;
                    }
                }
            }
        }

        return false;
    }


    /**
     * Very Basic heuristic for the board, doesn't take into account piece position
     * @param playerID The player making the move
     * @return the heuristic value they would have for the board
     */
    public int getHeuristic(int playerID){
        int score = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(board[i][j] == playerID){
                    score++;
                }
                else if (board[i][j] == 0) {

                }
                else {
                    score--;
                }
            }
        }
        return score;
    }

    public String toString() {
        String boardPrint = "";
        for (int i=-1; i<board.length+1; i++) {
            if (i == -1 || i == board.length) {
                for (int h = 0; h<board[board.length-1].length; h++) {
                    boardPrint += " ___";
                }
                boardPrint += "\n";
            }
            else {
                for (int j=0; j<board[i].length; j++) {
                    boardPrint += "| " + board[i][j] + " ";
                }
                boardPrint += "|\n";
            }
        }
        return boardPrint;
    }
}
