package control;

import rdb2xml.ui.MainInterface;

public class Start
{

    public static void main( String args[] )
    {
        Controller controller = new Controller();

        MainInterface mainFrame = new MainInterface( controller );
        controller.setMainFrame( mainFrame );
        mainFrame.setLocationRelativeTo( null );
        mainFrame.setVisible( true );
    }
}
