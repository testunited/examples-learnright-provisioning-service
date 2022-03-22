package org.testunited.examples.learnright.provisioning.test.unit;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testunited.examples.learnright.provisioning.BookTitle;
import org.testunited.examples.learnright.provisioning.BookTitleProxy;
import org.testunited.examples.learnright.provisioning.Course;
import org.testunited.examples.learnright.provisioning.CourseProxy;
import org.testunited.examples.learnright.provisioning.ProvisioningController;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
class ProvisioningControllerTests {

	@InjectMocks
	private ProvisioningController controller;

	@Mock
	private CourseProxy courseProxy;

	@Mock
	private BookTitleProxy bookTitleProxy;

	private MockMvc mockMvc;

	BookTitle bookTitle3 = new BookTitle(3,
			"Mastering Spring Boot 2.0: Build modern, cloud-native, and distributed systems using Spring Boot",
			"Dinesh Rajput");
	Course course5 = new Course(5, bookTitle3, "Mastering Spring Boot 2.0 - Sept 2019 Intake", new Date(2019, 6, 1),
			new Date(2019, 12, 1));

	@BeforeEach
	void setUp() throws Exception {
	}

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		mockMvc = standaloneSetup(controller).build();
	}

	@Test
	void testCourseProvisionRequests_CouseProxyNullResponse() throws Exception {
		when(this.bookTitleProxy.getBookTitle(bookTitle3.getId())).thenReturn(bookTitle3);
		when(this.courseProxy.createCourse(course5)).thenReturn(null);

		String courseJson = new ObjectMapper().writeValueAsString(course5);

		var result = this.mockMvc
				.perform(post("/course-provision-requests").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(courseJson).accept(MediaType.APPLICATION_JSON_UTF8));
		result.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message", Matchers.is("Course Provisioning Error")));
	}

	@Test
	void testCourseProvisionRequests_NonExistentBookTitle() throws Exception {
		when(this.bookTitleProxy.getBookTitle(bookTitle3.getId())).thenReturn(null);
		when(this.courseProxy.createCourse(course5)).thenReturn(course5);

		String courseJson = new ObjectMapper().writeValueAsString(course5);

		var result = this.mockMvc
				.perform(post("/course-provision-requests").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(courseJson).accept(MediaType.APPLICATION_JSON_UTF8));
		result.andExpect(status().isNotFound()).andExpect(jsonPath("$.message", Matchers.is("Book Title Not Found")));
	}

	@Test
	void testProvision() throws Exception {
		when(this.bookTitleProxy.getBookTitle(bookTitle3.getId())).thenReturn(bookTitle3);
		when(this.courseProxy.createCourse(argThat(c -> c.getId() == course5.getId()))).thenReturn(course5);

		String courseJson = new ObjectMapper().writeValueAsString(course5);

		var result = this.mockMvc
				.perform(post("/course-provision-requests").contentType(MediaType.APPLICATION_JSON_UTF8)
						.content(courseJson).accept(MediaType.APPLICATION_JSON_UTF8));
		result.andExpect(status().isCreated()).andExpect(jsonPath("$.id", Matchers.is(course5.getId())))
				.andExpect(jsonPath("$.name", Matchers.is(course5.getName())));
	}
}
