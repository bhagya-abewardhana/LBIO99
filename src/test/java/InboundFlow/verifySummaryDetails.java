package InboundFlow;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifySummaryDetails extends verifyDimensionsDetails{

    @Test(priority = 9)
    public void verifyGetRfidCheckCall(){
        Response respForGetRfidCheckCall = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(404).
                log().ifValidationFails().
                when().
                get(baseUrl + "/inboundservice/api/v1/itemsgroup/rfid/" + rfId );
    }

    @Test(priority = 10)
    public void verifySummaryPageCompleteButtonCall(){
        JSONObject reqBodyForDimensions = new JSONObject();
        reqBodyForDimensions.put("length",2);
        reqBodyForDimensions.put("height",2);
        reqBodyForDimensions.put("width",4);
        reqBodyForDimensions.put("weight",2.1);
        JSONArray reqBodyForStages = new JSONArray();
        reqBodyForStages.add("CREATE");
        reqBodyForStages.add("ITEM_DETAIL");
        reqBodyForStages.add("IMAGE");
        reqBodyForStages.add("DIMENSION");
        JSONObject reqBodyForResources = new JSONObject();
        reqBodyForResources.put("type","IMAGE");
        reqBodyForResources.put("value",imageUrl);
        JSONArray reqBodyForResourcesArray = new JSONArray();
        reqBodyForResourcesArray.add(reqBodyForResources);
        JSONObject reqBodyForItems = new JSONObject();
        reqBodyForItems.put("itemDisplayName", "Personal Effects");
        reqBodyForItems.put("itemDescription", "Personal Effects");
        reqBodyForItems.put("qty", 1);
        JSONArray reqBodyForItemsArray = new JSONArray();
        reqBodyForItemsArray.add(reqBodyForItems);
        JSONObject reqBodyForItemGroup = new JSONObject();
        reqBodyForItemGroup.put("parcelType", "POUCH");
        reqBodyForItemGroup.put("isLithiumBatteryIncluded", false);
        reqBodyForItemGroup.put("isDamaged", false);
        reqBodyForItemGroup.put("isInspectionNone", true);
        reqBodyForItemGroup.put("isProhibited", false);
        reqBodyForItemGroup.put("isDangerous", false);
        reqBodyForItemGroup.put("isParcelGrouped", false);
        reqBodyForItemGroup.put("items", reqBodyForItemsArray);
        reqBodyForItemGroup.put("noOfParcels", 1);
        reqBodyForItemGroup.put("totalItemQty", 1);
        reqBodyForItemGroup.put("isProcessing", true);
        reqBodyForItemGroup.put("isLocked", null);
        reqBodyForItemGroup.put("isActive", true);
        reqBodyForItemGroup.put("id", itemGroupsId);
        reqBodyForItemGroup.put("currentStage", "METADATA_MAPPING");
        reqBodyForItemGroup.put("nextStage", "METADATA_MAPPING");
        reqBodyForItemGroup.put("inboundBin", "1-1-A");
        reqBodyForItemGroup.put("igId", igId);
        reqBodyForItemGroup.put("rfid", rfId);
        reqBodyForItemGroup.put("dimensions", reqBodyForDimensions);
        reqBodyForItemGroup.put("resources", reqBodyForResourcesArray);
        reqBodyForItemGroup.put("created", "2022-09-03T14:24:30.339Z");
        reqBodyForItemGroup.put("updated", "2022-09-03T14:24:30.339Z");
        JSONArray reqBodyForItemGroupArray = new JSONArray();
        reqBodyForItemGroupArray.add(reqBodyForItemGroup);
        JSONObject reqBodyForSummaryPage = new JSONObject();
        reqBodyForSummaryPage.put("warehouseId", 1);
        reqBodyForSummaryPage.put("trackingId", trackingId);
        reqBodyForSummaryPage.put("customerAccount", customerAccount);
        reqBodyForSummaryPage.put("totParcels", 1);
        reqBodyForSummaryPage.put("qty", 1);
        reqBodyForSummaryPage.put("currentStage", "METADATA_MAPPING");
        reqBodyForSummaryPage.put("nextStage", "METADATA_MAPPING");
        reqBodyForSummaryPage.put("itemGroups", reqBodyForItemGroupArray);
        reqBodyForSummaryPage.put("isSpecialHandling", false);
        reqBodyForSummaryPage.put("created", "2022-08-12T05:39:47.053Z");
        reqBodyForSummaryPage.put("updated", "2022-08-12T05:39:47.053Z");
        reqBodyForSummaryPage.put("Stages", reqBodyForStages);

        Response respForSummaryPage = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForSummaryPage).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                put(baseUrl + "/inboundservice/api/v1/package/" + packageId);

        Assert.assertEquals(respForSummaryPage.jsonPath().getInt("trackingId"), reqBodyForSummaryPage.get("trackingId"));
        Assert.assertEquals(respForSummaryPage.jsonPath().getInt("id"),(packageId));
    }

    @Test(priority = 11)
    public void verifyPostTimeStopCall() {
        JSONObject reqBodyForTimeStop = new JSONObject();
        reqBodyForTimeStop.put("at", "2022-08-12T05:54:06.741Z");
        reqBodyForTimeStop.put("entityFlag", "PACKAGE");
        reqBodyForTimeStop.put("entityId", packageId);
        reqBodyForTimeStop.put("operation", "END");
        reqBodyForTimeStop.put("operationCode", "INBOUND");
        reqBodyForTimeStop.put("userName", userName);

        Response respForTimeStop = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForTimeStop).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                post(baseUrl + "/commonservice/api/v1/operations/time");

        Assert.assertEquals(respForTimeStop.jsonPath().getInt("entityId"), reqBodyForTimeStop.get("entityId"));
        Assert.assertEquals(respForTimeStop.jsonPath().getString("userName"), reqBodyForTimeStop.get("userName"));
    }
}
