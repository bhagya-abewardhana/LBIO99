package BinManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class deleteBin extends editBin {
    @Test(priority = 2)
    public void verifyBinDeleteAPI(){
        Response respForBinDeleteAPI = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                delete(baseUrl + "/binservice/api/v1/bins/"+ binId);
    }
}
