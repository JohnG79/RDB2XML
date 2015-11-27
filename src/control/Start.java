package control;

import rdb2xml.ui.MainFrame;

public class Start
{

    public static void main( String args[] )
    {
        Controller controller = new Controller();
        
        MainFrame mainFrame = new MainFrame( controller );
        controller.setMainFrame( mainFrame );
        mainFrame.setLocationRelativeTo( null );
        mainFrame.setVisible( true );
    }
}
