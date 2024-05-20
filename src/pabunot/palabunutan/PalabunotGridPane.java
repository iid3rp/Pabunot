package pabunot.palabunutan;

import pabunot.InitialFrame;
import pabunot.util.Intention;
import pabunot.util.RandomRange;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class PalabunotGridPane extends JPanel
{
    @Intention InitialFrame frame;
    @Intention JPanel panel = this;
    private int x;
    private int y;
    private int paperLength;
    public PalabunotGrid grid;

    @Deprecated
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
        try {
            Palabunot.image = ImageIO.read(Palabunot.selectTheme(grid.theme));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        Palabunot.image = Palabunot.image.getScaledInstance(paperLength, paperLength, Image.SCALE_SMOOTH);
        int index = 0;
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                try {
                    System.out.println(index);
                    if(!grid.grid.get(index).isPicked())
                    {
                        JLabel paper = createPaper(index, grid.grid.get(index).getValue());
                        paper.setBounds(i * paperLength, j * paperLength, paperLength, paperLength);
                        add(paper);
                    }
                    index++;
                }
                catch(IndexOutOfBoundsException e) {  System.out.println(index + ", error");  }
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
                    JLabel paper = createPaper(index, range.get(index++));

                    paper.setBounds(i * paperLength, j * paperLength, paperLength, paperLength);
                    // System.out.println(i * paperLength + " " + j * paperLength); // debuggers
                    add(paper);
                }
                catch(IndexOutOfBoundsException ignored) { /* System.out.println(index); */ }
            }
        }
    }

    private JLabel createPaper(int index, int value)
    {
        final boolean[] isEntered = {false};
        final BufferedImage x = new BufferedImage(paperLength, paperLength, BufferedImage.TYPE_INT_ARGB);
        JLabel label = new JLabel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                if(isEntered[0])
                {
                    Graphics2D g2d = x.createGraphics();
                    g2d.drawImage(Palabunot.image, 0, 0, null);
                    g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.DST_IN, 0.5f));
                    g2d.setColor(Color.white);
                    g2d.fillRect(0, 0, paperLength, paperLength);
                    g2d.dispose();

                    g.drawImage(x, 0,  0, null);
                }
                else
                {
                    try {
                        g.drawImage(Palabunot.image, 0, 0, null);
                    }
                    catch(Exception e) {
                        System.out.println(value);
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
                    System.out.println("You picked number " + value);
                    grid.grid.get(index).setPicked(true);
                    System.out.println(grid.getArrayNotPicked());
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
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(label.isEnabled()) {
                    isEntered[0] = true;
                }
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
        frame.mainMenu.remove(this);
    }

    @Override
    public void paintComponent(Graphics ignored) {} // avoid repainting...
}
