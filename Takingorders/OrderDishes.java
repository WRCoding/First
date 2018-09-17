package Takingorders;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;

public class OrderDishes extends JDialog implements ActionListener,ListSelectionListener {
    MealMenu mealMenuu;
    private JPanel panel;
    private JList menuList,orderList;
    private JButton addButton,randomButton,deleteButton,saveButton;
    private LinkedList<Meal> mealMenus;
    private Vector<String>mealName;
    private Vector<Double>mealPrice;
    private Vector<Meal>orderDishes;
    private Meal meal;
    private int selectedIndex,orderIndex;
    private DefaultListModel orderMealNames;
    private File file;

    public OrderDishes(){}
    public OrderDishes(MealMenu mealMenu,String name,File file){
        this.file=file;
        setTitle(name);
        mealMenus =mealMenu.getMeatMenu();

        mealName=new Vector<String>();
        mealPrice=new Vector<Double>();
        orderDishes=new Vector<Meal>();

        Iterator<Meal>it=mealMenus.iterator();
        while (it.hasNext()){
            meal=it.next();
            mealName.add(meal.getName());
            mealPrice.add(meal.getPrice());
        }
        panel=new JPanel();
        panel.setLayout(new GridLayout(1,3));
        JPanel pMenus=new JPanel();
        pMenus.setLayout(new BorderLayout());
        menuList=new JList(mealName);
        menuList.setSelectedIndex(0);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.addListSelectionListener(this);
        JScrollPane listScrollPane=new JScrollPane(menuList);
        pMenus.add(new JLabel("菜单",JLabel.CENTER),"North");
        pMenus.add(listScrollPane,"Center");

        JPanel pButton=new JPanel();
        Box box=Box.createVerticalBox();
        addButton=new JButton("点菜");
        addButton.addActionListener(this);
        randomButton=new JButton("随机推荐");
        randomButton.addActionListener(this);
        deleteButton=new JButton("撤销点菜");
        saveButton=new JButton("下单");
        saveButton.addActionListener(this);
        box.add(Box.createVerticalStrut(20));
        box.add(addButton);
        box.add(Box.createVerticalStrut(15));
        box.add(randomButton);
        box.add(Box.createVerticalStrut(15));
        box.add(saveButton);
        pButton.add(box);

        JPanel pOrder=new JPanel();
        pOrder.setLayout(new BorderLayout());
        orderMealNames=new DefaultListModel();
        orderList=new JList(orderMealNames);
        orderList.addListSelectionListener(this);
        JScrollPane listS2=new JScrollPane(orderList);
        pOrder.add(new JLabel("已点",JLabel.CENTER),"North");
        pOrder.add(listS2,"Center");

        panel.add(pMenus);
        panel.add(pButton);
        panel.add(pOrder);
        add(panel);

        setBounds(300,10,900,600);
        setResizable(false);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==addButton){
            Meal meal=mealMenus.get(selectedIndex);
            orderDishes.add(meal);
            orderMealNames.addElement(meal.getName());
        }
        if(e.getSource()==randomButton){
            int size=mealMenus.size();
            Random rand=new Random();
            selectedIndex=rand.nextInt(size);
            menuList.setSelectedIndex(selectedIndex);
        }
        if(e.getSource()==deleteButton){
            orderIndex=orderList.getSelectedIndex();
            orderDishes.remove(orderIndex);
            orderMealNames.removeElement(orderIndex);
        }
        if(e.getSource()==saveButton){
            saveButton.setEnabled(false);
            try {
                RandomAccessFile out=new RandomAccessFile(file,"rw");
                if(file.exists()){
                    long length=file.length();
                    out.seek(length);
                }

                for (int i=0;i<orderDishes.size();i++){
                    out.writeUTF(orderDishes.get(i).getName());
                    String str=String.valueOf(orderDishes.get(i).getPrice());
                    System.out.println(str);
                    out.writeUTF(str);
                }
              /*  FileWriter out=new FileWriter(file,true);
                double total=0;
                for(int i=0;i<orderDishes.size();i++){
                    out.write(orderDishes.get(i).getName());
                    out.write("  "+orderDishes.get(i).getPrice());
                    total+=orderDishes.get(i).getPrice();
                    out.write("\r\n");
                }*/
                out.close();
            }catch (IOException e1){}
            setVisible(false);

        }
    }

    public void valueChanged(ListSelectionEvent e) {
        if(e.getSource()==menuList){
            selectedIndex=menuList.getSelectedIndex();
        }
    }
}
