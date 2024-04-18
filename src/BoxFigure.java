public class BoxFigure extends RectangleFigure
{
    private double height;

    public BoxFigure()
    {
        super();
        height = 0;
    }

    public BoxFigure(double length, double width, double height)
    {
        super(length, width);
        this.height = height >= 0? height : 0;
    }

    public double getHeight()
    {
        return height;
    }


    public double area()
    {
        return 2 * (getLength() * getWidth()
                 + getLength() * height
                 + getWidth() * height);
    }

    public double volume()
    {
        return super.area() * height;
    }

    @Override
    public String toString()
    {
        return  "Length = " + getLength() + "\n" +
                "Width = " + getWidth() + "\n" +
                "Height = " + height + "\n\n" +
                "Surface Area = " + area() + "\n" +
                "Volume = " + volume() + "\n";
    }
}
