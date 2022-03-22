package org.testunited.examples.learnright.provisioning

import org.springframework.cloud.contract.spec.Contract

Contract.make {
	request {
		method 'POST'
		url '/course-provision-requests'
		body(
			id: $(p(5),c(anyNumber())),
			name: $(p("Mastering Spring Boot 2.0 - Sept 2019 Intake"),c(nonEmpty())),
			bookTitle: [
				id : $(p(3),c(anyNumber()))
			]
		)
		headers {
			header('Content-Type', 'application/json')
		}
	}
	response {
		status 201
		body (
			id: fromRequest().body('$.id'),
			name: fromRequest().body('$.name'),
			bookTitle: [
				id : fromRequest().body('$.bookTitle.id')
			]
		)
		headers {
			header('Content-Type': 'application/json')
		}
	}
}