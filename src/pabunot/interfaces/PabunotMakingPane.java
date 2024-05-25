package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.graphics.TrailLabel;
import pabunot.palabunutan.PabunotInterface;
import pabunot.palabunutan.Palabunot;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.prize.Prize;
import pabunot.prize.PrizeListPane;
import pabunot.streamio.PabunotMaker;
import pabunot.util.AndyBold;
import pabunot.util.DataType;
import pabunot.util.Intention;
import pabunot.util.TextFilter;
import pabunot.util.Theme;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.PlainDocument;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import static pabunot.util.AndyBold.createFont;

/**
 * The {@code PabunotMakingPane} class represents a custom JPanel
 * used for configuring and initiating a Pabunot game.
 * This panel allows users to set various parameters for the game,
 * including the dimensions of the grid, theme selection,
 * and prize management.
 * It provides interactive components like text fields, buttons,
 * and combo boxes to customize the game setup.
 *
 * <p>Key features include:</p>
 * <ul>
 *   <li>Setting grid dimensions with interactive increment and
 *   decrement buttons.</li>
 *   <li>Choosing a theme from a predefined list of themes.</li>
 *   <li>Adding prizes with titles and descriptions.</li>
 *   <li>Starting the game with the configured settings.</li>
 * </ul>
 *
 * <p>This class extensively uses {@link TrailLabel} for displaying animated text,
 * and interacts with {@link InitialFrame}
 * to handle user interactions and visual feedback. It also integrates with {@link PrizeListPane}
 * for managing a list of prizes.</p>
 *
 * @see JPanel
 * @see TrailLabel
 * @see InitialFrame
 * @see PrizeListPane
 * @author Francis (iid3rp) Madanlo
 */
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
    public int x;
    public int y;
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
    private BufferedImage pabunotThemeImage;
    private JPanel matrixPanel;
    private JComboBox<String> themeCombo;
    private String pabunotTitle;
    private JLabel contents;

    public PabunotMakingPane(InitialFrame frame, String titleString)
    {
        super();
        x = 10;
        y = 10;
        this.frame = frame;
        theme = Theme.RED_HEARTS;
        initializeComponent();
        title = new TrailLabel(
                "Title: " + titleString,
                30,
                150,
                15,
                25,
                TrailLabel.rainbow);

        pabunotTitle = titleString;
        matrixPanel = createMatrixPanel();
        prizeTitle = createPrize();
        prizeDescription = createDescription();
        letterLimitDesc = createLetterLimit();
        letterLimitField = createLetterLimitField();
        contents = createContents();
        addPrize = createAddPrize();
        matrix = createMatrixLabel();
        pane = new PrizeListPane(frame);
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

    private JLabel createContents()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("<html>" +
                "Pabunot Content: 100<br>" +
                "Chances when getting a prize: unidentified...<html>");
        label.setFont(createFont(20));
        label.setForeground(Color.gray);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight() * 2;
        label.setBounds(20, 60, width, height);
        return label;
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

        add(createGoBack());

        add(createNormalLabel("Prize Section", 20, 110));
        add(createNormalLabel("Dimension", 400, 110));
        add(createNormalLabel("Themes", 400, 320));

        matrixPanel.add(contents);
        matrixPanel.add(matrix);
        matrixPanel.add(createDimensionLabel());
    }


    private JPanel createMatrixPanel()
    {
        JPanel panel = new JPanel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                g.setColor(new Color(0, 0, 0, 90));
                g.fillRect(0, 0, (int) (InitialFrame.HEIGHT * 0.9),
                        InitialFrame.HEIGHT);
            }
        };
        panel.setSize(new Dimension((int) (InitialFrame.HEIGHT * 0.9),
                InitialFrame.HEIGHT));
        panel.setDoubleBuffered(true);
        panel.setOpaque(false);
        panel.setLayout(null);
        panel.setLocation(InitialFrame.WIDTH - panel.getWidth(),
                (InitialFrame.HEIGHT - panel.getHeight()) / 2);
        panel.setVisible(true);
        return panel;
    }

    /**
     * Creates a JCheckBox component styled with specific visual properties.
     * This checkbox is labeled "Apply Both" and is designed to toggle the application
     * of a certain setting uniformly across multiple parameters.
     * The method sets the font size, opacity, and foreground color of the checkbox.
     * It also positions the checkbox at a specific location on the component it is added to.
     * Additionally, it includes a mouse motion listener that triggers a parallax movement effect
     * when the mouse moves over the checkbox.
     *
     * @return JCheckBox The styled checkbox component with added functionality.
     */
    private JCheckBox createSameRatio()
    {
        JCheckBox check = new JCheckBox();
        check.setLayout(null);
        check.setText("Apply Both");
        check.setFont(createFont(20));
        check.setOpaque(false);
        check.setForeground(Color.white);
        FontMetrics metrics = check.getFontMetrics(check.getFont());
        int width = metrics.stringWidth(check.getText());
        int height = metrics.getHeight();
        check.setBounds(400, 230, width + 40, height);
        check.addMouseMotionListener(new MouseMotionAdapter()
    {
        @Override
        public void mouseMoved(MouseEvent e)
        {
            InitialFrame.parallaxMove(new Point(check.getX() + e.getX(),
                    check.getY() + e.getY()));
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
        label.setForeground(Color.white);
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
                setContent();
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
                label.setForeground(Color.white);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),
                        label.getY() + e.getY()));
            }
        });
        return label;
    }

    private void setContent()
    {
        String prizeChance = pane.list != null?
                (((float) pane.list.size()) / (x * y)) * 100 + "% per pabunot"
                : "Undefined...";
        contents.setText("<html>" +
                        "Pabunot Content: " + x * y + "<br>" +
                        "Chances when getting a prize: " + prizeChance + "<html>");
        FontMetrics metrics = contents.getFontMetrics(contents.getFont());
        int width = metrics.stringWidth(contents.getText());
        int height = metrics.getHeight() * 2;
        contents.setBounds(20, 60, width, height);
    }

    private JLabel createAddY()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("+");
        label.setFont(createFont(40));
        label.setForeground(Color.white);
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
                setContent();
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
                label.setForeground(Color.white);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),
                        label.getY() + e.getY()));
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
        label.setForeground(Color.white);
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
                setContent();
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
                label.setForeground(Color.white);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),
                        label.getY() + e.getY()));
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
        label.setForeground(Color.white);
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
                setContent();
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
                label.setForeground(Color.white);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),
                        label.getY() + e.getY()));
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
        label.setForeground(Color.white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(20, 20, width, height);
        return label;
    }

    private JLabel createXLabel()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Width");
        label.setFont(createFont(25));
        label.setForeground(Color.white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(400, 145, width, height);
        return label;
    }

    private JLabel createGoBack()
    {
        JLabel label = new JLabel();
        label.setText("< Back");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.gray);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(20, 20, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                back();
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.yellow);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                label.setForeground(Color.gray);
            }
        });

        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(e.getX() + label.getX(),
                        e.getY() + label.getY()));
            }
        });
        return label;
    }

    private void back()
    {
        setVisible(false);
        frame.picker.setVisible(true);
    }

    private JLabel createStart()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText("Save Pabunot");
        label.setFont(createFont(40));
        label.setForeground(Color.white);
        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds(20, InitialFrame.HEIGHT - height - 10, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                long serial = PabunotMaker.generateSerial();
                PalabunotGrid pb = new PalabunotGrid(x, y, serial, pabunotTitle, pane.list, theme);
                new PabunotMaker(pb);

                panel.setVisible(false);
                frame.picker.add(new PabunotInterface(frame, pb));
                frame.picker.setVisible(true);
                frame.repaint();
                // redirect to the main menu and the scroll pane should be basically restore already...
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                label.setForeground(Color.yellow);
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
                InitialFrame.parallaxMove(new Point(e.getX() + label.getX(),
                        e.getY() + label.getY()));
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
        label.setForeground(Color.white);
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
                                field.setForeground(Color.white);
                                setContent();
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
                                field.setForeground(Color.white);
                            }
                        }
                        else
                        {
                            x = 30;
                            field.setForeground(Color.red);
                        }
                        setContent();
                    }
                    catch(NumberFormatException ignored) {}
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {


            }
        });
        field.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),
                        field.getY() + e.getY()));
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
                                field.setForeground(Color.white);
                            }
                        }
                        else
                        {
                            y = 30;
                            field.setForeground(Color.red);
                        }
                        setContent();
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
                                field.setForeground(Color.white);
                            }
                        }
                        else
                        {
                            y = 30;
                            field.setForeground(Color.red);
                        }
                        setContent();
                    }
                    catch(NumberFormatException ignored) {
                    }
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {


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
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),
                        field.getY() + e.getY()));
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
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),
                        field.getY() + e.getY()));
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
        label.setForeground(Color.white);
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
                    String s = letterLimitDesc.getText().equals("0 / 75")?
                            "no description added..." : prizeDescription.getText();
                    pane.list.addFirst(new Prize(
                            prizeTitle.getText(),
                            s
                        )
                    );
                    pane.restore();

                    prizeTitle.setText("");
                    prizeDescription.setText("");

                    prizeTitle.requestFocus();
                    prizeDescription.requestFocus();
                    frame.requestFocus();
                    setContent();
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
                label.setForeground(Color.white);
            }
        });
        label.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(label.getX() + e.getX(),
                        label.getY() + e.getY()));
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
        label.setForeground(Color.white);
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

    private JLabel createNormalLabel(String s, int x, int y)
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setText(s);
        label.setFont(createFont(20));
        label.setForeground(Color.white);
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
                area.setForeground(Color.white);

            }

            @Override
            public void removeUpdate(DocumentEvent e)
            {
                int chars = area.getText().isEmpty() ? 0 : area.getText().length();
                letterLimitDesc.setText(chars + " / 75");
                area.setForeground(Color.white);
            }

            @Override
            public void changedUpdate(DocumentEvent e)
            {
                int chars = area.getText().isEmpty() ? 0 : area.getText().length();
                letterLimitDesc.setText(chars + " / 75");
                area.setForeground(Color.white);
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
                InitialFrame.parallaxMove(new Point(area.getX() + e.getX(),
                        area.getY() + e.getY()));
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
                "Rainbow hearts",
                "Teal hearts",
                "Wood flowers",
                "Orange fruit",
                "Clover leaf",
                "Lucky block",
                "Sunflowers"
        };
        JComboBox<String> combo = new JComboBox<>(themes);
        combo.setLayout(null);
        combo.setBackground(Color.white);
        combo.setBounds(400, 350, 150, 30);
        combo.setFont(createFont(20));
        combo.setForeground(Color.black);
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
                theme = Palabunot.valueOf(e.getItem().toString());
                try {
                    pabunotThemeImage = ImageIO.read(
                            Objects.requireNonNull(
                                    Palabunot.selectTheme(
                                            theme
                                    )
                            )
                    );
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
                InitialFrame.parallaxMove(new Point(combo.getX() + e.getX(),
                        combo.getY() + e.getY()));
            }
        });
        return combo;
    }

    public JLabel createMatrixLabel()
    {
        final int[] width = new int[1];
        final int[] height = new int[1];
        final int metric = (int) (InitialFrame.HEIGHT * 0.7);
        paperLength = (metric / Math.max(x, y));
        JLabel label = new JLabel()
        {
            {
                try {
                    pabunotThemeImage = ImageIO.read(
                            Objects.requireNonNull(
                                    Palabunot.selectTheme(
                                            theme
                                    )
                            )
                    );
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
                setLocation((int) ((matrixPanel.getWidth() - width[0]) * .6),
                            (int) ((matrixPanel.getHeight() - height[0]) * .6));
                Image io = pabunotThemeImage.getScaledInstance(
                        paperLength,
                        paperLength,
                        Image.SCALE_FAST
                );
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
        label.setOpaque(false);
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
                    i = ImageIO.read(Objects.requireNonNull(Palabunot.selectTheme(theme)));
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
        field.addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(field.getX() + e.getX(),
                        field.getY() + e.getY()));
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
        setVisible(false);
        setSize(new Dimension(InitialFrame.WIDTH, InitialFrame.HEIGHT));
        setOpaque(false);
        setIgnoreRepaint(true);
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
            public void mouseExited(MouseEvent e)
            {
                InitialFrame.parallaxX = -24;
                InitialFrame.parallaxY = -18;
            }
        });

        addMouseMotionListener(new MouseMotionAdapter()
        {
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
        g.setColor(new Color(66, 32, 10, 90));
        g.fillRect(0, 0, InitialFrame.WIDTH, InitialFrame.HEIGHT);
    }
}