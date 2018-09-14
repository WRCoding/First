package shapesCalculate;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class KeyJPanel extends JPanel implements ActionListener {
    JButton[] keyButton=new JButton[12];
    String[] num={"1","2","3","4","5","6","7","8","9","0",".","BackSpace"};
    AbstractPanel selectdPanel;
    JTextField inputText;

    public KeyJPanel(AbstractPanel selectdPanel){
        this.selectdPanel=selectdPanel;

        Border lb=BorderFactory.createLineBorder(Color.gray,2);
        setBorder(lb);
        setLayout(new GridLayout(4,3));

        for(int i=0;i<num.length;i++){
            keyButton[i]=new JButton(num[i]);
            keyButton[i].setFont(new Font("Arial",Font.BOLD,15));
            keyButton[i].addActionListener(this);
            add(keyButton[i]);
        }
    }

    public void actionPerformed(ActionEvent e) {
        JButton button=(JButton)e.getSource();
        inputText=selectdPanel.getInputTextField();
        inputNumber(inputText,button);
    }
    public void inputNumber(JTextField text,JButton button){
        String oldString=text.getText();
        if(oldString==null){
            text.setText(" ");
        }
        String subStr=oldString.substring(0,oldString.length()-1);
        String newString=button.getText();
        if(newString.equals("BackSpace")){
            text.setText(subStr);
        } else if(newString.equals(".")){
         text.setText(oldString+".");
        }else{
            text.setText(oldString+newString);
        }
    }
}
