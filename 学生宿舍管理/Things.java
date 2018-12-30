import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**宿舍物品
 * @author LB
 * @create 2018-12-26 15:58
 */
public class Things extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    Users users;//当前用户
    int type;//用户类型
    String Dno="";//宿舍号
    JTable table = new JTable();
    String[] col = {"宿舍号", "宿舍电话", "物品号", "物品名"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    JLabel dnoLabel,phoneLabel;
    JTextField dnoText,phoneText;
    JButton submit;
    JPanel suguan;
    public Things(int type,Users users){
        this.type=type;
        this.users=users;
        setLayout(new FlowLayout());
        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));//排序
        JScrollPane js=new JScrollPane(table);
        add(js);
        search();
    }
    private void search(){
        PreparedStatement state;
        ResultSet resultSet;
        if(type==1){//如果是学生，只显示学生自己宿舍的信息
            try {
                String select="select Dno from student where Sname"+"="+"'"+users.getName()+"'";
                state=connection.prepareStatement(select);
                resultSet=state.executeQuery();
                while (resultSet.next()){
                    Dno=resultSet.getString("Dno");
                }
                select="select*from Dormitory  where Dno"+"="+"'"+Dno+"'";
                state=connection.prepareStatement(select);
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Dno=resultSet.getString(1);
                    String Dphone=resultSet.getString(2);
                    String Pno=resultSet.getString(3);
                    String Pname=resultSet.getString(4);
                    String[] data={Dno,Dphone,Pno,Pname};
                    mm.addRow(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else if(type==2){//如果是宿管，则显示全部学生的宿舍
            try {
                xiugai();
                state=connection.prepareStatement("select *from Dormitory");
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Dno=resultSet.getString(1);
                    String Dphone=resultSet.getString(2);
                    String Pno=resultSet.getString(3);
                    String Pname=resultSet.getString(4);
                    String[] data={Dno,Dphone,Pno,Pname};
                    mm.addRow(data);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private void xiugai(){
        dnoLabel=new JLabel("宿舍号");
        dnoText=new JTextField(10);
        phoneLabel=new JLabel("电话");
        phoneText=new JTextField(10);
        submit=new JButton("提交");
        submit.addActionListener(this);
        suguan=new JPanel(new GridLayout(3, 2));
        suguan.add(dnoLabel);suguan.add(dnoText);
        suguan.add(phoneLabel);suguan.add(phoneText);
        suguan.add(submit);
        add(suguan);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit){
            if(phoneText.getText().length()==0||dnoText.getText().length()==0){
                JOptionPane.showMessageDialog(null, "请输入宿舍号或电话", "警告",JOptionPane.WARNING_MESSAGE);
            }else{
                try {
                    Statement statement = connection.createStatement();
                    String sql="update Dormitory set Dphone="+"'"+phoneText.getText()+"'"+"where Dno"+"="+"'"+dnoText.getText()+"'";
                    statement.executeUpdate(sql);
                    PreparedStatement state;
                    ResultSet resultSet;
                    state=connection.prepareStatement("select *from Dormitory");
                    resultSet = state.executeQuery();
                    while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                        //System.out.println(model.getRowCount());
                        mm.removeRow(mm.getRowCount()-1);
                    }
                    while (resultSet.next()){
                        String Dno=resultSet.getString(1);
                        String Dphone=resultSet.getString(2);
                        String Pno=resultSet.getString(3);
                        String Pname=resultSet.getString(4);
                        String[] data={Dno,Dphone,Pno,Pname};
                        mm.addRow(data);
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

            }
        }
    }
}
