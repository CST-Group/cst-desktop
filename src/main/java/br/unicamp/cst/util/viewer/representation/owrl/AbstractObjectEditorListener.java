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
package br.unicamp.cst.util.viewer.representation.owrl;

import br.unicamp.cst.representation.owrl.AbstractObject;

/**
 *
 * @author rgudwin
 */
public interface AbstractObjectEditorListener {
    
    public void notifyRootChange(AbstractObject newAO);
    
}
