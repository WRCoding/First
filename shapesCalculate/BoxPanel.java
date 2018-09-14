package shapesCalculate;

import javax.swing.*;
import java.awt.*;

public class BoxPanel extends JPanel {
    JTextField text;
    public BoxPanel(String s,int n){
        Box box=Box.createHorizontalBox();
        box.add(new JLabel(s));
        text=new JTextField(" ",n);
        text.setHorizontalAlignment(JTextField.RIGHT);
        text.setFont(new Font("Arial",Font.BOLD,15));
        box.add(text);
        add(box);
    }
    public JTextField getJText(){
        return text;
    }
}
