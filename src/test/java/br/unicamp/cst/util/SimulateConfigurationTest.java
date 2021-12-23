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
package br.unicamp.cst.util;

import br.unicamp.cst.util.viewer.SimulateConfiguration;
import org.junit.Test;

/**
 * @author suelen
 *
 */
public class SimulateConfigurationTest {
	
	@Test
	public void testSimulateConfiguration() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
            	br.unicamp.cst.util.viewer.SimulateConfiguration simulateConfiguration = new SimulateConfiguration();
            	simulateConfiguration.setVisible(true);
            	
            	try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	
            	simulateConfiguration.setVisible(false);
            }
        });
	}

}
