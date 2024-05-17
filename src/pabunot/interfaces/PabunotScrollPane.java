package pabunot.interfaces;

import pabunot.InitialFrame;
import pabunot.palabunutan.PabunotInterface;
import pabunot.util.Intention;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.ArrayList;


public class PabunotScrollPane extends JScrollPane
{
    ArrayList<PabunotInterface> list;
    private JPanel container;
    private boolean nothing;
    @Intention PabunotScrollPane pane = this;

    public PabunotScrollPane(ArrayList<PabunotInterface> list)
    {
        super();
        this.list = list;
        setSize(new Dimension(1000, 550));
        setPreferredSize(new Dimension(1000, 700));
        setBackground(new Color(0, 0, 0, 80));
        setDoubleBuffered(true);
        container = createContainer();
        container.setSize(new Dimension(PabunotInterface.WIDTH, ((PabunotInterface.HEIGHT) * list.size()) + list.size()));
        container.setPreferredSize(new Dimension(PabunotInterface.WIDTH, ((PabunotInterface.HEIGHT) * list.size()) + list.size()));

        // add components into the container here :3
        addComponents();

        setViewportView(container);
        //getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
        getViewport().setVisible(true);
        getViewport().setBackground(new Color(0, 0, 0, 90));
        getVerticalScrollBar().setVisible(false);
        getHorizontalScrollBar().setVisible(false);
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
        setVisible(true);
    }

    public void addComponents()
    {
        int index = 0; // iterator
        if(list != null) {
            for(PabunotInterface p : list) {
                p.setBounds(0, (PabunotInterface.HEIGHT * index++), PabunotInterface.WIDTH, PabunotInterface.HEIGHT);
                container.add(p);
            }
            nothing = false;
            container.repaint();
            repaint();
        }
    }

    public void restore()
    {
        container.removeAll();
        container.setSize(new Dimension(PabunotInterface.WIDTH, (PabunotInterface.HEIGHT * list.size()) + (list. size())));
        container.setPreferredSize(new Dimension(PabunotInterface.WIDTH, (PabunotInterface.HEIGHT * list.size()) + (list.size())));
        // add the components to the panel to be put into the scrollPane...
        addComponents();
        setViewportView(container);
        container.repaint();
        container.validate();
        repaint();
        validate();

    }

    private JPanel createContainer()
    {
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(InitialFrame.WIDTH, InitialFrame.HEIGHT));
        panel.setLayout(null);
        panel.setOpaque(false);
        panel.setDoubleBuffered(true);
        panel.setBackground(new Color(0, 0, 0, 100));
        return panel;
    }

    @Override
    public void paintComponent(Graphics ignored) {}
}
