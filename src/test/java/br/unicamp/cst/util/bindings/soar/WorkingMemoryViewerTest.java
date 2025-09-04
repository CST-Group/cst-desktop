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
package br.unicamp.cst.util.bindings.soar;

import br.unicamp.cst.bindings.soar.JSoarCodelet;
import br.unicamp.cst.util.viewer.bindings.soar.WorkingMemoryViewer;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author rgudwin
 *
 */
public class WorkingMemoryViewerTest {
    JSoarCodelet jSoarCodelet = new JSoarCodelet() {
        @Override
        public void accessMemoryObjects() {

        }

        @Override
        public void calculateActivation() {

        }

        @Override
        public void proc() {
            getJsoar().step();
        }
    };


	@Test
	public void testWorkingMemoryViewer() throws InterruptedException {
        String soarRulesPath = "src/test/resources/smartCar.soar";
        //JSoarCodelet soarCodelet = new TestJSoarCodelet(soarRulesPath);
        jSoarCodelet.initSoarPlugin("testAgent", new File(soarRulesPath), false);
        WorkingMemoryViewer ov = new WorkingMemoryViewer("Teste",jSoarCodelet);
        ov.setVisible(true);
        ov.updateTree(jSoarCodelet.getJsoar().getStates());
        
        Thread.sleep(1000);
        
        ov.setVisible(false);
	}


}
