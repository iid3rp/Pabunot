package pabunot.graphics;

import java.awt.*;
import java.util.Random;

public class Confetti
{
    Point position;
    public int speedX;
    public int speedY;
    public int length;
    Color color;
    Random r;
    public static int currentFrameIteration = 0;

    private Color[] colors = new Color[]
    {
            new Color(253, 79, 79, 125),
            new Color(253, 131, 79, 125),
            new Color(253, 183, 79, 125),
            new Color(253, 230, 79, 125),
            new Color(149, 253, 79, 125),
            new Color(79, 253, 88, 125),
            new Color(79, 253, 160, 125),
            new Color(79, 227, 253, 125),
            new Color(79, 149, 253, 125),
            new Color(102, 79, 253, 125),
            new Color(157, 79, 253, 125),
            new Color(247, 79, 253, 125),
            new Color(253, 79, 183, 125),

    };

    public Confetti()
    {
        r = new Random();
        color = colors[currentFrameIteration % colors.length];
        position = new Point(r.nextInt(2000), -10);
        speedX = (currentFrameIteration % 6) - 3;
        speedY = (currentFrameIteration % 3) + 1;
        length = 7 *  Math.abs(speedY);
        currentFrameIteration++;
    }

    public Confetti(int y)
    {
        r = new Random();
        color = colors[currentFrameIteration % colors.length];
        position = new Point(r.nextInt(2000), y);
        speedX = (currentFrameIteration % 6) - 3;
        speedY = (currentFrameIteration % 3) + 1;
        length = 7 * Math.abs(speedY);
        currentFrameIteration++;
    }

    @Override
    public String toString()
    {
        return "Confetti{" +
                "length=" + speedX +
                '}';
    }
}
