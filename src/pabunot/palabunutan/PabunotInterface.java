package pabunot.palabunutan;

import pabunot.InitialFrame;
import pabunot.interfaces.PabunotSection;
import pabunot.streamio.PabunotMaker;
import pabunot.util.AndyBold;
import pabunot.util.Intention;
import pabunot.util.Interface;
import pabunot.util.Theme;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

public class PabunotInterface extends Interface
{
    JLabel name;
    public JLabel description;
    JLabel play;
    JLabel delete;
    JLabel gridLabel;
    @Intention InitialFrame frame;
    public @Intention PabunotInterface panel;
    public @Intention PalabunotGrid pb;

    public PabunotInterface(InitialFrame frame, PalabunotGrid p)
    {
        super();
        panel = this;
        this.frame = frame;
        WIDTH = (int) (InitialFrame.WIDTH * 0.8);
        HEIGHT = 100;
        pb = p;
        initializeComponent();
        name = createName(p.getTitle());
        play = createPlay();
        description = createDescription();
        delete = createDelete();

        add(name);
        add(play);
        add(delete);
        add(description);
    }

    private JLabel createDescription()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.gray);
        label.setText(
                "Dimension: " + "\"" + pb.getX() + " x " + pb.getY() + "\"          " +
                "Theme: " + getThemeString() + "          " +
                "Prizes: " + (pb.prizeList != null ? pb.prizeList.size() : 0) + "          " +
                "Pabunot left: " + pb.getArrayNotPicked());
        label.setFont(AndyBold.createFont(20));

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(10, 65, width, height);
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
                @Intention boolean[] x = new boolean[]
                {
                    new File(PabunotMaker.pabunotDir + File.separator + panel.pb.getSerial() + File.separator + "Pabunot.ini").delete(),
                    new File(PabunotMaker.pabunotDir + File.separator + panel.pb.getSerial()).delete()
                };
                System.out.println(PabunotMaker.pabunotDir + File.separator + panel.pb.getSerial() + " " + x[0] + " " + x[1]);
                frame.picker.pane.list.remove(panel);
                frame.picker.pane.restore();
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
                frame.picker.setVisible(false);
                frame.section = new PabunotSection(frame, pb);
                frame.getContentPane().add(frame.section);
            }
        });
        return label;
    }

    private JLabel createName(String s)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s = s == null? "PalabunotGridPane 1" : s);
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

    public String getThemeString()
    {
        return switch(pb.theme) {
            case Theme.RED_HEARTS -> "Red hearts";
            case Theme.ORANGE_HEARTS -> "Orange hearts";
            case Theme.YELLOW_HEARTS -> "Yellow hearts";
            case Theme.GREEN_HEARTS -> "Green hearts";
            case Theme.BLUE_HEARTS -> "Blue hearts";
            case Theme.PURPLE_HEARTS -> "Purple hearts";
            case Theme.PINK_HEARTS -> "Pink hearts";
            case Theme.GRAY_HEARTS -> "Gray hearts";
            case Theme.RAINBOW_HEARTS -> "Rainbow hearts";
            case Theme.TEAL_HEARTS -> "Teal hearts";
            case Theme.WOOD -> "Wood flowers";
            case Theme.ORANGE_FRUIT -> "Orange fruit";
            case Theme.CLOVER -> "Clover leaf";
            case Theme.LUCKY -> "Lucky block";
            case Theme.SUNFLOWER -> "Sunflowers";
        };
    }
}