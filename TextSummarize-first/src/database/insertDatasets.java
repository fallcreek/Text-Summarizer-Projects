package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import sum.data.Sentence;
import sum.data.WaitingSentences;

public class insertDatasets {

	
	public void insertStructuralData(String name, WaitingSentences selectSent)
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			
			for(int asp = 1; asp <= 14; asp++)
			{
				PreparedStatement pre = conn
						.prepareStatement("select id from rawset");
				
				ResultSet rs = pre.executeQuery(); //执行query		
				rs.last(); //移到最后一行   
				int rowCount = rs.getRow(); //得到当前行号，也就是记录数   
				rs.beforeFirst();
				
				String query = "insert into rawset(id,phonename,aspectname,`text`,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,orilength) values";
			
				ArrayList<Sentence> sentences = selectSent.getSentList().get(asp);
				
				for(Sentence s : sentences)
				{
					query += "(?,?,?,?,?,?,?,?,?,?,?,?),";
				}
				query = query.substring(0, query.length() - 1);
				System.out.println(query);
				
				PreparedStatement ps = conn
						.prepareStatement(query);
				
				int i = 1;
				int id = rowCount + 1;
				for(Sentence s : sentences)
				{
					ps.setInt(i++,id++);
					ps.setString(i++, name);
					ps.setInt(i++, asp);
					ps.setString(i++, s.getText());
					
					if(s.isHasProductName())
						ps.setInt(i++, 1);
					else
						ps.setInt(i++, 0);
					
					if(s.isHasOtherProduct())
						ps.setInt(i++, 1);
					else
						ps.setInt(i++, 0);
					
					if(s.isTitleWithKW())
						ps.setInt(i++, 1);
					else
						ps.setInt(i++, 0);
					
					ps.setInt(i++, s.getKNumber());
					ps.setInt(i++, s.getaNumber());
					ps.setInt(i++, s.getnNumber());
					ps.setInt(i++, s.getAspectNumber());
					ps.setInt(i++, s.getLength());
					
				}
				
				ps.execute();
			}
	
			conn.commit(); //提交对数据库的修改
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新信息时出错！");
		}
	}
	
	
	public void insert(ArrayList<Sentence> sentences)
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			PreparedStatement pre = conn
					.prepareStatement("select id from trainset");
			
			ResultSet rs = pre.executeQuery(); //执行query		
			rs.last(); //移到最后一行   
			int rowCount = rs.getRow(); //得到当前行号，也就是记录数   
			rs.beforeFirst();
			
			String query = "insert into trainset(id,`text`,hasname,hasothername,titlewithkeyword,oriknumber,orianumber,orinnumber,oriaspectnumber,orilength) values";
			for(Sentence s : sentences)
			{
				query += "(?,?,?,?,?,?,?,?,?,?),";
			}
			query = query.substring(0, query.length() - 1);
			System.out.println(query);
			
			PreparedStatement ps = conn
					.prepareStatement(query);
			
			int i = 1;
			int id = rowCount + 1;
			for(Sentence s : sentences)
			{
				ps.setInt(i++,id++);
				ps.setString(i++, s.getText());
				
				if(s.isHasProductName())
					ps.setInt(i++, 1);
				else
					ps.setInt(i++, 0);
				
				if(s.isHasOtherProduct())
					ps.setInt(i++, 1);
				else
					ps.setInt(i++, 0);
				
				if(s.isTitleWithKW())
					ps.setInt(i++, 1);
				else
					ps.setInt(i++, 0);
				
				ps.setInt(i++, s.getKNumber());
				ps.setInt(i++, s.getaNumber());
				ps.setInt(i++, s.getnNumber());
				ps.setInt(i++, s.getAspectNumber());
				ps.setInt(i++, s.getLength());
				
			}
			
			
			ps.execute();
			
			conn.commit(); //提交对数据库的修改
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("更新信息时出错！");
		}
	}
	
	public void insertAspect(ArrayList<Sentence> sentences)
	{
		Connection conn = null;
		
		try {		
			ConnectionFactory cf = ConnectionFactory.getInstance();
			conn = cf.makeConnection();
			conn.setAutoCommit(false); //关闭事务的自动提交

			PreparedStatement pre = conn
					.prepareStatement("select id from aspectrecord");
			
			ResultSet rs = pre.executeQuery(); //执行query		
			rs.last(); //移到最后一行   
			int rowCount = rs.getRow(); //得到当前行号，也就是记录数   
			rs.beforeFirst();
			
			String query = "insert into aspectrecord(id,`text`,oriknumber) values";
			for(Sentence s : sentences)
			{
				query += "(?,?,?),";
			}
			query = query.substring(0, query.length() - 1);
			System.out.println(query);
			
			PreparedStatement ps = conn
					.prepareStatement(query);
			
			int i = 1;
			int id = rowCount + 1;
			for(Sentence s : sentences)
			{
				ps.setInt(i++,id++);
				ps.setString(i++, s.getText());
				ps.setInt(i++, s.getAspectNumber());			
			}
			
			ps.execute();
			
			conn.commit(); //提交对数据库的修改
			
		} catch (Exception e) {
			//e.printStackTrace();
			System.out.println("更新信息时出错！");
		}
	}
	
}
