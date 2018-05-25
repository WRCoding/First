package lotteryTest;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Lottery extends JFrame implements ActionListener{
	private JTextField infojt;
	private NumField[] numFields;
	private JButton startButton,stopButton,exitButton;
	private RandomNum t=new RandomNum();
	 static class NumField extends JTextField{
		private static final Font numfont=new Font("",Font.BOLD,48);
		public NumField(){
			super();
			setHorizontalAlignment(SwingConstants.CENTER);
			setFont(numfont);
			setFocusable(false);
		}
	}
	 public Lottery(){
		 JFrame jf=new JFrame("随机抽奖");
		 Container c=jf.getContentPane();
		 jf.setVisible(true);
		 jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		 jf.setBounds(200, 200, 520, 356);
		 
		 
		 JPanel contentPanel=new JPanel();
		 contentPanel.setLayout(new BorderLayout());
		 c.add(contentPanel);
		 JPanel numPanel=new JPanel();
		 numPanel.setLayout(new GridLayout(1, 5));
		 numFields=new NumField[5];
		 for(int i=0;i<numFields.length;i++){
			 numFields[i]=new NumField();
			 numPanel.add(numFields[i]);
		 }
		 
		 contentPanel.add(numPanel);
		 
		 JPanel infoPanel=new JPanel();
		 infoPanel.setLayout(new BorderLayout());
		 JLabel infojl=new JLabel("随机抽奖的中奖号码是：");
		 infojl.setFont(new Font("", Font.BOLD, 20));
		 infoPanel.add(infojl, BorderLayout.WEST);
		 infojt=new JTextField(10);
		 infoPanel.add(infojt);
		 contentPanel.add(infoPanel,BorderLayout.SOUTH);
		 
		 JLabel showjl=new JLabel("随机抽奖");
		 showjl.setFont(new Font("隶书", Font.BOLD, 72));
		 showjl.setHorizontalAlignment(SwingConstants.CENTER);
		 c.add(showjl, BorderLayout.NORTH);
		 
		 JPanel buttonPanel=new JPanel();
		 buttonPanel.setLayout(new FlowLayout());
		  startButton=new JButton("开始");
		 startButton.addActionListener(this);
		 stopButton=new JButton("停止");
		 stopButton.addActionListener(this);
		 exitButton=new JButton("退出");
		 exitButton.addActionListener(this);
		 
		 buttonPanel.add(startButton);
		 buttonPanel.add(stopButton);
		 buttonPanel.add(exitButton);
		 
		 c.add(buttonPanel, BorderLayout.SOUTH);
	 }
	 class RandomNum extends Thread{
		 private boolean stop=false;
		 public void run(){
			 while(!stop){
				 for (NumField numField : numFields) {
					try {
						Thread.sleep(1);
/*						
						numField.setText(num+"");*/
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					int num=(int) (Math.random()*10);
					
					EventQueue.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							// TODO 自动生成的方法存根
							numField.setText(num+"");
						}
					});
					
				}
			 }
		 }
		 public void stopLottery(){
			 this.stop=true;
		 }
	 }
	 public void actionPerformed(ActionEvent e) {
			// TODO 自动生成的方法存根
		 if(e.getSource()==exitButton){
			 System.exit(0);
		 }
		if(e.getSource()==startButton){
			if(t!=null){
				t.stopLottery();
			}
			t=new RandomNum();
			t.start();
		}
		if(e.getSource()==stopButton){
			if(t!=null){
				t.stopLottery();
			}
			try {
				t.join();
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					// TODO 自动生成的方法存根
					String code="";
					for (NumField numField : numFields) {
						System.out.println(numField.getText());
						code=code+numField.getText();
					}
					infojt.setText(code);
					//System.out.println(code);
				}
			});
		/*	String code="";
			for (NumField numField : numFields) {
				System.out.println(numField.getText());
				code=code+numField.getText();
			}
			infojt.setText(code);
			System.out.println(code);*/
		}
		}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new Lottery();
	}
	

}
