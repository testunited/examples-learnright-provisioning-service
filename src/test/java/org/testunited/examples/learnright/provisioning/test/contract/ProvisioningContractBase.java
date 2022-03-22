package org.testunited.examples.learnright.provisioning.test.contract;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import org.testunited.examples.learnright.provisioning.BookTitle;
import org.testunited.examples.learnright.provisioning.BookTitleProxy;
import org.testunited.examples.learnright.provisioning.Course;
import org.testunited.examples.learnright.provisioning.CourseProxy;
import org.testunited.examples.learnright.provisioning.ProvisioningController;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class ProvisioningContractBase {

	@InjectMocks
	private ProvisioningController proCont;

	@Mock
	private CourseProxy courseProxy;

	@Mock
	private BookTitleProxy bookTitleProxy;

	BookTitle bookTitle3 = new BookTitle(3,
			"Mastering Spring Boot 2.0: Build modern, cloud-native, and distributed systems using Spring Boot",
			"Dinesh Rajput");
	Course course5 = new Course(5, bookTitle3, "Mastering Spring Boot 2.0 - Sept 2019 Intake", new Date(2019, 6, 1),
			new Date(2019, 12, 1));

	@BeforeEach
	void setup() {
		// mockMvc = standaloneSetup(proCont).build();
		// when(this.proSvc.provision(course5, bookTitle3.getId())).thenReturn(course5);
		when(this.bookTitleProxy.getBookTitle(bookTitle3.getId())).thenReturn(bookTitle3);
		when(this.courseProxy.createCourse(argThat(c -> c.getId() == course5.getId()))).thenReturn(course5);
		RestAssuredMockMvc.standaloneSetup(this.proCont);
	}
}
