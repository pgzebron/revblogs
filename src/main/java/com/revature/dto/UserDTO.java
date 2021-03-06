package com.revature.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class UserDTO {
	
	/*
	 *  User Attributes
	 */
	private int userId;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String profilePicture;
	private String jobTitle;
	private String linkedInURL;
	private String description;
	
	private boolean active;
	
	/*
	 *  User Password
	 */
	@NotNull
	private String oldPassword;
	
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,30}$", 
			 message="Password MUST contain: uppercase, lowercase, numeric, and special character between 8 and 30 characters")
	private String newPassword;
	
	@NotNull
	private String confirmPassword;
	
	@NotNull
	private boolean newUser;
	
	public UserDTO(){
		super();
	}
	public UserDTO(String oldPassword, String newPassword, String confirmPassword, boolean newUser) {
		super();
		this.oldPassword = oldPassword;
		this.newPassword = newPassword;
		this.confirmPassword = confirmPassword;
		this.newUser = newUser;
	}
	
	/*
	 *	Constructor for Admin Update User Profile
	 */	
	public UserDTO(int userId, String email, String firstName, String lastName, String jobTitle,
			String linkedInURL, String description) {
		super();
		this.userId = userId;
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
		this.jobTitle = jobTitle;
		this.linkedInURL = linkedInURL;
		this.description = description;
	}
	
	/*
	 *  Getters and Setters for Admin Update User Profile
	 */
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public String getJobTitle() {
		return jobTitle;
	}
	public void setJobTitle(String jobTitle) {
		this.jobTitle = jobTitle;
	}
	public String getLinkedInURL() {
		return linkedInURL;
	}
	public void setLinkedInURL(String linkedInURL) {
		this.linkedInURL = linkedInURL;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getFullname(){
		return this.lastName + ", " + this.firstName;
	}
	
	
	/**
	 *  Getters and Setters for Update Password
	 */
	
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public boolean isNewUser() {
		return newUser;
	}
	public void setNewUser(boolean newUser) {
		this.newUser = newUser;
	}
}
