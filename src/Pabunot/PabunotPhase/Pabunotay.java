package Pabunot.PabunotPhase;

import Utils.RandomRange;

import javax.swing.*;
import java.awt.*;

public class Pabunotay extends JPanel
{
    private final int x;
    private final int y;
    private int paperLength;
    public Pabunotay(int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        initializeComponent(x, y);
        addPapers(new RandomRange(1, x * y));

    }

    private void initializeComponent(int x, int y)
    {
        paperLength = (600 / Math.max(x, y));
        int width, height;
        if(x > y)
        {
            width = paperLength * x;
            height = paperLength * y;
        }
        else
        {
            width = paperLength * y;
            height = paperLength * x;
        }
        setSize(new Dimension(width, height));
        setLayout(null);
        setDoubleBuffered(true);
    }

    private void addPapers(RandomRange range)
    {
        int index = 0;
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; i++)
            {
                JLabel paper = createPaper(range.get(index++));
                paper.setLocation(new Point(i * paperLength, y * paperLength));
            }
        }
    }

    private JLabel createPaper(int i)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setSize(new Dimension(paperLength, paperLength));
        return label;
    }


}
