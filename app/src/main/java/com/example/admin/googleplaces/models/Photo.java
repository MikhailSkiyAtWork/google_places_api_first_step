package com.example.admin.googleplaces.models;

/**
 * Created by Mikhail Valuyskiy on 06.07.2015.
 */

import java.util.List;

/**
 * Represents the photo
 */
public class Photo {
    private int height_;
    private int width_;
    private String photoReference_;
    private List<String> htmlAttrs_;

    //region Constructors
    public Photo(int height, int width, String photoReference, List<String> attrs) {
        this.height_ = height;
        this.width_ = width;
        this.photoReference_ = photoReference;
        this.htmlAttrs_ = attrs;
    }
    //endregion

    //region Accessors
    public void setHeight(int height) {
        this.height_ = height;
    }

    public int getHeight() {
        return this.height_;
    }

    public void setWidth(int width) {
        this.width_ = width;
    }

    public int getWidth() {
        return this.width_;
    }

    public void setPhotoReference(String photoReference) {
        this.photoReference_ = photoReference;
    }

    public String getPhotoReference() {
        return this.photoReference_;
    }

    public void setHtmlAttrs(List<String> attrs) {
        for (int i = 0; i < attrs.size(); i++) {
            this.htmlAttrs_.add(attrs.get(i));
        }
    }

    public List<String> getHtmlAttrs() {
        return this.htmlAttrs_;
    }
    //endregion

}
