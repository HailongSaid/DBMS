/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dbms;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Administrator
 */
public class Table{  
    Table(){
        pvData = new Vector();
        headr = new Header();
    }
    Table(Table tab){
        pvData = new Vector(tab.pvData);
//        pvData = tab.pvData;
        headr = new Header();
        headr = tab.headr;
        attributeNumber = tab.attributeNumber;
    }
    //将文件形式的表创建一个Table对象
    Table(String fileName){
        pvData = new Vector();
        headr = new Header();
        try {
            RandomAccessFile rf = new RandomAccessFile(fileName, "rw");
            long filePoint = 0;
            long fileLength = rf.length();
            headr.tableName = rf.readLine();
            String s = rf.readLine();
            filePoint = rf.getFilePointer();
            StringTokenizer st = new StringTokenizer(s," ");
            int length = Integer.parseInt(st.nextToken());
            rf.readLine();
            for(int i = 0;i < length;i++){
                headr.append(new Node(rf.readLine()));
            }
            filePoint = rf.getFilePointer();
            while(filePoint < fileLength){
                String ss = rf.readLine().trim() ;
                if(!ss.equals(""))
                    pvData.addElement(new NodeData(ss));
                filePoint = rf.getFilePointer();
            }
            rf.close();
        } catch (FileNotFoundException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
   } 
   void insertByLine(String ins){
         //值1，值2，。。。
        //insert into 表名 values(值1，值2，。。。）；
        StringTokenizer st = new StringTokenizer(ins," (),'");
            while(st.hasMoreTokens()){ 
                NodeData nodedata = new NodeData(headr.attributeNumber);
                for(int i = 0;i < headr.attributeNumber;i++)
                   nodedata.setData(i,st.nextToken());
                pvData.addElement(nodedata);
            }
    }    
    
    void writeIntoTxt(String fileName){
        try {
            RandomAccessFile rf = new RandomAccessFile(fileName, "rw");
            rf.writeBytes(headr.tableName + "\r\n");
            rf.writeBytes(headr.attributeNumber + " " + headr.size + "\r\n");
            rf.writeBytes("AttributeName     AttributeType     AttributeSize  \r\n");
            for(int i=0;i<headr.attributeNumber;i++){
                 rf.writeBytes(
                         headr.getAttributeName(i) + "    " +
                         headr.getAttributeType(i)+"    "+
                         headr.getAttributeSize(i)+"\r\n"
                         );
            }
            for(int i = 0;i < pvData.size();i++){
                NodeData nd = getData(i);
                for(int j = 0;j < nd.data.length;j++)
                     rf.writeBytes(nd.get(j) + "  ");
                rf.writeBytes(" \r\n ");
            }
            rf.close();
        } catch (FileNotFoundException ex) {
                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    void print(){
        for(int i = 0;i < headr.attributeNumber;i++){
            System.out.format("%-20s",headr.getAttributeName(i));
        }
        System.out.println();
        for(int i = 0;i < pvData.size();i++){
            NodeData nd = getData(i);
            for(int j = 0;j < nd.data.length;j++)
                 System.out.format("%-20s",nd.get(j));
            System.out.println();
        }
         System.out.println();
    }
    void add(int index,NodeData data){
        pvData.add(index, data);
    }
    void deleteDataByRow(int index){
        pvData.removeElementAt(index);
    }
    void replaceData(int index,NodeData data){
         pvData.removeElementAt(index);
        pvData.add(index, data);
    }
    void setData(int row,int col,Object data){
        NodeData nodedata = (NodeData)pvData.elementAt(row);
        if(getAttributeType(col).equalsIgnoreCase("int"))
            nodedata.setData(col,Integer.valueOf(data.toString()));
        else
            nodedata.setData(col,data);
    }
    NodeData getData(int index){
        return (NodeData)pvData.elementAt(index);
    }
    Object[] getDataByRow(int index){
       NodeData nodedata = (NodeData)pvData.elementAt(index);
       Object data[] = new Object[getRowNumber()];
       for(int i = 0;i < getRowNumber();i++)
           data[i] = nodedata.get(i);
        return data;
    }
     Object getData(int row,int col){
       NodeData nodedata = (NodeData)pvData.elementAt(row);
       return nodedata.get(col);
    }
    int getRowNumber(){
        return pvData.size();
    }
    int getAttributeNumber(){
        return headr.attributeNumber;
    }
    int getAttributeIndex(String attributeName){
       for(int i = 0;i < getAttributeNumber();i++){
           if(getAttributeName(i).equalsIgnoreCase(attributeName))
               return i;
       }
       return -1;
   }
    String getAttributeName(int index){
        return headr.elementAt(index).name;
    }
    String getAttributeType(int index){
        return headr.elementAt(index).dataType;
    }
    int getAttributeSize(int index){
        return headr.elementAt(index).size;
    }
    void setAttribute(int index,Node value){
        headr.pv.set(index, value);
    }
    String getTableName(){
        return headr.getTableName();
    }
    Header headr;
    Vector pvData ;
    int attributeNumber;
    
}