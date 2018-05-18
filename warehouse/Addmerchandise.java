package warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Addmerchandise {
	GetCon c=new GetCon();
	Connection con=c.GetConnection();
	public Merchandise addMerchandise(Merchandise mer){
		Merchandise mers=new Merchandise();
		try {
			PreparedStatement state=con.prepareStatement("insert into warehouses values(?,?,?,?)");
			state.setInt(1,mer.getId());
			state.setString(2, mer.getName());
			state.setString(3, mer.getPrice());
			state.setString(4, mer.getStock());
			state.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return mers;		
	}
}
