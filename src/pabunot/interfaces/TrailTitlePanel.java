package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.graphics.TrailLabel;
import pabunot.hardware.TitleTyping;
import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class TrailTitlePanel extends JPanel
{
    private JLabel goBack;
    public TrailLabel title;
    @Intention InitialFrame frame;
    private JLabel makePabunot;
    TitleTyping typeEvent;
    public TrailLabel titleLabel;

    public TrailTitlePanel(InitialFrame frame)
    {
        super();
        this.frame = frame;
        goBack = createGoBack();
        makePabunot = createMakingPabunot();
        typeEvent = new TitleTyping(frame);
        titleLabel = new TrailLabel("Lets call your new Pabunot!", 30, 200, 210, new Color[] {Color.gray});
        title = new TrailLabel("Type your title here", 100, 300, 320, new Color[] {new Color(127, 127, 127)});
        initializeComponent();
        addComponents();
        repaint();
    }

    private JLabel createTitleLabel()
    {
        JLabel label = new JLabel();
        label.setText("Lets call your new Pabunot!");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.gray);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(1280 - width + 20, 250, width, height);
        return label;
    }

    private JLabel createMakingPabunot()
    {
        JLabel label = new JLabel();
        label.setText("> Lets go continue!!");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.gray);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(1280 - width + 20, 720 - height - 20, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setVisible(false);
                resetTitle();
                frame.createPabunot = new PabunotMakingPane(frame, typeEvent.string);
                frame.getContentPane().add(frame.createPabunot);
                frame.createPabunot.setVisible(true);
                frame.getContentPane().repaint();
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

    public void resetTitle()
    {
        for(JLabel label : title)
        {
            remove(label);
        }
        title = new TrailLabel("Type your title here", 100, 300, 320, new Color[] {new Color(127, 127, 127)});
        for(JLabel label : title)
        {
            add(label);
        }
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

    private void addComponents()
    {
        for(JLabel label : title)
        {
            add(label);
        }
        add(goBack);
        add(makePabunot);
        for(JLabel label : titleLabel)
        {
            add(label);
        }
    }

    private void initializeComponent()
    {
        setSize(1280, 720);
        setVisible(false);
        setLayout(null);
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 10));
        setDoubleBuffered(true);
        frame.addKeyListener(typeEvent);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0 , getWidth(), getHeight());
    }
}
