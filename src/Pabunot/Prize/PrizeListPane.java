package Pabunot.Prize;

import Pabunot.InitialFrame;
import Pabunot.Utils.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class PrizeListPane extends JScrollPane
{
    private static final int width = PrizeInterface.WIDTH;
    private static final int height = PrizeInterface.HEIGHT;
    public PrizeList list;
    private JPanel container;
    private int size;
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
    public PrizeListPane(InitialFrame frame)
    {
        super();
        size = 0;
        list = new PrizeList();
        this.frame = frame;
        initializeComponent(frame);
        container.setSize(new Dimension(width, ((height) * list.size()) + list.size()));
        container.setPreferredSize(new Dimension(width, ((height) * list.size()) + size));
        // add components into the container here :3
        addComponents();

        setViewportView(container);
        getViewport().setScrollMode(JViewport.SIMPLE_SCROLL_MODE);
        getViewport().setOpaque(false);
        getVerticalScrollBar().setVisible(false);
        getHorizontalScrollBar().setVisible(false);
        setSize(new Dimension(width, 300));
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
                PrizeInterface bankInterface = new PrizeInterface(p, list, this);
                bankInterface.setBounds(0, (PrizeInterface.HEIGHT * index++), bankInterface.getWidth(), bankInterface.getHeight());
                container.add(bankInterface);
            }
            nothing = false;
            container.repaint();
            repaint();
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