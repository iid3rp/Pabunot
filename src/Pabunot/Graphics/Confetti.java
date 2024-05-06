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
            new Color(253, 79, 79, 127),
            new Color(253, 146, 79, 127),
            new Color(253, 204, 79, 127),
            new Color(91, 253, 79, 127),
            new Color(79, 253, 227, 127),
            new Color(79, 93, 253, 127),
            new Color(204, 79, 253, 127),
            new Color(253, 79, 230, 127),
            new Color(107, 107, 107, 127),

    };

    public Confetti()
    {
        color = colors[r.nextInt(colors.length)];
        position = new Point(r.nextInt(1500), 0);
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
