package treetable.tree.node;

import treetable.tree.node.Node;

public abstract class ChildNode extends Node
{

    public ChildNode( int treeItemNumber, Object[] objects )
    {
        
        super( treeItemNumber, objects );
    }

    public int getTreeItemNumber()
    {
        return treeItemNumber;
    }
    //public abstract void acceptVisitor( Model.RDFModel model );
    //public abstract void acceptVisitor( Model.OWLModel model );
}
