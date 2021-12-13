/**
 * 
 */
package br.unicamp.cst.util.bindings.soar;

import br.unicamp.cst.bindings.soar.JSoarCodelet;
import br.unicamp.cst.util.viewer.bindings.soar.WorkingMemoryViewer;
import org.junit.Test;

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
        String soarRulesPath = "src/test/java/br/unicamp/cst/bindings/soar/smartCar.soar";
        //JSoarCodelet soarCodelet = new TestJSoarCodelet(soarRulesPath);
        jSoarCodelet.initSoarPlugin("testAgent", new File(soarRulesPath), false);
        WorkingMemoryViewer ov = new WorkingMemoryViewer("Teste",jSoarCodelet);
        ov.setVisible(true);
        ov.updateTree(jSoarCodelet.getJsoar().getStates());
        
        Thread.sleep(1000);
        
        ov.setVisible(false);
	}


}
