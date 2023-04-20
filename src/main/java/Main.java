public class Main {
    public static void main(String[] args)
    {
        System.out.println("Hello World!");

        Player p1  = new Player(1);
        Player p2  = new Player(2);
        OthelloGame og = new OthelloGame();

        //System.out.println(og.brd.toString());
        boolean p1Turn = true;
        while(true){
            System.out.println("Round");

            if(p1Turn){
                int[] play = p1.humanInput(og);
                og.makePlay(play[0], play[1]);
            }
            else{
                int[] play = p2.humanInput(og);
                og.makePlay(play[0], play[1]);
            }
            p1Turn = !p1Turn;
        }
    }
}
