package Pabunot.StreamIO;

import Pabunot.InitialFrame;
import Pabunot.Prize.Prize;
import Pabunot.Prize.PrizeList;
import Pabunot.Utils.Intention;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class ImageMaker
{

    public static final String pictures = System.getProperty("user.home") + File.separator + "Pabunot" + File.separator;
    public static final String picturesNonUnix = pictures.replace("\\", "/");
    public ImageMaker() {}

    /**
     * optional: mag-kuha ug image sa Pabunot with the use of the bank account's number as the file name:
     */
    public static BufferedImage parseImage(PrizeList list, Prize prize)
    {
        BufferedImage bf = new BufferedImage(40, 40, BufferedImage.TYPE_INT_ARGB);
        try
        {
            @Intention(design = "Apparently, it throws an IOException bc the file readers cannot" +
                    "be read when using File.separator for some reason... i guess we go hard-code..")
            File path = new File(picturesNonUnix + list.getSerial() + "/" + prize.getSerial() + ".png");
            URL defaultImageURL = Objects.requireNonNull(InitialFrame.class.getResource("Resources/default-image.jpg"));

            Image image =  path.exists() ?
                    ImageIO.read(path) : ImageIO.read(defaultImageURL);

            image = image.getScaledInstance(40, 40, Image.SCALE_AREA_AVERAGING);
            bf = paintImage(bf, image);
        }
        catch(NullPointerException | IOException e)
        {
            e.printStackTrace(System.out);
            System.exit(0);
        }
        return bf;
    }

    private static BufferedImage paintImage(BufferedImage b, Image i) throws IOException
    {
        @Intention(design = "Apparently, it throws an IOException bc the file [blob-reference] cannot" +
                "be read when using File.separator for some reason... i guess we go hard-code..")
        BufferedImage blur = ImageIO.read(new File(Objects.requireNonNull(
               InitialFrame.class.getResource("Resources/blurCircle.png")).getPath()));
        BufferedImage smol = ImageIO.read(new File(Objects.requireNonNull(
                InitialFrame.class.getResource("Resources/smolCircle.png")).getPath()));

        Graphics2D g2d = b.createGraphics();
        g2d.drawImage(smol, 0, 0, null);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_IN, 1f));
        g2d.drawImage(i, 0, 0, null);
        g2d.dispose();

        BufferedImage image = new BufferedImage(b.getWidth(), b.getHeight(), BufferedImage.TYPE_INT_ARGB);
        g2d = image.createGraphics();
        g2d.drawImage(blur, 0 , 0, null);
        g2d.drawImage(b, 0, 0, null);
        g2d.dispose();

        return image;
    }

    public static void createImage(Prize prize, JLabel label, Image image, PrizeList list) throws IOException
    {
        int x = label.getX();
        int y = label.getY();
        BufferedImage b = new BufferedImage(180, 180, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = b.createGraphics();
        g2d.drawImage(image, x, y, null);
        g2d.dispose();
        ImageIO.write(b, "png", new File(pictures + list.getSerial() + File.separator + prize.getSerial() + ".png"));
    }
}
