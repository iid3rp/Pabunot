package pabunot.interfaces;

import pabunot.palabunutan.PalabunotGridPane;

import javax.swing.*;
import java.awt.*;

public class SettingsSection extends JPanel
{
    public PalabunotGridPane pane;
    public JLabel randomize;

    public SettingsSection()
    {
        super();
        setSize(new Dimension(1280, 720));
        setLayout(null);
        setLocation(0, 0);
    }
}
