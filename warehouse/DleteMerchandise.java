package warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DleteMerchandise {
	GetCon c=new GetCon();
	Connection con=c.GetConnection();
	public void delteMerchandise(Merchandise mer){
		try {
			PreparedStatement state=con.prepareStatement("delete from warehouses where id=?");
			state.setInt(1, mer.getId());
			state.executeUpdate();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}
