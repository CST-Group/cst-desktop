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
import br.unicamp.cst.util.viewer.MindRenderer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jsoar.kernel.memory.Wme;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;
import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.tree.*;

/**
 *
 * @author rgudwin
 */
public class IdeaPanel extends javax.swing.JPanel {
    
    Idea root;
    IdeaTreeNode rootlink;
    boolean editable;
    String path=null;
    IdeaTreeNode clipboard = null;
    transient static CopyOnWriteArrayList<Object> listtoavoidloops = new CopyOnWriteArrayList<>();
    
    public IdeaPanel(Idea rootId, boolean editable) {
        this.editable = editable;
        if (rootId != null) root = rootId;
        else root = new Idea("RootIdea","",0);
        initComponents();
        jsp.setViewportView(jtree);
        rootlink = new IdeaTreeNode(root.getName(),String.valueOf(root.getValue()),TreeElement.NODE_NORMAL,root,TreeElement.ICON_MIND);
        ExpandStateLibrary.set(rootlink,true);
        DefaultTreeModel tm = new DefaultTreeModel(rootlink);
        jtree.setModel(tm);
        jtree.setCellRenderer(new MindRenderer(2));

        jtree.setDragEnabled(true);
        jtree.setDropMode(DropMode.ON_OR_INSERT);
        jtree.setTransferHandler(new IdeaTreeTransferHandler(false));
        jtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

        MouseListener ml;
        ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = jtree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jtree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    IdeaTreeNode tn = (IdeaTreeNode)selPath.getLastPathComponent();
                    if(e.getClickCount() == 1 && e.getButton() == 3) {
                        TreeElement te = (TreeElement)tn.getUserObject();
                        IdeaTreeNode parentnode = (IdeaTreeNode)tn.getParent();
                        TreeElement parent=null;
                        Object element=null;
                        if (parentnode != null) {
                            parent = (TreeElement)(parentnode).getUserObject();
                            element = parent.getElement();
                        }
                        element = te.getElement();
                        Idea node = (Idea) element;
                        JPopupMenu popup = new JPopupMenu();
                        JMenuItem jm1 = new JMenuItem("Edit Idea");
                        ActionListener al = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    editIdea(tn);
                                }
                        };
                        jm1.addActionListener(al);
                        JMenuItem jm2 = new JMenuItem("Add child Idea");
                        ActionListener al2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    createIdea(tn);
                                }
                        };
                        jm2.addActionListener(al2);
                        JMenuItem jm3 = new JMenuItem("Paste Ideas in Clipboard");
                        ActionListener al3;
                        al3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    pasteComponent(tn);
                                }
                        };
                        jm3.addActionListener(al3);
                        JMenuItem jm4 = new JMenuItem("Delete this Idea");
                        ActionListener al4;
                        al4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    deleteComponent(tn);
                                }
                        };
                        jm4.addActionListener(al4);
                        JMenuItem jm5 = new JMenuItem("Copy this Idea");
                        ActionListener al5;
                        al5 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    copyComponent(tn);
                                }
                        };
                        jm5.addActionListener(al5);
                        popup.add(jm1);
                        popup.add(jm2);
                        popup.add(jm5);
                        if (clipboard != null) popup.add(jm3);
                        popup.add(jm4);
                        popup.show(jtree, e.getX(), e.getY());
                        
                    } else if (e.getClickCount() == 2 && e.getButton() == 1){
                        editIdea(tn);
                    }
                }
            }};
        if (editable) jtree.addMouseListener(ml);
    }
    
    public IdeaTreeNode getRootTreeNode() {
        return(rootlink);
    }
    
    public Idea getRoot() {
        return(root);
    }
    
    public void setRoot(Idea root) {
        this.root = root;
    }
    
    public void restartPanel(Idea root) {
        this.root = root;
        updateTree();
    }
    
