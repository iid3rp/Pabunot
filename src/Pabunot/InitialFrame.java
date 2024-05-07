package Pabunot;

import Pabunot.Graphics.PabunotTitle;
import Pabunot.Graphics.Snow;
import Pabunot.Graphics.TrailLabel;
import Pabunot.Interface.PabunotMakingPane;
import Pabunot.Pabunot.PabunotGrid;
import Pabunot.Pabunot.PabunotGridPane;
import Pabunot.Utils.AndyBold;
import Pabunot.Utils.Intention;
import Pabunot.Utils.Tip;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Random;

public class InitialFrame extends JFrame implements Runnable
{
    public static double snowMultiplierX;
    public static double snowMultiplierY;
    @Intention InitialFrame frame = this;
    public static GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    public static GraphicsDevice gd = ge.getDefaultScreenDevice();
    public static DisplayMode dm = gd.getDisplayMode();
    public static int refreshRate = dm.getRefreshRate();
    Snow snow;
    private JLabel pabunotTitle;
    public int reference;
    public static boolean isRunning = true;
    public JPanel panel;
    public boolean isDragging;
    public Point offset;
    PabunotGridPane bunot;

    public JPanel glassPane;
    public int fps;
    private JLabel framesPerSecond;
    private PabunotMakingPane createPabunot;

    int x = 0;
    int y = 0;

    private JLabel start;
    private JLabel settings;
    private JLabel exit;
    private JLabel closeApplication;

    private JTextArea area;
    private boolean fpsUnlocked = false;

    public InitialFrame()
    {
        super();
        PabunotTitle.setFonts();
        initializeComponent();
        snow = new Snow();
        panel = createPanel();
        glassPane = createGlassPane();
        pabunotTitle = createTitle();
        start = createStart();
        createPabunot = new PabunotMakingPane(frame, "BSIT-BTM Pabunot");

        settings = createSettings();
        exit = createExit();
        closeApplication = createClose();
        framesPerSecond = createFPS();

        area = createTextArea();

        add(panel);
        add(glassPane);
        // setContentPane(panel);
        setContentPane(createPabunot);
        setGlassPane(glassPane);
        // panel.add(area);

        glassPane.setVisible(true);

        bunot = new PabunotGridPane(this, new PabunotGrid(15, 10, "Hello World!", 12345,
                Objects.requireNonNull(InitialFrame.class.getResource("Resources/pink.png")).getPath()));
        addComponents();
    }

