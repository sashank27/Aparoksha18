package org.aparoksha.app18.utils;

/**
 * Created by akshat on 10/3/18.
 */

import java.util.List;
import com.squareup.moshi.Json;

public class EventPojo {

    @Json(name = "id")
    private Long id;
    @Json(name = "name")
    private String name;
    @Json(name = "description")
    private String description;
    @Json(name = "location")
    private String location;
    @Json(name = "timestamp")
    private Long timestamp;
    @Json(name = "imageUrl")
    private String imageUrl;
    @Json(name = "facebookEventLink")
    private String facebookEventLink;
    @Json(name = "additionalInfo")
    private List<Object> additionalInfo = null;
    @Json(name = "categories")
    private List<String> categories = null;
    @Json(name = "organizers")
    private List<Organizer> organizers = null;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getFacebookEventLink() {
        return facebookEventLink;
    }

    public void setFacebookEventLink(String facebookEventLink) {
        this.facebookEventLink = facebookEventLink;
    }

    public List<Object> getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(List<Object> additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public List<Organizer> getOrganizers() {
        return organizers;
    }

    public void setOrganizers(List<Organizer> organizers) {
        this.organizers = organizers;
    }

}

class Organizer {

    @Json(name = "name")
    private String name;
    @Json(name = "phoneNumber")
    private Long phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}