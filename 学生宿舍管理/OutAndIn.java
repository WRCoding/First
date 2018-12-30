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

/**回校返校记录
 * @author LB
 * @create 2018-12-24 21:34
 */
public class OutAndIn extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users users;
    JTable table = new JTable();
    JButton button = new JButton("666");
    String[] col = {"学号", "宿舍号", "离校时间", "返校时间"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    String DDno;//宿舍号

    JLabel Leave,Return,Dno,Sno;
    JTextField LeaveText,ReturnText,DnoText,SnoText;
    JButton submit;
    JPanel student;

    public OutAndIn(int type,Users users) {
        this.users=users;
        this.type=type;
        setLayout(new FlowLayout());
        table.setModel(mm);
        table.setRowSorter(new TableRowSorter<>(mm));
        JScrollPane js = new JScrollPane(table);
        add(js);
        search();
    }

    private void search() {
        if(type==1){
            record();
            try {
                String select="select Dno from student where Sname"+"="+"'"+users.getName()+"'";
                PreparedStatement state=connection.prepareStatement(select);
                ResultSet resultSet=state.executeQuery();
                while (resultSet.next()) {
                    DDno = resultSet.getString("Dno");
                }
                state = connection.prepareStatement("select*from leave  where Dno"+"="+"'"+DDno+"'");
                resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Sno = resultSet.getString(1);
                    String Dno = resultSet.getString(2);
                    String Ltime = resultSet.getString(3);
                    String Lreturn = resultSet.getString(4);
                    String[] data = {Sno, Dno, Ltime, Lreturn};
                    mm.addRow(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(type==2){
            try {
                PreparedStatement state = connection.prepareStatement("select*from leave");
                ResultSet resultSet = state.executeQuery();
                while (resultSet.next()) {
                    String Sno = resultSet.getString(1);
                    String Dno = resultSet.getString(2);
                    String Ltime = resultSet.getString(3);
                    String Lreturn = resultSet.getString(4);
                    String[] data = {Sno, Dno, Ltime, Lreturn};
                    mm.addRow(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void record(){
        Sno=new JLabel("学号");
        SnoText=new JTextField(10);
        Dno=new JLabel("宿舍号");
        DnoText=new JTextField(10);
        Leave=new JLabel("离校时间");
        LeaveText=new JTextField(10);
        Return=new JLabel("返校时间");
        ReturnText=new JTextField(10);
        submit=new JButton("提交");
        submit.addActionListener(this);
        student=new JPanel(new GridLayout(5, 2));
        student.add(Sno);student.add(SnoText);
        student.add(Dno);student.add(DnoText);
        student.add(Leave);student.add(LeaveText);
        student.add(Return);student.add(ReturnText);
        student.add(submit);
        add(student);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource()==submit&&type==1){
            PreparedStatement statement = null;
            try {
                statement = connection.prepareStatement("insert into leave values(?,?,?,?)");
                statement.setString(1, SnoText.getText());
                statement.setString(2, DnoText.getText());
                statement.setString(3, LeaveText.getText());
                statement.setString(4, ReturnText.getText());
                statement.executeUpdate();

                String select="select Dno from student where Sname"+"="+"'"+users.getName()+"'";
                PreparedStatement state=connection.prepareStatement(select);
                ResultSet resultSet=state.executeQuery();
                while (resultSet.next()) {
                    DDno = resultSet.getString("Dno");
                }
                state = connection.prepareStatement("select*from leave  where Dno"+"="+"'"+DDno+"'");
                resultSet = state.executeQuery();

                while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount()-1);
                }

                while (resultSet.next()) {
                    String Sno = resultSet.getString(1);
                    String Dno = resultSet.getString(2);
                    String Ltime = resultSet.getString(3);
                    String Lreturn = resultSet.getString(4);
                    String[] data = {Sno, Dno, Ltime, Lreturn};
                    mm.addRow(data);
                }


            } catch (SQLException e1) {
                e1.printStackTrace();
            }

        }
    }
}
