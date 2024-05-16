package pabunot;

import pabunot.graphics.Snow;
import pabunot.graphics.TrailLabel;
import pabunot.interfaces.PabunotMakingPane;
import pabunot.interfaces.PabunotPickerPanel;
import pabunot.interfaces.PabunotSection;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.palabunutan.PalabunotGridPane;
import pabunot.streamio.PabunotMaker;
import pabunot.util.AndyBold;
import pabunot.util.Intention;
import pabunot.util.Theme;
import pabunot.util.Tip;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.DisplayMode;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Random;

/**
 * The {@code InitialFrame} class serves as the main window for the Pabunot app,
 * handling the initialization and rendering of the game's graphical user interface.
 * It extends {@link JFrame} and implements {@link Runnable} to manage the game loop and rendering processes.
 *
 * <p>This class is responsible for setting up the game environment, including the creation of
 * UI components like buttons and labels, managing animations, and handling user interactions.
 * The game's main loop is driven by a separate thread to ensure smooth performance.</p>
 *
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Setting up the main frame and its properties such as size, visibility, and layout.</li>
 *   <li>Initializing game components like {@link Snow}, {@link TrailLabel}, and {@link PalabunotGridPane}.</li>
 *   <li>Handling the game's rendering loop, which updates animations and UI elements.</li>
 *   <li>Managing user interactions through mouse events and implementing custom cursor settings.</li>
 * </ul>
 *
 * <p>The frame is designed to be undecorated and utilizes custom drawing for its components and animations,
 * leveraging Java's 2D graphics capabilities.</p>
 *
 * @see JFrame
 * @see Runnable
 * @see Snow
 * @see TrailLabel
 * @see PalabunotGridPane
 *
 * @author Francis (iid3rp) Madanlo
 */
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
    public static Image mainBackGround;
    public static Image mainMiddleGround;
    @Intention InitialFrame frame = this;
    public static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static GraphicsDevice gd = ge.getDefaultScreenDevice();
    public static DisplayMode dm = gd.getDisplayMode();
    public static int refreshRate = dm.getRefreshRate() * 3;
    public static Snow snow;
    public int reference;
    public static boolean isRunning = true;
    public JPanel contentPanel;
    public static boolean isDragging;
    public static Point offset;
    PalabunotGridPane bunot;
    PabunotPickerPanel picker;
    public JPanel glassPane;
    public int fps;
    private JLabel framesPerSecond;
    private PabunotMakingPane createPabunot;
    public TrailLabel labels = new TrailLabel(">>><<<EED    le fiche    >>><<<EED", 20, 680, 690, TrailLabel.rainbow);
    public TrailLabel labels2 = new TrailLabel("Francis L. Madanlo", 20, 90, 100, new Color[]{Color.WHITE, Color.WHITE});

    public static int x = 0;
    public static int y = 0;

    private JTextArea area;
    private boolean fpsUnlocked = false;
    private Cursor pabunotCursor;
    private PabunotSection pabunotProcess;

    public InitialFrame()
    {
        super();
        pabunotCursor = createCursor();
        initializeComponent();
        snow = new Snow();
        contentPanel = createContentPanel();
        pabunotProcess = new PabunotSection(this, new PalabunotGrid());
        glassPane = createGlassPane();
        framesPerSecond = createFPS();
        createPabunot = new PabunotMakingPane(frame, "BSIT-BTM Pabunot");

        start = createStart();
        settings = createSettings();
        exit = createExit();
        titleLabel = new TrailLabel("Pabunot!", 150, 100, 120, TrailLabel.rainbow);

        picker = new PabunotPickerPanel(this);
        add(glassPane);
        //setContentPane(contentPanel); // the main menu
        //setContentPane(createPabunot); // the pabunot section where do you start making one
        //setContentPane(pabunotProcess); // the pabunot section when its starting...
        setContentPane(picker); // the process when doing the pabunot.
        setGlassPane(glassPane);
        

        glassPane.setVisible(true);

        bunot = new PalabunotGridPane(this, new PalabunotGrid(40, 40, "Hello World!", Theme.PINK_HEARTS));
        addComponents();
    }

    private Cursor createCursor()
    {
        Image i;
        BufferedImage img;
        try {
            i = ImageIO.read(new File(
                     Objects.requireNonNull(InitialFrame.class.getResource("Resources/pabunotCursorNew.png")).getPath()));
            i = i.getScaledInstance(100, 100, Image.SCALE_FAST);
            img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            g2d.drawImage(i, 0, 0, null);
            g2d.dispose();

        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        return Toolkit.getDefaultToolkit().createCustomCursor(img, new Point(0, 0), "topLeftCursor");
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
        for(JLabel l : labels)
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

        long lastSnowRenderTime = System.nanoTime();
        double snowRenderInterval = 1d / 90; // 90 FPS for snow rendering

        while(isRunning) {
            while(isRunning) {
                long currentTime = System.nanoTime();
                long passedTime = currentTime - prevTime;
                prevTime = currentTime;
                unprocessedSeconds += passedTime / 1_000_000_000.0;

                boolean snowRendered = false;

                while(unprocessedSeconds > secondsTick) {
                    unprocessedSeconds -= secondsTick;
                    tickCount++;

                    if(tickCount % refreshRate == 0) {
                        fps = frames;
                        framesPerSecond.setText("FPS Counter " + (fpsUnlocked ? "(Unlocked): " : "(locked): ") + fps);
                        prevTime += 1000;
                        frames = 0;
                        tickCount = 0;
                        if(seconds % 6 == 0) {
                            for(JLabel l : labels) {
                                contentPanel.remove(l);
                            }
                            labels = new TrailLabel(Tip.stuff[new Random().nextInt(Tip.stuff.length)], 20, 680, 690, TrailLabel.rainbow);
                            for(JLabel l : labels) {
                                contentPanel.add(l);
                            }
                        }
                        seconds++;
                    }
                    if ((currentTime - lastSnowRenderTime) / 1_000_000_000.0 > snowRenderInterval)
                    {
                        snow.render(currentTime);
                        lastSnowRenderTime = currentTime;
                    }
                    if(!fpsUnlocked) {
                        try {
                            // waving trail labels...
                            titleLabel.wave(currentTime);
                            labels.wave(currentTime);
                            labels2.wave(currentTime);
                            createPabunot.title.wave(currentTime);
                            picker.title.wave(currentTime);
                            // rendering process
                            getContentPane().repaint();

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
                    picker.title.wave(currentTime);
                    getContentPane().repaint();
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
                    mainBackGround = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/hello.png")));
                    mainMiddleGround = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/pabunotMiddleGround.png")));
                    mainBackGround = mainBackGround.getScaledInstance(1328, 756, Image.SCALE_SMOOTH);
                    mainMiddleGround = mainMiddleGround.getScaledInstance(1328, 756, Image.SCALE_SMOOTH);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                InitialFrame.render(g);
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

    public static void render(Graphics g)
    {
        g.drawImage(mainBackGround, x, y, null);
        g.drawImage(snow, 0, 0, null);
        g.drawImage(mainMiddleGround, (x * -1) - 48, (y * -1) - 36, null);
        g.setColor(new Color(0, 0, 0, 140));
        g.fillRect(0, 0, WIDTH, HEIGHT);
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
        setCursor(pabunotCursor);
        setTitle("");
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        setVisible(true);
    }

    public static double sineEase(double currentTime, double duration, double startY, double endY, int delaySine, int delayCosine) {
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