package pabunot.palabunutan;

import pabunot.InitialFrame;
import pabunot.util.Intention;
import pabunot.util.RandomRange;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PalabunotGridPane extends JPanel
{
    @Intention InitialFrame frame;
    @Intention JPanel panel = this;
    private int x;
    private int y;
    private int paperLength;
    private PalabunotGrid grid;


    public PalabunotGridPane(InitialFrame frame, int x, int y)
    {
        super();
        this.x = x;
        this.y = y;
        this.frame = frame;
        initializeComponent(x, y);
        addPapers(new RandomRange(1, x * y));
        setLocation((1280 / 2) - (getWidth() / 2), (720 / 2) - (getHeight() / 2));

    }

    public PalabunotGridPane(InitialFrame frame, PalabunotGrid grid)
    {
        super();
        this.x = grid.getX();
        this.y = grid.getY();
        this.frame = frame;
        this.grid = grid;
        initializeComponent(x, y);
        addPapers(grid);
        setBounds((1280 / 2) - (getWidth() / 2), (720 / 2) - (getHeight() / 2), getWidth(), getHeight());

    }

    private void addPapers(PalabunotGrid grid)
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
                catch(IndexOutOfBoundsException e) {  System.out.println(index + ", dummy");  }
            }
        }
    }

    private void initializeComponent(int x, int y)
    {
        paperLength = (600 / Math.max(x, y));
        int width, height;
        {
            width = paperLength * x;
            height = paperLength * y;
        }
        setLayout(null);
        setOpaque(false);
        setDoubleBuffered(true);
        setSize(new Dimension(width, height));
        setBackground(new Color(0, 0,0, 127));
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
                    g.drawImage(grid.grid[i - 1].image, 0, 0, null);
                    g.setColor(new Color(0, 0 ,0, 127));
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
                InitialFrame.parallaxMove(new Point(panel.getX() + label.getX() + e.getX(), panel.getY() + label.getY() + e.getY()));
            }
        });
        return label;
    }

    private void validateChecking()
    {
        for(Palabunot p : grid.getGrid())
        {
            if(!p.isPicked()) return;
        }
        frame.contentPanel.remove(this);
    }

    @Override
    public void paintComponent(Graphics ignored) {} // avoid repainting...
}
