package com.anshul.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.anshul.model.User;

@Repository
public class HomeDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insertInfo(User user) throws SQLException {
	
		Connection con=jdbcTemplate.getDataSource().getConnection();
		PreparedStatement pst=con.prepareStatement("insert into stu values(?,?)");
		pst.setInt(1,12);
		pst.setString(2,"planet parth code");
		int i=pst.executeUpdate();
		System.out.println(i);

		con.close();
	}

	public String getInfo() {
		 String s=null;
		  Connection con = null;
		     try {
			 con=jdbcTemplate.getDataSource().getConnection();
			PreparedStatement pst=con.prepareStatement("select * from stu where id=?");
			pst.setInt(1,12);
			ResultSet rs=pst.executeQuery();
			while(rs.next())
			{
				System.out.println(rs.getInt(1)+"   "+rs.getString(2));
				s=rs.getString("name");
			}
		     }
		     catch(Exception e)   {e.printStackTrace(); }
		     finally {
		    	 try {
		    		 con.close(); 
		    		 }
		    	 catch(Exception e) {e.printStackTrace();}
		    	 }
		     return s;
		     }

	public User getUser() {
		
		String s=null;
		  Connection con = null;
		  User u=new User();
		     try {
			 con=jdbcTemplate.getDataSource().getConnection();
			PreparedStatement pst=con.prepareStatement("select * from stu where id=?");
			pst.setInt(1,12);
			ResultSet rs=pst.executeQuery();
			if(rs.next())
			{
				u.setEmailId(rs.getString(2));
				u.setMobile(rs.getString(2));
				u.setPassword(rs.getString(2));
			}
		     }
		     catch(Exception e)   {e.printStackTrace(); }
		     finally {
		    	 try {
		    		 con.close(); 
		    		 }
		    	 catch(Exception e) {e.printStackTrace();}
		    	 }
		     return u;
	}
}

		
	


		

	
		  