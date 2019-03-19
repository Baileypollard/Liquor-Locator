package com.liqourlocator.Liquor.Locator.model;

import com.couchbase.client.java.repository.annotation.Field;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class Location
{
    @Field(value = "human_address")
    private String humanAddress;

    @Field(value = "latitude")
    private String latitude;

    @Field(value = "longitude")
    private String longitude;

    public Location(String humanAddress, String latitude, String longitude)
    {
        this.humanAddress = humanAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getHumanAddress()
    {
        return humanAddress;
    }

    public void setHumanAddress(String humanAddress)
    {
        this.humanAddress = humanAddress;
    }

    public String getLatitude()
    {
        return latitude;
    }

    public void setLatitude(String latitude)
    {
        this.latitude = latitude;
    }

    public String getLongitude()
    {
        return longitude;
    }

    public void setLongitude(String longitude)
    {
        this.longitude = longitude;
    }
}
