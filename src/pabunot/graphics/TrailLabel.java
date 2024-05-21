package pabunot.graphics;

import pabunot.InitialFrame;
import pabunot.util.AndyBold;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The {@code TrailLabel} class extends {@code ArrayList<JLabel>} to create a visually appealing trail effect with text labels.
 * Each character of the input string is represented as a separate {@code JLabel}, and these labels can be animated to move in a wave-like pattern.
 * This class supports customization of text size, start and end positions on the Y-axis, and color patterns.
 *
 * @author iid3rp
 */
public class TrailLabel extends ArrayList<JLabel>
{
    public char[] sequence;
    public String stringSequence;
    public Color[] colors;
    private int startX;
    private int startY;
    private int x;
    private int endY;
    private int size;

    private int i;

    private int letterLength;
    private int letterDepth;

    public TrailLabel(String s, int size, int startY, int endY)
    {
        //the default color will be rainbow :3
        i = 0;
        letterLength = 0;
        stringSequence = s;
        sequence = s.toCharArray();
        colors = TrailLabel.rainbow;
        this.size = size;
        this.startX = Short.MAX_VALUE;
        this.startY = startY;
        this.endY = endY;
        for(char c : sequence)
        {
            JLabel letter = createLetter(c + "", size);
            add(letter);
        }
    }

    /**
     * The TrailLabel constructor initializes a new instance of the
     * TrailLabel class, designed to display a sequence of characters (s) as
     * individual labels, each potentially with different colors.
     *
     * @param s         the string sequence
     * @param size      the size of the letters
     * @param startY    the starting Y position
     * @param endY      the ending Y position
     * @param colors    the array of colors
     */
    public TrailLabel(String s, int size, int startY, int endY, Color[] colors)
    {
        letterLength = 0;
        stringSequence = s;
        sequence = s.toCharArray();
        this.startX = Short.MAX_VALUE;
        this.startY = startY;
        this.endY = endY;
        this.colors = colors;
        for(char c : sequence)
        {
            JLabel letter = createLetter(c + "", size);
            add(letter);
        }
    }

    public TrailLabel(String s, int size, int x, int startY, int endY, Color[] colors)
    {
        letterLength = 0;
        stringSequence = s;
        sequence = s.toCharArray();
        this.startX = x;
        this.startY = startY;
        this.endY = endY;
        this.colors = colors;
        for(char c : sequence)
        {
            JLabel letter = createLetter(c + "", size);
            add(letter);
        }
    }

    public static Color[] rainbow =
    {
            new Color(255, 89, 89),
            new Color(255, 137, 73),
            new Color(255, 217, 88),
            new Color(115, 255, 74),
            new Color(87, 166, 253),
            new Color(142, 63, 255),
            new Color(255, 73, 255)
    };

    /**
     * Creates a {@code JLabel} representing a single character with specified styling.
     * <p>This method initializes a {@code JLabel} with the character {@code s}, sets its layout to null for manual positioning,
     * and applies double buffering to optimize rendering. The label's foreground color cycles through a predefined array of colors,
     * and its font size is set according to the {@code size} parameter.</p>
     * <ul>
     *     <li>{@code s} - the character to be displayed in the label</li>
     *     <li>{@code size} - the font size of the label</li>
     * </ul>
     * <p>The method calculates the width and height of the label based on the character's size and sets its bounds accordingly.
     * The horizontal position is determined by the cumulative width of previously created labels, ensuring proper spacing between characters.</p>
     *
     * @param s the character string to be displayed in the label
     * @param size the font size for the label
     * @return the created {@code JLabel} with the specified character and styling
     */
    public JLabel createLetter(String s, int size)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s);
        label.setDoubleBuffered(true); // very important!!!

        assert colors != null;
        label.setForeground(s.isBlank()? null : colors[i++ % colors.length]);

        label.setFont(AndyBold.createFont(size));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(s.toUpperCase() + 10);
        int height = metrics.getHeight();

        this.startX = startX == Short.MAX_VALUE? (int) (InitialFrame.WIDTH - metrics.stringWidth(
                stringSequence)) / 2 : startX;

        label.setBounds(startX + letterLength, startY + letterDepth, width, height);
        letterLength += metrics.stringWidth(s) + 1;
        // System.out.println((1280 / 2) - (metrics.stringWidth(stringSequence) / 2)); // debuggers
        return label;
    }

    public void wave(long currentTime)
    {
        int index = 0;
        if(sequence.length == 1)
        {
            get(0).setLocation(get(0).getX(), (int) InitialFrame.sineEase(currentTime, 1_000, startY, endY, 1, 0));
        }
        else
        {
            for(JLabel l : this)
            {
                l.setLocation(l.getX(), (int) InitialFrame.sineEase(currentTime, 1_000, startY, endY, (this.size() - 1) - index,index++));
            }
        }
    }

    public int getEndY()
    {
        return endY;
    }

    public void setEndY(int endY)
    {
        this.endY = endY;
    }

    public int getStartY()
    {
        return startY;
    }

    public void setStartY(int startY)
    {
        this.startY = startY;
    }

    public int getSize()
    {
        return size;
    }
}
