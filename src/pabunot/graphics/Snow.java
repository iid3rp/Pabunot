package pabunot.graphics;

import pabunot.InitialFrame;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code Snow} class is responsible for managing and rendering confetti-like snowflakes
 * in the Pabunot application.
 * It uses an {@link ArrayList} of {@link Confetti} objects to simulate snowflakes falling across the screen.
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
public class Snow
{
    public BufferedImage particle;
    private Random random;
    private ArrayList<Confetti> confettiList;

    public Snow()
    {
        particle = new BufferedImage(InitialFrame.WIDTH, InitialFrame.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        random = new Random();
        confettiList = new ArrayList<>();
        for(int i = 0; i < 800; i++)
        {
            confettiList.add(new Confetti(random.nextInt(InitialFrame.HEIGHT)));
        }
    }

    public void render(long currentTime)
    {
        particle = new BufferedImage(InitialFrame.WIDTH, InitialFrame.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = particle.createGraphics();
        for (Confetti c : confettiList)
        {
            g2d.setColor(c.color);
            g2d.fillRect(c.position.x - 250, c.position.y, c.length, c.length); // Draw confetti
            // Update position based directly on snow multipliers which are influenced by cursor location
            c.position.translate(
                    (int) (c.speedX + InitialFrame.snowMultiplierX), // Incorporate horizontal movement influenced by cursor
                    (int) (c.speedY + InitialFrame.snowMultiplierY)  // Incorporate vertical movement (like gravity or wind)
            );

        }
        g2d.dispose();
        confettiList.add(new Confetti());
        confettiList.removeFirst();

    }
}