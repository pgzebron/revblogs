package com.revature.service;

import java.util.List;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;

public interface BusinessDelegate {
	
	// Push
	public void putRecord(Object _obj);
	
	// Pull
	public User requestUsers(String _username);
	
	public List<User> requestUsers();
	public List<Blog> requestBlogs();
	public List<Tags> requestTags();
	public List<UserRoles> requestRoles();
	public List<Evidence> requestEvidence();
}
