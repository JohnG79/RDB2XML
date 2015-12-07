package rdb2xml.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class TabCloseButtonListener implements MouseListener
{

    private final JTabbedPane jTabbedPane;
    private final JButton closeButton;

    public TabCloseButtonListener( JTabbedPane jTabbedPane, JButton closeButton )
    {
        this.jTabbedPane = jTabbedPane;
        this.closeButton = closeButton;
    }

    @Override
    public void mouseClicked( MouseEvent e )
    {
        JPanel tab = ( JPanel ) closeButton.getParent();
        int index = jTabbedPane.indexOfTabComponent( tab );
        jTabbedPane.remove( index );

    }

    @Override
    public void mousePressed( MouseEvent e )
    {

    }

    @Override
    public void mouseReleased( MouseEvent e )
    {

    }

    @Override
    public void mouseEntered( MouseEvent e )
    {

    }

    @Override
    public void mouseExited( MouseEvent e )
    {

    }

}
