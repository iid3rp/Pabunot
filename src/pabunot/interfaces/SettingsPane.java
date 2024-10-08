package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.palabunutan.PabunotInterface;
import pabunot.prize.PrizeList;
import pabunot.util.AndyBold;
import pabunot.util.DataType;
import pabunot.util.Intention;
import pabunot.util.TextFilter;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class SettingsPane extends JScrollPane
{
    public static boolean fpsCountVisibility;
    public static boolean parallax;
    public static boolean fpsUnlock;
    public static boolean waveTrail;
    public static boolean snowVisible;
    public static String graphicsQuality;
    public JTextField fpsField;
    public PrizeList list;
    private JPanel container;
    private int size;
    private int width = (int) (InitialFrame.WIDTH * 0.8);
    private int height = 100;
    public JPanel countCap;
    public int fpsCap;
    private boolean nothing;
    @Intention public InitialFrame frame;

    /**<editor-fold desc="Description">
     * you need to get the panel's preferred size "daw" because that's going
     * to be the reference dimension of the JScrollPane (which is weird but whatever)
     * xd - derp.
     * <p></p>
     * REFER THE CONSTRUCTOR BELOW
     *</editor-fold>
     * */
    public SettingsPane()
    {
        super();
        initializeComponent(frame);
        setSize(new Dimension((int) (InitialFrame.WIDTH * 0.8), (int) (InitialFrame.HEIGHT * 0.8)));
        setPreferredSize(new Dimension((int) (InitialFrame.WIDTH * 0.8), (int) (InitialFrame.HEIGHT * 0.8)));
        setBackground(new Color(0, 0, 0, 80));
        setDoubleBuffered(true);

        container = createContainer();
        container.setSize(new Dimension(PabunotInterface.WIDTH, height * 5));
        container.setPreferredSize(new Dimension(PabunotInterface.WIDTH, height * 5));

        // add components into the container here :3
        addComponents();

        setViewportView(container);
        getViewport().setVisible(true);
        getViewport().setBackground(new Color(0, 0, 0, 90));
        getVerticalScrollBar().setVisible(false);
        getHorizontalScrollBar().setVisible(false);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(getX() + e.getX(),  getY() + e.getY()));

            }
        });

        addMouseWheelListener(e ->
        {
            // Increase scroll sensitivity by multiplying the scroll distance
            double unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            verticalScrollBar.setValue((int) (verticalScrollBar.getValue() + unitsToScroll));
        });
        setVisible(true);
    }

    private JPanel createContainer()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setDoubleBuffered(true);
        panel.setBackground(new Color(0, 0, 0, 100));
        return panel;
    }


    public void initializeComponent(InitialFrame frame)
    {
        // add a container to put stuff :3
        container = new JPanel();
        container.setLayout(null);
        container.setLocation(0, 0);
        container.setOpaque(false);
        setBackground(new Color(255, 255, 255, 60));
        setOpaque(false);
        container.setDoubleBuffered(true);
    }

    public void addComponents()
    {
        countCap = fpsCounterAmount();
        container.add(fpsCounter(fpsCountVisibility));
        container.add(parallaxOption(parallax));
        container.add(fpsUnlocker(fpsUnlock));
        container.add(countCap);
        container.add(graphicsQuality(graphicsQuality));
        container.add(waveTrail(waveTrail));
        container.add(snowVisibility(snowVisible));

        nothing = false;
        container.repaint();
        repaint();

    }

    public void verifyFPSCounterCap()
    {
        Component[] comp = countCap.getComponents();
        for(Component component : comp)
        {
            component.setEnabled(fpsUnlock);
        }

    }

    public JPanel fpsCounter(boolean fpsCountVisibility)
    {
        final boolean[] toggle = new boolean[] {fpsCountVisibility};
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(width, 100);
        panel.setBackground(new Color(0, 0, 0,120));
        panel.setLocation(0, 0);
        panel.setDoubleBuffered(true);
        panel.setBorder(new LineBorder(Color.white));

        JLabel title = new JLabel();
        title.setLayout(null);
        title.setDoubleBuffered(true);
        title.setText("FPS counter visibility");
        title.setFont(AndyBold.createFont(50));
        title.setForeground(Color.white);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int width = metrics.stringWidth(title.getText() + "a");
        int height = metrics.getHeight();
        title.setBounds(15, 15, width,  height);

        JLabel description = new JLabel();
        description.setLayout(null);
        description.setDoubleBuffered(true);
        description.setText("Shows a frame-per-second label at the bottom-right of your screen.");
        description.setFont(AndyBold.createFont(20));
        description.setForeground(Color.gray);
        metrics = description.getFontMetrics(description.getFont());
        width = metrics.stringWidth(description.getText().toUpperCase());
        height = metrics.getHeight();
        description.setBounds(20, 70, width,  height);

        // Create and configure the checkbox
        JLabel checker = new JLabel();
        checker.setText(fpsCountVisibility? "on" : "off");
        checker.setForeground(fpsCountVisibility? new Color(50, 200, 50) : new Color(200, 50, 50));
        checker.setFont(AndyBold.createFont(50));
        checker.setHorizontalAlignment(SwingConstants.CENTER);
        checker.setBounds(this.width - 120, (this.height - 60) / 2, 100, 60);
        checker.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(toggle[0])
                {
                    toggle[0] = false;
                    checker.setText("off");
                    checker.setForeground(new Color(200, 50, 50));
                    InitialFrame.setFPSCountVisibility(toggle[0]);
                    //System.out.println("h");
                }
                else
                {
                    toggle[0] = true;
                    checker.setForeground(new Color(50, 200, 50));
                    InitialFrame.setFPSCountVisibility(toggle[0]);
                    checker.setText("on");
                }
            }
        });

        panel.add(title);
        panel.add(description);
        panel.add(checker);

        return panel;
    }

    public JPanel parallaxOption(boolean parallax)
    {
        final boolean[] toggle = new boolean[] {parallax};
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(width, 100);
        panel.setBackground(new Color(0, 0, 0,120));
        panel.setLocation(0, 100);
        panel.setDoubleBuffered(true);
        panel.setBorder(new LineBorder(Color.white));

        JLabel title = new JLabel();
        title.setLayout(null);
        title.setDoubleBuffered(true);
        title.setText("Parallax");
        title.setFont(AndyBold.createFont(50));
        title.setForeground(Color.white);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int width = metrics.stringWidth(title.getText() + "a");
        int height = metrics.getHeight();
        title.setBounds(15, 15, width,  height);

        JLabel description = new JLabel();
        description.setLayout(null);
        description.setDoubleBuffered(true);
        description.setText("Creates a 3D effect by moving the background image at a different speed than the foreground.");
        description.setFont(AndyBold.createFont(20));
        description.setForeground(Color.gray);
        metrics = description.getFontMetrics(description.getFont());
        width = metrics.stringWidth(description.getText().toUpperCase());
        height = metrics.getHeight();
        description.setBounds(20, 70, width,  height);

        // Create and configure the checkbox
        JLabel checker = new JLabel();
        checker.setText(parallax? "on" : "off");
        checker.setForeground(parallax? new Color(50, 200, 50) : new Color(200, 50, 50));
        checker.setFont(AndyBold.createFont(50));
        checker.setHorizontalAlignment(SwingConstants.CENTER);
        checker.setBounds(this.width - 120, (this.height - 60) / 2, 100, 60);
        checker.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(toggle[0])
                {
                    toggle[0] = false;
                    checker.setText("off");
                    checker.setForeground(new Color(200, 50, 50));
                    InitialFrame.setParallax(toggle[0]);
                }
                else
                {
                    toggle[0] = true;
                    checker.setForeground(new Color(50, 200, 50));
                    checker.setText("on");
                    InitialFrame.setParallax(toggle[0]);
                }
            }
        });

        panel.add(title);
        panel.add(description);
        panel.add(checker);

        return panel;
    }

    public JPanel fpsUnlocker(boolean fpsUnlocker)
    {
        final boolean[] toggle = new boolean[] {fpsUnlocker};
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(width, 100);
        panel.setLocation(0, 200);
        panel.setBackground(new Color(0, 0, 0,120));
        panel.setDoubleBuffered(true);
        panel.setBorder(new LineBorder(Color.white));

        JLabel title = new JLabel();
        title.setLayout(null);
        title.setDoubleBuffered(true);
        title.setText("FPS Unlocker");
        title.setFont(AndyBold.createFont(50));
        title.setForeground(Color.white);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int width = metrics.stringWidth(title.getText() + "a");
        int height = metrics.getHeight();
        title.setBounds(15, 15, width,  height);

        JLabel description = new JLabel();
        description.setLayout(null);
        description.setDoubleBuffered(true);
        description.setText("Overclocks your graphics for a smoother experience. Toggle this with precaution..");
        description.setFont(AndyBold.createFont(20));
        description.setForeground(new Color(142, 109, 64));
        metrics = description.getFontMetrics(description.getFont());
        width = metrics.stringWidth(description.getText().toUpperCase());
        height = metrics.getHeight();
        description.setBounds(20, 70, width,  height);

        // Create and configure the checkbox
        JLabel checker = new JLabel();
        checker.setText(fpsUnlock? "on" : "off");
        checker.setForeground(fpsUnlock? new Color(50, 200, 50) : new Color(200, 50, 50));
        checker.setFont(AndyBold.createFont(50));
        checker.setHorizontalAlignment(SwingConstants.CENTER);
        checker.setBounds(this.width - 120, (this.height - 60) / 2, 100, 60);
        checker.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(toggle[0])
                {
                    toggle[0] = false;
                    fpsUnlock = false;
                    verifyFPSCounterCap();
                    checker.setText("off");
                    InitialFrame.refreshRate = InitialFrame.mode.getRefreshRate();
                    fpsField.setText(InitialFrame.refreshRate + "");
                    checker.setForeground(new Color(200, 50, 50));
                    //System.out.println("h");
                }
                else
                {
                    toggle[0] = true;
                    fpsUnlock = true;
                    verifyFPSCounterCap();
                    checker.setForeground(new Color(50, 200, 50));
                    checker.setText("on");
                }
            }
        });

        panel.add(title);
        panel.add(description);
        panel.add(checker);

        return panel;
    }

    public JPanel graphicsQuality(String graphicsQuality)
    {
        final boolean[] toggle = new boolean[] {false};
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(width, 100);
        panel.setLocation(0, 400);
        panel.setBackground(new Color(0, 0, 0,120));
        panel.setDoubleBuffered(true);
        panel.setBorder(new LineBorder(Color.white));

        JLabel title = new JLabel();
        title.setLayout(null);
        title.setDoubleBuffered(true);
        title.setText("Graphics Quality");
        title.setFont(AndyBold.createFont(50));
        title.setForeground(Color.white);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int width = metrics.stringWidth(title.getText() + "a");
        int height = metrics.getHeight();
        title.setBounds(15, 15, width,  height);

        JLabel description = new JLabel();
        description.setLayout(null);
        description.setDoubleBuffered(true);
        description.setText("Adjusts detail, textures, and effects in background to enhance rendering. May impact performance.");
        description.setFont(AndyBold.createFont(20));
        description.setForeground(new Color(142, 109, 64));
        metrics = description.getFontMetrics(description.getFont());
        width = metrics.stringWidth(description.getText().toUpperCase());
        height = metrics.getHeight();
        description.setBounds(20, 70, width,  height);

        String[] quality = new String[]
        {
            "Very low",
            "Low",
            "Medium",
            "High",
            "Very high"
        };
        JComboBox<String> comboBox = new JComboBox<>(quality);
        comboBox.setFont(AndyBold.createFont(50));
        comboBox.setSelectedItem(graphicsQuality);
        metrics = comboBox.getFontMetrics(metrics.getFont());
        height = metrics.getHeight();
        comboBox.setBounds(this.width - 250 - 20, (this.height - height - 20) / 2, 250, height + 20);
        comboBox.addItemListener(e ->
        {
            if (e.getStateChange() == ItemEvent.SELECTED)
            {
                String selectedQuality = (String) e.getItem();
                InitialFrame.setGraphicsQuality(selectedQuality);
            }
        });

        panel.add(title);
        panel.add(description);
        panel.add(comboBox);

        return panel;
    }

    public JPanel fpsCounterAmount()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setEnabled(fpsUnlock);
        panel.setSize(width, 100);
        panel.setLocation(0, 300);
        panel.setBackground(new Color(0, 0, 0,120));
        panel.setDoubleBuffered(true);
        panel.setBorder(new LineBorder(Color.white));

        JLabel title = new JLabel();
        title.setLayout(null);
        title.setDoubleBuffered(true);
        title.setText("FPS Cap");
        title.setEnabled(fpsUnlock);
        title.setFont(AndyBold.createFont(50));
        title.setForeground(Color.white);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int width = metrics.stringWidth(title.getText() + "a");
        int height = metrics.getHeight();
        title.setBounds(15, 15, width,  height);

        JLabel description = new JLabel();
        description.setLayout(null);
        description.setDoubleBuffered(true);
        description.setText("Caps at desired frame rate. May impact performance.");
        description.setEnabled(fpsUnlock);
        description.setFont(AndyBold.createFont(20));
        description.setForeground(new Color(142, 109, 64));
        metrics = description.getFontMetrics(description.getFont());
        width = metrics.stringWidth(description.getText().toUpperCase());
        height = metrics.getHeight();
        description.setBounds(20, 70, width,  height);

        // the fps handler
        fpsField = new JTextField();
        fpsField.setFont(AndyBold.createFont(50));
        fpsField.setOpaque(false);
        fpsField.setEnabled(fpsUnlock);
        fpsField.setForeground(Color.white);
        fpsField.setText(InitialFrame.refreshRate + "");
        fpsField.setBounds(this.width - 120, (this.height - 60) / 2, 100, 60);
        PlainDocument document = (PlainDocument) fpsField.getDocument();
        document.setDocumentFilter(new TextFilter(DataType.TYPE_NUMERICAL, 5));
        fpsField.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent ignored) {}

            @Override
            public void focusLost(FocusEvent e)
            {
                int x = Integer.parseInt(fpsField.getText());
                x = Math.max(x, 60);
                InitialFrame.refreshRate = x;
                fpsField.setText(x + "");
            }
        });
        fpsField.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                {
                    fpsField.transferFocus();
                }
            }
        });

        panel.add(title);
        panel.add(description);
        panel.add(fpsField);

        return panel;
    }

    public JPanel waveTrail(boolean waveTrail)
    {
        final boolean[] toggle = new boolean[] {waveTrail};
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(width, 100);
        panel.setLocation(0, 500);
        panel.setBackground(new Color(0, 0, 0,120));
        panel.setDoubleBuffered(true);
        panel.setBorder(new LineBorder(Color.white));

        JLabel title = new JLabel();
        title.setLayout(null);
        title.setDoubleBuffered(true);
        title.setText("Trail Label Waving");
        title.setFont(AndyBold.createFont(50));
        title.setForeground(Color.white);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int width = metrics.stringWidth(title.getText() + "a");
        int height = metrics.getHeight();
        title.setBounds(15, 15, width,  height);

        JLabel description = new JLabel();
        description.setLayout(null);
        description.setDoubleBuffered(true);
        description.setText("The trail labels are having wave animations.");
        description.setFont(AndyBold.createFont(20));
        description.setForeground(Color.gray);
        metrics = description.getFontMetrics(description.getFont());
        width = metrics.stringWidth(description.getText().toUpperCase());
        height = metrics.getHeight();
        description.setBounds(20, 70, width,  height);

        // Create and configure the checkbox
        JLabel checker = new JLabel();
        checker.setText(waveTrail? "on" : "off");
        checker.setForeground(waveTrail? new Color(50, 200, 50) : new Color(200, 50, 50));
        checker.setFont(AndyBold.createFont(50));
        checker.setHorizontalAlignment(SwingConstants.CENTER);
        checker.setBounds(this.width - 120, (this.height - 60) / 2, 100, 60);
        checker.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(toggle[0])
                {
                    toggle[0] = false;
                    SettingsPane.waveTrail = false;
                    checker.setText("off");
                    checker.setForeground(new Color(200, 50, 50));
                    //System.out.println("h");
                }
                else
                {
                    toggle[0] = true;
                    SettingsPane.waveTrail = true;
                    checker.setForeground(new Color(50, 200, 50));
                    checker.setText("on");
                }
            }
        });

        panel.add(title);
        panel.add(description);
        panel.add(checker);

        return panel;
    }

    public JPanel snowVisibility(boolean snowVisible)
    {
        final boolean[] toggle = new boolean[] {snowVisible};
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(width, 100);
        panel.setLocation(0, 600);
        panel.setBackground(new Color(0, 0, 0, 120));
        panel.setDoubleBuffered(true);
        panel.setBorder(new LineBorder(Color.white));

        JLabel title = new JLabel();
        title.setLayout(null);
        title.setDoubleBuffered(true);
        title.setText("Snow Animation Visibility");
        title.setFont(AndyBold.createFont(50));
        title.setForeground(Color.white);
        FontMetrics metrics = title.getFontMetrics(title.getFont());
        int width = metrics.stringWidth(title.getText() + "a");
        int height = metrics.getHeight();
        title.setBounds(15, 15, width, height);

        JLabel description = new JLabel();
        description.setLayout(null);
        description.setDoubleBuffered(true);
        description.setText("Snow effect animations are showing on background.");
        description.setFont(AndyBold.createFont(20));
        description.setForeground(Color.gray);
        metrics = description.getFontMetrics(description.getFont());
        width = metrics.stringWidth(description.getText().toUpperCase());
        height = metrics.getHeight();
        description.setBounds(20, 70, width, height);

        // Create and configure the checkbox
        JLabel checker = new JLabel();
        checker.setText(snowVisible ? "on" : "off");
        checker.setForeground(snowVisible ? new Color(50, 200, 50) : new Color(200, 50, 50));
        checker.setFont(AndyBold.createFont(50));
        checker.setHorizontalAlignment(SwingConstants.CENTER);
        checker.setBounds(this.width - 120, (this.height - 60) / 2, 100, 60);
        checker.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(toggle[0]) {
                    toggle[0] = false;
                    checker.setText("off");
                    SettingsPane.snowVisible = false;
                    checker.setForeground(new Color(200, 50, 50));
                    //System.out.println("h");
                }
                else {
                    toggle[0] = true;
                    SettingsPane.snowVisible = true;
                    checker.setForeground(new Color(50, 200, 50));
                    checker.setText("on");
                }
            }
        });

        panel.add(title);
        panel.add(description);
        panel.add(checker);

        return panel;
    }
}