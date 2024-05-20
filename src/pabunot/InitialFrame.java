package pabunot;

import pabunot.graphics.Snow;
import pabunot.graphics.TrailLabel;
import pabunot.hardware.MainMenuKey;
import pabunot.hardware.TitleTyping;
import pabunot.interfaces.PabunotMakingPane;
import pabunot.interfaces.PabunotPickerPanel;
import pabunot.interfaces.PabunotSection;
import pabunot.interfaces.TrailTitlePanel;
import pabunot.palabunutan.PalabunotGridPane;
import pabunot.streamio.PabunotMaker;
import pabunot.util.AndyBold;
import pabunot.util.Intention;
import pabunot.util.Tip;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
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
public class
InitialFrame extends JFrame implements Runnable
{
    public static double snowMultiplierX;
    public static double snowMultiplierY;
    private final JLabel start;
    private final JLabel settings;
    private final JLabel exit;
    private JLabel gitRepo;
    private final TrailLabel titleLabel;
    public static Image mainBackGround;
    public static Image mainMiddleGround;
    private static Image mainMiddleBackGround;
    public TitleTyping typeEvent;
    public PabunotSection section;
    @Intention InitialFrame frame = this;
    public static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static GraphicsDevice gd = ge.getDefaultScreenDevice();
    public static DisplayMode dm = gd.getDisplayMode();
    public static int refreshRate = dm.getRefreshRate() * 2;
    public static int WIDTH;
    public static int HEIGHT;
    public static Snow snow;
    public static boolean isRunning = true;
    public JPanel mainMenu;
    public JPanel contentPanel;
    public static boolean isDragging;
    public static Point offset;
    public PabunotPickerPanel picker;
    public JPanel glassPane;
    public int fps;
    private JLabel framesPerSecond;
    public PabunotMakingPane createPabunot;
    public TrailLabel tipLabel;
    public static int snowX;
    public static int snowY;
    public TrailTitlePanel titlePanel;
    private boolean fpsUnlocked = false;
    private Cursor pabunotCursor;
    private PabunotSection pabunotProcess;
    private MainMenuKey mainKey;

    public InitialFrame()
    {
        super();
        WIDTH = (int) (gd.getDisplayMode().getWidth() / 1.25);
        HEIGHT = (int) (gd.getDisplayMode().getHeight() / 1.25);
        pabunotCursor = createCursor();
        initializeComponent();

        // key events
        mainKey = new MainMenuKey(this);
        typeEvent = new TitleTyping(this);

        snow = new Snow();
        contentPanel = createContentPanel();
        mainMenu = createMainMenu();
        gitRepo = createGitLabel();

        glassPane = createGlassPane();
        framesPerSecond = createFPS();
        titlePanel = new TrailTitlePanel(this);

        start = createStart();
        settings = createSettings();
        exit = createExit();

        // trail labels here
        tipLabel = new TrailLabel(">>><<<EED    le fiche    >>><<<EED", 20, InitialFrame.HEIGHT - 50, InitialFrame.HEIGHT - 40, TrailLabel.rainbow);
        titleLabel = new TrailLabel("Pabunot!", 150, (int) ((InitialFrame.HEIGHT * 0.3) - 10), (int) ((InitialFrame.HEIGHT * 0.3) + 10), TrailLabel.rainbow);

        // bunot = new PalabunotGridPane(this, new PalabunotGrid(40, 40, "Hello World!", Theme.PINK_HEARTS));
        addComponents();

        picker = new PabunotPickerPanel(this);
        setContentPane(contentPanel);
        getContentPane().add(picker);
        getContentPane().add(mainMenu);
        getContentPane().add(titlePanel);
        setGlassPane(glassPane);

        validate();
        glassPane.setVisible(true);
        setVisible(true);
    }

    private JPanel createContentPanel()
    {
        JPanel panel = new JPanel()
        {
            {
                try {
                    mainBackGround = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/hello.png")));
                    mainMiddleGround = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/pabunotMiddleGround.png")));
                    mainMiddleBackGround = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/pabunotMiddleBackGround.png")));
                    mainBackGround = mainBackGround.getScaledInstance((int) (InitialFrame.WIDTH * 1.05),
                            (int) (InitialFrame.HEIGHT * 1.05), Image.SCALE_SMOOTH);
                    mainMiddleGround = mainMiddleGround.getScaledInstance((int) (InitialFrame.WIDTH * 1.1),
                            (int) (InitialFrame.HEIGHT * 1.05), Image.SCALE_SMOOTH);
                    mainMiddleBackGround = mainMiddleBackGround.getScaledInstance((int) (InitialFrame.WIDTH * 1.1),
                            (int) (InitialFrame.HEIGHT * 1.05), Image.SCALE_SMOOTH);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void paintComponent(Graphics g)
            {
                InitialFrame.render(g);
            }
        };
        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseExited(MouseEvent e)
            {
                snowX = -24;
                snowY = -18;
            }

        });
        panel.addMouseMotionListener(new MouseMotionAdapter()
        {
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
        label.setBounds(0, 0, width, height);
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
        mainMenu.add(framesPerSecond);
        mainMenu.add(gitRepo);
        for(JLabel l : titleLabel)
        {
            mainMenu.add(l);
        }
        for(JLabel l : tipLabel)
        {
            mainMenu.add(l);
        }
        mainMenu.add(start);
        mainMenu.add(settings);
        mainMenu.add(exit);
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
        long prevTime = System.currentTimeMillis();
        double secondsTick = ((1d / refreshRate));
        int tickCount = 0;
        boolean ticked = false;
        int seconds = 0;

        long lastSnowRenderTime = System.currentTimeMillis();
        double snowRenderInterval = 1d / 60; // 90 FPS for snow rendering

        while(isRunning) {
            long currentTime = System.currentTimeMillis();
            long passedTime = currentTime - prevTime;
            prevTime = currentTime;
            unprocessedSeconds += (double) passedTime / 1_000;

            boolean snowRendered = false;

            while(unprocessedSeconds > secondsTick)
            {
                unprocessedSeconds -= secondsTick;
                tickCount++;

                if(tickCount % refreshRate == 0)
                {
                    fps = frames;
                    framesPerSecond.setText("FPS Counter " + (fpsUnlocked ? "(Unlocked): " : "(locked): ") + fps);
                    frames = 0;
                    tickCount = 0;
                    tipUpdate(seconds);
                    seconds++;
                }
                if ((double) (currentTime - lastSnowRenderTime) / 1_000 > snowRenderInterval)
                {
                    snow.render(currentTime);
                    lastSnowRenderTime = currentTime;
                }
                if(!fpsUnlocked) {
                    try {
                        // waving trail labels...

                        waveComponents(currentTime);

                        // rendering process
                        getContentPane().repaint();

                        frames++;
                    }
                    catch(Exception ignored) {
                    }
                }
            }

            if(fpsUnlocked) {

                waveComponents(currentTime);

                getContentPane().repaint();
                //getGlassPane().repaint();
                frames++;
            }
        }

    }

    private void tipUpdate(int seconds)
    {
        if(seconds % 6 == 0)
        {
            for(JLabel l : tipLabel)
            {
                mainMenu.remove(l);
            }
            tipLabel = new TrailLabel(Tip.stuff[new Random().nextInt(Tip.stuff.length)], 20, InitialFrame.HEIGHT - 50, InitialFrame.HEIGHT - 40, TrailLabel.rainbow);
            for(JLabel l : tipLabel)
            {
                mainMenu.add(l);
            }
        }
    }

    private void waveComponents(long currentTime)
    {
        titleLabel.wave(currentTime);
        tipLabel.wave(currentTime);
        picker.title.wave(currentTime);
        titlePanel.title.wave(currentTime);
        titlePanel.titleLabel.wave(currentTime);
        if (createPabunot != null)
        {
            createPabunot.title.wave(currentTime);
        }
    }

    private JPanel createMainMenu()
    {
        JPanel panel = new JPanel();
        panel.addMouseListener(new MouseAdapter()
        {

            @Override
            public void mouseExited(MouseEvent e)
            {
                snowX = -24;
                snowY = -18;
            }

        });
        panel.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                parallaxMove(e.getPoint());
            }
        });
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setIgnoreRepaint(true);
        panel.setDoubleBuffered(true);
        panel.setSize(getWidth(), getHeight());
        return panel;
    }

    public static void render(Graphics g)
    {
        g.drawImage(mainBackGround, snowX, snowY, null);
        g.drawImage(snow.particle, 0, 0, null);
        g.drawImage(mainMiddleBackGround, ((snowX * -1) - 48) / 2, ((snowY * -1) - 36) / 2, null);
        g.drawImage(mainMiddleGround, (snowX * -1) - 48, (snowY * -1) - 36, null);
        g.setColor(new Color(0, 0, 0, 90));
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    public static void parallaxMove(Point e)
    {
        snowX = (int) -((e.getX() * 48) / WIDTH);
        snowY = (int) -((e.getY() * 36) / HEIGHT);

        snowMultiplierX = (int) ((e.getX() / WIDTH) * ((InitialFrame.WIDTH / refreshRate)) / 2);
        snowMultiplierY = (int) ((e.getY() / HEIGHT) * ((InitialFrame.HEIGHT / refreshRate) / 2));
    }

    private void initializeComponent()
    {
        setSize(new Dimension(WIDTH, HEIGHT));
        setPreferredSize(getSize());
        setCursor(pabunotCursor);
        setIgnoreRepaint(true);
        setTitle("Pabunot");
        setMinimumSize(getPreferredSize());
        setMaximumSize(getPreferredSize());
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(true);
        addKeyListener(mainKey);
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
        label.setBounds((getWidth() / 2) - (width / 2), (int) (InitialFrame.HEIGHT * 0.50), width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                play();
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
        label.setBounds((getWidth() / 2) - (width / 2),  (int) (InitialFrame.HEIGHT * 0.5) + 75, width, height);
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
        label.setBounds((getWidth() / 2) - (width / 2),  (int) (InitialFrame.HEIGHT * 0.5) + 150, width, height);
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
        SwingUtilities.invokeLater(() ->
        {
            InitialFrame frame = new InitialFrame();
            @Intention var x = new File(PabunotMaker.pabunotDir).mkdirs();
            frame.start();
        });
    }

    public void play()
    {
        mainMenu.setVisible(false);
        picker.setVisible(true);
    }

    @Override
    public void validate()
    {
        super.validate();
        gitRepo.setLocation(10, getHeight() - gitRepo.getHeight() - 10);
    }
}