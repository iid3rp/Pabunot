package Pabunot.Pabunot;

import Pabunot.InitialFrame;
import Pabunot.Utils.Intention;
import Pabunot.Utils.RandomRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PabunotGridPane extends JPanel
{
    @Intention InitialFrame frame;
    @Intention JPanel panel = this;
    private int x;
    private int y;
    private int paperLength;
    private PabunotGrid grid;


    public PabunotGridPane(InitialFrame frame, int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        this.frame = frame;
        initializeComponent(x, y);
        addPapers(new RandomRange(1, x * y));
        setLocation((1280 / 2) - (getWidth() / 2), (720 / 2) - (getHeight() / 2));

    }

    public PabunotGridPane(InitialFrame frame, PabunotGrid grid)
    {
        super();
        this.x = grid.getX();
        this.y = grid.getY();
        this.frame = frame;
        this.grid = grid;
        initializeComponent(x, y);
        addPapers(grid);
        setLocation((1280 / 2) - (getWidth() / 2), (720 / 2) - (getHeight() / 2));

    }

    private void addPapers(PabunotGrid grid)
    {
        int index = 0;
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                try {
                    JLabel paper = createPaper(grid.grid[index].getValue());
                    grid.grid[index].image = grid.grid[index].image.getScaledInstance(paperLength, paperLength, Image.SCALE_FAST);
                    paper.setBounds(i * paperLength, j * paperLength, paperLength, paperLength);
                    add(paper);
                    index++;
                }
                catch(IndexOutOfBoundsException ignored) {  System.out.println(index);  }
            }
        }
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
        setLayout(null);
        setOpaque(false);
        setSize(new Dimension(width, height));
        setDoubleBuffered(true);
        setBackground(new Color(0, 0,0, 0));
        System.out.println("rendering...");
    }

    private void addPapers(RandomRange range)
    {
        int index = 0;
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                try {
                    JLabel paper = createPaper(range.get(index++));

                    paper.setBounds(i * paperLength, j * paperLength, paperLength, paperLength);
                    // System.out.println(i * paperLength + " " + j * paperLength); // debuggers
                    add(paper);
                }
                catch(IndexOutOfBoundsException ignored) { /* System.out.println(index); */ }
            }
        }
    }

    private JLabel createPaper(int i)
    {
        final boolean[] isEntered = {false};
        JLabel label = new JLabel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                if(isEntered[0])
                {
                    g.setColor(Color.GRAY);
                    g.fillRect(0, 0, paperLength, paperLength);
                }
                else
                {
                    try {
                        g.drawImage(grid.grid[i - 1].image, 0, 0, null);
                    }
                    catch(Exception e) {
                        System.out.println(i);
                    }
                }
                System.out.println("ren");
            }
        };
        label.setLayout(null);
        label.setDoubleBuffered(true);
        label.setOpaque(false);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(label.isEnabled()) {
                    System.out.println("You picked number " + i);
                    label.setEnabled(false);
                    frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
                    grid.grid[i - 1].setPicked(true);
                    remove(label);
                    validateChecking();
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if(label.isEnabled())
                {
                    isEntered[0] = false;
                }
                else frame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(label.isEnabled()) {
                    isEntered[0] = true;
                }
                else frame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                frame.parallaxMove(new Point(panel.getX() + label.getX() + e.getX(), panel.getY() + label.getY() + e.getY()));
            }
        });
        return label;
    }

    private void validateChecking()
    {
        for(Pabunot p : grid.getGrid())
        {
            if(!p.isPicked()) return;
        }
        frame.panel.remove(this);
    }

    @Override
    public void paintComponent(Graphics ignored) {} // avoid repainting...
}
