/*******************************************************************************
 * Copyright (c) 2012  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors:
 *     K. Raizer, A. L. O. Paraense, R. R. Gudwin - initial API and implementation
 ******************************************************************************/
package br.unicamp.cst.util.viewer.representation.owrl;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import javax.swing.JFileChooser;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Affordance;
import br.unicamp.cst.representation.owrl.Entity;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;
import br.unicamp.cst.util.viewer.DialogFactory;
import br.unicamp.cst.util.viewer.RendererJTree;
import br.unicamp.cst.util.viewer.TreeElement;

/**
 *
 * @author rgudwin
 */
public class AbstractObjectEditor extends javax.swing.JFrame {
    
    AbstractObject root;
    List<AbstractObjectEditorListener> listeners;

    /**
     * Creates new form AbstractObjectEditor
     * @param rootAO the root object in the editor
     */
    public AbstractObjectEditor(AbstractObject rootAO) {
        root = rootAO;
        if (rootAO == null) root = new AbstractObject("AbstractObject");
        listeners = new ArrayList<AbstractObjectEditorListener>();
        initComponents();
        updateTree(root);
        jsp.setViewportView(jtree);
        jtree.setCellRenderer(new RendererJTree());
        setTitle(root.getName());
        MouseListener ml;
        ml = new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                int selRow = jtree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = jtree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1 && e.getButton() == 3) {
                        //DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selPath.getParentPath().getLastPathComponent();
                        
                        DefaultMutableTreeNode tn = (DefaultMutableTreeNode)selPath.getLastPathComponent();
                        br.unicamp.cst.util.viewer.TreeElement te = (br.unicamp.cst.util.viewer.TreeElement)tn.getUserObject();
                        DefaultMutableTreeNode parentnode = (DefaultMutableTreeNode)tn.getParent();
                        br.unicamp.cst.util.viewer.TreeElement parent=null;
                        Object element=null;
                        if (parentnode != null) {
                            parent = (br.unicamp.cst.util.viewer.TreeElement)(parentnode).getUserObject();
                            //System.out.println("Parent: "+parent.getName());
                            element = parent.getElement();
                        }
                        Object parentelement = element;
                        String classname = te.getElement().getClass().getCanonicalName();
                        if (classname != null)
                        if (classname.equals("br.unicamp.cst.representation.owrl.AbstractObject")) {
                            AbstractObject ao = (AbstractObject) te.getElement();
                            JPopupMenu popup = new JPopupMenu();
                            JMenuItem jm1 = new JMenuItem("Edit AbstractObject");
                            ActionListener al = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    editAbstractObject(ao);
                                    updateTree(root);
                                }
                            };
                            jm1.addActionListener(al);
                            JMenuItem jm2 = new JMenuItem("Add new composite object");
                            ActionListener al2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    createCompositeObject(ao);
                                    updateTree(root);
                                }
                            };
                            jm2.addActionListener(al2);
                            JMenuItem jm3 = new JMenuItem("Add new aggregate object");
                            ActionListener al3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    createAggregateObject(ao);
                                    updateTree(root);
                                }
                            };
                            jm3.addActionListener(al3);
                            JMenuItem jm4 = new JMenuItem("Add new Property");
                            ActionListener al4 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    createProperty(ao);
                                    updateTree(root);
                                }
                            };
                            jm4.addActionListener(al4);
                            JMenuItem jm5 = new JMenuItem("Delete this AbstractObject");
                            ActionListener al5;
                            al5 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    deleteComponent(parentelement, ao);
                                    updateTree(root);
                                }
                            };
                            jm5.addActionListener(al5);
                            popup.add(jm1);
                            popup.add(jm2);
                            popup.add(jm3);
                            popup.add(jm4);
                            popup.add(jm5);
                            popup.show(jtree, e.getX(), e.getY());
                        }
                        else if (classname.equals("br.unicamp.cst.representation.owrl.Property")) {
                            Property p = (Property) te.getElement();
                            JPopupMenu popup = new JPopupMenu();
                            JMenuItem jm1 = new JMenuItem("Edit Property");
                            ActionListener al = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    editProperty(p);
                                    updateTree(root);
                                }
                            };
                            jm1.addActionListener(al);
                            JMenuItem jm2 = new JMenuItem("Add new QualityDimension");
                            ActionListener al2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    createQualityDimension(p);
                                    updateTree(root);
                                }
                            };
                            jm2.addActionListener(al2);
                            JMenuItem jm3 = new JMenuItem("Delete this Property");
                            ActionListener al3;
                            al3 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    deleteComponent(parentelement, p);
                                    updateTree(root);
                                }
                            };
                            jm3.addActionListener(al3);
                            popup.add(jm1);
                            popup.add(jm2);
                            popup.add(jm3);
                            popup.show(jtree, e.getX(), e.getY());
                        }
                        else if (classname.equals("br.unicamp.cst.representation.owrl.QualityDimension")) {
                            QualityDimension qd = (QualityDimension) te.getElement();
                            JPopupMenu popup = new JPopupMenu();
                            JMenuItem jm1 = new JMenuItem("Edit QualityDimension");
                            ActionListener al = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    editQualityDimension(qd);
                                    updateTree(root);
                                }
                            };
                            jm1.addActionListener(al);
                            JMenuItem jm2 = new JMenuItem("Delete this QualityDimension");
                            ActionListener al2;
                            al2 = new ActionListener() {
                                public void actionPerformed(ActionEvent e) {
                                    deleteComponent(parentelement, qd);
                                    updateTree(root);
                                }
                            };
                            jm2.addActionListener(al2);
                            popup.add(jm1);
                            popup.add(jm2);
                            popup.show(jtree, e.getX(), e.getY());
                        }
                    }
