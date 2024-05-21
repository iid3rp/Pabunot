package pabunot.controls;

import pabunot.InitialFrame;
import pabunot.util.Intention;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class MainMenuKey implements KeyListener
{
    @Intention InitialFrame frame;
    public MainMenuKey(InitialFrame frame)
    {
        this.frame = frame;
    }
    @Override
    public void keyTyped(KeyEvent ignored) {}

    @Override
    public void keyPressed(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
        else if(e.getKeyCode() == KeyEvent.VK_ENTER || e.getKeyCode() == KeyEvent.VK_P || e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            frame.play();
        }
    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
