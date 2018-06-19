package notepad;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import javax.management.monitor.MonitorSettingException;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.View;
import javax.swing.undo.UndoManager;

public class NotePadJframe extends JFrame{
	private JFrame jf;
	private Container c;
	private JMenuBar menu;
	 public JMenu fileMenu,editMenu,formatMenu,lookMenu,helpMenu;
	private JMenuItem newItem,openItem,saveItem,saveotherItem,exitItem,UndoItem,ClipItem,CopyItem,stickItem,deleteItem
	,findItem,findnextItem,replaceItem,turnItem,selectAllItem,timeItem,aboutItem,statusItem;
	private JCheckBoxMenuItem NewlineItem,fontItem;
	public static JTextArea noteja;
	private JScrollPane js;
	private boolean saved=false;//判断是否已经保存过
	private String fstr,namestr;//获得当前文件的绝对路径,获得文件名
	private File file;
	public NotePadJframe(){
		
		JFrame jf=new JFrame();
		Container c=jf.getContentPane();
		
		
		noteja=new JTextArea(100, 100);
		js=new JScrollPane(noteja);
		c.add(js);
		/**
		 * getDocument()获得doucument实例，通过注册 addUndoableEditListener
		 * (UndoableEditListener listener)方法实现撤销功能
		 */
		UndoManager u=new UndoManager();
		noteja.getDocument().addUndoableEditListener(u);
		
		menu=new JMenuBar();
		jf.setJMenuBar(menu);
		/**
		 * 文件菜单以及子菜单
		 */
		fileMenu=new JMenu("文件（F）");
		fileMenu.setMnemonic('F');
		menu.add(fileMenu);
		
		newItem=new JMenuItem("新建（N）");
		newItem.setMnemonic('N');
		newItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));//Ctrl+N
		newItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				if(noteja.getText().length()!=0){	
					if(saved==true){
						int r=JOptionPane.showInternalConfirmDialog(c, "是否将更改保存到"+fstr,"提示框"
								,JOptionPane.YES_NO_OPTION);//获得用户所点击的按钮，第一个按钮为0，以此类推
						if(r==0){
							SavedFile();
						}else if(r==1){
							noteja.setText("");
							saved=false;
							jf.setTitle("无标题-记事本");
						}
					}else{
						int r=JOptionPane.showInternalConfirmDialog(c, "是否保存","提示框"
								,JOptionPane.YES_NO_OPTION);//获得用户所点击的按钮，第一个按钮为0，以此类推
						if(r==0){
							SavedFile();
						}else if(r==1){
							noteja.setText("");
						}
					}
				}
			}
		});
		fileMenu.add(newItem);
		
		openItem=new JMenuItem("打开（O）");
		openItem.setMnemonic('O');
		openItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
		openItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JFileChooser filec=new JFileChooser();
				int returnV=filec.showOpenDialog(c);
				if(returnV==filec.APPROVE_OPTION){
					File file=filec.getSelectedFile();
					//noteja.setText("绝对路径："+file.getAbsolutePath()+"\n");
					try {
						FileReader fr=new FileReader(file);
						char[] ch=new char[(int) file.length()];
						fr.read(ch);
						String str=String.valueOf(ch).trim();
						noteja.setText(str);
						System.out.println(file.getName());
						jf.setTitle(file.getName().trim()+"-记事本");
						fr.close();
						
					} catch (Exception e1) {
						// TODO 自动生成的 catch 块
						e1.printStackTrace();
					}
				if(noteja.getText()!=null){
					saved=true;//设置该文件已保存过
					fstr=file.getAbsolutePath();//获得该文件的绝对路径
				}
				}
			}
		});
		fileMenu.add(openItem);
		
		saveItem=new JMenuItem("保存（S）");
		saveItem.setMnemonic('S');
		saveItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
		saveItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根				
				SavedFile();
				jf.setTitle(namestr+"-记事本");//把标题改为当前文件名
			}
		});
		fileMenu.add(saveItem);
		
		saveotherItem=new JMenuItem("另存为（A）");
		saveotherItem.setMnemonic('A');
		fileMenu.add(saveotherItem);
		saveotherItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
			try {
				JFileChooser jfile=new JFileChooser();
				int value=jfile.showSaveDialog(c);
				if( value==jfile.APPROVE_OPTION){
					File ff=jfile.getSelectedFile();
					FileWriter fw=new FileWriter(ff);
					String strr=noteja.getText();
					fw.write(strr);
					fw.close();
					fstr=ff.getAbsolutePath();	
					namestr=ff.getName();
					jf.setTitle(namestr+"-记事本");
					saved=true;
				}
			} catch (Exception e2) {
				e2.printStackTrace();
				// TODO: handle exception
			}							
			}
		});
		fileMenu.addSeparator();
		
		
		exitItem=new JMenuItem("退出（X）");
		exitItem.setMnemonic('X');
		exitItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				System.exit(0);
			}
		});
		fileMenu.add(exitItem);
		
		
		/**
		 * 编辑菜单和子菜单
		 */
		editMenu=new JMenu("编辑（E）");
		editMenu.setMnemonic('E');
		menu.add(editMenu);
		
		UndoItem=new JMenuItem("撤销（U）");
		UndoItem.setMnemonic('U');
		UndoItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				u.undo();
			}
		});
		editMenu.add(UndoItem);
		editMenu.addSeparator();
		
		
		ClipItem=new JMenuItem("剪切（T）");
		ClipItem.setMnemonic('T');
		ClipItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				noteja.cut();
			}
		});
		ClipItem.setEnabled(false);
		editMenu.add(ClipItem);
		
		CopyItem=new JMenuItem("复制（C）");
		CopyItem.setMnemonic('C');
		CopyItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				noteja.copy();
			}
		});
		CopyItem.setEnabled(false);
		editMenu.add(CopyItem);
		
		stickItem=new JMenuItem("粘贴（P）");
		stickItem.setMnemonic('P');
		stickItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				noteja.paste();
			}
		});
		editMenu.add(stickItem);
		
		deleteItem=new JMenuItem("删除（L）");
		deleteItem.setMnemonic('L');
		deleteItem.setEnabled(false);
		editMenu.add(deleteItem);
		editMenu.addSeparator();
		
		findItem=new JMenuItem("查找（D）");
		findItem.setMnemonic('D');
		findItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				new ReplaceFrame();
			}
		});
		editMenu.add(findItem);
		
		findnextItem=new JMenuItem("查找下一处（N）");
		findnextItem.setMnemonic('N');
		editMenu.add(findnextItem);
		
		replaceItem=new JMenuItem("替换（R）");
		replaceItem.setMnemonic('R');
		editMenu.add(replaceItem);
		
		turnItem=new JMenuItem("转到（G）");
		turnItem.setMnemonic('G');
		editMenu.add(turnItem);
		editMenu.addSeparator();
		
		selectAllItem=new JMenuItem("全选（A）");
		selectAllItem.setMnemonic('A');
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,InputEvent.CTRL_MASK));
		selectAllItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				noteja.selectAll();
				if(noteja.getText()!=null){
					deleteItem.setEnabled(true);
					ClipItem.setEnabled(true);
					CopyItem.setEnabled(true);
				}
			}
		});
		editMenu.add(selectAllItem);
		
		timeItem=new JMenuItem("时间（D）");
		timeItem.setMnemonic('D');
		timeItem.setAccelerator(KeyStroke.getKeyStroke("F5"));
		timeItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				Date date=new Date();
				String time=String.format("%tT", date);
				String day=String.format("%tF", date);
				String times=time+" "+day;
				String notes=noteja.getText();
				int pos=noteja.getCaretPosition();//获取当前光标的位置
				if(notes==null)
				noteja.setText(time+" "+times);
				else{
					//String str=notes+" "+times;
					noteja.insert(times, pos);//在光标的位置插入时间
				}
			}
		});
		editMenu.add(timeItem);
		
		formatMenu=new JMenu("格式（O）");
		formatMenu.setMnemonic('O');
		menu.add(formatMenu);
		
		NewlineItem=new JCheckBoxMenuItem("自动换行（W）");
		NewlineItem.setMnemonic('W');
		formatMenu.add(NewlineItem);
		
		fontItem=new JCheckBoxMenuItem("字体（F）");
		fontItem.setMnemonic('F');
		formatMenu.add(fontItem);
		
		lookMenu=new JMenu("查看（V）");
		lookMenu.setMnemonic('V');
		menu.add(lookMenu);
		
		statusItem=new JMenuItem("状态栏（S）");
		statusItem.setMnemonic('S');
		lookMenu.add(statusItem);
		
		helpMenu=new JMenu("帮助（H）");
		helpMenu.setMnemonic('H');
		menu.add(helpMenu);
		
		aboutItem=new JMenuItem("关于记事本（A）");
		aboutItem.setMnemonic('A');
		aboutItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				JDialog jd=new JDialog(jf, "关于", true);
				jd.setLayout(new GridLayout(4, 1));
				JLabel label1=new JLabel("记事本");
				JLabel label2=new JLabel("Version1.0");
				JLabel label3=new JLabel("JRE Version 1.8.0");
				JLabel label4=new JLabel("Author:柠檬百事");
				jd.setSize(250, 300);
				JButton jb=new JButton("确定");
				jb.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO 自动生成的方法存根
						jd.dispose();
					}
				});
				jd.add(label1);
				jd.add(label2);
				jd.add(label3);
				jd.add(label4);
				jd.add(jb);
				jd.setVisible(true);
			}
		});
		helpMenu.add(aboutItem);
		
		jf.setSize(600, 300);
		jf.setVisible(true);
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
	}
	
	public void SavedFile(){
		try {
			JFileChooser jfile=new JFileChooser();
			
		 if(saved==false){
			 int value=jfile.showSaveDialog(c);
			if( value==jfile.APPROVE_OPTION){
				File ff=jfile.getSelectedFile();
				FileWriter fw=new FileWriter(ff);
				String strr=noteja.getText();
				fw.write(strr);
				fw.close();
				saved=true;
				fstr=ff.getAbsolutePath();	
				namestr=ff.getName();
			}								
		}
		 else if(saved){
			 File fff=new File(fstr);
			 FileWriter ffw=new FileWriter(fff);
			 String strr=noteja.getText();
			 ffw.write(strr);
			 ffw.close();
			 namestr=fff.getName();
		 }
			}
		 catch (Exception e2) {
			// TODO: handle exception
		}
	}
	
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new NotePadJframe();
	}

}
