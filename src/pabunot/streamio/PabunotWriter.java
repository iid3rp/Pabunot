package pabunot.streamio;

import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGridPane;
import pabunot.prize.Prize;
import pabunot.util.Intention;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PabunotWriter
{
    public static void writePabunot(PalabunotGridPane pane)
    {
        String serialDir = PabunotMaker.pabunotDir + File.separator + pane.grid.getSerial();
        @Intention var x = new File(serialDir).mkdirs();
        File f = new File(serialDir + File.separator + "Pabunot.ini");
        try
        {
            FileWriter writer = new FileWriter(f);
            writer.write("x:" + pane.grid.getX() + "\n");
            writer.write("y:" + pane.grid.getY() + "\n");
            writer.write("Title:" + pane.grid.getTitle() + "\n");
            writer.write("Serial:" + pane.grid.getSerial() + "\n");
            writer.write("Theme:" + pane.grid.theme.toString() + "\n");

            writer.write("Prizes\n");
            if(pane.grid.prizeList != null) {
                for(Prize p : pane.grid.prizeList)
                {
                    writer.write(p.getTitle() + ":" + p.getDescription() + ":" + p.getNumber() + "\n");
                }
            }

            writer.write("Pabunot\n");
            for(Palabunot p : pane.grid.grid)
            {
                writer.write(p.getValue() + ":" + p.isPicked() + "\n");
            }
            writer.close();
        }
        catch(IOException ignored) {}
    }
}
