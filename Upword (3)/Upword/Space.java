

public class Space {
    private int height;
    private String letter;


    public Space(int height, String letter) {
        this.height = height;
        this.letter = letter;
    }

    public Space() {
        this.height = 0;
        this.letter = "";
    }

    public Space clone(){
        return new Space(height,letter);
    }


    public String toString() {
        if (letter.equals("")){
            return "   ";
        }
        if (letter.equals("Qu")){
            return letter+Integer.valueOf(height);
        }
        return letter+" "+Integer.valueOf(height);
    }

    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public String getLetter() {
        return letter;
    }
    public void setLetter(String letter) {
        this.letter = letter;
        height++;
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + height;
        result = prime * result + ((letter == null) ? 0 : letter.hashCode());
        return result;
    }
}

