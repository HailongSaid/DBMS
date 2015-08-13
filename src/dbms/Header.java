/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbms;

import java.util.Vector;

/**
 *
 * @author Administrator
 */
public  class Header{
    Header(){
        pv = new Vector();
        size = 0;
        attributeNumber = 0;
    }
    void append(Node anode){
        pv.addElement(anode);
        size = size + anode.size;
        attributeNumber = attributeNumber + 1;
    }
    Node elementAt(int index){
        return (Node)pv.elementAt(index);
    }
    String getAttributeName(int index){
        return elementAt(index).name;
    }
    String getAttributeType(int index){
        return elementAt(index).dataType;
    }
    int getAttributeSize(int index){
        return elementAt(index).size;
    }
    void setAttribute(int index,Node value){
        pv.set(index, value);
    }
    String getTableName(){
        return tableName;
    }
    int size;
    int attributeNumber;
    Vector pv ;
    String tableName;
        
}
