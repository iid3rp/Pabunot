package pabunot.graphics;

import pabunot.InitialFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code Snow} class extends {@link BufferedImage} and is responsible for managing and rendering confetti-like snowflakes
 * in the Pabunot application.
 * It utilizes an {@link ArrayList} of {@link Confetti} objects to simulate snowflakes falling across the screen.
 *
 * <p>Each {@code Confetti} object represents an individual snowflake with its own properties such as position, speed, and color.
 * The snowflakes are dynamically updated and rendered according to the game's environmental factors like wind (snowMultiplierX) and gravity (snowMultiplierY).</p>
 *
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Initializing the snowflake buffer with a predefined number of confetti.</li>
 *   <li>Updating the position of each snowflake based on its speed and the environmental multipliers.</li>
 *   <li>Rendering each snowflake within the bounds of the frame.</li>
 *   <li>Adding new snowflakes periodically and removing the ones that move out of the frame to maintain a constant number of snowflakes.</li>
 * </ul>
 *
 * @see BufferedImage
 * @see Confetti
 * @see InitialFrame
 */
public class Snow extends BufferedImage
{
    private Random random;
    private ArrayList<Confetti> confettiList;

    public Snow()
    {
        super(InitialFrame.WIDTH, InitialFrame.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        random = new Random();
        confettiList = new ArrayList<>();
        for(int i = 0; i < 400; i++)
        {
            confettiList.add(new Confetti(random.nextInt(720)));
        }
    }

    public void moveSnowflakes(long currentTime)
    {
        for(Confetti c : confettiList)
        {
            if(c.position.y + c.speedY <= 740)
            {
                c.position.translate((int) (c.speedX + (1.3 * (InitialFrame.snowMultiplierX - 2.3))),
                        (int) (c.speedY + InitialFrame.snowMultiplierY));

            }
        }
        if(currentTime % 2 == 0)
        {
            confettiList.add(new Confetti());
            confettiList.removeFirst();
        }
    }

    public void render(long currentTime)
    {
        Graphics2D g2d1 = createGraphics();
        g2d1.setComposite(AlphaComposite.Clear);
        g2d1.fillRect(0, 0, getWidth(), getHeight());
        g2d1.dispose();

        Graphics2D g2d = createGraphics();
        for (Confetti c : confettiList)
        {
            if(c.position.x >= -20 && c.position.x <= 1530 && c.position.y <= 740)
            {
                g2d.setColor(c.color);
                g2d.fillRect(c.position.x - 250, c.position.y - 10, c.length, c.length); // Draw snowflake
            }
        }
        g2d.dispose();
        moveSnowflakes(currentTime);
    }
}