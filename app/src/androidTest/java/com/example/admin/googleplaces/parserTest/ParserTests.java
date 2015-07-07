package com.example.admin.googleplaces.parserTest;

import android.test.AndroidTestCase;

import com.example.admin.googleplaces.data.Photo;
import com.example.admin.googleplaces.data.PlaceDetails;
import com.example.admin.googleplaces.requests.FetchPlaceDeatilsRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 07.07.2015.
 */
public class ParserTests extends AndroidTestCase {

    //region Constants for working with JSON
    // There are the names of the JSON objects that need to be extracted
    final static String STATUS = "status";

    final static String RESULTS_ARRAY = "results";
    final static String ICON = "icon";
    final static String ID = "id";
    final static String NAME = "name";

    // Items for extracting photo attrs
    final static String PHOTOS_ARRAY = "photos";
    final static String HEIGHT = "height";
    final static String WIDTH = "width";
    final static String HTML_ATTRIBUTIONS = "html_attributions";
    final static String PHOTO_REFERENCE = "photo_reference";

    final static String TYPES = "types";
    final static String PLACE_ID = "place_id";
    final static String RATING = "rating";
    //endregion

    //region Constants for testing Photo object
    private static final int HEIGHT = 3456;
    private static final int WIDTH = 5184;
    private String PHOTO_REFS = "CmRdAAAA1Soy7QuTPuDmSP3EoyuQYmv8U7h458xQ64YqYZE0KLxl-jCsPhRRNdULzyseg4gveQTuAT2R5AYw0HSObw19XUVKpNCTVAwRXiM8ZZC5g-gN3ZiOb6grjwOKTyIThgNPEhDZGV_BmlyKQuRYtr4E6ZSeGhSpiwGHt6gelU9UlolM9DdXJm8fgw";
    private List<String> HTML_ATTRS = Arrays.asList("<a href=\"https://www.google.com/maps/views/profile/106283553457831154892\">Михаил Бадретдинов</a>");
    //endregion

    public static final String LOG_TAG = ParserTests.class.getSimpleName();

    //region Test string
    private static final String JSON_RESPONSE="{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"results\" : [   \n" +
            "      \n" +
            "      {\n" +
            "         \"geometry\" : {\n" +
            "            \"location\" : {\n" +
            "               \"lat\" : 47.206579,\n" +
            "               \"lng\" : 38.931234\n" +
            "            }\n" +
            "         },\n" +
            "         \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/museum-71.png\",\n" +
            "         \"id\" : \"ebd527cf2c50d7a2f18898f1551c444db052445b\",\n" +
            "         \"name\" : \"Домик Чехова\",\n" +
            "         \"photos\" : [\n" +
            "            {\n" +
            "               \"height\" : 3456,\n" +
            "               \"html_attributions\" : [\n" +
            "                  \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/106283553457831154892\\\"\\u003eМихаил Бадретдинов\\u003c/a\\u003e\"\n" +
            "               ],\n" +
            "               \"photo_reference\" : \"CmRdAAAA1Soy7QuTPuDmSP3EoyuQYmv8U7h458xQ64YqYZE0KLxl-jCsPhRRNdULzyseg4gveQTuAT2R5AYw0HSObw19XUVKpNCTVAwRXiM8ZZC5g-gN3ZiOb6grjwOKTyIThgNPEhDZGV_BmlyKQuRYtr4E6ZSeGhSpiwGHt6gelU9UlolM9DdXJm8fgw\",\n" +
            "               \"width\" : 5184\n" +
            "            }\n" +
            "         ],\n" +
            "         \"place_id\" : \"ChIJoW6k8eBX4UARPp4F58iGoFg\",\n" +
            "         \"rating\" : 4.2,\n" +
            "         \"reference\" : \"CnRqAAAATpxfNzYyqEr4KX-ub0ZVvXaglFJdPT3aXDN2uIN2FZA4ELdnyXfX6enI1F16-lqyOaNuxtuobKHMx4kgJY8FbnHQ3YTol_bUgp3IYnq8GATeK1WlaIzMaK3JzlIFTv6WQ6Tqs1oaNSSPRZJH9z7oUxIQ7cCeklm2CZkgbBTbYIU44BoUL-5QTNAI9LMWk595ajQ4kowooT8\",\n" +
            "         \"scope\" : \"GOOGLE\",\n" +
            "         \"types\" : [ \"museum\", \"point_of_interest\", \"establishment\" ],\n" +
            "         \"vicinity\" : \"\"\n" +
            "      }\n" +
            "      \n" +
            "   ],\n" +
            "   \"status\" : \"OK\"\n" +
            "}";

    //endregion

    @Override
    protected void setUp() throws Exception{
        super.setUp();
    }

    public void testJsonParser(String JSON_RESPONSE) throws JSONException{
        JSONObject response = new JSONObject(JSON_RESPONSE);
        JSONArray results = response.getJSONArray(RESULTS_ARRAY);

        int placesCount = results.length();

        // Check the count of places
        assertFalse(placesCount != 2);

        List<PlaceDetails> placeDetailsList = new ArrayList<PlaceDetails>();

        JSONObject placeDetailsJsonObject =  results.getJSONObject(0);

    }

    public void testGetPlaceDetails(JSONObject placeDetailsJsonObject){

    }


    public void testGetPlacePhotoMethod(JSONObject photoJsonObjec) throws JSONException {

        Photo extractedPhoto = FetchPlaceDeatilsRequest.getPlacePhoto(photoJsonObjec);
        assertEquals(HEIGHT,extractedPhoto.getHeight());
        assertEquals(WIDTH,extractedPhoto.getWidth());
        assertEquals(PHOTO_REFERENCE,extractedPhoto.getPhotoReference());
        assertEquals(HTML_ATTRIBUTIONS,extractedPhoto.getHtmlAttrs());
    }

    public void testGetTypesMethod(JSONArray types) throws JSONException {
        List<String> typesList = FetchPlaceDeatilsRequest.getTypes(types);
        assertEquals(typesList.get(0),"museum");
        assertEquals(typesList.get(1),"point_of_interest");
        assertEquals(typesList.get(2),"establishment");
    }



}
