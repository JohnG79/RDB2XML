package rdb2xml.ui.tree.node;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.TreeNode;
import org.jdesktop.swingx.treetable.MutableTreeTableNode;
import org.jdesktop.swingx.treetable.TreeTableNode;

public abstract class AbstractLeafNode extends AbstractNode {

    public AbstractLeafNode(Object[] objects) {
        super(objects);
        setEditable(1, true);
    }

    @Override
    public final void add(MutableTreeTableNode mttn) {
    }

    @Override
    protected final List<MutableTreeTableNode> createChildrenList() {
        return new ArrayList<>();
    }

    @Override
    public final Enumeration<? extends MutableTreeTableNode> children() {
        throw new UnsupportedOperationException("Leaf nodes do not have children.");
    }

    @Override
    public final TreeTableNode getChildAt(int index) {
        throw new UnsupportedOperationException("Leaf nodes do not have children.");
    }

    @Override
    public final int getChildCount() {
        return 0;
    }

    @Override
    public final int getIndex(TreeNode node) {
        throw new UnsupportedOperationException("Leaf nodes do not have children.");
    }

    @Override
    public final boolean isLeaf() {
        return true;
    }

    @Override
    public final void remove(int index) {
    }

    @Override
    public final void remove(MutableTreeTableNode node) {
    }

    @Override
    public final void setAllowsChildren(boolean allowsChildren) {
    }

    @Override
    public final boolean getAllowsChildren() {
        return false;
    }

    @Override
    public final void insert(MutableTreeTableNode child, int index) {

    }

}
