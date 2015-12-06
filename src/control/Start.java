package control;

import rdb2xml.ui.MainDialog;

public class Start
{

    public static void main( String args[] )
    {
        Controller controller = new Controller();

        MainDialog mainFrame = new MainDialog( controller );
        controller.setMainFrame( mainFrame );
        mainFrame.setLocationRelativeTo( null );
        mainFrame.setVisible( true );
    }
}
