/*******************************************************************************
 * Copyright (c) 2012  DCA-FEEC-UNICAMP
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v3
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 * 
 * Contributors to this module:
 *     S. M. de Paula and R. R. Gudwin 
 ******************************************************************************/

package br.unicamp.cst.util.viewer;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 * @author Suelen Mapa
 */
public class TreeElement {

    private String name;
    private String value="";
    private Color color;
    private Object element;
    private int icon_type;
    private int id_node = 0;
    private static int ncode=0;
    
    public static final int NODE_NORMAL = 1;
    public static final int NODE_CHANGE = 2;
    public static final int NODE_EXCLUSION = 3;
    public static final int NODE_CREATION = 4;

    public static final int ICON_OBJECT = 2;
    public static final int ICON_CONFIGURATION = 1;
    public static final int ICON_COMPOSITE = 2;
    public static final int ICON_AGGREGATE = 3;
    public static final int ICON_PROPERTY = 4;
    public static final int ICON_QUALITYDIM = 5;
    public static final int ICON_VALUE = 6;
    public static final int ICON_MIND = 7;
    public static final int ICON_CODELET = 8;
    public static final int ICON_CODELETS = 9;
    public static final int ICON_MEMORY = 10;
    public static final int ICON_MEMORIES = 11;
    public static final int ICON_CONTAINER = 12;
    public static final int ICON_MO = 13;
    public static final int ICON_INPUT = 14;
    public static final int ICON_OUTPUT = 15;
    public static final int ICON_BROADCAST = 16;
    public static final int ICON_AFFORDANCE = 17;
    public static final int ICON_OBJECT2 = 18;
    public static final int ICON_OBJECT3 = 19;
    public static final int ICON_TIME = 20;
    public static final int ICON_PROPERTYCATEGORY = 21;
    public static final int ICON_OBJECTCATEGORY = 22;
    public static final int ICON_EPISODECATEGORY = 23;
    public static final int ICON_PROPERTYPOSSIBILITY = 24;
    public static final int ICON_OBJECTPOSSIBILITY = 25;
    public static final int ICON_EPISODEPOSSIBILITY = 26;
    public static final int ICON_OBJECTEXISTENCE = 27;
    public static final int ICON_ACTIONPOSSIBILITY = 28;
    public static final int ICON_ACTIONEXISTENCE = 29;
    public static final int ICON_ACTIONCATEGORY = 30;
    public static final int ICON_GOAL = 31;
    
    public static Object[][] table = { // Table with pairs icon/extended text mode
        {ICON_OBJECTEXISTENCE, 0},   // 0 AbstractObject - Existence
        {ICON_PROPERTY, 1},          // 1 Property - Existence
        {ICON_OBJECT2, 2},           // 2 Link
        {ICON_QUALITYDIM, 1},        // 3 Quality Dimension
        {ICON_AFFORDANCE, 0},        // 4 Episode - Existence
        {ICON_COMPOSITE, 0},         // 5 Composite
        {ICON_AGGREGATE, 0},         // 6 Agregate
        {ICON_CONFIGURATION, 0},     // 7 Configuration
        {ICON_TIME, 0},              // 8 TimeStep
        {ICON_PROPERTYCATEGORY, 1},  // 9 Property - Category
        {ICON_OBJECTCATEGORY, 0},    // 10 AbstractObject - Category
        {ICON_EPISODECATEGORY, 0},   // 11 Episode - Category
        {ICON_PROPERTYPOSSIBILITY, 1}, // 12 Property - Possibility
        {ICON_OBJECTPOSSIBILITY, 0},   // 13 AbstractObject - Possibility
        {ICON_EPISODEPOSSIBILITY, 0},  // 14 Episode - Possibility
        {ICON_ACTIONPOSSIBILITY, 0},   // 15 Action - Possibility
        {ICON_ACTIONEXISTENCE, 0},     // 16 Action - Existence
        {ICON_ACTIONCATEGORY,0},       // 17 Action - Category
        {ICON_GOAL,0},                 // 18 Goal
    };
    
