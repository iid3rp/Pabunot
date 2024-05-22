package pabunot.graphics;

import pabunot.InitialFrame;

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
        position = new Point(r.nextInt((int) (InitialFrame.WIDTH / InitialFrame.scaleFactor)), -10);
        speedX = (currentFrameIteration % 10) - 5;
        speedY = (currentFrameIteration % 6) + 2;
        length = (int) (7 *  Math.abs(speedY / 2));
        currentFrameIteration++;
    }

    public Confetti(int metric)
    {
        r = new Random();
        color = colors[currentFrameIteration % colors.length];
        position = new Point(r.nextInt((int) (InitialFrame.WIDTH / InitialFrame.scaleFactor)), metric);
        speedX = (currentFrameIteration % 10) - 5;
        speedY = (currentFrameIteration % 6) + 2;
        length = (int) (7 * Math.abs(speedY / 2));
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
