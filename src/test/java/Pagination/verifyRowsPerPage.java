package Pagination;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;

import static InboundFlow.data.baseUrl;
import static InboundFlow.data.token;

public class verifyRowsPerPage {
    @Test
    public void verifyRowsPerPageApi(){
        JSONObject reqBodyForRowsPerPage = new JSONObject();
        reqBodyForRowsPerPage.put("currentPage",0);
        reqBodyForRowsPerPage.put("pageSize",10);
        JSONArray reqBodyForSortColumnsArray = new JSONArray();
        reqBodyForSortColumnsArray.add("created");
        reqBodyForRowsPerPage.put("sortColumns", reqBodyForSortColumnsArray);
        reqBodyForRowsPerPage.put("sortOrder","desc");

        System.out.println(reqBodyForRowsPerPage);

        Response respForRowsPerPage = RestAssured.given().relaxedHTTPSValidation().
                headers("Authorization", "Bearer " + token).
                contentType("application/json").
                body(reqBodyForRowsPerPage).
                expect().
                statusCode(200).
                log().ifValidationFails().
                when().
                post(baseUrl + "/inboundservice/api/v1/search/parcels");
        System.out.println(respForRowsPerPage);

    }
}
