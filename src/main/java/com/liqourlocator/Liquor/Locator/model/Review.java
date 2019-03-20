package com.liqourlocator.Liquor.Locator.model;

public class Review
{
    private String comment;
    private String rating;
    private String timeCreated;
    private String url;
    private String author;

    public Review(String comment, String rating, String timeCreated, String url, String author)
    {
        this.comment = comment;
        this.rating = rating;
        this.timeCreated = timeCreated;
        this.url = url;
        this.author = author;
    }

    public String getComment()
    {
        return comment;
    }

    public void setComment(String comment)
    {
        this.comment = comment;
    }

    public String getRating()
    {
        return rating;
    }

    public void setRating(String rating)
    {
        this.rating = rating;
    }

    public String getTimeCreated()
    {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated)
    {
        this.timeCreated = timeCreated;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }
}
