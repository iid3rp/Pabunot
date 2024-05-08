package Pabunot.Pabunot;

import Pabunot.Utils.Theme;
import Pabunot.InitialFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

    public static File selectTheme(Theme theme)
    {
        switch(theme) {
            case RED_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/red.png")).getPath());
            }
            case ORANGE_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/orange.png")).getPath());
            }
            case YELLOW_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/yellow.png")).getPath());
            }
            case GREEN_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/green.png")).getPath());
            }
            case BLUE_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/blue.png")).getPath());
            }
            case PURPLE_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/purple.png")).getPath());
            }
            case PINK_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/pink.png")).getPath());
            }
            case GRAY_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/gray.png")).getPath());
            }
            case RAINBOW_HEARTS ->
            {
                return new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/rainbow.png")).getPath());
            }
            default -> { return null; }
        }

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
