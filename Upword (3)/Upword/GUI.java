import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
class GUI extends JFrame{

    public GUI(){
        
        super("Upwords");
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel);
                Game game = new Game(2);
        JLabel Uppertext = new JLabel("Player 1s turn      Score: "+game.getPlayers()[0].getScore(), SwingConstants.LEFT);
        JLabel Bottomtext = new JLabel(" ", SwingConstants.LEFT);
        Grid grid = new Grid(Uppertext,Bottomtext,game);
        mainPanel.add(Uppertext,BorderLayout.PAGE_START);
        mainPanel.add(grid, BorderLayout.CENTER);
        mainPanel.add(Bottomtext, BorderLayout.PAGE_END);
        pack();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new GUI();
    }
   
   
}
class Grid extends JPanel {
    Game game;
    JButton[][] squares = new JButton[8][8];
    JButton[] pieces = new JButton[7];
    JButton[] options = new JButton[4];
    JLabel upperText;
    JLabel bottomText;
    Board board;
    Board cloneBoard;
    ArrayList<String> CurrrentPlayerhand;
    int turn;
    String CurrentLetter;
    String CurrentLocation;
    int CurrentHeight;


    public Grid(JLabel upperText, JLabel bottomText,Game games) {
        this.game = games;
         CurrrentPlayerhand = game.getPlayers()[turn].getHand();
        this.game.setCurrrentPlayerhand(CurrrentPlayerhand);
        this.upperText = upperText;
        this.bottomText = bottomText;
        setLayout(new BorderLayout());
        board = game.getGameboard();
        cloneBoard = new Board();
        turn = game.getPlayerturn();
        CurrentLetter ="";
        CurrentLocation = "";
        CurrentHeight = 0;
        initializeBoard();
        initializeBottomrow(); 
    }
    
