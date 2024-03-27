/***********************************************************************************************
 * Copyright (c) 2012  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * <p>
 * Contributors:
 * K. Raizer, A. L. O. Paraense, E. M. Froes, R. R. Gudwin - initial API and implementation
 ***********************************************************************************************/
package br.unicamp.cst.util.viewer.representation.idea;

import br.unicamp.cst.representation.idea.Idea;
import br.unicamp.cst.util.viewer.TreeElement;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;


/**
 *
 * @author rgudwin
 */
public class IdeaTreeNode extends DefaultMutableTreeNode {
    
    static CopyOnWriteArrayList<Idea> repr = new CopyOnWriteArrayList();
    
    public IdeaTreeNode() {
        super(new TreeElement("State", TreeElement.NODE_NORMAL, "State", TreeElement.ICON_MIND));
    }
    
    public IdeaTreeNode(String name, int node_type, Object element, int typeIcon) {
        super(new TreeElement(name,node_type,element,typeIcon));
    }
    
    public IdeaTreeNode(String name, String value, int node_type, Object element, int typeIcon) {
        super(new TreeElement(name,value,node_type,element,typeIcon));
    }
    
    public IdeaTreeNode(String name, int icon_type) {
        super(new TreeElement(name, TreeElement.NODE_NORMAL, name, icon_type));
    }
    
    public IdeaTreeNode(Idea idea) {
        super(new TreeElement(idea.getName(),TreeElement.NODE_NORMAL,idea,TreeElement.ICON_OBJECT3));
        for (Idea i : idea.getL()) {
            IdeaTreeNode ntn = new IdeaTreeNode(i);
            add(ntn);
        }
        representIdea(idea);
    }
    
    public IdeaTreeNode(String fullname, Idea idea) {
        super(new TreeElement(fullname+"."+idea.getName(),TreeElement.NODE_NORMAL,idea,TreeElement.ICON_OBJECT3));
        for (Idea i : idea.getL()) {
            IdeaTreeNode ntn = new IdeaTreeNode(fullname+"."+idea.getName(),i);
            add(ntn);
        }
        representIdea(idea);
    }
    
    public IdeaTreeNode addRootNode(String rootNodeName) {
        Idea rootWM = Idea.createIdea(rootNodeName,"",0);
        IdeaTreeNode root = new IdeaTreeNode(rootNodeName, TreeElement.NODE_NORMAL, rootWM, TreeElement.ICON_MIND);
        return(root);
    }
    
    public IdeaTreeNode genIdNode(Idea ido) {
        String value = "";
        if (!String.valueOf(ido.getValue()).equals("")) value = " [" + String.valueOf(ido.getValue())+"]";
        IdeaTreeNode idNode = new IdeaTreeNode(ido.getName()+value, TreeElement.NODE_NORMAL, ido, TreeElement.ICON_OBJECT3); 
        return(idNode);
    }
    
    public IdeaTreeNode genFinalIdNode(Idea ido) {
        String value = "";
        if (!String.valueOf(ido.getValue()).equals("")) value = " [<font color=red>" + String.valueOf(ido.getValue())+"</font>]";
        IdeaTreeNode idNode = new IdeaTreeNode(ido.getName()+value, TreeElement.NODE_NORMAL, ido, TreeElement.ICON_OBJECT2); 
        return(idNode);
    }
    
    public IdeaTreeNode genValNode(Idea node) {
        IdeaTreeNode valueNode = new IdeaTreeNode(node.getName()+": "+String.valueOf(node.getValue()), TreeElement.NODE_NORMAL, node, TreeElement.ICON_PROPERTY);
        return(valueNode);
    }
    
    public void resetType(Idea node) {
        Idea category = node.get("category");
        if (category != null && category.getValue() instanceof String) {
            String category_type = (String) category.getValue();
            node.setType(Idea.guessType(category_type,node.getScope()));
        } 
    }
    
    public String ExtendedTextMode(int mode, String name, String value) {
        String out;
        if (value.equalsIgnoreCase(""))
            return(name);
        switch(mode) {
            default:
            case 0: 
                    out = name+" ["+value+"]";
                    break;
            case 1: out = name+": "+value;
                    break;
            case 2: out = name+" [<font color=red>"+value+"</font>]";
                    break;
        }
        return(out);
    }
    
    public void representIdea(Idea node) {
        TreeElement te = getTreeElement();
        String value = "";
        if (node.getValue() != null) value = node.getValue().toString();
        resetType(node);
        
        int type = node.getType();
        int ic;
        int mode;
        if (type <= TreeElement.table.length) {
            ic = (int) TreeElement.table[type][0];
            mode = (int) TreeElement.table[type][1];
        }
        else {
            ic = TreeElement.ICON_OBJECT3;
            mode = 0;
        }
        if (te.getIcon() != TreeElement.ICON_MIND) 
           te.setIcon(ic);
        te.setName(ExtendedTextMode(mode,node.getName(),value));
    }
    
    public IdeaTreeNode getIdNode(Idea node) {
        // IF the ido is already in the list, just return it
        for (Idea ii : repr) {
            if (equals(node,ii)) {
                IdeaTreeNode idNode = genFinalIdNode(node);
                //System.out.println("getIdNode: Já está na lista ... "+ido.toString());                
                return idNode;
            }
        }
        // ELSE, first add it to the list
        repr.add(node);
        // THEN generate a new node for it
        IdeaTreeNode idNode;
        if (node.isType(0)) idNode = genIdNode(node);
        else idNode = genFinalIdNode(node);
        return(idNode);
    }
    
    int recursion = 0;
    public IdeaTreeNode addIdeaNode(Idea node) {
        recursion++;
        IdeaTreeNode idNode = getIdNode(node);
        idNode.representIdea(node);
        for (Idea n : node.getL()) {
            IdeaTreeNode part = addIdeaNode(n);
            idNode.add(part); 
        }
        add(idNode);
        return(idNode);    
    }
    
    public IdeaTreeNode restartRootNode(Idea node) {
        IdeaTreeNode root = addRootNode(node.getName());
        repr = new CopyOnWriteArrayList<Idea>();
        ExpandStateLibrary.set(root,true);
        for (Idea wm : node.getL()) {
            IdeaTreeNode child = root.addIdeaNode(wm);
            ExpandStateLibrary.set(child,true);
        }
        TreeElement oldrootte = (TreeElement)root.getUserObject();
        oldrootte.setElement(node);
        return root;
    }
    
    public boolean equals(Idea ido, Idea ii) {
        String s1 = ido.getName()+String.valueOf(ido.getValue());
        String s2 = ii.getName()+String.valueOf(ii.getValue());
        return(s1.toString().equalsIgnoreCase(s2.toString()));
    }
    
    public TreeElement getTreeElement() {
        return((TreeElement)this.getUserObject());
    }
    
    public void sort() {
        IdeaTreeNodeComparator comparator = new IdeaTreeNodeComparator();
        if (comparator != null)
        {
            Collections.sort(this.children,comparator);
        }
    }

    public void addWithoutSorting(MutableTreeNode newChild){
        super.add(newChild);
    }

    @Override
    public void add(MutableTreeNode newChild)
    {
        IdeaTreeNodeComparator comparator = new IdeaTreeNodeComparator();
        super.add(newChild);
        if (comparator != null)
        {
            Collections.sort(this.children,comparator);
        }
    }
    
}
