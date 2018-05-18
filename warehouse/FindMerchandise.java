package warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class FindMerchandise {
	GetCon c=new GetCon();
	Connection con=c.GetConnection();
	public Merchandise findMerchandise(Merchandise mer){
		Merchandise mers=null;
		try {
			PreparedStatement state=con.prepareStatement("select*from warehouses where id=?");
			state.setInt(1, mer.getId());
			ResultSet res=state.executeQuery();
			while(res.next()){
				mers=new Merchandise();
				mers.setId(res.getInt("id"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return mers;
		
	}
}
