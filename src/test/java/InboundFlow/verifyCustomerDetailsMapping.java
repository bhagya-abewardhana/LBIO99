package InboundFlow;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifyCustomerDetailsMapping {

    @Test(priority = 0)
    public void verifyGetPackageByTrackingId() {

        Response respForGetPackageByTrackingId = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(500).
                log().ifValidationFails().
                when().
                get(baseUrl + "/inboundservice/api/v1/package/trackingid/" + trackingId);
        System.out.println(trackingId);
    }

    @Test(priority = 1)
    public void verifyGetCustomerByAccNumber() {
        Response respForGetCustomerByAccNumber = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/usercustomerservice/api/v1/customers/accounts/" + customerAccount);
    }

    @Test(priority = 2)
    public void verifyPostInitialPackageCreation() {
        JSONObject reqBodyForPackage = new JSONObject();
        reqBodyForPackage.put("warehouseId", 1);
        reqBodyForPackage.put("trackingId", trackingId);
        reqBodyForPackage.put("customerAccount", customerAccount);
        reqBodyForPackage.put("currentStage", "CREATE");
        reqBodyForPackage.put("nextStage", "CREATE");
        reqBodyForPackage.put("created", "2022-06-03T17:31:11.005Z");
        reqBodyForPackage.put("updated", "2022-06-03T17:31:11.005Z");
        reqBodyForPackage.put("isSpecialHandling", true);

        Response respForPackageCreation = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForPackage).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                post(baseUrl + "/inboundservice/api/v1/package");

        Assert.assertEquals(respForPackageCreation.jsonPath().getInt("trackingId"), reqBodyForPackage.get("trackingId"));
        Assert.assertEquals(respForPackageCreation.jsonPath().getString("customerAccount"), reqBodyForPackage.get("customerAccount"));
        Assert.assertNotNull(respForPackageCreation.jsonPath().getInt("id"));

        packageId = respForPackageCreation.jsonPath().getInt("id");
    }

    @Test(priority = 3)
    public void verifyTimeStartCall() {
        JSONObject reqBodyForTimeStart = new JSONObject();

        reqBodyForTimeStart.put("at", "2022-08-12T05:31:51.819Z");
        reqBodyForTimeStart.put("entityFlag", "PACKAGE");
        reqBodyForTimeStart.put("entityId", packageId);
        reqBodyForTimeStart.put("operation", "START");
        reqBodyForTimeStart.put("operationCode", "INBOUND");
        reqBodyForTimeStart.put("userName", userName);

        Response respForTimeStart = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForTimeStart).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                post(baseUrl + "/commonservice/api/v1/operations/time");

        Assert.assertEquals(respForTimeStart.jsonPath().getInt("entityId"), reqBodyForTimeStart.get("entityId"));
        Assert.assertEquals(respForTimeStart.jsonPath().getString("operationCode"), reqBodyForTimeStart.get("operationCode"));
        Assert.assertEquals(respForTimeStart.jsonPath().getString("userName"), reqBodyForTimeStart.get("userName"));
    }
}
