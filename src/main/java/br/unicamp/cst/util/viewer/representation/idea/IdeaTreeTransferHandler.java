package br.unicamp.cst.util.viewer.representation.idea;

import br.unicamp.cst.representation.idea.Idea;
import br.unicamp.cst.util.viewer.TreeElement;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class IdeaTreeTransferHandler extends TransferHandler {

    private boolean isTemplate;

    private static Transferable transferable;

    public IdeaTreeTransferHandler(boolean isTemplate){
        this.isTemplate = isTemplate;
    }

    @Override
    public boolean canImport(TransferHandler.TransferSupport support){
        if (isTemplate)
            return false;

        try {
            IdeaTreeNode source = (IdeaTreeNode) transferable.getTransferData(DataFlavor.stringFlavor);
            TreePath dropPath = ((JTree) support.getComponent()).getDropLocation().getPath();
            if (dropPath != null) {
                IdeaTreeNode target = (IdeaTreeNode) dropPath.getLastPathComponent();
                if (source.equals(target))
                    return false;
            }
        } catch (NullPointerException | IOException | UnsupportedFlavorException e) {
        }

        support.setShowDropLocation(true);

        return true;
    }

    @Override
    public int getSourceActions(JComponent c){
        if (isTemplate)
            return COPY;
        return MOVE;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        JTree tree = (JTree) c;

        TreePath[] paths = tree.getSelectionPaths();
        if (paths == null) {
            return null;
        }

        Object k = paths[0].getLastPathComponent();
        IdeaNodeTransferable t = new IdeaNodeTransferable((IdeaTreeNode) k);
        transferable = t;
        return t;
    }

    @Override
    public boolean importData(TransferHandler.TransferSupport support) {
        JTree editTree = (JTree) support.getComponent();
        TreePath dropLocation = editTree.getDropLocation().getPath();

        try {
            IdeaTreeNode transferedNode = (IdeaTreeNode) transferable.getTransferData(DataFlavor.stringFlavor);
            TreeElement transferedElement = (TreeElement) transferedNode.getUserObject();

            Idea newIdea;
            Idea selectedIdea = (Idea) transferedElement.getElement();
            if (selectedIdea != null) {
                if(support.getDropAction() == MOVE)
                    newIdea = selectedIdea;
                else
                    newIdea = selectedIdea.clone();
            } else {
                System.out.println("Creating from scratch");
                newIdea = new Idea(transferedElement.getName(), transferedElement.getValue(), TreeElement.getIdeaTypeFromIcon(transferedElement.getIcon()));
            }

            IdeaTreeNode newTreeNode = new IdeaTreeNode(newIdea);
            newTreeNode.representIdea(newIdea);
            IdeaTreeNode destNode = (IdeaTreeNode) dropLocation.getLastPathComponent();
            if (editTree.getDropLocation().getChildIndex() >= 0)
                destNode.insert(newTreeNode, editTree.getDropLocation().getChildIndex());
            else
                destNode.addWithoutSorting(newTreeNode);
            Idea destIdea = (Idea) destNode.getTreeElement().getElement();
            destIdea.add(newIdea);
            ExpandStateLibrary.set(destNode, true);

            if (support.getDropAction() == MOVE){
                IdeaTreeNode parentNode = (IdeaTreeNode) transferedNode.getParent();
                Idea parentNodeIdea = (Idea) parentNode.getTreeElement().getElement();
                transferedNode.removeFromParent();
                parentNodeIdea.getL().remove(selectedIdea);
            }

            editTree.setModel(new DefaultTreeModel((TreeNode) editTree.getModel().getRoot()));
            IdeaPanel.restoreExpansion(editTree);
        } catch (UnsupportedFlavorException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public class IdeaNodeTransferable implements Transferable{

        IdeaTreeNode element;

        public IdeaNodeTransferable(IdeaTreeNode n) {
            element = n;
        }

        @Override
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[0];
        }

        @Override
        public boolean isDataFlavorSupported(DataFlavor dataFlavor) {
            return true;
        }

        @NotNull
        @Override
        public Object getTransferData(DataFlavor dataFlavor) throws UnsupportedFlavorException, IOException {
            return element;
        }
    }
}