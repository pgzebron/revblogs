package com.revature.beans;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Entity
@Table(name="PP_BLOG")
@Indexed
public class Blog {
	
	//----------------------------------
	// Attributes
	@Id
	@Column(name="BLOG_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="blogSequence")
	@SequenceGenerator(name="blogSequence",sequenceName="BLOG_SEQUENCE",initialValue=1,allocationSize=1)
	private int blogId;
	
	@Column(name="BLOG_TITLE", unique=true, nullable=false)
	@Field(name="title")
	private String blogTitle;
	
	@Column(name="BLOG_SUBTITLE")
	@Field(name="subtitle")
	private String blogSubtitle;
	
	@Column(name="BLOG_CONTENT", nullable=false)
	@Lob
	private String blogContent;
	
	@Column(name="BLOG_VIEWS")
	private int blogViews;
	
	@Column(name="BLOG_PUBLISH_DATE")
	private Date publishDate;
	
	@Column(name="BLOG_LOCATION", unique=true, nullable=false)
	private String locationURL;
	
	@Column(name="BLOG_ACTIVE", nullable=false)
	@Field(name="active")
	private boolean active;
	
	@Column(name="BLOG_HTML")
	@Lob
	private String staticHTML;
	
	private transient List<String> references;
	private transient String blogTagsString;
	
	//----------------------------------
	// Realationship Mapping
	@ManyToOne
	@JoinColumn(name="BLOG_AUTHOR", nullable=false)
	@IndexedEmbedded
	private User author;
	
	@OneToMany
	@JoinColumn(name="BLOG_EVIDENCE")
	@IndexedEmbedded
	private Set<Evidence> evidences;
		
	@ManyToMany
	@JoinTable(name="BLOG_TAGS",
		joinColumns=@JoinColumn(name="BLOG_ID"),
		inverseJoinColumns=@JoinColumn(name="TAG_ID"))
	@IndexedEmbedded
	private Set<Tags> tags;
	
	/*
	 *  Constructors
	 */
	public Blog() {
		super();
		this.references = new ArrayList<>();
		for ( int i=0; i<20; i++ ) {
			this.references.add("");
		}
		// Blogs publish date is the date of construction
		this.publishDate = new Date();
		// Blogs always start active
		this.active = true;
	}	
	public Blog(String blogTitle, String blogSubtitle, String blogContent, String locationURL, User author, Set<Tags> tags) {
		this();
		this.blogTitle = blogTitle;
		this.blogSubtitle = blogSubtitle;
		this.blogContent = blogContent;
		this.author = author;
		this.tags = tags;
		this.locationURL = locationURL;
	}

	/*
	 *   Getters & Setters
	 */
	public int getBlogId() {
		return blogId;
	}
	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}
	public String getBlogTitle() {
		return blogTitle;
	}
	public void setBlogTitle(String blogTitle) {
		this.blogTitle = blogTitle;
	}
	public String getBlogSubtitle() {
		return blogSubtitle;
	}
	public void setBlogSubtitle(String blogSubtitle) {
		this.blogSubtitle = blogSubtitle;
	}
	public String getBlogContent() {
		return blogContent;
	}
	public void setBlogContent(String blogContent) {
		this.blogContent = blogContent;
	}
	public int getBlogViews() {
		return blogViews;
	}
	public void setBlogViews(int blogViews) {
		this.blogViews = blogViews;
	}
	public Date getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}
	public String getLocationURL() {
		return locationURL;
	}
	public void setLocationURL(String locationURL) {
		this.locationURL = locationURL;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public Set<Evidence> getEvidences() {
		return evidences;
	}
	public void setEvidences(Set<Evidence> evidences) {
		this.evidences = evidences;
	}
	public Set<Tags> getTags() {
		return tags;
	}
	public void setTags(Set<Tags> tags) {
		this.tags = tags;
	}
	public String getStaticHTML() {
		return staticHTML;
	}
	public void setStaticHTML(String staticHTML) {
		this.staticHTML = staticHTML;
	}
	public User getAuthor() {
		return author;
	}
	public void setAuthor(User author) {
		this.author = author;
	}
	public List<String> getReferences() {
		return references;
	}
	public void setReferences(List<String> references) {
		this.references = references;
	}
	public String getBlogTagsString() {
		return blogTagsString;
	}
	public void setBlogTagsString(String blogTagsString) {
		this.blogTagsString = blogTagsString;
	}
}