    /**Initializes the board with buttons
     * 
     */
    private void initializeBoard(){
        JPanel gridPanel = new JPanel(new GridLayout(8, 8));
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new JButton();
                String letter = (((board.getBoard()).get(Integer.toString(j+1)+Integer.toString(i+1)))).getLetter();
                if(board.getBoard().get(Integer.toString(j+1)+Integer.toString(i+1)).getHeight()>0){
                    squares[i][j].setText(letter+board.getBoard().get(Integer.toString(j+1)+Integer.toString(i+1)).getHeight());
                }
                else{
                    squares[i][j].setText("");
                }
                gridPanel.add(squares[i][j]);
                addActionListenerBoard(i,j);
            }
        }
        add(gridPanel, BorderLayout.CENTER);
    }
    
    /**initializes the player hand and the option buttons
     * 
     */
    private void initializeBottomrow(){
        JPanel Bottomrow = new JPanel();
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        for (int j = 0; j < 7; j++) {
            pieces[j] = new JButton();
            pieces[j].setPreferredSize(new Dimension(70, 50)); 
            if(j<CurrrentPlayerhand.size()){
                pieces[j].setText(CurrrentPlayerhand.get(j)); 
                pieces[j].repaint();
            }
            else{
                   pieces[j].setText("?"); 
            }
            rowPanel.add(pieces[j]);
            addActionListenerHand(j);   
        }
        Bottomrow.add(rowPanel);
        JPanel bowPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 5));
        String[] option = {"Skip","End Turn","Restart","Exchange a peice"};
        for (int j = 0; j < 4; j++) {
            options[j] = new JButton();
            options[j].setPreferredSize(new Dimension(150, 50)); 
            options[j].setText(option[j]);
            bowPanel.add(options[j]);
            addActionListenerOption(j);
        }
            Bottomrow.repaint();
        Bottomrow.add(bowPanel);
        add(Bottomrow, BorderLayout.PAGE_END);
    }

    /**Adds actions to the button
     * @param i the index location of the button
     * @param j the index location of the button
     */
    private void addActionListenerBoard(int i, int j){
        squares[i][j].addActionListener(e -> actionPerformedBoard(i, j));
    }

  
    /**Plays a move when you have selected a letter and selected a space on the board
     * @param i the index location of the button
     * @param j the index location of the button
     */
    private void actionPerformedBoard(int i, int j){
        if(CurrentLetter.equals("")){
            System.out.println(squares[i][j].getText());
            bottomText.setText("Must click a letter before clicking a space");
        }
        else{
            String location = numberToLetter(j+1)+Integer.toString(i+1);
            game.setCurrentPlacedLetter(CurrentLetter);
            game.setCurrentPlacedLocation(location);
        try{
                game.LetterinHand();
                game.SurroundingisEmpty(cloneBoard);
                game.checkplayedlocations();
                cloneBoard.PlaceALetter(location, CurrentLetter);
                location = game.gameboard.getnumber(location.substring(0,1))+location.substring(1);
                game.TakenOverLetters+= game.gameboard.getBoard().get(location).getLetter();
            }
            catch(Exception a){
                bottomText.setText(a.getMessage());
                return;
            }  
            CurrentHeight = cloneBoard.getBoard().get(location).getHeight();
            squares[i][j].setText(CurrentLetter+CurrentHeight);
            game.removeletter();
            CurrentLetter = "";
            CurrentLocation = "";
            for(int a = 0;i<CurrrentPlayerhand.size();i++){
                if(CurrrentPlayerhand.get(a).equals(CurrentLetter)){
                    CurrrentPlayerhand.remove(a);
                    break;
                }
            }
            updateHand();
            bottomText.setText("  ");
        }
    }
    /**Adds actions to the button
     * @param i the index location of the button
     */
    private void addActionListenerHand(int i){
        pieces[i].addActionListener(e -> actionPerformedHand(i));
    }

  
    /**makes the current letter the on the user selects
     * @param i the index location of the button
     */
    private void actionPerformedHand(int i){
        CurrentLetter = pieces[i].getText();
        bottomText.setText("Current letter is "+CurrentLetter);  
    }

    /**Adds an action to the buttons
     * @param i the index location of the button
     */
    private void addActionListenerOption(int i){
        options[i].addActionListener(e -> actionPerformedOption(i));
    }

  
    /**Chooses the option based on what button the user selected
     * @param i the index location of the button
     */
    private void actionPerformedOption(int i){
       if(options[i].getText().equals("Restart")) {
            Restart();
       } 
       if(options[i].getText().equals("End Turn")) {
            Endturn();
       }
       if(options[i].getText().equals("Skip")) {
            Skip();
       }
       if(options[i].getText().equals("Exchange a peice")) {
            Exchange();
       }
       
    }

    /**Restarts the users turn
     * 
     */
    private void Restart() {
    while (game.enteredLetters.size() > 0) {
        game.CurrrentPlayerhand.add(game.enteredLetters.get(0));
        game.enteredLetters.remove(0);
    }
    resetvariables();
    updateHand();
    updateBoard(); 
    bottomText.setText("Restarted Board");
    }

/**restets all game variable
 * 
 */
private void resetvariables(){
    game.enteredLocations = new ArrayList<String>();
    game.currentPlacedLetter ="";
    game.currentPlacedLocation = "";
    CurrrentPlayerhand = game.CurrrentPlayerhand;
    game.enteredLetters = new ArrayList<String>();
    cloneBoard = game.gameboard.clone();
    board = game.getGameboard();
    CurrentLetter = "";
}


/**Updates the users hand after playing a letter
 * 
 */
private void updateHand() {
    for (int i = 0; i < 7; i++) {
        pieces[i].setText("");
        if (i < CurrrentPlayerhand.size()) {
            pieces[i].setText(CurrrentPlayerhand.get(i));
        }
    }
}
/**Updates the board
 * 
 */
