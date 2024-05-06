package Pabunot.Graphics;

import Pabunot.InitialFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class Snow extends BufferedImage
{
    private Random random;
    private ArrayList<Confetti> confettiList;

    public Snow()
    {
        super(1280, 720, BufferedImage.TYPE_INT_ARGB);
        random = new Random();
        confettiList = new ArrayList<>();
        for(int i = 0; i < 400; i++)
        {
            confettiList.add(new Confetti(random.nextInt(720)));
        }
    }

    public void moveSnowflakes()
    {
        for (Confetti c : confettiList)
        {
            if(c.position.y + c.speedY <= 720)
            {
                c.position.translate((int) (c.speedX + InitialFrame.snowMultiplierX - 2),
                        (int) (c.speedY + InitialFrame.snowMultiplierY));
            }
        }
    }

    public void render(long currentTime)
    {
        Graphics2D g2d1 = createGraphics();
        g2d1.setComposite(AlphaComposite.Clear);
        g2d1.fillRect(0, 0, getWidth(), getHeight());
        if(currentTime % 50 == 0)
        {
            confettiList.add(new Confetti());
            confettiList.removeFirst();
        }

        Graphics2D g2d = createGraphics();
        for (Confetti c : confettiList)
        {
            g2d.setColor(c.color);
            g2d.fillRect(c.position.x - 250, c.position.y - 10, c.length, c.length); // Draw snowflake
        }
        moveSnowflakes();
    }
}


