package Pagination;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static InboundFlow.data.baseUrl;
import static InboundFlow.data.token;

public class verifyNavigation {
    @Test
    public void verifyNavigationApi(){
        JSONObject reqBodyForNavigation = new JSONObject();
        reqBodyForNavigation.put("currentPage",1);
        reqBodyForNavigation.put("pageSize",10);
        JSONArray reqBodyForSortColumnsArray = new JSONArray();
        reqBodyForSortColumnsArray.add("created");
        reqBodyForNavigation.put("sortColumns", reqBodyForSortColumnsArray);
        reqBodyForNavigation.put("sortOrder","desc");

        System.out.println(reqBodyForNavigation);

        Response respForNavigation = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForNavigation).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                post(baseUrl + "/inboundservice/api/v1/search/parcels");
        System.out.println(respForNavigation);

    }
}

