package rdb2xml.ui.tree.table;

import java.awt.Color;
import java.awt.Component;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

class SelectedRowHighlighter extends ColorHighlighter
{

    public SelectedRowHighlighter( HighlightPredicate highlightPredicate )
    {
        super( highlightPredicate );
    }

    @Override
    protected Component doHighlight( Component component, ComponentAdapter componentAdapter )
    {
        component.setBackground( new Color( 255, 255, 225 ) );
        return component;
    }
}
