package rdb2xml.ui;

import static java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment;

public class DialogHelper
{

    public static int GetScreenWorkingWidth()
    {
        return getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
    }

    public static int GetScreenWorkingHeight()
    {
        return getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
    }

}
