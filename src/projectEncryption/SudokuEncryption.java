package projectEncryption;

import java.io.*;
import java.util.LinkedList;
import java.util.Random;

public class SudokuEncryption extends Algorithem{
    private Random random;
    private static final int SIZE = 9;
    private int[][] board;
    public SudokuEncryption()
    {
        super();
        this.board =new int[SIZE][SIZE];
        random = new Random();
        generate();
        createKeys();
    }
    // Generate a valid Sudoku board
    public void generate() {
        generate(0, 0);
    }

    //to generate Board
    private Boolean generate(int row,int col)
    {
        // If we have reached the end of the board, the board is valid
        if (row == SIZE) {
            return true;
        }

        // If the current cell is not empty, move to the next cell
        if (board[row][col] != 0) {
            return generate(nextRow(row, col), nextCol(row, col));
        }

        // Try placing a number in the current cell and recurse
        int[] values = new int[9];
        for (int i = 1; i <= 9; i++) {
            values[i - 1] = i;
        }
        shuffle(values);
        for (int i : values) {
            board[row][col] = i;
            if (isValid(row, col) && generate(nextRow(row, col), nextCol(row, col))) {
                return true;
            }
            board[row][col] = 0;
        }

        // No valid number was found, backtrack
        return false;
    }
    // shuffles the numbers in values
    private void shuffle(int[] values)
    {
        for (int i = 0; i < values.length; i++) {
            int j = random.nextInt(values.length);
            int temp = values[i];
            values[i] = values[j];
            values[j] = temp;
        }
    }

    // checks if the sudoku is Valid by using backTracking method
    private boolean isValid(int row, int col)
    {
        // Check rows
        for (int i = 0; i < SIZE; i++) {
            if (i != col && board[row][i] == board[row][col]) {
                return false;
            }
        }

        // Check columns
        for (int i = 0; i < SIZE; i++) {
            if (i != row && board[i][col] == board[row][col]) {
                return false;
            }
        }

        // Check block
        int blockRow = row / 3 * 3;
        int blockCol = col / 3 * 3;
        for (int i = blockRow; i < blockRow + 3; i++) {
            for (int j = blockCol; j < blockCol + 3; j++) {
                if ((i != row || j != col) && board[i][j] == board[row][col]) {
                    return false;
                }
            }
        }

        return true;
    }


    // go to next Row
    private int nextRow(int row, int col) {
        return (col == SIZE - 1) ? row + 1 : row;
    }


    //go to next Column
    private int nextCol(int row, int col) {
        return (col == SIZE - 1) ? 0 : col + 1;
    }


    @Override
    public LinkedList<Integer> createKeys()
    {
        for(int i = 0; i<9;i++)
        {
            for(int j = 0;j<9;j++)
            {
                super.getKeys().add(board[i][j]-1);
            }
        }
        return super.getKeys();
    }


    @Override
    public String encrypt(File f)
    {
        try
        {
            int temp,index=0,size = super.getKeys().size();
            FileOutputStream out;
            FileInputStream in;
            File outFile = new File( f.getParentFile(),"1" +f.getName() );
            in = new FileInputStream(f);
            out = new FileOutputStream(outFile);
            while((temp = in.read()) != -1)
            {
                out.write(super.MoveBites(temp,super.getKeys().get(index++)));
                if(size == index)
                    index = 0;
            }
            for(int i = 0;i< 81;i++) {
                out.write(super.getKeys().get(i));
            }
            in.close();
            out.close();
            return outFile.getAbsolutePath();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return  "";
    }


    @Override
    public String decrypt(File f)
    {
        try
        {
            int temp,index=0;
            int [] vec = new int[81];
            FileOutputStream out;
            FileInputStream in;
            File outFile = new File( f.getParentFile(),f.getName().substring(1) );
            in = new FileInputStream(f);
            out = new FileOutputStream(outFile);
            long sizeOfFile = f.length();
            while((temp = in.read()) != -1)
            {

                    if(sizeOfFile <=81)
                    {

                        vec[(int) sizeOfFile-1] = temp;
                    }
                sizeOfFile--;

            }
            in = new FileInputStream(f);
            sizeOfFile = f.length();
            while((temp = in.read()) != -1)
            {
                out.write(super.MoveBites(temp,vec[80-index]));
                index++;
                if(index == 81)
                    index = 0;
            }
            out.getChannel().truncate(out.getChannel().size()-81);
            in.close();
            out.close();
            return outFile.getAbsolutePath();
        } catch (IOException e)
        {
            e.printStackTrace();
        } catch (Exception e)
        {
            System.out.println(e);
        }
        return  "";
    }

}
