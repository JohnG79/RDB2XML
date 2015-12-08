package rdb2xml.ui;

import java.awt.Font;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import javax.swing.JComponent;
import javax.swing.JScrollPane;

public class ScrollFontListener implements MouseWheelListener
{

    private final JComponent component;

    public ScrollFontListener( JComponent component )
    {
        this.component = component;
    }

    @Override
    public void mouseWheelMoved( MouseWheelEvent e )
    {
        if ( e.isAltDown() )
        {
            Font fontBefore = component.getFont();
            Font fontAfter;
            if ( e.getUnitsToScroll() > 0 )
            {
                if ( fontBefore.getSize() > 4 )
                {
                    fontAfter = new Font( fontBefore.getFontName(), fontBefore.getStyle(), fontBefore.getSize() - 1 );
                    component.setFont( fontAfter );
                }
            }
            else if ( fontBefore.getSize() < 64 )
            {
                fontAfter = new Font( fontBefore.getFontName(), fontBefore.getStyle(), fontBefore.getSize() + 1 );
                component.setFont( fontAfter );
            }
        }
        else if ( !e.isControlDown() && !e.isShiftDown() )
        {
            JScrollPane jScrollPane = ( JScrollPane ) ( component.getParent().getParent() );

            if ( e.getUnitsToScroll() > 0 )
            {
                jScrollPane.getVerticalScrollBar().setValue( jScrollPane.getVerticalScrollBar().getValue() + 30 );
            }
            else
            {
                jScrollPane.getVerticalScrollBar().setValue( jScrollPane.getVerticalScrollBar().getValue() - 30 );
            }
        }
    }
}
