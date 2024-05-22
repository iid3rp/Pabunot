package pabunot.graphics;

import pabunot.InitialFrame;
import pabunot.util.Intention;

import java.awt.Graphics2D;
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
public class Snow implements Runnable
{
    public BufferedImage particle;
    private Random random;
    private ArrayList<Confetti> confettiList;
    public boolean isRunning = true;

    public Snow()
    {
        particle = new BufferedImage((int) (InitialFrame.WIDTH / InitialFrame.scaleFactor),
                (int) (InitialFrame.HEIGHT / InitialFrame.scaleFactor), BufferedImage.TYPE_INT_ARGB);
        random = new Random();
        confettiList = new ArrayList<>();
        for(int i = 0; i < 800; i++)
        {
            confettiList.add(new Confetti(random.nextInt((InitialFrame.HEIGHT))));
        }
    }

    @Intention(design = "Confetti when won")
    public Snow(int metric)
    {
        particle = new BufferedImage((int) (InitialFrame.WIDTH / InitialFrame.scaleFactor),
                (int) (InitialFrame.HEIGHT / InitialFrame.scaleFactor), BufferedImage.TYPE_INT_ARGB);
        random = new Random();
        confettiList = new ArrayList<>();
        for(int i = 0; i < 800; i++)
        {
            confettiList.add(new Confetti(metric));
        }
    }

    public void render(long currentTime)
    {
        BufferedImage reference = new BufferedImage(InitialFrame.WIDTH, InitialFrame.HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = reference.createGraphics();
        for (Confetti c : confettiList)
        {
            g2d.setColor(c.color);
            g2d.fillRect(c.position.x + 40, c.position.y, (int) (c.length / InitialFrame.scaleFactor),
                    (int) (c.length / InitialFrame.scaleFactor)); // Draw confetti
        }
        if(currentTime % 50 == 0)
        {
            confettiList.add(new Confetti());
            confettiList.removeFirst();
        }
        g2d.dispose();
        particle = reference;
        moveSnowflakes();
    }

    public void moveSnowflakes()
    {
        for (Confetti c : confettiList)
        {
            if(c.position.y + c.speedY <= InitialFrame.HEIGHT)
            {
                c.position.translate((int) ((c.speedX + InitialFrame.snowMultiplierX / InitialFrame.scaleFactor) / InitialFrame.scaleFactor),
                        (int) ((int) (c.speedY + InitialFrame.snowMultiplierY) / InitialFrame.scaleFactor)); // Move snowflake down
            }
        }
    }

    @Override
    public void run()
    {
        int frames = 0;
        double delta = 0;
        long prevTime = System.nanoTime();
        double tickPerSecond = 1_000_000_000d / 60; // renders at 60 fps for consistency,
        // regardless of the frames in the current render stream of {@code InitialFrame} class
        long currentTime;

        while(isRunning)
        {
            currentTime = System.nanoTime();
            long passedTime = currentTime - prevTime;
            prevTime = currentTime;
            delta += (double) passedTime / tickPerSecond;

            if(delta >= 1)
            {
                delta--;
                render(currentTime);
                frames++;
            }
        }
    }
}