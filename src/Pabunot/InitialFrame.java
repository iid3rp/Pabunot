package Pabunot;

import Pabunot.Graphics.Snow;
import Pabunot.Graphics.TrailLabel;
import Pabunot.Interface.PabunotMakingPane;
import Pabunot.Pabunot.PabunotGrid;
import Pabunot.Pabunot.PabunotGridPane;
import Pabunot.StreamIO.PabunotMaker;
import Pabunot.Utils.AndyBold;
import Pabunot.Utils.Intention;
import Pabunot.Utils.Theme;
import Pabunot.Utils.Tip;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Random;

public class InitialFrame extends JFrame implements Runnable
{
    public static int WIDTH = 1280;
    public static int HEIGHT = 720;
    public static double snowMultiplierX;
    public static double snowMultiplierY;
    private final JLabel start;
    private final JLabel settings;
    private final JLabel exit;
    private final TrailLabel titleLabel;
    public Image mainBackground;
    @Intention InitialFrame frame = this;
    public static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static GraphicsDevice gd = ge.getDefaultScreenDevice();
    public static DisplayMode dm = gd.getDisplayMode();
    public static int refreshRate = dm.getRefreshRate();
    public Snow snow;
    public int reference;
    public static boolean isRunning = true;
    public JPanel contentPanel;
    public boolean isDragging;
    public Point offset;
    PabunotGridPane bunot;

    public JPanel glassPane;
    public int fps;
    private JLabel framesPerSecond;
    private PabunotMakingPane createPabunot;
    public TrailLabel labels = new TrailLabel(">>><<<EED    le fiche    >>><<<EED", 20, 680, 690, Tip.colors);
    public TrailLabel labels2 = new TrailLabel("Francis L. Madanlo", 20, 90, 100, new Color[]{Color.WHITE, Color.WHITE});

    public static int x = 0;
    public static int y = 0;

    private JTextArea area;
    private boolean fpsUnlocked = false;

    public InitialFrame()
    {
        super();
        initializeComponent();
        snow = new Snow();
        contentPanel = createContentPanel();
        glassPane = createGlassPane();
        framesPerSecond = createFPS();
        createPabunot = new PabunotMakingPane(frame, "BSIT-BTM Pabunot");
        start = createStart();
        settings = createSettings();
        exit = createExit();
        titleLabel = new TrailLabel("Pabunot!", 150, 100, 120, TrailLabel.rainbow);

        add(glassPane);
        //setContentPane(contentPanel);
        setContentPane(createPabunot);
        setGlassPane(glassPane);

        glassPane.setVisible(true);

        bunot = new PabunotGridPane(this, new PabunotGrid(40, 40, "Hello World!", 12345, Theme.PINK_HEARTS));
        addComponents();
    }

