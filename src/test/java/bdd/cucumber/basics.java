package bdd.cucumber;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import files.addplace_payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class basics {
	
		 static String place_id;
		 static String address;
		public static void main(String[] args) {
			// TODO Auto-generated method stub
			
			RestAssured.baseURI="https://rahulshettyacademy.com/";
			
//			Post method
			
			String response = given().log().all().queryParam("key", "qaclick123")
							  .header("Content-Type","application/json")
							  .body(addplace_payload.bodyFile())
							  .when()
							  .post("/maps/api/place/add/json")
							  .then().log().all()
							  .statusCode(200)
							  .body("scope",equalTo("APP") )
							  .extract().response().asString();
			
			System.out.println(response);
			JsonPath js = new JsonPath(response);
			 place_id = js.get("place_id");
			
			System.out.println("place_id: "+place_id);
			System.out.println("*********************************************************************8");
			
//			UPDATE method
			address = "48,Indra nagar, north,PALANI";
			 String update = given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
			.body("{\r\n"
					+ "\"place_id\":\""+place_id+"\",\r\n"
					+ "\"address\":\""+address+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}\r\n"
					+ "")
			.when().put("/maps/api/place/update/json")
			.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).extract().response().asString();
			System.out.println("updated msg "+ update );
			System.out.println("post method");
			
			System.out.println("*********************************************************************8");

//			Get method
			
			given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
			.when().get("/maps/api/place/get/json")
			.then().log().all().assertThat().statusCode(200).body("address", equalTo(address) );
			System.out.println("get method");
			

			System.out.println("*********************************************************************8");

	// Delete method
			String del_res = given().log().all().queryParam("key",  "qaclick123").body("{\r\n"
					+ "\r\n"
					+ "    \"place_id\":\""+place_id+"\"\r\n"
					+ "}").when().delete("/maps/api/place/delete/json").then().log().all().assertThat().statusCode(200).extract().response().asString();
			System.out.println(del_res);
		}
	}

