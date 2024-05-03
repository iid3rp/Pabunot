package Pabunot.Graphics;

import Pabunot.InitialFrame;
import Pabunot.Utils.AndyBold;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

@Deprecated
public class TrailLabel extends ArrayList<JLabel>
{
    char[] sequence;
    private int startX;
    private int startY;
    private int x;
    private int y;
    public TrailLabel(String s, int size, int startY, int endY)
    {

    }

    public TrailLabel(String s, int size, int x, int y, int startY, int endY)
    {
        sequence = s.toCharArray();
        this.startY = startY;
        this.y = endY;
        createLabel(size, x, y, startY, endY);
    }

    private void createLabel(int size, int x, int y, int startY, int endY)
    {
        int previousWidth = 0;
        int currentWidth;
        for(char c : sequence)
        {
            JLabel label = new JLabel();
            label.setLayout(null);
            label.setText(c + "");
            label.setForeground(Color.white);
            label.setFont(AndyBold.createFont(size));
            FontMetrics metrics = label.getFontMetrics(label.getFont());
            int width = metrics.stringWidth(Arrays.toString(sequence).toUpperCase());
            int height = metrics.getHeight();
            currentWidth = metrics.stringWidth(label.getText().toUpperCase());
            label.setBounds((1280 / 2) - (metrics.stringWidth(Arrays.toString(sequence)) / 2) + previousWidth, y, previousWidth + currentWidth, height);
            previousWidth += currentWidth;
            System.out.println(previousWidth);

            add(label);
        }
    }

    public void wave(long currentTime)
    {
        int index = 0;
        for(JLabel l : this)
        {
            l.setLocation(PabunotTitle.p.getX(), (int) InitialFrame.sineEaseY(currentTime, 1_000_000_000, startY, y, index, this.size() - index++));
        }
    }
}
