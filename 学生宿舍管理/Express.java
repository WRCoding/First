import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**快件信息
 * @author LB
 * @create 2018-12-24 21:33
 */
public class Express extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users users;
    JTable table=new JTable();
    JButton button = new JButton("666");
    String[] col = { "姓名", "宿舍号", "到达时间","接收时间","邮件数量" };
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板
    JLabel arrive,take,name;
    JTextField arriveText,takeText,nameText;
    JButton submit;
    JPanel suguan;
    public Express(int type,Users users){
        this.type=type;
        this.users=users;
        setLayout(new FlowLayout());
        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));
        JScrollPane js=new JScrollPane(table);
        add(js);
        search();
    }
    private void search(){
        PreparedStatement state;
        ResultSet resultSet;
        if(type==1){//如果是学生，只显示学生自己宿舍的信息
            try {
                String select="select * from express where Sname"+"="+"'"+users.getName()+"'";
                state=connection.prepareStatement(select);
                resultSet=state.executeQuery();
                while (resultSet.next()){
                    String Sname=resultSet.getString(1);
                    String Dno=resultSet.getString(2);
                    String Marrive=resultSet.getString(3);
                    String Mreceive=resultSet.getString(4);
                    String Mnumber=resultSet.getString(5);
                    String[] data={Sname,Dno,Marrive,Mreceive,Mnumber};
                    mm.addRow(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(type==2){//如果是宿管，则显示全部学生的宿舍
            try {
                xiugai();
                state=connection.prepareStatement("select *from express");
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sname=resultSet.getString(1);
                    String Dno=resultSet.getString(2);
                    String Marrive=resultSet.getString(3);
                    String Mreceive=resultSet.getString(4);
                    String Mnumber=resultSet.getString(5);
                    String[] data={Sname,Dno,Marrive,Mreceive,Mnumber};
                    mm.addRow(data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void xiugai(){//宿管修改
        arrive=new JLabel("到达时间");
        take=new JLabel("取件时间");
        arriveText=new JTextField(10);
        takeText=new JTextField(10);
        name=new JLabel("名字");
        nameText=new JTextField(10);
        suguan=new JPanel(new GridLayout(4, 2));
        submit=new JButton("提交");
        submit.addActionListener(this);


        suguan.add(name);suguan.add(nameText);
        suguan.add(arrive);suguan.add(arriveText);
        suguan.add(take);suguan.add(takeText);
        add(suguan);
        suguan.add(submit);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String arrive=" ";
        String take=" ";
        arrive=arriveText.getText();
        take=takeText.getText();
        if(e.getSource()==submit){
            try {
                if (arriveText.getText().length()>0&&takeText.getText().length()==0){//只修改宿舍号

                    Statement statement = connection.createStatement();
                    String sql="update express set Marrive="+"'"+arrive+"'"+"where Sname"+"="+"'"+nameText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from express");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Sname=resultSet.getString(1);
                        String Dno=resultSet.getString(2);
                        String Marrive=resultSet.getString(3);
                        String Mreceive=resultSet.getString(4);
                        String Mnumber=resultSet.getString(5);
                        String[] data={Sname,Dno,Marrive,Mreceive,Mnumber};
                        mm.addRow(data);
                    }
                }
                if(arriveText.getText().length()==0&&takeText.getText().length()>0){//只修改所在系
                    Statement statement = connection.createStatement();
                    String sql="update express set Mreceive="+"'"+take+"'"+",Mnumber=Mnumber-1"  +"where Sname"+"="+"'"+nameText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from express");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Sname=resultSet.getString(1);
                        String Dno=resultSet.getString(2);
                        String Marrive=resultSet.getString(3);
                        String Mreceive=resultSet.getString(4);
                        String Mnumber=resultSet.getString(5);
                        String[] data={Sname,Dno,Marrive,Mreceive,Mnumber};
                        mm.addRow(data);
                    }
                }
                if(arriveText.getText().length()>0&&takeText.getText().length()>0){
                    Statement statement = connection.createStatement();
                    String sql="update express set Marrive="+"'"+arrive+"'"+", Mreceive="+ "'"+take+"'" +",Mnumber=Mumber-1"  +"where Sname"+"="+"'"+nameText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from express");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Sname=resultSet.getString(1);
                        String Dno=resultSet.getString(2);
                        String Marrive=resultSet.getString(3);
                        String Mreceive=resultSet.getString(4);
                        String Mnumber=resultSet.getString(5);
                        String[] data={Sname,Dno,Marrive,Mreceive,Mnumber};
                        mm.addRow(data);
                    }
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
    }
}
