package org.testunited.examples.learnright.provisioning;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.testunited.examples.learnright.provisioning.Course.CourseStatus;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseMSEntity {
	int id;
	int bookTitleId;
	String name;
	Date startDate;
	Date endDate;
	CourseStatus courseStatus;

	public CourseMSEntity() {
	}

	public CourseMSEntity(int id, int bookTitleId, String name, Date startDate, Date endDate,
			CourseStatus courseStatus) {
		super();
		this.id = id;
		this.bookTitleId = bookTitleId;
		this.name = name;
		this.startDate = startDate;
		this.endDate = endDate;
		this.courseStatus = courseStatus;
	}

	public int getBookTitleId() {
		return bookTitleId;
	}

	public CourseStatus getCourseStatus() {
		return courseStatus;
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

	public void setBookTitleId(int bookTitleId) {
		this.bookTitleId = bookTitleId;
	}

	public void setCourseStatus(CourseStatus courseStatus) {
		this.courseStatus = courseStatus;
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
}