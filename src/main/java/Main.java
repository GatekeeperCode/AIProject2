import java.util.Arrays;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Hello World!");

        Player p1  = new Player(1);
        Player p2  = new Player(2);
        OthelloGame og = new OthelloGame();

        while(true){
//            System.out.println("Turn: " + og.player);
            System.out.println("Player " + og.player + "'s turn");
            if(og.player == 1){
                int[] play = p1.getMiniMaxPlay(og);
                System.out.println(Arrays.toString(play));

                og.makePlay(play[0], play[1]);

            }
            else{
                int[] play = p2.getMiniMaxPlay(og);
                System.out.println(Arrays.toString(play));
                og.makePlay(play[0], play[1]);

            }
            System.out.println(og.brd);
        }
    }
}
