import java.util.ArrayList;

public class Player {
    public int numberOfPlayers;
    public Bag letters;
    private ArrayList<String> hand;
    private int score;

    public Player(Bag bag) {
        numberOfPlayers = 0;
        letters = bag;
        this.hand = letters.randomHand();
        this.score = 0;
    }


    /**Adds points to the players score 
     * @param a repressents points - int
     */
    public void addscore(int a){
        this.score +=a;
    }
   

    public int getScore() {
        return score;
    }

    

    public ArrayList<String> getHand() {
        return hand;
    }

}
