package Pabunot.Interface;

import Pabunot.Graphics.TrailLabel;
import Pabunot.InitialFrame;
import Pabunot.Pabunot.Pabunot;
import Pabunot.Prize.Prize;
import Pabunot.Prize.PrizeListPane;
import Pabunot.Utils.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.Objects;

import static Pabunot.Utils.AndyBold.createFont;
import static java.awt.Color.white;

public class PabunotMakingPane extends JPanel implements Runnable
{
    private int paperLength;
    @Intention InitialFrame frame;
    private final JTextField xField;
    private final JTextField yField;
    private final JLabel yLabel;
    private final JLabel xLabel;
    public TrailLabel title;
    public JTextField prizeTitle;
    public JTextArea prizeDescription;
    public Pabunot theme;
    public JLabel matrix;
    private JLabel letterLimitDesc;
    private JLabel letterLimitField;
    public int x = 10;
    public int y = 10;
    public String other;
    public JLabel addPrize;
    PrizeListPane pane;
    public JLabel startPabunot;

    public JLabel addX;
    public JLabel addY;
    public JLabel decreaseX;
    public JLabel decreaseY;
    private boolean startProcess;

    public PabunotMakingPane(InitialFrame frame, String titleString)
    {
        super();
        this.frame = frame;
        initializeComponent();
        title = new TrailLabel(titleString, 40, 20, 20, 25, TrailLabel.rainbow);
        prizeTitle = createPrize();
        prizeDescription = createDescription();
        letterLimitDesc = createLetterLimit();
        letterLimitField = createLetterLimitField();
        addPrize = createAddPrize();
        matrix = createMatrixPanel(Theme.GRAY_HEARTS);
        pane = new PrizeListPane(frame);
        pane.setLocation(20, 350);
        startPabunot = createStart();
        xField = createXField();
        yField = createYField();
        yLabel = createYLabel();
        xLabel = createXLabel();
        addX = createAddX();
        addY = createAddY();
        decreaseX = createDecreaseX();
        decreaseY = createDecreaseY();

        startProcess = true;


        for(JLabel l : title)
        {
            add(l);
        }

        add(prizeTitle);
        add(matrix);
        add(prizeDescription);
        add(letterLimitDesc);
        add(letterLimitField);
        add(addPrize);
        add(xField);
        add(yField);
        add(xLabel);
        add(yLabel);
        add(addX);
        add(addY);
        add(decreaseX);
        add(decreaseY);
        add(createDimensionLabel());
        add(pane);
        add(startPabunot);


        add(createPrizesLabel());
        start();
    }

    private void start()
    {
        Thread thread = new Thread(this);
        thread.start();
    }

