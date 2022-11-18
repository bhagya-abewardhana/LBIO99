package OrderPacking;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifyEnterPackingParcelId {
    @Test(priority = 0)
    public void verifyParcelScanPackApi(){
        JSONObject reqBodyForParcelScan = new JSONObject();
        reqBodyForParcelScan.put("parcelId", packingParcelId);
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
        orderCodeForPacking = respForParcelScanPackApi.jsonPath().getString("orderCode");
        System.out.println(orderCodeForPacking);
        idForPacking = respForParcelScanPackApi.jsonPath().getInt("id");
        System.out.println(idForPacking);
    }

    @Test(priority = 1)
    public void verifyChangeStatusApi(){
        System.out.println(token);
        System.out.println(orderCodeForPacking);
        System.out.println(idForPacking);
        Response respForChangeStatusApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/cartservice/api/v1/orders/ordernumber/"+ orderCodeForPacking + "/deliveryaddress/changedstatus");

    }
    @Test(priority = 2)
    public void verifyPackageItemsApi() {
        System.out.println(orderCodeForPacking);
        System.out.println(idForPacking);
        Response respForPackageItemsApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/outboundservice/api/v1/orders/" + idForPacking + "/package-items");
    }
    @Test(priority = 3)
    public void verifyTimeStartCall() {
        JSONObject reqBodyForTimeStart = new JSONObject();

        reqBodyForTimeStart.put("at", "2022-10-22T10:38:18.967Z");
        reqBodyForTimeStart.put("entityFlag", "ORDER");
        reqBodyForTimeStart.put("entityId", idForPacking);
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
