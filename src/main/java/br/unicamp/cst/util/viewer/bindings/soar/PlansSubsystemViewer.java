/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.unicamp.cst.util.viewer.bindings.soar;

import br.unicamp.cst.bindings.soar.JSoarCodelet;
import br.unicamp.cst.bindings.soar.PlanSelectionCodelet;
import br.unicamp.cst.bindings.soar.PlansSubsystemModule;
import br.unicamp.cst.core.entities.Mind;
import br.unicamp.cst.util.viewer.TreeViewerUtil;

import java.util.Arrays;

import java.util.List;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import org.jsoar.kernel.symbols.Identifier;

/**
 * @author du
 */
public class PlansSubsystemViewer extends JPanel {

    private Thread threadPlans;

    private JSoarCodelet soarPlanningCodelet;

    private PlanSelectionCodelet planSelectionCodelet;

    private Mind mind;

    private ImageIcon pauseIcon;
    private ImageIcon playIcon;

    private int debugstate = 0;
    private boolean bStopRefresh = false;
    private long refreshTime = 100;

    /**
     * Creates new form PlansSubsystemViewer
     */
    public PlansSubsystemViewer(long refreshTime, Mind mind) {
        initComponents();
        setRefreshTime(refreshTime);
        setMind(mind);

        setPauseIcon(new ImageIcon(getClass().getClassLoader().getResource("pause-icon.png")));
        setPlayIcon(new ImageIcon(getClass().getClassLoader().getResource("play-icon.png")));

        initPlansSubsystemViewer(mind.getPlansSubsystemModule());

    }

    private void initPlansSubsystemViewer(PlansSubsystemModule module) {
        setSoarPlanningCodelet(module.getjSoarCodelet());
        setPlanSelectionCodelet(module.getPlanSelectionCodelet());
        startPlansThread();
    }

