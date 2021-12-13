/**
 * 
 */
package br.unicamp.cst.util.bindings.soar;

import br.unicamp.cst.bindings.soar.JSoarCodelet;
import br.unicamp.cst.util.viewer.bindings.soar.SOARInspector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author gudwin
 *
 */
public class SOARInspectorTest {
	
	@BeforeClass
    public static void beforeAllTestMethods() {
    }

	@AfterClass
    public static void afterAllTestMethods() {
    }
    
    @Test
    public void testSOARInspector() throws InterruptedException {
    	
    	//String soarRulesPath="src/test/java/br/unicamp/cst/bindings/soar/smartCar.soar";
        String soarRulesPath="src/test/resources/mac.soar";
        JSoarCodelet soarCodelet = new TestJSoarCodelet(soarRulesPath);
        SOARInspector si = new SOARInspector(soarCodelet);
        si.setVisible(true);	
        
        Thread.sleep(60000);
        
        si.setVisible(false);	
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
