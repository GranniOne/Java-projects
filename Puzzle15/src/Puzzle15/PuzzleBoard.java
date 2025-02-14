
package Puzzle15;
import Puzzle15.Main;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class PuzzleBoard {
    int[][] puzzleBoard;
    int[] emptySpotCords;

    PuzzleBoard(){
        this.puzzleBoard = getValidatedPuzzleBoard(false);
        this.emptySpotCords = findEmptySpot(this.puzzleBoard);
    }

    // metode til at generere et nyt spilbræt, det bliver ikke valideret her
    int[][] genNewPuzzleBoard() {
        // opret tomt 2D array
        int[][] puzzle = new int[4][4];

        // lav arraylist med tal fra 1-15
        Integer[] a = {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(a));

        Random random = new Random();

        // tilføj tal til 2D array i tilfældig rækkefølge
        int x, y;
        for (y = 0; y < 4; y++) {
            for (x = 0; x < 4; x++) {
                if (!numbers.isEmpty()) {
                    int index = random.nextInt(numbers.size());
                    puzzle[x][y] = numbers.get(index);
                    numbers.remove(numbers.get(index));
                }
            }
        }
        return puzzle;
    }

    // metode som giver et puzzleboard som er et træk fra færdigt
    int[][] getValidatedPuzzleBoard(Boolean rigged){
        if(rigged == true){
            int[][] riggedBoard = {{1,5,9,-1},{2,6,10,13},{3,7,11,14},{4,8,12,15}};
            return riggedBoard;
        }else{
            return getValidatedPuzzleBoard();
        }



    }
    // metode som bruteforcer et validt puzzleboard.

    int[][] getValidatedPuzzleBoard( ){
        int [][] puzzle;
        boolean condition1;
        boolean condition2;
        // do-while loop til at generer puzzleboards
        do{
            puzzle = genNewPuzzleBoard();
            // find distancen til bunden fra den tomme plads
            //Da puzzleboardets koordinater bruger tal fra 0-3 og går i omvendt rækkefølge end der skal bruges til
            //at validere puzzleboardet. Derfor laves dette switch case for at omdanne dem til den korrekte værdi
            int distanceToBottom = switch (findEmptySpot(puzzle)[1]) {
                case 0 -> 4;
                case 1 -> 3;
                case 2 -> 2;
                case 3 -> 1;
                default -> 0;
            };
            // Her udregnes de to betingelser som skal tjekkes for at se om puzzleboardet er validt
            condition1 = distanceToBottom%2==0;
            condition2 = getInversion(puzzle);
        }while (condition1 == condition2);
        //Hvis begge betingelser er ens er puzzleboardet validt og returneres.'
        return puzzle;
    }

    // tæl inversions og returner om det er et lige tal eller ej:
    boolean getInversion(int[][] board){
        // lav puzzleboardet til 1d array:
        ArrayList<Integer> board1D = new ArrayList<>();
        for (int[] row: board){
            for(int num: row){
                board1D.add(num);
            }
        }

        // gennemgå alle tal i arraylisten og tjek hvor mange tal til højre for dem er højere end sig selv
        // den tomme plads markeret med -1 springes over
        int inversions = 0;
        for(int i = 0;i<board1D.size()-1;i++){
            for (int j = i+1;j<board1D.size();j++){
                if ((board1D.get(i)>board1D.get(j)) && (board1D.get(i) != -1)&&board1D.get(j) != -1){
                    inversions++;
                }
            }
        }
        // returnere boolean som er True hvis tallet er lige og False hvis det er ulige
        return inversions%2==0;
    }

    // finder den tomme plads på brættet indikeret af -1.
    int[] findEmptySpot(int[][]board){
        int[] cords = {-1,-1};
        // looper igennem hele brættet indtil den finder -1 og returnere positionen.
        for (int y =0;y< 4;y++){
            for(int x=0; x<4;x++ ){
                if (board[x][y] == -1){
                    cords[1] = y;
                    cords[0] = x;
                }
            }
        }
        return cords;
    }


    // metode til at flytte en brik på brættet. tager brikkens koordinater som input i form af integer array
    void MovePiece(int[] cords){

        // tjek om brikken ligger ved siden af den tomme plads:
        int x_dif =this.emptySpotCords[0]-cords[0];
        int y_dif = this.emptySpotCords[1]-cords[1];

        // tjek forskellen mellem den valgte brik og den tomme briks position
        if (((x_dif == 1 || x_dif == -1) && y_dif == 0)||((y_dif == 1 || y_dif == -1) && x_dif == 0)){
            // byt plads af værdierne
            this.puzzleBoard[this.emptySpotCords[0]][this.emptySpotCords[1]] = this.puzzleBoard[cords[0]][cords[1]];
            this.puzzleBoard[cords[0]][cords[1]] = -1;


            // gemt -1 lokation
            this.emptySpotCords[0] = cords[0];
            this.emptySpotCords[1] = cords[1];
            //opdater GUI
            GUI_Puzzle15.ScoreCounter++;
            if (checkWin()){
                System.out.println("DU VANDT");
                JOptionPane.showMessageDialog(
                        null,
                        "du vandt med "+(GUI_Puzzle15.ScoreCounter)+" forsøg",
                        "You won",
                        JOptionPane.INFORMATION_MESSAGE);
                GUI_Puzzle15.pb = new PuzzleBoard();
                Main.frame.repaint();
                GUI_Puzzle15.ScoreCounter = 0;
            }
        }
        else{
            JOptionPane.showMessageDialog(
                    null,
                    "ugyldigt træk",
                    "fejl træk",
                    JOptionPane.WARNING_MESSAGE);

        }

    }


    // metode til at tjekke om puzzleboardet er løst:
    public boolean checkWin(){
        int[][] winBoard = {{1,5,9,13},{2,6,10,14},{3,7,11,15},{4,8,12,-1}};
        return Arrays.deepEquals(this.puzzleBoard, winBoard);
    }


    // debugging metode som printer brættet i konsolen
    public void printBoard(){
        for (int[] row:this.puzzleBoard){
            System.out.println(Arrays.toString(row));
        }
    }

}
