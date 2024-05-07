package Pabunot.Prize;

import Pabunot.InitialFrame;
import Pabunot.Utils.Intention;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PrizeListPane extends JScrollPane
{
    private static final int width = PrizeInterface.WIDTH;
    private static final int height = PrizeInterface.HEIGHT;
    PrizeList list;
    private JPanel container;
    private int size;
    private boolean nothing;
    @Intention
    private InitialFrame frame;

    /**<editor-fold desc="Description">
     * you need to get the panel's preferred size "daw" because that's going
     * to be the reference dimension of the JScrollPane (which is weird but whatever)
     * xd - derp.
     * <p></p>
     * REFER THE CONSTRUCTOR BELOW
     *</editor-fold>
     * */
    public PrizeListPane(PrizeList list, InitialFrame frame)
    {
        super();
        if(list != null)
        {
            size = list.size();
            this.list = list;
            this.frame = frame;
            initializeComponent(frame);
            container.setSize(new Dimension(width, ((height) * list.size()) + list.size()));
            container.setPreferredSize(new Dimension(width, ((height) * list.size()) + size));
            // add components into the container here :3
            addComponents();

            setViewportView(container);
            getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
            setSize(new Dimension(width, 720));
            setDoubleBuffered(true);
            setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            addMouseWheelListener(e ->
            {
                // Increase scroll sensitivity by multiplying the scroll distance
                double unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
                JScrollBar verticalScrollBar = getVerticalScrollBar();
                verticalScrollBar.setValue((int) (verticalScrollBar.getValue() + unitsToScroll));
            });
        }
    }

    public void initializeComponent(InitialFrame frame)
    {
        // add a container to put stuff :3
        container = new JPanel();
        container.setLayout(null);
        container.setLocation(0, 0);
        container.setBackground(Color.white);
        container.setDoubleBuffered(true);
    }

    public JLabel addBankDesign()
    {
        BufferedImage x;
        try {
            x = ImageIO.read(Objects.requireNonNull(InitialFrame.class.getResource("Resources/bankie.png")));
        }
        catch(IOException e) {
            throw new RuntimeException(e);
        }
        JLabel label = new JLabel()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(x, 0, 0, null);
            }
        };
        label.setLayout(null);
        label.setFont(new Font("Segoe UI", Font.BOLD, 20));
        label.setBounds((1080 / 2) - (x.getWidth() / 2) + 10, 300, x.getWidth(), x.getHeight());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }


    private JLabel createNotFound(int x, int y, int size, int font, String text)
    {
        JLabel label = new JLabel();
        label.setForeground(Color.BLACK);
        label.setLayout(null);
        label.setText(text);
        label.setFont(new Font("Segoe UI", font, size));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText());
        int height = metrics.getHeight();

        if(x == -1)
        {
            if(y == -1)
            {
                label.setBounds((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);
            }
            else
            {
                label.setBounds((getWidth() / 2) - (width / 2), y, width, height);
                label.setHorizontalAlignment(SwingConstants.CENTER);
            }
        }
        else
        {
            if(y == -1)
            {
                label.setBounds((getWidth() / 2) - (width / 2), (getHeight() / 2) - (height / 2), width, height);
                label.setVerticalAlignment(SwingConstants.CENTER);
            }
            else
            {
                label.setBounds(x, y, width, height);
            }

        }

        return label;
    }


    public void addComponents()
    {
        int index = 0; // iterator
        if(list != null)
        {
            for(Prize p : list)
            {
                PrizeInterface bankInterface = new PrizeInterface(p);
                bankInterface.setBounds(0, (PrizeInterface.HEIGHT * index++), bankInterface.getWidth(), bankInterface.getHeight());
                container.add(bankInterface);
            }
            nothing = false;
            container.repaint();
            repaint();
        }
    }


    public JLabel createEmptyList()
    {
        JLabel label = new JLabel();
        label.setLayout(null);
        label.setForeground(Color.black);
        label.setText("This bank seems empty!");
        label.setFont(new Font("Segoe UI", Font.BOLD, 55));
        FontMetrics metrics = getFontMetrics(label.getFont());
        int width = metrics.stringWidth(label.getText().toUpperCase());
        int height = metrics.getHeight();
        label.setBounds((container.getWidth() / 2) - (width / 2), 100, width, height);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVisible(true);
        return label;
    }

    public void replaceAccount(Prize b)
    {
        if(b != null)
        {
            for(int i = 0; i < list.size(); i++)
            {
                if(b.getSerial() == list.get(i).getSerial())
                {
                    list.set(i, b);
                }
            }
            restore();
        }
    }

    public void restore()
    {
        container.removeAll();
        container.setSize(new Dimension(width, (PrizeInterface.HEIGHT * list.size()) + (list. size())));
        container.setPreferredSize(new Dimension(width, (PrizeInterface.HEIGHT * list.size()) + (list.size())));
        // add the components to the panel to be put into the scrollPane...
        addComponents();
        setViewportView(container);
        container.repaint();
        container.validate();
        repaint();
        validate();

    }

    @Deprecated
    public JScrollPane getPane()
    {
        return this;
    }

    @Deprecated
    public int getLength()
    {
        return size;
    }

    @Deprecated
    public void setSize(int size)
    {
        this.size = size;
    }
}



