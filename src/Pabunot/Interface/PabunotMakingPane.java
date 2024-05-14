package Pabunot.Interface;

import Pabunot.Graphics.TrailLabel;
import Pabunot.InitialFrame;
import Pabunot.Pabunot.Pabunot;
import Pabunot.Pabunot.PabunotGrid;
import Pabunot.Prize.Prize;
import Pabunot.Prize.PrizeListPane;
import Pabunot.StreamIO.PabunotMaker;
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
import static java.awt.Color.*;

public class PabunotMakingPane extends JPanel
{
    private int paperLength;
    @Intention InitialFrame frame;
    @Intention JPanel panel = this;
    private final JTextField xField;
    private final JTextField yField;
    private final JLabel yLabel;
    private final JLabel xLabel;
    public TrailLabel title;
    public JTextField prizeTitle;
    public JTextArea prizeDescription;
    public Theme theme;
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
    private JCheckBox sameRatio;
    private boolean startProcess;
    private Image pabunotThemeImage;
    private JPanel matrixPanel;
    private JComboBox<String> themeCombo;
    private String pabunotTitle;

    public PabunotMakingPane(InitialFrame frame, String titleString)
    {
        super();
        this.frame = frame;
        theme = Theme.RED_HEARTS;
        initializeComponent();
        title = new TrailLabel(titleString, 40, 20, 20, 25, TrailLabel.rainbow);

        matrixPanel = createMatrixPanel();
        prizeTitle = createPrize();
        prizeDescription = createDescription();
        letterLimitDesc = createLetterLimit();
        letterLimitField = createLetterLimitField();
        addPrize = createAddPrize();
        matrix = createMatrixLabel();
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
        sameRatio = createSameRatio();
        themeCombo = createThemeComboBox();

        startProcess = true;


        for(JLabel l : title)
        {
            add(l);
        }

        addComponents();
    }

    private void addComponents()
    {
        add(prizeTitle);
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
        add(themeCombo);
        add(pane);
        add(startPabunot);
        add(sameRatio);
        add(matrixPanel);


        add(createNormalLabel("Prize Section", 20, 110));
        add(createNormalLabel("Dimension", 400, 110));
        add(createNormalLabel("Themes", 400, 320));

        matrixPanel.add(matrix);
        matrixPanel.add(createDimensionLabel());
    }

    private JPanel createMatrixPanel()
    {
        JPanel panel = new JPanel();
        panel.setSize(new Dimension(600, 600));
        panel.setBackground(new Color(0, 0, 0, 125));
        panel.setDoubleBuffered(true);
        panel.setLayout(null);
        panel.setLocation(600, (getHeight() - 600) / 2);
        panel.setVisible(true);
        return panel;
    }

