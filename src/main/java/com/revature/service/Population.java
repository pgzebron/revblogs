package com.revature.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.revature.beans.ApplicationProperties;
import com.revature.beans.Blog;
import com.revature.beans.Tags;
import com.revature.beans.User;
import com.revature.beans.UserRoles;
import com.revature.data.impl.PropertyType;
import com.revature.service.impl.Crypt;

public class Population {

	private BusinessDelegate delegate;
	private Logging logger;
	private Session session;
	
	public void setDelegate(BusinessDelegate delegate) {
		this.delegate = delegate;
	}
	public void setLogger(Logging logger) {
		this.logger = logger;
	}

	//-----------------------------------
	// Complete Population (Besides Evidence)
	public void populateDatabase(String[] properties){
		
		// Props
		encryptProperty(properties);
		
		populateRoles();
		populateTags();
		populateUsers();
		populateBlogs();
	}
	
	//-----------------------------------
	// Roles
	public void populateRoles(){
		
		/**
		 *  List the roles.
		 *  Iterate through the list.
		 *  Insantiate a new Role for each role description.
		 *  Save that role in the database.
		 */
		
		String[] roles = new String[]{"ADMIN", "CONTRIBUTOR"};
		
		List<UserRoles> roleList = new ArrayList<UserRoles>();
		UserRoles role;
		
		for(int i = 0; i < 2; i++){
			
			role = new UserRoles(roles[i]);
			roleList.add(role);
		}
		
		Object[] roleArray = roleList.toArray();
		delegate.putRecord(roleArray);
		
		logger.log("-- Done Populating Role Table --");
	}
	
	//-----------------------------------
	// Tags
	public void populateTags(){
		
		/**
		 *  List the tags.
		 *  Iterate through the tags.
		 *  Insantiate a new Tag for each tag description.
		 *  Save that tag to the GLOBAL list.
		 *  Save that tag in the database.
		 */
		
		String[] tags = new String[]{"Java", "SQL", "Apian", "JSP", "Servlet", "Hibernate", "Spring", "REST", "SOAP"};
		
		List<Tags> tagList = new ArrayList<Tags>();
		Tags tag;
		
		for(int i = 0; i < tags.length; i++){
			
			tag = new Tags(tags[i]);
			tagList.add(tag);
		}
		
		Object[] tagArray = tagList.toArray();
		delegate.putRecord(tagArray);
		
		logger.log("-- Done Populating Tags Table --");
	}
	
