package com.anshul.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.anshul.model.User;
import com.anshul.service.HomeService;

@RestController
public class ApiController {
	@Autowired
	HomeService hs;


	
	@GetMapping("/apiget")
	public User api1(User u1)  {
		System.out.println(u1.getEmailId());;
		//User u=new User();
		//u.setEmailId("anshul@co.in.c");
		//u.setMobile("66544343455");
		
		//System.out.println(u1.getEmailId());;
	    User u=hs.getUser();
		return u;
		
		
	}
	

}




