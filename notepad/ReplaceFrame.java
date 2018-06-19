package notepad;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.annotation.Repeatable;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.View;

public class ReplaceFrame extends JFrame{
	JLabel findJlabel,replaceJlable;
	JTextField findtext,replacetext;
	JButton findnextButton,replaceButton,cancelButton;
	JPanel jp1,jp2;
	int count;
	public ReplaceFrame(){
		JFrame jf=new JFrame("查找");
		Container c=jf.getContentPane();
		c.setLayout(new BorderLayout());
		findJlabel=new JLabel("查找内容：");
		findtext=new JTextField(10);
		replaceJlable=new JLabel("替换为：");
		replacetext=new JTextField(10);
		jp1=new JPanel();
		jp1.setLayout(new GridLayout(2, 2));
		jp1.add(findJlabel);
		jp1.add(findtext);
		jp1.add(replaceJlable);
		jp1.add(replacetext);
		
		findnextButton=new JButton("查找下一处");
		findnextButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String str=NotePadJframe.noteja.getText();
				String strr=findtext.getText();
				/**
				 * 获得要查找字符的位置
				 */
				int selectionStart=str.indexOf(strr,count);
				int selectionEnd=str.indexOf(strr,count)+1;
				count=str.indexOf(strr,count)+1;//记录下一个查找的起始位置，避免一直找第一个
				if(count>0)
				//System.out.println(str.charAt(count-1));
				NotePadJframe.noteja.select(selectionStart, selectionEnd);//选中要查找的字符
				NotePadJframe.noteja.setSelectionColor(Color.BLUE);//将选中字符的背景选为蓝色
				if(count==0){
					JOptionPane.showMessageDialog(jf, "没有查找到  "+strr+" 字","记事本",JOptionPane.WARNING_MESSAGE);
				}
				
			}
		});
		replaceButton=new JButton("替换");
		replaceButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				String str=NotePadJframe.noteja.getText();
				String strr=findtext.getText();//获得要替换的字符位置
				/**
				 * 获得要替换字符的位置
				 */
				int selectionStart=str.indexOf(strr,count);
				int selectionEnd=str.indexOf(strr,count)+1;
				count=str.indexOf(strr,count)+1;
				String s=replacetext.getText();//获得要替换成的字符
				if(count>0)
				NotePadJframe.noteja.select(selectionStart, selectionEnd);
				NotePadJframe.noteja.setSelectionColor(Color.BLUE);
				NotePadJframe.noteja.replaceRange(s, selectionStart, selectionEnd);//替换字符
				if(count==0){
					JOptionPane.showMessageDialog(jf, "没有查找到  "+strr+" 字","记事本",JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		cancelButton=new JButton("取消");
		cancelButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
			}
		});
		jp2=new JPanel();
		jp2.setLayout(new GridLayout(3, 1));
		jp2.add(findnextButton);
		jp2.add(replaceButton);
		jp2.add(cancelButton);
		
		c.add(jp1,BorderLayout.WEST);
		c.add(jp2,BorderLayout.EAST);
			
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.DISPOSE_ON_CLOSE);
	}
}