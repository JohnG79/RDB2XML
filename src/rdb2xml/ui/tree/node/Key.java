package rdb2xml.ui.tree.node;

public interface Key extends Attribute
{

    public String getConstraintName();

    public RelationNode getParent();
}
