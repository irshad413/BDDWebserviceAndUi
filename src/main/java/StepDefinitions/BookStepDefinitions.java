package StepDefinitions;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Assert;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import com.jayway.restassured.path.xml.XmlPath;

public class BookStepDefinitions {

	private Response response;
	private ValidatableResponse json;
	private RequestSpecification request;

	private String ENDPOINT_GET_BOOK_BY_ISBN = "https://www.googleapis.com/books/v1/volumes";


	@Given("a book exists with an isbn of (.*)")
	public void bookExistsWithIsbn(String isbn){
		request = given().param("q", "isbn:" + isbn);
		//what you give in param completely depends on the parameters Service is expecting
		//if parameters won't match then service fails
		
	}

	@When("a user retrieves the book by isbn")
	public void retrievevBookWithIsbn(){
		response = request.relaxedHTTPSValidation().when().get(ENDPOINT_GET_BOOK_BY_ISBN);
		System.out.println("response: " + response.prettyPrint());
		
		/** relaxedHTTPSvalidation is to avoid SSL Handshake failures
		 * If your service has any Certificates, use request..keystore("/pathToJksInClassPath", <password>).when()
		 */
	}

	@Then("the status code is (\\d+)")
	public void verifyResponseStatus(int statusCode){
		json = response.then().statusCode(statusCode);
	}

	@And("response includes the following$")
	public void response_equals(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			}
			else{
				json.body(field.getKey(), equalTo(field.getValue()));
			}
		}
	}

	@And("response includes the following in any order")
	public void response_contains_in_any_order(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				json.body(field.getKey(), containsInAnyOrder(Integer.parseInt(field.getValue())));
			}
			else{
				json.body(field.getKey(), containsInAnyOrder(field.getValue()));
			}
		}
	}
	
	@Given("^user calls webservice for registration$")
	public void userInitiatesregistration(Map<String,String> requestFileds) throws Throwable {
		request = given().accept(ContentType.JSON).contentType(ContentType.JSON).body(new JSONObject(requestFileds));
		System.out.println("Request JSON we are posting is \n"+new JSONObject(requestFileds));
		//passing whole map in request as JSON Object
	}

	@When("^user posts a request with information$")
	public void userPostsRequestWithInformation() {
		response = request.relaxedHTTPSValidation().when().post("http://restapi.demoqa.com/customer/register");
		System.out.println("response: " + response.prettyPrint());
	}
	
	/** demonstration validation can be done by extracting the response to JSON Object
	 * @throws Exception
	 */
	@Then("^error message should be recieved$")
	public void userCreationFailed() throws Exception{
		Assert.assertTrue("Response is not received", response.statusCode() == 200);
		JSONParser parser = new JSONParser();
		JSONObject json2 = (JSONObject) parser.parse(response.body().asString());
		if(json2.containsKey("FaultId")){
			Assert.assertTrue("Fault string did not match", json2.get("FaultId").equals("User already exists"));
		}
	}
	
	//hardcoded the SOAP Request here for now
	//For Large scale implementations we need to have the WSDL in resources and apache.cxf plugin in place
	//then request creation would be easy, in such case we use "given().parameters(....)" method
	@Given("^user has a webservice for currency converter$")
	public void userInitiatesCurrencyConverter() {
	    String myXmlReq = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\"><soapenv:Header/><soapenv:Body><tem:GetCurrencies/></soapenv:Body></soapenv:Envelope>";
	    request = given().request().body(myXmlReq).headers("SOAPAction", "http://tempuri.org/GetCurrencies", "Content-Type", "text/xml");
	    //SOAP Action can be derived from WSDL
	}

	@When("^user tries to retrieve all currencies$")
	public void userPostsForCurrencyRequests() {
		response = request.relaxedHTTPSValidation().when().post("http://currencyconverter.kowabunga.net/converter.asmx");
		System.out.println(response.asString());
	}

	@Then("^response should have all currencies$")
	public void validateCurrenciesInResponse() {
	    List <String> currencies = XmlPath.from(response.asString()).getList("Envelope.Body.GetCurrenciesResponse");
		//getList("Envelope.Body.GetCurrenciesResponse.find { it.@type == 'GetCurrenciesResult' }.string")
	    System.out.println(currencies);
	}

}


