import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**登录界面
 * @author LB
 * @create 2018-12-24 15:58
 */
public class login extends JFrame implements ActionListener {
    JLabel user, password;
    JTextField username;
    JPasswordField passwordField;
    JButton loginButton;
    CardLayout cardLayout = new CardLayout();
    JPanel card;
    JPanel cardPanel;
    JTabbedPane jTabbedPane;
    int type=1;
    Users users;



    public login() {
        init();
    }

    private void init() {
        setTitle("宿舍管理系统");
        setLayout(new BorderLayout());
        user = new JLabel("用户名");
        password = new JLabel("密码");

        card = new JPanel(cardLayout);

        JPanel panel1 = new JPanel(new BorderLayout());

        username = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("登录");
        loginButton.addActionListener(this);

        JPanel titlepanel = new JPanel(new FlowLayout());//标题面板
        JLabel title = new JLabel("学生宿舍管理系统");
        titlepanel.add(title);

        JPanel loginpanel = new JPanel();//登录面板
        loginpanel.setLayout(null);

        user.setBounds(50, 20, 50, 20);
        password.setBounds(50, 60, 50, 20);
        username.setBounds(110, 20, 120, 20);
        passwordField.setBounds(110, 60, 120, 20);
        loginpanel.add(user);
        loginpanel.add(password);
        loginpanel.add(username);
        loginpanel.add(passwordField);

        panel1.add(titlepanel, BorderLayout.NORTH);
        panel1.add(loginpanel, BorderLayout.CENTER);
        panel1.add(loginButton, BorderLayout.SOUTH);


        card.add(panel1, "login");
        //card.add(cardPanel, "info");

        add(card);
        setBounds(600, 200, 900, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        new login();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean flag=false;//用来标志用户是否正确

        if (e.getSource() == loginButton) {
            ArrayList<Users> list = new CheckUsers().getUsers();//获得所有用户信息
            for (int i = 0; i < list.size(); i++) {//遍历所有用户信息，以此来判断输入的信息是否正确
                users = list.get(i);
                String passwordStr = new String(passwordField.getPassword());
                if (username.getText().equals(users.getName()) && passwordStr.equals(users.getPassword())) {
                    if(users.getType()==1){//如果时学生
                        type=users.getType();
                        JOptionPane.showMessageDialog(null, "欢迎登录(学生)", "学生宿舍管理系统", JOptionPane.PLAIN_MESSAGE);
                    }else{//如果时宿管
                        type=users.getType();
                        System.out.println(type);
                        JOptionPane.showMessageDialog(null, "欢迎登录(宿管)", "学生宿舍管理系统", JOptionPane.PLAIN_MESSAGE);
                    }
                    flag = true;
                    break;//如果信息正确就退出遍历，提高效率
                }
            }
            if(!flag){//信息不正确，重新输入
                JOptionPane.showMessageDialog(null, "请输入正确的用户名或密码", "警告",JOptionPane.WARNING_MESSAGE);
                username.setText("");
                passwordField.setText("");
            }else{
                //当输入的信息正确时，就开始加载选项卡界面，并把选项卡界面加入到卡片布局器中
                DormitoryInfo dormitoryInfo = new DormitoryInfo(users,type);//宿舍信息
                Express express = new Express(type,users);//快件信息
                Renovation renovation = new Renovation(type,users);//维修信息
                OutAndIn outAndIn = new OutAndIn(type,users);//学生离校和回校信息
                Things things=new Things(type,users);//宿舍物品信息
                Later later = new Later(type,users);//晚归信息
                cardPanel = new JPanel();
                jTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
                jTabbedPane.add("宿舍信息", dormitoryInfo);
                jTabbedPane.add("快件信息", express);
                jTabbedPane.add("维修信息", renovation);
                jTabbedPane.add("学生离校与返校", outAndIn);
                jTabbedPane.add("晚归记录", later);
                jTabbedPane.add("宿舍物品", things);
                cardPanel.add(jTabbedPane);
                card.add(cardPanel, "info");
                cardLayout.show(card, "info");//输入信息正确就显示操作界面，否则重新输入正确信息
            }
        }
    }
}