    private JLabel createAddX()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("+");
        label.setFont(AndyBold.createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(430, 10, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                x = x + 1 > 30 ? x : x + 1;
                matrix.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(white);
            }
        });
        return label;
    }
    private JLabel createAddY()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("+");
        label.setFont(AndyBold.createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(430, 30, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                y = y + 1 > 30? y : y + 1;
                matrix.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(white);
            }
        });
        return label;
    }

    private JLabel createDecreaseX()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("-");
        label.setFont(AndyBold.createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(400, 10, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                x = x - 1 < 5 ? x : x - 1;
                matrix.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(white);
            }
        });
        return label;
    }
    private JLabel createDecreaseY()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("-");
        label.setFont(AndyBold.createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(400, 30, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                y = y - 1 < 5? y : y - 1;
                matrix.repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.orange);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(white);
            }
        });
        return label;
    }

    private JLabel createDimensionLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Pabunot Matrix Preview");
        label.setFont(createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(600, 10, width, height);
        return label;
    }

    private JLabel createXLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Width amount:");
        label.setFont(createFont(20));
        label.setForeground(new Color(0, 0,0, 127));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(400, 60, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JLabel createStart()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Start Pabunot");
        label.setFont(createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(20, 720 - height - 10, width, height);
        return label;
    }

    private JLabel createYLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Height Amount:");
        label.setFont(createFont(20));
        label.setForeground(new Color(0, 0,0, 127));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(400, 130, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JTextField createXField()
    {
        JTextField field = new JTextField();
        field.setLayout(null);
        field.setFont(createFont(30));
        field.setBackground(new Color(0, 0, 0, 125));
        field.setBounds(500, 100, 50, 20);
        field.setText("10");
        field.setForeground(new Color(255, 255, 255, 127));
        PlainDocument doc = (PlainDocument) field.getDocument();
        doc.setDocumentFilter(new TextFilter(DataType.TYPE_STRING, 25));

        field.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent ignored)
            {
                if(field.getText().equals("Enter prize title:"))
                {
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if(field.getText().isEmpty())
                {
                    field.setText("Enter prize title:");
                    field.setForeground(new Color(255, 255, 255, 127));
                }
            }
        });
        return field;
    }

    private JTextField createYField()
    {
        JTextField field = new JTextField();
        field.setLayout(null);
        field.setFont(createFont(30));
        field.setBackground(new Color(0, 0, 0, 125));
        field.setBounds(500, 130, 50, 20);
        field.setText("10");
        field.setForeground(new Color(255, 255, 255, 127));
        PlainDocument doc = (PlainDocument) field.getDocument();
        doc.setDocumentFilter(new TextFilter(DataType.TYPE_STRING, 25));

        field.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(field.getText().equals("Enter prize title:"))
                {
                    field.setText("");
                }
            }
        });

        field.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent ignored)
            {
                if(field.getText().equals("Enter prize title:"))
                {
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if(field.getText().trim().isEmpty())
                {
                    field.setText("Enter prize title:");
                    field.setForeground(new Color(255, 255, 255, 127));
                    letterLimitField.setText("0 / 25");

                }
            }
        });

        field.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                frame.parallaxMove(new Point(field.getX() + e.getX(),  field.getY() + e.getY()));
            }
        });
        return field;
    }

    private JLabel createAddPrize()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Add Prize");
        label.setFont(createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(20, 300, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(!letterLimitField.getText().equals("0 / 25"))
                {
                    String s = letterLimitDesc.getText().equals("0 / 75")? "no description added..." : prizeDescription.getText();
                    pane.list.addFirst(new Prize(prizeTitle.getText(), s));
                    pane.restore();

                    prizeTitle.setText("");
                    prizeDescription.setText("");

                    prizeTitle.requestFocus();
                    prizeDescription.requestFocus();
                    frame.requestFocus();
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                if(!letterLimitField.getText().equals("0 / 25"))
                {
                    label.setForeground(Color.orange);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(white);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                frame.parallaxMove(new Point(label.getX() + e.getX(),  label.getY() + e.getY()));
            }
        });
        return label;
    }

    private JLabel createLetterLimitField()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("0 / 25");
        label.setFont(createFont(20));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(245, 165, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JLabel createLetterLimit()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("0 / 75");
        label.setFont(createFont(20));
        label.setForeground(white);
        label.setDoubleBuffered(true);
        label.setOpaque(false);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(245, 255, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JLabel createPrizesLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Prize Section");
        label.setFont(createFont(20));
        label.setForeground(white);
        label.setDoubleBuffered(true);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(20, 110, width, height);
        return label;
    }

    private JTextArea createDescription()
    {
        JTextArea area = new JTextArea();
        area.setDoubleBuffered(true);
        area.setLayout(null);
        area.setFont(createFont(20));
        area.setText("Enter description here:");
        area.setBounds(20, 200, 300, 80);
        area.setSelectionColor(new Color(243, 164, 104));
        area.setForeground(new Color(255, 255, 255, 127));
        area.setBorder(new LineBorder(white));
        area.setLineWrap(true);
        area.setDoubleBuffered(true);
        area.setOpaque(false);
        area.setTabSize(2);
        area.setBackground(new Color(255, 128, 3, 100));
        PlainDocument doc = (PlainDocument) area.getDocument();
        doc.setDocumentFilter(new TextFilter(DataType.TYPE_STRING, 75));
        doc.addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                int chars = area.getText().isEmpty() ? 0 : area.getText().length();
                letterLimitDesc.setText(chars + " / 75");
                area.setForeground(white);

            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                int chars = area.getText().isEmpty() ? 0 : area.getText().length();
                letterLimitDesc.setText(chars + " / 75");
                area.setForeground(white);
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                int chars = area.getText().isEmpty() ? 0 : area.getText().length();
                letterLimitDesc.setText(chars + " / 75");
                area.setForeground(white);
            }
        });

        area.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(area.getText().equals("Enter description here:"))
                {
                    area.setText("");
                }
            }
        });

        area.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                frame.parallaxMove(new Point(area.getX() + e.getX(),  area.getY() + e.getY()));
            }
        });

        area.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                if(area.getText().equals("Enter description here:"))
                {
                    area.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent ignored)
            {
                if(area.getText().trim().isEmpty())
                {
                    area.setText("Enter description here:");
                    letterLimitDesc.setText("0 / 75");
                    area.setForeground(new Color(255, 255, 255, 127));
                }
            }
        });
        return area;
    }

    public JLabel createMatrixPanel(Theme theme)
    {
        final int[] width = new int[1];
        final int[] height = new int[1];
        final int metric = 500;
        paperLength = (metric / Math.max(x, y));
        JLabel label = new JLabel()
        {
            Image image;
            {
                try {
                    image = ImageIO.read(Objects.requireNonNull(Pabunot.selectTheme(theme)));
                    image = image.getScaledInstance(paperLength, paperLength, Image.SCALE_SMOOTH);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                paperLength = metric / Math.max(x, y);
                width[0] = paperLength * x;
                height[0] = paperLength * y;
                setSize(width[0], height[0]);
                Image io = image.getScaledInstance(paperLength, paperLength, Image.SCALE_FAST);
                g.setColor(new Color(0, 0, 0, 127));
                g.fillRect(0, 0, metric, metric);
                for(int i = 0; i < x; i++)
                {
                    for(int j = 0; j < y; j++)
                    {
                        g.drawImage(io, paperLength * i, paperLength * j, null);
                    }
                }
            }
        };
        label.setLayout(null);

        width[0] = paperLength * x;
        height[0] = paperLength * y;

        label.setLayout(null);
        label.setDoubleBuffered(true);
        label.setSize(metric, metric);
        label.setLocation(600, 60);
        label.setOpaque(false);
        return label;
    }

    @SuppressWarnings("other stuff goes here lol")
    private JLabel createPabunotReference(int paperLength, Theme theme)
    {
        JLabel label = new JLabel()
        {
            Image i;
            {
                try {
                    i = ImageIO.read(Objects.requireNonNull(Pabunot.selectTheme(theme)));
                    i = i.getScaledInstance(paperLength, paperLength, Image.SCALE_SMOOTH);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(i, 0, 0, null);
            }
        };
        label.setLayout(null);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(0, 0, paperLength, paperLength);
        return label;
    }

    @Override
    public void run()
    {

    }

    private JTextField createPrize()
    {

        JTextField field = new JTextField();
        field.setLayout(null);
        field.setFont(createFont(30));
        field.setBounds(20, 140, 300, 50);
        field.setDoubleBuffered(true);
        field.setText("Enter prize title:");
        field.setForeground(new Color(255, 255, 255, 127));
        field.setOpaque(false);
        PlainDocument doc = (PlainDocument) field.getDocument();
        doc.setDocumentFilter(new TextFilter(DataType.TYPE_STRING, 25));
        doc.addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                int chars = field.getText().isEmpty() ? 0 : field.getText().length();
                letterLimitField.setText(chars + " / 25");
                field.setForeground(white);
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                int chars = field.getText().isEmpty() ? 0 : field.getText().length();
                letterLimitField.setText(chars + " / 25");
                field.setForeground(white);
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                int chars = field.getText().isEmpty() ? 0 : field.getText().length();
                letterLimitField.setText(chars + " / 25");
                field.setForeground(white);
            }
        });

        field.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(field.getText().equals("Enter prize title:"))
                {
                    field.setText("");
                }
            }
        });
        field.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                frame.parallaxMove(new Point(field.getX() + e.getX(),  field.getY() + e.getY()));
            }
        });

        field.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent ignored)
            {
                if(field.getText().equals("Enter prize title:"))
                {
                    field.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                if(field.getText().trim().isEmpty())
                {
                    field.setText("Enter prize title:");
                    field.setForeground(new Color(255, 255, 255, 127));
                    letterLimitField.setText("0 / 25");
                }
            }
        });

        return field;
    }

    private void initializeComponent()
    {
        try
        {
            i = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/hello.png")));
            i = i.getScaledInstance(1328, 756, Image.SCALE_SMOOTH);
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }

        setSize(new Dimension(1280,720));
        setOpaque(false);
        setDoubleBuffered(true);
        setLayout(null);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                frame.requestFocus();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    frame.isDragging = true;
                    frame.offset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    frame.isDragging = false;
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                frame.x = -24;
                frame.y = -18;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if(frame.isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - frame.offset.x;
                    int deltaY = currentMouse.y - frame.offset.y;

                    frame.setLocation(deltaX, deltaY);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                frame.parallaxMove(e.getPoint());
            }
        });
    }


    public Image i;

    @Override
    public void paintComponent(Graphics g)
    {
        g.drawImage(i, frame.x, frame.y, null);
        g.setColor(new Color(0, 0, 0, 120));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(frame.snow, 0, 0, null);
    }

}