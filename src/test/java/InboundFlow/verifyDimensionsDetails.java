package InboundFlow;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifyDimensionsDetails extends verifyImageCaptureDetails{

    @Test(priority = 7)
    public void verifyGetBinCheckCall(){
        Response respForGetBinCheckCall = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                get(baseUrl + "/binservice/api/v1/bin/code/" + binCode );

        Assert.assertNotNull(respForGetBinCheckCall.jsonPath().getString("id"));
        Assert.assertNotNull(respForGetBinCheckCall.jsonPath().getString("code"));
        Assert.assertNotNull(respForGetBinCheckCall.jsonPath().getString("status"));
        Assert.assertNotNull(respForGetBinCheckCall.jsonPath().getString("type"));
        Assert.assertNotNull(respForGetBinCheckCall.jsonPath().getString("noOfItems"));
        Assert.assertNotNull(respForGetBinCheckCall.jsonPath().getString("warehouseId"));
    }

    @Test (priority = 8)
    public void verifyDimensionsPageNextButtonCall(){
        JSONObject reqBodyForDimensions = new JSONObject();
        reqBodyForDimensions.put("length",2);
        reqBodyForDimensions.put("height",2);
        reqBodyForDimensions.put("width",4);
        reqBodyForDimensions.put("weight",2.1);
        JSONArray reqBodyForStages = new JSONArray();
        reqBodyForStages.add("CREATE");
        reqBodyForStages.add("ITEM_DETAIL");
        reqBodyForStages.add("IMAGE");
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
        reqBodyForItemGroup.put("currentStage", "DIMENSION");
        reqBodyForItemGroup.put("nextStage", "DIMENSION");
        reqBodyForItemGroup.put("inboundBin", "1-1-A");
        reqBodyForItemGroup.put("igId", null);
        reqBodyForItemGroup.put("dimensions", reqBodyForDimensions);
        reqBodyForItemGroup.put("resources", reqBodyForResourcesArray);
        reqBodyForItemGroup.put("created", "2022-09-03T14:24:30.339Z");
        reqBodyForItemGroup.put("updated", "2022-09-03T14:24:30.339Z");
        JSONArray reqBodyForItemGroupArray = new JSONArray();
        reqBodyForItemGroupArray.add(reqBodyForItemGroup);
        JSONObject reqBodyForDimensionsDetails = new JSONObject();
        reqBodyForDimensionsDetails.put("warehouseId", 1);
        reqBodyForDimensionsDetails.put("trackingId", trackingId);
        reqBodyForDimensionsDetails.put("customerAccount", customerAccount);
        reqBodyForDimensionsDetails.put("totParcels", 1);
        reqBodyForDimensionsDetails.put("qty", 1);
        reqBodyForDimensionsDetails.put("currentStage", "DIMENSION");
        reqBodyForDimensionsDetails.put("nextStage", "DIMENSION");
        reqBodyForDimensionsDetails.put("itemGroups", reqBodyForItemGroupArray);
        reqBodyForDimensionsDetails.put("isSpecialHandling", false);
        reqBodyForDimensionsDetails.put("created", "2022-08-12T05:39:47.053Z");
        reqBodyForDimensionsDetails.put("updated", "2022-08-12T05:39:47.053Z");
        reqBodyForDimensionsDetails.put("Stages", reqBodyForStages);
        Response respForDimensionsDetails = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForDimensionsDetails).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                put(baseUrl + "/inboundservice/api/v1/package/" + packageId);

        igId = respForDimensionsDetails.jsonPath().getString("itemGroups[0].igId");

        Assert.assertEquals(respForDimensionsDetails.jsonPath().getInt("trackingId"), reqBodyForDimensionsDetails.get("trackingId"));
        Assert.assertEquals(respForDimensionsDetails.jsonPath().getInt("id"),(packageId));

    }

}
