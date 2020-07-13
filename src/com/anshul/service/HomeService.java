package com.anshul.service;

import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anshul.dao.HomeDao;
import com.anshul.model.User;

@Service
public class HomeService {
	@Autowired
	HomeDao homeDao;
	 
	public void insertInfo(User user)throws SQLException{
		
		System.out.println("service first");
		homeDao.insertInfo(user);
		System.out.println("service last");
		
		
		
	}

	public String getInfo() {
		
		return homeDao.getInfo();
	}

	public User getUser() {
		// TODO Auto-generated method stub
		return homeDao.getUser();
	}

}
