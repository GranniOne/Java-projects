import java.util.Arrays;

public class ChessBoard {
    // Chessboard constructor:
    public String[][] chessBoard;
    ChessBoard(){
        this.chessBoard = getNewBoard();
    }

    // Få et frisk skakbræt
    public String[][] getNewBoard() {
        return new String[][]{
                {"black_tower","black_knight","black_bishop","black_queen","black_king","black_bishop","black_knight","black_tower"},
                {"black_pawn","black_pawn","black_pawn","black_pawn","black_pawn","black_pawn","black_pawn","black_pawn"},
                {"","","","","","","",""},
                {"","","","","","","",""},
                {"","","","","","","",""},
                {"","","","","","","",""},
                {"white_pawn","white_pawn","white_pawn","white_pawn","white_pawn","white_pawn","white_pawn","white_pawn"},
                {"white_tower","white_knight","white_bishop","white_queen","white_king","white_bishop","white_knight","white_tower"}
        };
    }
    // få string på koordinater af skakbrættet
    public String ChessPieceType(int[] cords) {
        return this.chessBoard[cords[0]][cords[1]];

    }

    // flyt en skakbrik, chosenPieceCords er koordinaterne til den valgte brik
    // chosenLokationCords er koordinaterne til oladsen man gerne vil rykke brikken til
    public void ChessMove(int[] chosenPieceCords, int[] chosenLokationCords) {
        this.chessBoard[chosenLokationCords[0]][chosenLokationCords[1]] = ChessPieceType(chosenPieceCords);
        this.chessBoard[chosenPieceCords[0]][chosenPieceCords[1]] = "";

    }

}
