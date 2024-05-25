package pabunot;

import pabunot.controls.TitleTyping;
import pabunot.graphics.Snow;
import pabunot.graphics.TrailLabel;
import pabunot.interfaces.PabunotEnding;
import pabunot.interfaces.PabunotMakingPane;
import pabunot.interfaces.PabunotPickerPanel;
import pabunot.interfaces.PabunotSection;
import pabunot.interfaces.PrizePicked;
import pabunot.interfaces.SettingsPane;
import pabunot.interfaces.SettingsSection;
import pabunot.interfaces.TrailTitlePanel;
import pabunot.palabunutan.PalabunotGridPane;
import pabunot.streamio.PabunotMaker;
import pabunot.streamio.PabunotReader;
import pabunot.streamio.PabunotWriter;
import pabunot.util.AndyBold;
import pabunot.util.Intention;
import pabunot.util.Tip;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Random;

/**
 * The {@code InitialFrame} class serves as the main
 * window for the Pabunot app, handling the initialization
 * and rendering of the game's graphical user interface.
 * It extends {@link JFrame} and implements {@link Runnable}
 * to manage the game loop and rendering processes.
 *
 * <p>This class is responsible for setting up the game environment,
 * including the creation of UI components like buttons and labels,
 * managing animations, and handling user interactions.
 * The game's main loop is driven by a separate thread to
 * ensure smooth performance.</p>
 *
 * <p>Key functionalities include:</p>
 * <ul>
 *   <li>Setting up the main frame and its properties such as
 *   size, visibility, and layout.</li>
 *   <li>Initializing game components like {@link Snow},
 *   {@link TrailLabel}, and {@link PalabunotGridPane}.</li>
 *   <li>Handling the game's rendering loop, which updates animations
 *   and UI elements.</li>
 *   <li>Managing user interactions through mouse events and implementing
 *   custom cursor settings.</li>
 * </ul>
 *
 * <p>The frame is designed to be undecorated and utilizes custom drawing
 * for its components and animations,
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
    public static int WIDTH;
    public static int HEIGHT;
    public static double scaleFactor = 2;
    public static double snowMultiplierX;
    public static double snowMultiplierY;
    public static GraphicsEnvironment environment =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static GraphicsDevice device =
            environment.getDefaultScreenDevice();
    public static DisplayMode mode =
            device.getDisplayMode();
    public static int refreshRate =
            mode.getRefreshRate() * 2;
    public static Snow snow;
    public static boolean isRunning = true;
    public static TrailLabel tipLabel;
    public static int parallaxX;
    public static int parallaxY;
    public static Image mainBackGround;
    public static Image mainMiddleGround;
    private static Image mainMiddleBackGround;
    private static JLabel gitRepo;
    private static boolean snowVisible;
    private final JLabel start;
    private final JLabel settings;
    private final JLabel exit;
    private final TrailLabel titleLabel;
    public PrizePicked prizePicked;
    public PabunotEnding ended;
    public TitleTyping typeEvent;
    public PabunotSection section;
    public @Intention InitialFrame frame = this;
    public JPanel mainMenu;
    public static JPanel contentPanel;
    public PabunotPickerPanel picker;
    public int fps;
    private static JLabel framesPerSecond;
    public PabunotMakingPane createPabunot;
    public TrailTitlePanel titlePanel;
    private boolean fpsUnlocked = false;
    private Cursor pabunotCursor;
    private SettingsSection settingsPanel;
    private static int parallaxControl;

    public InitialFrame()
    {
        super();
        verifyDimensionFromScalingSystem();
        pabunotCursor = createCursor();
        initializeComponent();
        setImages();

        // key events
        typeEvent = new TitleTyping(this);

        snow = new Snow();
        contentPanel = createContentPanel();
        mainMenu = createMainMenu();
        settingsPanel = new SettingsSection(this);
        gitRepo = createGitLabel();

        framesPerSecond = createFPS();
        titlePanel = new TrailTitlePanel(this);

        start = createStart();
        settings = createSettings();
        exit = createExit();

        // trail labels here
        tipLabel = new TrailLabel(
                ">>><<<EED    le fiche    >>><<<EED",
                20,
                InitialFrame.HEIGHT - 50,
                InitialFrame.HEIGHT - 40,
                TrailLabel.rainbow
        );
        titleLabel = new TrailLabel(
                "Pabunot!",
                150,
                (int) ((InitialFrame.HEIGHT * 0.3) - 10),
                (int) ((InitialFrame.HEIGHT * 0.3) + 10),
                TrailLabel.rainbow
        );

        // adding components
        addComponents();

        picker = new PabunotPickerPanel(this);
        setContentPane(contentPanel);
        getContentPane().add(picker);
        getContentPane().add(mainMenu);
        getContentPane().add(titlePanel);
        getContentPane().add(settingsPanel);

        initializeGame();
        setVisible(true);
    }

    private void initializeGame()
    {
        setGraphicsQuality(SettingsPane.graphicsQuality);
        setParallax(SettingsPane.parallax);
        setFPSCountVisibility(SettingsPane.fpsCountVisibility);
    }

    public static void setFPSCountVisibility(boolean fpsCountVisibility)
    {
        SettingsPane.fpsCountVisibility = fpsCountVisibility;
        if(fpsCountVisibility)
        {
            contentPanel.add(framesPerSecond);
        }
        else contentPanel.remove(framesPerSecond);
    }

    public static void setParallax(boolean parallax)
    {
        parallaxControl = parallax? 1 : 0;
        SettingsPane.parallax = parallax;
    }

    public static void setGraphicsQuality(String quality)
    {
        switch (quality)
        {
            case "Very low":
                scaleFactor = 3;
                break;
            case "Low":
                scaleFactor = 2.5;
                break;
            case "Medium":
                scaleFactor = 2;
                break;
            case "High":
                scaleFactor = 1.5;
                break;
            case "Very high":
                scaleFactor = 1;
                break;
            default:
                break;
        }
        try {
            int width = (int) ((InitialFrame.WIDTH / scaleFactor) * 1.1);
            int height = (int) ((InitialFrame.HEIGHT / scaleFactor) * 1.1);

            Image image1 = ImageIO.read(
                    Objects.requireNonNull(
                            InitialFrame.class.getResource(
                                    "Resources/hello.png"
                            )
                    )
            );
            Image image2 = ImageIO.read(
                    Objects.requireNonNull(
                            InitialFrame.class.getResource(
                                    "Resources/pabunotMiddleGround.png"
                            )
                    )
            );
            Image image3 = ImageIO.read(
                    Objects.requireNonNull(
                            InitialFrame.class.getResource(
                                    "Resources/pabunotMiddleBackGround.png"
                            )
                    )
            );
            mainBackGround = image1.getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH
            );
            mainMiddleGround = image2.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            mainMiddleBackGround = image3.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            System.out.println(scaleFactor);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            SettingsPane.graphicsQuality = quality;
        }
    }

    private void verifyDimensionFromScalingSystem()
    {
        AffineTransform at = device.getDefaultConfiguration().getDefaultTransform();

        double scaleX = at.getScaleX();
        double scaleY = at.getScaleY();

        InitialFrame.WIDTH = (int) (device.getDisplayMode().getWidth() / scaleX);
        InitialFrame.HEIGHT = (int) (device.getDisplayMode().getHeight() / scaleY);
    }

    private JPanel createContentPanel()
    {
        JPanel panel = new JPanel()
        {
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
                parallaxX = (int) (-24 / scaleFactor);
                parallaxY = (int) (-18 / scaleFactor);
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

    private void setImages()
    {
        try {
            int width = (int) (((InitialFrame.WIDTH) / scaleFactor) * 1.1);
            int height = (int) (((InitialFrame.HEIGHT) / scaleFactor) * 1.1);

            mainBackGround = ImageIO.read(
                    Objects.requireNonNull(
                            InitialFrame.class.getResource(
                                    "Resources/hello.png")));
            mainMiddleGround = ImageIO.read(
                    Objects.requireNonNull(
                            InitialFrame.class.getResource(
                                    "Resources/pabunotMiddleGround.png")));
            mainMiddleBackGround = ImageIO.read(
                    Objects.requireNonNull(
                            InitialFrame.class.getResource(
                                    "Resources/pabunotMiddleBackGround.png")));
            mainBackGround = mainBackGround.getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH
            );
            mainMiddleGround = mainMiddleGround.getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH);
            mainMiddleBackGround = mainMiddleBackGround.getScaledInstance(
                    width,
                    height,
                    Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Cursor createCursor()
    {
        Image i;
        BufferedImage img;
        try {
            i = ImageIO.read(
                     Objects.requireNonNull(
                             InitialFrame.class.getResource(
                                     "Resources/pabunotCursorNew.png")
                     )
                );
            i = i.getScaledInstance(100, 100, Image.SCALE_FAST);
            img = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = img.createGraphics();
            g2d.drawImage(i, 0, 0, null);
            g2d.dispose();

        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        return Toolkit.getDefaultToolkit().createCustomCursor(
                img,
                new Point(0, 0),
                "topLeftCursor"
        );
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
        label.setBounds(getWidth() - width - 20, 10, width + 200, height);
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
                    Desktop.getDesktop().browse(
                            new URI(
                                    "https://github.com/iid3rp/pabunot"
                            )
                    );
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
                parallaxMove(
                        new Point(
                                label.getX() + e.getX(),
                                label.getY() + e.getY()
                        )
                );
            }
        });
        return label;
    }

    public void addComponents()
    {
        if(SettingsPane.fpsCountVisibility) {
            contentPanel.add(framesPerSecond);
        }
        mainMenu.add(gitRepo);
        for(JLabel l : titleLabel)
        {
            mainMenu.add(l);
        }
        for(JLabel l : tipLabel)
        {
            contentPanel.add(l);
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

    /**
     * The run method in the InitialFrame class is
     * responsible for managing the game loop,
     * which includes updating the frame rate, handling animations,
     * and repainting the components.
     *
     * <p>Here's a breakdown of its functionality:</p>
     *
     * <ul>
     * <li><b>Frame Rate Management:</b> Calculates the time
     * passed between frames and adjusts
     * the frame rate accordingly. This is controlled by the
     * <code>tickPerSecond</code> which is
     * derived from the refresh rate of the display device.</li>
     *
     * <li><b>Animation Handling:</b> Includes a loop that checks if the
     * unprocessed seconds exceed the <code>tickPerSecond</code>.
     * If true, it updates the animations by calling <code>waveComponents</code>,
     * which likely animates certain UI components.</li>
     *
     * <li><b>Conditional Rendering:</b> The rendering of frames is conditional
     * based on whether the frame rate is locked (<code>fpsUnlocked</code>).
     * If unlocked, it continuously repaints the components to achieve smoother
     * animations.</li>
     *
     * <li><b>FPS Counter Update:</b> Updates an FPS counter label every
     * second if the frame rate is locked, providing feedback on
     * the performance.</li>
     *
     * <li><b>Exception Handling:</b> Includes a try-catch block to
     * handle any exceptions during the rendering process,
     * ensuring the game loop continues running smoothly.</li>
     * </ul>
     *
     * <p>This method effectively manages the timing and rendering
     * necessary for a game loop, ensuring that the game updates consistently
     * and responds to user interactions promptly.</p>
     *
     * @Citation:
     * <a href="https://www.youtube.com/watch?v=0zuVHDNYPQU&list=PL656DADE0DA25ADBB&index=2">
     *     [1] 3D Game Programming - Episode 2 - Game Loop | The Cherno on YouTube</a>
     * <a href="https://www.youtube.com/watch?v=VpH33Uw-_0E&list=PL_QPQmz5C6WUF-pOQDsbsKbaBZqXj4qSq&index=2">
     *     [2] Game Loop and Key Input - How to Make a 2D Game in Java #2 | RyiSnow on YouTube</a>
     * @Modifier: <a href="https://github.com/iid3rp">derp :3 | GitHub</a>
     */
    @Override
    public void run()
    {
        int frames = 0;
        double unprocessedSeconds = 0;
        long prevTime = System.currentTimeMillis();
        double tickPerSecond;
        int tickCount = 0;
        int seconds = 0;

        long lastSnowRenderTime = System.currentTimeMillis();
        double snowRenderInterval = 1d / 60; // 90 FPS for snow rendering

        while(isRunning) {
            tickPerSecond = ((1d / refreshRate));
            long currentTime = System.currentTimeMillis();
            long passedTime = currentTime - prevTime;
            prevTime = currentTime;
            unprocessedSeconds += (double) passedTime / 1_000;

            boolean snowRendered = false;

            while(unprocessedSeconds > tickPerSecond)
            {
                unprocessedSeconds -= tickPerSecond;
                tickCount++;

                if(tickCount % refreshRate == 0)
                {
                    fps = frames;
                    framesPerSecond.setText(
                            (
                                    fpsUnlocked ? "(Unlocked): " : "(locked): "
                            ) + fps + "fps"
                    );
                    frames = 0;
                    tickCount = 0;
                    tipUpdate(seconds);
                    seconds++;
                }
                if(!fpsUnlocked)
                {
                    try
                    {
                        // waving trail labels...
                        waveComponents(currentTime);

                        // rendering process
                        getContentPane().repaint();

                        frames++;
                    }
                    catch(Exception ignored) {}
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
                contentPanel.remove(l);
            }
            tipLabel = new TrailLabel(
                    Tip.stuff[
                            new Random().nextInt(Tip.stuff.length)
                        ],
                    20,
                    InitialFrame.HEIGHT - 50,
                    InitialFrame.HEIGHT - 40,
                    TrailLabel.rainbow
            );
            for(JLabel l : tipLabel)
            {
                contentPanel.add(l);
            }
        }
    }

    private void waveComponents(long currentTime)
    {
        if(SettingsPane.waveTrail)
        {
            titleLabel.wave(currentTime);
            tipLabel.wave(currentTime);
            titlePanel.title.wave(currentTime);
            titlePanel.titleLabel.wave(currentTime);
            if (createPabunot != null)
            {
                createPabunot.title.wave(currentTime);
            }
            if(section != null)
            {
                section.prizeLabel.wave(currentTime);
            }
            if(prizePicked != null)
            {
                prizePicked.infoLabel.wave(currentTime);
            }
            if(ended != null)
            {
                ended.infoLabel.wave(currentTime);
            }
            if(settingsPanel != null)
            {
                settingsPanel.title.wave(currentTime);
            }
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
                parallaxX = -24;
                parallaxY = -18;
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
        BufferedImage image = new BufferedImage(
                (int) (InitialFrame.WIDTH / scaleFactor),
                (int) (InitialFrame.HEIGHT / scaleFactor),
                BufferedImage.TYPE_INT_ARGB
        );
        Graphics2D g2d = image.createGraphics();
        g2d.drawImage(mainBackGround, parallaxX, parallaxY, null);
        if(SettingsPane.snowVisible)
        {
            g2d.drawImage(snow.particle, 0, 0,
                    InitialFrame.WIDTH, InitialFrame.HEIGHT, null
            );
        }
        g2d.drawImage(mainMiddleBackGround,
                (int) (((parallaxX * -1) - 24) / scaleFactor),
                (int) (((parallaxY * -1) - 18) / scaleFactor), null
        );
        g2d.drawImage(mainMiddleGround,
                (int) ((parallaxX * -1) - (48 / scaleFactor)),
                (int) ((parallaxY * -1) - (36 / scaleFactor)), null
        );
        g2d.setColor(new Color(0, 0, 0, 90));
        g2d.fillRect(0, 0,
                (int) (WIDTH / scaleFactor),
                (int) (HEIGHT / scaleFactor)
        );
        g2d.dispose();

        g.drawImage(image, 0, 0,
                InitialFrame.WIDTH, InitialFrame.HEIGHT, null
        );
    }

    public static void parallaxMove(Point e)
    {
        parallaxX = (int) ((int) (-((e.getX() * 48) / WIDTH)) / scaleFactor) * parallaxControl;
        parallaxY = (int) ((int) (-((e.getY() * 36) / HEIGHT)) / scaleFactor) * parallaxControl;

        snowMultiplierX = (int) (((((e.getX() / WIDTH) *
                ((InitialFrame.WIDTH / refreshRate / scaleFactor) * 2) -
                ((double) InitialFrame.WIDTH / refreshRate / scaleFactor)))) / 2);
        snowMultiplierY = (int) (((((e.getY() / HEIGHT) *
                ((InitialFrame.HEIGHT / refreshRate / scaleFactor) / 2) +
                (1 - (1 / scaleFactor))))) / 2);
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
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                if(section != null)
                {
                    PabunotWriter.writePabunot(section.pane);
                }
                PabunotWriter.writeInitialization();
            }
        });
    }

    public static double sineEase(
            double currentTime,
            double duration,
            double startY,
            double endY,
            int delaySine,
            int delayCosine
    ) {
        double easingSine = (Math.sin(
                currentTime * Math.PI * 2 / duration
        ) + 1) / scaleFactor;
        double easingCosine = (Math.cos(
                currentTime * Math.PI * 2 / duration
        ) + 1) / scaleFactor;
        double easing = ((easingSine * delaySine) +
                (easingCosine * delayCosine)) /
                (delaySine + delayCosine);
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
        label.setBounds((getWidth() / 2) - (width / 2),
                (int) (InitialFrame.HEIGHT * 0.50), width, height);
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
                InitialFrame.parallaxMove(
                        new Point(
                                label.getX() + e.getX(),
                                label.getY() + e.getY()
                        )
                );
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
        label.setBounds((getWidth() / 2) - (width / 2),
                (int) (InitialFrame.HEIGHT * 0.5) + 75,
                width, height
        );
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setSettings();
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
                InitialFrame.parallaxMove(
                        new Point(
                                label.getX() + e.getX(),
                                label.getY() + e.getY()
                        )
                );
            }
        });
        return label;
    }

    private void setSettings()
    {
        mainMenu.setVisible(false);
        settingsPanel.setVisible(true);
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
        label.setBounds((getWidth() / 2) - (width / 2),
                (int) (InitialFrame.HEIGHT * 0.5) + 150,
                width, height
        );
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.dispose();
                if(section != null)
                {
                    PabunotWriter.writePabunot(section.pane);
                }
                PabunotWriter.writeInitialization();
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
                InitialFrame.parallaxMove(
                        new Point(
                                label.getX() + e.getX(),
                                label.getY() + e.getY()
                        )
                );
            }
        });
        return label;
    }

    public void start()
    {
        Thread thread = new Thread(this);
        Thread snowThread = new Thread(snow);
        thread.start();
        snowThread.start();
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

    public void removeAllKeyListeners()
    {
        for(KeyListener listener : getKeyListeners())
        {
            removeKeyListener(listener);
        }
    }

    /**
     * This method is the entry point of the program.
     * It creates an instance of the InitialFrame class,
     * sets up the necessary directories, reads initialization data,
     * and starts the frame.
     * It uses SwingUtilities.invokeLater to ensure the GUI is
     * created on the Event Dispatch Thread (EDT).
     */
    public static void main(String[] a)
    {
        System.out.println("hello World!");
        try {
            SwingUtilities.invokeLater(() ->
            {
                @Intention var x = new File(PabunotMaker.pabunotDir).mkdirs();
                PabunotMaker.setNewInitialization();
                PabunotReader.readInitialization();
                InitialFrame frame = new InitialFrame();
                frame.start();
            });
        }
        catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}