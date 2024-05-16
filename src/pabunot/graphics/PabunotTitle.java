package pabunot.graphics;

import pabunot.util.AndyBold;

import javax.swing.*;
import java.awt.*;


public class PabunotTitle
{
    private static int letterLength = 0;
    private static int i;

    public static JLabel p;
    public static JLabel a;
    public static JLabel b;
    public static JLabel u;
    public static JLabel n;
    public static JLabel o;
    public static JLabel t;
    private static int letterDepth;

    public static void setFonts()
    {
        p = createLetter("P");
        a = createLetter("a");
        b = createLetter("b");
        u = createLetter("u");
        n = createLetter("n");
        o = createLetter("o");
        t = createLetter("t");

    }

    private static Color[] rainbow =
    {
        new Color(255, 89, 89),
        new Color(255, 137, 73),
        new Color(255, 217, 88),
        new Color(115, 255, 74),
        new Color(87, 166, 253),
        new Color(142, 63, 255),
        new Color(255, 73, 255)
    };

    public static JLabel createLetter(String s)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s);
        label.setForeground(rainbow[i++]);
        label.setFont(AndyBold.createFont(150));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(s.toUpperCase());
        int height = metrics.getHeight();
        label.setBounds((1280 / 2) - (metrics.stringWidth("pabunot") / 2) + letterLength, 100 + letterDepth, width, height);
        letterLength += metrics.stringWidth(s);
        letterDepth += 3;
        System.out.println(letterLength + 184);
        return label;
    }
}
