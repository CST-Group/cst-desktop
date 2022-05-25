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
package br.unicamp.cst.util.viewer.representation.idea;

import br.unicamp.cst.representation.idea.Idea;

import java.util.List;

import java.awt.BorderLayout;

/**
 *
 * @author rgudwin
 */
public class IdeaEditor extends javax.swing.JFrame {
    
    IdeaPanel wmp;
    Idea root;
    List<IdeaEditorListener> listeners;
    public boolean finished = false;

    /**
     * Creates new form IdeaEditor
     * @param rootId the root idea on the editor
     * @param editable a flag indicating if the editor is suitable only for viewing of also for editing
     */
    public IdeaEditor(Idea rootId, boolean editable) {
        initComponents();
        setTitle("Idea Editor");
        //root = rootId;
        wmp = new IdeaPanel(rootId, editable);
        //wmp.setOpaque(true); //content panes must be opaque
        //this.setContentPane(wmp);
        jPanel1.setLayout(new BorderLayout());
        jPanel1.add(wmp);
        jPanel1.revalidate();
        if (!editable) {
            //send.setEnabled(false);
            mLoad.setEnabled(false);
            jMenu2.setEnabled(false);
        }
        
        pack();
    }
    
    public void addListener(IdeaEditorListener listener) {
        listeners.add(listener);
    }
    
    private void notifyListeners() {
        for (IdeaEditorListener listener : listeners) {
            listener.notifyRootChange(root);
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
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mLoad = new javax.swing.JMenuItem();
        mSave = new javax.swing.JMenuItem();
        mClose = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 420, Short.MAX_VALUE)
        );

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
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void zoom_inActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoom_inActionPerformed
        wmp.expandAllNodes();
    }//GEN-LAST:event_zoom_inActionPerformed

    private void zoom_outActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_zoom_outActionPerformed
        wmp.collapseAllNodes();
    }//GEN-LAST:event_zoom_outActionPerformed

    private void searchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchActionPerformed
        // TODO add your handling code here:

    }//GEN-LAST:event_searchActionPerformed

    private void mSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mSaveActionPerformed
        wmp.save();
    }//GEN-LAST:event_mSaveActionPerformed

    private void mCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mCloseActionPerformed
        // TODO add your handling code here:
        finished = true;
        System.out.println("Setting finished to "+finished);
        //this.dispose();
        //this.setVisible(false);
    }//GEN-LAST:event_mCloseActionPerformed

    private void mLoadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mLoadActionPerformed
        // TODO add your handling code here:
        wmp.load();
        wmp.updateTree();
//        WMNode il = wmp.getRoot();
//        mv.ilpanel.setRoot(il);
//        mv.ilpanel.updateTree2();
//        mv.sb.setInputLink(il);
//        mv.sb.prepareInputLink();
//        mv.wmpanel.updateTree();
//        mv.setPhaseIndication();
    }//GEN-LAST:event_mLoadActionPerformed
    


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JMenuItem mClose;
    private javax.swing.JMenuItem mLoad;
    private javax.swing.JMenuItem mSave;
    private javax.swing.JButton search;
    private javax.swing.JButton zoom_in;
    private javax.swing.JButton zoom_out;
    // End of variables declaration//GEN-END:variables
}
