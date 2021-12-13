/**
 * 
 */
package br.unicamp.cst.util.bindings.soar;

import br.unicamp.cst.bindings.soar.JSoarCodelet;
import br.unicamp.cst.util.viewer.bindings.soar.WorkingMemoryViewer;
import org.junit.Test;

/**
 * @author rgudwin
 *
 */
public class WorkingMemoryViewerTest {
	
	@Test
	public void testWorkingMemoryViewer() throws InterruptedException {
        String soarRulesPath = "src/test/java/br/unicamp/cst/bindings/soar/smartCar.soar";
        JSoarCodelet soarCodelet = new TestJSoarCodelet(soarRulesPath);
        WorkingMemoryViewer ov = new WorkingMemoryViewer("Teste",soarCodelet);
        ov.setVisible(true);
        ov.updateTree(soarCodelet.getJsoar().getStates());
        
        Thread.sleep(1000);
        
        ov.setVisible(false);
	}

    private class TestJSoarCodelet extends JSoarCodelet {
        public TestJSoarCodelet(String soarRulesPath) {
        }

        @Override
        public void accessMemoryObjects() {

        }

        @Override
        public void calculateActivation() {

        }

        @Override
        public void proc() {

        }
    }
}