//               else if(e.getClickCount() == 2) {
//                    System.out.println(selRow + " "+ selPath);
//               }
                }
            }};
        jtree.addMouseListener(ml);
    }
    
    public void addListener(AbstractObjectEditorListener listener) {
        listeners.add(listener);
    }
    
    private void notifyListeners() {
        for (AbstractObjectEditorListener listener : listeners) {
            listener.notifyRootChange(root);
        }
    }
    
    private void createCompositeObject(AbstractObject a) {
        AbstractObject newao = br.unicamp.cst.util.viewer.DialogFactory.getAbstractObject();
        a.addCompositePart(newao);
        
    }
    
    private void createAggregateObject(AbstractObject a) {
        AbstractObject newao = br.unicamp.cst.util.viewer.DialogFactory.getAbstractObject();
        a.addAggregatePart(newao);        
    }
    
    private void createProperty(AbstractObject a) {
        Property newp = br.unicamp.cst.util.viewer.DialogFactory.getProperty();
        a.addProperty(newp);
    }
    
    private void createQualityDimension(Property p) {
        QualityDimension newqd = br.unicamp.cst.util.viewer.DialogFactory.getQualityDimension();
        p.addQualityDimension(newqd);
    }
    
    private void editAbstractObject(AbstractObject ao) {
        br.unicamp.cst.util.viewer.DialogFactory.editAbstractObject(ao);
    }
    
    private void editProperty(Property p) {
        br.unicamp.cst.util.viewer.DialogFactory.editProperty(p);
    }
    
    private void editQualityDimension(QualityDimension qd) {
        DialogFactory.editQualityDimension(qd);
    }
    
    private void deleteComponent(Object parent, Object child) {
        if (parent == null) return;
        String parentclass = parent.getClass().getCanonicalName();
        //System.out.println("Parent class: "+parentclass);
        //String childclass = child.getClass().getCanonicalName();
        if (parentclass.equals("br.unicamp.cst.representation.owrl.AbstractObject")) {
            ((AbstractObject)parent).deleteChild(child);
        }
        if (parentclass.equals("br.unicamp.cst.representation.owrl.Property")) {
            ((Property)parent).deleteChild(child);
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        zoom_in = new javax.swing.JButton();
        zoom_out = new javax.swing.JButton();
        search = new javax.swing.JButton();
        jsp = new javax.swing.JScrollPane();
        jtree = new javax.swing.JTree();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mLoad = new javax.swing.JMenuItem();
        mSave = new javax.swing.JMenuItem();
        mClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        zoom_in.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zoom-in-icon.png"))); // NOI18N
        zoom_in.setFocusable(false);
        zoom_in.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoom_in.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoom_in.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoom_inActionPerformed(evt);
            }
        });
        jToolBar1.add(zoom_in);

        zoom_out.setIcon(new javax.swing.ImageIcon(getClass().getResource("/zoom-out-icon.png"))); // NOI18N
        zoom_out.setFocusable(false);
        zoom_out.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        zoom_out.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        zoom_out.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zoom_outActionPerformed(evt);
            }
        });
        jToolBar1.add(zoom_out);

        search.setIcon(new javax.swing.ImageIcon(getClass().getResource("/binoculars.png"))); // NOI18N
        search.setFocusable(false);
        search.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        search.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        search.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchActionPerformed(evt);
            }
        });
        jToolBar1.add(search);

        jsp.setViewportView(jtree);

        jMenu1.setText("File");

        mLoad.setText("Load");
        mLoad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mLoadActionPerformed(evt);
            }
        });
        jMenu1.add(mLoad);

        mSave.setText("Save");
        mSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mSaveActionPerformed(evt);
            }
        });
        jMenu1.add(mSave);

        mClose.setText("Close");
        mClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mCloseActionPerformed(evt);
            }
        });
        jMenu1.add(mClose);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE)
            .addComponent(jsp)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jsp, javax.swing.GroupLayout.DEFAULT_SIZE, 420, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void zoom_inActionPerformed(ActionEvent evt) {//GEN-FIRST:event_zoom_inActionPerformed
        expandAllNodes(jtree);
    }//GEN-LAST:event_zoom_inActionPerformed

    private void zoom_outActionPerformed(ActionEvent evt) {//GEN-FIRST:event_zoom_outActionPerformed
        collapseAllNodes(jtree);
    }//GEN-LAST:event_zoom_outActionPerformed

    private void searchActionPerformed(ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:

        //� so um teste!
        br.unicamp.cst.util.viewer.TreeElement.reset();
        DefaultMutableTreeNode ao = addObject(root, true);
        TreeModel ttm = new DefaultTreeModel(ao);
        jtree.setModel(ttm);
        expandAllNodes(jtree);

        String nameNode = null;
        nameNode = JOptionPane.showInputDialog("Node Name:");
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) jtree.getModel().getRoot();
        List<DefaultMutableTreeNode> ol = find(root, nameNode);
        for (DefaultMutableTreeNode o : ol) {
            ((br.unicamp.cst.util.viewer.TreeElement) o.getUserObject()).setColor(br.unicamp.cst.util.viewer.TreeElement.NODE_CHANGE);
        }

        //� so um teste!
        TreeModel tm = new DefaultTreeModel(root);
        jtree.setModel(tm);
        expandAllNodes(jtree);


    }//GEN-LAST:event_searchActionPerformed

    private void mSaveActionPerformed(ActionEvent evt) {//GEN-FIRST:event_mSaveActionPerformed
        String filename = "";
        try {JFileChooser chooser = new JFileChooser();
             if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                  filename = chooser.getSelectedFile().getCanonicalPath();
             }
             if (!filename.equals("")) {
                File logFile = new File(filename);
	        BufferedWriter writer = new BufferedWriter(new FileWriter(logFile, true));
                writer.write(root.toStringFull());
                writer.close();
             }
        } catch (Exception e) { e.printStackTrace(); }
    }//GEN-LAST:event_mSaveActionPerformed

    private void mCloseActionPerformed(ActionEvent evt) {//GEN-FIRST:event_mCloseActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_mCloseActionPerformed

    private void mLoadActionPerformed(ActionEvent evt) {//GEN-FIRST:event_mLoadActionPerformed
        // TODO add your handling code here:
        load();
    }//GEN-LAST:event_mLoadActionPerformed
    
//    public enum mode {NULL, COMPOSITE, AGGREGATE, PROPERTY, QUALITY_DIMENSION}
//    
//    private int getLevel(String splitted[]) {
//        int level = 0;
//        for (int i=0;i<splitted.length;i++) {
//            if (splitted[i].equals("")) level++;
//        }
//        return(level/3);
//    }
//    
//    private mode getMode(String splitted[]) {
//        mode m = mode.NULL;
//        for (int i=0;i<splitted.length;i++) {
//            if (splitted[i].equals("*")) {
//                m = mode.COMPOSITE;
//                break;
//            }
//            if (splitted[i].equals("+")) {
//                m = mode.AGGREGATE;
//                break;
//            }
//            if (splitted[i].equals(">")) {
//                m = mode.PROPERTY;
//                break;
//            }
//            if (splitted[i].equals("-")) {
//                m = mode.QUALITY_DIMENSION;
//                break;
//            }
//        }
//        return(m);
//    }
//    
//    private String getName(String splitted[]) {
//        int level = getLevel(splitted);
//        if (level == 0) return(splitted[0]);
//        else return(splitted[3*level+1]);
//    }
//    
//    private String getValue(String splitted[]) {
//        String value = "";
//        for (int i=0;i<splitted.length;i++) {
//            if (splitted[i].equals(":")) {
//                value = splitted[i+1];
//                break;
//            }
//        }    
//        return(value);    
//    }
    
    private boolean load() {
        boolean result = false;
        String line;
        String filename = "";
        AbstractObject newAO = null;
        List<Entity> parseAOs = new ArrayList<Entity>();
        try {JFileChooser chooser = new JFileChooser();
             if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                  filename = chooser.getSelectedFile().getCanonicalPath();
             }
             if (!filename.equals("")) {
//                File logFile = new File(filename);
//	        BufferedReader reader = new BufferedReader(new FileReader(logFile));
//                while ((line = reader.readLine()) != null) {
//                    String linesplitted[] = line.split(" ");
//                    int level = getLevel(linesplitted);
//                    mode m = getMode(linesplitted);
//                    String name = getName(linesplitted);
//                    String value = getValue(linesplitted);
//                    AbstractObject ao;
//                    AbstractObject father;
//                    Property pfather;
//                    if (level == 0) {
//                        newAO = new AbstractObject(name);
//                        parseAOs.add(newAO);
//                    }
//                    else {
//                          switch(m) {
//                                case COMPOSITE:ao = new AbstractObject(name);
//                                               parseAOs.add(ao);
//                                               father = (AbstractObject) parseAOs.get(level-1);
//                                               father.addCompositePart(ao);
//                                               if (level >= parseAOs.size()) parseAOs.add(ao);
//                                               else parseAOs.set(level, ao);
//                                               break;
//                                case AGGREGATE:ao = new AbstractObject(name);
//                                               parseAOs.add(ao);
//                                               father = (AbstractObject) parseAOs.get(level-1);
//                                               father.addAggregatePart(ao);
//                                               if (level >= parseAOs.size()) parseAOs.add(ao);
//                                               else parseAOs.set(level, ao);
//                                               break;
//                                case PROPERTY:Property p = new Property(name);
//                                              father = (AbstractObject) parseAOs.get(level-1);
//                                              father.addProperty(p);
//                                              if (level >= parseAOs.size()) parseAOs.add(p);
//                                              else parseAOs.set(level, p);
//                                              break;
//                                case QUALITY_DIMENSION:QualityDimension qd = new QualityDimension(name,value);
//                                                       pfather = (Property) parseAOs.get(level-1);
//                                                       pfather.addQualityDimension(qd);
//                                                       break;
//                          }                            
//                    }
//                    //System.out.println(line+" -> level: "+level+" mode: "+m+" name: "+name+" value: "+value);
//                }
                newAO = new AbstractObject(new File(filename));
                updateTree(newAO);
                notifyListeners();
                //reader.close();
             }
        } catch (Exception e) { e.printStackTrace(); }
        return(result);
    }
        
    private static QualityDimension getTemperatureValue(AbstractObject object) {
        List<Object> temperature_values = object.search("temperature.value");
        for (Object value : temperature_values) {
            if (value instanceof QualityDimension
                && ((QualityDimension) value).isDouble()) {
                return (QualityDimension) value;
            }
        }
        return null;
    }
    
    private static List<QualityDimension> getColorDimensions(AbstractObject object) {
        List<Object> colors = object.search("color");
        for (Object c : colors) {
            if (c instanceof Property) {
                List<QualityDimension> qds = ((Property) c).getQualityDimensions();
                if (qds.size() == 3) {
                    boolean doubles = true;
                    for (QualityDimension qd : qds) {
                        doubles &= qd.isDouble();
                    }
                    if (doubles) {
                        return qds;
                    }
                }
            }
        }
        return null;
    }
    
    private static List<QualityDimension> getSpeedDimensions(AbstractObject object) {
        List<Object> speeds = object.search("speed");
        for (Object c : speeds) {
            if (c instanceof Property) {
                List<QualityDimension> qds = ((Property) c).getQualityDimensions();
                boolean doubles = true;
                for (QualityDimension qd : qds) {
                    doubles &= qd.isDouble();
                }
                if (doubles) {
                    return qds;
                }
            }
        }
        return null;
    }
    
    private static List<QualityDimension> getPositionDimensions(AbstractObject object) {
        List<Object> speeds = object.search("position");
        for (Object c : speeds) {
            if (c instanceof Property) {
                List<QualityDimension> qds = ((Property) c).getQualityDimensions();
                boolean doubles = true;
                for (QualityDimension qd : qds) {
                    doubles &= qd.isDouble();
                }
                if (doubles) {
                    return qds;
                }
            }
        }
        return null;
    }
    
    private DefaultMutableTreeNode addRootNode(AbstractObject wo) {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(new br.unicamp.cst.util.viewer.TreeElement(wo.getName() , br.unicamp.cst.util.viewer.TreeElement.NODE_NORMAL, wo, br.unicamp.cst.util.viewer.TreeElement.ICON_CONFIGURATION));
        return(root);
    }
    
    private DefaultMutableTreeNode addObject(AbstractObject wo, boolean composite) {
        DefaultMutableTreeNode objectNode;
        //String name = wo.getName() + " &lt;" + wo.getID()+">";
        String name = wo.getName();
        if (composite) objectNode = new DefaultMutableTreeNode(new br.unicamp.cst.util.viewer.TreeElement(name, br.unicamp.cst.util.viewer.TreeElement.NODE_NORMAL, wo, br.unicamp.cst.util.viewer.TreeElement.ICON_COMPOSITE));
        else objectNode = new DefaultMutableTreeNode(new br.unicamp.cst.util.viewer.TreeElement(name, br.unicamp.cst.util.viewer.TreeElement.NODE_NORMAL, wo, br.unicamp.cst.util.viewer.TreeElement.ICON_AGGREGATE));
        List<AbstractObject> parts = wo.getCompositeParts();
        for (AbstractObject oo : parts) {
            DefaultMutableTreeNode part = addObject(oo,true);
            objectNode.add(part);
        }
        List<AbstractObject> aggregates = wo.getAggregateParts();
        for (AbstractObject oo : aggregates) {
            DefaultMutableTreeNode part = addObject(oo,false);
            objectNode.add(part);
        }
        List<Property> props = wo.getProperties();
        for (Property p : props) {
            DefaultMutableTreeNode propertyNode = addProperty(p);
            objectNode.add(propertyNode);
        }
        List<Affordance> affordances = wo.getAffordances();
        for (Affordance a : affordances) {
            DefaultMutableTreeNode propertyNode = addAffordance(a);
            objectNode.add(propertyNode);
        }
        
        return(objectNode);    
    }
    
    private DefaultMutableTreeNode addProperty(Property p) {
        DefaultMutableTreeNode propertyNode = new DefaultMutableTreeNode(new br.unicamp.cst.util.viewer.TreeElement(p.getName(), br.unicamp.cst.util.viewer.TreeElement.NODE_NORMAL, p, br.unicamp.cst.util.viewer.TreeElement.ICON_PROPERTY));
        int size = ((Property) p).getQualityDimensions().size();
        for (int s = 0; s < size; s++) {
            QualityDimension qd = ((Property) p).getQualityDimensions().get(s);
            String chave = qd.getName();
            String value = qd.getValue().toString();
            //DefaultMutableTreeNode qualityDimensionNode = new DefaultMutableTreeNode(new TreeElement(chave+" : "+value, TreeElement.NODE_NORMAL, qd, TreeElement.ICON_QUALITYDIM));
            DefaultMutableTreeNode qualityDimensionNode = new DefaultMutableTreeNode(new br.unicamp.cst.util.viewer.TreeElement(chave,": "+value, br.unicamp.cst.util.viewer.TreeElement.NODE_NORMAL, qd, br.unicamp.cst.util.viewer.TreeElement.ICON_QUALITYDIM));
            propertyNode.add(qualityDimensionNode);
            

        }
        return(propertyNode);
    }
    
    private DefaultMutableTreeNode addAffordance(Affordance a) {
        DefaultMutableTreeNode affordanceNode = new DefaultMutableTreeNode(new br.unicamp.cst.util.viewer.TreeElement(a.getName(), br.unicamp.cst.util.viewer.TreeElement.NODE_NORMAL, a, br.unicamp.cst.util.viewer.TreeElement.ICON_AFFORDANCE));
        
        return(affordanceNode);
    }
    

    
    public void updateTree(AbstractObject wo) {
       br.unicamp.cst.util.viewer.TreeElement.reset();
       root = wo;
       DefaultMutableTreeNode o = addObject(wo,true);
        //DefaultMutableTreeNode o = addRootNode(wo);
        TreeModel tm = new DefaultTreeModel(o);
       //TreeModel tm = createTreeModel(wo);
       jtree.setModel(tm);
       expandAllNodes(jtree);
    }
       
    private void expandAllNodes(JTree tree) {
         expandAllNodes(tree, 0, tree.getRowCount());
    }
    
    private void expandAllNodes(JTree tree, int startingIndex, int rowCount){
       for(int i=startingIndex;i<rowCount;++i){
          tree.expandRow(i);
       }
       if(tree.getRowCount()!=rowCount){
          expandAllNodes(tree, rowCount, tree.getRowCount());
       }
    }
    
    private void collapseAllNodes(JTree tree) {
       int row = tree.getRowCount() - 1;
       while (row >= 0) {
          tree.collapseRow(row);
          row--;
       }
       //tree.expandRow(0);
       row = tree.getRowCount() - 1;
       while (row >= 0) {
          tree.expandRow(row);
          row--;
       }
    }
    
    private List<DefaultMutableTreeNode> findObject(DefaultMutableTreeNode root, Object obj, AtomicReference<String> path) {
        List<DefaultMutableTreeNode> results = new ArrayList<>();
        AtomicReference<String> rootPath = new AtomicReference<>(path.get());
        for (Enumeration children = root.children(); children.hasMoreElements(); ) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) children.nextElement();
            if (((br.unicamp.cst.util.viewer.TreeElement) child.getUserObject()).getElement() == obj) {
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
    
    
    private List<DefaultMutableTreeNode> find(DefaultMutableTreeNode root, String s) {    
        
        if (s != null) {
            AbstractObject aoRoot = (AbstractObject) ((br.unicamp.cst.util.viewer.TreeElement) root.getUserObject()).getElement();
            List<Object> results = aoRoot.search(s);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JScrollPane jsp;
    private javax.swing.JTree jtree;
    private javax.swing.JMenuItem mClose;
    private javax.swing.JMenuItem mLoad;
    private javax.swing.JMenuItem mSave;
    private javax.swing.JButton search;
    private javax.swing.JButton zoom_in;
    private javax.swing.JButton zoom_out;
    // End of variables declaration//GEN-END:variables
}
