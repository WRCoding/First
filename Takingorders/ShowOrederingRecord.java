package Takingorders;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.RandomAccessFile;

public class ShowOrederingRecord extends JDialog {
    private File file;
    private  JTextArea showArea;
    public ShowOrederingRecord(File file){
        this.file=file;
        showArea=new JTextArea(4,2);
        showArea.setFont(new Font("楷体",Font.BOLD,20));
        add(new JScrollPane(showArea),BorderLayout.CENTER);
        setBounds(200,200,300,400);
    }
    public void showRecord(){
        showArea.setText(null);
        showArea.append("---菜名--------------------价格---------------");
        double totalPrice=0;
        try {
            RandomAccessFile in=new RandomAccessFile(file,"r");
            String mealName=null;
            while((mealName=in.readUTF())!=null){
                showArea.append("\n"+mealName);
                String mealPrice=in.readUTF();
                showArea.append("                    "+mealPrice);
                totalPrice=totalPrice+Double.parseDouble(mealPrice);
            }
        }catch(Exception e){}
        showArea.append("\n-------------------------------------------");
        showArea.append("\n结账：               "+totalPrice);

    }
}
