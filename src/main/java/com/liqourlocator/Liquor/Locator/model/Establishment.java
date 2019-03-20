package com.liqourlocator.Liquor.Locator.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.vaadin.tapio.googlemaps.client.LatLon;
import org.springframework.data.couchbase.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document
public class Establishment
{
    @Field(value = "id")
    private String id;

    @Field(value = "city_town")
    private String cityTown;

    @Field(value = "establishment")
    private String establishment;

    @Field(value = "license_number")
    private String licenseNumber;

    @Field(value = "license_type")
    private String licenseType;

    @Field(value = "postal_code")
    private String postalCode;

    @Field(value = "street_address")
    private String streetAddress;

    @Field(value = "province")
    private String province;

    @Field(value = "location")
    private Location location;

    @Field(value = "rating")
    private String rating;

    @Field(value = "phone_number")
    private String phoneNumber;

    @Field(value = "reviews")
    private List<Review> reviews;


    public Establishment(String cityTown, String establishment, String licenseNumber, String licenseType, String postalCode, String streetAddress, String province)
    {
        this.cityTown = cityTown;
        this.establishment = establishment;
        this.licenseNumber = licenseNumber;
        this.licenseType = licenseType;
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
        this.province = province;
        this.reviews = new ArrayList<>();
    }

    public List<Review> getReviews()
    {
        return reviews;
    }

    public void setReviews(List<Review> reviews)
    {
        this.reviews = reviews;
    }

    public LatLon getLatLong()
    {
        if (location.getLatitude() == null || location.getLongitude() == null)
        {
            return null;
        }
        double lat = Double.parseDouble(location.getLatitude());
        double lng = Double.parseDouble(location.getLongitude());

        return new LatLon(lat, lng);
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Location getLocation()
    {
        return location;
    }

    public String getRating()
    {
        return rating != null ? rating : "Not Found";
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getPhoneNumber()
    {
        return phoneNumber != null ? phoneNumber : "Not Found";
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String getCityTown()
    {
        return cityTown != null ? cityTown : "Not Found";
    }

    public String getProvince()
    {
        return province != null ? province : "Not Found";
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public void setCityTown(String cityTown)
    {
        this.cityTown = cityTown;
    }

    public String getEstablishment()
    {
        return establishment != null ? establishment : "Not Found";
    }

    public void setEstablishment(String establishment)
    {
        this.establishment = establishment;
    }

    public String getLicenseNumber()
    {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber)
    {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseType()
    {
        return licenseType;
    }

    public void setLicenseType(String licenseType)
    {
        this.licenseType = licenseType;
    }

    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(String postalCode)
    {
        this.postalCode = postalCode;
    }

    public String getStreetAddress()
    {
        return streetAddress != null ? streetAddress : "Not Found";
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = streetAddress;
    }
}
