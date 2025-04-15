package net.apicall.demo.Controller;

import static io.restassured.RestAssured.given;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Utlility.ConsoleColors;
import Utlility.ValuePusher;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;

@RestController
@RequestMapping("/selldo")
public class SelldoController {

	private static final String domain = "https://v2.sell.do";
	private static final String CID = "587ddb2b5a9db31da9000002";
	private static final String website = "fa8d6ca0217e676a7b0e06f51c32568c";
	private static final String fullAPI = "c13ad8e13264b1c22bc39bb475889c7e";
	private static final String SRD = "";
	private static final String Project = "";
	 
	@GetMapping("id/{lead}")
	public String getLead(@PathVariable String lead) {

		String URLP = domain + "/api/leads/phone/retrieve_lead";
		String URLE = domain + "/api/leads/email/retrieve_lead";
		String URL = lead.contains("@") ? URLE : URLP;
		if (!lead.contains("@") && lead.trim().length() > 10) {
			lead = lead.replaceAll("[+]", "").trim().substring(lead.length() - 10, lead.length());
		}
		System.out.println(ConsoleColors.YELLOW_BOLD_BRIGHT + "Searched Element in Retrive lead API >> "
				+ ConsoleColors.RED_BOLD_BRIGHT + lead + ConsoleColors.RESET);
		return given().urlEncodingEnabled(false).contentType(ContentType.JSON).with().queryParam("api_key", fullAPI)
				.queryParam("client_id", CID).queryParam("value", lead).get(URL).then().parser("text/html", Parser.JSON)
				.statusCode(200).extract().response().jsonPath().prettyPrint();
	}
	
	@PostMapping("/createlead")
	public String createLead(@RequestBody ValuePusher je) {
		
		String name = je.getValue_1();
		String email = je.getValue_2();
		String phone = je.getValue_3();
		String source = je.getValue_4();
		String subsource = je.getValue_5();
		String payload = "{\r\n"
				+ "    \"sell_do\": {\r\n"
				+ "        \"form\": {\r\n"
				+ "            \"lead\": {\r\n"
				+ "                \"name\": \""+name+"\",\r\n"
				+ "                \"phone\": \""+phone+"\",\r\n"
				+ "                \"email\": \""+email+"\",\r\n"
				+ "                \"sub_source\": \""+subsource+"\",\r\n"
				+ "                \"source\": \""+source+"\",\r\n"
				+ "                \"project_id\": \""+Project+"\"\r\n"
				+ "            }\r\n"
				+ "        },\r\n"
				+ "        \"campaign\": {\r\n"
				+ "             \"srd\": \""+SRD+"\" \r\n"
				+ "        }\r\n"
				+ "    },\r\n"
				+ "    \"api_key\": \""+website+"\"\r\n"
				+ "}";
		
		return RestAssured.given().urlEncodingEnabled(false).contentType(ContentType.JSON).body(payload).when()
				.post(domain + "/api/leads/create").then().parser("text/html", Parser.JSON).extract().response().jsonPath().getString("sell_do_lead_id");
	}
}
