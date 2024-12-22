
import java.util.ArrayList;

public class Bag {
    ArrayList<String> bag;

    public Bag() {
        this.bag = new ArrayList<String>();
        String[] letters = Letters();
        for(int i = 0; i<letters.length; i++ ){
            if (letters[i].equals("A")){
                for (int a = 0; a < 5; a++) bag.add("A");
            }
            else if (OneLetters().contains(letters[i])) {
                bag.add(letters[i]);
            }
            else if (TwoLetters().contains(letters[i])){
                for (int a = 0; a < 2; a++){
                    bag.add(letters[i]);;
                }
            }
            else if (ThreeLetters().contains(letters[i])){
                for (int a = 0; a < 3; a++){
                    bag.add(letters[i]);;
                }
            }
            else if (FourLetters().contains(letters[i])){
                for (int a = 0; a < 4; a++){
                    bag.add(letters[i]);
                } 
            }
            else{
                for (int a = 0; a < 6; a++) {
                bag.add("E");
            }
            }
        }   
    }
    /**Creates a array of the alphabet
     * @return the letters - String[]
     */
    private String[] Letters(){
        String[] a =  {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Qu", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
        return a; 
    }
    /**Creates and array of all the letters that appear in the bag once
     * @return the letters - String[]
     */
    private String OneLetters(){
        return "F, J, K, Qu, V, W, X, Z";  
    }
    /**Creates and array of all the letters that appear in the bag twice
     * @return the letters - String[]
     */
    private String TwoLetters(){
        return "B, C, G, H, R, Y";  
    }

    /**Creates and array of all the letters that appear in the bag three times
     * @return the letters - String[]
     */
    private String ThreeLetters(){
        return "D, L, M, N, P, S, U";  
    }

    /**Creates and array of all the letters that appear in the bag four times
     * @return the letters - String[]
     */
    private String FourLetters(){
        return "I, O, T";  
    }

    /**Gets the size of the bag
     * @return the size - int
     */
    public int size(){
        return bag.size();
    }

   /** Creates a random 7 letter hand
     * @return the hand - ArrayList<String> 
     */
    public ArrayList<String> randomHand(){
        ArrayList<String> hand = new ArrayList<String>();
        for (int a = 0; a < 7; a++){
            hand.add(Draw1Letter());
        }
        return hand;
    }

    /** Draws one letter from the bag
     * @return the latter - String
     */
    public String Draw1Letter(){
        while(bag.size()>1){
        int size = bag.size()-1;
        int index = (int)(Math.random()*(size));
        String a = bag.get(index);
        bag.remove(index);    
        return a;
        }
        return"";
    }

    /**Adds a letter to the bag
     * @param letter repressents a letter
     */
    public void add1Letter(String letter){
        bag.add(letter);    
    }

    
    public String toString() {
        return bag.toString();
    }

    public ArrayList<String> getBag() {
        return bag;
    }

    public void setBag(ArrayList<String> bag) {
        this.bag = bag;
    }

    
}    
   
