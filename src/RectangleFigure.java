public class RectangleFigure
{
    private double length;
    private double width;

    public RectangleFigure()
    {
        length = width = 0;
    }

    public RectangleFigure(double length, double width)
    {
        setDimension(length, width);
    }

    private void setDimension(double length, double width)
    {
        this.length = length >= 0? length : 0;
        this.width = width >= 0? width : 0;
    }

    public double getLength()
    {
        return length;
    }

    public double getWidth()
    {
        return width;
    }

    public double area()
    {
        return width * length;
    }

    public double perimeter()
    {
        return 2 * (length + width);
    }

    public String toString()
    {
        return  "Length = " + length + "\n" +
                "Width = " + width + "\n" +
                "Area = " + area() + "\n";
    }
}
