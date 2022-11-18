package BinManagement;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class editBin extends createBin {
    @Test(priority = 1)
    public void verifyBinEditingAPI() {
        JSONObject reqBodyForBinEdit = new JSONObject();
        reqBodyForBinEdit.put("binId", binId);
        reqBodyForBinEdit.put("status", statusOfBin);
        reqBodyForBinEdit.put("type", "Pouch");

        Response respForBinCreationAPI = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForBinEdit).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                patch(baseUrl + "/binservice/api/v1/bins");
    }
}
