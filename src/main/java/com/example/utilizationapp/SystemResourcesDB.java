package com.example.utilizationapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SystemResourcesDB {
	

	private static String username="root";
	private static String password="password";
	private static String dbname ="mysql";
	private static String localhostport ="mysql:3306"; // "localhost:3306"; //
//	private static String driver= "com.mysql.jdbc.Driver"; // com.mysql.cj.jdbc.Driver

	
	
	public boolean deleteImage(String album, String filename) {
		boolean rs=false;
		try {
			String URL = "jdbc:mysql://"+localhostport+"/"+dbname;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(URL, username, password);
			Statement stmt = con.createStatement();
			
			System.out.println("Delete FROM ImageBlob where imagename= \""+album+"\" AND imagename = \""+filename+"\" ;");
			rs = stmt.execute("Delete FROM ImageBlob where imagename= \""+filename+"\" AND albumname = \""+album+"\" ;");
			con.close();
			} catch (Exception e) {
			System.out.println(e);
			}
		return rs;
	}
	
	public boolean deleteImageFolder(String album) {
		boolean rs=false;
		try {
			String URL = "jdbc:mysql://"+localhostport+"/"+dbname;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(URL, username, password);
			Statement stmt = con.createStatement();
			
			System.out.println("DELETE FROM ImageBlob WHERE albumname= \""+album+"\" ;");
			rs = stmt.execute("DELETE FROM ImageBlob WHERE albumname= \""+album+"\" ;");
			con.close();
			} catch (Exception e) {
			System.out.println(e);
			}
		return rs;
	}
	
	public String storeImage(String album, byte[] content, String filename)  {
		
		try {
		
			String URL = "jdbc:mysql://"+localhostport+"/"+dbname;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(URL, username, password);
			Statement stmt = con.createStatement();
			
			PreparedStatement  statement = con.prepareStatement("INSERT INTO ImageBlob (imagename ,imgae, albumname) " + "values(?,?,?)");
            statement.setString(1, filename);
            statement.setObject(2, content);
            statement.setString(3, album);
            
            int res =statement.executeUpdate();
            System.out.println("1 means success"+res);
			
			System.out.println("INSERT INTO ImageBlob VALUES (\" "+filename+" \", \" "+content+" \", \""+album+"\" );");
		//	boolean rs = stmt.execute("INSERT INTO ImageBlob VALUES (\" "+filename+" \", \" "+content+" \", \""+album+"\" );");
		//	System.out.println(rs);
			con.close();
			} catch (Exception e) {
			System.out.println(e);
			return "Query Failed";
		}
		return "success";

	}
	
	public List<ImageStore> showImagebasedonAlbum(String album) {
		List<ImageStore> list = new ArrayList<>();
		try {
			String URL = "jdbc:mysql://"+localhostport+"/"+dbname;
			
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection(URL, username, password);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select * from ImageBlob where albumname = "+"\""+album+"\";");
			while (rs.next()){
//				System.out.println(rs.getString(1)+"  "+rs.getBytes(2)+"  "+rs.getString(3));
//				
//				byte[] b1 = rs.getBytes(2);
//				BufferedImage image = ImageIO.read(new ByteArrayInputStream(b1));
//				
//				ImageIO.write(image, "JPEG", new File("\\images\\"+"a.jpg"));
//				
//				Blob blob = (Blob) rs.getBlob(2);
//
//				int blobLength = (int) blob.length();  
//				byte[] b = blob.getBytes(1, blobLength);
//				
//				
//				System.out.println(">>>>>>>>>>>>"+new ByteArrayInputStream(b));
//				
//				 image = ImageIO.read(new ByteArrayInputStream(b));
//	
//				ImageIO.write(image, "JPEG", new File("\\images\\"+album+"\\"+"aaa.jpg")); // C:\images\
				
				ImageStore img = new ImageStore(rs.getString(1),  rs.getString(3), rs.getBytes(2));
				list.add(img);
			}
			con.close();
			} catch (Exception e) {
			System.out.println(e);
		}
		return list;
	}
	
}
