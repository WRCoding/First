package notebook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

public class WriteNoteBook {
	public WriteNoteBook(String str){
		File file=new File("D:\\File\\kang\\notebook.txt");
		try {
			FileWriter fw=new FileWriter(file,true);
			Date time=new Date();
/*			String timestr=String.format("%tF",time);
			String datestr=String.format("%tT", time);*/
			String timerstr=time.toString();
			fw.write(str);
			fw.write("\r\n");
			fw.write(timerstr);
			//fw.write(timestr+" "+datestr);
			fw.write("\r\n");
			fw.write("------------------------");
			fw.write("\r\n");

			fw.close();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
