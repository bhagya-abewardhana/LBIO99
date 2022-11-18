package InboundFlow;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static InboundFlow.data.*;

public class verifyItemDetailsMapping extends verifyCustomerDetailsMapping {

    @Test(priority = 4)
    public void verifyItemDetailsPageNextButtonCall(){
        JSONArray reqBodyForStagesArray = new JSONArray();
        reqBodyForStagesArray.add("CREATE");
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
        reqBodyForItemGroup.put("isProcessing", false);
        reqBodyForItemGroup.put("isLocked", false);
        reqBodyForItemGroup.put("isActive", true);
        reqBodyForItemGroup.put("created", "2022-09-03T14:24:30.339Z");
        reqBodyForItemGroup.put("updated", "2022-09-03T14:24:30.339Z");
        JSONArray reqBodyForItemGroupArray = new JSONArray();
        reqBodyForItemGroupArray.add(reqBodyForItemGroup);
        JSONObject reqBodyForItemDetails = new JSONObject();
        reqBodyForItemDetails.put("warehouseId", 1);
        reqBodyForItemDetails.put("trackingId", trackingId);
        reqBodyForItemDetails.put("customerAccount", customerAccount);
        reqBodyForItemDetails.put("totParcels", 1);
        reqBodyForItemDetails.put("qty", 0);
        reqBodyForItemDetails.put("currentStage", "ITEM_DETAIL");
        reqBodyForItemDetails.put("nextStage", "ITEM_DETAIL");
        reqBodyForItemDetails.put("itemGroups", reqBodyForItemGroupArray);
        reqBodyForItemDetails.put("isSpecialHandling", false);
        reqBodyForItemDetails.put("created", "2022-08-12T05:39:47.053Z");
        reqBodyForItemDetails.put("updated", "2022-08-12T05:39:47.053Z");
        reqBodyForItemDetails.put("Stages", reqBodyForStagesArray);

        Response respForItemDetails = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForItemDetails).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                put(baseUrl + "/inboundservice/api/v1/package/"+ packageId);

        itemGroupsId = respForItemDetails.jsonPath().getInt("itemGroups[0].id");

        Assert.assertEquals(respForItemDetails.jsonPath().getInt("trackingId"), reqBodyForItemDetails.get("trackingId"));
        Assert.assertEquals(respForItemDetails.jsonPath().getInt("id"),(packageId));


    }

}
