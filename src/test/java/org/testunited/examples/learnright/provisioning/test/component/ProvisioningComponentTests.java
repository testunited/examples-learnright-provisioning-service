package org.testunited.examples.learnright.provisioning.test.component;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.Date;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties.StubsMode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.testunited.examples.learnright.provisioning.BookTitle;
import org.testunited.examples.learnright.provisioning.Course;
import org.testunited.examples.learnright.provisioning.ProvisioningController;

@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@AutoConfigureStubRunner(ids = { "org.testunited.examples.learnright:learnright-booktitle-service:+:stubs:8001",
		"org.testunited.examples.learnright:learnright-course-service:+:stubs:8002" }, stubsMode = StubsMode.LOCAL)
public class ProvisioningComponentTests {

	private MockMvc mockMvc;

	@Autowired
	private ProvisioningController controller;

	@BeforeAll
	public void setUp() throws Exception {
		mockMvc = standaloneSetup(controller).build();
	}

	@Test
	public void testCourseProvisionRequests() throws Exception {
		BookTitle bookTitle3 = new BookTitle(3,
				"Mastering Spring Boot 2.0: Build modern, cloud-native, and distributed systems using Spring Boot",
				"Dinesh Rajput");
		Course course5 = new Course(5, bookTitle3, "Mastering Spring Boot 2.0 - Sept 2019 Intake", new Date(2019, 6, 1),
				new Date(2019, 12, 1));

		String courseJson = new ObjectMapper().writeValueAsString(course5);

		var result = this.mockMvc
				.perform(post("/course-provision-requests").contentType(MediaType.APPLICATION_JSON)
						.content(courseJson).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isCreated()).andExpect(jsonPath("$.id", Matchers.is(course5.getId())))
				.andExpect(jsonPath("$.name", Matchers.is(course5.getName())));
	}

	@Test
	public void testCourseProvisionRequests_NonExistentBookTitle() throws Exception {
		BookTitle bookTitle4 = new BookTitle(4);
		Course course6 = new Course(6, bookTitle4, "Mastering Spring Boot 2.0 - Sept 2019 Intake", new Date(2019, 6, 1),
				new Date(2019, 12, 1));

		String courseJson = new ObjectMapper().writeValueAsString(course6);

		var result = this.mockMvc
				.perform(post("/course-provision-requests").contentType(MediaType.APPLICATION_JSON)
						.content(courseJson).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isNotFound()).andExpect(jsonPath("$.message", Matchers.is("Book Title Not Found")));
	}

}
