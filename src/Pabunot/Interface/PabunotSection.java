package Pabunot.Interface;

import Pabunot.Pabunot.PabunotGridPane;

import javax.swing.*;
import java.awt.*;

public class PabunotSection extends JPanel
{
    public PabunotGridPane pane;
    public JLabel randomize;

    public PabunotSection()
    {
        super();
        setSize(new Dimension(1280, 720));
        setLayout(null);
        setLocation(0, 0);
    }
}
