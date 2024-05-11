package Pabunot.Interface;

import Pabunot.Pabunot.PabunotGrid;
import Pabunot.Pabunot.PabunotGridPane;

import javax.swing.*;
import java.awt.*;

public class PabunotSection extends JPanel
{
    public PabunotGridPane pane;
    public PabunotGrid grid;
    public JLabel randomize;

    public PabunotSection(PabunotGrid grid)
    {
        super();
        this.grid = grid;
        setSize(new Dimension(1280, 720));
        setLayout(null);
        setLocation(0, 0);
    }

    @Override
    public String toString()
    {
        return "PabunotSection{" +
                "grid=" + grid +
                '}';
    }
}
