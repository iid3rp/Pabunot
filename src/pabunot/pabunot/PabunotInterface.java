package pabunot.pabunot;

import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PabunotInterface extends JPanel
{
    public static int WIDTH = 1000;
    public static int HEIGHT = 100;
    JLabel name;
    JLabel description;
    JLabel play;
    JLabel delete;
    JLabel gridLabel;
    @Intention Pabunot pb;

    public PabunotInterface(Pabunot p)
    {
        super();
        pb = p;
        initializeComponent();
        name = createName(p.getTitle());
        play = createPlay();
        delete = createDelete();
        gridLabel = createGridLabel();

        add(name);
        add(play);
        add(delete);
    }

    private JLabel createGridLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(pb.getX() + "x" + pb.getY());
        label.setFont(AndyBold.createFont(15));
        label.setForeground(new Color(255, 127, 127));

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(30, 50, width, height);
        return label;
    }

    private JLabel createDelete()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Delete");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(new Color(255, 127, 127));

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(WIDTH - width - 40, (HEIGHT - height) / 2, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(new Color(255, 127, 127));
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.red);
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
            }
        });
        return label;
    }

    private JLabel createPlay()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Play!");
        label.setForeground(new Color(127, 255, 127));
        label.setFont(AndyBold.createFont(30));

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(WIDTH - width - 180, (HEIGHT - height) / 2, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(new Color(127, 255, 127));
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.green);
            }

            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
            }
        });
        return label;
    }

    private JLabel createName(String s)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s = s == null? "Pabunot 1" : s);
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
        setBorder(new LineBorder(Color.white));
        setBackground(new Color(0, 0, 0,120));
    }
}
