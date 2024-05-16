package pabunot.hardware;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@Deprecated(since = " xd, will be used when implemented well..")
public class DefaultKeyInput implements KeyListener
{
    @Override
    public void keyTyped(KeyEvent e)
    {
        System.out.print("Hello");
    }

    @Override
    public void keyPressed(KeyEvent e)
    {

    }

    @Override
    public void keyReleased(KeyEvent e)
    {

    }
}
