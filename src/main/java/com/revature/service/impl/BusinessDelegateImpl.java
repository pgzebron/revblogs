package com.revature.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.jets3t.service.S3Service;
import org.jets3t.service.security.AWSCredentials;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.revature.beans.Blog;
import com.revature.beans.Evidence;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.DataService;
import com.revature.data.impl.PaginatedResultList;
import com.revature.data.impl.PropertyType;
import com.revature.dto.AuthorDTO;
import com.revature.dto.BlogPostCollectionDTO;
import com.revature.dto.BlogPostDTO;
import com.revature.service.BusinessDelegate;
import com.revature.service.JetS3;
import com.revature.service.ServiceLocator;

@Service
public class BusinessDelegateImpl implements BusinessDelegate{
	
	private AWSCredentials credentials;
	private S3Service s3;

	/*
	 * 	Attributes && Getters/Setters
	 * 
	 */
	private DataService dataService;
	private JetS3 jetS3;

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}
	public void setServiceLocator(ServiceLocator serviceLocator) {
	}
	public Session requestSession() {
		return dataService.grabSession();
	}
	public void setJetS3(JetS3 jetS3) {
		this.jetS3 = jetS3;
		jetS3.syncBusinessDelegate(this);
	}
	
	public JetS3 getJetS3() {
		return jetS3;
	}
	/**
	 * Attempts to upload a resource (such as a CSS or JS file) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadResource(String fileName, MultipartFile file) {
		return jetS3.uploadResource(fileName, file);

	}
	
	/**
	 * Attempts to upload a front-end page (html) to the S3 server
	 * @param file a file that is to be uploaded to the database, the file should have a valid extension
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadPage(File file) {
		return jetS3.uploadPage(file);

	}
	
	/**
	 * Attempts to upload an 'evidence' (picture, code, attachment) to the S3 server
	 * @param fileName the destination name of the file, a valid extension should be included
	 * @param file a file that is to be uploaded to the S3 server
	 * @return the URL where the file was uploaded if successful, null otherwise
	 */
	public String uploadEvidence(String fileName, MultipartFile file) {
		return jetS3.uploadEvidence(fileName, file);

	}
	
	/*
	 *  Database Altering Methods
	 */
	
	public void putRecord(Object obj){
		dataService.makeRecord(obj);
	}
	public void putRecord(Object[] obj){
		dataService.makeRecord(obj);
	}
	public void updateRecord(Object obj){
		dataService.changeRecord(obj);
	}
	
	/*
	 *  Database Query Methods
	 */
	
	public User requestUsers(String username){
		return dataService.grabUsers(username);
	}
	public UserRoles requestRoles(int roleId) {
		return dataService.grabRoles(roleId);
	}
	public UserRoles requestRoles(String role) {
		return dataService.grabRoles(role);
	}
	public String requestProperty(PropertyType type){
		return dataService.grabProperty(type);
	}
	
	public List<User> requestUsers(){
		return dataService.grabUsers();
	}
	public List<Blog> requestBlogs(){
		return dataService.grabBlogs();
	}
	public List<Tags> requestTags(){
		return dataService.grabTags();
	}
	public List<UserRoles> requestRoles(){
		return dataService.grabRoles();
	}
	public List<Evidence> requestEvidence(){
		return dataService.grabEvidence();
	}
	public User requestUser(int id) {
		return dataService.grabUser(id);
	}
	public Tags requestTag(int id) {
		return dataService.grabTag(id);
	}
	
	
	//-------------------------------------------------------------------------------------------------
	// Pagination
	
	public BlogPostCollectionDTO requestBlogPosts(int page, int perPage) throws IllegalArgumentException {
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException("page and perPage must be positive integers");
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			postList.add(new BlogPostDTO(p));
		}
		long totalItems = results.getTotalItems();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		
		return postCollection;
	}
	
	public BlogPostCollectionDTO requestBlogPosts(User author, int page, int perPage) throws IllegalArgumentException {
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException("page and perPage must be positive integers");
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(author, start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			postList.add(new BlogPostDTO(p));
		}
		long totalItems = results.getTotalItems();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		postCollection.setAuthor(new AuthorDTO(author));
		
		return postCollection;
	}

	public BlogPostCollectionDTO requestBlogPosts(Tags category, int page, int perPage) throws IllegalArgumentException {
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException("page and perPage must be positive integers");
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(category, start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			postList.add(new BlogPostDTO(p));
		}
		long totalItems = results.getTotalItems();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		postCollection.setCategory(category.getDescription());
		
		return postCollection;
	}
	
	public BlogPostCollectionDTO searchBlogPosts(String query, int page, int perPage) throws IllegalArgumentException {
		
		if (page < 1 || perPage < 1) {
			throw new IllegalArgumentException("page and perPage must be positive integers");
		}
		
		// Instantiate post collection DTO
		BlogPostCollectionDTO postCollection = new BlogPostCollectionDTO();
		
		int start = (page-1)*perPage;
		int maxResults = perPage;
		
		PaginatedResultList<Blog> results = dataService.grabBlogs(query, start, maxResults);
		List<BlogPostDTO> postList = new ArrayList<>();
		for (Blog p: results.getItems()) {
			postList.add(new BlogPostDTO(p));
		}
		long totalItems = results.getTotalItems();
		int totalPages = (int)Math.ceil((double)totalItems/perPage);
		
		postCollection.setPosts(postList);
		postCollection.setPage(page);
		postCollection.setTotalPosts(totalItems);
		postCollection.setTotalPages(totalPages);
		postCollection.setPerPage(perPage);
		
		return postCollection;
	}
}
