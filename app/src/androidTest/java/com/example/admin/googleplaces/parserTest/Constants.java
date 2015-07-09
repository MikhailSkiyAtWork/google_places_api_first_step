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

    //region Values for NearbyPlaceDetails object
    public static final String ID = "ebd527cf2c50d7a2f18898f1551c444db052445b";
    public static final String PLACE_ID = "ChIJoW6k8eBX4UARPp4F58iGoFg";
    public static final String NAME = "Домик Чехова";
    public static final String ICON_LINK = "http://maps.gstatic.com/mapfiles/place_api/icons/museum-71.png";
    public static final double RATING = 4.2;
    public static final String MUSEUM_TYPE = "museum";
    public static final String POINT_OF_INTEREST_TYPE = "point_of_interest";
    public static final String ESTABLISHMENT = "establishment";
    //endregion

    //region Fictional response from server for Search Places request
    public static final String SEARCH_PLACES_RESPONSE = "{\n" +
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

    //endregion fo

    //region Fictional response from server for Place Details request
     public static final String PLACE_DETAILS_RESPONSE = "{\n" +
            "   \"html_attributions\" : [],\n" +
            "   \"result\" : {\n" +
            "      \"address_components\" : [\n" +
            "         {\n" +
            "            \"long_name\" : \"350\",\n" +
            "            \"short_name\" : \"350\",\n" +
            "            \"types\" : [ \"street_number\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"5th Avenue\",\n" +
            "            \"short_name\" : \"5th Ave\",\n" +
            "            \"types\" : [ \"route\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Manhattan\",\n" +
            "            \"short_name\" : \"Manhattan\",\n" +
            "            \"types\" : [ \"sublocality_level_1\", \"sublocality\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"New York\",\n" +
            "            \"short_name\" : \"NY\",\n" +
            "            \"types\" : [ \"locality\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"New York County\",\n" +
            "            \"short_name\" : \"New York County\",\n" +
            "            \"types\" : [ \"administrative_area_level_2\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"New York\",\n" +
            "            \"short_name\" : \"NY\",\n" +
            "            \"types\" : [ \"administrative_area_level_1\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"Соединенные Штаты\",\n" +
            "            \"short_name\" : \"US\",\n" +
            "            \"types\" : [ \"country\", \"political\" ]\n" +
            "         },\n" +
            "         {\n" +
            "            \"long_name\" : \"10118\",\n" +
            "            \"short_name\" : \"10118\",\n" +
            "            \"types\" : [ \"postal_code\" ]\n" +
            "         }\n" +
            "      ],\n" +
            "      \"adr_address\" : \"\\u003cspan class=\\\"street-address\\\"\\u003e350 5th Avenue\\u003c/span\\u003e, \\u003cspan class=\\\"locality\\\"\\u003eNew York\\u003c/span\\u003e, \\u003cspan class=\\\"region\\\"\\u003eNY\\u003c/span\\u003e \\u003cspan class=\\\"postal-code\\\"\\u003e10118\\u003c/span\\u003e, \\u003cspan class=\\\"country-name\\\"\\u003eСоединенные Штаты\\u003c/span\\u003e\",\n" +
            "      \"formatted_address\" : \"350 5th Avenue, New York, NY 10118, Соединенные Штаты\",\n" +
            "      \"formatted_phone_number\" : \"(212) 736-3100\",\n" +
            "      \"geometry\" : {\n" +
            "         \"location\" : {\n" +
            "            \"lat\" : 40.74844,\n" +
            "            \"lng\" : -73.985664\n" +
            "         }\n" +
            "      },\n" +
            "      \"icon\" : \"http://maps.gstatic.com/mapfiles/place_api/icons/generic_business-71.png\",\n" +
            "      \"id\" : \"bc232d2422e7068b2a2ffb314f02e3733dd47796\",\n" +
            "      \"international_phone_number\" : \"+1 212-736-3100\",\n" +
            "      \"name\" : \"Empire State Building\",\n" +
            "      \"opening_hours\" : {\n" +
            "         \"open_now\" : true,\n" +
            "         \"periods\" : [\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"0200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 0,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"0200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 1,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"0200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 2,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"0200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 3,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"0200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 4,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 6,\n" +
            "                  \"time\" : \"0200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 5,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            },\n" +
            "            {\n" +
            "               \"close\" : {\n" +
            "                  \"day\" : 0,\n" +
            "                  \"time\" : \"0200\"\n" +
            "               },\n" +
            "               \"open\" : {\n" +
            "                  \"day\" : 6,\n" +
            "                  \"time\" : \"0800\"\n" +
            "               }\n" +
            "            }\n" +
            "         ],\n" +
            "         \"weekday_text\" : [\n" +
            "            \"Понедельник: 8:00–2:00\",\n" +
            "            \"Вторник: 8:00–2:00\",\n" +
            "            \"Среда: 8:00–2:00\",\n" +
            "            \"Четверг: 8:00–2:00\",\n" +
            "            \"Пятница: 8:00–2:00\",\n" +
            "            \"Суббота: 8:00–2:00\",\n" +
            "            \"Воскресенье: 8:00–2:00\"\n" +
            "         ]\n" +
            "      },\n" +
            "      \"photos\" : [\n" +
            "         {\n" +
            "            \"height\" : 2048,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/108431038197638690229\\\"\\u003eAndreas Weygandt\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAAxDeYHV4yyuEuMV2Pc87N8dq83uazh099sV04J4KCQx9L2j7xUMQTfgdztIE2w1wc7fDawh64a6nwrEl1A4SwTDu9BqoXpuQ7kF71LB7z341GscW2rPYRUltZJjkmbrmJEhBdFtxIh9bgBe0zzdKheQI2GhT6s3Qy36qjCwGPgFaYL6ArxBaQKA\",\n" +
            "            \"width\" : 1536\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2248,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/106166646647214148437\\\"\\u003eovidio47\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAAeDNUUpDIrLPXY2BB1Lca_zhNKhEWwQJIqDdzL7VBqe0OFNovWYJdBHdfJRAMH7jxeVVKrYJS0uddCO9pUYEbG22dQcNmqahSv740WzoFxHolmNJsc-QpLmWKVJKACpZLEhDI7JB3KibWeX9D7Hf6MLyOGhQNjPsAoet3BXH4DS14GvbLu1l0LA\",\n" +
            "            \"width\" : 4000\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 797,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/109538993373077302745\\\"\\u003eMaik Heller\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAASirUeKYAjS7pX5vFrb4qxIj-DrCEnfqsBZAywSV20RSe0gT72QpcMI2Tw2SsWK60rAXd3zv7Ld0KJCjeNwCG-RHk2UOWvpRpr9JuFLQS011fBOf6DxPSe848wPAN5-_cEhBBldNndVJrfoJ-tqPQZiYiGhTat6eE_avu8H0yW_iFxhf5eBrjQQ\",\n" +
            "            \"width\" : 1200\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 3168,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/115017966923955601623\\\"\\u003eAdam Williams\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAAFmQqBLkbaeeeUKvyAYqnxr01VjRUQVJOOPc1GHtV-z9PsjKRd2hT35Tvf-TKw04zhfaYyTafXgpxoP8JHFLcY-t1Q19eQGJYww4hCX3hDYXQuBywaRIyL5Pc8zO8wlHPEhCi23Qrrdz176nN94iMtS52GhQlIIktGKDli2jhGhu5diKZm8B58w\",\n" +
            "            \"width\" : 4752\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2048,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/108837428016438949189\\\"\\u003ePablo Costa Tirado\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAA0m5liqbn68hZBZdbOtTMCPI7Y10iaF5IZ8l-28ropBnCDa7v3DPF2uU7o3NHI0LNu_SwaEMvq7wNUXd99H4Tziyx_LNI6luE3-oBCRTNWN7uBebpIvy-DpjfCQ0Q11pnEhArKfhM4VRCUfJ6Xb3ombEFGhTSV67POPPbTJab1xXXAbFRkS6R3w\",\n" +
            "            \"width\" : 1536\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 1224,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/110101267233557063978\\\"\\u003eJeremey Perkins\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAAwvMObXtJyt9zCvC9vbbhufVJEk5-puJV31bt-cZGxFykapK-zIZaihSxCWs2aT5o1gev78mYRcxEOlTurjEBzbUGmjWmAFHS3e6HI2nMK1whzYG2YT1v3OFMDr5b1S5MEhAaoR6ib5nxO6hB7x3BsfcGGhQKng41wlVHZXJyVypKzQeXCxAsKg\",\n" +
            "            \"width\" : 1632\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 3920,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/106166646647214148437\\\"\\u003eovidio47\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAA6RPFO6DCajFb9da3x1iqT4tJWx66QhgrjWsRgA5E1mlj2iUm1O8UUSnlpu-AjxW59YFOHo10yoOE15cUBJnNyKeOieKLA3iXzc8LuBxHCcJRjLdy896G42HPq5BztDujEhDKswN_1OSUWoxkuj1SZUCjGhSitiqzkOu5e-iShpTzPmOswBzvKw\",\n" +
            "            \"width\" : 2204\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 4128,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/110078255106528529014\\\"\\u003evasilios dinis\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAAws9IiFHIZCl7le1_1Cf07ZxcOB_vFq1_SfHNSRKu0xQ2CQ4dqA1HgN7I2-JKkKIi6liRQwxp9zZJ-TosglCq-_1kJhNFW91HiegooU5Vf_FCUnhO19-xsFutzc9iWsmjEhAbeoX7RehxjAcq0lMsxtkgGhSyyUGBYNx-hJ9TuJEuJoYHxSTKXg\",\n" +
            "            \"width\" : 3096\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 1920,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/102531763221075960917\\\"\\u003eЕкатерина Мартынова\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAAnNn9ee3YKB6SGSraqreC38jwAtUr7zk0Nez6-Tyoz4qdt79O00sAhb3mPeKsMuyRuHyfVjTYIQZpappJrymyO0vwLweY87AnBewuBzJVW5YsYOLWwdOltzFuvE5lA7enEhBD3bYCtakOk8oI9VlA_ce_GhSk9plyj55W0O9qwib787y9zvqrvw\",\n" +
            "            \"width\" : 1280\n" +
            "         },\n" +
            "         {\n" +
            "            \"height\" : 2448,\n" +
            "            \"html_attributions\" : [\n" +
            "               \"\\u003ca href=\\\"https://www.google.com/maps/views/profile/102660355937433329951\\\"\\u003eKarl Daniels\\u003c/a\\u003e\"\n" +
            "            ],\n" +
            "            \"photo_reference\" : \"CmRdAAAAdZSkOGZ_Qp3-XKPS0v1G6C1FeLjE8eOu16dKws0gFApt_XZYk44klQC3jYHfNU8P5mJxxjqiJM_m9b1Tgeg5yKGKA7xaBomvGNOxfmEXOBiSvITF-jNbt9hZcWRC5U0EEhD1t_GEDpeaO6EwLtaKHdmEGhSz9qZRoiv3MJQr3j3RF8ztCXW3Ow\",\n" +
            "            \"width\" : 3264\n" +
            "         }\n" +
            "      ],\n" +
            "      \"place_id\" : \"ChIJaXQRs6lZwokRY6EFpJnhNNE\",\n" +
            "      \"rating\" : 4.6,\n" +
            "      \"reference\" : \"CnRpAAAAHAqdKFXVppKurHUu0k5s5ahHIHdfP3wB1RJx0BYr_ljjHyj-MtvnFl7MYEaHgYR5861-I0PKMCPmQB1fIVlqWnhh8pZu-xysK42ZygMI06CSJeoyJwnSCNB-ytAp2Rmcf_Je-NNas4yUTKIrzEzXyRIQ2gQH3Wm6YqwjU1OXz3cGbxoUCH_EmzHlGSyEabVPT_btO1NhByE\",\n" +
            "      \"reviews\" : [\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Mikhail Lebedev\",\n" +
            "            \"author_url\" : \"https://plus.google.com/110065847026276052329\",\n" +
            "            \"language\" : \"ru\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"К сожалению, могу оценить только внешний вид. Поразило это здание меня своими размерами. В ночной подсветке тоже очень красивый вид. Жаль я не успел посетить его во время путешествия своего.\",\n" +
            "            \"time\" : 1425058738\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Александр Скрынник\",\n" +
            "            \"author_url\" : \"https://plus.google.com/103402024293102034921\",\n" +
            "            \"language\" : \"ru\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Билет на 86 этаж стоит 29 долларов. VIP проход (везде без очередей) стоит 50 долларов\\nМожно доплатить 20 долларов и попасть еще и на 102 этаж.\\nВид с 86-го - впечатляет. Хоть и смотришь через решетку.\\nКрасота необыкновенная\\nА вот 102-й этаж разочаровал. Смотришь на город через стекло. к тому же и не очень чистое. Как в аквариуме. Мое мнение: нет смысла переплачивать 20 долларов за 102-й этаж.\\nНо посетить 86-й этаж однозначно стОит.\",\n" +
            "            \"time\" : 1409557137\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Егор Трущелев\",\n" +
            "            \"author_url\" : \"https://plus.google.com/115182736613415053500\",\n" +
            "            \"language\" : \"ru\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Очень знаменитое здание мира! Жалко, что Центр Всемирной Торговли взорвали террористы((( НЕНАВИЖУ ИХ!!!!!\",\n" +
            "            \"time\" : 1427296198\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Rasulov Shertoji\",\n" +
            "            \"author_url\" : \"https://plus.google.com/111885243809743469153\",\n" +
            "            \"language\" : \"ru\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Я был в 102 этаже. Отличный обзор. Я восторге особенно когда смотриш вниз эти желтые такси как муравьи передвигаются.\\n\\n\",\n" +
            "            \"time\" : 1419138541\n" +
            "         },\n" +
            "         {\n" +
            "            \"aspects\" : [\n" +
            "               {\n" +
            "                  \"rating\" : 3,\n" +
            "                  \"type\" : \"overall\"\n" +
            "               }\n" +
            "            ],\n" +
            "            \"author_name\" : \"Fyodor Ivlev\",\n" +
            "            \"author_url\" : \"https://plus.google.com/112914175640785456444\",\n" +
            "            \"language\" : \"ru\",\n" +
            "            \"rating\" : 5,\n" +
            "            \"text\" : \"Очень крутой ночной вид на один из самых крупных в некотором смысле городов мира\",\n" +
            "            \"time\" : 1426345954\n" +
            "         }\n" +
            "      ],\n" +
            "      \"scope\" : \"GOOGLE\",\n" +
            "      \"types\" : [ \"point_of_interest\", \"establishment\" ],\n" +
            "      \"url\" : \"https://plus.google.com/110101791098901696787/about?hl=ru-RU\",\n" +
            "      \"user_ratings_total\" : 2032,\n" +
            "      \"utc_offset\" : -240,\n" +
            "      \"vicinity\" : \"350 5th Avenue, New York\",\n" +
            "      \"website\" : \"http://www.esbnyc.com/\"\n" +
            "   },\n" +
            "   \"status\" : \"OK\"\n" +
            "}";
    //endregion


}
