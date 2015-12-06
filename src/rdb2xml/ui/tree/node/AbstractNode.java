package rdb2xml.ui.tree.node;

import java.util.ArrayList;

public abstract class AbstractNode extends AbstractMutableTreeTableNode
{

    private ArrayList<Integer> editableColumns;
    private int columnCount = 1;

    private AbstractNode()
    {
    }

    public AbstractNode( Object[] objects )
    {
        super( objects );
        editableColumns = new ArrayList<>();
    }

    @Override
    public Object getValueAt( int columnIndex )
    {
        return getUserObject()[ columnIndex ];
    }

    @Override
    public int getColumnCount()
    {
        return columnCount;
    }

    public int setColumnCount( int columnCount )
    {
        return columnCount;
    }

    @Override
    public Object[] getUserObject()
    {
        return ( Object[] ) super.getUserObject();
    }

    @Override
    public void setUserObject( Object object )
    {
        userObject = object;
    }

    public Object[] getData()
    {
        return getUserObject();
    }

    public void setEditable( int i, boolean isEditable )
    {

        if ( isEditable )
        {
            editableColumns.add( i );
        }
        else
        {
            editableColumns.remove( editableColumns.indexOf( i ) );
        }
    }

    @Override
    public boolean isEditable( int i )
    {
        return editableColumns.contains( i );
    }
}
