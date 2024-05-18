package pabunot.streamio;

import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.prize.Prize;
import pabunot.util.Intention;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Random;

public class PabunotMaker
{
    public static final String pabunotDir = System.getProperty("user.home") + File.separator + "Pabunot";
    public File fileName;
    private static Random r = new Random();
    private @Intention PalabunotGrid pb;

    public PabunotMaker(PalabunotGrid p)
    {
        pb = p;
        generatePrizeNumbers();
        writePabunot();
    }
    
    public static long generateSerial()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 16; i++) {
            sb.append(i == 0 ? r.nextInt(9) + 1 : r.nextInt(10));
        }
        return Long.parseLong(sb.toString());
    }

    /**
     * Shuffles the elements in the `pb.grid`
     * array and assigns each element's value to the corresponding `Prize` object in the `pb.list`.
     * This method first generates a random number to determine the number of times the grid array will be shuffled.
     * Then, it shuffles the grid array that many times to randomize the order of elements.
     * Finally, it iterates through the `pb.list` of prizes, assigning each prize a number from the shuffled grid.
     */
    public void generatePrizeNumbers()
    {
        if(pb.prizeList != null)
        {
            for(int i = 0; i <  new Random().nextInt(10); i++)
            {
                Collections.shuffle(pb.grid);
            }
            for(int i = 0; i < pb.prizeList.size(); i++)
            {
                pb.prizeList.get(i).setNumber(pb.grid.get(i).getValue());
            }
        }
    }

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
            writer.write("Theme:" + pb.theme.toString() + "\n");

            writer.write("Prizes\n");
            if(pb.prizeList != null) {
                for(Prize p : pb.prizeList)
                {
                    writer.write(p.getTitle() + ":" + p.getDescription() + ":" + p.getNumber() + "\n");
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

    @Override
    public String toString()
    {
        return "Saved file to " + pabunotDir + File.separator + pb.getSerial() + File.separator + "Pabunot.ini";
    }
}
