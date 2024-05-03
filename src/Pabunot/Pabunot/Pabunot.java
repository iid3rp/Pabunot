package Pabunot.Pabunot;

public class Pabunot
{

    private int value;
    private boolean isPicked;

    public int getValue()
    {
        return value;
    }

    public Pabunot(int value)
    {
        this.value = value;
        isPicked = false;
    }

    @Deprecated
    public void setValue(int value)
    {
        this.value = value;
    }

    public void setPicked(boolean flag)
    {
        this.isPicked = flag;
    }

    public boolean isPicked()
    {
        return isPicked;
    }
}
