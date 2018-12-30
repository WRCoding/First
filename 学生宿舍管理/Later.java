import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**学生晚归
 * @author LB
 * @create 2018-12-25 13:44
 */
public class Later extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    JButton button = new JButton("666");
    String[] col = {"学号", "宿舍号", "晚归时间", "晚归原因"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    String DDno;//宿舍号

    JLabel Btime,Breason,Dno,Sno;
    JTextField BtimeText,BreasonText,DnoText,SnoText;
    JButton submit;
    JPanel student;


    public Later(int type, Users user){
        this.user=user;
        this.type=type;
        setLayout(new FlowLayout());

        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));
        JScrollPane js=new JScrollPane(table);
        add(js);
        search();
    }
    private void search(){
        if(type==1){
            try {
                String select="select Dno from student where Sname"+"="+"'"+user.getName()+"'";
                PreparedStatement state=connection.prepareStatement(select);
                ResultSet resultSet=state.executeQuery();
                while (resultSet.next()) {
                    DDno = resultSet.getString("Dno");
                }
                state=connection.prepareStatement("select*from later  where Dno"+"="+"'"+DDno+"'");
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Dno=resultSet.getString(2);
                    String Btime=resultSet.getString(3);
                    String Breason=resultSet.getString(4);
                    String[] data={Sno,Dno,Btime,Breason};
                    mm.addRow(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
       if(type==2){
           try {
               record();
               PreparedStatement state=connection.prepareStatement("select*from later");
               ResultSet resultSet = state.executeQuery();
               while (resultSet.next()){
                   String Sno=resultSet.getString(1);
                   String Dno=resultSet.getString(2);
                   String Btime=resultSet.getString(3);
                   String Breason=resultSet.getString(4);
                   String[] data={Sno,Dno,Btime,Breason};
                   mm.addRow(data);
               }
           }catch (Exception e){
               e.printStackTrace();
           }
       }
    }
    private void record(){
        Sno=new JLabel("学号");
        SnoText=new JTextField(10);
        Dno=new JLabel("宿舍号");
        DnoText=new JTextField(10);
        Btime=new JLabel("晚归时间");
        BtimeText=new JTextField(10);
        Breason=new JLabel("晚归原因");
        BreasonText=new JTextField(10);
        submit=new JButton("提交");
        submit.addActionListener(this);
        student=new JPanel(new GridLayout(5, 2));
        student.add(Sno);student.add(SnoText);
        student.add(Dno);student.add(DnoText);
        student.add(Btime);student.add(BtimeText);
        student.add(Breason);student.add(BreasonText);
        student.add(submit);
        add(student);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit&&type==2){
            try {
                PreparedStatement   statement = connection.prepareStatement("insert into later values(?,?,?,?)");
                statement.setString(1, SnoText.getText());
                statement.setString(2, DnoText.getText());
                statement.setString(3, BtimeText.getText());
                statement.setString(4, BreasonText.getText());
                statement.executeUpdate();


                PreparedStatement state=connection.prepareStatement("select*from later");
                ResultSet resultSet = state.executeQuery();

                while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount()-1);
                }

                while (resultSet.next()){
                    String Sno=resultSet.getString(1);
                    String Dno=resultSet.getString(2);
                    String Btime=resultSet.getString(3);
                    String Breason=resultSet.getString(4);
                    String[] data={Sno,Dno,Btime,Breason};
                    mm.addRow(data);
                }

            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }
}
