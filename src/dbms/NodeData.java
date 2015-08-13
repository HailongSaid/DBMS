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
public class NodeData{   
    Object data[];
    NodeData(int num){
        //num为属性的个数
        data = new Object[num];
    }
    NodeData(String s){
        StringTokenizer st = new StringTokenizer(s,", ");
        int num = st.countTokens();
        data = new Object[num];
        for(int i = 0;i < num;i++){
            data[i] = st.nextToken();
        }
    }   
    Object get(int index){
        return data[index];
    }
    void setData(int index,Object value){
        data[index] = value;
    }
}
