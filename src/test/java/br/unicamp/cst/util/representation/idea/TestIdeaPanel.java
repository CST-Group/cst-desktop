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
package br.unicamp.cst.util.representation.idea;

import javax.swing.JFrame;

import br.unicamp.cst.representation.idea.Idea;
import br.unicamp.cst.util.viewer.representation.idea.IdeaPanel;
import org.junit.jupiter.api.Test;

public class TestIdeaPanel {
    
    IdeaPanel wmp;
    
    public Idea initialize() {
        Idea node = new Idea("Test","",0);
        node.add(new Idea("child1","",0));
        node.add(new Idea("child2","I2",0));
        node.add(new Idea("child3",3.1416d,1));
        node.add(new Idea("child3","I3",2));
        return(node);
    }
    
    @Test 
    public void testWMNode() {
        
        JFrame frame = new JFrame();
        frame.setSize(300,200);
        Idea node = initialize();
        System.out.println(node.toStringFull());
        wmp = new IdeaPanel(node,true);
        wmp.setOpaque(true); //content panes must be opaque
        frame.setContentPane(wmp);
        wmp.expandAllNodes();
        wmp.updateTree();
        frame.setVisible(true);
        
        try {
           Thread.sleep(2000);
        } catch (Exception e) {} 
        frame.setVisible(false);
    }
    
}    
