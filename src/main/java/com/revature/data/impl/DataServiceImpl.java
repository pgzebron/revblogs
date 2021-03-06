package com.revature.data.impl;

import java.util.List;

import org.hibernate.Session;
import org.springframework.stereotype.Service;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DAO;
import com.revature.data.DataService;

@Service
public class DataServiceImpl implements DataService{

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private DAO dao;

	public void setDao(DAO dao) {
		this.dao = dao;
	}
	public Session grabSession(){
		return dao.getSession();
	}
	
	/*
	 *  Database Altering Methods
	 */
	
	public void makeRecord(Object obj){
		dao.insertRecord(obj);
	}
	public void makeRecord(Object[] obj){
		dao.insertRecord(obj);
	}
	public void changeRecord(Object obj) {
		dao.editRecord(obj);
	}
	public void changeRecord(Object[] obj) {
		dao.editRecord(obj);
	}
	public void deleteRecord(Object obj){
		dao.deleteRecord(obj);
	}
	/*
	 *  Database Query Methods
	 */
	
	public User grabUsers(String email){
		return dao.getUsers(email);
	}
	public UserRoles grabRoles(int roleId) {
		return dao.getRoles(roleId);
	}
	public UserRoles grabRoles(String role) {
		return dao.getRoles(role);
	}
	
	public List<User> grabUsers(){
		return dao.getUsers();
	}
	public List<Blog> grabBlogs(){
		return dao.getBlogs();
	}
	public List<Tags> grabTags(){
		return dao.getTags();
	}
	public List<UserRoles> grabRoles(){
		return dao.getRoles();
	}
	public List<Evidence> grabEvidence(){
		return dao.getEvidence();
	}
	public User grabUser(int id) {
		return dao.getUser(id);
	}
	public Tags grabTag(int id) {
		return dao.getTag(id);
	}
	
	//-------------------------------------------------------------------------------------------------
	// Pagination
	
	public PaginatedResultList<Blog> grabBlogs(int start, int max){
		return dao.getBlogs(start, max);
	}
	public PaginatedResultList<Blog> grabBlogs(User author, int start, int max){
		return dao.getBlogs(author, start, max);
	}
	public PaginatedResultList<Blog> grabBlogs(Tags category, int start, int max){
		return dao.getBlogs(category, start, max);
	}
	public PaginatedResultList<Blog> grabBlogs(String search, int start, int max){
		return dao.getBlogs(search, start, max);
	}
	@Override
	public Blog grabBlog(int id) {
		return dao.getBlog(id);
	}
}
