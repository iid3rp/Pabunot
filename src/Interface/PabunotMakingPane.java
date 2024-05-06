package Interface;

import Pabunot.Graphics.TrailLabel;
import Pabunot.InitialFrame;
import Pabunot.Pabunot.Pabunot;
import Pabunot.Utils.AndyBold;
import Pabunot.Utils.Intention;

import javax.swing.*;
import java.awt.*;

public class PabunotMakingPane extends JPanel
{
    @Intention InitialFrame frame;
    public TrailLabel title;
    public JTextField prize;
    public JTextArea prizeDescription;
    public Pabunot theme;
    public int x;
    public int y;
    public String other;
    public PabunotMakingPane(InitialFrame frame)
    {
        super();
        this.frame = frame;
        initializeComponent();
        title = new TrailLabel("Title goes here!", 20, 10, 20, new Color[] {Color.WHITE});
        prize = createPrize();
        prizeDescription = createDescription();
        add(prize);
        add(prizeDescription);
    }

    private JTextArea createDescription()
    {
        JTextArea area = new JTextArea();
        area.setDoubleBuffered(true);
        area.setLayout(null);
        area.setFont(AndyBold.createFont(20));
        area.setBounds(20, 70, 300, 200);
        area.setSelectionColor(new Color(243, 164, 104));
        area.setLineWrap(true);
        area.setTabSize(2);
        area.setBackground(new Color(119, 91, 91, 61));
        return area;
    }

    private JTextField createPrize()
    {

        JTextField field = new JTextField();
        field.setLayout(null);
        field.setFont(AndyBold.createFont(30));
        field.setBackground(new Color(0, 0, 0, 125));
        field.setBounds(20, 20, 300, 40);
        field.setForeground(Color.white);

        return field;
    }

    private void initializeComponent()
    {
        setSize(new Dimension(1280,720));
        setOpaque(false);
        setDoubleBuffered(true);
        setLayout(null);
    }

    @Override
    public void paintComponent(Graphics ignored) {}

    public static void main(String[] args)
    {
        InitialFrame frame = new InitialFrame();
        frame.setContentPane(new PabunotMakingPane(frame));
    }
}
