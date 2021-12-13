/**
 * 
 */
package br.unicamp.cst.util.bindings.soar;

import br.unicamp.cst.bindings.soar.JSoarCodelet;
import br.unicamp.cst.util.viewer.bindings.soar.SOARInspector;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;

/**
 * @author gudwin
 *
 */
public class SOARInspectorTest {

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
        jSoarCodelet.initSoarPlugin("testAgent", new File(soarRulesPath), false);
        //JSoarCodelet soarCodelet = new TestJSoarCodelet(soarRulesPath);
        SOARInspector si = new SOARInspector(jSoarCodelet);
        si.setVisible(true);	
        
        Thread.sleep(60000);
        
        si.setVisible(false);	
    }

}


