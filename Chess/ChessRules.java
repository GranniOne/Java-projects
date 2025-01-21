import java.util.Arrays;
import java.util.Objects;

class ChessRules{
    public static boolean Chessrules(String Brik, int[] chosenPieceCords, int[] chosenLokationCords) {
        int row = chosenLokationCords[0];
        int col = chosenLokationCords[1];
        String color1 = Brik;
        String color2 = "";
        String piece = ChessBoard_GUI.chessBoard.chessBoard[row][col];
        int underscoreIndex = Brik.indexOf("_");

        if (underscoreIndex != -1) {
            color1 = Brik.substring(0, underscoreIndex);
        }
        if (!piece.isEmpty()) {
            int pieceUnderscoreIndex = piece.indexOf("_");
            color2 = piece.substring(0, pieceUnderscoreIndex);
        }

        // Check if the two pieces have different colors
        if (!color1.equals(color2)) {
            if (Brik.substring(underscoreIndex + 1).equals("knight")) {
                return (Knight(chosenPieceCords, chosenLokationCords));
            }
            else {
                return switch (Brik.substring(underscoreIndex + 1)) {
                    case "tower" -> Tower(chosenPieceCords, chosenLokationCords);
                    case "pawn" -> Pawn(Brik, chosenPieceCords, chosenLokationCords);
                    case "bishop" -> Bishop(chosenPieceCords, chosenLokationCords);
                    case "king" -> King(chosenPieceCords, chosenLokationCords);
                    case "queen" -> Queen(chosenPieceCords, chosenLokationCords);
                    default -> false;
                } && !isPieceBlockingPath(ChessBoard_GUI.chessBoard.chessBoard, chosenPieceCords, chosenLokationCords);
            }

        }
        return false;
    }

    public static boolean isPieceBlockingPath(String[][] chessBoard, int[] start, int[] end){
        int dx = end[0] - start[0];
        int dy = end[1] - start[1];
        int steps = Math.max(Math.abs(dx), Math.abs(dy));
        for (int i = 1; i < steps; i++){
            int x = start[0] + (dx * i / steps);
            int y = start[1] + (dy * i / steps);
            if (!chessBoard[x][y].isEmpty()){
                return true;
            }
        }
        return false;
    }
    // regel sættet til tårnet
    public static boolean Tower(int[] chosenPieceCords, int[] chosenLokationCords){
        return chosenPieceCords[0] - chosenLokationCords[0] == 0 && chosenPieceCords[1] - chosenLokationCords[1] != 0 || chosenPieceCords[0] - chosenLokationCords[0] != 0 && chosenPieceCords[1] - chosenLokationCords[1] == 0;
    }
    // regel sættet til bønder
    public static boolean Pawn(String PawnType,int[] chosenPieceCords, int[] chosenLokationCords){

        // udregning af relevante værdier
        String pawnType = PawnType.substring(0, PawnType.indexOf("_"));
        int rowDifference = chosenPieceCords[0] - chosenLokationCords[0];
        int colDifference = chosenPieceCords[1] - chosenLokationCords[1];

        // standart forlæns bevægelse
        if ((pawnType.equals("white") && rowDifference == 1 && colDifference == 0) ||
                (pawnType.equals("black") && rowDifference == -1 && colDifference == 0)) {
            return Objects.equals(ChessBoard_GUI.chessBoard.ChessPieceType(chosenLokationCords), "");
        }
        // angreb: diagonal bevægelse
        else if((pawnType.equals("white") && (colDifference == -1||colDifference == 1) && rowDifference == 1)||
                (pawnType.equals("black") && (colDifference == -1||colDifference == 1) && rowDifference == -1)){
            return !Objects.equals(ChessBoard_GUI.chessBoard.ChessPieceType(chosenLokationCords), "");

        }
        // dobbelt bevægelse på første tur af bonden
        else if ((pawnType.equals("white") && rowDifference == 2 && colDifference == 0 && chosenPieceCords[0] == 6) ||
                (pawnType.equals("black") && rowDifference == -2 && colDifference == 0 && chosenPieceCords[0] == 1)) {
            return Objects.equals(ChessBoard_GUI.chessBoard.ChessPieceType(chosenLokationCords), "") &&
                    Objects.equals(ChessBoard_GUI.chessBoard.ChessPieceType(new int[]{chosenPieceCords[0] - rowDifference / 2, chosenPieceCords[1]}), "");
        }
        return false;
    }
    // regelsættet til springeren
    public static boolean Knight(int[] chosenPieceCords, int[] chosenLokationCords){
        int[][] dxy = {{2, 2, -2, -2, 1, 1, -1, -1},{1, -1, 1, -1, 2, -2, 2, -2}};
        for (int i = 0; i < dxy[0].length; i++) {
            if (Arrays.equals(new int[]{chosenPieceCords[0] + dxy[0][i], chosenPieceCords[1] + dxy[1][i]}, chosenLokationCords)){
                return true;}

        }
        return false;

    }

    // regelsættet til løberen
    public static boolean Bishop(int[] chosenPieceCords, int[] chosenLokationCords){
        double test = (double) (chosenPieceCords[1] - chosenLokationCords[1]) / (chosenPieceCords[0] - chosenLokationCords[0]);
        return test == 1 || test == -1;
    }
    // regelsættet til kongen
    public static boolean King(int[] chosenPieceCords, int[] chosenLokationCords){
        int xDif =chosenPieceCords[1]-chosenLokationCords[1];
        int yDif =chosenPieceCords[0]-chosenLokationCords[0];
        return((xDif >= -1 && xDif <= 1) && (yDif >= -1 && yDif <= 1) && (xDif != 0 || yDif != 0));
    }

    // regelsættet til dronningen
    public static boolean Queen(int[] chosenPieceCords, int[] chosenLokationCords){
        return Tower(chosenPieceCords,chosenLokationCords) || Bishop(chosenPieceCords,chosenLokationCords);

    }


}