package pabunot.palabunutan;

import pabunot.InitialFrame;
import pabunot.interfaces.PrizePicked;
import pabunot.prize.Prize;
import pabunot.util.Intention;
import pabunot.util.RandomRange;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Represents a JPanel for displaying a grid of papers in a Palabunot game.
 * This class handles the rendering of papers based on the provided grid or a random range.
 * It allows users to interact with the papers by clicking on them, revealing prizes.
 * The class also validates the selection of papers and triggers events accordingly.
 */
public class PalabunotGridPane extends JPanel
{
    @Intention InitialFrame frame;
    @Intention JPanel panel = this;
    private int x;
    private int y;
    private int width;
    private int height;
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
        setLocation((InitialFrame.WIDTH  - getWidth()) / 2,
                (InitialFrame.HEIGHT - getHeight()) / 2);

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

    }

    private void addPapers(PalabunotGrid grid)
    {
        try {
            Palabunot.image = ImageIO.read(
                    Palabunot.selectTheme(grid.currentTheme));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        Palabunot.image = Palabunot.image.getScaledInstance(
                paperLength, paperLength, Image.SCALE_SMOOTH);
        int index = 0;
        for(int i = 0; i < x; i++)
        {
            for(int j = 0; j < y; j++)
            {
                try {
                    //System.out.println(index);
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
        paperLength = (int) ((InitialFrame.HEIGHT * 0.8) / Math.max(x, y));
        {
            width = paperLength * x;
            height = paperLength * y;
        }
        setLayout(null);
        setOpaque(false);
        setDoubleBuffered(true);
        setSize(width, height);
        setBackground(new Color(0, 0,0, 127));
        //System.out.println("rendering...");
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

                    paper.setBounds(
                            i * paperLength,
                            j * paperLength,
                            paperLength,
                            paperLength
                    );
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
        final BufferedImage x = new BufferedImage(
                paperLength,
                paperLength,
                BufferedImage.TYPE_INT_ARGB);
        JLabel label = new JLabel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                if(isEntered[0])
                {
                    Graphics2D g2d = x.createGraphics();
                    g2d.drawImage(Palabunot.image, 0, 0, null);
                    g2d.setComposite(AlphaComposite.getInstance(
                            AlphaComposite.DST_IN, 0.5f));
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
                    grid.grid.get(index).setPicked(true);
                    System.out.println(grid.getArrayNotPicked());
                    remove(label);
                    validateChecking();

                    validatePalabunot(grid.grid.get(index));
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
                InitialFrame.parallaxMove(
                        new Point(
                                panel.getX() + label.getX() + e.getX(),
                                panel.getY() + label.getY() + e.getY()
                        )
                );
            }
        });
        return label;
    }

    private void validatePalabunot(Palabunot palabunot)
    {
        Prize reference = null;
        for(Prize p : grid.prizeList)
        {
            if(p.getNumber() == palabunot.getValue())
            {
                reference = p;
            }
        }
        if(reference != null)
        {
            grid.prizeList.remove(reference);
            frame.section.prizeListPane.restore();
            frame.prizePicked = new PrizePicked(frame, grid, palabunot, reference);
            //System.out.println("YOU WON");
        }
        if(frame.prizePicked == null)
        {
            frame.prizePicked = new PrizePicked(frame, grid, palabunot);
            //System.out.println("YOU LOST");
        }

        frame.section.setVisible(false);
        InitialFrame.contentPanel.add(frame.prizePicked);
        frame.prizePicked.setVisible(true);
        frame.repaint();
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