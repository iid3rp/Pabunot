package Pabunot.Utils;

import Pabunot.InitialFrame;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * The {@code AndyBold} class provides a utility method to create a custom {@link Font} object from a TrueType font file.
 * This class is specifically designed to load the "Andy Bold" font from a font file, which is expected to be located
 * in the "Resources" directory relative to the {@link InitialFrame} class's location.
 *
 * <p>This class contains a static method {@code createFont} which takes a font size and returns a {@link Font}
 * object set to the specified size. The method ensures that the font file is loaded only once and reused for
 * subsequent calls.</p>
 *
 * <p>Usage example:</p>
 * <pre>
 *     Font myFont = AndyBold.createFont(24f);
 * </pre>
 *
 * @see Font
 */
public class AndyBold
{
    public static Font font;

    /**
     * Creates and returns a {@link Font} object for the AndyBold font with the specified size.
     * If the font has not been loaded before, it loads it from the file system.
     *
     * @param size the size of the font
     * @return the {@link Font} object set to the specified size
     * @throws RuntimeException if there is an error loading the font file, including missing file or format issues
     */
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