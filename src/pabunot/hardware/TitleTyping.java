package pabunot.hardware;

import pabunot.InitialFrame;
import pabunot.graphics.TrailLabel;
import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TitleTyping implements KeyListener
{
    @Intention InitialFrame frame;
    private JPanel panel;
    private TrailLabel title;
    int startY;
    int endY;
    public String string;

    public TitleTyping(InitialFrame frame)
    {
        super();
        string = "";
        this.frame = frame;
        FontMetrics metrics = frame.getFontMetrics(AndyBold.createFont(100));
        int half = (frame.getHeight() - metrics.getHeight()) / 2;
        startY = half + 10;
        endY = half - 10;
    }
    @Override
    public void keyTyped(KeyEvent ignored) {}
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(string.length() < 30)
        {
            if(Character.isLetterOrDigit(e.getKeyChar()))
            {
                string += e.getKeyChar();
                updateTitleLabel();
            }
            else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE && !string.isEmpty())
            {
                string = string.substring(0, string.length() - 1);
                updateTitleLabel();
            }
            else if (e.getKeyChar() != KeyEvent.CHAR_UNDEFINED && e.getKeyChar() != KeyEvent.VK_BACK_SPACE) {
                string += e.getKeyChar();
                updateTitleLabel();
            }
            else if(e.getKeyCode() == KeyEvent.VK_SPACE)
            {
                string += " ";
                updateTitleLabel();
            }
            else updateTitleLabel();
        }
        else if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
        {
            string = string.substring(0, string.length() - 1);
            updateTitleLabel();
        }
    }

    private void updateTitleLabel()
    {
        if(string.isEmpty())
        {
            for (JLabel label : frame.titlePanel.title)
            {
                frame.titlePanel.remove(label);
            }
            frame.titlePanel.title = new TrailLabel("Type your title here", 100, startY, endY, new Color[] {new Color(127, 127, 127)});
            for (JLabel label : frame.titlePanel.title)
            {
                frame.titlePanel.add(label);
            }
        }
        else if(string.length() <= 25)
        {
            for (JLabel label : frame.titlePanel.title)
            {
                frame.titlePanel.remove(label);
            }
            frame.titlePanel.title = new TrailLabel(string, 100, startY, endY, TrailLabel.rainbow);
            for (JLabel label : frame.titlePanel.title)
            {
                frame.titlePanel.add(label);
            }
        }
        else
        {
            for (JLabel label : frame.titlePanel.title)
            {
                frame.titlePanel.remove(label);
            }
            frame.titlePanel.title = new TrailLabel(string, 100, startY, endY, new Color[] {Color.red});
            for (JLabel label : frame.titlePanel.title)
            {
                frame.titlePanel.add(label);
            }
        }
        frame.titlePanel.revalidate();
        frame.titlePanel.repaint();
    }


    @Override
    public void keyReleased(KeyEvent ignored) {}
}