//    public void addListener(WmeEditorListener listener) {
//        listeners.add(listener);
//    }
//    
//    private void notifyListeners() {
//        for (WmeEditorListener listener : listeners) {
//            //listener.notifyRootChange(root);
//        }
//    }
    
    private void createIdea(IdeaTreeNode parent) {
        TreeElement.reset();
        IdeaTreeNode newao = IdeaDialogFactory.getIdea(parent);
        Idea wmparent = (Idea)parent.getTreeElement().getElement();
        Idea wmtobeadded = (Idea) newao.getTreeElement().getElement();
        wmparent.add(wmtobeadded);
        if (wmtobeadded.getName().equalsIgnoreCase("category")) {
               //parent.resetType(wmparent);
               parent.representIdea(wmparent);
        }  
        //newao.getTreeElement().setExpand(true);
        //System.out.println("inputLink "+sb.inputLink);
        parent.add(newao);
        ExpandStateLibrary.set(newao,true);
        TreeModel tm = new DefaultTreeModel(rootlink);
        jtree.setModel(tm);
        restoreExpansion(jtree);
    }
    
    private void editIdea(IdeaTreeNode node) {
        IdeaDialogFactory.editIdea(node);
        Idea editedIdea = (Idea) node.getTreeElement().getElement();
        if (editedIdea.getName().equalsIgnoreCase("category")) {
            IdeaTreeNode parent = (IdeaTreeNode) node.getParent();
            Idea parentIdea = (Idea)parent.getTreeElement().getElement();
            //parent.resetType(parentIdea);
            parent.representIdea(parentIdea);
        }
        TreeModel tm = new DefaultTreeModel(rootlink);
        jtree.setModel(tm);
        restoreExpansion(jtree);
    }
    
    private void deleteComponent(Object node) {
        if (node == null) return;
        if (node instanceof IdeaTreeNode){
            IdeaTreeNode tnode = (IdeaTreeNode) node;
            IdeaTreeNode tparent = (IdeaTreeNode) tnode.getParent();
            Idea wmparent = (Idea)tparent.getTreeElement().getElement();
            Idea wmtoberemoved = (Idea) tnode.getTreeElement().getElement();
            tnode.removeFromParent();
            clipboard = tnode;
            wmparent.getL().remove(wmtoberemoved);
            TreeModel tm = new DefaultTreeModel(rootlink);
            jtree.setModel(tm);
            restoreExpansion(jtree);
        }
    }
    
    private void pasteComponent(Object node) {
        if (node == null || clipboard == null) return;
        if (node instanceof IdeaTreeNode){
            IdeaTreeNode tnode = (IdeaTreeNode) node;
            //IdeaTreeNode tparent = (IdeaTreeNode) tnode.getParent();
            //Idea wmparent = (Idea)tparent.getTreeElement().getElement();
            //Idea wmtoberemoved = (Idea) tnode.getTreeElement().getElement();
            //tnode.removeFromParent();
            //clipboard.resetType(root);
            //clipboard.representIdea(root);
            tnode.add(clipboard);
            Idea tidea = (Idea) tnode.getTreeElement().getElement();
            Idea clipidea = (Idea) clipboard.getTreeElement().getElement();
            tidea.add(clipidea);
            //wmparent.getL().remove(wmtoberemoved);
            ExpandStateLibrary.set(tnode,true);
            ExpandStateLibrary.set(clipboard,true);
            TreeModel tm = new DefaultTreeModel(rootlink);
            jtree.setModel(tm);
            restoreExpansion(jtree);
        }
    }
    
    private void copyComponent(Object node) {
        if (node == null) return;
        if (node instanceof IdeaTreeNode){
            IdeaTreeNode tnode = (IdeaTreeNode) node;
            Idea tidea = (Idea) tnode.getTreeElement().getElement();
            Idea copyIdea = tidea.clone();
            IdeaTreeNode newNode = new IdeaTreeNode(copyIdea);
            //newNode.resetType(tidea);
            newNode.representIdea(tidea);
            clipboard = newNode;
            TreeModel tm = new DefaultTreeModel(rootlink);
            jtree.setModel(tm);
            restoreExpansion(jtree);
        }
    }
    
    public void expanded(TreeExpansionEvent evt) {
        TreePath tp = evt.getPath();
        Object[] path = tp.getPath();
        for (Object t : path) {
            if (t instanceof IdeaTreeNode) {
                IdeaTreeNode n = (IdeaTreeNode) t;
                TreeElement e = (TreeElement)n.getUserObject();
                //e.setExpand(true);
                ExpandStateLibrary.set(n, true);
                //System.out.println(e.getName());
            }
        }
    }
    
    public void collapse(TreeExpansionEvent evt) {
        TreePath tp = evt.getPath();
        Object[] path = tp.getPath();
        for (Object t : path) {
            if (t instanceof IdeaTreeNode) {
                IdeaTreeNode n = (IdeaTreeNode) t;
                TreeElement e = (TreeElement)n.getUserObject();
                if (t != path[path.length-1]) ExpandStateLibrary.set(n, true);//e.setExpand(true);
                else {
                    //e.setExpand(false);
                    ExpandStateLibrary.set(n, false);
                    //System.out.println("Setting "+e.getName()+" to collapse");
                }
                //System.out.println(e.getName());
            }
        }
    }
    
    private void treatExpansionOrCollapse(TreeExpansionEvent evt) {
        TreePath tp = evt.getPath();
        JTree tree = (JTree) evt.getSource(); 
        TreeModel tm = tree.getModel();
        IdeaTreeNode rootNode = (IdeaTreeNode)tm.getRoot();
        Object[] path = tp.getPath();
        for (Object t : path) {
            if (t instanceof TreeElement) {
                TreeElement e = (TreeElement) t;
            } else if (t instanceof IdeaTreeNode) {
                IdeaTreeNode n = (IdeaTreeNode) t;
                TreeElement e = (TreeElement)n.getUserObject();
            }
        }    
    }
    
    public enum mode {NULL, LINK, VALUE}
    
    private int getLevel(String splitted[]) {
        int level = 0;
        for (int i=0;i<splitted.length;i++) {
            if (splitted[i].equals("")) level++;
        }
        return(level/3);
    }
    
    private mode getMode(String splitted[]) {
        mode m = mode.NULL;
        for (int i=0;i<splitted.length;i++) {
            if (splitted[i].equals("*")) {
                m = mode.LINK;
                break;
            }
            if (splitted[i].equals("-")) {
                m = mode.VALUE;
                break;
            }
        }
        return(m);
    }
    
    private int getIntMode(String s) {
        String mode[] = s.split("\\(");
        String mode2[] = {""};
        int nmode = 0;
        int n=0;
        if (mode.length > 1) {
            mode2 = mode[1].split("\\)");
            String moden = mode2[0];
            try {
                 nmode = Integer.parseInt(moden);
            } catch (Exception e) {
                    nmode = 0;
            }
        }
        return(nmode);
    }
    
    private String getName(String splitted[]) {
        int level = getLevel(splitted);
        String almost = splitted[3*level+1];
        String last = almost.split(":")[0];
        return(last);
    }
    
    private String getValue(String splitted[]) {
        String value = "";
        for (int i=0;i<splitted.length;i++) {
            
            if (splitted[i].startsWith("[")) {
                String casted = splitted[i];
                String scasted[] = casted.split("\\[");
                value = scasted[1].split("\\]")[0];
                break;
            }
        }    
        return(value);    
    }

    public void save() {
        Gson gson = new GsonBuilder()
                //.registerTypeAdapter(Idea.class, new InterfaceAdapter<Idea>())
                             .setPrettyPrinting().create();
        String ss = gson.toJson(root);
        //System.out.println(ss);
        String filename = "";
        try {JFileChooser chooser = new JFileChooser(path);
             if (chooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
                  filename = chooser.getSelectedFile().getCanonicalPath();
                  path = filename;
             }
             if (!filename.equals("")) {
                File logFile = new File(filename);
	        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, false));
                //String s = this.buildFromIdeaTreeNode(rootlink);
                //writer.write(s);                
                writer.write(ss);
                writer.close();
                //System.out.println("Wrote to "+filename);
                //System.out.println(s);
             }
        } catch (Exception e) { e.printStackTrace(); }
    }
    
    public Idea load() {
        //boolean result = false;
        String line;
        String filename = "";
        Idea newAO = null;
        List<Idea> parseAOs = new ArrayList<Idea>();
        try {JFileChooser chooser = new JFileChooser(path);
             if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                  filename = chooser.getSelectedFile().getCanonicalPath();
                  path = filename;
             }
             if (!filename.equals("")) {
//                File logFile = new File(filename);
//	        BufferedReader reader = new BufferedReader(new FileReader(logFile));
//                while ((line = reader.readLine()) != null) {
//                    String linesplitted[] = line.split(" ");
//                    int level = getLevel(linesplitted);
//                    mode m = getMode(linesplitted);
//                    int mo = getIntMode(linesplitted[3*level]);
//                    String name = getName(linesplitted);
//                    String value = getValue(linesplitted);
//                    System.out.println("mode: "+mo+" name: "+name+" value: "+value+ " level: "+level);
//                    Idea ao;
//                    Idea father;
//                    if (level == 0) {
//                        //newAO = Idea.createIdea(name,value,mo);
//                        newAO = new Idea(name,value,mo);
//                        parseAOs.add(newAO);
//                    }
//                    else {
//                          //ao = Idea.createIdea(name,value,mo);
//                          ao = new Idea(name,value,mo);
//                          parseAOs.add(ao);
//                          father = (Idea) parseAOs.get(level-1);
//                          father.add(ao);
//                          if (level >= parseAOs.size()) parseAOs.add(ao);
//                          else parseAOs.set(level, ao);
//                    }
//                }
                newAO = loadFileJson(filename);
                //newAO = 
                //String res = toStringFull(newAO);
                //System.out.println(res);
                //notifyListeners();
//                reader.close();
                root = newAO;
                rootlink = rootlink.restartRootNode(root);
                //TreeElement oldrootte = (TreeElement)rootlink.getUserObject();
                //oldrootte.setElement(root);
             }
        } catch (Exception e) { e.printStackTrace(); }
        return(newAO);
    }
    
    Idea loadFile(String filename) {
        String line;
        Idea newAO = null;
        List<Idea> parseAOs = new ArrayList<Idea>();
        File logFile = new File(filename);
        try {
	        BufferedReader reader = new BufferedReader(new FileReader(logFile));
                while ((line = reader.readLine()) != null) {
                    String linesplitted[] = line.split(" ");
                    int level = getLevel(linesplitted);
                    mode m = getMode(linesplitted);
                    int mo = getIntMode(linesplitted[3*level]);
                    String name = getName(linesplitted);
                    String value = getValue(linesplitted);
                    System.out.println("mode: "+mo+" name: "+name+" value: "+value+ " level: "+level);
                    Idea ao;
                    Idea father;
                    if (level == 0) {
                        //newAO = Idea.createIdea(name,value,mo);
                        newAO = new Idea(name,value,mo);
                        parseAOs.add(newAO);
                    }
                    else {
                          //ao = Idea.createIdea(name,value,mo);
                          ao = new Idea(name,value,mo);
                          parseAOs.add(ao);
                          father = (Idea) parseAOs.get(level-1);
                          father.add(ao);
                          if (level >= parseAOs.size()) parseAOs.add(ao);
                          else parseAOs.set(level, ao);
                    }
                }
                reader.close();
        } catch (Exception e) {
        }
        return(newAO);
    }
    
    Idea loadFileJson(String filename) {
        Gson gson = new GsonBuilder()
                //.registerTypeAdapter(Idea.class, new InterfaceAdapter<Idea>())
                             .setPrettyPrinting().create();
        String content="";
        try{content= new String ( Files.readAllBytes( Paths.get(filename) ) );}catch(Exception e){}
        //System.out.println(content);
        Idea id = gson.fromJson(content,Idea.class);
        return(id);
    }
    
    private static void expandNode(JTree tree, IdeaTreeNode node) {
        //System.out.println("Expanding node "+node.getTreeElement().getName()+" that has expand "+node.getTreeElement().getExpand());
        TreePath tp = new TreePath(node.getPath());
        tree.expandPath(tp);
        ExpandStateLibrary.set(node, true);
    }
    
    private static void collapseNode(JTree tree, IdeaTreeNode node) {
        //System.out.println("Collapsing node "+node.getTreeElement().getName()+" that has expand "+node.getTreeElement().getExpand());
        TreePath tp = new TreePath(node.getPath());
        tree.collapsePath(tp);
        ExpandStateLibrary.set(node, false);
    }
    
    private static void restoreExpansion(IdeaTreeNode wn, JTree tree) {
        //if (wn.isExpanded()) expandNode(tree,wn);
        if (ExpandStateLibrary.get(wn)) expandNode(tree,wn);
        else collapseNode(tree,wn);
        int childnumber = wn.getChildCount();
        for (int i=0;i<childnumber;i++) {
            IdeaTreeNode child = (IdeaTreeNode) wn.getChildAt(i);
            //if (child.isExpanded()) restoreExpansion(child,tree);
            if (ExpandStateLibrary.get(child)) restoreExpansion(child,tree);
        }
    }
    
    public static void restoreExpansion(JTree tree) {
        TreeModel tm = tree.getModel();
        IdeaTreeNode rootNode = (IdeaTreeNode)tm.getRoot();
        restoreExpansion(rootNode,tree);
    }
       
    private void expandAllNodes(JTree tree) {
         expandAllNodes(tree, 0, tree.getRowCount());
    }
    
    public void expandAllNodes() {
        expandAllNodes(jtree);
    }
    
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
       for(int i=startingIndex;i<rowCount;++i){
          tree.expandRow(i);
          IdeaTreeNode node = (IdeaTreeNode)tree.getPathForRow(i).getLastPathComponent();
          //((TreeElement)node.getUserObject()).setExpand(true);
          ExpandStateLibrary.set(node, true);
          //System.out.println("Expanding node "+((TreeElement)node.getUserObject()).getName());
       }
       if(tree.getRowCount()!=rowCount){
          expandAllNodes(tree, rowCount, tree.getRowCount());
       }
    }
    
    public void collapseAllNodes() {
        collapseAllNodes(jtree);
    }
    
    private void collapseAllNodes(JTree tree) {
       int row = tree.getRowCount() - 1;
       while (row >= 0) {
          tree.collapseRow(row);
          IdeaTreeNode node = (IdeaTreeNode)tree.getPathForRow(row).getLastPathComponent();
          //((TreeElement)node.getUserObject()).setExpand(false);
          ExpandStateLibrary.set(node,false);
          //System.out.println("Collapsing node "+((TreeElement)node.getUserObject()).getName());
          row--;
       }
       //tree.expandRow(0);
       row = tree.getRowCount() - 1;
       while (row >= 0) {
          tree.expandRow(row);
          IdeaTreeNode node = (IdeaTreeNode)tree.getPathForRow(row).getLastPathComponent();
          //((TreeElement)node.getUserObject()).setExpand(true);
          ExpandStateLibrary.set(node,true);
          //System.out.println("Expanding node "+((TreeElement)node.getUserObject()).getName());
          row--;
       }
    }
    
    private List<DefaultMutableTreeNode> findObject(DefaultMutableTreeNode root, Object obj, AtomicReference<String> path) {
        List<DefaultMutableTreeNode> results = new ArrayList<>();
        AtomicReference<String> rootPath = new AtomicReference<>(path.get());
        for (Enumeration children = root.children(); children.hasMoreElements(); ) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
            if (((TreeElement) child.getUserObject()).getElement() == obj) {
                results.add(child);
            }
        }
        if (!results.isEmpty()) {
            int dot = path.get().lastIndexOf(".");
            path.set(path.get().substring(0, (dot < 0 ? 0 : dot)));
        }
        for (Enumeration children = root.children(); children.hasMoreElements(); ) {
            AtomicReference<String> rootPath2 = new AtomicReference<>(rootPath.get());
            List<DefaultMutableTreeNode> result = findObject((DefaultMutableTreeNode) children.nextElement(), obj, rootPath2);
            if (!result.isEmpty()) {
                results.addAll(result);
                int dot = rootPath2.get().lastIndexOf(".");
                rootPath2.set(rootPath2.get().substring(0, (dot < 0 ? 0 : dot)));
                if (path.get().equals(rootPath.get()) || path.get().length() < rootPath2.get().length()) {
                    path.set(rootPath2.get());
                }
            }
        }
        if (!results.isEmpty() && !path.get().isEmpty() && !path.get().equals(rootPath.get())) {
            results.add(0, root);
        }
        return results;
    }
    

    private List<Object> searchSubIdeas(Idea idea, String s){
        List<Object> results = new ArrayList<>();
        if (idea.getName().contains(s) || idea.getValue().toString().contains(s))
            results.add(idea);
        for (Idea subIdea : idea.getL()){
            results.addAll(searchSubIdeas(subIdea, s));
        }
        return results;
    }
    public List<DefaultMutableTreeNode> find(DefaultMutableTreeNode root, String s) {
        
        if (s != null) {
            Idea aoRoot = (Idea) ((TreeElement) root.getUserObject()).getElement();
            List<Object> results = searchSubIdeas(aoRoot, s);
            List<DefaultMutableTreeNode> treeResults = new ArrayList<>();
            for (Object result : results) {
                if (((TreeElement) root.getUserObject()).getElement() == result) {
                    treeResults.add(root);
                } else {
                    List<DefaultMutableTreeNode> treeResult = findObject(root, result, new AtomicReference<>(s));
                    treeResults.addAll(treeResult);
                }
            }
            return treeResults;
        } else {
            return new ArrayList<>();
        }
        
        
        /*DefaultMutableTreeNode root2 = root;
        
        Enumeration<DefaultMutableTreeNode> e = root2.depthFirstEnumeration();
        List<TreePath> listPath = new ArrayList<>();
        TreePath raffledPath = null;
        int cont = 0;
        
        while (e.hasMoreElements()) {
            DefaultMutableTreeNode node = e.nextElement();
             TreeElement te = (TreeElement)node.getUserObject();
                        
            if (te.getName().toString().equalsIgnoreCase(s)) {
                cont++;
                listPath.add(new TreePath(node.getPath()));
                
                //return new TreePath(node.getPath());
            }
        }
        
        if( listPath.size()> 0){
            Random r = new Random();
            raffledPath = listPath.get(r.nextInt(listPath.size()));

            DefaultMutableTreeNode tn = (DefaultMutableTreeNode) raffledPath.getLastPathComponent();
            TreeElement te = (TreeElement) tn.getUserObject();
            te.setColor(TreeElement.NODE_CHANGE);
            
            
            
        
        }
       
        
        System.out.println("Achei: "+cont);
       // return raffledPath;
       return root2; */
        
    }
    
    public void updateTreeOld() {
       rootlink = rootlink.restartRootNode(root);
       TreeModel tm = new DefaultTreeModel(rootlink);
       ExpandStateLibrary.set(rootlink, true);
       jtree.setModel(tm);
       restoreExpansion(jtree);
    }
    
    public void updateTree() {
       
       
       rootlink = rootlink.restartRootNode(root);
       rootlink.sort();
       TreeModel tm = new DefaultTreeModel(rootlink);
       ExpandStateLibrary.set(rootlink, true);
       jtree.setModel(tm);
       restoreExpansion(jtree);
    }
    
    // This method creates a complete new Tree in the Editor
