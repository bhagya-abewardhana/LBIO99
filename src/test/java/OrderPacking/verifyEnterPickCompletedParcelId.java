package OrderPacking;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifyEnterPickCompletedParcelId  {

    @Test(priority = 4)
    public void verifyParcelScanPackApi(){
        JSONObject reqBodyForParcelScan = new JSONObject();
        reqBodyForParcelScan.put("parcelId", pickCompletedParcelId);
        reqBodyForParcelScan.put("isPacked", true);

        Response respForParcelScanPackApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForParcelScan).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                patch(baseUrl + "/outboundservice/api/v1/orders/package-items-groups/parcel-scan-pack");
        orderCode = respForParcelScanPackApi.jsonPath().getString("orderCode");
        id = respForParcelScanPackApi.jsonPath().getInt("id");

    }

    @Test(priority = 5)
    public void verifyChangeStatusApi(){
        System.out.println(token);
        System.out.println(orderCode);
        System.out.println(id);
        Response respForChangeStatusApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/cartservice/api/v1/orders/ordernumber/"+ orderCode + "/deliveryaddress/changedstatus");

    }
    @Test(priority = 6)
    public void verifyPackageItemsApi() {
        System.out.println(orderCode);
        System.out.println(id);
        Response respForPackageItemsApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/outboundservice/api/v1/orders/" + id + "/package-items");
    }
    @Test(priority = 7)
    public void verifyTimeStartCall() {
        JSONObject reqBodyForTimeStart = new JSONObject();

        reqBodyForTimeStart.put("at", "2022-10-22T10:38:18.967Z");
        reqBodyForTimeStart.put("entityFlag", "ORDER");
        reqBodyForTimeStart.put("entityId", id);
        reqBodyForTimeStart.put("operation", "START");
        reqBodyForTimeStart.put("operationCode", "OUTBOUND_PACK");
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
    }
}
