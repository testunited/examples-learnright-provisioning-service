package org.testunited.examples.learnright.provisioning;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.testunited.examples.learnright.provisioning.Course.CourseStatus;

@RestController
public class ProvisioningController {

	@Autowired
	private CourseProxy courseProxy;

	@Autowired
	private BookTitleProxy bookTitleProxy;

	@PostMapping("/course-provision-requests")
	public ResponseEntity provision(@Valid @RequestBody Course course) {
		var bookTitle = this.bookTitleProxy.getBookTitle(course.getBookTitle().getId());

		if (bookTitle == null)
			return new ResponseEntity("{\"message\":\"Book Title Not Found\"}", HttpStatus.NOT_FOUND);

		course.setStatus(CourseStatus.Provisioned);

		var provisionedCourse = this.courseProxy.createCourse(course);

		if (provisionedCourse == null)
			return new ResponseEntity("{\"message\":\"Course Provisioning Error\"}", HttpStatus.INTERNAL_SERVER_ERROR);

		provisionedCourse.setBookTitle(bookTitle);
		return new ResponseEntity<Course>(provisionedCourse, HttpStatus.CREATED);
	}
}