    private JTextArea createTextArea()
    {
        JTextArea area = new JTextArea();
        area.setLayout(null);
        area.setBackground(new Color(0, 0, 0, 0));
        area.setForeground(Color.white);
        area.setBounds(200, 200, 500, 200);
        area.setFont(AndyBold.createFont(20));
        area.setDoubleBuffered(true);
        return area;
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

    private JLabel createClose()
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
                panel.add(bunot);
                panel.remove(start);
                panel.remove(settings);
                panel.remove(exit);
                panel.remove(PabunotTitle.p);
                panel.remove(PabunotTitle.a);
                panel.remove(PabunotTitle.b);
                panel.remove(PabunotTitle.u);
                panel.remove(PabunotTitle.n);
                panel.remove(PabunotTitle.o);
                panel.remove(PabunotTitle.t);
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
                parallaxMove(new Point(label.getX() + e.getX(), label.getY() + e.getY()));
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
                parallaxMove(new Point(label.getX() + e.getX(), label.getY() + e.getY()));
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
                dispose();
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
                 parallaxMove(new Point(label.getX() + e.getX(), label.getY() + e.getY()));
             }
        });
        return label;
    }

    public Color[] colors =
    {
        new Color(80, 255, 80)
    };

    TrailLabel labels = new TrailLabel(">>><<<EED    le fiche    >>><<<EED", 20, 680, 690, Tip.colors);
    TrailLabel labels2 = new TrailLabel("Francis L. Madanlo", 20, 90, 100, new Color[]{Color.WHITE, Color.WHITE});
    public void addComponents()
    {
        panel.add(PabunotTitle.p);
        panel.add(PabunotTitle.a);
        panel.add(PabunotTitle.b);
        panel.add(PabunotTitle.u);
        panel.add(PabunotTitle.n);
        panel.add(PabunotTitle.o);
        panel.add(PabunotTitle.t);
        panel.add(start);
        panel.add(settings);
        panel.add(exit);
        panel.add(closeApplication);
        panel.add(framesPerSecond);

        for(JLabel l : labels)
        {
            panel.add(l);
        }
        //panel.add(bunot);
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

    private JLabel createTitle()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Pabunot");
        label.setForeground(Color.white);
        label.setFont(AndyBold.createFont(150));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds((getWidth() / 2) - (width / 2), 100, width, height);
        return label;
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
        while (isRunning)

        {
            while(isRunning)
            {
                long currentTime = System.nanoTime();
                long passedTime = currentTime - prevTime;
                prevTime = currentTime;
                unprocessedSeconds += passedTime / 1_000_000_000.0;

                while(unprocessedSeconds > secondsTick)
                {
                    unprocessedSeconds -= secondsTick;
                    tickCount++;

                    if(tickCount % refreshRate == 0)
                    {
                        fps = frames;
                        framesPerSecond.setText("FPS Counter " + (fpsUnlocked ? "(Unlocked): " : "(locked):") + fps);
                        prevTime += 1000;
                        frames = 0;
                        tickCount = 0;
                        if(seconds % 6 == 0)
                        {
                            for(JLabel l : labels)
                            {
                                panel.remove(l);
                            }
                            labels = new TrailLabel(Tip.stuff[new Random().nextInt(Tip.stuff.length)], 20, 680, 690, Tip.colors);
                            for(JLabel l : labels)
                            {
                                panel.add(l);
                            }
                        }
                        seconds++;
                    }
                    snow.render(currentTime);
                    if(!fpsUnlocked)
                    {
                        try {
                            // the wave thingies
                            moveTrail(currentTime);
                            labels.wave(currentTime);
                            labels2.wave(currentTime);

                            createPabunot.title.wave(currentTime);
                            panel.repaint();


                            renderOtherComponents();
                            frames++;
                        }
                        catch(Exception ignored) {}
                    }
                }

                if(fpsUnlocked)
                {
                    // the wave thingies
                    moveTrail(currentTime);
                    labels.wave(currentTime);
                    labels2.wave(currentTime);
                    createPabunot.title.wave(currentTime);
                    panel.repaint();
                    frames++;
                }
            }
        }
    }

    private void renderOtherComponents() {}

    private void moveTrail(long currentTime)
    {
        PabunotTitle.p.setLocation(PabunotTitle.p.getX(), (int) sineEaseY(currentTime, 1_000_000_000, 100, 115, 6, 0));
        PabunotTitle.a.setLocation(PabunotTitle.a.getX(), (int) sineEaseY(currentTime, 1_000_000_000, 100, 115, 5, 1));
        PabunotTitle.b.setLocation(PabunotTitle.b.getX(), (int) sineEaseY(currentTime, 1_000_000_000, 100, 115, 4, 2));
        PabunotTitle.u.setLocation(PabunotTitle.u.getX(), (int) sineEaseY(currentTime, 1_000_000_000, 100, 115, 3, 3));
        PabunotTitle.n.setLocation(PabunotTitle.n.getX(), (int) sineEaseY(currentTime, 1_000_000_000, 100, 115, 2, 4));
        PabunotTitle.o.setLocation(PabunotTitle.o.getX(), (int) sineEaseY(currentTime, 1_000_000_000, 100, 115, 1, 5));
        PabunotTitle.t.setLocation(PabunotTitle.t.getX(), (int) sineEaseY(currentTime, 1_000_000_000, 100, 115, 0, 6));
    }

    private JPanel createPanel()
    {
        JPanel panel = new JPanel()
        {
            Image i;
            {
                try {
                    i = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/hello.png")));
                    i = i.getScaledInstance(1328, 756, Image.SCALE_SMOOTH);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void paintComponent(Graphics g)
            {
                g.drawImage(i, x, y, null);
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

    public void parallaxMove(Point e)
    {
        x = (int) -((e.getX() * 48) / getWidth());
        y = (int) -((e.getY() * 36) / getHeight());

        snowMultiplierX = (int) ((e.getX() / getWidth()) * 4);
        snowMultiplierY = (int) ((e.getY() / getHeight()) * 2);
    }

    private void initializeComponent()
    {
        setSize(new Dimension(1280, 720));
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

    public void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    public static void main(String[] a)
    {
        InitialFrame frame = new InitialFrame();
        frame.start();
    }
}