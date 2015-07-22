package com.example.admin.googleplaces.requests;

import com.example.admin.googleplaces.models.RequestParams;

/**
 * Created by Mikhail Valuyskiy on 22.07.2015.
 */

/**
 * Abstract class which represents general kind of request
 */
public abstract class GeneralRequest {

    /**
     * Creates special URL for request
     */
    public abstract String getUrl();

    /**
     * Returns tag of request
     */
    public abstract String getTag();

    /**
     * Returns status for MessageHandler
     */
    public abstract int getStatus();

}
