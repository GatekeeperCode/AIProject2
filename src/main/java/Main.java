import java.util.Arrays;

public class Main {
    public static void main(String[] args)
    {
        System.out.println("Hello World!");

        Player p1  = new Player(1, false, 1, 12);
        Player p2  = new Player(2, false, 1,  12);
        OthelloGame og = new OthelloGame();

        while(true){
//            System.out.println("Turn: " + og.player);
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
