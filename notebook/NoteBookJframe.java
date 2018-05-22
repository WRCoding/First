package notebook;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NoteBookJframe extends JFrame implements ActionListener{
	private JTextArea addja,showja;
	private JButton addjb,showjb;
	private JPanel addjp,showjpp;
	private JScrollPane showjp;
	public NoteBookJframe(){
		JFrame jf=new JFrame("简易记事本一");
		Container c=jf.getContentPane();
		c.setLayout(new GridLayout(2, 1));
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		
		addja=new JTextArea(10, 30);
		addjb=new JButton("添加");
		addjb.addActionListener(this);
		addjp=new JPanel(new GridLayout(2, 1));
		addjp.add(addja);
		addjp.add(addjb);
		
		showja=new JTextArea(10, 30);
		showjb=new JButton("查看");
		showjb.addActionListener(this);
		showjp=new JScrollPane();
		showjp.setViewportView(showja);
		//showjp.add(showjb);
		showjpp=new JPanel(new GridLayout(2, 1));
		showjpp.add(showjp);
		showjpp.add(showjb);
		c.add(addjp);
		c.add(showjpp);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==addjb){
			if(addja.getText().equals("")){
				JOptionPane.showMessageDialog(this, "未输入信息","提示框",JOptionPane.WARNING_MESSAGE);
			}else{
				String str=addja.getText();
				new WriteNoteBook(str);
				JOptionPane.showMessageDialog(this, "添加成功","提示框",JOptionPane.WARNING_MESSAGE);
				addja.setText("");
			}
		}
		if(e.getSource()==showjb){
			ReaderNoteBook rn=new ReaderNoteBook();
			String str=rn.readerNoteBook();
			showja.setText(str);
		}
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new NoteBookJframe();
	}


}
