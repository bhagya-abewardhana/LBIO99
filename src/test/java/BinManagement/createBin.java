package BinManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class createBin {

@Test(priority = 0)
    public void verifyBinCreationAPI(){
    JSONObject reqBodyForBinCreate = new JSONObject();
    reqBodyForBinCreate.put("warehouseId", 1);
    reqBodyForBinCreate.put("code", newBinCode);
    reqBodyForBinCreate.put("status", statusOfBin);
    reqBodyForBinCreate.put("type", type);

    Response respForBinCreationAPI = RestAssured.given().relaxedHTTPSValidation().
            headers("Authorization", "Bearer " + token).
            contentType("application/json").
            body(reqBodyForBinCreate).
            expect().
            statusCode(200).
            log().ifValidationFails().
            when().
            post(baseUrl + "/binservice/api/v1/bins");
    System.out.println(respForBinCreationAPI.jsonPath().getInt("id"));
    Assert.assertEquals(respForBinCreationAPI.jsonPath().getString("code"), reqBodyForBinCreate.get("code"));
    Assert.assertNotNull(respForBinCreationAPI.jsonPath().getInt("id"));

    binId = respForBinCreationAPI.jsonPath().getInt("id");
    System.out.println("bin id " + binId);

    }


}
