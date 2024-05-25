package pabunot.controls;

import pabunot.InitialFrame;
import pabunot.util.Intention;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class PrizePickedKey implements KeyListener
{
    @Intention InitialFrame frame;
    public PrizePickedKey(InitialFrame frame)
    {
        this.frame = frame;
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
        frame.prizePicked.winPress();
        frame.removeAllKeyListeners();
    }

    @Override
    public void keyPressed(KeyEvent ignored) {}

    @Override
    public void keyReleased(KeyEvent ignored) {}
}