    private void startPlansThread() {
        setThreadPlans(new Thread() {
            @Override
            public void run() {

                while (!isbStopRefresh()) {
                    while (getDebugstate() != 0) {
                        try {
                            synchronized (getThreadPlans()) {
                                getThreadPlans().wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    if (getDebugstate() == 0) {

                        if (getSoarPlanningCodelet().getInputLinkAsString().trim() != "")
                            txtPlanInput.setText(getSoarPlanningCodelet().getInputLinkAsString());

                        if (getSoarPlanningCodelet().getOutputLinkAsString().trim() != "") {
                            txtPlanOutput.setText(getSoarPlanningCodelet().getOutputLinkAsString());
                        }

                        updateTreePanelCreatedPlans();
                    }

                    try {
                        Thread.sleep(getRefreshTime());
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        if (getSoarPlanningCodelet() != null) {
            getThreadPlans().start();
        }
    }

    public void updateTreePanelCreatedPlans() {
        if (getPlanSelectionCodelet() != null) {
            spCreatedPlans.setEnabled(true);
            TreeViewerUtil.createTreeModelGUI(spCreatedPlans, Arrays.asList(planSelectionCodelet), "Plan Selection Codelet");
        }
    }

    public void updateTreePanel() {
        spPlanSubsystem.setEnabled(true);
        TreeViewerUtil.createTreeModelGUIbyIdentifiers(spPlanSubsystem, getSoarPlanningCodelet().getJsoar().getStates(), "WMEs Debugger");
        TreeViewerUtil.createTreeModelGUIbyIdentifiers(spPlanOperators, getSoarPlanningCodelet().getOperatorsPathList(), "Operators Path Debugger");
    }

    public void finishTreePanel() {
        spPlanSubsystem.setEnabled(false);
    }


    public synchronized void startDebugState() {
        setDebugstate(1);
        getSoarPlanningCodelet().setDebugState(1);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        getSoarPlanningCodelet().getJsoar().resetSimulation();

        startstop.setIcon(getPlayIcon());
        mstep.setEnabled(true);
        step.setEnabled(true);
        //setPhaseIndication();
        //updateTreePanel();

    }

    public synchronized void stopDebugState() {
        getSoarPlanningCodelet().getJsoar().resetSOAR();
        setDebugstate(0);
        getSoarPlanningCodelet().setDebugState(0);
        startstop.setIcon(getPauseIcon());
        mstep.setEnabled(false);
        step.setEnabled(false);
        stepDebugState();
        //setPhaseIndication();
        //finishTreePanel();

        synchronized (getThreadPlans()) {
            getThreadPlans().notify();
        }
    }

    public void moveToFinalStepDebugState() {
        if (getDebugstate() == 1) {
            try {
                getSoarPlanningCodelet().getJsoar().moveToFinalStep();
                txtPlanInput.setText(getSoarPlanningCodelet().getInputLinkAsString());
                txtPlanOutput.setText(getSoarPlanningCodelet().getOutputLinkAsString());
                setPhaseIndication();
                updateTreePanel();
                updateTreePanelCreatedPlans();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void stepDebugState() {
        if (getDebugstate() == 1) {
            try {
                getSoarPlanningCodelet().getJsoar().step();
                txtPlanInput.setText(getSoarPlanningCodelet().getInputLinkAsString());
                txtPlanOutput.setText(getSoarPlanningCodelet().getOutputLinkAsString());
                setPhaseIndication();
                updateTreePanel();
                updateTreePanelCreatedPlans();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setPhaseIndication() {
        if (getSoarPlanningCodelet().getPhase() == -1) {
            getCurrentPhase().setText("Current Phase: " + "<HALT>");
        } else if (getSoarPlanningCodelet().getPhase() == 0)
            getCurrentPhase().setText("Current Phase: " + "<INPUT>");
        else if (getSoarPlanningCodelet().getPhase() == 1)
            getCurrentPhase().setText("Current Phase: " + "<PROPOSE>");
        else if (getSoarPlanningCodelet().getPhase() == 2)
            getCurrentPhase().setText("Current Phase: " + "<DECISION>");
        else if (getSoarPlanningCodelet().getPhase() == 3)
            getCurrentPhase().setText("Current Phase: " + "<APPLY>");
        else if (getSoarPlanningCodelet().getPhase() == 4)
            getCurrentPhase().setText("Current Phase: " + "<OUTPUT>");
        else if (getSoarPlanningCodelet().getPhase() == 5)
            getCurrentPhase().setText("Current Phase: " + "<HALT>");
    }


    public TreeModel createTreeModel(List<Identifier> wo) {
        DefaultMutableTreeNode root = TreeViewerUtil.addRootNode("Root");
        for (Identifier ii : wo) {
            DefaultMutableTreeNode o = TreeViewerUtil.addIdentifier(ii, "State");
            root.add(o);
        }
        TreeModel tm = new DefaultTreeModel(root);
        return (tm);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jspPlanSubsystem = new JSplitPane();
        pnPlanSubsystem = new JPanel();
        tpPlanInput = new JTabbedPane();
        spPlanInput = new JScrollPane();
        txtPlanInput = new JTextArea();
        tpPlanOutput = new JTabbedPane();
        spPlanOutput = new JScrollPane();
        txtPlanOutput = new JTextArea();
        spCreatedPlans = new JScrollPane();
        jToolBar1 = new JToolBar();
        startstop = new JButton();
        mstep = new JButton();
        step = new JButton();
        txtCurrentPhase = new JTextField();
        jspPlanDebugger = new JSplitPane();
        tpPlanOperators = new JTabbedPane();
        spPlanOperators = new JScrollPane();
        tpPlanWme = new JTabbedPane();
        spPlanSubsystem = new JScrollPane();

        setLayout(new java.awt.GridBagLayout());

        jspPlanSubsystem.setOrientation(JSplitPane.VERTICAL_SPLIT);

        pnPlanSubsystem.setMinimumSize(new java.awt.Dimension(0, 0));
        pnPlanSubsystem.setLayout(new java.awt.GridBagLayout());

        tpPlanInput.setMinimumSize(new java.awt.Dimension(0, 0));

        txtPlanInput.setEditable(false);
        txtPlanInput.setColumns(20);
        txtPlanInput.setRows(5);
        spPlanInput.setViewportView(txtPlanInput);

        tpPlanInput.addTab("Input", spPlanInput);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        pnPlanSubsystem.add(tpPlanInput, gridBagConstraints);

        tpPlanOutput.setMinimumSize(new java.awt.Dimension(0, 0));

        txtPlanOutput.setEditable(false);
        txtPlanOutput.setColumns(20);
        txtPlanOutput.setRows(5);
        spPlanOutput.setViewportView(txtPlanOutput);

        tpPlanOutput.addTab("Output", spPlanOutput);
        tpPlanOutput.addTab("Plan Selection Codelet", spCreatedPlans);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        pnPlanSubsystem.add(tpPlanOutput, gridBagConstraints);

        jToolBar1.setRollover(true);

        startstop.setIcon(new ImageIcon(getClass().getClassLoader().getResource("pause-icon.png"))); // NOI18N
        startstop.setToolTipText("Play/Pause");
        startstop.setFocusable(false);
        startstop.setHorizontalTextPosition(SwingConstants.CENTER);
        startstop.setVerticalTextPosition(SwingConstants.BOTTOM);
        startstop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startstopActionPerformed(evt);
            }
        });
        jToolBar1.add(startstop);

        mstep.setIcon(new ImageIcon(getClass().getClassLoader().getResource("skip-forward-icon.png"))); // NOI18N
        mstep.setToolTipText("micro-step");
        mstep.setEnabled(false);
        mstep.setFocusable(false);
        mstep.setHorizontalTextPosition(SwingConstants.CENTER);
        mstep.setVerticalTextPosition(SwingConstants.BOTTOM);
        mstep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mstepActionPerformed(evt);
            }
        });
        jToolBar1.add(mstep);

        step.setIcon(new ImageIcon(getClass().getClassLoader().getResource("forward-icon.png"))); // NOI18N
        step.setToolTipText("step");
        step.setEnabled(false);
        step.setFocusable(false);
        step.setHorizontalTextPosition(SwingConstants.CENTER);
        step.setVerticalTextPosition(SwingConstants.BOTTOM);
        step.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stepActionPerformed(evt);
            }
        });
        jToolBar1.add(step);

        txtCurrentPhase.setText("Current Phase: <HALT>");
        jToolBar1.add(txtCurrentPhase);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnPlanSubsystem.add(jToolBar1, gridBagConstraints);

        jspPlanSubsystem.setLeftComponent(pnPlanSubsystem);

        jspPlanDebugger.setDividerLocation(200);
        jspPlanDebugger.setMinimumSize(new java.awt.Dimension(0, 0));

        tpPlanOperators.addTab("Operators", spPlanOperators);

        jspPlanDebugger.setRightComponent(tpPlanOperators);

        tpPlanWme.addTab("WMEs", spPlanSubsystem);

        jspPlanDebugger.setLeftComponent(tpPlanWme);

        jspPlanSubsystem.setRightComponent(jspPlanDebugger);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.weighty = 0.1;
        add(jspPlanSubsystem, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void startstopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startstopActionPerformed
        if (getDebugstate() == 0) {
            startDebugState();
        } else {
            stopDebugState();
        }
    }//GEN-LAST:event_startstopActionPerformed

    private void mstepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mstepActionPerformed
        stepDebugState();
    }//GEN-LAST:event_mstepActionPerformed

    private void stepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stepActionPerformed
        moveToFinalStepDebugState();
    }//GEN-LAST:event_stepActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JToolBar jToolBar1;
    private JSplitPane jspPlanDebugger;
    private JSplitPane jspPlanSubsystem;
    private JButton mstep;
    private JPanel pnPlanSubsystem;
    private JScrollPane spCreatedPlans;
    private JScrollPane spPlanInput;
    private JScrollPane spPlanOperators;
    private JScrollPane spPlanOutput;
    private JScrollPane spPlanSubsystem;
    private JButton startstop;
    private JButton step;
    private JTabbedPane tpPlanInput;
    private JTabbedPane tpPlanOperators;
    private JTabbedPane tpPlanOutput;
    private JTabbedPane tpPlanWme;
    private JTextField txtCurrentPhase;
    private JTextArea txtPlanInput;
    private JTextArea txtPlanOutput;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the threadPlans
     */
    public Thread getThreadPlans() {
        return threadPlans;
    }

    /**
     * @param threadPlans the threadPlans to set
     */
    public void setThreadPlans(Thread threadPlans) {
        this.threadPlans = threadPlans;
    }

    /**
     * @return the bStopRefresh
     */
    public boolean isbStopRefresh() {
        return bStopRefresh;
    }

    /**
     * @param bStopRefresh the bStopRefresh to set
     */
    public void setbStopRefresh(boolean bStopRefresh) {
        this.bStopRefresh = bStopRefresh;
    }

    /**
     * @return the soarPlanningCodelet
     */
    public synchronized JSoarCodelet getSoarPlanningCodelet() {
        return soarPlanningCodelet;
    }

    /**
     * @param soarPlanningCodelet the soarPlanningCodelet to set
     */
    public synchronized void setSoarPlanningCodelet(JSoarCodelet soarPlanningCodelet) {
        this.soarPlanningCodelet = soarPlanningCodelet;
    }

    public Mind getMind() {
        return mind;
    }

    public void setMind(Mind mind) {
        this.mind = mind;
    }

    public synchronized long getRefreshTime() {
        return refreshTime;
    }

    public synchronized void setRefreshTime(long refreshTime) {
        this.refreshTime = refreshTime;
    }

    public ImageIcon getPauseIcon() {
        return pauseIcon;
    }

    public void setPauseIcon(ImageIcon pauseIcon) {
        this.pauseIcon = pauseIcon;
    }

    public ImageIcon getPlayIcon() {
        return playIcon;
    }

    public void setPlayIcon(ImageIcon playIcon) {
        this.playIcon = playIcon;
    }

    public int getDebugstate() {
        return debugstate;
    }

    public void setDebugstate(int debugstate) {
        this.debugstate = debugstate;
    }


    public JTextField getCurrentPhase() {
        return txtCurrentPhase;
    }

    public void setCurrentPhase(JTextField currentPhase) {
        this.txtCurrentPhase = currentPhase;
    }

    /**
     * @return the planSelectionCodelet
     */
    public PlanSelectionCodelet getPlanSelectionCodelet() {
        return planSelectionCodelet;
    }

    /**
     * @param planSelectionCodelet the planSelectionCodelet to set
     */
    public void setPlanSelectionCodelet(PlanSelectionCodelet planSelectionCodelet) {
        this.planSelectionCodelet = planSelectionCodelet;
    }
}
