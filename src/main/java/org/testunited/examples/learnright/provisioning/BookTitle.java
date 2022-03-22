package org.testunited.examples.learnright.provisioning;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookTitle {

/**
	 * @return the edition
	 */
//	public String getEdition() {
//		return edition;
//	}
//	/**
//	 * @param edition the edition to set
//	 */
//	public void setEdition(String edition) {
//		this.edition = edition;
//	}
//	/**
//	 * @return the year
//	 */
//	public String getYear() {
//		return year;
//	}
//	/**
//	 * @param year the year to set
//	 */
//	public void setYear(String year) {
//		this.year = year;
//	}

	private int id;

	private String name;

	private String author;
//	private String edition;
//	private String year;

	//	public BookTitle(int id, String name, String author, String edition, String year) {
//		super();
//		this.id = id;
//		this.name = name;
//		this.author = author;
//		this.edition = edition;
//		this.year = year;
//	}
	public BookTitle() {
		super();
	}

	public BookTitle(int id) {
		super();
		this.id = id;

	}

	public BookTitle(int id, String name, String author) {
		super();
		this.id = id;
		this.name = name;
		this.author = author;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
