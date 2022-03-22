package org.testunited.examples.learnright.provisioning;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class BookTitleProxy {
	// Logger logger = LoggerFactory.getLogger(BookTitleProxy.class);
	@Autowired
	private RestTemplate restTemplate;

	@Value("${booktitle.service.url}")
	private String service_url;

	public BookTitle getBookTitle(int bookTitleId) {
		ResponseEntity<BookTitle> response;

		try {
			response = this.restTemplate.getForEntity(
					new URI(this.service_url +  "/booktitles/" + bookTitleId),
					BookTitle.class);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return response.getBody();
	}

}
