package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.graphics.Snow;
import pabunot.graphics.TrailLabel;
import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.prize.Prize;
import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PrizePicked extends JPanel
{
    private PalabunotGrid grid;
    private Prize prize;
    private Palabunot palabunot;
    public TrailLabel infoLabel;
    private JLabel description;
    private JLabel winLoseLabel;
    @Intention InitialFrame frame;
    @Intention PrizePicked panel = this;
    private JLabel numberReveal;
    public boolean paint;
    public static Snow snow;
    public boolean breaking = false;
    public Thread snowThread;

    @Intention(design = "Winners label")
    public PrizePicked(InitialFrame frame, PalabunotGrid grid, Palabunot palabunot, Prize prize)
    {
        super();
        initializeComponent();
        this.grid = grid;
        paint = true;
        this.frame = frame;
        snow = new Snow(InitialFrame.HEIGHT + 1);
        int half = (InitialFrame.HEIGHT - getFontMetrics(AndyBold.createFont(100)).getHeight()) / 2;
        infoLabel = new TrailLabel("Won: " + prize.getTitle(), 100, half - 10, half + 10, TrailLabel.rainbow);
        this.palabunot = palabunot;
        this.prize = prize;
        numberReveal = createNumberReveal(palabunot.getValue());
        description = createDescription(prize.getDescription());
        addComponents();
        snowThread = new Thread(snow);
        snowThread.start();
    }

    private JLabel createDescription(String description)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(description);
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.gray);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int w = metrics.stringWidth(label.getText().toUpperCase());
        int h = metrics.getHeight();
        label.setBounds(0, 10, w, h);
        return label;
    }

    @Intention(design = "Losers label")
    public PrizePicked(InitialFrame frame, PalabunotGrid grid, Palabunot palabunot)
    {
        super();
        initializeComponent();
        System.out.println("Hello World!");
        this.grid = grid;
        snow = null;
        this.frame = frame;
        int half = (InitialFrame.HEIGHT - getFontMetrics(AndyBold.createFont(100)).getHeight()) / 2;
        infoLabel = new TrailLabel("Better Luck next time", 100, half - 10, half + 10, new Color[] {Color.gray});
        this.prize = null;
        this.palabunot = palabunot;
        winLoseLabel  = createLoseLabel();
        numberReveal = createNumberReveal(palabunot.getValue());
        addComponents();
    }

    private JLabel createLoseLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("You didnt won anything.");
        label.setFont(AndyBold.createFont(50));
        label.setForeground(Color.gray);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int w = metrics.stringWidth(label.getText().toUpperCase() + "pp");
        int h = metrics.getHeight();
        label.setBounds((int) (InitialFrame.HEIGHT * .3), (InitialFrame.WIDTH - w) / 2, w, h);
        return label;
    }

    private JLabel createNumberReveal(int number)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("You picked " + number);
        label.setFont(AndyBold.createFont(50));
        label.setForeground(Color.gray);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int w = metrics.stringWidth(label.getText() + "a");
        int h = metrics.getHeight();
        label.setBounds((InitialFrame.WIDTH - w) / 2, (int) (InitialFrame.HEIGHT * .3), w, h);
        return label;
    }

    private void addComponents()
    {
        for(JLabel label : infoLabel)
        {
            add(label);
        }
        add(clickAnywhere());
        add(numberReveal);
    }

    private JLabel clickAnywhere()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Click anywhere to continue");
        label.setFont(AndyBold.createFont(50));
        label.setForeground(Color.gray);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int w = metrics.stringWidth(label.getText().toUpperCase());
        int h = metrics.getHeight();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds((InitialFrame.WIDTH - w) / 2, (int) (InitialFrame.HEIGHT * .6), w, h);
        return label;
    }

    private void initializeComponent()
    {
        setSize(InitialFrame.WIDTH, InitialFrame.HEIGHT);
        setDoubleBuffered(true);
        setOpaque(false);
        setLayout(null);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                panel.setVisible(false);
                frame.contentPanel.remove(panel);
                if(frame.section.grid.prizeList.isEmpty())
                {
                    frame.ended = new PabunotEnding(frame, grid);
                    frame.contentPanel.add(frame.ended);
                    frame.ended.setVisible(true);
                }
                else
                {
                    frame.section.setVisible(true);
                    frame.prizePicked = null;
                    if(snow != null)
                    {
                        snow.isRunning = false;
                    }
                }
            }
        });
        setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0 , getWidth(), getHeight());
        if(snow != null)
        {
            g.drawImage(PrizePicked.snow.particle, 0 ,0, getWidth(), getHeight(), null);
        }
    }
}
