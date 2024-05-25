package pabunot.streamio;

import pabunot.InitialFrame;
import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.prize.Prize;
import pabunot.util.Intention;
import pabunot.util.RandomRange;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class is responsible for creating and
 * managing the Pabunot game setup.
 * It handles the generation of prize numbers and the
 * writing of game configurations to a file.
 */
public class PabunotMaker
{
    public static final String pabunotDir =
            System.getProperty("user.home") + File.separator + "Pabunot";
    public File fileName;
    private static Random r = new Random();
    private @Intention PalabunotGrid pb;

    /**
     * Constructor for PabunotMaker.
     * Initializes the game grid and starts the
     * process of generating prize numbers and writing
     * the game setup to a file.
     *
     * @param p The PalabunotGrid object that contains
     *          the game grid and prize list.
     */
    public PabunotMaker(PalabunotGrid p)
    {
        pb = p;
        generatePrizeNumbers();
        writePabunot();
    }

    /**
     * Generates a unique serial number for the game session.
     *
     * @return A long value representing the serial number.
     */
    public static long generateSerial()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 16; i++) {
            sb.append(i == 0 ? r.nextInt(9) + 1 : r.nextInt(10));
        }
        return Long.parseLong(sb.toString());
    }

    public static void setNewInitialization()
    {
        File file = new File(pabunotDir + File.separator + "Settings.ini");
        if(!file.exists())
        {
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("Settings\n");
                writer.write("fpsCountVisibility:false\n");
                writer.write("parallax:true\n");
                writer.write("fpsUnlocker:false\n");
                writer.write("fpsFrameCap:" + InitialFrame.refreshRate + "\n");
                writer.write("graphicsQuality:Medium\n");
                writer.write("trailWave:true\n");
                writer.write("snowVisible:true\n");
                writer.close();
            }
            catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Shuffles the elements in the `pb.grid`
     * array and assigns each element's value to the
     * corresponding `Prize` object in the `pb.list`.
     * This method first generates a random number to determine
     * the number of times the grid array will be shuffled.
     * Then, it shuffles the grid array that many times to randomize
     * the order of elements.
     * Finally, it iterates through the `pb.list` of prizes,
     * assigning each prize a number from the shuffled grid.
     */
    public void generatePrizeNumbers()
    {
        ArrayList<Integer> range = new RandomRange(1, pb.getX()* pb.getY());
        if(pb.prizeList != null)
        {
            for(int i = 0; i <  new Random().nextInt(10); i++)
            {
                Collections.shuffle(range);
            }
            for(int i = 0; i < pb.prizeList.size(); i++)
            {
                pb.prizeList.get(i).setNumber(range.get(i));
            }
        }
    }

    /**
     * Shuffles the elements in the {@code pb.grid} array and assigns
     * each element's value to the corresponding {@code Prize}
     * object in the {@code pb.list}.
     * This method first generates a random number to determine the number of
     * times the grid array will be shuffled.
     * Then, it shuffles the grid array that many times to randomize the order of elements.
     * Finally, it iterates through the {@code pb.list} of prizes, assigning each
     * prize a number from the shuffled grid.
     */
    public void writePabunot()
    {
        String serialDir = pabunotDir + File.separator + pb.getSerial();
        var x = new File(serialDir).mkdirs();
        File f = new File(serialDir + File.separator + "Pabunot.ini");

        try
        {
            FileWriter writer = new FileWriter(f);
            writer.write("x:" + pb.getX() + "\n");
            writer.write("y:" + pb.getY() + "\n");
            writer.write("Title:" + pb.getTitle() + "\n");
            writer.write("Serial:" + pb.getSerial() + "\n");
            writer.write("Theme:" + pb.currentTheme.toString() + "\n");

            writer.write("Prizes\n");
            if(pb.prizeList != null) {
                for(Prize p : pb.prizeList)
                {
                    writer.write(
                            p.getTitle() + ":" +
                            p.getDescription() + ":" +
                                    p.getNumber() + "\n");
                }
            }

            writer.write("Pabunot\n");
            for(Palabunot p : pb.grid)
            {
                writer.write(p.getValue() + ":" + p.isPicked() + "\n");
            }
            writer.close();
        }
        catch(IOException ignored) {}
    }

    /**
     * Provides a string representation of the object,
     * indicating where the game configuration file was saved.
     *
     * @return A string detailing the save location of the
     * game configuration.
     */
    @Override
    public String toString()
    {
        return "Saved file to " + pabunotDir + File.separator + pb.getSerial() +
                File.separator + "Pabunot.ini";
    }
}