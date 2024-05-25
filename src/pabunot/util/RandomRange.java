package pabunot.util;

import java.util.ArrayList;
import java.util.Collections;

/**
 * <p>This class extends {@link ArrayList} and is designed to hold a shuffled range of integers.</p>
 * <p>It initializes the list with integers ranging from {@code minimumRange} to {@code maximumRange} and then shuffles them.</p>
 */
public class RandomRange extends ArrayList<Integer>
{
    public RandomRange(int minimumRange, int maximumRange)
    {
        super();

        if(minimumRange > maximumRange)
            throw new IllegalArgumentException();

        for(int i = minimumRange; i <= maximumRange; i++)
        {
            add(i);
        }
        Collections.shuffle(this);
    }
}