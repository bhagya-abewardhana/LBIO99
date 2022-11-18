package OrderPacking;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifyRejectPacking  {
    @Test(priority = 9)
    public void verifyParcelScanPackApi() {
        JSONObject reqBodyForParcelScan = new JSONObject();
        reqBodyForParcelScan.put("parcelId", pickCompletedParcelIdForReject);
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
        orderCodeForReject = respForParcelScanPackApi.jsonPath().getString("orderCode");
        System.out.println(orderCodeForReject);
        idForReject = respForParcelScanPackApi.jsonPath().getInt("id");
        System.out.println(idForReject);
    }

    @Test(priority = 10)
    public void verifyChangeStatusApi() {
        System.out.println(token);
        System.out.println(orderCodeForReject);
        System.out.println(idForReject);
        Response respForChangeStatusApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/cartservice/api/v1/orders/ordernumber/" + orderCodeForReject + "/deliveryaddress/changedstatus");

    }

    @Test(priority = 11)
    public void verifyPackageItemsApi() {
        System.out.println(orderCode);
        System.out.println(idForReject);
        Response respForPackageItemsApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/outboundservice/api/v1/orders/" + idForReject + "/package-items");
    }

    @Test(priority = 12)
    public void verifyTimeStartCall() {
        JSONObject reqBodyForTimeStart = new JSONObject();

        reqBodyForTimeStart.put("at", "2022-10-22T10:38:18.967Z");
        reqBodyForTimeStart.put("entityFlag", "ORDER");
        reqBodyForTimeStart.put("entityId", idForReject);
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

    @Test(priority = 13)
    public void verifyParcelScanPackRejectApi() {
        JSONObject reqBodyForParcelScan = new JSONObject();
        reqBodyForParcelScan.put("parcelId", pickCompletedParcelIdForReject);
        reqBodyForParcelScan.put("isPacked", false);

        Response respForParcelScanPackApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForParcelScan).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                patch(baseUrl + "/outboundservice/api/v1/orders/package-items-groups/parcel-scan-pack");
    }

    @Test(priority = 14)
    public void verifyChangeStatusRejectApi() {
        System.out.println(token);
        System.out.println(orderCodeForReject);
        System.out.println(idForReject);
        Response respForChangeStatusApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/cartservice/api/v1/orders/ordernumber/" + orderCodeForReject + "/deliveryaddress/changedstatus");

    }

    @Test(priority = 15)
    public void verifyPackageItemsRejectApi() {
        System.out.println(orderCodeForReject);
        System.out.println(idForReject);
        Response respForPackageItemsApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/outboundservice/api/v1/orders/" + idForReject + "/package-items");
    }

    @Test(priority = 16)
    public void verifyParcelPackRejectApi() {
        JSONObject reqBodyForParcelReject = new JSONObject();
        reqBodyForParcelReject.put("orderId", idForReject);
        reqBodyForParcelReject.put("rejectNote", "test rejection");

        Response respForParcelScanPackApi = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForParcelReject).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                patch(baseUrl + "/outboundservice/api/v1/orders/package-items-groups/reject");
    }
}
