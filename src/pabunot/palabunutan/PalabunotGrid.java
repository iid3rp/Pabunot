package pabunot.palabunutan;

import pabunot.prize.PrizeList;
import pabunot.util.RandomRange;
import pabunot.util.Theme;

import java.util.ArrayList;
import java.util.Arrays;

public class PalabunotGrid extends ArrayList<Palabunot>
{
    private int x;
    private int y;
    public Palabunot[] grid;
    public PrizeList list;
    public Theme theme;
    private int size;
    private String title;
    private long serial;

    public PalabunotGrid(int x, int y, String title, Theme theme)
    {
        grid = new Palabunot[x * y];
        this.theme = theme;
        this.x = x;
        this.y = y;
        this.title = title;
        setPabunot(theme);
    }

    public PalabunotGrid(int x, int y, String title,  PrizeList list, Theme theme)
    {
        grid = new Palabunot[x * y];
        this.theme = theme;
        this.list = list;
        this.x = x;
        this.y = y;
        this.title = title;
        this.serial = 0L;
        setPabunot(theme);
    }

    private void setPabunot(Theme theme)
    {
        int index = 0;
        for(int i : new RandomRange(1, x * y))
        {
            grid[index++] = new Palabunot(i, theme);
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

    public Palabunot[] getGrid()
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
                ", grid=" + Arrays.toString(grid) +
                ", list=" + list +
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
}
