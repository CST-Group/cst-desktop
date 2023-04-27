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

import br.unicamp.cst.util.viewer.*;
import br.unicamp.cst.representation.idea.Idea;
import java.awt.Dialog;

import org.jfree.ui.RefineryUtilities;

import br.unicamp.cst.representation.owrl.AbstractObject;
import br.unicamp.cst.representation.owrl.Property;
import br.unicamp.cst.representation.owrl.QualityDimension;
import br.unicamp.cst.util.viewer.representation.idea.IdeaTreeNode;

/**
 *
 * @author rgudwin
 */
public class IdeaDialogFactory extends javax.swing.JDialog {
    
    
    
    boolean ok = false;
    String categories[] = {"AbstractObject","Property","Link","QualityDimension","Episode","Composite","Aggregate","Configuration",
    "TimeStep","Property","Action","Goal"};

    /**
     * Creates new form AbstractObjectDialog
     * @param parent the parent of the dialog
     * @param modal a flag indicating if the dialog is supposed to be modal or amodal
     */
    public IdeaDialogFactory(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jCategory.setModel(new javax.swing.DefaultComboBoxModel<>(categories));
        jCategory.setSelectedIndex(1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jName = new javax.swing.JTextField();
        jOK = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jValue = new javax.swing.JTextField();
        jCancel = new javax.swing.JButton();
        jType = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScope = new javax.swing.JComboBox<>();
        jTypeEnable = new javax.swing.JCheckBox();
        jCategory = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Name:");

        jName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jNameActionPerformed(evt);
            }
        });

        jOK.setText("OK");
        jOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOKActionPerformed(evt);
            }
        });

        jLabel3.setText("Value:");

        jValue.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jValueActionPerformed(evt);
            }
        });

        jCancel.setText("Cancel");
        jCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCancelActionPerformed(evt);
            }
        });

        jType.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTypeActionPerformed(evt);
            }
        });

        jLabel1.setText("Type:");

        jLabel4.setText("Category:");

        jLabel5.setText("Scope:");

        jScope.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Possibility", "Existence", "Law" }));
        jScope.setSelectedIndex(1);
        jScope.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jScopeActionPerformed(evt);
            }
        });

        jTypeEnable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTypeEnableActionPerformed(evt);
            }
        });

        jCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jType)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jCancel))
                    .addComponent(jName)
                    .addComponent(jValue)
                    .addComponent(jScope, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jCategory, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTypeEnable)
                .addGap(15, 15, 15))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jValue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1))
                    .addComponent(jTypeEnable))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jScope, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jOK)
                    .addComponent(jCancel))
                .addGap(24, 24, 24))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jNameActionPerformed

    private void jOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOKActionPerformed
        // TODO add your handling code here:
        ok = true;
        setVisible(false);
    }//GEN-LAST:event_jOKActionPerformed

    private void jValueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jValueActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jValueActionPerformed

    private void jCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCancelActionPerformed
        // TODO add your handling code here:
        ok = false;
        setVisible(false);
    }//GEN-LAST:event_jCancelActionPerformed

    private void jTypeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTypeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTypeActionPerformed

    private void jScopeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jScopeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jScopeActionPerformed

    private void jTypeEnableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTypeEnableActionPerformed
        jType.setEditable(jTypeEnable.isSelected());
        jType.setEnabled(jTypeEnable.isSelected());
    }//GEN-LAST:event_jTypeEnableActionPerformed
    
    public static IdeaTreeNode getIdea(IdeaTreeNode baseNode) {

    IdeaDialogFactory dialog = new IdeaDialogFactory(null,true);
    dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    //dialog.setBounds(350, 350, 200, 200);
    dialog.setTitle("Enter child Idea ...");
    dialog.jName.setEditable(true);
    dialog.jName.setEnabled(true);
    dialog.jValue.setEditable(true);
    dialog.jValue.setEnabled(true);
    dialog.jType.setEditable(false);
    dialog.jType.setEnabled(false);
    //RefineryUtilities.centerFrameOnScreen(dialog);
    dialog.setVisible(true);
    dialog.dispose();
    if (dialog.ok == true) {
        String newname = dialog.jName.getText();
        String newvalue = dialog.jValue.getText();
        String newcategory = (String) dialog.jCategory.getSelectedItem();
        int newscope;
        newscope = dialog.jScope.getSelectedIndex();
        Idea newwmnode;
        if (!dialog.jType.isEditable()) {
            newwmnode = new Idea(newname,newvalue,newcategory,newscope);
        }
        else {
            int newtype;
            try {newtype = Integer.parseInt(dialog.jType.getText());
            } catch (Exception e) {newtype = 0;}
            newwmnode = new Idea(newname,newvalue,newtype,newcategory,newscope);
        }
        IdeaTreeNode newnode = baseNode.addIdeaNode(newwmnode);
        newnode.representIdea(newwmnode);
        return(newnode);
    }
    else return(null);
    } 
    
    public static String editIdea(IdeaTreeNode node) {
        IdeaDialogFactory dialog = new IdeaDialogFactory(null,true);
        dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        //dialog.setBounds(350, 350, 200, 200);
        dialog.setTitle("Edit Idea data ...");
        dialog.jValue.setEditable(true);
        dialog.jValue.setEnabled(true);
        dialog.jType.setEnabled(false);
        dialog.jType.setEditable(false);
        TreeElement te = (TreeElement) node.getUserObject();
        Idea idea = (Idea) te.getElement();
        String text = te.getName();
        dialog.jName.setText(idea.getName());
        dialog.jValue.setText(String.valueOf(idea.getValue()));
        dialog.jType.setText(String.valueOf(idea.getType()));
        dialog.jCategory.setSelectedItem(idea.getCategory());
        dialog.jScope.setSelectedIndex(idea.getScope());
        //RefineryUtilities.centerFrameOnScreen(dialog);
        dialog.setVisible(true);
        dialog.dispose();
        if (dialog.ok == true) {
           Idea id = (Idea) te.getElement();
           id.setName(dialog.jName.getText());
           id.setValue(dialog.jValue.getText());
           id.setCategory((String)dialog.jCategory.getSelectedItem());
           id.setScope(dialog.jScope.getSelectedIndex());
           int type;
           if (dialog.jType.isEditable()) {
               try {type = Integer.parseInt(dialog.jType.getText());
               } catch (Exception e) {type = id.guessType(id.getCategory(),id.getScope());}
           }
           else 
               type = id.guessType(id.getCategory(),id.getScope());
           id.setType(type);
           node.representIdea(id);
           return(dialog.jName.getText());
        }
        else return null;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jCancel;
    private javax.swing.JComboBox<String> jCategory;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JTextField jName;
    private javax.swing.JButton jOK;
    private javax.swing.JComboBox<String> jScope;
    private javax.swing.JTextField jType;
    private javax.swing.JCheckBox jTypeEnable;
    private javax.swing.JTextField jValue;
    // End of variables declaration//GEN-END:variables
}
