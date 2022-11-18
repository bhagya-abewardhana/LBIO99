package OrderPacking;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifyPackComplete extends verifyEnterPickCompletedParcelId{

    @Test(priority = 8)
    public void verifyPackCompleteApi(){
        JSONObject reqBodyForPackComplete = new JSONObject();
        reqBodyForPackComplete.put("orderId", id);
        reqBodyForPackComplete.put("height", 4);
        reqBodyForPackComplete.put("weight", 3);
        reqBodyForPackComplete.put("length", 3);
        reqBodyForPackComplete.put("width", 3);
        reqBodyForPackComplete.put("lengthUnit", "In");
        reqBodyForPackComplete.put("weightUnit", "lb");
        reqBodyForPackComplete.put("userName", userName);
        reqBodyForPackComplete.put("isComplete", true);
        JSONArray reqBodyForParcelIdArray = new JSONArray();
        reqBodyForParcelIdArray.add( pickCompletedParcelId);
        reqBodyForPackComplete.put("parcelIds", reqBodyForParcelIdArray);



        Response respForPackCompleteApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForPackComplete).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                post(baseUrl + "/outboundservice/api/v1/orders/order-consignments/package-items-groups/complete-pack");

    }
}
