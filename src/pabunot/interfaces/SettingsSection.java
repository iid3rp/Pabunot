package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.graphics.TrailLabel;
import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class SettingsSection extends JPanel
{
    public TrailLabel title;
    public SettingsPane pane;
    JLabel goBack;
    @Intention InitialFrame frame;

    public SettingsSection(InitialFrame frame)
    {
        super();
        this.frame = frame;
        initializeComponent();
        title = new TrailLabel("Settings!", 50, 20, 30, TrailLabel.rainbow);
        pane = new SettingsPane();
        pane.setLocation((getWidth() - pane.getWidth())/2, 100);
        goBack= createGoBack();
        addComponents();
    }

    private JLabel createGoBack()
    {
        JLabel label = new JLabel();
        label.setText("< Back");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.white);

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
                frame.mainMenu.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.yellow);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.white);
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

    private void addComponents()
    {
        for(JLabel l : title)
        {
            add(l);
        }
        add(pane);
        add(goBack);
    }

    private void initializeComponent()
    {
        setLayout(null);
        setOpaque(false);
        setSize(new Dimension(InitialFrame.WIDTH, InitialFrame.HEIGHT));
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.requestFocus();
            }
        });
        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                InitialFrame.parallaxMove(e.getPoint());
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(e.getPoint());
            }
        });
        setVisible(false);
    }
}