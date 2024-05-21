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
    public int width = 350;
    public int height = 50;

    public PabunotPrizeInterface(Prize prize, PrizeList list, PabunotPrizeListPane pane)
    {
        super();
        this.prize = prize;
        this.list = list;
        this.pane = pane;
        initializeComponent();
        title = createTitle(prize.getTitle());
        prizeNumber = createPrizeNumber(prize.getNumber());
        add(title);
        add(prizeNumber);
    }

    private JLabel createPrizeNumber(int i)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(i + "");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(new Color(255, 255, 255));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int w = metrics.stringWidth(label.getText().toUpperCase() + "pp");
        int h = metrics.getHeight();
        label.setBounds(width - w - 10, 10, w, h);
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
        label.setFont(AndyBold.createFont(30));
        label.setForeground(new Color(255, 255, 255));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(5, 5, 230, height);
        return label;
    }

    private void initializeComponent()
    {
        setLayout(null);
        setOpaque(true);
        setBorder(new LineBorder(Color.white));
        setBackground(new Color(0x00_00_000, true));
        setSize(new Dimension(width, height));
    }
}