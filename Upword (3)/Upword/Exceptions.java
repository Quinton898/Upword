class InvalidInputException extends RuntimeException{
        public InvalidInputException(){
          super("Invalid input");
        }
}
class InvalidBoardException extends RuntimeException{
        public InvalidBoardException(){
          super("There is an invalid word on the board");
        }
}
class TakenOverWordException extends RuntimeException{
        public TakenOverWordException(){
          super("You have taken over a word, Restart to continue");
        }
}

class LetterNotInHandException extends RuntimeException{
        public LetterNotInHandException(){
          super("The entered letter is not in your hand.");
        }
}
class FirstPieceException extends RuntimeException{
        public FirstPieceException(){
          super("You need to start by placing a tile in the middle 4 spaces.");
        }
}
class SameLetterException extends RuntimeException{
        public SameLetterException(){
          super("Can't play the same lettr on space.");
        }
}
class InvalidRowOrColumnException extends RuntimeException{
        public InvalidRowOrColumnException(){
          super("You have to place a tile in the same row or column");
        }
}
class NoSurroundingPiecesException extends RuntimeException{
        public NoSurroundingPiecesException(){
          super("Space has no neighboring peices");
        }
}


