package com.liqourlocator.Liquor.Locator.model;

import com.couchbase.client.java.repository.annotation.Field;
import org.springframework.data.couchbase.core.mapping.Document;

@Document
public class EstablishmentType
{
    @Field(value = "license_type")
    private String type;

    public EstablishmentType(String type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return type;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
