package gameTetris;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * A title is an object built from a .txt file that has to have the right format:
 * all lines must be formed by integers, and all lines have to have the same length.
 * For a title to be valid, it also has to fit within the bounds of a given MyGrid object
 *
 * @author Danilo Del Busso
 * @version 16/07/2018
 */
public class Title {

    private ArrayList<String> lines;  //contains all the lines of the title file
    private int width, height;

    /**
     * The title constructor uses the path of the .txt file where the file is located and the
     * grid on which the title has to be drawn to construct an arraylist that stores all the right
     * integer values corresponding to colors on the grid.
     * @param path the path of the file in the project
     * @param grid the grid on which the title has to be drawn
     * @throws Exception if the file doesn't have the right format, an exception is thrown
     */
    public Title(String path, MyGrid grid) throws Exception {
        lines = new ArrayList<>();
        String line = null;

        try {
            FileReader fileReader =
                    new FileReader(path);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }

            bufferedReader.close();
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                    "Unable to open file '" +
                            path + "'");
        }
        catch(IOException ex) {
            System.out.println(
                    "Error reading file '"
                            + path + "'");

        }

        if(checkFileFormat(lines, grid)){
            width = lines.get(0).length();
            height = lines.size();
        }
        else{
            throw new Exception("The .txt file has the wrong format: all lines have to have the same length and have to be " +
                    "able to fit in the grid");
        }
    }

    /**
     * Checks if the title can fit in the grid
     * @param lines the ArrayList containing the lines of the .txt file that have to be mapped into a title on the grid
     * @return
     */
    private boolean checkFileFormat(ArrayList<String> lines, MyGrid grid) {
       int length = lines.get(0).length();
        for(String line: lines){
            if(line.length() != length
                    || line.length() < 1
                    || line.length() >= grid.getPrefColumns()
                    || lines.size()>=grid.getPrefRows()){
                return false;
            }
        }
        return true;
    }

    /**
     * Return the width of the title
     * @return the width of the title
     */
    public int getWidth() {
        return width;
    }

    /**
     * Return the height of the title
     * @return the height of the title
     */
    public int getHeight(){
        return height;
    }


    /**
     * Return the numerical value stored in the .txt file
     * @param line the line of the value to return
     * @param position the position of the value to return
     * @return the numerical value stored in the .txt file
     */
    public int getValueAt(int line, int position) {
        String [] values = lines.get(line-1).split("");
        int color = 0;
        try{
            color = Integer.parseInt(values[position]);
        }
        catch (Exception e){
            System.out.println("Colors have to be integers comprised in the color palette");
        }
        return color;
    }
}
