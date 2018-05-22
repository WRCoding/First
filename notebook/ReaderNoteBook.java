package notebook;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class ReaderNoteBook {
	public String readerNoteBook(){
		String str=null;
		File file=new File("D:\\File\\kang\\notebook.txt");
		try {
			FileReader fr=new FileReader(file);
			char[]ch=new char[(int)file.length()];
			fr.read(ch);
/*			str=ch.toString();
			str=Arrays.toString(ch);*/
			str=String.valueOf(ch).trim();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		return str;		
	}
}
