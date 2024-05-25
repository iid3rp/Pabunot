package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.graphics.TrailLabel;
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
    private JLabel goBack;
    public PabunotPrizeListPane prizeListPane;
    public TrailLabel prizeLabel;
    private @Intention InitialFrame frame;
    private @Intention PabunotSection panel;

    public PabunotSection(InitialFrame frame, PalabunotGrid grid)
    {
        super();
        this.grid = grid;
        this.frame = frame;
        prizeLabel = new TrailLabel("Prizes here", 60, 30, 100, 110, TrailLabel.rainbow);
        initializeComponent();
        pane = new PalabunotGridPane(frame, grid);
        pane.setLocation((int) (InitialFrame.WIDTH * 0.4), (InitialFrame.HEIGHT - pane.getHeight()) / 2);
        goBack = createGoBack();
        prizeListPane = new PabunotPrizeListPane(frame, grid.prizeList);

        add(pane);
        add(prizeListPane);
        add(goBack);
        for(JLabel label : prizeLabel)
        {
            add(label);
        }
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
        setSize(new Dimension(InitialFrame.WIDTH, InitialFrame.HEIGHT));
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
            public void mouseExited(MouseEvent e)
            {
                InitialFrame.parallaxX = -24;
                InitialFrame.parallaxY = -18;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
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
        g.setColor(new Color(66, 32, 10, 90));
        g.fillRect(0, 0, InitialFrame.WIDTH, InitialFrame.HEIGHT);
    }
}