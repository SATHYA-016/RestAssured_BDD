package step_defenition;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import Resources.build;
import files.addplace_payload;

import static org.junit.Assert.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

public class add_place {

	RequestSpecification req ;
	ResponseSpecification resspec;
	RequestSpecification res;
	Response response ;
	build data = new build();
	
	@Given("Add Place Payload with name,language,address")
	public void add_place_payload_with_name_language_address() {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI="https://rahulshettyacademy.com";


		 req =new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123")
		.setContentType(ContentType.JSON).build();
		 
		 
		resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		res=given().spec(req)
		.body(data.addplace_payload());
	}
	
	@When("user calls AddPlaceAPI with POST http request")
	public void user_calls_add_place_api_with_post_http_request() {
	    // Write code here that turns the phrase above into concrete actions
		response =res.when().post("/maps/api/place/add/json").
		then().spec(resspec).extract().response();
	}
	
	@Then("the API call got success with status code {int}")
	public void the_api_call_got_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(response.getStatusCode(),200);
	}
	
	@Then("{string} in response body is {string}")
	public void status_in_response_body_is_ok(String key, String value) {
	    // Write code here that turns the phrase above into concrete actions
	    String resp = response.asString();
	    JsonPath js = new JsonPath(resp);
	    assertEquals(js.get(key).toString(),value);
	}
	
}
