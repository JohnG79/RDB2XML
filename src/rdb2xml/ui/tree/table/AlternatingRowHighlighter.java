package rdb2xml.ui.tree.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.ComponentAdapter;
import org.jdesktop.swingx.decorator.HighlightPredicate;

class AlternatingRowHighlighter extends ColorHighlighter {

    private Color color;

    public AlternatingRowHighlighter(HighlightPredicate highlightPredicate, Color color) {
        super(highlightPredicate);
        this.color = color;
    }

    @Override
    protected Component doHighlight(Component component, ComponentAdapter componentAdapter) {
        component.setBackground(color);
        return component;
    }
}