    private JLabel createFPS()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("FPS Counter: 0");
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(20));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, 10, width + 200, height);
        return label;
    }

    private JLabel createGitLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("git repository...");
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(20));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(10, getHeight() - height - 10, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                try {
                    Desktop.getDesktop().browse(new URI("https://github.com/iid3rp/pabunot"));
                }
                catch(Exception ex) {
                    throw new RuntimeException(ex);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.magenta);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.white);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                parallaxMove(new Point(label.getX() + e.getX(), label.getY() + e.getY()));
            }
        });
        return label;
    }

    public void addComponents()
    {
        contentPanel.add(framesPerSecond);
        contentPanel.add(createGitLabel());
        for(JLabel l : titleLabel)
        {
            contentPanel.add(l);
        }
        contentPanel.add(start);
        contentPanel.add(settings);
        contentPanel.add(exit);
    }

    private JPanel createGlassPane()
    {
        JPanel panel = new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                //g.drawImage(snow, 0, 0, null);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(null);
        panel.setDoubleBuffered(true);
        panel.setSize(getWidth(), getHeight());
        return panel;
    }

    @Override
    public void run()
    {
        int frames = 0;
        double unprocessedSeconds = 0;
        long prevTime = System.nanoTime();
        double secondsTick = ((1d / refreshRate));
        int tickCount = 0;
        boolean ticked = false;
        int seconds = 0;
        while(isRunning) {
            while(isRunning) {
                long currentTime = System.nanoTime();
                long passedTime = currentTime - prevTime;
                prevTime = currentTime;
                unprocessedSeconds += passedTime / 1_000_000_000.0;

                while(unprocessedSeconds > secondsTick) {
                    unprocessedSeconds -= secondsTick;
                    tickCount++;

                    if(tickCount % refreshRate == 0) {
                        fps = frames;
                        framesPerSecond.setText("FPS Counter " + (fpsUnlocked ? "(Unlocked): " : "(locked):") + fps);
                        prevTime += 1000;
                        frames = 0;
                        tickCount = 0;
                        if(seconds % 6 == 0) {
                            for(JLabel l : labels) {
                                contentPanel.remove(l);
                            }
                            labels = new TrailLabel(Tip.stuff[new Random().nextInt(Tip.stuff.length)], 20, 680, 690,
                                    Tip.colors);
                            for(JLabel l : labels) {
                                contentPanel.add(l);
                            }
                        }
                        seconds++;
                    }
                    snow.render(currentTime);
                    if(!fpsUnlocked) {
                        try {
                            titleLabel.wave(currentTime);
                            labels.wave(currentTime);
                            labels2.wave(currentTime);
                            createPabunot.title.wave(currentTime);
                            contentPanel.repaint();
                            createPabunot.repaint();

                            frames++;
                        }
                        catch(Exception ignored) {
                        }
                    }
                }

                if(fpsUnlocked) {

                    titleLabel.wave(currentTime);
                    // the wave thingies
                    labels.wave(currentTime);
                    labels2.wave(currentTime);
                    createPabunot.title.wave(currentTime);
                    contentPanel.repaint();
                    createPabunot.repaint();
                    frames++;
                }
            }
        }
    }

    private JPanel createContentPanel()
    {
        JPanel panel = new JPanel()
        {
            {
                try {
                    mainBackground = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/hello.png")));
                    mainBackground = mainBackground.getScaledInstance(1328, 756, Image.SCALE_SMOOTH);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(mainBackground, x, y, null);
                g.setColor(new Color(0, 0, 0, 120));
                g.fillRect(0, 0, getWidth(), getHeight());
                g.drawImage(snow, 0, 0, null);
            }
        };
        panel.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    isDragging = true;
                    offset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    isDragging = false;
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                x = -24;
                y = -18;
            }

        });
        panel.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if (isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - offset.x;
                    int deltaY = currentMouse.y - offset.y;

                    setLocation(deltaX, deltaY);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                parallaxMove(e.getPoint());
            }
        });
        panel.setLayout(null);
        panel.setDoubleBuffered(true);
        panel.setSize(getWidth(), getHeight());
        return panel;
    }

    public static void parallaxMove(Point e)
    {
        x = (int) -((e.getX() * 48) / WIDTH);
        y = (int) -((e.getY() * 36) / HEIGHT);

        snowMultiplierX = (int) ((e.getX() / WIDTH) * 4);
        snowMultiplierY = (int) ((e.getY() / HEIGHT) * 2);
    }

    private void initializeComponent()
    {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setSize(getPreferredSize());
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
    }

    @Deprecated @Intention(design = "for easing x, but for some reason it's in no use as of now...")
    public static double sineEaseX(double currentTime, double duration, double startX, double endX, int delaySine, int delayCosine) {
        double easingSine = (Math.sin(currentTime * Math.PI * 2 / duration) + 1) / 2;
        double easingCosine = (Math.cos(currentTime * Math.PI * 2 / duration) + 1) / 2;
        double easing = ((easingSine * delaySine) + (easingCosine * delayCosine)) / (delaySine + delayCosine);
        return (startX + (endX - startX) * easing);
    }

    public static double sineEaseY(double currentTime, double duration, double startY, double endY, int delaySine, int delayCosine) {
        double easingSine = (Math.sin(currentTime * Math.PI * 2 / duration) + 1) / 2;
        double easingCosine = (Math.cos(currentTime * Math.PI * 2 / duration) + 1) / 2;
        double easing = ((easingSine * delaySine) + (easingCosine * delayCosine)) / (delaySine + delayCosine);
        return (startY + (endY - startY) * easing);
    }

    private JLabel createStart()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Start Game!");
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(50));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds((getWidth() / 2) - (width / 2), 350, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.out.println("Coming soon!");
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.green);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.white);
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(), label.getY() + e.getY()));
            }
        });
        return label;
    }

    private JLabel createSettings()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Settings");
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(50));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds((getWidth() / 2) - (width / 2), 420, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.out.println("Coming soon!");
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(new Color(15, 123, 218));
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.white);
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(), label.getY() + e.getY()));
            }
        });
        return label;
    }

    private JLabel createExit()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Exit");
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(50));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds((getWidth() / 2) - (width / 2), 490, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.dispose();
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.white);
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(), label.getY() + e.getY()));
            }
        });
        return label;
    }

    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] a)
    {
        InitialFrame frame = new InitialFrame();
        @Intention var x = new File(PabunotMaker.pabunotDir).mkdirs();
        frame.start();
    }
}