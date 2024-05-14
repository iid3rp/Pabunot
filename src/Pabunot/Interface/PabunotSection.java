package Pabunot.Interface;

import Pabunot.InitialFrame;
import Pabunot.Pabunot.PabunotGrid;
import Pabunot.Pabunot.PabunotGridPane;
import Pabunot.Utils.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PabunotSection extends JPanel
{
    public PabunotGridPane pane;
    public PabunotGrid grid;
    public JLabel randomize;
    @Intention InitialFrame frame;

    public PabunotSection(InitialFrame frame, PabunotGrid grid)
    {
        super();
        this.grid = grid;
        this.frame = frame;
        setSize(new Dimension(1280, 720));
        setLayout(null);
        setLocation(0, 0);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.requestFocus();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    InitialFrame.isDragging = true;
                    InitialFrame.offset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    InitialFrame.isDragging = false;
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                InitialFrame.x = -24;
                InitialFrame.y = -18;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if(InitialFrame.isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - InitialFrame.offset.x;
                    int deltaY = currentMouse.y - InitialFrame.offset.y;

                    frame.setLocation(deltaX, deltaY);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(e.getPoint());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g)
    {
        InitialFrame.render(g);
    }
}