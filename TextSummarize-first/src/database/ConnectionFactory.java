package database;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;


public class ConnectionFactory {
	private static String driver;
	private static String dburl;
	private static String user;
	private static String password;
	
	private static final ConnectionFactory factory = new ConnectionFactory();
	private Connection conn;
	
	static
	{
//		Properties prop = new Properties();
//		try {
//			InputStream in = ConnectionFactory.class.getClassLoader()
//					.getResourceAsStream("conf/dbconfig.properties");
//			prop.load(in);
//		} catch (Exception e) {
//			System.out.println("error1");
//			e.printStackTrace();
//		}
		
		driver = "com.mysql.jdbc.Driver";	
		dburl = "jdbc:mysql://127.0.0.1:3306/datasets?useUnicode=true&characterEncoding=utf-8";
		user = "root";
		password = "sa";
		System.out.println(driver+dburl+user+password);
	}
	
	private ConnectionFactory()
	{
		
	}
	
	public static ConnectionFactory getInstance()
	{
		return factory;
	}
	
	public Connection makeConnection()
	{
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(dburl,user,password);
		} catch (Exception e) {
			System.out.println("error2");
			e.printStackTrace();
		}
		return conn;
	}
}
