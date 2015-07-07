package com.example.admin.googleplaces.parserTest;

import android.annotation.SuppressLint;
import android.test.AndroidTestCase;

import com.example.admin.googleplaces.data.Photo;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Mikhail Valuyskiy on 07.07.2015.
 */
public class ClassesTests extends AndroidTestCase {

    // Dummy data for test
    private static final int HEIGHT = 3456;
    private static final int WIDTH = 5184;
    private String PHOTO_REFS = "CmRdAAAA1Soy7QuTPuDmSP3EoyuQYmv8U7h458xQ64YqYZE0KLxl-jCsPhRRNdULzyseg4gveQTuAT2R5AYw0HSObw19XUVKpNCTVAwRXiM8ZZC5g-gN3ZiOb6grjwOKTyIThgNPEhDZGV_BmlyKQuRYtr4E6ZSeGhSpiwGHt6gelU9UlolM9DdXJm8fgw";
    private List<String> HTML_ATTRS = Arrays.asList("<a href=\"https://www.google.com/maps/views/profile/106283553457831154892\">Михаил Бадретдинов</a>");

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

   public void testCreatePhoto(){
       Photo testPhoto = new Photo(HEIGHT,WIDTH,PHOTO_REFS,HTML_ATTRS);
       assertEquals(HEIGHT,testPhoto.getHeight());
       assertEquals(WIDTH,testPhoto.getWidth());
       assertEquals(PHOTO_REFS,testPhoto.getPhotoReference());
       assertEquals(HTML_ATTRS.get(0),testPhoto.getHtmlAttrs().get(0));
   }
}
