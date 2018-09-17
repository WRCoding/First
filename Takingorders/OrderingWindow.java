package Takingorders;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class OrderingWindow extends JFrame implements ActionListener {
    JPanel pNorth,pCenter,pSouth;
    JButton meatButton,vegetarianButton,querenButton;
    JButton stapleFoodButton,soupAndPorridgeButton;
    JButton showButton,stopOrderingButton;
    JTextField idText,dateText;
    MealMenu mealMenus;
    File file;
    HashSet<String> idSet;

    public OrderingWindow(){
        setTitle("点菜界面");
        idSet=new HashSet<>();

        pNorth=new JPanel();
        idText=new JTextField(10);
        idText.addActionListener(this);
        querenButton=new JButton("确认");
        querenButton.addActionListener(this);
        dateText=new JTextField(20);
        dateText.setEnabled(false);
        pNorth.add(new JLabel("请输入桌号： "));
        pNorth.add(idText);
        pNorth.add(querenButton);
        pNorth.add(new JLabel("点餐日期和时间： "));
        pNorth.add(dateText);

        pCenter=new JPanel();
        pCenter.setBorder(new TitledBorder(new LineBorder(Color.BLUE),"分类点菜",TitledBorder.LEFT,TitledBorder.TOP));
        meatButton=new JButton("荤菜");
        meatButton.addActionListener(this);
        meatButton.setEnabled(false);
        vegetarianButton=new JButton("素菜");
        vegetarianButton.addActionListener(this);
        vegetarianButton.setEnabled(false);
        stapleFoodButton=new JButton("主食");
        stapleFoodButton.addActionListener(this);
        stapleFoodButton.setEnabled(false);
        soupAndPorridgeButton=new JButton("汤粥");
        soupAndPorridgeButton.addActionListener(this);
        soupAndPorridgeButton.setEnabled(false);

        Box basebox= Box.createHorizontalBox();
        Box box1=Box.createVerticalBox();
        box1.add(meatButton);
        box1.add(Box.createVerticalStrut(20));
        box1.add(stapleFoodButton);
        Box box2=Box.createVerticalBox();
        box2.add(vegetarianButton);
        box2.add(Box.createHorizontalStrut(20));
        box2.add(soupAndPorridgeButton);
        basebox.add(box1);
        basebox.add(Box.createHorizontalStrut(100));
        basebox.add(box2);
        pCenter.add(basebox);

        pSouth=new JPanel();
        showButton=new JButton("显示点菜结果");
        showButton.addActionListener(this);
        showButton.setEnabled(false);
        stopOrderingButton=new JButton("结束本次点菜");
        stopOrderingButton.addActionListener(this);
        pSouth.add(showButton);
        pSouth.add(stopOrderingButton);

        add(pNorth,"North");
        add(pCenter,"Center");
        add(pSouth,"South");

        setBounds(100,100,600,230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==querenButton){
            if(idText.getText().length()!=0){
                if(!idSet.add(idText.getText())){
                    JOptionPane.showMessageDialog(this,"此桌已经有人");
                }else{
                    idText.setEnabled(false);
                    dateText.setEnabled(true);
                    meatButton.setEnabled(true);
                    vegetarianButton.setEnabled(true);
                    stapleFoodButton.setEnabled(true);
                    soupAndPorridgeButton.setEnabled(true);
                    showButton.setEnabled(true);

                    Date time=new Date();
                    SimpleDateFormat matter=new SimpleDateFormat("点菜时间： yyyy-MM-dd HH:mm:ss");
                    String date=matter.format(time);
                    dateText.setText(date);
                    String ordersDish=idText.getText()+" 号的点菜清单.txt";
                    file=new File("D:\\oreders\\"+ordersDish);
                }
            }
        }
       else if(e.getSource()==showButton){
            ShowOrederingRecord showOrdering=new ShowOrederingRecord(file);
            showOrdering.setVisible(true);
            showOrdering.showRecord();
        }
       else if(e.getSource()==stopOrderingButton){
            idText.setText(" ");
            idText.setEnabled(true);
            dateText.setEnabled(false);
            meatButton.setEnabled(false);
            vegetarianButton.setEnabled(false);
            stapleFoodButton.setEnabled(false);
            soupAndPorridgeButton.setEnabled(false);
            showButton.setEnabled(false);
        }else{
            String menusName=null;
            if(e.getSource()==meatButton){
                mealMenus=new MeatMenu();
                menusName=" 荤菜 点菜";
                meatButton.setEnabled(false);
            }
            if(e.getSource()==vegetarianButton){
                mealMenus=new VegetarianMenu();
                menusName="素菜 点菜";
                vegetarianButton.setEnabled(false);
            }
            if(e.getSource()==stapleFoodButton){
                mealMenus=new StapleFoodMenu();
                menusName="主食 点菜";
                stapleFoodButton.setEnabled(false);
            }
            if(e.getSource()==soupAndPorridgeButton){
                mealMenus=new SoupAndPorridgeMenu();
                menusName="汤粥 点菜";
                soupAndPorridgeButton.setEnabled(false);
            }
            new OrderDishes(mealMenus,menusName,file).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new OrderingWindow();
    }
}
