package Pabunot.Interface;

import Pabunot.Graphics.TrailLabel;
import Pabunot.InitialFrame;
import Pabunot.Pabunot.Pabunot;
import Pabunot.Prize.Prize;
import Pabunot.Prize.PrizeListPane;
import Pabunot.Utils.AndyBold;
import Pabunot.Utils.DataType;
import Pabunot.Utils.Intention;
import Pabunot.Utils.TextFilter;

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

public class PabunotMakingPane extends JPanel
{
    @Intention InitialFrame frame;
    public TrailLabel title;
    public JTextField prizeTitle;
    public JTextArea prizeDescription;
    public Pabunot theme;
    public JPanel matrix;
    private JLabel letterLimitDesc;
    private JLabel letterLimitField;
    public int x;
    public int y;
    public String other;
    public JLabel addPrize;
    PrizeListPane pane;
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
        matrix = createMatrixPanel();
        pane = new PrizeListPane(frame);
        pane.setLocation(20, 350);

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
        add(pane);



        add(createPrizesLabel());
        var x1 = createXField();
    }

    private JLabel createXScrollbar()
    {
        final int[] x = {0};
        JLabel label = new JLabel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.setColor(new Color(235, 123, 235, 80));
                g.fillRect(0, 0, 200, 300);
                g.fillRect(0, 0, x[0], 300);
            }
        };
        label.setLayout(null);
        label.setText("Width: ");
        label.setFont(AndyBold.createFont(20));
        label.setBounds(255, 275, 200, 40);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                x[0] = e.getX();
                label.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {

                x[0] = e.getX();
                label.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {

                x[0] = e.getX();
                label.repaint();
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                x[0] = e.getX();
                label.repaint();
            }
        });
        return label;
    }

    private JLabel createYScrollbar()
    {
        final int[] x = {0};
        JLabel label = new JLabel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.setColor(new Color(235, 123, 235, 80));
                g.fillRect(0, 0, 200, 300);
                g.fillRect(0, 0, x[0], 300);
            }
        };
        label.setLayout(null);
        label.setText("Width: ");
        label.setFont(AndyBold.createFont(20));
        label.setBounds(255, 275, 200, 40);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {

                x[0] = e.getX();
                label.repaint();
            }

            @Override
            public void mousePressed(MouseEvent e)
            {

                x[0] = e.getX();
                label.repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {

                x[0] = e.getX();
                label.repaint();
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                x[0] = e.getX();
                label.repaint();
            }
        });
        return label;
    }

    private JLabel createXLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Width: ");
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(0, 0,0, 127));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(245, 255, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JLabel createYLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Height: ");
        label.setFont(AndyBold.createFont(20));
        label.setForeground(new Color(0, 0,0, 127));
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(245, 255, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JTextField createXField()
    {
        JTextField field = new JTextField();
        field.setLayout(null);
        field.setFont(AndyBold.createFont(30));
        field.setBackground(new Color(0, 0, 0, 125));
        field.setBounds(20, 140, 300, 50);
        field.setText("Enter prize title:");
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
        field.setFont(AndyBold.createFont(30));
        field.setBackground(new Color(0, 0, 0, 125));
        field.setBounds(20, 140, 300, 50);
        field.setText("Enter prize title:");
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
                System.out.println("\"" + x + "\"");
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
        label.setFont(AndyBold.createFont(40));
        label.setForeground(Color.black);
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
                    pane.list.add(new Prize(prizeTitle.getText(), prizeDescription.getText()));
                    pane.restore();

                    prizeTitle.setText("");
                    prizeDescription.setText("");

                    prizeTitle.requestFocus();
                    prizeTitle.requestFocus();
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
                label.setForeground(Color.BLACK);
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
        label.setFont(AndyBold.createFont(20));
        label.setForeground(Color.white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth("100 / 100");
        int height = metrics.getHeight();
        label.setBounds(245, 115, width, height);
        label.setHorizontalAlignment(SwingConstants.RIGHT);
        return label;
    }

    private JLabel createLetterLimit()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("0 / 75");
        label.setFont(AndyBold.createFont(20));
        label.setForeground(Color.white);
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
        label.setText("Add prize");
        label.setFont(AndyBold.createFont(20));
        label.setForeground(Color.white);
        label.setDoubleBuffered(true);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(20, 110, width, height);
        return label;
    }

    private JTextArea createDescription()
    {
        JTextArea area = new JTextArea();
        area.setDoubleBuffered(true);
        area.setLayout(null);
        area.setFont(AndyBold.createFont(20));
        area.setText("Enter description here:");
        area.setBounds(20, 200, 300, 80);
        area.setSelectionColor(new Color(243, 164, 104));
        area.setForeground(Color.white);
        area.setBorder(new LineBorder(Color.white));
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
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                int chars = area.getText().isEmpty() ? 0 : area.getText().length();
                letterLimitDesc.setText(chars + " / 75");
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                int chars = area.getText().isEmpty() ? 0 : area.getText().length();
                letterLimitDesc.setText(chars + " / 75");
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
                }
            }
        });
        return area;
    }

    public JPanel createMatrixPanel()
    {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setSize(400, 400);
        panel.setLocation(500, 200);
        panel.setBackground(new Color(0, 0, 0, 100));
        panel.setDoubleBuffered(true);
        return panel;
    }

    private JTextField createPrize()
    {

        JTextField field = new JTextField();
        field.setLayout(null);
        field.setFont(AndyBold.createFont(30));
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
                field.setForeground(Color.white);
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                int chars = field.getText().isEmpty() ? 0 : field.getText().length();
                letterLimitField.setText(chars + " / 25");
                field.setForeground(Color.white);
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                int chars = field.getText().isEmpty() ? 0 : field.getText().length();
                letterLimitField.setText(chars + " / 25");
                field.setForeground(Color.white);
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
                x = -24;
                y = -18;
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