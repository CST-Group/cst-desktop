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
    
    public IdeaTreeNode addRootNode(String rootNodeName) {
        Idea rootWM = Idea.createIdea(rootNodeName,"",0);
        IdeaTreeNode root = new IdeaTreeNode(rootNodeName, TreeElement.NODE_NORMAL, rootWM, TreeElement.ICON_MIND);
        return(root);
    }
    
    public IdeaTreeNode genIdNode(Idea ido) {
        String value = "";
        if (!ido.getValue().toString().equals("")) value = " [" + ido.getValue().toString()+"]"; 
        IdeaTreeNode idNode = new IdeaTreeNode(ido.getName()+value, TreeElement.NODE_NORMAL, ido, TreeElement.ICON_OBJECT3); 
        return(idNode);
    }
    
    public IdeaTreeNode genFinalIdNode(Idea ido) {
        String value = "";
        if (!ido.getValue().toString().equals("")) value = " [<font color=red>" + ido.getValue().toString()+"</font>]";
        IdeaTreeNode idNode = new IdeaTreeNode(ido.getName()+value, TreeElement.NODE_NORMAL, ido, TreeElement.ICON_OBJECT2); 
        return(idNode);
    }
    
    public IdeaTreeNode genValNode(Idea node) {
        IdeaTreeNode valueNode = new IdeaTreeNode(node.getName()+": "+node.getValue().toString(), TreeElement.NODE_NORMAL, node, TreeElement.ICON_PROPERTY);
        return(valueNode);
    }
    
    public void resetType(Idea node) {
        Idea category = node.get("category");
        if (category != null && category.getValue() instanceof String) {
            String category_type = (String) category.getValue();
            if (category_type.equalsIgnoreCase("AbstractObject")) {
                node.setType(0);
            }
            if (category_type.equalsIgnoreCase("Property")) {
                node.setType(1);
            }
            else if (category_type.equalsIgnoreCase("Link")) {
                node.setType(2);
            }
            else if (category_type.equalsIgnoreCase("QualityDimension")) {
                node.setType(3);
            }
            else if (category_type.equalsIgnoreCase("Episode")) {
                node.setType(4);
            }
            else if (category_type.equalsIgnoreCase("Composite")) {
                node.setType(5);
            }
            else if (category_type.equalsIgnoreCase("Aggregate")) {
                node.setType(6);
            }
            else if (category_type.equalsIgnoreCase("Configuration")) {
                node.setType(7);
            }
            else if (category_type.equalsIgnoreCase("TimeStep")) {
                node.setType(8);
            }
            else if (category_type.equalsIgnoreCase("PropertyCategory")) {
                node.setType(9);
            }
            else if (category_type.equalsIgnoreCase("ObjectCategory")) {
                node.setType(10);
            }
            else if (category_type.equalsIgnoreCase("EpisodeCategory")) {
                node.setType(11);
            }
        } 
    }
    
    public void representIdea(Idea node) {
        TreeElement te = getTreeElement();
        String value = "";
        if (node.getValue() != null) value = node.getValue().toString();
        resetType(node);
        switch(node.getType()) {
               case 0: // This type is for Idea objects
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_OBJECT3);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;
               case 1: // This type is for a Property
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_PROPERTY);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+": "+value);
                       break;
               case 2: // This type is for an object which is already referenced elsewhere (just a link)
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_OBJECT2);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" [<font color=red>"+value+"</font>]"); 
                       break;
               case 3: // This type is for a Quality Dimension
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_QUALITYDIM);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+": "+value); 
                       break;
               case 4: // This type is for an Episode
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_AFFORDANCE);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;
               case 5: // This type is for a Composite Object
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_COMPOSITE);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;        
               case 6: // This type is for an Aggregation Object
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_AGGREGATE);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;                
               case 7: // This type is for a Configuration
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_CONFIGURATION);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;
               case 8: // This type is for a TimeStep
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_TIME);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;
               case 9: // This type is for a PropertyCategory
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_PROPERTYCATEGORY);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+": "+value); 
                       break;        
               case 10: // This type is for a ObjectCategory
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_OBJECTCATEGORY);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;
               case 11: // This type is for a EpisodeCategory
                       if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_EPISODECATEGORY);
                       if (value.equalsIgnoreCase(""))
                           te.setName(node.getName()); 
                       else te.setName(node.getName()+" ["+value+"]"); 
                       break;        
               default: if (te.getIcon() != TreeElement.ICON_MIND) 
                          te.setIcon(TreeElement.ICON_OBJECT3);
                        if (value.equalsIgnoreCase(""))
                           te.setName(node.getName());         
                        else te.setName(node.getName()+" ["+value+"]"); 
                        break;
           }
    }
    
//    public IdeaTreeNode genValNode2(String a, String v) {
//        IdeaTreeNode valueNode = new IdeaTreeNode(a+": "+v, TreeElement.NODE_NORMAL, a, TreeElement.ICON_QUALITYDIM);
//        return(valueNode);
//    }
    
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
    
//    public IdeaTreeNode addIdeaNode(Idea n) {
//        IdeaTreeNode part = addIdentifier(n);
//        return(part);        
//    }
    
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
    
