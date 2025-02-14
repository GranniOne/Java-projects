package game2048;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

import java.util.Random;

public class GameData {
    int[][] BoardData;
    int BoardLength;
    int LatestElement;

    Random rand;

    GameData(){
        this.BoardData = new int[][]{{2,2,2,2},
                                     {0,0,0,0},
                                     {0,0,0,0},
                                     {0,0,0,0}};
        this.BoardLength = BoardData.length;
        this.rand = new Random();


    }


    // Function to handle movement
    public int[][] move(int[][] grid, String direction) {
        switch (direction.toLowerCase()) {
            case "down":
                for (int i = 0; i < grid.length; i++) {
                    // Reverse the row, process it, then reverse it back
                    grid[i] = reverseArray(processRow(reverseArray(grid[i])));
                }

                break;

            case "up":
                for (int i = 0; i < grid.length; i++) {
                    // Directly process the row
                    grid[i] = processRow(grid[i]);
                }
                break;

            case "left":
                int n = grid.length;
                int[][] res = new int[n][n];
                // Rotate the matrix 90 degrees counterclockwise
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        res[j][n - i - 1] = grid[i][j];
                    }
                }
                for (int i = 0; i < n; i++) {
                    System.arraycopy(res[i], 0, grid[i], 0, n);
                }

                for (int i = 0; i < grid.length; i++) {
                    grid[i] = reverseArray(processRow(reverseArray(grid[i])));
                }
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        res[n - j - 1][i] = grid[i][j];
                    }
                }
                for (int i = 0; i < n; i++) {
                    System.arraycopy(res[i], 0, grid[i], 0, n);
                }
                break;

            case "right":
                int n1 = grid.length;
                int[][] res1 = new int[n1][n1];
                // Rotate the matrix 90 degrees counterclockwise
                for (int i = 0; i < n1; i++) {
                    for (int j = 0; j < n1; j++) {
                        res1[j][n1 - i - 1] = grid[i][j];
                    }
                }
                for (int i = 0; i < n1; i++) {
                    System.arraycopy(res1[i], 0, grid[i], 0, n1);
                }
                for (int i = 0; i < grid.length; i++) {
                    grid[i] = processRow(grid[i]);
                }
                for (int i = 0; i < n1; i++) {
                    for (int j = 0; j < n1; j++) {
                        res1[n1 - j - 1][i] = grid[i][j];
                    }
                }
                for (int i = 0; i < n1; i++) {
                    System.arraycopy(res1[i], 0, grid[i], 0, n1);
                }
                break;
            default:
                System.out.println("Invalid direction! Use 'left', 'right', 'up', or 'down'.");
        }
        grid = pieceGenerator(grid);
        return grid;


    }

    // Function to process a single row (slide, combine, slide)
    public static int[] processRow(int[] row) {
        int[] newRow = new int[row.length];
        int index = 0;

        // Step 1: Slide (Remove zeros)
        for (int num : row) {
            if (num != 0) {
                newRow[index++] = num;
            }
        }
        // Step 2: Combine
        for (int i = 0; i < newRow.length - 1; i++) {
            if (newRow[i] == newRow[i + 1] && newRow[i] != 0) {
                newRow[i] *= 2;          // Double the value
                newRow[i + 1] = 0;      // Set the next tile to zero
            }
        }

        // Step 3: Slide Again (Remove zeros after combining)
        int[] finalRow = new int[row.length];
        index = 0;
        for (int num : newRow) {
            if (num != 0) {
                finalRow[index++] = num;
            }
        }

        return finalRow;
    }

    public int[][] pieceGenerator(int[][] BoardData) {
        ArrayList<Integer> emptySpacesList = new ArrayList<>();

        for (int i = 0; i < BoardLength; i++) {
            for (int j = 0; j < BoardLength; j++) {
                if (BoardData[i][j] == 0) {
                    emptySpacesList.add(i * BoardLength + j);
                }
            }
        }
        if(emptySpacesList.size() == 0){
            return BoardData;
        }
        // Generate a random index within the correct range
        int randomIndex = rand.nextInt(emptySpacesList.size());
        int randomPosition = emptySpacesList.get(randomIndex);
        int randomValue = rand.nextInt(10) < 9 ? 2 : 4;

        // Place the value in the selected position
        BoardData[randomPosition / BoardLength][randomPosition % BoardLength] = randomValue;
        LatestElement = randomPosition;
        emptySpacesList.clear();
        return BoardData;

    }

    public void LosingCondition(){
        int[][] temp = new int[BoardLength][BoardLength];
        String[] directions = {"up", "down", "left", "right"};
        System.arraycopy(BoardData, 0, temp, 0, BoardLength);

        for(String direction : directions){
            temp = move(temp, direction);
            if(!Arrays.deepEquals(temp, BoardData)){
                return;
            }
        }
        System.out.println(Arrays.deepToString(temp));
        System.out.println(Arrays.deepToString(BoardData));
        JOptionPane.showMessageDialog(null, "You Lose");

    }


    // Utility to reverse an array (for right and down movements)
    public static int[] reverseArray(int[] row) {
        int[] reversed = new int[row.length];
        for (int i = 0; i < row.length; i++) {
            reversed[i] = row[row.length - 1 - i];
        }
        return reversed;
    }
    public void setBoardData(int[][] boardData) {
        BoardData = boardData;
    }
    public int[][] getBoardData() {
        return BoardData;
    }

    public int getLatestElement() {
        return LatestElement;
    }
}
