package stepDefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.CoreConnectionPNames;
import resources.PetAPIResources;
import resources.PetDataBuilder;

import resources.Utils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;


public class petStepDefinition extends Utils {
    RequestSpecification request;
    ResponseSpecification responseSpecification;
    Response response;
    static String petId;
    PetDataBuilder data = new PetDataBuilder();
    RequestConfig requestConfig = RequestConfig.custom()
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(5000)
            .build();

    private String timeStamp = new SimpleDateFormat("ddHHss").format(new Date());

    @When("Add pet Payload with {string} {string} {string} {string} {string} {string}")
    public void createPet(String id, String petName, String category, String photoURL,String tagName, String status) throws IOException {

        request =given().spec(requestSpecification())
                .body(data.addPetLoad(id + timeStamp,petName,category,photoURL,tagName,status));
    }

    @When("The user calls {string} with {string} http request")
    public void userCallsHttpRequest(String resource, String method) {
        PetAPIResources resourceAPI = PetAPIResources.valueOf(resource);
        System.out.println(resourceAPI.getResource());

        responseSpecification = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if (method.equalsIgnoreCase("POST"))
            response = request.when().post(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = request.when().get(resourceAPI.getResource());
        else if (method.equalsIgnoreCase("DELETE"))
            response = request.when().delete(resourceAPI.getResource());
    }

    @Given("Get pet payload")
    public void getPetDetails() throws IOException, InterruptedException {
        request =given().spec(requestSpecification()).pathParam("petId",petId);
    }

    @Then("API call got success with status code {int}")
    public void verifyStatusCode(int expectedStatus) {
        assertEquals(response.getStatusCode(),expectedStatus);
    }

    @Then("verify pet_Id created maps to {string} {string} using {string}")
    public void verifyRecentlyCreatedPet(String expectedName,String expectedStatus, String resource) throws IOException {

        petId=getJsonPath(response,"id");
        System.out.println(petId);
        request = given().spec(requestSpecification()).pathParam("petId",petId);
        userCallsHttpRequest(resource,"GET");
        userCallsHttpRequest(resource,"GET");
        String actualName=getJsonPath(response,"name");
        String actualStatus=getJsonPath(response,"status");
        assertEquals(actualName,expectedName);
        assertEquals(actualStatus,expectedStatus);
    }

    @When("Update the pet with {string} and {string}")
    public void updateThePet(String petName, String status) throws IOException {
        request = given().spec(requestSpecification()).queryParam("name",petName).queryParam("status",status)
                .pathParam("petId",petId);
    }

    @Given("^DeletePet Payload$")
    public void deletePetPayload() throws IOException {
        request =given().spec(requestSpecification()).pathParam("petId",petId);
    }
}
