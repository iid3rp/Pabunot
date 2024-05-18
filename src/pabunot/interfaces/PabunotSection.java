package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.palabunutan.PalabunotGridPane;
import pabunot.streamio.PabunotWriter;
import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PabunotSection extends JPanel
{
    public PalabunotGridPane pane;
    public PalabunotGrid grid;
    public JLabel randomize;
    private JLabel goBack;
    private @Intention InitialFrame frame;
    private @Intention PabunotSection panel;

    public PabunotSection(InitialFrame frame, PalabunotGrid grid)
    {
        super();
        this.grid = grid;
        this.frame = frame;
        initializeComponent();
        pane = new PalabunotGridPane(frame, grid);
        goBack = createGoBack();
        pane.setLocation((getWidth() - pane.getWidth()) / 2, (getHeight() - pane.getHeight()) / 2);
        add(pane);
        add(goBack);
        // setting the prize here:


    }

    private JLabel createGoBack()
    {
        JLabel label = new JLabel();
        label.setText("< Back");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.gray);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(20, 20, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setVisible(false);
                frame.picker.setVisible(true);
                PabunotWriter.writePabunot(pane);
                frame.picker.pane.refresh();
                frame.mainMenu.remove(frame.section);

            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.yellow);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.gray);
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(e.getX() + label.getX(), e.getY() + label.getY()));
            }
        });
        return label;
    }

    private void initializeComponent()
    {
        setSize(new Dimension(1280, 720));
        setLayout(null);
        setOpaque(false);
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
                InitialFrame.snowX = -24;
                InitialFrame.snowY = -18;
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
    public void paintComponent(Graphics ignored) {}
}
