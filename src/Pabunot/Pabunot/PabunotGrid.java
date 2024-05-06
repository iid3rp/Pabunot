package Pabunot.Pabunot;

import Pabunot.Utils.RandomRange;

public class PabunotGrid
{
    private int x;
    private int y;
    public Pabunot[] grid;
    private int size;
    private String title;
    private long serial;

    public PabunotGrid(int x, int y, String title, long serial, String theme)
    {
        grid = new Pabunot[x * y];
        this.x = x;
        this.y = y;
        this.title = title;
        this.serial = serial;
        setPabunot(theme);
    }

    private void setPabunot(String theme)
    {
        int index = 0;
        for(int i : new RandomRange(1, x * y))
        {
            grid[index++] = new Pabunot(i, theme);
        }
    }

    public PabunotGrid()
    {
        size = 0;
    }

    public Pabunot[] getGrid()
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

}
