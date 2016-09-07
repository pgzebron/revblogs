package com.revature.controllers;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.revature.beans.Blog;
import com.revature.beans.User;
import com.revature.dto.UserDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.Logging;
import com.revature.service.Population;

@Controller
public class BaseController {
	
	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private Logging logging;
	private BusinessDelegate businessDelegate;
	private static final String HOME = "/home";
	private static final String TITLE = "title";
	private static final String LOGGED = "Logged in as ";
	private static final String MESSAGE = "message";
	private static final String WELCOME = "Welcome ";
	
	
	public void setBusinessDelegate(BusinessDelegate businessDelegate){
		this.businessDelegate = businessDelegate;
	}
	public BusinessDelegate getBusinessDelegate() {
		return businessDelegate;
	}
	public Logging getLogging() {
		return logging;
	}
	public void setLogging(Logging logging) {
		this.logging = logging;
	}

	/*
	 *  Default Controller
	 *  
	 */
	
	//------------------------------------------------
	// Redirections
	
	// Default Page
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home(){
		
		return "index";
	}
	
	// Login Page
	@RequestMapping(value="/loginPage", method=RequestMethod.GET)
	public String login(HttpServletRequest req){
	
		// Toggle Population Button (True is On and False is Off
		req.getSession().setAttribute("populate", false);
		
		if(req.getSession().getAttribute("user") != null){
			User user = (User) req.getSession().getAttribute("user");
		}
		
		return "loginPage";
	}
	
	@RequestMapping(value="/password", method=RequestMethod.GET)
	public String password(HttpServletRequest req){
		req.setAttribute("updatePassword", new UserDTO());
		return "password";
	}
	
	// Profile Page
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(HttpServletRequest req){
				
		User myUser = (User) req.getSession().getAttribute("user");
		
		if(myUser.isNewUser()){
			
	
			return "password";
			
		} else {
			
			req.setAttribute("updateUser", new User());
			
			return "profile";
		}
	}
	
	// Create Blog Page
	@RequestMapping(value="/create-blog", method=RequestMethod.GET)
	public String createBlog(HttpServletRequest req){
		
		req.setAttribute("blog", new Blog());
		return "create-blog";
	}
	
	// Upload Example Page
	@RequestMapping(value="/upload-example", method=RequestMethod.GET)
	public String uploadExamplePage() {
		
		return "upload-example";
	}
	
	// Add Picture Page
	@RequestMapping(value="/add-picture", method=RequestMethod.GET)
	public String addPicture(){
		return "add-picture";
	}
	
	// Navbar Page
	@RequestMapping(value="/navbar-partial", method=RequestMethod.GET)
	public String getNavbar() {
		return "navbar";
	}
	
	//------------------------------------------------
	// SPRING SECURITY
	
	//Delegation - Reduces Duplicate Code
	private static ModelAndView modelCreation(String jobTitle,String name){
		ModelAndView model = new ModelAndView();
		model.setViewName(HOME);
		model.addObject(TITLE, LOGGED + jobTitle);
		model.addObject(MESSAGE, WELCOME + name);
		return model;
	}
	
	// Admin Page
	@RequestMapping(value="/admin**")
	public ModelAndView viewAdmin(HttpServletRequest request, HttpServletRequest response, Principal principal){
		String name = principal.getName();
		User user = businessDelegate.requestUsers(name);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		return modelCreation(user.getJobTitle(), user.getFirstName());
	}
	
	// Contributor Page
	@RequestMapping(value="/contributor**")
	public ModelAndView viewContributor(HttpServletRequest request, HttpServletRequest response, Principal principal){
		
		String name = principal.getName();
		User user = businessDelegate.requestUsers(name);
		HttpSession session = request.getSession();
		session.setAttribute("user", user);
		
		return modelCreation(user.getJobTitle(), user.getFirstName());
	}
	
	@RequestMapping(value="/go-admin")
	public ModelAndView getViewAdmin(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		return modelCreation(user.getJobTitle(), user.getFirstName());
	}
	
	@RequestMapping(value="/go-contributor")
	public ModelAndView getViewContributor(HttpServletRequest request, HttpServletResponse response){
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		
		return modelCreation(user.getJobTitle(), user.getFirstName());
	}
	
	@RequestMapping(value="edit.do")
	public String getEditBlog(HttpServletRequest req) {
		if((req.getParameter("blogid")) != null){
			int id = Integer.parseInt(req.getParameter("blogid"));
			Blog blog = businessDelegate.requestBlog(id);
			req.setAttribute("blog", blog);
			return "edit-blog";
		}
		Blog blog = (Blog) req.getSession().getAttribute("blog");
		req.setAttribute("blog", blog);
		return "edit-blog";
	}
	
	@RequestMapping(value="user-blogs")
	public String getUserBlogs(){
		return "user-blogs";
	}
}