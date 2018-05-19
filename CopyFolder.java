package copyfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;

public class CopyFolder {

	public static void main(String[] args) {
		// TODO 自动生成的方法存根
		CopyFolder c=new CopyFolder();
		String oldfolder="D:\\File";
		String newfolder="D:\\newfile";
		File oldfile=new File(oldfolder);
		File newfile=new File(newfolder);
		if(!oldfile.isDirectory()||!oldfile.exists()){
			System.out.println("原文件夹不存在");
		}
		c.copyfolder(oldfile.listFiles(),newfile);
	}

	public void copyfolder(File[] listFiles, File newfile) {
		for(int i=0;i<listFiles.length;i++){
			if(listFiles[i].isFile()){
				try {
					/*System.out.println(newfile.getPath()+"     "+listFiles[i].getName());
					System.out.println(newfile.getPath()+File.separator+listFiles[i].getName());*/
					
					
					/*FileReader fr=new FileReader(listFiles[i]);
					FileWriter fw=new FileWriter(new File(newfile.getPath()+File.separator+listFiles[i].getName()));//File.separator是用来分隔同一个路径字符串中的目录的
					char[] ch=new char[(int) listFiles[i].length()];
					fr.read(ch);
					String str=String.valueOf(ch).trim();
					//System.out.println(str);
					fw.write(str);
					fr.close();
					fw.close();*/
					
					FileInputStream fi=new FileInputStream(listFiles[i]);
					FileOutputStream fo=new FileOutputStream(new File(newfile.getPath()+File.separator+
							listFiles[i].getName()));
					int count=fi.available();
					byte[] data=new byte[count];
					if((fi.read(data))!=-1){
						fo.write(data);
					}
					fi.close();
					fo.close();
				} catch (Exception e) {
					e.printStackTrace();
					// TODO: handle exception
				}
			}
			if(listFiles[i].isDirectory()){
				File file=new File(newfile.getPath()+File.separator+listFiles[i].getName());
				file.mkdir();
				copyfolder(listFiles[i].listFiles(), file);
			}
		}
		System.out.println("文件复制成功");
	}
}
