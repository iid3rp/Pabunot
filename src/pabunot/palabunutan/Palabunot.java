package pabunot.palabunutan;

import pabunot.util.Theme;
import pabunot.InitialFrame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Palabunot
{

    private int value;
    private boolean isPicked;
    protected Image image;

    public int getValue()
    {
        return value;
    }

    public Palabunot(int value)
    {
        this.value = value;
        isPicked = false;
    }

    public Palabunot(int value, Theme theme)
    {
        try {
            image = ImageIO.read(Objects.requireNonNull(selectTheme(theme)));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        this.value = value;
        isPicked = false;
    }
    public Palabunot(int value, boolean isPicked, Theme theme)
    {
        try {
            image = ImageIO.read(Objects.requireNonNull(selectTheme(theme)));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        this.value = value;
        this.isPicked = isPicked;
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
