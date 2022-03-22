package org.testunited.examples.learnright.provisioning;

import java.util.Date;

public class Course {

	public enum CourseStatus {
		Provisioned, ReadyForEnrolment, EnrolmentFrozen, Archived
	}

	private int id;

	private BookTitle bookTitle;

	private String name;

	private Date startDate;

	private Date endDate;

	private CourseStatus status;
	public Course() {
		super();
	}
	public Course(int id) {
		super();
		this.id = id;
	}
	public Course(int id, BookTitle bookTitle, String name, Date startDate, Date endDate) {
		super();
		this.id = id;
		this.bookTitle = bookTitle;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public Course(int id, BookTitle bookTitle, String name, Date startDate, Date endDate, CourseStatus status) {
		super();
		this.id = id;
		this.bookTitle = bookTitle;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.status = status;
	}

	public BookTitle getBookTitle() {
		return bookTitle;
	}

	public Date getEndDate() {
		return endDate;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getStartDate() {
		return startDate;
	}

	public CourseStatus getStatus() {
		return status;
	}

	public void setBookTitle(BookTitle bookTitle) {
		this.bookTitle = bookTitle;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setStatus(CourseStatus status) {
		this.status = status;
	}
}
