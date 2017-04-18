import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Level extends JPanel{
    HashMap<Character, Color> colorMap;
    HashSet<Character> chars = new HashSet<>();
    char[][] grid;

    public void gridFromFile(String filename) throws IOException {
        FileReader fr = new FileReader(filename);
        BufferedReader br = new BufferedReader(fr);
        ArrayList<String> tmpGrid = new ArrayList<>();

        String line = br.readLine();
        while (line != null){

            tmpGrid.add(line);

            try {
                line = br.readLine();
            } catch (IOException e){
                e.printStackTrace();
            }
        }

        int rows = tmpGrid.size();
        int cols = tmpGrid.get(0).length();

        grid = new char[rows][cols];

        for (int row = 0; row < rows; row++){
            String rowString = tmpGrid.get(row);
            for (int col = 0; col < cols; col++){
                char c = rowString.charAt(col);
                chars.add(c);
                grid[row][col] = c;
            }
        }
    }

    private Color[] generateNColors(int nCols){
        Color[] colors = new Color[nCols];
        Random rand = new Random();

        int idx = 0;

        for(int i = 0; i < 360; i += 360 / nCols) {

            float h = i;
            float s = 90 + rand.nextFloat() * 10;
            float l = 50 + rand.nextFloat() * 10;
            HSLColor c = new HSLColor(i,s,l);

            colors[idx] = c.getRGB();
            idx++;
        }
        return colors;
    }

    public Level() throws IOException {

        gridFromFile("tunnelOutput.txt");

        // Stoerrelse paa vinduet
        setPreferredSize(new Dimension(800, 600));

        // Fyld colorMap med de forskellige chars og deres farve
        colorMap = new HashMap<>();

        Color[] colors = generateNColors(chars.size());

        Iterator<Character> it = chars.iterator();

        for (int i = 0; i < chars.size(); i++){
            colorMap.put(it.next(),colors[i]);
        }
        colorMap.put('+',Color.BLACK);
        colorMap.put(' ',Color.WHITE);

        //colorMap.put('*', new Color(0,0,0));
        //colorMap.put(' ', new Color(255, 255, 255));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.clearRect(0,0,getWidth(), getHeight());

        // Size of a single cell
        int dim = 20;
        for(int i = 0; i < grid.length; ++i)
        {
            for(int j = 0; j < grid[0].length; ++j)
            {
                Color c = colorMap.get(grid[i][j]);
                g.setColor(c);
                g.fillRect(j * dim, i * dim, dim, dim);
            }
        }
    }
}
