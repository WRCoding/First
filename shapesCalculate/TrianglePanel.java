package shapesCalculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class TrianglePanel extends  AbstractPanel implements ActionListener,FocusListener {
    private JButton resultButton,clearButton;
    private JPanel leftpanel,rightpanel,buttonpanel;
    private JTextField sideAtext,sideBtext,sideCtext;
    private JTextField lengthText,areaText,inputText;
    private BoxPanel bpsideA,bpsiedB,bpsideC,bplength,bparea;

    public TrianglePanel(){
        setLayout(new GridLayout(1,2));
        rightpanel=new KeyJPanel(this);
        leftpanel=new JPanel();

        Box box=Box.createVerticalBox();
        bpsideA=new BoxPanel("请输入三角形的边A： ",10);
        sideAtext=bpsideA.getJText();
        sideAtext.addFocusListener(this);
        bpsiedB=new BoxPanel("请输入三角形的边B： ",10);
        sideBtext=bpsiedB.getJText();
        sideBtext.addFocusListener(this);
        bpsideC=new BoxPanel("请输入三角形的边C： ",10);
        sideCtext=bpsideC.getJText();
        sideCtext.addFocusListener(this);

        buttonpanel=new JPanel();
        resultButton=new JButton("计算结果是 ");
        resultButton.addActionListener(this);
        clearButton=new JButton("清除");
        clearButton.addActionListener(this);
        buttonpanel.add(resultButton);
        buttonpanel.add(clearButton);

        bplength=new BoxPanel("三角形的周长： ",10);
        lengthText=bplength.getJText();
        bparea=new BoxPanel("三角形的面积： ",10);
        areaText=bparea.getJText();

        box.add(bpsideA);
        box.add(bpsiedB);
        box.add(bpsideC);
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
             double sideA=Double.parseDouble(sideAtext.getText());
             double sideB=Double.parseDouble(sideBtext.getText());
             double sideC=Double.parseDouble(sideCtext.getText());
             if((sideA+sideB>sideC)&&(sideA+sideC>sideB)&&(sideB+sideC>sideA)){
                 double p=(sideA+sideB+sideC)/2.0;
                 lengthText.setText(""+2*p);
                 double area= Math.sqrt(p*(p-sideA)*(p-sideB)*(p-sideC));
                 areaText.setText(""+area);
             }else{
                 JOptionPane.showMessageDialog(this,"这构不成一个三角形","提示",JOptionPane.WARNING_MESSAGE);
             }
            }catch (NumberFormatException e1){
                JOptionPane.showMessageDialog(this,"请输入数字","提示",JOptionPane.WARNING_MESSAGE);
            }
        }
        if(e.getSource()==clearButton){
            sideAtext.setText(" ");
            sideBtext.setText(" ");
            sideCtext.setText(" ");
            lengthText.setText(" ");
            areaText.setText(" ");
        }
    }

    public void focusGained(FocusEvent e) {
            inputText=(JTextField)e.getSource();
    }

    public void focusLost(FocusEvent e) {

    }

    public JTextField getInputTextField() {
        return inputText;
    }
}
