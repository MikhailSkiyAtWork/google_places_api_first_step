package com.example.admin.googleplaces.parserTest;

import com.example.admin.googleplaces.data.Photo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 08.07.2015.
 */
public class Constants {

    //region Names of the JSON objects that need to be extracted
    final static String STATUS_KEY = "status";

    final static String RESULTS_ARRAY_KEY = "results";
    final static String ICON_KEY = "icon";
    final static String ID_KEY = "id";
    final static String NAME_KEY = "name";

    // Items for extracting photo attrs
    final static String PHOTOS_ARRAY_KEY = "photos";
    final static String HEIGHT_KEY = "height";
    final static String WIDTH_KEY = "width";
    final static String HTML_ATTRIBUTIONS_KEY = "html_attributions";
    final static String PHOTO_REFERENCE_KEY = "photo_reference";

    final static String TYPES_KEY = "types";
    final static String PLACE_ID_KEY = "place_id";
    final static String RATING_KEY = "rating";
    //endregion

    //region Values for Photo object
    public static final int HEIGHT = 3456;
    public static final int WIDTH = 5184;
    public static final String PHOTO_REFS = "CmRdAAAA1Soy7QuTPuDmSP3EoyuQYmv8U7h458xQ64YqYZE0KLxl-jCsPhRRNdULzyseg4gveQTuAT2R5AYw0HSObw19XUVKpNCTVAwRXiM8ZZC5g-gN3ZiOb6grjwOKTyIThgNPEhDZGV_BmlyKQuRYtr4E6ZSeGhSpiwGHt6gelU9UlolM9DdXJm8fgw";
    public static List<String> HTML_ATTRS = Arrays.asList("<a href=\"https://www.google.com/maps/views/profile/106283553457831154892\">Михаил Бадретдинов</a>");
    //endregion

    //region Values for PlaceDetails object
    public static final String ID = "ebd527cf2c50d7a2f18898f1551c444db052445b";
    public static final String PLACE_ID = "ChIJoW6k8eBX4UARPp4F58iGoFg";
    public static final String NAME = "Домик Чехова";
    public static final String ICON_LINK = "http://maps.gstatic.com/mapfiles/place_api/icons/museum-71.png";
    public static final double RATING = 4.2;
    public static final String MUSEUM_TYPE = "museum";
    public static final String POINT_OF_INTEREST_TYPE = "point_of_interest";
    public static final String ESTABLISHMENT = "establishment";
    //endregion

    //region Fictional response from server
    public static final String JSON_RESPONSE = "{\n" +
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


}
