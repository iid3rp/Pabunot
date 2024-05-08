package Pabunot.Prize;

import Pabunot.Utils.AndyBold;

import javax.swing.*;
import java.awt.*;

public class PrizeInterface extends JPanel
{
    public static final int WIDTH = 400;
    public static final int HEIGHT = 60;
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

        add(title);
        add(description);
    }

    private JLabel createTitle(String title)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(title);
        label.setFont(AndyBold.createFont(30));
        label.setForeground(new Color(0, 0,0));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(5, 5, 300, height);
        return label;
    }

    private JLabel createDescription(String desc)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(desc);
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(0, 0,0, 127));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int height = metrics.getHeight();
        label.setBounds(5, 35, 300, height);
        return label;
    }

    private void initializeComponent()
    {
        setLayout(null);
        setBackground(new Color(255, 255, 255, 60));
        setSize(new Dimension(WIDTH, HEIGHT));
    }
}
