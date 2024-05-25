package pabunot.palabunutan;

import pabunot.InitialFrame;
import pabunot.util.Intention;
import pabunot.util.Theme;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
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

    @Intention(design = "Using InputStream than file due to URI to File weak reading..")
    public static InputStream selectTheme(Theme theme) {
        return switch(theme) {
            case RED_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/red.png"));
            case ORANGE_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/orange.png"));
            case YELLOW_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/yellow.png"));
            case GREEN_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/green.png"));
            case BLUE_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/blue.png"));
            case TEAL_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/teal.png"));
            case PURPLE_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/purple.png"));
            case PINK_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/pink.png"));
            case GRAY_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/gray.png"));
            case RAINBOW_HEARTS -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/rainbow.png"));
            case WOOD -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/wood.png"));
            case ORANGE_FRUIT -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/orangeFruit.png"));
            case CLOVER -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/clover.png"));
            case LUCKY -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/lucky.png"));
            case SUNFLOWER -> Objects.requireNonNull(InitialFrame.class.getResourceAsStream("Resources/sunflower.png"));
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