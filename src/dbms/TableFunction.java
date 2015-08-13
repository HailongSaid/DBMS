/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbms;

import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author Administrator
 */
public class TableFunction{
    Table executeStatement(String state){
        StringTokenizer st = new StringTokenizer(state," /(/),'");
	String ss = st.nextToken();
        if(ss.equals("create"))
            return creatTable(state);
        else if(ss.equals("select"))
            return selectTable(state);
        else if(ss.equals("insert"))
            return insertIntoTable(state);
        
        
        return new Table();
    }
    Table insertIntoTable(String ins){
        //insert into 表名 values(值1，值2，。。。）；
        StringTokenizer st = new StringTokenizer(ins," ,/(/)''");
        st.nextToken();
        st.nextToken();
        String tabname = st.nextToken();
        Table tab = new Table(tabname);
        if("values".equals(st.nextToken()))
            while(st.hasMoreTokens()){ 
                NodeData nodedata = new NodeData(tab.headr.attributeNumber);
                for(int i = 0;i < tab.headr.attributeNumber;i++)
                   nodedata.setData(i,st.nextToken());
                tab.pvData.addElement(nodedata);
            }
        else if("as".equals(st.nextToken())){
//            Table 查询结果表名 = new Table();
//            for(int i=0;i<查询结果表名.pvData.size();i++)
//                pvData.addElement(查询结果表名.pvData.elementAt(i));
        }
        return tab;  
    }
    void printByTable(Table tab){
        Header headr = tab.headr;
        for(int i = 0;i < headr.attributeNumber;i++){
             System.out.format("%-20s",headr.getAttributeName(i));
        }
        System.out.println();
        for(int i = 0;i < tab.pvData.size();i++){
            NodeData nd = tab.getData(i);
            for(int j = 0;j < nd.data.length;j++)
                 System.out.format("%-20s",nd.get(j));
            System.out.println();
        }
         System.out.println();
    }
    Table creatTable(String tab){
        //create table 表名（
        //  属性 数据类型 size ， 
        Table table = new Table();
        StringTokenizer st = new StringTokenizer(tab," ,/(/)");//,()
        st.nextToken();
        st.nextToken();
        table.headr.tableName = st.nextToken();
        System.out.println(table.headr.tableName);
        while(st.hasMoreTokens()){ 
            Node node = new Node(
                    st.nextToken(),
                    st.nextToken(),
                    Integer.parseInt(st.nextToken())
                    );
	    table.headr.append(node);
	}
        table.pvData = new Vector();
        return table;
    }
    Table selectTable(String sel){
        // select * from 表名 
        //where 条件 /////
        StringTokenizer st = new StringTokenizer(sel," ,/(/)");
        st.nextToken();
        if("*".equals(st.nextToken())){
            st.nextToken();
            String tabfile = st.nextToken();
            return new Table(tabfile + ".txt");
        }
        else{
            return  new Table();
        }
    } 
}