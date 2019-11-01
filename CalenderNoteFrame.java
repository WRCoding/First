import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class CalenderNoteFrame extends JFrame implements ActionListener{
    private JFrame jf;
    private Container c;
    private JButton downYear,upYear,downMonth,upMonth,thisdayButton,notelistButoon,saveButton,deleteButton;
    private JComboBox yearbox,monthbox;
    private JButton[] weekButton=new JButton[7];//星期数组
    private JButton[] daysButton=new JButton[42];//每月天数数组
    private String years[]={"2015","2016","2017","2018","2019","2020","2021"};;
    private String months[]={"1","2","3","4","5","6","7","8","9","10","11","12"};
    private JPanel leftpanel,rightpanel;
    private JPanel leftCenter,cardpanel;
    private String year,month;//记录年份和月份
    private  int recordYear,recordMonth,listCount=0,cancel=1;//记录年份框/月份框当前选项的下标,listCount记录一共有多少条记事
    private boolean flag=false;//flag判断面板是否切换过,cancel让表格不会因为每次点击按钮就重复增加行数
    private JLabel timeLabel,dateLabel;
    private JTextArea noteTextArea;//记事文本域
    private	DefaultTableModel model;
    private JTable table;
    private CardLayout card;
    private SimpleDateFormat currentDate = new SimpleDateFormat("yyyy年MM月dd日");//获得当前的日期
    public CalenderNoteFrame(){
        jf=new JFrame("日历记事本");
        c=jf.getContentPane();
        c.setLayout(new BorderLayout());
        jf.setSize(700, 700);
        jf.setVisible(true);
        jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
        /**
         * 制作出日历表
         */
        leftpanel=new JPanel(new BorderLayout());
        leftCenter=new JPanel();
        leftCenter.setLayout(new GridLayout(7, 7));

        /**
         * 添加星期按钮数组和天数按钮数组
         */
        String[] week={"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Font font=new Font("宋体",Font.BOLD, 15);
        for(int i=0;i<week.length;i++){
            weekButton[i]=new JButton(week[i]);
            weekButton[i].setFont(font);
            weekButton[i].setEnabled(false);
            leftCenter.add(weekButton[i]);
        }
        for( int i=0;i<42;i++){
            daysButton[i]=new JButton("");
            leftCenter.add(daysButton[i]);
        }



        /**
         * 制作记事表格
         */
        String[] col={"ID","日期","记事"};
        model=new DefaultTableModel(col, 0);
        table=new JTable(model);
        table.setRowSorter(new TableRowSorter<>(model));
        JScrollPane js=new JScrollPane(table);
        /**
         * 使用卡片布局，使得不同面板可以进行来回切换
         */
        card=new CardLayout();
        cardpanel=new JPanel(card);
        cardpanel.add(leftCenter,"left");
        cardpanel.add(js,"js");
        leftpanel.add(cardpanel, BorderLayout.CENTER);//将卡片布局器添加到容器中
        /**
         * 添加组件
         */
        yearbox=new JComboBox<>(years);
        yearbox.setEditable(false);
        yearbox.addActionListener(e -> CalclulateDate());
        monthbox=new JComboBox<>(months);
        monthbox.setEditable(false);
        monthbox.addActionListener(e -> CalclulateDate());
        /**
         * 使得程序一开始就处于当前日期的位置
         */
        Calendar cc=Calendar.getInstance();
        cc.set(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH), 1);
        int a=cc.get(Calendar.DAY_OF_WEEK)-1;
        for(int i=1;i<=calclulate(cc.get(Calendar.YEAR), cc.get(Calendar.MONTH)+1);i++){
            daysButton[a].setText(""+i+"");
            a++;
        }
        for(int i=0;i<years.length;i++){//查找出当前年份对应的数组下标
            String temp=String.valueOf(cc.get(Calendar.YEAR));
            if(years[i].equals(temp)){
                yearbox.setSelectedIndex(i);
            }
        }
        monthbox.setSelectedIndex(cc.get(Calendar.MONTH));
//        年份减一按钮
        downYear=new JButton("<");
        downYear.addActionListener(this);
//        年份加一按钮
        upYear=new JButton(">");
        upYear.addActionListener(this);
//        月份减一按钮
        downMonth=new JButton("<");
        downMonth.addActionListener(this);
//        月份加一按钮
        upMonth=new JButton(">");
        upMonth.addActionListener(this);
        thisdayButton=new JButton("当前日期");
        thisdayButton.addActionListener(this);
        notelistButoon=new JButton("记事列表");
        notelistButoon.addActionListener(this);
//        日期选择面板
        JPanel DateSelectPanel=new JPanel(new FlowLayout());
        DateSelectPanel.add(downYear);DateSelectPanel.add(yearbox);DateSelectPanel.add(upYear);
        DateSelectPanel.add(downMonth);DateSelectPanel.add(monthbox);DateSelectPanel.add(upMonth);
        DateSelectPanel.add(thisdayButton);DateSelectPanel.add(notelistButoon);
        leftpanel.add(DateSelectPanel, BorderLayout.NORTH);
        c.add(leftpanel,BorderLayout.WEST);
        timeLabel=new JLabel();
        Font font2=new Font("黑体", Font.BOLD, 16);
        timeLabel.setFont(font2);
        Timer time=new Timer();
        TimerTask task=new TimerTask() {//设置定时器，在界面中显示时间
            @Override
            public void run() {
                // TODO 自动生成的方法存根
                long timemillis = System.currentTimeMillis();
                //转换日期显示格式   
                SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日      HH:mm:ss  E");
                timeLabel.setText("            当前时间：    "+df.format(new Date(timemillis)));
            }
        };
        time.schedule(task, 1000,1000);//每过一秒执行一次
        rightpanel=new JPanel(new BorderLayout());
        rightpanel.add(timeLabel,BorderLayout.NORTH);
        JPanel rightCenter=new JPanel(new BorderLayout());
//        记事日期
        dateLabel=new JLabel(" ",JLabel.CENTER);

        dateLabel.setFont(font2);
        dateLabel.setText(currentDate.format(new Date()));
//      记事文本域
        noteTextArea=new JTextArea(100, 100);
        noteTextArea.setLineWrap(true);//自动换行
        noteTextArea.setWrapStyleWord(true);//换行不换字
        noteTextArea.setFont(new Font("黑体",Font.BOLD,24));//设置字体
        saveButton=new JButton("保存");
        saveButton.addActionListener(this);
        deleteButton=new JButton("删除");
        deleteButton.addActionListener(this);
        JPanel buttonpanel=new JPanel(new FlowLayout());
        buttonpanel.add(saveButton);buttonpanel.add(deleteButton);
        rightCenter.add(noteTextArea, BorderLayout.CENTER);
        rightCenter.add(dateLabel, BorderLayout.NORTH);
        rightpanel.add(rightCenter, BorderLayout.CENTER);
        rightpanel.add(buttonpanel, BorderLayout.SOUTH);
        c.add(rightpanel);
    }
    public int calclulate(int year,int month){//计算月份的天数
        int day;
        if(((year%4==0&&year%100!=0)||year%400==0) && month==2){//闰年二月29天
            day=29;
            return day;
        }
        if(month==1||month==3||month==5||month==5||month==7||month==8||month==10||month==12){
            day=31;
        }else{
            day = month==2 ? 28 : 30;
        }
        return day;
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==downYear){
            recordYear--;
            if(recordYear>=0){
                yearbox.setSelectedIndex(recordYear);//通过recordYear减一，让年份框的年份向前减一
            }
            if(recordYear==-1){
                yearbox.setSelectedIndex(yearbox.getItemCount()-1);
            }
        }
        if(e.getSource()==upYear){
            recordYear++;
            if(recordYear<yearbox.getItemCount()){
                yearbox.setSelectedIndex(recordYear);//通过recordYear加一，让年份框的年份往后加一
            }
            if(recordYear>=(yearbox.getItemCount())){
                yearbox.setSelectedIndex(0);//因为选项下标重新设置为0，所以record也重新被设置为0
            }
        }
        if(e.getSource()==downMonth){
            recordMonth--;
            if(recordMonth>=0){
                monthbox.setSelectedIndex(recordMonth);
            }
            if(recordMonth==-1){
                monthbox.setSelectedIndex(monthbox.getItemCount()-1);
                yearbox.setSelectedIndex(recordYear-1);
                CalclulateDate();
            }
        }
        if(e.getSource()==upMonth){
            recordMonth++;
            if(recordMonth<monthbox.getItemCount()){
                monthbox.setSelectedIndex(recordMonth);
            }if(recordMonth>=(monthbox.getItemCount())){
                monthbox.setSelectedIndex(0);
                yearbox.setSelectedIndex(recordYear+1);
                CalclulateDate();
            }
        }
        if(e.getSource()==thisdayButton){
            dateLabel.setText(currentDate.format(new Date()));//设置为当前日期
            for(int i=0;i<daysButton.length;i++){//清空数组
                daysButton[i].setText("");
            }
            Calendar calendar=Calendar.getInstance();
            for(int i=0;i<years.length;i++){//查找出当前年份对应的数组下标
                String temp=String.valueOf(calendar.get(Calendar.YEAR));
                if(years[i].equals(temp)){
                    yearbox.setSelectedIndex(i);
                }
            }
            calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1);
            monthbox.setSelectedIndex(calendar.get(Calendar.MONTH));//将月份框设置为当前日期的月份
            CalclulateDate();
        }
        if(e.getSource()==notelistButoon){
            try {
                if(!flag){
                    card.show(cardpanel, "left");
                    flag=true;
                    if(cancel==2){
                        listCount=0;
                        while(model.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                            System.out.println(model.getRowCount());
                            model.removeRow(model.getRowCount()-1);
                        }
                    }
                }else if(flag){
                    card.show(cardpanel, "js");
                    flag=false;
                    String note,noteTime;
                    File file=new File("D://newfile//note");
                    File[] notelist=file.listFiles();
                    for(int i=0;i<notelist.length;i++){
                        if(notelist[i].isFile()){
                            listCount++;
                            noteTime=notelist[i].getName();
                            FileReader fr=new FileReader(notelist[i]);
                            char ch[]=new char[(int)notelist[i].length()];
                            fr.read(ch);
                            note=String.valueOf(ch);
                            String row[]={String.valueOf(listCount),noteTime,note};
                            model.addRow(row);
                            fr.close();
                        }
                    }
                    cancel=2;//表格刷新的标志
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if(e.getSource()==saveButton){
            try {
                String str=dateLabel.getText()+ "-"+UUID.randomUUID().toString().substring(0,8);//将要保存的年月日作为文件名
                if(str.trim().length() == 0){//如果没点击按钮直接保存记事，则文件名默认为当前日期
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日");
                    str = dateFormat.format(new Date());
                }
                File file=new  File("D://newfile//note//"+str+".txt");//创建出以str为文件名的文本
                if(!file.getParentFile().exists()){
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                //FileOutputStream fo=new FileOutputStream(file);
                FileWriter fw=new FileWriter(file,true);//把文本域中的文本保存到文件中
                String ss=noteTextArea.getText();
                fw.write(ss);
                //fo.close();
                fw.close();
                JOptionPane.showMessageDialog(this, "保存成功！","提示框",JOptionPane.WARNING_MESSAGE);
                noteTextArea.setText("");
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        if(e.getSource()==deleteButton){
            try {
                String str=dateLabel.getText();
                File file=new File("D://newfile//note//"+str);
                if(file.exists()){
                    int r=JOptionPane.showInternalConfirmDialog(c, "确认删除吗？","提示框",JOptionPane.YES_NO_OPTION);
                    if(r==0){
                        file.delete();
                        noteTextArea.setText("");
                    }
                }else if(!file.exists()){
                    JOptionPane.showMessageDialog(this, "这一天没有记事","提示框",JOptionPane.WARNING_MESSAGE);
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }


    public void CalclulateDate(){
        for(int i=0;i<daysButton.length;i++){//       初始时清空所有按钮的数字，以及设置为不可选
            daysButton[i].setBackground(null);
            daysButton[i].setText("");
            daysButton[i].setEnabled(false);
        }
        year=(String)yearbox.getSelectedItem();//获得点击的年份
        month=(String)monthbox.getSelectedItem();//获得点击的月份
        Calendar ca=Calendar.getInstance();
        //int q=ca.get(Calendar.DAY_OF_MONTH);
        ca.set(Integer.parseInt(year),Integer.parseInt(month)-1, 1);
        int day=ca.get(Calendar.DAY_OF_WEEK)-1;//减一是因为从星期天开始算，星期天=1
        for(int i=1;i<=calclulate(Integer.parseInt(year),Integer.parseInt(month));i++){
            daysButton[day].setText(""+i+"");
//          将按钮设置为可选
            daysButton[day].setEnabled(true);
            daysButton[day].addMouseListener(new MyAdapter(daysButton[day]));
            daysButton[day].addActionListener(new setlabel(daysButton[day],day));
            day++;
        }
        recordMonth=monthbox.getSelectedIndex();//记录当前年份框值的索引
        recordYear=yearbox.getSelectedIndex();//记录当前月份框值的索引
    }

    public static void main(String[] args) {
        // TODO 自动生成的方法存根
        new CalenderNoteFrame();
    }
    class MyAdapter extends MouseAdapter{//使用鼠标事件适配器，减少代码量
        JButton button;
        public MyAdapter(JButton b){
            this.button=b;
        }
        public void mouseEntered(MouseEvent e){
//            鼠标经过时显示为红色
            if(button.getText().length() > 0){
                button.setBackground(Color.RED);
            }
        }
        public void mouseExited(MouseEvent e){
            button.setBackground(null);
        }
    }
    class setlabel implements ActionListener{//点击按钮时，datelabel获得相应的年月日星期
        private JButton button;
        private int day;
        public  setlabel(JButton button,int day) {
            // TODO 自动生成的构造函数存根
            this.button=button;
            this.day=day;
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO 自动生成的方法存根
            String week=null;
            switch (day%7) {
                case 0:
                    week="星期天";
                    break;
                case 1:
                    week="星期一";
                    break;
                case 2:
                    week="星期二";
                    break;
                case 3:
                    week="星期三";
                    break;
                case 4:
                    week="星期四";
                    break;
                case 5:
                    week="星期五";
                    break;
                case 6:
                    week="星期六";
                    break;
            }
            year=(String)yearbox.getSelectedItem();//获得点击的年份
            month=(String)monthbox.getSelectedItem();//获得点击的月份
            dateLabel.setText(year+"年"+month+"月"+button.getText()+"日");
        }
    }
}