	//-----------------------------------
	// Blogs
	@SuppressWarnings("unchecked")
	public void populateBlogs(){
		
		/**
		 *  List the blog data.
		 *  Iterate through the blog data.
		 *  Insantiate a new Blog for each corresponding data element.
		 *  Save that blog in the database.
		 */
		
		session = delegate.requestSession();
		
		List<Tags> tagList = new ArrayList<>();
		
		Criteria criteria = session.createCriteria(Tags.class);
		tagList = (List<Tags>)criteria.list();
	
		
		String[] blogTitle = new String[]{
			
			// 1
			"Making Web Pages Flexible",
			
			// 2
			"The differences between SOAP and REST Web Services",
			
			// 3
			"Ditching SQL with HIbernate!"	
		};
		
		String[] blogSubtitle = new String[]{
				
			// 1
			"JSP, JSTL, and Servlets",
			
			// 2
			"Hint: Loads of fun here.",
			
			// 3
			"Criteria to the Rescue"	
		};
		
		String[] blogContent = new String[]{
			
			// 1
			"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris dapibus, est non sodales blandit, "
			+ "dolor odio blandit lorem, eget euismod eros dolor sit amet tortor. Aenean malesuada tempor accumsan. "
			+ "In hac habitasse platea dictumst. Duis commodo metus nulla, et pharetra ipsum venenatis a. Mauris "
			+ "cursus fringilla fermentum. Suspendisse vitae ante nulla. Donec auctor quam ex, at tincidunt lacus "
			+ "volutpat ac.",
			
			// 2
			// 
			"Etiam quis velit fermentum, scelerisque velit eget, dapibus metus. Ut eu diam gravida ligula mollis "
			+ "feugiat sed pellentesque ligula. Nullam commodo, leo vel accumsan cursus, elit quam commodo diam, "
			+ "ullamcorper auctor ante dolor in nibh. Cras mattis nunc at felis accumsan pellentesque. Nulla "
			+ "tincidunt dapibus tortor, mattis fringilla turpis. Nam nulla enim, posuere at mauris et, varius "
			+ "sollicitudin tellus. Quisque cursus vel tellus non fringilla.",
			
			// 3
			"Donec facilisis urna sit amet quam varius, eu porttitor tortor imperdiet. Duis justo felis, elementum"
			+ "eu ante vitae, dignissim tincidunt lectus. Pellentesque odio tellus, blandit a metus sit amet, "
			+ "dignissim sollicitudin tortor. In hac habitasse platea dictumst. Aenean dui risus, auctor non luctus "
			+ "vel, condimentum a turpis. Aenean eu massa malesuada, suscipit purus quis, tristique sapien."
			+ "Aliquam leo nisl, feugiat in porta et, laoreet non purus."
		};
		
		String[] blogLocation = new String[]{
		
			"url1", "url2", "url3"	
		};
		
		// Tag Population Nonsense
		int[][] tagIndexes = new int[][]{
			
			/*
			 * First  [] = Blog #
			 * Second [] = Tags of that Blog
			 * 
			 * Tags: Java, SQL, Apian, JSP, Servlet, Hibernate, Spring, REST, SOAP
			 * 	 Java      (1)
			 *   SQL       (2)
			 *   Apian     (3)
			 *   JSP 	   (4)
			 *   Servlet   (5)
			 *   Hibernate (6)
			 *   Spring    (7)
			 *   REST      (8)
			 *   SOAP      (9)
			 */
	
			// 1
			{1, 0, 0, 4, 5, 0, 0, 0, 0},
			
			// 2
			{1, 0, 0, 0, 0, 0, 0, 8, 9},
			
			// 3
			{1, 2, 0, 0, 0, 6, 0, 0, 0}
		};
		
		List<User> users = delegate.requestUsers();
		
		List<Blog> blogList = new ArrayList<Blog>();
		Blog blog;
		
		for(int i = 0; i < blogTitle.length; i++){
			
			Set<Tags> tagsSet = new HashSet<>();
			
			// Attach the set of Product Categories that correspond to THIS particular product.
			for(int j = 0; j < tagIndexes.length; j++){
				
				if(tagIndexes[i][j] != 0){
					
					tagsSet.add(tagList.get(tagIndexes[i][j] - 1));
				}
			}
			
			blog = new Blog(blogTitle[i], blogSubtitle[i], blogContent[i], blogLocation[i], users.get(i), tagsSet);
			blogList.add(blog);
		}
		
		Object[] blogArray = blogList.toArray();
		delegate.putRecord(blogArray);

		logger.log("-- Done Populating Blogs Table --");
	}
	
	//-----------------------------------
	// Users
	public void populateUsers(){
		
		session = delegate.requestSession();
		
		Criteria criteria1 = session.createCriteria(UserRoles.class).add(Restrictions.eq("role", "ADMIN"));
		Criteria criteria2 = session.createCriteria(UserRoles.class).add(Restrictions.eq("role", "CONTRIBUTOR"));
		
		UserRoles admin = (UserRoles) criteria1.uniqueResult();
		UserRoles contributor = (UserRoles) criteria2.uniqueResult();
		
				
		/*
		 * 	String[]   = User #
		 * 	String[][] = Property Type:
		 * 
		 * 					Email
		 * 					Password
		 * 					First Name
		 * 					Last Name
		 * 					Title
		 * 					Linked In
		 * 					Description
		 * 					isAdmin
		 * 
		 * 					In that order...
		 */
		
		String[][] users = new String[100][7];
		
		for(int i = 0; i < users.length; i++){
			for(int j = 0; j < 7; j++){
				switch(j){
					
					// Email
					case 0:
						users[i][j] = "pickles" + i + "@yahoo.com";
						break;
						
					// Password
					case 1:
						users[i][j] = "password" + i;
						break;
						
					// First
					case 2:					
						users[i][j] = "Dan";
						break;
						
					// Last
					case 3:					
						users[i][j] = "Pickles";
						break;
						
					// Title
					case 4:					
						users[i][j] = "Java Developer #" + i;
						break;
						
					// Linked In
					case 5:					
						users[i][j] = "https://www.linkedin.com/" + i + "/";
						break;
						
					// Description
					case 6:				
						users[i][j] = "I am a software developer who focuses on applications that revolve around pickles.";
						break;
				}	
			}
		}
		
		List<User> userList = new ArrayList<User>();
		UserRoles myRole = new UserRoles();

		for(int k = 0; k < users.length; k++){
			
			User user = new User();
			
			for(int q = 0; q < users[k].length; q++){
				
				// Find Role
				if(k < 50){
					
					myRole = admin;
					
				} else {
					
					myRole = contributor;
				}
				
				switch(q){
	
					// Email
					case 0:
						
						user.setEmail(users[k][q]);
						break;
						
					// Password
					case 1:
						
						user.setPassword(users[k][q]);
						break;
						
					// First
					case 2:					
						
						user.setFirstName(users[k][q]);
						break;
						
					// Last
					case 3:					
						
						user.setLastName(users[k][q]);
						break;
						
					// Title
					case 4:					
						
						user.setJobTitle(users[k][q]);
						break;
						
					// Linked In
					case 5:					
						
						user.setLinkedInURL(users[k][q]);
						break;
						
					// Description
					case 6:				
						
						user.setDescription(users[k][q]);
						break;
				}
			}
			
			// Encrypt Password
			user.setPassword(Crypt.encrypt(user.getPassword(), user.getEmail(), user.getFullname()));
			
			user.setUserRole(myRole);
			userList.add(user);
		}
		
		Object[] userArray = userList.toArray();
		delegate.putRecord(userArray);
		
		logger.log("-- Done Populating Users Table --");
	}
	
