import com.sun.org.apache.bcel.internal.generic.NEW;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


/**宿舍信息(学生)
 * @author LB
 * @create 2018-12-24 21:27
 */
public class DormitoryInfo extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    Users users;//当前用户
    int type;//用户类型
    String Dno="";//宿舍号
    JTable table=new JTable();
    String[] col = { "学号", "姓名", "性别","专业","宿舍号","入住时间" };
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板
    JLabel Sdept,suse,name;
    JTextField SdeptText,suseText,nameText;
    JButton submit;
    JPanel suguan;
    public DormitoryInfo(Users users,int type){//从登录界面传回，用户名和用户类型
        this.type=type;
        this.users=users;
        setLayout(new FlowLayout());

        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));//排序
        JPanel jPanel=new JPanel(new FlowLayout());
        JScrollPane js=new JScrollPane(table);
        jPanel.add(js);

        add(jPanel);
        search();
    }

    private void search(){
        PreparedStatement state;
        ResultSet resultSet;
        if(type==1){//如果是学生，只显示学生自己宿舍的信息
            try {
                inquire();
                String select="select Dno from student where Sname"+"="+"'"+users.getName()+"'";
                state=connection.prepareStatement(select);
                resultSet=state.executeQuery();
                while (resultSet.next()){
                    Dno=resultSet.getString("Dno");
                }
                System.out.println(users.getName()+users.getName().length());
                select="select*from student where Dno"+"="+"'"+Dno+"'";
                state=connection.prepareStatement(select);
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Ssex=resultSet.getString(3);
                    String Sdept=resultSet.getString(4);
                    String Dno=resultSet.getString(5);
                    String Scheckin=resultSet.getString(6);
                    String[] data={Sno,Sname,Ssex,Sdept,Dno,Scheckin};
                    mm.addRow(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(type==2){//如果是宿管，则显示全部学生的宿舍
            try {
                xiugai();
                state=connection.prepareStatement("select *from student");
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Sname=resultSet.getString(2);
                    String Ssex=resultSet.getString(3);
                    String Sdept=resultSet.getString(4);
                    String Dno=resultSet.getString(5);
                    String Scheckin=resultSet.getString(6);
                    String[] data={Sno,Sname,Ssex,Sdept,Dno,Scheckin};
                    mm.addRow(data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    private void inquire(){//学生只能查询任意宿舍的电话
        suse=new JLabel("宿舍号");
        suseText=new JTextField(10);
        submit=new JButton("查询");
        submit.addActionListener(this);
       suguan=new JPanel(new GridLayout(2, 2));
       suguan.add(suse);suguan.add(suseText);suguan.add(submit);
       add(suguan);
    }


    private void xiugai(){//宿管修改学生的宿舍信息
        Sdept=new JLabel("学院");
        suse=new JLabel("宿舍号");
        SdeptText=new JTextField(10);
        suseText=new JTextField(10);
        name=new JLabel("名字");
        nameText=new JTextField(10);
        suguan=new JPanel(new GridLayout(4, 2));
        submit=new JButton("提交");
        submit.addActionListener(this);
        suguan.add(name);suguan.add(nameText);
        suguan.add(Sdept);suguan.add(SdeptText);
        suguan.add(suse);suguan.add(suseText);
        add(suguan);
        suguan.add(submit);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit&&type==2){//如果点击按钮的是宿管
            try {
                if (suseText.getText().length()>0&&SdeptText.getText().length()==0){//只修改宿舍号

                    Statement statement = connection.createStatement();
                    String sql="update student set Dno="+"'"+suseText.getText()+"'"+"where Sname"+"="+"'"+nameText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){//把更新后的数据重新显示到表格中，下同
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String DDno=resultSet.getString(5);
                        String Scheckin=resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,DDno,Scheckin};
                        mm.addRow(data);
                    }
                }
              if(suseText.getText().length()==0&&SdeptText.getText().length()>0){//只修改所在系
                    Statement statement = connection.createStatement();
                    String sql="update student set Sdept="+"'"+SdeptText.getText()+"'"+"where Sname"+"="+"'"+nameText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String DDno=resultSet.getString(5);
                        String Scheckin=resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,DDno,Scheckin};
                        mm.addRow(data);
                    }
                }
                if(suseText.getText().length()>0&&SdeptText.getText().length()>0){//同时修改专业和宿舍
                    Statement statement = connection.createStatement();
                    String sql="update student set Sdept="+"'"+SdeptText.getText()+"'"+", Dno="+ "'"+suseText.getText()+"'"   +"where Sname"+"="+"'"+nameText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from student");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Sno=resultSet.getString(1);
                        String Sname=resultSet.getString(2);
                        String Ssex=resultSet.getString(3);
                        String Sdept=resultSet.getString(4);
                        String DDno=resultSet.getString(5);
                        String Scheckin=resultSet.getString(6);
                        String[] data={Sno,Sname,Ssex,Sdept,DDno,Scheckin};
                        mm.addRow(data);
                    }
                }

            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource()==submit&&type==1){//如果是学生的身份进入
            PreparedStatement state;
            ResultSet resultSet;
            try {
                state=connection.prepareStatement("select Dphone from Dormitory where Dno ="+"'"+suseText.getText()+"'");
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    //suse.setText("电话");
                    suseText.setText(resultSet.getString("Dphone"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }
}
