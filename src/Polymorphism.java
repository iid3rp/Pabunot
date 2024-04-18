public class Polymorphism
{
    public static void main(String[] a)
    {
        RectangleFigure rectangle, shapeRef;
        BoxFigure box;

        rectangle = new RectangleFigure(8, 5);
        box = new BoxFigure(10, 7, 3);

        shapeRef = rectangle;
        System.out.println(shapeRef);

        shapeRef = box;
        System.out.println(shapeRef);
    }
}
