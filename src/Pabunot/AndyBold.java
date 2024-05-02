package Pabunot;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class AndyBold
{
    public static Font font;
    public static Font createFont(float size)
    {
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File((Objects.requireNonNull(InitialFrame.class.getResource("Resources/Pabunot.AndyBold.ttf")).getPath())));
        }
        catch(FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        return font.deriveFont(size);
    }
}