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
    protected static Image image;

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
         return switch(theme) {
            case RED_HEARTS -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/red.png")).getPath());
            case ORANGE_HEARTS -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/orange.png")).getPath());
            case YELLOW_HEARTS -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/yellow.png")).getPath());
            case GREEN_HEARTS ->  new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/green.png")).getPath());
            case BLUE_HEARTS -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/blue.png")).getPath());
            case TEAL_HEARTS -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/teal.png")).getPath());
            case PURPLE_HEARTS ->  new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/purple.png")).getPath());
            case PINK_HEARTS -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/pink.png")).getPath());
            case GRAY_HEARTS ->  new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/gray.png")).getPath());
            case RAINBOW_HEARTS -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/rainbow.png")).getPath());
            case WOOD -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/wood.png")).getPath());
            case ORANGE_FRUIT -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/orangeFruit.png")).getPath());
            case CLOVER -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/clover.png")).getPath());
            case LUCKY -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/lucky.png")).getPath());
            case SUNFLOWER -> new File(Objects.requireNonNull(InitialFrame.class.getResource("Resources/sunflower.png")).getPath());
        };
    }

    public static Theme valueOf(String s)
    {
        return switch(s) {
            case "Red hearts" -> Theme.RED_HEARTS;
            case "Orange hearts" -> Theme.ORANGE_HEARTS;
            case "Yellow hearts" -> Theme.YELLOW_HEARTS;
            case "Green hearts" -> Theme.GREEN_HEARTS;
            case "Blue hearts" -> Theme.BLUE_HEARTS;
            case "Purple hearts" -> Theme.PURPLE_HEARTS;
            case "Pink hearts" -> Theme.PINK_HEARTS;
            case "Gray hearts" -> Theme.GRAY_HEARTS;
            case "Rainbow hearts" -> Theme.RAINBOW_HEARTS;
            case "Teal hearts" -> Theme.TEAL_HEARTS;
            case "Wood flowers" -> Theme.WOOD;
            case "Orange fruit" -> Theme.ORANGE_FRUIT;
            case "Clover leaf" -> Theme.CLOVER;
            case "Lucky block" -> Theme.LUCKY;
            case "Sunflowers" -> Theme.SUNFLOWER;
            default -> throw new IllegalArgumentException("Unknown theme description: " + s);
        };
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