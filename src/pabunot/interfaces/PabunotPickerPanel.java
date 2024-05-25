package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.controls.TitleTyping;
import pabunot.graphics.TrailLabel;
import pabunot.palabunutan.PabunotInterface;
import pabunot.palabunutan.PalabunotGrid;
import pabunot.streamio.PabunotMaker;
import pabunot.streamio.PabunotReader;
import pabunot.util.AndyBold;
import pabunot.util.Intention;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.util.ArrayList;

public class PabunotPickerPanel extends JPanel
{
    public TrailLabel title;
    public PabunotScrollPane pane;

    JLabel goBack;
    @Intention InitialFrame frame;
    @Intention
    PabunotPickerPanel picker = this;
    JLabel addLabel;
    ArrayList<PabunotInterface> list;

    public PabunotPickerPanel(InitialFrame frame)
    {
        super();
        this.frame = frame;
        initializeComponent();
        list = searchPabunot();
        title = new TrailLabel("Select A Spot for a Pabunot!", 50, 20, 30, TrailLabel.rainbow);
        pane = new PabunotScrollPane(list);
        pane.setLocation((getWidth() - pane.getWidth())/2, 100);
        pane.restore();
        goBack= createGoBack();
        addLabel = createAddLabel();
        addComponents();
    }

    private JLabel createGoBack()
    {
        JLabel label = new JLabel();
        label.setText("< Back");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.white);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(20, 20, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                setVisible(false);
                frame.mainMenu.setVisible(true);
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
                InitialFrame.parallaxMove(new Point(e.getX() + label.getX(), e.getY() + label.getY()));
            }
        });
        return label;
    }

    private <L> ArrayList<PabunotInterface> searchPabunot()
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
            System.out.println("its not empty");
            for(String s : folderNames)
            {
                PalabunotGrid pbn = PabunotReader.createPalabunotFromFile(s + File.separator + "Pabunot.ini");

                System.out.println(s + File.separator + "Pabunot.ini");
                if(pbn != null)
                {
                    PabunotInterface inter = new PabunotInterface(frame, pbn);
                    reference.add(inter);
                }
            }
        }
        return reference;
    }

    private void addComponents()
    {
        for(JLabel label : title)
        {
            add(label);
        }
        add(pane);
        add(addLabel);
        add(goBack);
    }

    private void initializeComponent()
    {
        setLayout(null);
        setOpaque(false);
        setSize(new Dimension(InitialFrame.WIDTH, InitialFrame.HEIGHT));
        addMouseListener(new MouseAdapter()
        {

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
        setVisible(false);
    }

    public JLabel createAddLabel()
    {
        JLabel label = new JLabel();
        label.setText("+ Add Pabunot");
        label.setFont(AndyBold.createFont(30));
        label.setForeground(Color.white);

        FontMetrics metrics = label.getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();

        label.setBounds(getWidth() - width, getHeight() - height - 20, width, height);
        label.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                picker.setVisible(false);
                frame.typeEvent = new TitleTyping(frame);
                frame.addKeyListener(frame.typeEvent);
                frame.requestFocus();
                frame.titlePanel.setVisible(true);
                frame.titlePanel.resetTitle();
                frame.typeEvent.string = "";
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
                InitialFrame.parallaxMove(new Point(e.getX() + label.getX(), e.getY() + label.getY()));
            }
        });
        return label;
    }

    public void restore()
    {
        list = searchPabunot();
        pane.restore();
    }

    public void add(PabunotInterface p)
    {
        list.add(p);
        pane.restore();
    }
}
