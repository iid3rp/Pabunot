package pabunot.palabunutan;

import pabunot.prize.PrizeList;
import pabunot.streamio.PabunotMaker;
import pabunot.util.Intention;
import pabunot.util.RandomRange;
import pabunot.util.Theme;

import java.io.File;
import java.util.ArrayList;

public class PalabunotGrid
{
    private int x;
    private int y;
    public ArrayList<Palabunot> grid;
    public PrizeList prizeList;
    public Theme currentTheme;
    private int size;
    private String title;
    private long serial;

    public PalabunotGrid(int x, int y, long serial, String title, PrizeList list, Theme theme)
    {
        grid = new ArrayList<>();
        this.currentTheme = theme;
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
        this.currentTheme = theme;
        this.prizeList = list;
        this.x = x;
        this.y = y;
        this.title = title;
        setPabunot(theme);
    }

    public PalabunotGrid(int x, int y, ArrayList<Palabunot> pb, long serial, String title, PrizeList list, Theme theme)
    {
        grid = pb;
        this.currentTheme = theme;
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
                ", theme=" + currentTheme +
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

    public void deletePabunot()
    {
        @Intention boolean[] x = new boolean[]
        {
            new File(PabunotMaker.pabunotDir + File.separator + getSerial() + File.separator + "Pabunot.ini").delete(),
            new File(PabunotMaker.pabunotDir + File.separator + getSerial()).delete()
        };
        System.out.println(PabunotMaker.pabunotDir + File.separator + getSerial() + " " + x[0] + " " + x[1]);
    }
}