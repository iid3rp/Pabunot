package Pabunot.Prize;

import java.util.ArrayList;

public class Prizes extends ArrayList<Prizes>
{
    private String title;
    private String description;

    public Prizes()
    {
        super();
    }

    private static class Prize
    {
        private String title;
        private String description;

        public Prize(String title, String description)
        {
            this.title = title;
            this.description = description;
        }
    }
}
