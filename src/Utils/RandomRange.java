package Utils;

import java.util.ArrayList;
import java.util.Collections;

public class RandomRange extends ArrayList<Integer>
{
    public RandomRange(int minimumRange, int maximumRange)
    {
        super();
        for(int i = minimumRange; i < maximumRange; i++)
        {
            add(i);
        }
        Collections.shuffle(this);
    }
}
