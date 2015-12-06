package rdb2xml.ui.tree.node;

public interface Foreign extends Key
{

    public Primary getReferencedKey();
}
