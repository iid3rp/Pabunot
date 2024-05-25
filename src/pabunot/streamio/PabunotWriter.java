package pabunot.streamio;

import pabunot.InitialFrame;
import pabunot.interfaces.SettingsPane;
import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGridPane;
import pabunot.prize.Prize;
import pabunot.util.Intention;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The PabunotWriter class provides functionality to write the state of a PalabunotGridPane to a file.
 * This includes details such as grid dimensions, title, serial number, theme, and the list of prizes.
 * It is designed to create a persistent record of the game state that can be used for later retrieval or analysis.
 *
 * @author Francis (iid3rp) Madanlo
 */
public class PabunotWriter
{
    /**
     * Writes the details of a PalabunotGridPane object to a file in the Pabunot directory.
     * Includes information such as grid dimensions, title, serial, theme, prizes, and grid contents.
     * If the prize list is not null, it iterates over each prize and writes its title, description, and number.
     * Finally, it writes the grid contents with each Palabunot value and picked status to the file.
     */
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
            writer.write("Theme:" + pane.grid.currentTheme.toString() + "\n");

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

    public static void writeInitialization()
    {
        File file = new File(PabunotMaker.pabunotDir + File.separator + "Settings.ini");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("Settings\n");
            writer.write("fpsCountVisibility:" + SettingsPane.fpsCountVisibility +"\n");
            writer.write("parallax:" + SettingsPane.parallax + "\n");
            writer.write("fpsUnlocker:" + SettingsPane.fpsUnlock + "\n");
            writer.write("fpsFrameCap:" + InitialFrame.refreshRate + "\n");
            writer.write("graphicsQuality:" + SettingsPane.graphicsQuality + "\n");
            writer.write("trailWave:" + SettingsPane.waveTrail + "\n");
            writer.write("snowVisible:" + SettingsPane.snowVisible + "\n");
            writer.close();
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }

    }
}
