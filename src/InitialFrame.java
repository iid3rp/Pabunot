import javax.swing.*;
import java.awt.*;

public class InitialFrame extends JFrame
{
    public InitialFrame()
    {
        super();
        initializeComponent();
    }

    private void initializeComponent()
    {
        setSize(new Dimension(1280, 720));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] a)
    {
        new InitialFrame();
    }
}
