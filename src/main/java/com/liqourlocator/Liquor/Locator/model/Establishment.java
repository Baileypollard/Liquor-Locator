package com.liqourlocator.Liquor.Locator.model;

import com.couchbase.client.java.repository.annotation.Field;
import com.vaadin.tapio.googlemaps.client.LatLon;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class Establishment
{
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


    public Establishment(String cityTown, String establishment, String licenseNumber, String licenseType, String postalCode, String streetAddress, String province)
    {
        this.cityTown = cityTown;
        this.establishment = establishment;
        this.licenseNumber = licenseNumber;
        this.licenseType = licenseType;
        this.postalCode = postalCode;
        this.streetAddress = streetAddress;
        this.province = province;
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

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
    }

    public String getCityTown()
    {
        return cityTown;
    }

    public String getProvince()
    {
        return province;
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
        return establishment;
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
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress)
    {
        this.streetAddress = streetAddress;
    }
}
