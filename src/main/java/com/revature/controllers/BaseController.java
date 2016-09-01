package com.revature.controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.revature.app.TemporaryFile;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.service.BusinessDelegate;
import com.revature.service.HtmlWriter;
import com.revature.service.Logging;
import com.revature.service.Population;

@Controller
public class BaseController {
	
	private Logging logging;
	private BusinessDelegate businessDelegate;
	private Population population;

	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public Population getPopulation() {
		return population;
	}
	public void setPopulation(Population population) {
		this.population = population;
	}
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(HttpServletRequest req, HttpServletResponse resp){
	
		return "login";
	}
	@RequestMapping(value="/temp-AddClient", method=RequestMethod.GET)
	public String newClient(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("user", new User());
		List<UserRoles> arrl = new ArrayList<>();
		arrl.add(new UserRoles(1, "Manager"));
		arrl.add(new UserRoles(2, "Employee"));
		
		req.setAttribute("roleDropDown", arrl);
		return "makeClientAccount";
	}
	@RequestMapping(value="/populate", method=RequestMethod.GET)
	public String populate(HttpServletRequest req, HttpServletResponse resp){
	
		return "login";
	}
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req, HttpServletResponse resp){
		req.setAttribute("blog", new Blog());
		return "create-blog";
	}

	@RequestMapping(value="add-blog.do", method=RequestMethod.POST)
	public String addBlog(
			@ModelAttribute("blog") @Valid Blog blog, 
			BindingResult bindingResult,
			HttpServletRequest req,
			HttpServletResponse resp) {
		/*
		 * Blog Bean will be generated with proper tags and fields
		 */
		
		if(blog.getBlogTagsString().isEmpty()){
			blog.setTags(new HashSet<Tags>());
		}
		else{
			String tmp = blog.getBlogTagsString();
			List<String> myList = Arrays.asList(tmp.split(","));
			Set<Tags> tmpTags = new HashSet<Tags>();
			List<Tags> dbTags = businessDelegate.requestTags();
			/*
			 * loop through List of tag descriptions the user types in
			 */
			for(String a : myList){
				boolean check = false;
				String tagDesc = a.toLowerCase().replaceAll("\\s+","");
				/*
				 * loop through database Tags to check with user input tags
				 * if theres a match, put instance of database Tag into User bean, if not, create new Tag bean
				 */
				for(Tags b : dbTags){
					if(b.getDescription().equals(tagDesc)){
						tmpTags.add(b);
						check = true;
					}
				}
				if(!check){
					Tags myTag = new Tags(tagDesc);
					businessDelegate.putRecord(myTag);
					tmpTags.add(myTag);
					
				}
			}
			blog.setTags(tmpTags);
		}
		
//		User author = new User();
//		author.setFirstName("Simon");
//		author.setLastName("& Garfunkel");
//		author.setDescription("Simon and Garfunkel are Paul Simon and Art Garfunkel. "
//				+ "They are musicians who one day decided to start recording together. "
//				+ "<i>The Sound of Silence</i> might be their most well-known song.");
		User author = businessDelegate.requestUsers("dpickles");
		blog.setAuthor(author);
		blog.setPublishDate(new Date());
		businessDelegate.putRecord(blog);
		
		HtmlWriter htmlWriter;
		try {
			InputStream templateStream = this.getClass().getClassLoader().getResourceAsStream("template.html");
			htmlWriter = new HtmlWriter(blog, blog.getAuthor(), templateStream);
			TemporaryFile tempFile = htmlWriter.render();
			String tempPath = tempFile.getTemporaryFile().getPath();
			req.setAttribute("path", tempPath);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return "preview-blog";
	}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public ModelAndView home(){
		ModelAndView mv = new ModelAndView("index");
		return mv;
	}
	
	@RequestMapping(value="/upload-example", method=RequestMethod.GET)
	public ModelAndView uploadExamplePage() {
		return new ModelAndView("upload-example");
	}
	
	@RequestMapping(value="/upload-resource", method=RequestMethod.POST)
	public void uploadResourceHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadResource(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><img src=\"" + url + "\" /></body></html>");
		} catch (IOException e) {
			logging.info(e);
		}
	}
	@RequestMapping(value="/upload-page", method=RequestMethod.POST)
	public void uploadPageHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		TemporaryFile tempFile = TemporaryFile.make(file);
		File convertedFile = tempFile.getTemporaryFile();
		String url = businessDelegate.uploadPage(convertedFile);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><a href=\"" + url + "\">" + url + "</a></body></html>");
		} catch (IOException e) {
			logging.info(e);
		}
	}
	@RequestMapping(value="/add-picture", method=RequestMethod.GET)
	public String addPicture(HttpServletRequest req, HttpServletResponse resp){
		return "add-picture";
	}
	@RequestMapping(value="/upload-picture", method=RequestMethod.POST)
	public void uploadPictureHandler(@RequestParam("file") MultipartFile file, HttpServletResponse resp)
	{
		String url = businessDelegate.uploadEvidence(file.getOriginalFilename(), file);
		try {
			PrintWriter writer = resp.getWriter();
			writer.append("<html><body><h3>Copy URL into Add Image</h3><br></body></html>" + url);
		} catch (IOException e) {
			logging.info(e);
		}
	}
	
}