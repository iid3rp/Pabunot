package pabunot.prize;

import java.util.ArrayList;

public class PrizeList extends ArrayList<Prize>
{
    private String title;
    private String description;

    private long serial;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public void setSerial(long serial)
    {
        this.serial = serial;
    }

    public PrizeList()
    {
        super();
    }

    public long getSerial()
    {
        return serial;
    }
}
