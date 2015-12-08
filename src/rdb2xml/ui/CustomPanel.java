package rdb2xml.ui;

import javax.swing.JPanel;

public class CustomPanel extends JPanel
{

    public boolean savedStatus = false;

    public CustomPanel()
    {
        super();
    }

    public void setSavedStatus( boolean savedStatus )
    {
        this.savedStatus = savedStatus;
    }

    public boolean isSaved()
    {
        return savedStatus;
    }
}