	//-----------------------------------
	// Properties
	public void encryptProperty(String[] property){
		
		if(property.length == 11){
			

			property[0] = Crypt.encrypt(property[0], 
										"CZmTgoznKnJocTkGuFFURvZjUDuVvBhoETorfnzPOfqymleBbOOHfqPCSSty", 
										"pneumonoultramicroscopicsilicovolcanoconiosis"); 
		
			property[1] = Crypt.encrypt(property[1], 
										"GSXWzGGiiDBvlYxTNddabeUOsSPLHoYnibqBEAtRrSDnZPrACvUjBMGxcoBZ", 
										"Pseudopseudohypoparathyroidism"); 
					
			property[2] = Crypt.encrypt(property[2], 
										"cCpQZBETFySMWXeMTQDQomszbDhIgTCWNfjzrBQjwyzcMIrNeFGZggWpzSdQ", 
										"Floccinaucinihilipilification"); 
			
			property[3] = Crypt.encrypt(property[3], 
										"UjVheJqfrHXEuciEaIEibjRYjaxGEJFPrLcZNuugxZQmpHdeoBJRVLFeEDfc", 
										"Antidisestablishmentarianism"); 
			
			property[4] = Crypt.encrypt(property[4], 
										"BhXCFkEevSCHlJMCJyvqhyOiNnKDaoxwcdWrNGxUZySIJspidexHSROVXDAh", 
										"supercalifragilisticexpialidocious"); 
			
			property[5] = Crypt.encrypt(property[5], 
										"RnhHIlwovrapdVzySrOIfmMZPOPOEACAsVScsBIflnsIphgireiIRKkmINdr", 
										"Incomprehensibilities"); 
			
			property[6] = Crypt.encrypt(property[6], 
										"momGKfMimvxYGNKmZCzdXNSBGpvQngTbtvxETwjePoZWyirhkyAWMhkFzxQI", 
										"honorificabilitudinitatibus"); 
			
			property[7] = Crypt.encrypt(property[7], 
										"TuJgzrAAFblqmFUfDvRyNHOtKQjVpxESLwrXecnGMSrSEJyhfkgPGvTccbPJ", 
										"sesquipedalianism"); 
			
			property[8] = Crypt.encrypt(property[8], 
										"JplYSkoJXvxUEIaEZtLMzYugcPINpzArbIoGHjwHwFzdoUtfNfMOetPvvsHn", 
										"METHIONYLTHREONYLTHREONYGLUTAMINYLARGINY"); 
		
			property[9] = Crypt.encrypt(property[9], 
										"iqGJkjoSepUYggqxsZCdxXzCSyjxADhQtsiMPhyNRMxJbGowMrGmlIQETFzC", 
										"Aequeosalinocalcalinoceraceoaluminosocupreovitriolic"); 
			
			property[10] = Crypt.encrypt(property[10], 
										"boosNkoVgLkjnWJUMEeHAGbUmwWhVlBOPZKZjUduUXunxwbsZmnNxKdAWePg", 
										"peobuefdvxjbtoajefspkfuccfngbf"); 
	
			
	
			ApplicationProperties propObj = new ApplicationProperties(	property[0], property[1], property[2], property[3],
																		property[4], property[5], property[6], property[7],
																		property[8], property[9], property[10]);
			delegate.putRecord(propObj);
			
			logger.log("-- Done Populating Properties Table --");
		}
	}
}