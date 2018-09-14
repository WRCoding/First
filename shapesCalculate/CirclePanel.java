package shapesCalculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class CirclePanel extends  AbstractPanel implements ActionListener,FocusListener {
    private JButton resultButton,clearButton;
    private JPanel leftpanel,rightpanel,buttonpanel;
    private JTextField radiusText;
    private JTextField lengthText,areaText,inputText;
    private BoxPanel bpradius,bplength,bparea;

    public CirclePanel(){
        setLayout(new GridLayout(1,2));
        rightpanel=new KeyJPanel(this);
        leftpanel=new JPanel();

        Box box=Box.createVerticalBox();
        bpradius=new BoxPanel("请输入圆的半径： ",10);
        radiusText=bpradius.getJText();
        radiusText.addFocusListener(this);

        resultButton=new JButton("计算结果");
        resultButton.addActionListener(this);
        clearButton=new JButton("清除");
        clearButton.addActionListener(this);
        buttonpanel=new JPanel();
        buttonpanel.add(resultButton);
        buttonpanel.add(clearButton);

        bplength=new BoxPanel("圆的周长： ",10);
        lengthText=bplength.getJText();
        bparea=new BoxPanel("圆的面积： ",10);
        areaText=bparea.getJText();

        box.add(bpradius);
        box.add(buttonpanel);
        box.add(bplength);
        box.add(bparea);

        leftpanel.add(box);

        add(leftpanel);
        add(rightpanel);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==resultButton){
            double radius=Double.parseDouble(radiusText.getText());
            double length=2* Math.PI*radius;
            double area=Math.PI*radius*radius;
            BigDecimal lengthbg = new BigDecimal(length).setScale(2,RoundingMode.UP);//保留double后面两位小数，四舍五入
            BigDecimal areabp = new BigDecimal(area).setScale(2,RoundingMode.UP);
            lengthText.setText(""+lengthbg.doubleValue());
            areaText.setText(""+ areabp.doubleValue());
        }else if(e.getSource()==clearButton){
            radiusText.setText(" ");
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
