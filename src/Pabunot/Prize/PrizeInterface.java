package Pabunot.Prize;

import Pabunot.Utils.AndyBold;

import javax.swing.*;
import java.awt.*;

public class PrizeInterface extends JPanel
{
    public static final int WIDTH = 300;
    public static final int HEIGHT = 50;
    private final JLabel title;
    private final JLabel description;
    Prize prize;

    public PrizeInterface(Prize prize)
    {
        super();
        this.prize = prize;
        initializeComponent();
        title = createTitle(prize.getTitle());
        description = createDescription(prize.getDescription());
    }

    private JLabel createDescription(String desc)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(desc);
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(0, 0,0, 127));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(245, 255, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JLabel createTitle(String title)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(title);
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(0, 0,0, 127));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(245, 255, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private void initializeComponent()
    {
        setSize(new Dimension(WIDTH, HEIGHT));
        setLayout(null);
    }
}
