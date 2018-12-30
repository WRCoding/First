import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

/**报修表
 * @author LB
 * @create 2018-12-24 21:32
 */
public class Renovation extends JPanel implements ActionListener {
    Connection connection = new GetConnection().GetConnection();
    int type;
    Users user;
    JTable table = new JTable();
    JButton button = new JButton("666");
    String[] col = {"宿舍号", "物品号", "提交日期", "解决日期","报修原因"};
    DefaultTableModel mm = new DefaultTableModel(col, 0); // 定义一个表的模板

    String Dno;//宿舍号；

    JLabel suse,solve,rsubmit,pno,reason;
    JTextField suseText,solveText,rsubmitText,pnoText,reasonText;
    JButton submit;
    JPanel suguan,student;
    public Renovation(int type,Users user){
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

        PreparedStatement state;
        ResultSet resultSet;
        if(type==1){
            baoxiu();
            String select="select Dno from student where Sname"+"="+"'"+user.getName()+"'";
            try {
                state=connection.prepareStatement(select);
                resultSet=state.executeQuery();
                while (resultSet.next()){
                    Dno=resultSet.getString("Dno");
                }
                select="select*from renovation  where Dno"+"="+"'"+Dno+"'";
                state=connection.prepareStatement(select);
                resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Dno=resultSet.getString(1);
                    String Pno=resultSet.getString(2);
                    String Rsubmint=resultSet.getString(3);
                    String Rsolve=resultSet.getString(4);
                    String Rseason=resultSet.getString(5);
                    String[] data={Dno,Pno,Rsubmint,Rsolve,Rseason};
                    mm.addRow(data);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(type==2){
            try {
                xiugai();
                 state=connection.prepareStatement("select*from renovation");
                 resultSet = state.executeQuery();
                while (resultSet.next()){
                    String Dno=resultSet.getString(1);
                    String Pno=resultSet.getString(2);
                    String Rsubmint=resultSet.getString(3);
                    String Rsolve=resultSet.getString(4);
                    String Rseason=resultSet.getString(5);
                    String[] data={Dno,Pno,Rsubmint,Rsolve,Rseason};
                    mm.addRow(data);
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private void xiugai(){//宿管修改
        suse=new JLabel("宿舍号");
        suseText=new JTextField(10);
        solve=new JLabel("解决时间");
        solveText=new JTextField(10);
        suguan=new JPanel(new GridLayout(4, 2));
        submit=new JButton("提交");
        submit.addActionListener(this);


        suguan.add(suse);suguan.add(suseText);
        suguan.add(solve );suguan.add(solveText);
        suguan.add(submit);
        add(suguan);
    }
    private void baoxiu(){
        pno=new JLabel("物品号");
        pnoText=new JTextField(10);
        rsubmit=new JLabel("报修时间");
        rsubmitText=new JTextField(10);
        reason=new JLabel("报修原因");
        reasonText=new JTextField(10);
        submit=new JButton("提交");
        submit.addActionListener(this);
        student=new JPanel(new GridLayout(4, 2));
        student.add(pno);student.add(pnoText);
        student.add(rsubmit);student.add(rsubmitText);
        student.add(reason);student.add(reasonText);
        student.add(submit);
        add(student);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==submit&&type==1){
            try {
                PreparedStatement statement = connection.prepareStatement("insert into renovation values(?,?,?,?,?)");
                statement.setString(1, Dno);
                statement.setInt(2, Integer.parseInt(pnoText.getText()));
                statement.setString(3, rsubmitText.getText());
                statement.setString(4, null);
                statement.setString(5, reasonText.getText());
                statement.executeUpdate();

                String select="select Dno from student where Sname"+"="+"'"+user.getName()+"'";
                PreparedStatement state=connection.prepareStatement(select);
                ResultSet resultSet=state.executeQuery();
                    while (resultSet.next()){
                        Dno=resultSet.getString("Dno");
                    }
                    select="select*from renovation  where Dno"+"="+"'"+Dno+"'";
                    state=connection.prepareStatement(select);
                    resultSet = state.executeQuery();

                while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount()-1);
                }

                    while (resultSet.next()){
                        String Dno=resultSet.getString(1);
                        String Pno=resultSet.getString(2);
                        String Rsubmint=resultSet.getString(3);
                        String Rsolve=resultSet.getString(4);
                        String Rseason=resultSet.getString(5);
                        String[] data={Dno,Pno,Rsubmint,Rsolve,Rseason};
                        mm.addRow(data);
                    }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
        if(e.getSource()==submit&&type==2){
            try {
                Statement statement = connection.createStatement();
                String sql="update renovation set Rsolve="+"'"+solveText.getText()+"'"+"where Dno"+"="+"'"+suseText.getText()+"'";
                statement.executeUpdate(sql);
                PreparedStatement state;
                ResultSet resultSet;
                state=connection.prepareStatement("select *from renovation");
                resultSet = state.executeQuery();
                while(mm.getRowCount()>0){//把表格进行刷新，下次显示的时候重头开始显示
                    //System.out.println(model.getRowCount());
                    mm.removeRow(mm.getRowCount()-1);
                }
                while (resultSet.next()){
                    String Dno=resultSet.getString(1);
                    String Pno=resultSet.getString(2);
                    String Rsubmint=resultSet.getString(3);
                    String Rsolve=resultSet.getString(4);
                    String Rseason=resultSet.getString(5);
                    String[] data={Dno,Pno,Rsubmint,Rsolve,Rseason};
                    mm.addRow(data);
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }
}
