package pabunot.interfaces;

import pabunot.prize.Prize;
import pabunot.prize.PrizeList;
import pabunot.util.AndyBold;
import pabunot.util.Interface;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PabunotPrizeInterface extends Interface
{
    private JLabel title;
    private JLabel prizeNumber;
    private PrizeList list;
    Prize prize;
    PabunotPrizeListPane pane;

    public PabunotPrizeInterface(Prize prize, PrizeList list, PabunotPrizeListPane pane)
    {
        super();
        WIDTH = 200;
        HEIGHT = 30;
        this.prize = prize;
        this.list = list;
        this.pane = pane;
        initializeComponent();
        title = createTitle(prize.getTitle());
        prizeNumber = createPrizeNumber(0);
        add(title);
    }

    private JLabel createPrizeNumber(int i)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(i + "");
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(215, 39, 39));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(WIDTH - width, 10, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                list.remove(prize);
                pane.restore();
            }
        });
        return label;
    }

    private JLabel createDelete()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("delete");
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(215, 39, 39));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(225, 10, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                list.remove(prize);
                pane.restore();
            }
        });
        return label;
    }

    private JLabel createTitle(String title)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(title);
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(255, 255, 255));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(5, 5, 230, height);
        return label;
    }

    private JLabel createDescription(String desc)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(desc);
        label.setFont(AndyBold.createFont(15));
        label.setForeground(new Color(126, 126, 126));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int height = metrics.getHeight();
        label.setBounds(5, 25, 230, height);
        return label;
    }

    private JLabel createStuff()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("");
        label.setFont(AndyBold.createFont(23));
        label.setForeground(Color.BLACK);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int height = metrics.getHeight();
        label.setBounds(0, 0, 0, 0);
        return label;
    }

    private void initializeComponent()
    {
        setLayout(null);
        setBorder(new LineBorder(Color.white));
        setBackground(new Color(0x00_00_000, true));
        setSize(new Dimension(WIDTH, HEIGHT));
    }
}