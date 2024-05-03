package Pabunot.StreamIO;

import java.io.File;
import java.util.ArrayList;

public class PabunotSearcher extends ArrayList<String>
{

    private static final String directory = System.getProperty("user.home") + File.separator + "Pabunot";

    public PabunotSearcher()
    {
        File pabunotFolder = new File(directory);
        if(!pabunotFolder.exists())
        {
            var x = pabunotFolder.mkdirs();
        }
        File[] files = pabunotFolder.listFiles();

        if(files != null) {
            for(File f : files)
            {
                add(f.getAbsolutePath());
            }
        }
    }
}
