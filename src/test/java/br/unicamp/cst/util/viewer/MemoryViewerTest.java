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
package br.unicamp.cst.util.viewer;

import br.unicamp.cst.core.entities.MemoryObject;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.Random;

import br.unicamp.cst.support.TestComplexMemoryObjectInfo;
import org.junit.Test;

/**
 *
 * @author rgudwin
 */
public class MemoryViewerTest {
    
    @Test
    public void testMemoryInspector() {

        Robot rob = startRobot();
        rob.delay(1000);         //delay to let the application load
        rob.setAutoDelay(20); 
        TestComplexMemoryObjectInfo m = new TestComplexMemoryObjectInfo();
        m.complextest = new TestComplexMemoryObjectInfo();
        for (int i=0;i<3;i++)
            m.complextestarray[i] = new TestComplexMemoryObjectInfo();
        MemoryObject mo = new MemoryObject();
        mo.setType("TestObject");
        mo.setI(m);
        MemoryViewer mv = new MemoryViewer(mo);
        mv.setVisible(true);
        rob.delay(5000);
        float[] m2 = new float[256];
        Random n = new Random();
        for (int i=0;i<256;i++)
            m2[i] = n.nextFloat();
        mo.setI(m2);
        MemoryViewer mv2 = new MemoryViewer(mo);
        mv2.setVisible(true);
        rob.delay(5000);
        mv.setVisible(false);
        mv2.setVisible(false);
    }    
    
    Robot startRobot() {
        Robot rob=null;
        try
        {
            rob = new Robot();
        }
        catch (AWTException ex)
        {
            System.err.println("Can't start Robot: " + ex);
            System.exit(0);
        }
        return(rob);
    }
    
    
}
