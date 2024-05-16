package pabunot.prize;

import pabunot.util.AndyBold;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrizeInterface extends JPanel
{
    public static final int WIDTH = 300;
    public static final int HEIGHT = 45;
    private final JLabel title;
    private final JLabel description;
    private final JLabel delete;
    private final PrizeList list;
    Prize prize;
    PrizeListPane pane;

    public PrizeInterface(Prize prize, PrizeList list, PrizeListPane pane)
    {
        super();
        this.prize = prize;
        this.list = list;
        this.pane = pane;
        initializeComponent();
        title = createTitle(prize.getTitle());
        description = createDescription(prize.getDescription());
        delete = createDelete();

        add(title);
        add(description);
        add(delete);
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