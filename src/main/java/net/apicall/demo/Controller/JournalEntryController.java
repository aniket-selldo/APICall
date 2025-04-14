package net.apicall.demo.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JournalEntryController {

	@GetMapping("/health-check")
	public String healthCheck() {
		return "OK From Aniket";
	}
}

