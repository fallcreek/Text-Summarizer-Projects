package JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import entity.question;
import util.ConnectionFactory;

public class searchQuestion {

	public static question searchOneQuestion(String username, int id)
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			String sql = "select id,product,aspect,mmr,mlp,ilp,"
					+ username
					+ " from question "
					+ "where id = " + id;
					
			PreparedStatement ps = conn
					.prepareStatement(sql);
//			System.out.println(sql);
			ResultSet rs = ps.executeQuery(); //执行query
			
				
			rs.last(); //移到最后一行   
			int rowCount = rs.getRow(); //得到当前行号，也就是记录数   
			rs.beforeFirst();
			
			
			if(rowCount == 0)
			{
				System.out.println("找不到该问题");
				return null;
			}
			question q = new question();
			
			while(rs.next())
			{
				q.setId(rs.getInt("id"));
				q.setProductname(rs.getString("product"));
				q.setAspect(rs.getInt("aspect"));
				
				q.setMmr(question.getList(rs.getString("mmr")));
				q.setMlp(question.getList(rs.getString("mlp")));
				q.setIlp(question.getList(rs.getString("ilp")));
				
			}
			
			rs.close();
			ps.close();
			conn.close();
			
			return q;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static List<question> searchAllQuestion(String username)
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			String sql = "select id,product,aspect,"
					+ username
					+ " from question";
			
			PreparedStatement ps = conn
					.prepareStatement(sql);
//			System.out.println(sql);
			ResultSet rs = ps.executeQuery(); //执行query
			
				
			rs.last(); //移到最后一行   
			int rowCount = rs.getRow(); //得到当前行号，也就是记录数   
			rs.beforeFirst();
			
			
			if(rowCount == 0)
			{
				System.out.println("该用户已经完成全部问题");
				rs.close();
				ps.close();
				conn.close();
				
				return null;
			}
			List<question> qlist = new ArrayList<question>();	
			
			while(rs.next())
			{
				question q = new question();
				q.setId(rs.getInt("id"));
				q.setProductname(rs.getString("product"));
				q.setAspect(rs.getInt("aspect"));
				
				if(rs.getString(username).equals("-1"))
					q.setAnswer(false);
				else
					q.setAnswer(true);
				qlist.add(q);
			}
			rs.close();
			ps.close();
			conn.close();
			return qlist;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static boolean complete(int id,String username,String q1,String q2,String q3)
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			String sql = "update question set "
						+ username
						+ " = '"
						+ q1 + ";" + q2 + ";" + q3
						+ "' where id = " + id;
			
			
			PreparedStatement ps = conn
					.prepareStatement(sql);
			ps.execute();
			System.out.println(sql);
			
			conn.commit();
			
			ps.close();
			conn.close();
			return true;
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	public static question search(String username)
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			String sql = "select id,product,aspect,mmr,mlp,ilp,"
					+ username
					+ " from question "
					+ "where "
					+ username
					+ " = -1 limit 0,1";
			PreparedStatement ps = conn
					.prepareStatement(sql);
//			System.out.println(sql);
			ResultSet rs = ps.executeQuery(); //执行query
			
				
			rs.last(); //移到最后一行   
			int rowCount = rs.getRow(); //得到当前行号，也就是记录数   
			rs.beforeFirst();
			
			
			if(rowCount == 0)
			{
				System.out.println("该用户已经完成全部问题");
				return null;
			}
			question q = new question();
			
			while(rs.next())
			{
				q.setId(rs.getInt("id"));
				q.setProductname(rs.getString("product"));
				q.setAspect(rs.getInt("aspect"));
				
				q.setMmr(question.getList(rs.getString("mmr")));
				q.setMlp(question.getList(rs.getString("mlp")));
				q.setIlp(question.getList(rs.getString("ilp")));
				
			}
			
			rs.close();
			ps.close();
			conn.close();
			return q;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
