package pabunot.prize;

import java.util.Random;

public class Prize
{
    private String title;
    private String description;
    private long serial;
    private int number;
    private static final Random rand = new Random();

    public Prize(String title, String description)
    {
        this.title = title;
        this.description = description;
        serial = getSerial();
    }

    public Prize(String title, String description, int number)
    {
        this.title = title;
        this.description = description;
        this.number = number;
        serial = getSerial();
    }

    public String getTitle()
    {
        return title;
    }

    public String getDescription()
    {
        return description;
    }

    public long getSerial()
    {
        String rnd = generateNumber();
        return Long.parseLong(rnd);
    }

    private String generateNumber()
    {
        StringBuilder str  = new StringBuilder();
        for(int i = 0; i < 12; i++)
        {
            if(i == 0)
            {
                str.append(rand.nextInt(9) + 1);
            }
            else
            {
                str.append(rand.nextInt(10));
            }
        }
        return str.toString();
    }

    public int getNumber()
    {
        return number;
    }

    public void setNumber(int number)
    {
        this.number = number;
    }
}
