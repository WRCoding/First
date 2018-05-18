package warehouse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ShowMerchandise {
	GetCon c=new GetCon();
	Connection con=c.GetConnection();
	public ArrayList<Merchandise> showMerchandise(){
		ArrayList<Merchandise>list=new ArrayList<>();
		try {

			PreparedStatement state=con.prepareStatement("select *from warehouses");
			ResultSet res=state.executeQuery();
			while(res.next()){
				Merchandise mer=new Merchandise();
				mer.setId(res.getInt("id"));
				mer.setName(res.getString("name"));
				mer.setPrice(res.getString("price"));
				mer.setStock(res.getString("stock"));
				list.add(mer);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return list;
	}
}
