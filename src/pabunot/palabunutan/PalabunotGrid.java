package pabunot.palabunutan;

import pabunot.prize.PrizeList;
import pabunot.util.RandomRange;
import pabunot.util.Theme;

import java.util.ArrayList;

public class PalabunotGrid
{
    private int x;
    private int y;
    public ArrayList<Palabunot> grid;
    public PrizeList prizeList;
    public Theme theme;
    private int size;
    private String title;
    private long serial;

    public PalabunotGrid(int x, int y, long serial, String title, PrizeList list, Theme theme)
    {
        grid = new ArrayList<>();
        this.theme = theme;
        this.x = x;
        this.y = y;
        this.prizeList = list;
        this.title = title;
        this.serial = serial;
        setPabunot(theme);
    }

    @Deprecated
    public PalabunotGrid(int x, int y, String title,  PrizeList list, Theme theme)
    {
        grid = new ArrayList<>();
        this.theme = theme;
        this.prizeList = list;
        this.x = x;
        this.y = y;
        this.title = title;
        setPabunot(theme);
    }

    public PalabunotGrid(int x, int y, ArrayList<Palabunot> pb, long serial, String title, PrizeList list, Theme theme)
    {
        grid = pb;
        this.theme = theme;
        this.x = x;
        this.y = y;
        this.prizeList = list;
        this.title = title;
        this.serial = serial;
    }

    private void setPabunot(Theme theme)
    {
        int index = 0;
        for(int i : new RandomRange(1, x * y))
        {
            grid.add(new Palabunot(i, theme));
        }
    }

    public PalabunotGrid()
    {
        x = 0;
        y = 0;
        grid = null;
        size = 0;
        title = null;
        serial = 0;
    }

    public ArrayList<Palabunot> getGrid()
    {
        return grid;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    @Override
    public String toString()
    {
        return "PabunotGrid{" +
                "x=" + x +
                ", y=" + y +
                ", grid=" + grid +
                ", list=" + prizeList +
                ", theme=" + theme +
                ", size=" + size +
                ", title='" + title + '\'' +
                ", serial=" + serial +
                '}';
    }

    public String getTitle()
    {
        return title;
    }

    public long getSerial()
    {
        return serial;
    }

    public int getArrayNotPicked()
    {
        int index = 0;
        for(Palabunot palabunot : grid)
        {
            if(!palabunot.isPicked())
            {
                index++;
            }
        }
        return index;
    }
}