private void updateBoard(){
    for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String letter = (((board.getBoard()).get(Integer.toString(j+1)+Integer.toString(i+1)))).getLetter();
                if(board.getBoard().get(Integer.toString(j+1)+Integer.toString(i+1)).getHeight()>0){
                    squares[i][j].setText(letter+board.getBoard().get(Integer.toString(j+1)+Integer.toString(i+1)).getHeight());
                }
                else{
                    squares[i][j].setText("");
                }
            }
        }
}


    /**Ends the users turn if there are no invalid words on the board
     * 
     */
    private void Endturn(){
                if(cloneBoard.equals(game.gameboard)){
                    bottomText.setText("No changes to the board have been made can't finish turn");
                    return;
                }
                try{
                    game.gameboard.checkHorizontalWords();
                    game.gameboard.checkVerticalWords();
                    game.BoardisValid(cloneBoard);
                    game.gameboard = cloneBoard.clone();
                    while(game.CurrrentPlayerhand.size()<7){
                        game.CurrrentPlayerhand.add(game.letters.Draw1Letter());
                    }
                    game.NumberofSkips = 0;  
                    changeturn();

                }
                catch(Exception a){
                   bottomText.setText(a.getMessage());
                }

    }

 /**Skips the users turn
     * 
     */
       private void Skip(){
        if(cloneBoard.equals(board) == false){
            bottomText.setText("Can't skip because you have placed letters on the board.");
            return;
        }
        game.NumberofSkips++;
        if(game.NumberofSkips == 2){
            endgame();
            return;
        }
        changeturn();
    }

    /**Skips the users turn and exchanges on of the user pieces for one in the bag
     * 
     */
    private void Exchange(){
        if(CurrentLetter.equals("")){
            bottomText.setText("Press a letter you want to replace");
            return;
        }
        else if(game.enteredLetters.size()>0){
                    bottomText.setText("Can't discard if you have played letters, you must restart.");
                    return;
        }
        else{
            game.CurrrentPlayerhand.remove(CurrentLetter);
            game.CurrrentPlayerhand.add(game.letters.Draw1Letter());
            game.letters.add1Letter(CurrentLetter);
            bottomText.setText("Your new hand is "+ CurrrentPlayerhand);
        }
        Restart();
        Skip();
    }


    /**Changes player turn
     * 
     */
    private void changeturn(){
        if(game.getPlayerturn() == 1){
            game.playerturn = 0;
        }
        else if(game.getPlayerturn() == 0){
            game.playerturn = 1;
        }
        turn = game.getPlayerturn();
        game.CurrrentPlayerhand = game.players[turn].getHand(); 
        resetvariables();
           
        upperText.setText("Player "+(turn+1)+"s turn      Score: "+game.getPlayers()[turn].getScore());
        updateHand();
    }

    /**Ends the game and declares a winner
     * 
     */
    private void endgame(){
        int highestscore = -20;
        for(int i = 0; i<game.players.length;i++){
            int numberoftilesleft = game.players[i].getHand().size();
            if(game.players[i].getScore()-numberoftilesleft>highestscore){
                highestscore = game.players[i].getScore()-numberoftilesleft;
                game.playerturn = i;
            }
            else if(game.players[i].getScore()-numberoftilesleft==highestscore){
                bottomText.setText("Its a tie");
                  disableButtons();
                  return;
            }
        }
        bottomText.setText("The higehst score was "+highestscore+" this score was set by player "+(game.playerturn+1)+". Therefore player "+(game.playerturn+1)+" wins!!!!!!");
        disableButtons();
    }
    
    /**Disables all buttons
     * 
     */
    private void disableButtons(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                    squares[i][j].setEnabled(false);
            }
        } 
        for (int j = 0; j < 4; j++) {
                    options[j].setEnabled(false);
        }
        for (int j = 0; j < 7; j++) {
                    pieces[j].setEnabled(false);
        }
    }

    /**Returns the number value for th board location value
     * @param num inputed location
     * @return the letter location
     */
    private String numberToLetter(int num){
        String[] letters = {"a","b","c","d","e","f","g","h"};
        return letters[num-1];
    }
}

