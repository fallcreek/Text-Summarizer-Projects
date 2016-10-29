package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.ConnectionFactory;

public class searchUserDatabase {

	public String search()
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			PreparedStatement ps = conn
					.prepareStatement("select * from testtable");
			
			
			ResultSet rs = ps.executeQuery(); //执行query
			
				
			rs.last(); //移到最后一行   
			int rowCount = rs.getRow(); //得到当前行号，也就是记录数   
			rs.beforeFirst();
			
			
			if(rowCount == 0)
			{
				System.out.println("查询不到该用户");
				rs.close();
				ps.close();
				conn.close();
				return "";
			}
			String result = "";
			
			while(rs.next())
			{
				result += rs.getString("id");
			}
			rs.close();
			ps.close();
			conn.close();
			return result;
			
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
}
