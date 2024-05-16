package Pabunot.Pabunot;

import Pabunot.Utils.AndyBold;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PabunotInterface extends JPanel
{
    public static int WIDTH = 1000;
    public static int HEIGHT = 100;
    JLabel name;
    JLabel description;
    JLabel play;
    JLabel delete;

    public PabunotInterface(Pabunot p)
    {
        super();
        initializeComponent();
        name = createName(p.getName());
        play = createPlay();
        delete = createDelete();

        add(name);
        add(play);
        add(delete);
    }

    private JLabel createDelete()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Delete");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.white);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        label.setBounds(700, 50, width, height);
        return label;
    }

    private JLabel createPlay()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Play!");
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(30));


        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        label.setBounds(500, 40, width, height);
        return label;
    }

    private JLabel createName(String s)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s);
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(50));

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(s.toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(10, 10, width, height);
        return label;
    }

    private void initializeComponent()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
        setBackground(new Color(0, 0, 0,120));
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                super.mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                super.mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                super.mouseExited(e);
            }
        });
        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                super.mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                super.mouseMoved(e);
            }
        });
    }
}
