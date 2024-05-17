package pabunot.graphics;

import pabunot.InitialFrame;
import pabunot.util.AndyBold;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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

    public JLabel createLetter(String s, int size)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s);
        label.setDoubleBuffered(true); // very important!!!

        assert colors != null;
        label.setForeground(colors[i++ % colors.length]);

        label.setFont(AndyBold.createFont(size));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(s.toUpperCase() + 10);
        int height = metrics.getHeight();

        this.startX = startX == Short.MAX_VALUE? (1280 / 2) - ((metrics.stringWidth(stringSequence) / 2)) : startX;

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
            get(0).setLocation(get(0).getX(), (int) InitialFrame.sineEase(currentTime, 1_000_000_000, startY, endY, 1, 0));
        }
        else
        {
            for(JLabel l : this)
            {
                l.setLocation(l.getX(), (int) InitialFrame.sineEase(currentTime, 1_000_000_000, startY, endY, (this.size() - 1) - index,index++));
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
