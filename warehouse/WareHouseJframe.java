package warehouse;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class WareHouseJframe extends JFrame implements ActionListener{
	private JLabel idjl,namejl,pricejl,stockjl,deletjl;
	private JTextField idjt,namejt,pricejt,stockjt,deletjt;
	private JTextArea showja;
	private JButton addjb,deletjb,showjb;
	private JScrollPane js;
	private JPanel jp;
	private JTable table;
	private DefaultTableModel model;
	/**
	 * 
	 */
	public WareHouseJframe(){
		JFrame jf=new JFrame("商品库存管理");
		Container c=jf.getContentPane();
		GridBagLayout grid=new GridBagLayout();
		jp=new JPanel();

		jp.setLayout(grid);
		c.setLayout(new BorderLayout());
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setVisible(true);
		GridBagConstraints gr=new GridBagConstraints();
		gr.gridx=0;
		gr.gridy=4;
		idjl=new JLabel("商品编号：");
		grid.setConstraints(idjl, gr);
		gr.gridx=1;
		gr.gridy=4;
		idjt=new JTextField(10);
		grid.setConstraints(idjt, gr);
		jp.add(idjl);
		jp.add(idjt);
		
		
		
		gr.gridx=0;
		gr.gridy=3;
		namejl=new JLabel("商品名称：");
		grid.setConstraints(namejl, gr);
		gr.gridx=1;
		gr.gridy=3;
		namejt=new JTextField(10);
		grid.setConstraints(namejt, gr);
		jp.add(namejl);
		jp.add(namejt);
		
		gr.gridx=0;
		gr.gridy=2;
		pricejl=new JLabel("商品价格：");
		grid.setConstraints(pricejl, gr);
		gr.gridx=1;
		gr.gridy=2;
		pricejt=new JTextField(10);
		grid.setConstraints(pricejt, gr);
		jp.add(pricejl);
		jp.add(pricejt);
		
		
		
		gr.gridx=0;
		gr.gridy=1;
		stockjl=new JLabel("商品库存：");
		grid.setConstraints(stockjl, gr);
		gr.gridx=1;
		gr.gridy=1;
		stockjt=new JTextField(10);
		grid.setConstraints(stockjt, gr);
		jp.add(stockjl);
		jp.add(stockjt);
		
		gr.gridx=1;
		gr.gridy=5;
		addjb=new JButton("添加商品");
		addjb.addActionListener(this);
		grid.setConstraints(addjb, gr);
		jp.add(addjb);
		
		
		gr.gridx=0;
		gr.gridy=7;
		deletjl=new JLabel("要删除的商品编号：");
		grid.setConstraints(deletjl, gr);
		gr.gridx=1;
		gr.gridy=7;
		deletjt=new JTextField(10);
		grid.setConstraints(deletjt, gr);
		gr.gridx=1;
		gr.gridy=8;
		deletjb=new JButton("删除该商品");
		deletjb.addActionListener(this);
		grid.setConstraints(deletjb, gr);
		jp.add(deletjl);
		jp.add(deletjt);
		jp.add(deletjb);
		
		gr.gridx=1;
		gr.gridy=11;
		showjb=new JButton("查看商品");
		grid.setConstraints(showjb, gr);
		showjb.addActionListener(this);
		jp.add(showjb);
		

		String[] col={"商品库存","商品价格","商品名称","商品编号"};
		model=new DefaultTableModel(col, 0);
		table=new JTable(model);
		table.setRowSorter(new TableRowSorter<>(model));
		js=new JScrollPane(table);
		
		
		c.add(jp,BorderLayout.WEST);
		c.add(js,BorderLayout.EAST);
	}
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
		if(e.getSource()==addjb){
			if(idjt.getText().equals("")||namejt.getText().equals("")||pricejt.getText().equals("")||
					stockjt.getText().equals("")){
				JOptionPane.showMessageDialog(this, "请输入完整的商品信息","提示框",JOptionPane.WARNING_MESSAGE);
			}else{
				Merchandise mer=new Merchandise();
				mer.setId(Integer.parseInt(idjt.getText()));
				mer.setName(namejt.getText());
				mer.setPrice(pricejt.getText());
				mer.setStock(stockjt.getText());
				FindMerchandise findmers=new FindMerchandise();
				Merchandise merss=findmers.findMerchandise(mer);
				if(merss==null){
					Addmerchandise addmer=new Addmerchandise();
					Merchandise mers=addmer.addMerchandise(mer);
					JOptionPane.showMessageDialog(this, "商品添加成功","提示框",JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "该商品已存在","提示框",JOptionPane.WARNING_MESSAGE);
				}
				//System.out.println("添加成功！");
			}
		}
		if(e.getSource()==showjb){
			StringBuffer stb=new StringBuffer();
			ShowMerchandise showmer=new ShowMerchandise();
			ArrayList<Merchandise>list=showmer.showMerchandise();
			Merchandise mer=new Merchandise();
			for(int i=0;i<list.size();i++){
				String[]row={String.valueOf(mer.getId()),mer.getName(),
						String.valueOf(mer.getPrice()),String.valueOf(mer.getStock())};
				model.addRow(row);
			}
		}
		if(e.getSource()==deletjb){
			if(deletjt.getText().equals("")){
				JOptionPane.showMessageDialog(this, "请输入要删除的商品编号","提示框",JOptionPane.WARNING_MESSAGE);
			}else{
				Merchandise mer=new Merchandise();
				mer.setId(Integer.parseInt(deletjt.getText()));
				FindMerchandise findmer=new FindMerchandise();
				Merchandise mers=findmer.findMerchandise(mer);
				if(mers!=null){
					DleteMerchandise dlemer=new DleteMerchandise();
					dlemer.delteMerchandise(mer);
					JOptionPane.showMessageDialog(this, "商品删除成功","提示框",JOptionPane.WARNING_MESSAGE);
				}else{
					JOptionPane.showMessageDialog(this, "没有该商品编号","提示框",JOptionPane.WARNING_MESSAGE);
				}
			}			
		}
	}
	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		new WareHouseJframe();
	}
	

}