//    public IdeaTreeNode addValue(Idea node) {
//        IdeaTreeNode valueNode = genValNode(node);
//        add(valueNode);
//        return(valueNode); 
//    }
    
    // The following methods: restartRootNode, addIdentifier2 and addWME are used in a new way to construct the jTree
    
    // First, the restartRootNode creates a completely new Tree, by adding a root node and the State node, which is the
    // root of the a new Tree
//    public IdeaTreeNode restartRootNode(List<WMNode> lwm) {
//        IdeaTreeNode root = addRootNode("Root");
//        repr = new CopyOnWriteArrayList<WMNode>();
//        for (Idea wm : lwm) {
//            IdeaTreeNode child = root.addIdentifier(wm);
//            ExpandStateLibrary.set(child,true);
//        }
//        Runtime.getRuntime().gc();
//        return root;
//    }
    
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
    
//    public IdeaTreeNode addIdentifier2(Idea node) {
//        List<WMNodeToBeCreated> toBeCreated = new ArrayList<WMNodeToBeCreated>();
//        List<WMNodeToBeCreated> toBeFurtherProcessed = new ArrayList<WMNodeToBeCreated>();
//        IdeaTreeNode idNode = getIdNode(node);
//        toBeCreated.add(new WMNodeToBeCreated(idNode));
//        List<WMNodeToBeCreated> nextList;
//        do {
//            nextList = new ArrayList<WMNodeToBeCreated>();
//            for (WMNodeToBeCreated wme : toBeCreated) {
//                toBeFurtherProcessed = processStep(wme.parent,wme.newId,wme.attrib);
//                for (WMNodeToBeCreated e : toBeFurtherProcessed) {
//                    Identifier id_ = e.newId;
//                    String attr_ = e.attrib;
//                    IdeaTreeNode part = getIdNode(id_,attr_);
//                    wme.parent.add(part);
//                    nextList.add(new WMNodeToBeCreated(part,id_,attr_));
//                }
//            }
//            toBeCreated = nextList;
//        } while (nextList.size() > 0);    
//        repr = new CopyOnWriteArrayList<Identifier>();
//        return(idNode);    
//    }
    
//    public List<WMNodeToBeCreated> addWME(IdeaTreeNode idNode, Wme wme) {
//        List<WMNodeToBeCreated> toBeFurtherProcessed = new ArrayList<WMNodeToBeCreated>();
//        Identifier idd = wme.getIdentifier();
//        Symbol a = wme.getAttribute();
//        Symbol v = wme.getValue();
//        Identifier testv = v.asIdentifier();
//        if (testv != null) { // v is an identifier
//            // if the identifier is final I can safely introduce it in the tree
//            if (isIdentifierFinal(testv) || idd.toString().equalsIgnoreCase(testv.toString()) ) {
//                String preference = "";
//                if (wme.isAcceptable()) preference = " +";
//                IdeaTreeNode newidNode = genFinalIdNode(testv,a.toString()+preference);
//                idNode.add(newidNode);
//            }
//            else { // mark the identifier to be further processed
//               String preference = "";
//               if (wme.isAcceptable()) preference = " +";
//               if (idd.toString().equalsIgnoreCase(testv.toString()))
//                   System.out.println("WME auto-recursivo detectado: ("+idd.toString()+" "+a.toString()+" "+v.toString()+")");
//               toBeFurtherProcessed.add(new WMNodeToBeCreated(null,testv,a.toString()+preference));
//            }   
//        }
//        else { // v is a value 
//               IdeaTreeNode valueNode = genValNode(a,v);
//               idNode.add(valueNode);
//            }
//        return(toBeFurtherProcessed);    
//    }
    
    
//    void printOperator(Wme wme) {
//        String preference = "";
//        if (wme.isAcceptable()) preference = " +";
//        if (wme.getAttribute().toString().startsWith("operator")) System.out.println("("+wme.getIdentifier()+" "+wme.getAttribute()+" "+wme.getValue()+preference+")");
//    }
    
    // This method scans the identifier ido and finds the new children to be created, inserting nodes for values and final children
//    private List<WMNodeToBeCreated> processStep(IdeaTreeNode idNode, Identifier ido, String attr) {    
//        List<WMNodeToBeCreated> toBeFurtherProcessed = new ArrayList();
//        // Verify all WMEs using ido as an identifier
//        Iterator<Wme> It = ido.getWmes();
//        while (It.hasNext()) {
//            Wme wme = It.next();
//            // Insert nodes for values and final children ... returns the id nodes in the list for further processing
//            List<WMNodeToBeCreated> tbfp = addWME(idNode,wme);
//            toBeFurtherProcessed.addAll(tbfp);
//        }
//        return(toBeFurtherProcessed);
//    }
    
    public boolean equals(Idea ido, Idea ii) {
        String s1 = ido.getName()+ido.getValue().toString();
        String s2 = ii.getName()+ii.getValue().toString();
        return(s1.toString().equalsIgnoreCase(s2.toString()));
    }
    
//    private boolean isIdentifierFinal(Idea ido) {
//        for (Idea ii : repr) {
//            if (equals(ido,ii)) {
//                return true;
//            }
//        }
//        return false;
//    }
    
    public TreeElement getTreeElement() {
        return((TreeElement)this.getUserObject());
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
