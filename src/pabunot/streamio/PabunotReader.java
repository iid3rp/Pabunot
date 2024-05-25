package pabunot.streamio;


import pabunot.InitialFrame;
import pabunot.interfaces.PabunotSection;
import pabunot.interfaces.SettingsPane;
import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.prize.Prize;
import pabunot.prize.PrizeList;
import pabunot.util.Intention;
import pabunot.util.Theme;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;

/**
 * The {@code PabunotReader} class provides functionalities for reading data from files
 * and constructing game elements such as {@code PabunotSection}
 * and {@code PalabunotGrid}.
 * This class is essential
 * for initializing the game state with predefined configurations and
 * prize setups stored in external files.
 *<p>
 * The class supports operations to read various configurations and elements
 * including grid dimensions,
 * themes, prizes, and individual {@code Palabunot} statuses from a file.
 * It handles file I/O operations
 * and ensures that the data is correctly parsed and instantiated into the game's objects.
 *
 * @author Francis (iid3rp) Madanlo
 */
public class PabunotReader
{
    /**
     * Constructs a `PabunotReader` object. (Default constructor with no arguments)
     */
    public PabunotReader() {}

    public static void readInitialization()
    {
        File file = new File(PabunotMaker.pabunotDir + File.separator + "Settings.ini");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] setting = line.split(":");
                if (setting.length == 2) {
                    switch (setting[0].trim()) {
                        case "fpsCountVisibility":
                            SettingsPane.fpsCountVisibility =
                                    Boolean.parseBoolean(setting[1].trim());
                            break;
                        case "parallax":
                            SettingsPane.parallax =
                                    Boolean.parseBoolean(setting[1].trim());
                            break;
                        case "fpsUnlocker":
                            SettingsPane.fpsUnlock =
                                    Boolean.parseBoolean(setting[1].trim());
                            break;
                        case "fpsFrameCap":
                            InitialFrame.refreshRate =
                                    Integer.parseInt(setting[1].trim());
                            break;
                        case "graphicsQuality":
                            SettingsPane.graphicsQuality = setting[1];
                            break;
                        case "trailWave":
                            SettingsPane.waveTrail = Boolean.parseBoolean(setting[1].trim());
                            break;
                        case "snowVisible":
                            SettingsPane.snowVisible = Boolean.parseBoolean(setting[1].trim());
                            break;
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads data from a specified file and creates a
     * {@code PabunotSection} with a {@code PabunotGrid}
     * containing prizes and pabunots.
     * <p>
     * Steps:
     * <ul>
     * <li>Reads configuration data from the file to set up the
     * grid dimensions, title, theme, and serial number.</li>
     * <li>Processes prize data to populate a {@code PrizeList}.</li>
     * <li>Reads and creates {@code Palabunot} objects based on the
     * remaining file content.</li>
     * <li>Handles exceptions by displaying an error message and returning
     * null if file reading fails.</li>
     * </ul>
     *
     * @param frame A reference to an {@code InitialFrame} object, used for
     *              UI interactions during the reading process.
     * @param file The {@code File} object representing the data file to be read.
     * @return A {@code PabunotSection} containing the constructed {@code PabunotGrid},
     * or null if an error occurs.
     */
    @Deprecated
    public PabunotSection createPabunotFromFile(InitialFrame frame, File file)
    {
        try {
            // FileWriter ug BufferedReader
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int x = Integer.parseInt(reader.readLine().split(":")[1]);
            int y = Integer.parseInt(reader.readLine().split(":")[1]);
            String title = reader.readLine().split(":")[1];
            long serial = Long.parseLong(reader.readLine().split(":")[1]);
            Theme theme = Theme.valueOf(reader.readLine().split(":")[1]);

            reader.readLine(); // skip

            String line = reader.readLine();
            while(!line.equalsIgnoreCase("pabunot"))
            {
                String[] s = line.split(":");

                System.out.println(line);
                line = reader.readLine();
            }

            reader.readLine(); // skip
            line = reader.readLine();
            while(line != null)
            {
                String[] s = line.split(":");
                line = reader.readLine();
            }
            return null;
        }
        catch(InputMismatchException |
              IOException |
              ArrayIndexOutOfBoundsException ignored)
        {
            return null;
        }
    }

    /**
     * Creates a {@code Pabunot} object from a file. This method reads the
     * file to configure and populate a {@code PalabunotGrid} with {@code Palabunot}
     * objects and associated prizes.
     * <p>
     * Steps involved:
     * <ul>
     * <li>Initialize a {@code PrizeList}.</li>
     * <li>Open and read the file using a {@code BufferedReader}.</li>
     * <li>Extract grid dimensions, title, serial number, and theme from the file.</li>
     * <li>Read and add prizes to the {@code PrizeList} until the "pabunot" marker is
     * encountered.</li>
     * <li>Create a {@code PalabunotGrid} with the read configuration and prizes.</li>
     * <li>Read and add {@code Palabunot} objects to the grid.</li>
     * <li>Close the reader and return the constructed {@code Pabunot}.</li>
     * </ul>
     * <p>
     * If any errors occur during file reading or processing,
     * such as {@link InputMismatchException}, {@link IOException}, or
     * {@link ArrayIndexOutOfBoundsException}, the method returns {@code null}.
     *
     * @param file the path to the data file as a {@code String}
     * @return a {@code Pabunot} object containing the configured grid
     * and prizes, or {@code null} if an error occurs
     */
    public static PalabunotGrid createPalabunotFromFile(String file)
    {
        try
        {
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
            while(!line.equalsIgnoreCase("pabunot"))
            {
                String[] s = line.split(":");
                int num = Integer.parseInt(s[2]);
                list.add(new Prize(s[0], s[1], num));
                System.out.println(line);
                line = reader.readLine();
            }

            // extra stretch...
            if(list.isEmpty())
            {
                reader.close();
                @Intention var del = new File(file).delete();
                @Intention String s = file.replace(File.separator + "Settings.ini", "");
                @Intention var b = new File(s).delete();
                return null;
            }

            ArrayList<Palabunot> palabunotList = new ArrayList<>();
            line = reader.readLine();
            int xy = 0;
            while(line != null)
            {
                String[] s = line.split(":");
                palabunotList.add(new Palabunot(Integer.parseInt(s[0]),
                        Boolean.parseBoolean(s[1]), theme));
                line = reader.readLine();
            }
            reader.close();
            return new PalabunotGrid(x, y, palabunotList, serial, title, list, theme);
        }
        catch(InputMismatchException | IOException | ArrayIndexOutOfBoundsException ignored)
        {
            return null;
        }
    }
}
