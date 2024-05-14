package Pabunot.Graphics;

import java.awt.*;
import java.util.Random;

public class Confetti
{
    Point position;
    public int speedX;
    public int speedY;
    public int length;
    Color color;
    Random r = new Random();

    private Color[] colors = new Color[]
    {
            new Color(253, 79, 79),
            new Color(253, 131, 79),
            new Color(253, 183, 79),
            new Color(253, 230, 79),
            new Color(149, 253, 79),
            new Color(79, 253, 88),
            new Color(79, 253, 160),
            new Color(79, 227, 253),
            new Color(79, 149, 253),
            new Color(102, 79, 253),
            new Color(157, 79, 253),
            new Color(247, 79, 253),
            new Color(253, 79, 183),

    };

    public Confetti()
    {
        color = colors[r.nextInt(colors.length)];
        position = new Point(r.nextInt(1600), 0);
        speedX = r.nextInt(3);
        speedY = r.nextInt(3) + 1;
        length = 7 * speedY;
    }

    public Confetti(int y)
    {
        color = colors[r.nextInt(colors.length)];
        position = new Point(r.nextInt(1500), y);
        speedX = r.nextInt(3);
        speedY = r.nextInt(3) + 1;
        length = 7 * speedY;
    }

}
