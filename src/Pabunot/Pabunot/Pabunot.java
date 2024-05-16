package Pabunot.Pabunot;

import Pabunot.Palabunutan.PalabunotGrid;

public class Pabunot
{
    String name;
    PalabunotGrid grid;

    public Pabunot(String name, PalabunotGrid grid)
    {
        this.name = name;
        this.grid = grid;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }
}
