package Pabunot.Interface;

import Pabunot.Graphics.TrailLabel;
import Pabunot.InitialFrame;
import Pabunot.Pabunot.Pabunot;
import Pabunot.Pabunot.PabunotInterface;
import Pabunot.StreamIO.PabunotMaker;
import Pabunot.StreamIO.PabunotReader;
import Pabunot.Utils.AndyBold;
import Pabunot.Utils.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;

public class PabunotPickerPanel extends JPanel
{
    public TrailLabel title;
    PabunotScrollPane pane;
    @Intention InitialFrame frame;
    JLabel addLabel;
    ArrayList<PabunotInterface> list;

    public PabunotPickerPanel(InitialFrame frame)
    {
        super();
        this.frame = frame;
        initializeComponent();
        list = searchPabunot();
        title = new TrailLabel("Select Pabunot", 50, 20, 20, 30, TrailLabel.rainbow);
        pane = new PabunotScrollPane(list);
        pane.setLocation((getWidth() - pane.getWidth())/2, 100);
        pane.restore();
        addLabel = createAddLabel();
        addComponents();
    }

    private <E> ArrayList<PabunotInterface> searchPabunot()
    {
        ArrayList<PabunotInterface> reference = new ArrayList<>();
        File directory = new File(PabunotMaker.pabunotDir);
        // Filter directories based on the searchPattern
        File[] matchingFolders = directory.listFiles((dir, name) -> new File(dir, name).isDirectory());

        if (matchingFolders == null)
        {
            System.out.println("empty");
            return new ArrayList<>();
        }

        String[] folderNames = matchingFolders.length == 0? null : new String[matchingFolders.length];
        for (int i = 0; i < matchingFolders.length; i++)
        {
            folderNames[i] = matchingFolders[i].getPath();
            System.out.println(folderNames[i]);
        }

        if(folderNames != null)
        {
            for(String s : folderNames)
            {
                Pabunot pbn = PabunotReader.createPalabunotFromFile(s + File.separator + "Pabunot.ini");
                if(pbn != null)
                {
                    PabunotInterface inter = new PabunotInterface(pbn);
                    reference.add(inter);
                }
            }
        }
        return reference;
    }

    private void addComponents()
    {
        for(JLabel l : title)
        {
            add(l);
        }
        add(pane);
        add(addLabel);
    }

    private void initializeComponent()
    {
        setLayout(null);
        setBackground(new Color(0, 0, 0, 0));
        setSize(new Dimension(InitialFrame.WIDTH, InitialFrame.HEIGHT));
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
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
                InitialFrame.isDragging = false;
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
                if (InitialFrame.isDragging)
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

    public JLabel createAddLabel()
    {
        JLabel label = new JLabel();
        label.setText("Add Pabunot");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.white);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(getWidth() - width, getHeight() -  height - 20, width, height);
        return label;
    }
    @Override
    public void paintComponent(Graphics g)
    {
        InitialFrame.render(g);
    }
}
