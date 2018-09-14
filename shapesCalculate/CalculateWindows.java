package shapesCalculate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CalculateWindows extends JFrame implements ActionListener,ItemListener {
    private CardLayout mycard;
    private JPanel controlPanel,pCenter;
    private JComboBox chooseList;
    private JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem mainWindow,exit;
    public CalculateWindows(){
        setTitle("常见几何图形的计算器");

        menuBar=new JMenuBar();
        menu=new JMenu("操作");
        mainWindow=new JCheckBoxMenuItem("主界面");
        mainWindow.addActionListener(this);
        exit=new JCheckBoxMenuItem("退出");
        exit.addActionListener(this);

        menu.add(mainWindow);
        menu.add(exit);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        mycard=new CardLayout();
        pCenter=new JPanel();
        pCenter.setLayout(mycard);
        add(pCenter,"Center");

        controlPanel=new JPanel();
        controlPanel.setLayout(new BorderLayout());
        chooseList=new JComboBox();
        chooseList.addItem("请单击下拉列表选择");
        chooseList.addItem("矩形");
        chooseList.addItem("圆形");
        chooseList.addItem("三角形");
        chooseList.addItemListener(this);

        ImageIcon icon=new ImageIcon("D:\\newfile\\2.jpg");
        JButton imageButton=new JButton(icon);


        controlPanel.add(imageButton,"Center");
        controlPanel.add(chooseList,"North");

        pCenter.add("0",controlPanel);
        pCenter.add("1",new RectanglePanel());
        pCenter.add("2",new CirclePanel());
        pCenter.add("3",new TrianglePanel());


        setBounds(100,100,700,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==mainWindow){
            mycard.first(pCenter);
            chooseList.setSelectedIndex(0);
        }
        if(e.getSource()==exit)
            System.exit(0);
    }

    public void itemStateChanged(ItemEvent e) {
        int index=chooseList.getSelectedIndex();
        String choic= String.valueOf(index);
        mycard.show(pCenter,choic);
    }

    public static void main(String[] args) {
        new CalculateWindows();
    }
}
