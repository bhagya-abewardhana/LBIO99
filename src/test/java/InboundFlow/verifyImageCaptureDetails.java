package InboundFlow;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static InboundFlow.data.*;

public class verifyImageCaptureDetails extends verifyItemDetailsMapping {

    @Test(priority = 5)
    public static void verifyImageCaptureButtonCall(){
        JSONObject reqBodyForImage = new JSONObject();
        File uploadFile = new File("C:\\Users\\bhagyaa\\Desktop\\API_Automation with REST\\image24.png");

        Response respForImageUpload = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                //contentType("application/json").
                multiPart(uploadFile).
                body(reqBodyForImage).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                post(baseUrl + "/inboundservice/api/v1/itemsgroup/image/upload");

        Assert.assertNotNull(respForImageUpload.jsonPath().getString("imageUrl"));

        imageUrl = respForImageUpload.jsonPath().getString("imageUrl");
    }

    @Test(priority = 6)
    public void verifyImageCapturePageNextButtonCall(){
        JSONArray reqBodyForStages = new JSONArray();
        reqBodyForStages.add("CREATE");
        reqBodyForStages.add("ITEM_DETAIL");
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
        reqBodyForItemGroup.put("currentStage", "IMAGE");
        reqBodyForItemGroup.put("nextStage", "IMAGE");
        reqBodyForItemGroup.put("resources", reqBodyForResourcesArray);
        reqBodyForItemGroup.put("created", "2022-09-03T14:24:30.339Z");
        reqBodyForItemGroup.put("updated", "2022-09-03T14:24:30.339Z");
        JSONArray reqBodyForItemGroupArray = new JSONArray();
        reqBodyForItemGroupArray.add(reqBodyForItemGroup);
        JSONObject reqBodyForImageDetails = new JSONObject();
        reqBodyForImageDetails.put("warehouseId", 1);
        reqBodyForImageDetails.put("trackingId", trackingId);
        reqBodyForImageDetails.put("customerAccount", customerAccount);
        reqBodyForImageDetails.put("totParcels", 1);
        reqBodyForImageDetails.put("qty", 1);
        reqBodyForImageDetails.put("currentStage", "IMAGE");
        reqBodyForImageDetails.put("nextStage", "IMAGE");
        reqBodyForImageDetails.put("itemGroups", reqBodyForItemGroupArray);
        reqBodyForImageDetails.put("isSpecialHandling", false);
        reqBodyForImageDetails.put("created", "2022-08-12T05:39:47.053Z");
        reqBodyForImageDetails.put("updated", "2022-08-12T05:39:47.053Z");
        reqBodyForImageDetails.put("Stages", reqBodyForStages);

        Response respForImageDetails = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForImageDetails).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                put(baseUrl + "/inboundservice/api/v1/package/"+ packageId);

        Assert.assertEquals(respForImageDetails.jsonPath().getInt("trackingId"), reqBodyForImageDetails.get("trackingId"));
        Assert.assertEquals(respForImageDetails.jsonPath().getInt("id"),(packageId));

    }
}
