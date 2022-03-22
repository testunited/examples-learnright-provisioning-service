package org.testunited.examples.learnright.provisioning;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CourseProxy {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${course.service.url}")
	private String service_url;

	private Course convertToCourse(CourseMSEntity c) {
		return new Course(c.id, new BookTitle(c.bookTitleId), c.name, c.startDate, c.endDate, c.courseStatus);
	}

	private CourseMSEntity convertToCourseMSEntity(Course course) {
		return new CourseMSEntity(course.getId(), course.getBookTitle().getId(), course.getName(),
				course.getStartDate(), course.getEndDate(), course.getStatus());
	}

	public Course createCourse(Course course) {
		var courseEntity = this.convertToCourseMSEntity(course);

		ResponseEntity<CourseMSEntity> response;
		try {
			response = this.restTemplate.postForEntity(new URI(this.service_url +  "/courses"),
					courseEntity, CourseMSEntity.class);
			course = this.convertToCourse(response.getBody());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return course;
	}
}
