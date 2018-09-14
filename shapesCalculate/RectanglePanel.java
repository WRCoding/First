package shapesCalculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class RectanglePanel extends AbstractPanel implements ActionListener,FocusListener {
    private JButton resultButton,clearButton;
    private JPanel leftpanel,rightpanel,buttonpanel;
    private JTextField widthText,heightText;
    private JTextField lengthText,areaText,inputText;
    private BoxPanel bpwidth,bpheight,bplength,bparea;

    public RectanglePanel(){
        setLayout(new GridLayout(1,2));
        rightpanel=new KeyJPanel(this);
        leftpanel=new JPanel();

        Box box=Box.createVerticalBox();
        bpwidth=new BoxPanel("请输入矩形的高： ",10);
        widthText=bpwidth.getJText();
        widthText.addFocusListener(this);

        bpheight=new BoxPanel("请输入矩形的高",10);
        heightText=bpheight.getJText();
        heightText.addFocusListener(this);

        buttonpanel=new JPanel();
        resultButton=new JButton("计算结果 ");
        resultButton.addActionListener(this);
        clearButton=new JButton("清空");
        clearButton.addActionListener(this);
        buttonpanel.add(resultButton);
        buttonpanel.add(clearButton);

        bplength=new BoxPanel("矩形的周长是： ",20);
        lengthText=bplength.getJText();
        bparea=new BoxPanel("矩形的面积是： ",20);
        areaText=bparea.getJText();

        box.add(bpwidth);
        box.add(bpheight);
        box.add(buttonpanel);
        box.add(bplength);
        box.add(bparea);
        leftpanel.add(box);

        add(leftpanel);
        add(rightpanel);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resultButton){
            try {
                double width=Double.parseDouble(widthText.getText());
                double height=Double.parseDouble(heightText.getText());
                lengthText.setText(""+2*(width+height));
                areaText.setText(""+width*height);
            }catch (NumberFormatException el){
                JOptionPane.showMessageDialog(this,"请正确输入数字","提示",JOptionPane.WARNING_MESSAGE);
            }
        }else if(e.getSource()==clearButton){
            widthText.setText(" ");
            heightText.setText(" ");
            lengthText.setText(" ");
            areaText.setText(" ");
        }
    }

    public void focusGained(FocusEvent e) {
        inputText=(JTextField) e.getSource();
    }

    public void focusLost(FocusEvent e) {

    }

    public JTextField getInputTextField() {
        return inputText;
    }
}
