package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.prize.Prize;
import pabunot.prize.PrizeInterface;
import pabunot.prize.PrizeList;
import pabunot.util.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PabunotPrizeListPane extends JScrollPane
{
    public PrizeList list;
    private JPanel container;
    private int size;
    private int width = 350;
    private int height = 50;
    private boolean nothing;
    @Intention public InitialFrame frame;

    /**<editor-fold desc="Description">
     * you need to get the panel's preferred size "daw" because that's going
     * to be the reference dimension of the JScrollPane (which is weird but whatever)
     * xd - derp.
     * <p></p>
     * REFER THE CONSTRUCTOR BELOW
     *</editor-fold>
     * */
    public PabunotPrizeListPane(InitialFrame frame, PrizeList list)
    {
        super();
        size = 0;
        this.list = list;
        this.frame = frame;
        initializeComponent(frame);
        container.setSize(new Dimension(width, height * (list.size() - 1)));
        container.setPreferredSize(new Dimension(width, height * (list.size() - 1)));
        // add components into the container here :3
        addComponents();

        setViewportView(container);
        getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        getViewport().setOpaque(false);
        setLocation(40, 200);
        getVerticalScrollBar().setVisible(false);
        getHorizontalScrollBar().setVisible(false);
        setSize(new Dimension(width, InitialFrame.HEIGHT - 250));
        setDoubleBuffered(true);
        setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        addMouseMotionListener(new MouseMotionAdapter()
        {
            @Override
            public void mouseMoved(MouseEvent e)
            {
                InitialFrame.parallaxMove(new Point(getX() + e.getX(),  getY() + e.getY()));
            }
        });

        addMouseWheelListener(e ->
        {
            // Increase scroll sensitivity by multiplying the scroll distance
            double unitsToScroll = e.getWheelRotation() * e.getScrollAmount() * 5; // Adjust multiplier as needed
            JScrollBar verticalScrollBar = getVerticalScrollBar();
            verticalScrollBar.setValue((int) (verticalScrollBar.getValue() + unitsToScroll));
        });
    }

    public void initializeComponent(InitialFrame frame)
    {
        // add a container to put stuff :3
        container = new JPanel();
        container.setLayout(null);
        container.setLocation(0, 0);
        container.setOpaque(false);
        setBackground(new Color(255, 255, 255, 60));
        setOpaque(false);
        container.setDoubleBuffered(true);
    }

    public void addComponents()
    {
        int index = 0; // iterator
        if(list != null)
        {
            for(Prize p : list)
            {
                PabunotPrizeInterface prizeInterface = new PabunotPrizeInterface(p, list, this);
                prizeInterface.setBounds(0, (prizeInterface.height * index++), prizeInterface.getWidth(), prizeInterface.getHeight());
                container.add(prizeInterface);
            }
            nothing = false;
            container.repaint();
            repaint();
        }
    }

    public void restore()
    {
        container.removeAll();
        container.setSize(new Dimension(width, PrizeInterface.HEIGHT * list.size()));
        container.setPreferredSize(new Dimension(width, PrizeInterface.HEIGHT * list.size()));
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