//package dbms;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.RandomAccessFile;
//import java.util.StringTokenizer;
//import java.util.Vector;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
//class Node{
//    Node(String name,String dataType,int size){
//        this.name = name;
//        this.dataType = dataType;
//        this.size = size;
//        
//    }
//    Node(String str){
//        StringTokenizer st = new StringTokenizer(str," ");
//        this.name = st.nextToken();
//        this.dataType = st.nextToken();
//        this.size = Integer.parseInt(st.nextToken());
//     }
//    
//    String name;
//    String dataType;
//    int size;
//}
//class Header{
//    Header(){
//        pv = new Vector();
//        size = 0;
//        length = 0;
//    }
//    void append(Node anode){
//        pv.addElement(anode);
//        size = size + anode.size;
//        length = length + 1;
//    }
//    Node elementAt(int index){
//        return (Node)pv.elementAt(index);
//    }
//    String getAttributeName(int index){
//        return elementAt(index).name;
//    }
//    String getAttributeType(int index){
//        return elementAt(index).dataType;
//    }
//    int getAttributeSize(int index){
//        return elementAt(index).size;
//    }
//    void setAttribute(int index,Node value){
//        pv.set(index, value);
//    }
//    String getTableName(){
//        return tableName;
//    }
//    int size;
//    int length;
//    Vector pv ;
//    String tableName;
//        
//}
//
////存放元祖中的数据
//class NodeData{   
//    Object data[];
//    NodeData(int num){
//        //num为属性的个数
//        data = new Object[num];
//    }
//    NodeData(String s){
//        StringTokenizer st = new StringTokenizer(s,", ");
//        int num = st.countTokens();
//        data = new Object[num];
//        for(int i = 0;i < num;i++){
//            data[i] = st.nextToken();
//        }
//    }   
//    Object get(int index){
//        return data[index];
//    }
//    void setAttribute(int index,Object value){
//        data[index] = value;
//    }
//}
//class Table{  
//    Table(){
//        pvData = new Vector();
//        headr = new Header();
//    }
//    Table(String fileName){//将文件形式的表创建一个Table对象
//        pvData = new Vector();
//        headr = new Header();
//        headr.tableName = fileName;
//        try {
//            RandomAccessFile rf = new RandomAccessFile(fileName+".txt", "rw");
//            long filePoint = 0;
//            long fileLength = rf.length();
//            String s = rf.readLine();
//            StringTokenizer st = new StringTokenizer(s," ");
//            int length = Integer.parseInt(st.nextToken());
//            rf.readLine();
//            for(int i = 0;i < length;i++){
//                headr.append(new Node(rf.readLine()));
//            }
//            filePoint = rf.getFilePointer();
//            while(filePoint < fileLength){
//                String ss = rf.readLine() ;
//                if(!ss.equals(" "))
//                    pvData.addElement(new NodeData(ss));
//                filePoint = rf.getFilePointer();
//            }
//            rf.close();
//        } catch (FileNotFoundException ex) {
//                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
//        }
//   } 
//    NodeData getData(int index){
//        return (NodeData)pvData.elementAt(index);
//    }
//     void insertByLine(String ins){
//         //值1，值2，。。。
//        //insert into 表名 values(值1，值2，。。。）；
//        StringTokenizer st = new StringTokenizer(ins," (),'");
//            while(st.hasMoreTokens()){ 
//                NodeData nodedata = new NodeData(headr.length);
//                for(int i = 0;i < headr.length;i++)
//                   nodedata.setAttribute(i,st.nextToken());
//                pvData.addElement(nodedata);
//            }
//    }    
//    
//    void writeIntoTxt(String fileName){
//        try {
//            RandomAccessFile rf = new RandomAccessFile(fileName + ".txt", "rw");
//            rf.writeBytes(headr.length + " " + headr.size + "\r\n");
//            rf.writeBytes("AttributeName     AttributeType     AttributeSize  \r\n");
//            for(int i=0;i<headr.length;i++){
//                 rf.writeBytes(
//                         headr.getAttributeName(i) + "    " +
//                         headr.getAttributeType(i)+"    "+
//                         headr.getAttributeSize(i)+"\r\n"
//                         );
//            }
//            for(int i = 0;i < pvData.size();i++){
//                NodeData nd = getData(i);
//                for(int j = 0;j < nd.data.length;j++)
//                     rf.writeBytes(nd.get(j) + "  ");
//                rf.writeBytes(" \r\n ");
//            }
//            rf.close();
//        } catch (FileNotFoundException ex) {
//                Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(Table.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }   
//    void print(){
//        for(int i = 0;i < headr.length;i++){
//            System.out.format("%-20s",headr.getAttributeName(i));
//        }
//        System.out.println();
//        for(int i = 0;i < pvData.size();i++){
//            NodeData nd = getData(i);
//            for(int j = 0;j < nd.data.length;j++)
//                 System.out.format("%-20s",nd.get(j));
//            System.out.println();
//        }
//         System.out.println();
//    }
//    int getAttributeNumber(){
//        return headr.length;
//    }
//     String getAttributeName(int index){
//        return headr.elementAt(index).name;
//    }
//    String getAttributeType(int index){
//        return headr.elementAt(index).dataType;
//    }
//    int getAttributeSize(int index){
//        return headr.elementAt(index).size;
//    }
//    void setAttribute(int index,Node value){
//        headr.pv.set(index, value);
//    }
//    String getTableName(){
//        return headr.getTableName();
//    }
//    Header headr;
//    Vector pvData ;
//    int attributeNumber;
//    
//}
//class TableFunction{
//    Table executeStatement(String state){
//        StringTokenizer st = new StringTokenizer(state," (),'");
//	String ss = st.nextToken();
//        if(ss.equals("create"))
//            return creatTable(state);
//        else if(ss.equals("select"))
//            return selectTable(state);
//        else if(ss.equals("insert"))
//            return insertIntoTable(state);
//        
//        
//        return new Table();
//    }
//    Table insertIntoTable(String ins){
//        //insert into 表名 values(值1，值2，。。。）；
//        StringTokenizer st = new StringTokenizer(ins," ,()''");
//        st.nextToken();
//        st.nextToken();
//        String tabname = st.nextToken();
//        Table tab = new Table(tabname);
//        if("values".equals(st.nextToken()))
//            while(st.hasMoreTokens()){ 
//                NodeData nodedata = new NodeData(tab.headr.length);
//                for(int i = 0;i < tab.headr.length;i++)
//                   nodedata.setAttribute(i,st.nextToken());
//                tab.pvData.addElement(nodedata);
//            }
//        else if("as".equals(st.nextToken())){
////            Table 查询结果表名 = new Table();
////            for(int i=0;i<查询结果表名.pvData.size();i++)
////                pvData.addElement(查询结果表名.pvData.elementAt(i));
//        }
//        return tab;  
//    }
//    void printByTable(Table tab){
//        Header headr = tab.headr;
//        for(int i = 0;i < headr.length;i++){
//             System.out.format("%-20s",headr.getAttributeName(i));
//        }
//        System.out.println();
//        for(int i = 0;i < tab.pvData.size();i++){
//            NodeData nd = tab.getData(i);
//            for(int j = 0;j < nd.data.length;j++)
//                 System.out.format("%-20s",nd.get(j));
//            System.out.println();
//        }
//         System.out.println();
//    }
//    Table creatTable(String tab){
//        //create table 表名（
//        //  属性 数据类型 size ， 
//        Table table = new Table();
//        StringTokenizer st = new StringTokenizer(tab,"  ,()");
//        st.nextToken();st.nextToken();
//        table.headr.tableName = st.nextToken();
//        while(st.hasMoreTokens()){ 
//            Node node = new Node(
//                    st.nextToken(),
//                    st.nextToken(),
//                    Integer.parseInt(st.nextToken())
//                    );
//	    table.headr.append(node);
//	}
//        table.pvData = new Vector();
//        return table;
//    }
//    Table selectTable(String sel){
//        // select * from 表名 
//        //where 条件 /////
//        StringTokenizer st = new StringTokenizer(sel," ,()");
//        st.nextToken();
//        if("*".equals(st.nextToken())){
//            st.nextToken();
//            String tabfile = st.nextToken();
//            return new Table(tabfile);
//        }
//        else{
//            return  new Table();
//        }
//    } 
//}
//public class DBMS {
//    public static void main(String[] args) {
//        TableFunction tf = new TableFunction();
//        Table tab = tf.creatTable("create table qq( name String(10),"
//                + "age int 10)");
//        //"insert into qq values('liu'，20）"
//        tab.insertByLine("('liu',20)");
//        tab.insertByLine("('hai',21)");
//        tab.print();
//        tab.writeIntoTxt("1");
//        tf.selectTable("select * from qq").print();
//        tf.executeStatement("select * from qq").print();
//        tab = tf.executeStatement("insert into qq values('long',23)");
//        tab.print();
//        tab.writeIntoTxt("1");
//        
//    }
//}