//    public void updateTree() {
//       rootlink = rootlink.restartRootNode(sb.getWM());
//       TreeModel tm = new DefaultTreeModel(rootlink);
//       ExpandStateLibrary.set(rootlink, true);
//       jtree.setModel(tm);
//       restoreExpansion(jtree);
//    }
    
    public Idea addIdeaTreeNode2(IdeaTreeNode node) {
        TreeElement te = node.getTreeElement();
        if (te.getIcon() == TreeElement.ICON_OBJECT || te.getIcon() == TreeElement.ICON_OBJECT2 || 
            te.getIcon() == TreeElement.ICON_CONFIGURATION || te.getIcon() == TreeElement.ICON_MIND ) {
            Idea ln = Idea.createIdea(te.getName(),"",0);
            int numberofchildren = node.getChildCount();
            for (int i=0;i<numberofchildren;i++) {
                IdeaTreeNode c = (IdeaTreeNode) node.getChildAt(i);
                Idea e = addIdeaTreeNode(c);
                ln.add(e);
            }
            return(ln);
        }
        else {
            String parse[] = te.getName().split(": ");
            Idea vn = new Idea(parse[0],parse[1]);
            return(vn);
        }
    }
    
     
    public Idea addIdeaTreeNode(IdeaTreeNode node) {
        TreeElement te = node.getTreeElement();
        return((Idea)te.getElement());
    }
    
    public String buildFromIdeaTreeNode(IdeaTreeNode rootwme) {
        Idea e = addIdeaTreeNode(rootwme);
        //return(e.toStringFull());
        return(toStringFull(e));
    }
    
    public String toStringFull(Idea id) {
        return(toStringFull(id,false));
    }
    
    public static void reset() {
        listtoavoidloops = new CopyOnWriteArrayList<>();
    }
    
    public String toStringFull(Idea id, boolean withid) {
        reset();
        return(toStringFull(id,1,withid));
    }
    
    public String toStringPlus(Idea id,boolean withid) {
        String appendix = "";
        String out;
        String value = (String) id.getValue();
        if (value != null && !value.equals("")) {
            if (id.getType() == 1) appendix = ": "+value;
            else appendix = " ["+value+"]";
        }
        if (withid) appendix += " <"+id+">";
        out = "("+id.getType()+") "+ id.getName()+appendix;
        return(out);
                    
    }
    
    
    
    public boolean already_exists(Idea id, Object o) {
        if (o == null || id.isPrimitive(o)) return false;
        for (Object oo : listtoavoidloops)
           if (oo.hashCode() == o.hashCode()) return true;
        return false;
    }
    
    public String toStringFull(Idea id, int level, boolean withid) {
        String out; 
        out = toStringPlus(id,withid)+"\n";
        listtoavoidloops.add(toStringPlus(id,withid));
        for (Idea ln : id.getL()) {
            for (int i=0;i<level;i++) out += "   ";
            if (listtoavoidloops.contains(toStringPlus(ln,withid)) || already_exists(id,toStringPlus(ln,withid))) {
                out += toStringPlus(ln,withid)+"\n";
            }
            else out += toStringFull(ln,level+1,withid);
        }
        return(out);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jsp = new javax.swing.JScrollPane();
        jtree = new JTree();

        jtree.setMaximumSize(new java.awt.Dimension(83, 200));
        jtree.setMinimumSize(new java.awt.Dimension(0, 200));
        jtree.addTreeExpansionListener(new javax.swing.event.TreeExpansionListener() {
            public void treeExpanded(TreeExpansionEvent evt) {
                jtreeTreeExpanded(evt);
            }
            public void treeCollapsed(TreeExpansionEvent evt) {
                jtreeTreeCollapsed(evt);
            }
        });
        jsp.setViewportView(jtree);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }
    // </editor-fold>                        

    private void jtreeTreeCollapsed(TreeExpansionEvent evt) {
        collapse(evt);
    }                                   

    private void jtreeTreeExpanded(TreeExpansionEvent evt) {
        expanded(evt);
    }                                  


    // Variables declaration - do not modify                     
    private javax.swing.JScrollPane jsp;
    private JTree jtree;
    // End of variables declaration                   
}
