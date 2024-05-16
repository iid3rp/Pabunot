package pabunot.streamio;


import pabunot.InitialFrame;
import pabunot.interfaces.PabunotSection;
import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.prize.Prize;
import pabunot.pabunot.Pabunot;
import pabunot.prize.PrizeList;
import pabunot.util.Theme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.InputMismatchException;

/**
 * The `PabunotReader` class provides functionalities for reading and processing bank data.
 *
 * @author Francis (iid3rp) Madanlo
 */
public class PabunotReader
{
    /**
     * Constructs a `PabunotReader` object. (Default constructor with no arguments)
     */
    public PabunotReader() {}

    /**
     * Reads data from a specified file and creates a PabunotSection with a PabunotGrid containing prizes and pabunots.
     * <p>
     * Steps:
     * <ul>
     * <li>Reads configuration data from the file to set up the grid dimensions, title, theme, and serial number.</li>
     * <li>Processes prize data to populate a PrizeList.</li>
     * <li>Reads and creates Pabunot objects based on the remaining file content.</li>
     * <li>Handles exceptions by displaying an error message and returning null if file reading fails.</li>
     * </ul>
     *
     * @param frame A reference to an InitialFrame object, used for UI interactions during the reading process.
     * @param file The File object representing the data file to be read.
     * @return A PabunotSection containing the constructed PabunotGrid, or null if an error occurs.
     */
    public PabunotSection createPabunotFromFile(InitialFrame frame, File file)
    {
        try {
            PrizeList list = new PrizeList();
            // FileWriter ug BufferedReader
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int x = Integer.parseInt(reader.readLine().split(":")[1]);
            int y = Integer.parseInt(reader.readLine().split(":")[1]);
            String title = reader.readLine().split(":")[1];
            long serial = Long.parseLong(reader.readLine().split(":")[1]);
            Theme theme = Theme.valueOf(reader.readLine().split(":")[1]);

            reader.readLine(); // skip

            String line = reader.readLine();
            while(!line.equals("pabunot"))
            {
                String[] s = line.split(":");
                list.add(new Prize(s[0], s[1]));
                System.out.println(line);
                line = reader.readLine();
            }

            reader.readLine(); // skip

            PalabunotGrid grid = new PalabunotGrid(x, y, title, list, theme);

            line = reader.readLine();
            while(line != null)
            {
                String[] s = line.split(":");
                grid.add(new Palabunot(Integer.parseInt(s[0]), Boolean.parseBoolean(s[1]), theme));
                line = reader.readLine();
            }
            return new PabunotSection(frame, grid);
        }
        catch(InputMismatchException | IOException | ArrayIndexOutOfBoundsException ignored)
        {
            return null;
        }
    }

    public static Pabunot createPalabunotFromFile(String file)
    {
        try {
            PrizeList list = new PrizeList();
            // FileWriter ug BufferedReader
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int x = Integer.parseInt(reader.readLine().split(":")[1]);
            int y = Integer.parseInt(reader.readLine().split(":")[1]);
            String title = reader.readLine().split(":")[1];
            long serial = Long.parseLong(reader.readLine().split(":")[1]);
            Theme theme = Theme.valueOf(reader.readLine().split(":")[1]);

            reader.readLine(); // skip

            String line = reader.readLine();
            while(!line.equals("pabunot"))
            {
                String[] s = line.split(":");
                list.add(new Prize(s[0], s[1]));
                System.out.println(line);
                line = reader.readLine();
            }

            reader.readLine(); // skip

            PalabunotGrid grid = new PalabunotGrid(x, y, title, list, theme);

            line = reader.readLine();
            while(line != null)
            {
                String[] s = line.split(":");
                grid.add(new Palabunot(Integer.parseInt(s[0]), Boolean.parseBoolean(s[1]), theme));
                line = reader.readLine();
            }
            reader.close();
            return new Pabunot(x, y, title, list, theme);
        }
        catch(InputMismatchException | IOException | ArrayIndexOutOfBoundsException ignored)
        {
            return null;
        }
    }
}