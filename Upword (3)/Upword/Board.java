import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;

public class Board {
    private HashMap<String, Space> board;
    private ArrayList<String> wordbank;
    private HashMap<String,Integer> HorizontalWord;
    private HashMap<String,Integer> VerticalWord;


    public Board() {
       this.board = new HashMap<String, Space>();
       for(int i = 1; i<9;i++){
            for(int a = 1; a<9;a++){
                this.board.put(Integer.toString(i)+Integer.toString(a), new Space());
            }
       } 
       this.wordbank = AddWords();
       this.HorizontalWord = new HashMap<String, Integer>();
       this.VerticalWord = new HashMap<String, Integer>();
    }
    
    /** Creates a arraylist of the lisyt of words
     * @return the word list -  ArrayList<String>
     */
    private ArrayList<String> AddWords(){
        ArrayList<String> words = new ArrayList<String>();
        try{
            File file = new File("words.txt");
            Scanner input = new Scanner(file);
            while (input.hasNext()) {
                words.add(input.next());
            }
            input.close();
        }
        catch(FileNotFoundException e){
            System.out.println("File not found");
        }
        return words;
  }

    
    public String toString(){
        String art ="   "+"-".repeat(33);
        art += "\n   | A | B | C | D | E | F | G | H |";
        int i = 1;
            for (int row = 1; row < 9; row ++){
                    art+= "\n   |---|---|---|---|---|---|---|---|\n "+Integer.valueOf(i)+" ";
                for (int col = 1; col < 9; col ++){
                   art += "|" + board.get(Integer.toString(col)+Integer.toString(row));
                }
                art+="|";
                i++;
            }
            art+="\n   "+"-".repeat(33);
        return art;
    }

    /**Places a letter on the board
     * @param location repressents the location of the letter - String
     * @param letter reopressents the letter - String
     * @throws InvalidInputException if the letter is not in proper format, 
     * if the space is too high or the letter on the space is the same as te one you are putting down 
     */
    public void PlaceALetter(String location, String letter)throws InvalidInputException{
        location = getnumber(location.substring(0,1))+location.substring(1);
        if(location.matches("[1-8][1-8]") == false | letter.matches("[A-Z]|Qu") == false){
            throw new InvalidInputException();
        }
        Space space = this.board.get(location);
        if(space.getHeight() == 5){
            throw new InvalidInputException();
        }
        if(space.getLetter().equals(letter)){
            throw new InvalidInputException();
        }
        space.setLetter(letter);   
    }

    /**Gets the number value for the location that is entered
     * @param other repressents a letter - String
     * @return the number value - String
     */
    public String getnumber(String other){
        String[] letters = {"a","b","c","d","e","f","g","h"};
        ArrayList<String> a = new ArrayList<String>();
        String[] num = {"1","2","3","4","5","6","7","8"};
        for(int i = 0;i<letters.length;i++){
            a.add(letters[i]);
        }
        other = other.toLowerCase();
        if(a.contains(other) == false){
            return "9";
        }
        return num[a.indexOf(other)];
    }

   /**Checks if the the horizontal words on the board are valid
     * @return true if they are all valid or false if one of the words on the board is invalid
     */
     public boolean checkHorizontalWords(){
        this.HorizontalWord = new HashMap<String, Integer>();
        int score = 0;
        String word = "";
        int check = 0;
        for(int a = 1; a<9;a++){
            for(int i = 1; i<9;i++){
                String key = Integer.toString(i)+Integer.toString(a);
                if((board.get(key)).getLetter().equals("") &&  word.equals("") == false){
                    if(wordbank.contains(word.toLowerCase()) == false && word.matches("[A-Z]|Qu") == false ){
                        return false;
                    }
                    if(word.contains("Qu")&& check == 0){
                        score+=2;
                    }
                    if(word.matches("[A-Z]|Qu")==false){
                        HorizontalWord.put(word,score);
                    }
                    score = 0;
                    word = "";
                }
                if((board.get(key)).getLetter().equals("") == false){
                    word += (board.get(key)).getLetter();
                    score+=(board.get(key)).getHeight()-1+2;
                }
            }
            if(wordbank.contains(word.toLowerCase()) == false && word.matches("[A-Z]|Qu|") == false ){
                return false;
            }
            word = "";
        }
        return true;
    }
    /**Checks if the the vertical words on the board are valid
     * @return true if they are all valid or false if one of the words on the board is invalid
     */
    public boolean checkVerticalWords(){
        this.VerticalWord = new HashMap<String, Integer>();
        int score = 0;
        String word = "";
        int check = 0;
        for(int i = 1; i<9;i++){
            for(int a = 1; a<9;a++){
                String key = Integer.toString(i)+Integer.toString(a);
                if((board.get(key)).getLetter().equals("") &&  word.equals("") == false){
                    if(wordbank.contains(word.toLowerCase()) == false && word.matches("[A-Z]|Qu") == false ){
                        return false;
                    }
                    if(word.contains("Qu")&& check == 0){
                        score+=2;
                    }
                    if(word.matches("[A-Z]|Qu")==false){
                        VerticalWord.put(word,score);
                    }
                    score = 0;
                    word = "";
                }
                if((board.get(key)).getLetter().equals("") == false){
                    word += (board.get(key)).getLetter();
                    score+=(board.get(key)).getHeight()-1+2;
                    
                }
            }
            if(wordbank.contains(word.toLowerCase()) == false && word.matches("[A-Z]|Qu|") == false ){
                return false;
            }
            word = "";
        }
        return true;
    }

