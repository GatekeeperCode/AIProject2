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

    /**
     * Takes a play at x,y and 1) checks if it's viable, and 2) if viable, follows the procedure
     * @param xPos X postion of placed piece
     * @param yPos Y position of placed piece
     * @return True if play followed through, false if play spot was invalid.
     */
    boolean play(int xPos, int yPos, int player)
    {
        if(!viablePlay(xPos, yPos, player))
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

        scoreGame();
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

//    /**
//     * Checks if the game ends here
//     * @return True if the game ends, false otherwise
//     */
//    boolean gameEndCheck()
//    {
//        boolean ablePlay = false;
//
//        for(int i=0; i<board.length; i++)
//        {
//            for(int j=0; j<board[i].length; j++)
//            {
//                if(board[i][j]==0)
//                {
//                    if(!ablePlay && viablePlay(i,j, 1) && viablePlay(i,j, 2))
//                    {
//                        ablePlay=true;
//                    }
//                }
//            }
//        }
//
//        return ablePlay;
//    }

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
