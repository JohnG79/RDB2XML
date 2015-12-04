package control;

import rdb2xml.ui.Main;

public class Start
{

    public static void main( String args[] )
    {
        Controller controller = new Controller();

        Main mainFrame = new Main( controller );
        controller.setMainFrame( mainFrame );
        mainFrame.setLocationRelativeTo( null );
        mainFrame.setVisible( true );
    }
}