    public Board clone(){
        Board clone = new Board();
        HashMap<String, Space> boardclone = clone.getBoard();
        for(int i = 1; i<9;i++){
            for(int a = 1; a<9;a++){
                boardclone.put(Integer.toString(i)+Integer.toString(a), (board.get(Integer.toString(i)+Integer.toString(a))).clone());
            }
       } 
    return clone;
    }

    /**Checkls if the board has any tiles on it
     * @return true if there are none and false if there is atleast one
     */
    public boolean BoardisEmpty(){
        for(int i = 1; i<9;i++){
            for(int a = 1; a<9;a++){
                Space space = board.get(Integer.toString(i)+Integer.toString(a));
                if(space.getHeight()>0){
                    return false;
                }
            }
       } 
       return true;
    }

    /**Checks if there is only one letter on the board
     * @return true if there is only one and false if there is more or less than one
     */
    public boolean BoardHasOneLetter(){
        int check = 0;
        for(int i = 1; i<9;i++){
            for(int a = 1; a<9;a++){
                Space space = board.get(Integer.toString(i)+Integer.toString(a));
                if(space.getHeight()>0){
                    check++;
                }
            }
       } 
       if(check == 1){
        return true;
       }
       return false;
    }

  /**Checks if to boards are the same
     * @param other the second board that is compared - Board
     * @return true i they are the
     */
      public boolean equals(Board other){
        HashMap<String, Space> thisboard = this.getBoard();
        HashMap<String, Space> otherboard = other.getBoard();
        for(int i = 1; i<9;i++){
            for(int a = 1; a<9;a++){
                String key = Integer.toString(i) +Integer.toString(a);
                if((thisboard.get(key)).getLetter().equals((otherboard.get(key)).getLetter()) == false){
                    return false;
                }
            }
       }
       return true;
    }
    /**Test the board class
     * 
     */
    private static void test() {
        Board g = new Board();
        try{
            g.PlaceALetter("a1","Qu");
            g.PlaceALetter("a2","I");
            g.PlaceALetter("a3","T");     
            g.PlaceALetter("b1","I");
            g.PlaceALetter("c1","T");
            g.PlaceALetter("d1","E");     
            g.PlaceALetter("d2","A");     
            g.PlaceALetter("d3","T");     

        }
        catch(InvalidInputException f){
            f.getMessage();
        }
        System.out.println(g);
        System.out.println(g.checkHorizontalWords());
        System.out.println(g.checkVerticalWords());
        System.out.println(g.HorizontalWord);
        System.out.println(g.VerticalWord);
        Board a = new Board();
        try{
            a.PlaceALetter("a1","Qu");
            a.PlaceALetter("a2","I");
            a.PlaceALetter("a3","T");     
            a.PlaceALetter("b1","I");
            a.PlaceALetter("c1","T");
            a.PlaceALetter("d1","E");     
            a.PlaceALetter("d2","A");     
            a.PlaceALetter("d3","a");     

        }
        catch(InvalidInputException b){
           System.out.println(b.getMessage());
        }
        System.out.println(a);
        System.out.println(a.checkHorizontalWords());
        System.out.println(a.checkVerticalWords());
        System.out.println(a.HorizontalWord);
        System.out.println(a.VerticalWord);
     

    }


    public static void main(String[] args) {
        test();
    }
    public HashMap<String, Space> getBoard() {
        return this.board;
    }

    public HashMap<String, Integer> getHorizontalWord() {
        return HorizontalWord;
    }

    

    public HashMap<String, Integer> getVerticalWord() {
        return VerticalWord;
    }
}