    public static Object[][] icon_table = {
        {ICON_COMPOSITE, "object.png"},
        {ICON_AGGREGATE, "object2.gif"},
        {ICON_PROPERTY, "property.png"},
        {ICON_QUALITYDIM, "qualityDim.png"},
        {ICON_VALUE, "value.png"},
        {ICON_MIND, "mind.png"},
        {ICON_CODELET, "codelet.png"},
        {ICON_CODELETS, "codelets.png"},
        {ICON_MEMORY, "memory.png"},
        {ICON_MEMORIES, "memories.png"},
        {ICON_CONTAINER, "container.png"},
        {ICON_MO, "mo.png"},
        {ICON_INPUT, "input.png"},
        {ICON_OUTPUT, "output.png"},
        {ICON_BROADCAST, "broadcast.png"},
        {ICON_AFFORDANCE, "codelet2.png"},
        {ICON_OBJECT2, "object2.png"}, 
        {ICON_OBJECT3, "object3.png"},
        {ICON_CONFIGURATION, "configuration.png"},
        {ICON_TIME, "time-machine.png"},
        {ICON_PROPERTYCATEGORY, "propertyCategory.png"},
        {ICON_OBJECTCATEGORY, "objectCategory.png"},
        {ICON_EPISODECATEGORY, "episodeCategory.png"},
        {ICON_PROPERTYPOSSIBILITY, "property-p.png"},
        {ICON_OBJECTPOSSIBILITY, "object-p.png"},
        {ICON_EPISODEPOSSIBILITY, "episode-p.png"},
        {ICON_OBJECTEXISTENCE, "object-e.png"},
        {ICON_ACTIONPOSSIBILITY, "action-p.png"},
        {ICON_ACTIONEXISTENCE, "action-e.png"},
        {ICON_ACTIONCATEGORY, "action-l.png"},
        {ICON_GOAL, "goal.png"}
    };
   
    public TreeElement(String name, int node_type, Object element, int typeIcon) {
        //this(name,node_type,element,typeIcon,0);
        this(name,node_type,element,typeIcon,ncode++);
    }
    
    public TreeElement(String name, String value, int node_type, Object element, int typeIcon) {
        //this(name,node_type,element,typeIcon,0);
        this(name, node_type,element,typeIcon,ncode++);
        this.value = value;
    }
    
    public static void reset() {
        ncode = 0;
    }
    
     public TreeElement(String name, int node_type, Object element, int typeIcon, int id) {
    
        setColor(node_type);
        setIcon(typeIcon);
        setId_node(id);
        //setName(name+"_"+getId_node());
        //setName(name+" ["+getId_node()+"]");
         setName(name);        
        this.element = element;
        this.icon_type = typeIcon;
    }
    
    
     public String getNamePlusValuePlusId() {
        return getName()+ getValue()+" ["+getId_node()+"]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(int node_type) {
        switch (node_type) {
            case NODE_NORMAL:
                color = Color.BLACK;
                break;
            case NODE_CHANGE:
                color = Color.ORANGE;
                break;
            case NODE_EXCLUSION:
                color = Color.RED;
                break;
            case NODE_CREATION:
                color = Color.GREEN;
                break;

        }
    }

    public void setIcon(int icon_type) {
        this.icon_type = icon_type;
    }

    public int getIcon() {
        return icon_type;
    }

    public Object getElement() {
        return element;
    }
    
    public void setElement(Object el) {
        this.element = el;
    } 

    public int getId_node() {
        return id_node;
    }

    public void setId_node(int id_node) {
        this.id_node = id_node;
    }
    
    public static int iconByType(int type) {
        switch(type) {
               case 0: return(ICON_OBJECTEXISTENCE);
               case 1: return(ICON_PROPERTY);
               case 2: return(ICON_OBJECT2);
               case 3: return(ICON_QUALITYDIM);
               case 4: return(ICON_AFFORDANCE);
               case 5: return(ICON_COMPOSITE);
               case 6: return(ICON_AGGREGATE);
               case 7: return(ICON_CONFIGURATION);
               case 8: return(ICON_TIME);
               case 9: return(ICON_PROPERTYCATEGORY);
               case 10: return(ICON_OBJECTCATEGORY);
               case 11: return(ICON_EPISODECATEGORY);
               case 12: return(ICON_PROPERTYPOSSIBILITY);
               case 13: return(ICON_OBJECTPOSSIBILITY);
               case 14: return(ICON_EPISODEPOSSIBILITY);
               case 15: return(ICON_ACTIONPOSSIBILITY);
               case 16: return(ICON_ACTIONEXISTENCE);
               case 17: return(ICON_ACTIONCATEGORY);
               case 18: return(ICON_GOAL);
               default: return(ICON_OBJECT3);
           }
    }

    public static int getIdeaTypeFromIcon(int icon){
        int p = 0;
        for (int i=0; i<table.length; i++){
            if (table[i][0].equals(icon)) {
                break;
            }
            p++;
        }
        return p ;
    }
    
    
}
