package Pabunot.StreamIO;

import Pabunot.Pabunot.Pabunot;
import Pabunot.Pabunot.PabunotGrid;
import Pabunot.Prize.Prize;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PabunotMaker
{
    public static final String pabunotDir = System.getProperty("user.home") + File.separator + "Pabunot";
    public String title;
    public File fileName;
    public long serial;
    private Random r = new Random();
    private PabunotGrid grid;

    public PabunotMaker(PabunotGrid grid)
    {
        this.grid = grid;
        title = grid.getTitle();
        serial = generateSerial();
        writePabunot();
    }
    
    public long generateSerial()
    {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < 16; i++) {
            sb.append(i == 0 ? r.nextInt(9) + 1 : r.nextInt(10));
        }
        return Long.parseLong(sb.toString());
    }

    public void writePabunot()
    {
        String serialDir = pabunotDir + File.separator + serial;
        var x = new File(serialDir).mkdirs();
        File f = new File(serialDir + File.separator + "Pabunot.ini");

        try
        {
            FileWriter writer = new FileWriter(f);
            writer.write("x:" + grid.getX() + "\n");
            writer.write("y:" + grid.getY() + "\n");
            writer.write("Title:" + title + "\n");
            writer.write("Serial:" + serial + "\n");
            writer.write("Theme:" + grid.theme.toString() + "\n");

            writer.write("Prizes\n");
            for(Prize p : grid.list)
            {
                writer.write(p.getTitle() + ":" + p.getDescription() + "\n");
            }

            writer.write("Pabunot\n");
            for(Pabunot p : grid.grid)
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
        return "Saved file to " + pabunotDir + File.separator + serial + File.separator + "Pabunot.ini";
    }
}