    /**
     * The createSameRatio method creates a checkbox component with the label "Apply Both" and some visual styling.
     * @return
     */
    private JCheckBox createSameRatio()
    {
        JCheckBox check = new JCheckBox();
        check.setLayout(null);
        check.setText("Apply Both");
        check.setFont(createFont(20));
        check.setOpaque(false);
        check.setForeground(white);
        FontMetrics metrics = check.getFontMetrics(check.getFont());
        int width = metrics.stringWidth(check.getText());
        int height = metrics.getHeight();
        check.setBounds(400, 230, width + 40, height);
        check.addMouseMotionListener(new MouseMotionAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent e)
        {
            InitialFrame.parallaxMove(new Point(check.getX() + e.getX(),  check.getY() + e.getY()));
        }
    });
        return check;
    }

    private JLabel createAddX()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("+");
        label.setFont(createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(560, 130, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                x = x + 1 > 30 ? x : x + 1;
                y = sameRatio.isSelected() && y + 1 <= 30 ? y + 1 : y;
                xField.setText("" + x);
                yField.setText("" + y);
                matrix.repaint();
                matrix.revalidate();
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
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),  label.getY() + e.getY()));
            }
        });
        return label;
    }
    private JLabel createAddY()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("+");
        label.setFont(createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(560, 180, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                x = sameRatio.isSelected() && x + 1 <= 30 ? x + 1 : x;
                y = y + 1 > 30? y : y + 1;
                xField.setText("" + x);
                yField.setText("" + y);
                matrix.repaint();
                matrix.revalidate();
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
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),  label.getY() + e.getY()));
            }
        });
        return label;
    }

    private JLabel createDecreaseX()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("-");
        label.setFont(createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(470, 130, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                x = x - 1 < 5 ? x : x - 1;
                y = sameRatio.isSelected() && y - 1 >= 5 ? y - 1 : y;
                xField.setText("" + x);
                yField.setText("" + y);
                matrix.repaint();
                matrix.revalidate();
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
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),  label.getY() + e.getY()));
            }
        });
        return label;
    }
    private JLabel createDecreaseY()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("-");
        label.setFont(createFont(40));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();
        label.setBounds(470, 180, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                x = sameRatio.isSelected() && x - 1 >= 5 ? x - 1 : x;
                y = y - 1 < 5 ? y : y - 1;
                xField.setText("" + x);
                yField.setText("" + y);
                matrix.repaint();
                matrix.revalidate();
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
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),  label.getY() + e.getY()));
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
        label.setBounds((matrixPanel.getWidth() - width) / 2, 10, width, height);
        return label;
    }

    private JLabel createXLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Width");
        label.setFont(createFont(25));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(400, 145, width, height);
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
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                PabunotGrid grid = new PabunotGrid(x, y, pabunotTitle, theme, pane.list);
                PabunotMaker pm = new PabunotMaker(grid);
                System.out.println(pm);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(yellow);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(white);
            }
        });
        return label;
    }

    private JLabel createYLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Height");
        label.setFont(createFont(25));
        label.setForeground(white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(400, 195, width, height);
        return label;
    }

    private JTextField createXField()
    {
        JTextField field = new JTextField();
        field.setLayout(null);
        field.setFont(createFont(30));
        field.setBackground(new Color(0, 0, 0, 125));
        field.setBounds(500, 140, 50, 30);
        field.setText("10");
        field.setOpaque(false);
        field.setForeground(new Color(255, 255, 255, 127));
        PlainDocument doc = (PlainDocument) field.getDocument();
        doc.setDocumentFilter(new TextFilter(DataType.TYPE_NUMERICAL, 2));
        doc.addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                if(!field.getText().isEmpty())
                {
                    try {
                        int n = Integer.parseInt(field.getText());
                        if(n <= 30)
                        {
                            if(n >= 5)
                            {
                                x = Integer.parseInt(field.getText());
                                field.setForeground(white);
                            }
                        }
                        else
                        {
                            x = 30;
                            field.setForeground(Color.red);
                        }
                    }
                    catch(NumberFormatException ignored) {}
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                if(!field.getText().isEmpty())
                {
                    try {
                        int n = Integer.parseInt(field.getText());
                        if(n <= 30)
                        {
                            if(n >= 5)
                            {
                                x = Integer.parseInt(field.getText());
                                field.setForeground(white);
                            }
                        }
                        else
                        {
                            x = 30;
                            field.setForeground(Color.red);
                        }
                    }
                    catch(NumberFormatException ignored) {}
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                if(!field.getText().isEmpty())
                {
                    try {
                        int n = Integer.parseInt(field.getText());
                        if(n <= 30)
                        {
                            if(n >= 5)
                            {
                                x = Integer.parseInt(field.getText());
                                field.setForeground(white);
                            }
                        }
                        else
                        {
                            x = 30;
                            field.setForeground(Color.red);
                        }
                    }
                    catch(NumberFormatException ignored) {}
                }
            }
        });
        field.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),  field.getY() + e.getY()));
            }
        });

        field.addFocusListener(new FocusListener()
        {
            @Override
            public void focusGained(FocusEvent ignored)
            {
                field.selectAll();
            }

            @Override
            public void focusLost(FocusEvent e)
            {
                field.setText("" + x);
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
        field.setBounds(500, 190, 50, 30);
        field.setText("10");
        field.setOpaque(false);
        field.setForeground(new Color(255, 255, 255, 127));
        PlainDocument doc = (PlainDocument) field.getDocument();
        doc.setDocumentFilter(new TextFilter(DataType.TYPE_STRING, 25));
        doc.addDocumentListener(new DocumentListener()
        {
            @Override
            public void insertUpdate(DocumentEvent e)
            {
                if(!field.getText().isEmpty())
                {
                    try
                    {
                        int n = Integer.parseInt(field.getText());
                        if(n <= 30)
                        {
                            if(n >= 5)
                            {
                                y = Integer.parseInt(field.getText());
                                field.setForeground(white);
                            }
                        }
                        else
                        {
                            y = 30;
                            field.setForeground(Color.red);
                        }
                    }
                    catch(NumberFormatException ignored) {
                    }
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                if(!field.getText().isEmpty())
                {
                    try
                    {
                        int n = Integer.parseInt(field.getText());
                        if(n <= 30)
                        {
                            if(n >= 5)
                            {
                                y = Integer.parseInt(field.getText());
                                field.setForeground(white);
                            }
                        }
                        else
                        {
                            y = 30;
                            field.setForeground(Color.red);
                        }
                    }
                    catch(NumberFormatException ignored) {
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                if(!field.getText().isEmpty())
                {
                    try
                    {
                        int n = Integer.parseInt(field.getText());
                        if(n <= 30)
                        {
                            if(n >= 5)
                            {
                                y = Integer.parseInt(field.getText());
                                field.setForeground(white);
                            }
                        }
                        else
                        {
                            y = 30;
                            field.setForeground(Color.red);
                        }
                    }
                    catch(NumberFormatException ignored) {
                    }
                }
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
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),  field.getY() + e.getY()));
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
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),  field.getY() + e.getY()));
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
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),  label.getY() + e.getY()));
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

    private JLabel createNormalLabel(String s, int x, int y)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s);
        label.setFont(createFont(20));
        label.setForeground(white);
        label.setDoubleBuffered(true);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(x, y, width, height);
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
                InitialFrame.parallaxMove(new Point(area.getX() + e.getX(),  area.getY() + e.getY()));
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

    /**
     * Creates a list for selecting a theme.
     * @return the created list
     */
    public JComboBox<String> createThemeComboBox()
    {
        final String[] themes = new String[]
        {
            "Red hearts",
            "Orange hearts",
            "Yellow hearts",
            "Green hearts",
            "Blue hearts",
            "Purple hearts",
            "Pink hearts",
            "Gray hearts",
            "Rainbow hearts"
        };
        JComboBox<String> combo = new JComboBox<>(themes);
        combo.setLayout(null);
        combo.setBackground(white);
        combo.setBounds(400, 350, 150, 30);
        combo.setFont(createFont(20));
        combo.setForeground(black);
        combo.addActionListener(new AbstractAction()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                frame.requestFocus();
            }
        });
        combo.addItemListener(e ->
        {
            if(e.getStateChange() == ItemEvent.SELECTED)
            {
                theme = switch(e.getItem().toString())
                {
                    case "Red hearts" -> Theme.RED_HEARTS;
                    case "Orange hearts" -> Theme.ORANGE_HEARTS;
                    case "Yellow hearts" -> Theme.YELLOW_HEARTS;
                    case "Green hearts" -> Theme.GREEN_HEARTS;
                    case "Blue hearts" -> Theme.BLUE_HEARTS;
                    case "Purple hearts" -> Theme.PURPLE_HEARTS;
                    case "Pink hearts" -> Theme.PINK_HEARTS;
                    case "Gray hearts" -> Theme.GRAY_HEARTS;
                    case "Rainbow hearts" -> Theme.RAINBOW_HEARTS;
                    default -> throw new IllegalStateException("Unexpected value: " + e.getItem().toString());
                };
                try {
                    pabunotThemeImage = ImageIO.read(Objects.requireNonNull(Pabunot.selectTheme(theme)));
                }
                catch(IOException ex) {
                    throw new RuntimeException(ex);
                }
                matrix.repaint();
                matrix.revalidate();
            }
        });
        combo.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(combo.getX() + e.getX(),  combo.getY() + e.getY()));
            }
        });
        return combo;
    }

    public JLabel createMatrixLabel()
    {
        final int[] width = new int[1];
        final int[] height = new int[1];
        final int metric = 500;
        paperLength = (metric / Math.max(x, y));
        JLabel label = new JLabel()
        {
            {
                try {
                    pabunotThemeImage = ImageIO.read(Objects.requireNonNull(Pabunot.selectTheme(theme)));
                    pabunotThemeImage = pabunotThemeImage.getScaledInstance(paperLength, paperLength, Image.SCALE_SMOOTH);
                }
                catch(IOException e) {
                    throw new RuntimeException(e);
                }
            }
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                paperLength = (int) Math.floor((double) metric / Math.max(x, y));
                width[0] = paperLength * x;
                height[0] = paperLength * y;
                setSize(width[0], height[0]);
                setLocation((matrixPanel.getWidth() - width[0]) / 2, ((matrixPanel.getHeight() - height[0]) / 2));
                Image io = pabunotThemeImage.getScaledInstance(paperLength, paperLength, Image.SCALE_FAST);
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
        label.setSize(paperLength * x, paperLength * y);
        label.setLocation((matrixPanel.getWidth() - metric) / 2, (matrixPanel.getHeight() - metric) / 2);
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
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),  field.getY() + e.getY()));
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
            pabunotThemeImage = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/hello.png")));
            pabunotThemeImage = pabunotThemeImage.getScaledInstance(1328, 756, Image.SCALE_SMOOTH);
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
                    InitialFrame.isDragging = true;
                    InitialFrame.offset = e.getPoint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (SwingUtilities.isLeftMouseButton(e))
                {
                    InitialFrame.isDragging = false;
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                InitialFrame.x = -24;
                InitialFrame.y = -18;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if(InitialFrame.isDragging)
                {
                    Point currentMouse = e.getLocationOnScreen();

                    int deltaX = currentMouse.x - InitialFrame.offset.x;
                    int deltaY = currentMouse.y - InitialFrame.offset.y;

                    frame.setLocation(deltaX, deltaY);
                }
            }

            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(e.getPoint());
            }
        });
    }

    @Override
    public void paintComponent(Graphics g)
    {
        InitialFrame.render(g);
    }

}