/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbms;

import java.util.StringTokenizer;

/**
 *
 * @author Administrator
 */
public  class Node{
    Node(String name,String dataType,int size){
        this.name = name;
        this.dataType = dataType;
        this.size = size;
        
    }
    Node(String str){
        StringTokenizer st = new StringTokenizer(str," ");
        this.name = st.nextToken();
        this.dataType = st.nextToken();
        this.size = Integer.parseInt(st.nextToken());
     }
    
    String name;
    String dataType;
    int size;
}
