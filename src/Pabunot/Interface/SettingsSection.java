package Pabunot.Interface;

import Pabunot.Pabunot.PabunotGridPane;

import javax.swing.*;
import java.awt.*;

public class SettingsSection extends JPanel
{
    public PabunotGridPane pane;
    public JLabel randomize;

    public SettingsSection()
    {
        super();
        setSize(new Dimension(1280, 720));
        setLayout(null);
        setLocation(0, 0);
    }
}
