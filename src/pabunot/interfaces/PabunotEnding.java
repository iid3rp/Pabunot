package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.graphics.TrailLabel;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PabunotEnding extends JPanel
{
    public TrailLabel infoLabel;
    private JLabel end;
    @Intention PalabunotGrid grid;
    @Intention
    InitialFrame frame;
    @Intention PabunotEnding panel = this;

    public PabunotEnding(InitialFrame frame, PalabunotGrid grid)
    {
        super();
        initializeComponent();
        this.grid = grid;
        this.frame = frame;
        int heightHalf = (InitialFrame.HEIGHT - getFontMetrics(AndyBold.createFont(200)).getHeight()) / 2;
        infoLabel = new TrailLabel("Pabunot!", 200, heightHalf - 10, heightHalf + 10, TrailLabel.rainbow);
        end = endTitle();
        addComponents();
    }

    private JLabel endTitle()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Thank you for playing");
        label.setFont(AndyBold.createFont(50));
        label.setForeground(Color.gray);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int w = metrics.stringWidth(label.getText() + "t");
        int h = metrics.getHeight();
        label.setBounds((InitialFrame.WIDTH - w) / 2, (int) (InitialFrame.HEIGHT * .25), w, h);
        return label;
    }
    private void addComponents()
    {
        for(JLabel label : infoLabel)
        {
            add(label);
        }
        add(clickAnywhere());
        add(end);
    }

    private JLabel clickAnywhere()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Click anywhere to go back to main menu");
        label.setFont(AndyBold.createFont(50));
        label.setForeground(Color.gray);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int w = metrics.stringWidth(label.getText() + "p");
        int h = metrics.getHeight();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setBounds((InitialFrame.WIDTH - w) / 2, (int) (InitialFrame.HEIGHT * .65), w, h);
        return label;
    }

    private void initializeComponent()
    {
        setLayout(null);
        setSize(InitialFrame.WIDTH, InitialFrame.HEIGHT);
        setDoubleBuffered(true);
        setVisible(false);
        setOpaque(false);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.contentPanel.remove(panel);
                frame.mainMenu.setVisible(true);
                grid.deletePabunot();

                frame.contentPanel.remove(frame.picker);
                frame.picker = new PabunotPickerPanel(frame);
                frame.contentPanel.add(frame.picker);

                PrizePicked.snow.isRunning = false;
                frame.prizePicked = null;
            }
        });
    }

    @Override
    public void paintComponent(Graphics g)
    {
        g.setColor(new Color(0, 0, 0, 200));
        g.fillRect(0, 0 , getWidth(), getHeight());
        if(PrizePicked.snow != null)
        {
            g.drawImage(PrizePicked.snow.particle, 0 ,0, getWidth(), getHeight(), null);
        }
    }
}
