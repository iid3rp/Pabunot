package Pabunot.Pabunot;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class Pabunot
{

    private int value;
    private boolean isPicked;

    protected Image image;

    public int getValue()
    {
        return value;
    }

    public Pabunot(int value)
    {
        this.value = value;
        isPicked = false;
    }
    public Pabunot(int value, String s)
    {
        try {
            image = ImageIO.read(new File(s));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        this.value = value;
        isPicked = false;
    }

    @Deprecated
    public void setValue(int value)
    {
        this.value = value;
    }

    public void setPicked(boolean flag)
    {
        this.isPicked = flag;
    }

    public boolean isPicked()
    {
        return isPicked;
    }
}
