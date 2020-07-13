package com.anshul.controller;


import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.anshul.model.User;
import com.anshul.model.Userfile;
import com.anshul.service.HomeService;
@Controller
public class Home1Controller {
@Autowired
HomeService homeService;

	@RequestMapping(value="/")
	public String home() {
		System.out.println("controller cALLED");
		return "index";
	}
	@RequestMapping(value="/test1")
	public String test1(HttpServletRequest req,User user) throws SQLException {
		String s=user.getUserName();
		homeService.insertInfo(user);
		System.out.println("test controller cALLED"+s);
		return "index";
	}
	@RequestMapping(value="/test2")
	public String test2(HttpServletRequest req) throws SQLException {
		String s=homeService.getInfo();
		req.setAttribute("abc", s);    
	return "index";	
	}
	@RequestMapping(value="/test3")
		public String test3(HttpServletRequest req) throws IOException {
	        File f1=new File("H:/code.pdf");
	       File f2=new File("H:/code.pdf");
	       PDFMergerUtility pdf=new PDFMergerUtility();
	       pdf.setDestinationFileName("H:\\code2.pdf");
	      pdf.addSource(f1);
	       pdf.addSource(f2);
	        pdf.mergeDocuments(null);
	      System.out.println("pdf merged");
	      return "index";
	        
	}
	@RequestMapping(value="/test4",method=RequestMethod.POST)
		public String test4(HttpServletRequest req,Userfile userfile) throws SQLException {
	String s=userfile.getName();
		String filepath=uploadFileOnServer(userfile);
		System.out.println("test4 controller called"+s);
		return "index";
		
	}
	private String uploadFileOnServer(Userfile userfile) {
		String rootdirectory="H:/files/merge";
		File directory= new File(rootdirectory);
		if(!directory.exists())
			directory.mkdirs();
		MultipartFile[] f=userfile.getUserfiles();
		
		for(MultipartFile filedata:f) {
		String filename=filedata.getOriginalFilename();
		if(filename!=null && filename.length()>0)
		{
			try {
			String filepath=directory.getCanonicalPath() + File.separator+filename;
			BufferedOutputStream bos=new BufferedOutputStream(new FileOutputStream(filepath));
			bos.write(filedata.getBytes());
			bos.close();
			return filepath;
			}
			catch(Exception e) {
				e.printStackTrace();
		
	        }
		}
			
	

		
		}
		return null;

		
	
		
}
	@RequestMapping(value="download")
	public String download(HttpServletResponse res) throws IOException {
		String mimeType=null;
		File f=new File("H:/files/merge/DSA.jpg");
		mimeType=getMimeType(f.getCanonicalPath());
		res.setContentType(mimeType);
		res.setHeader("Content-Disposition","attachement;filename=\""+f.getName()+"\"");
		res.setContentLength((int)f.length());
		InputStream is=new FileInputStream(f);
		ServletOutputStream out=res.getOutputStream();
		org.apache.commons.io.IOUtils.copy(is,out);
		is.close();
		out.flush();
		out.close();
		return "index";
		
}
	private String getMimeType(String canonicalPath) {
		canonicalPath=canonicalPath.toLowerCase();

		if(canonicalPath.endsWith(".jpg")||canonicalPath.endsWith(".jpeg")||canonicalPath.endsWith(".jpe"))
		
	return "image/jpeg";
		else if(canonicalPath.endsWith(".pdf"))
		return "application/pdf";
		else
		return "application/pdf";
	}
//}
@RequestMapping("/splitter")
public String splitter() throws IOException {
File file=new File("H:/11.pdf");
PDDocument pd=PDDocument.load(file);
Splitter sp=new Splitter();
List<PDDocument> pd1=sp.split(pd);
  Iterator<PDDocument> it=pd1.listIterator();
  int i=1;
  while(it.hasNext())
  {
	  PDDocument pd2=it.next();
	  pd2.save("H:/files/split"+i+".pdf");
	  i++;
  }
	  
  pd.close();
  return "index";
}
@RequestMapping(value="/extractData")
public String extractData(HttpServletRequest req)throws IOException {
	File file=new File("H:/14.pdf");
PDDocument pd=PDDocument.load(file);
PDFTextStripper pdf=new PDFTextStripper();
String s=pdf.getText(pd);
req.setAttribute("data",s);
System.out.println(s);
return "extractedData";


	
}
@RequestMapping(value="/removepage")
public String removepage()throws IOException{
	File file=new File("H:/11.pdf");
	PDDocument pd=PDDocument.load(file);
	int totalpage=pd.getNumberOfPages();
	System.out.println(totalpage);
	pd.removePage(1);
	pd.save("H:/11.pdf");
	pd.close();
	return "index";
}
@RequestMapping(value="/pdftoimage")
public String pdftoimage()throws IOException{
	File file=new File("H:/files/split1.pdf");
	PDDocument pd=PDDocument.load(file);
 PDFRenderer re=new PDFRenderer(pd);
   BufferedImage img=re.renderImage(0);
  ImageIO.write(img,"JPEG",new File("H:/files/split.jpg"));
  pd.close();
  return "index";
   
}
@RequestMapping(value="/pdftoimage1")
public String pdftoimage1()throws IOException{
	File file=new File("H:/files/merge/14.pdf");
	PDDocument pd=PDDocument.load(file);
	int totalpage=pd.getNumberOfPages();
	 PDFRenderer re=new PDFRenderer(pd);
	int i=0;
	while(i<totalpage) {
  
   BufferedImage img=re.renderImage(i);
   ImageIO.write(img,"JPEG",new File("H:/files/merge/img"+i+".jpg"));
   i++;
	}
   pd.close();
  
   


	return "index";
}

@RequestMapping(value="/protect")
public String protect()throws IOException {
	File file=new File("H:/files/merge/12.pdf");
	PDDocument pd=PDDocument.load(file);
	AccessPermission ap=new AccessPermission();
	StandardProtectionPolicy policy=new StandardProtectionPolicy("code123@","parth@212",ap);
	policy.setEncryptionKeyLength(256);
	policy.setPermissions(ap);
	pd.protect(policy);
	pd.save("H:/files/merge/12.pdf");
	pd.close();
	return "index";


	
}
@RequestMapping(value="/extractDataa")
public String extractData1(HttpServletRequest req) throws IOException {
	String password=req.getParameter("password");
	if(password==null) {
	File file = new File("H:/files/merge/12.pdf");
	PDDocument pd=null;
	try{
	pd =PDDocument.load(file);
	}catch(org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException e) {
		req.setAttribute("filepath","H:/files/merge/12.pdf");
		return "requestPassword";
				
	}
	PDFTextStripper pdf=new PDFTextStripper();
	String s=pdf.getText(pd);
	req.setAttribute("data", s);
	System.out.println(s);
	return "extractedData";}
	else {
		File file = new File(req.getParameter("filePath"));
		PDDocument pd=null;
		try{
			pd =PDDocument.load(file,password);
			}catch(org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException e) {
				req.setAttribute("filepath","H:/files/merge/12.pdf");
				req.setAttribute("error", "password is not correct");
				return "requestPassword";
						
			}
		PDFTextStripper pdf=new PDFTextStripper();
		String s=pdf.getText(pd);
		req.setAttribute("data", s);
		System.out.println(s);
		return "extractedData";
		
	}
}
@RequestMapping(value="unlock")
public String unlock() throws IOException {
	File file=new File("H:/files/merge/12.pdf");
	PDDocument pd=PDDocument.load(file,"code123@");
	pd.setAllSecurityToBeRemoved(true);
	pd.save("H:/files/merge/12.pdf");
	pd.close();
	
	return "index";
	
}
@RequestMapping(value="/splitterzip", produces="appication/zip")
public String splitterzip(HttpServletRequest request, HttpServletResponse response) throws IOException {
	File file = new File("H:/files/merge/12.pdf"); 
	PDDocument pd =PDDocument.load(file);
	 Splitter sp=new Splitter(); 
	 List<PDDocument> pd1=sp.split(pd);
	Iterator<PDDocument> it=pd1.listIterator();
	int i=1;
	List<String> filepaths=new ArrayList<String>();
	while(it.hasNext())
	{
		String x="H:/split"+i+".pdf";
	PDDocument pd2=it.next(); pd2.save(x);
	i++;
	filepaths.add(x);
	}
	pd.close();
	zipFiles(filepaths,response);

	return "index";
}
	private void zipFiles(List<String> filepaths, HttpServletResponse res) throws IOException {
			String zipFileName="H:/splitterzip.zip";
			FileOutputStream fos= new FileOutputStream(zipFileName);
			ZipOutputStream zos= new ZipOutputStream(fos);
			for(String s:filepaths)
			{
				zos.putNextEntry(new ZipEntry(new File(s).getName()));
				byte[] bytes=Files.readAllBytes(Paths.get(s));
				zos.write(bytes);
				zos.closeEntry();
				
			}
			zos.close();
			File f= new File(zipFileName);
			res.setContentType("application/zip");
			res.setHeader("Content-Disposition", "attachement;filename=\""+f.getName()+"\"");
			res.setContentLength((int)f.length());
			InputStream is =new FileInputStream(f);
			ServletOutputStream out=res.getOutputStream();
			IOUtils.copy(is,out);
			is.close();
			out.flush();
			out.close();

}

}

	
	
	
	
	
	
	
	
			
	
	
	






		
	
		






