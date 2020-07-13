package com.anshul.model;

import org.springframework.web.multipart.MultipartFile;

public class Userfile {
	
	String name;
	MultipartFile userfile;
	MultipartFile[] userfiles;
	public MultipartFile[] getUserfiles() {
		return userfiles;
	}
	public void setUserfiles(MultipartFile[] userfiles) {
		this.userfiles = userfiles;
	}

	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public MultipartFile getUserfile() {
		return userfile;
	}
	public void setUserfile(MultipartFile userfile) {
		this.userfile = userfile;
	}
	

}
