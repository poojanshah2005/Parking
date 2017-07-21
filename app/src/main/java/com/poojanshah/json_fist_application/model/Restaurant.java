package com.poojanshah.json_fist_application.model;

import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Restaurant implements Parcelable
{

    @SerializedName("Badges")
    @Expose
    private List<Object> badges = null;
    @SerializedName("Score")
    @Expose
    private Double score;
    @SerializedName("DriveDistance")
    @Expose
    private Double driveDistance;
    @SerializedName("DriveInfoCalculated")
    @Expose
    private Boolean driveInfoCalculated;
    @SerializedName("NewnessDate")
    @Expose
    private String newnessDate;
    @SerializedName("DeliveryMenuId")
    @Expose
    private Double deliveryMenuId;
    @SerializedName("DeliveryOpeningTime")
    @Expose
    private String deliveryOpeningTime;
    @SerializedName("DeliveryCost")
    @Expose
    private Double deliveryCost;
    @SerializedName("MinimumDeliveryValue")
    @Expose
    private Double minimumDeliveryValue;
    @SerializedName("DeliveryTimeMinutes")
    @Expose
    private Object deliveryTimeMinutes;
    @SerializedName("DeliveryWorkingTimeMinutes")
    @Expose
    private Double deliveryWorkingTimeMinutes;
    @SerializedName("OpeningTime")
    @Expose
    private String openingTime;
    @SerializedName("OpeningTimeIso")
    @Expose
    private String openingTimeIso;
    @SerializedName("SendsOnItsWayNotifications")
    @Expose
    private Boolean sendsOnItsWayNotifications;
    @SerializedName("RatingAverage")
    @Expose
    private Double ratingAverage;
    @SerializedName("Latitude")
    @Expose
    private Double latitude;
    @SerializedName("Longitude")
    @Expose
    private Double longitude;
    @SerializedName("Tags")
    @Expose
    private List<Object> tags = null;
    @SerializedName("Id")
    @Expose
    private Double id;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Address")
    @Expose
    private String address;
    @SerializedName("Postcode")
    @Expose
    private String postcode;
    @SerializedName("City")
    @Expose
    private String city;
    @SerializedName("CuisineTypes")
    @Expose
    private List<CuisineType> cuisineTypes = null;
    @SerializedName("Url")
    @Expose
    private String url;
    @SerializedName("IsOpenNow")
    @Expose
    private Boolean isOpenNow;
    @SerializedName("IsPremier")
    @Expose
    private Boolean isPremier;
    @SerializedName("IsSponsored")
    @Expose
    private Boolean isSponsored;
    @SerializedName("SponsoredPosition")
    @Expose
    private Double sponsoredPosition;
    @SerializedName("IsNew")
    @Expose
    private Boolean isNew;
    @SerializedName("IsTemporarilyOffline")
    @Expose
    private Boolean isTemporarilyOffline;
    @SerializedName("ReasonWhyTemporarilyOffline")
    @Expose
    private String reasonWhyTemporarilyOffline;
    @SerializedName("UniqueName")
    @Expose
    private String uniqueName;
    @SerializedName("IsCloseBy")
    @Expose
    private Boolean isCloseBy;
    @SerializedName("IsHalal")
    @Expose
    private Boolean isHalal;
    @SerializedName("IsTestRestaurant")
    @Expose
    private Boolean isTestRestaurant;
    @SerializedName("DefaultDisplayRank")
    @Expose
    private Double defaultDisplayRank;
    @SerializedName("IsOpenNowForDelivery")
    @Expose
    private Boolean isOpenNowForDelivery;
    @SerializedName("IsOpenNowForCollection")
    @Expose
    private Boolean isOpenNowForCollection;
    @SerializedName("RatingStars")
    @Expose
    private Double ratingStars;
    @SerializedName("Logo")
    @Expose
    private List<Logo> logo = null;
    @SerializedName("Deals")
    @Expose
    private List<Object> deals = null;
    @SerializedName("NumberOfRatings")
    @Expose
    private Double numberOfRatings;
    public final static Parcelable.Creator<Restaurant> CREATOR = new Creator<Restaurant>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Restaurant createFromParcel(Parcel in) {
            Restaurant instance = new Restaurant();
            in.readList(instance.badges, (java.lang.Object.class.getClassLoader()));
            instance.score = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.driveDistance = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.driveInfoCalculated = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.newnessDate = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryMenuId = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.deliveryOpeningTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.deliveryCost = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.minimumDeliveryValue = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.deliveryTimeMinutes = ((Object) in.readValue((Object.class.getClassLoader())));
            instance.deliveryWorkingTimeMinutes = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.openingTime = ((String) in.readValue((String.class.getClassLoader())));
            instance.openingTimeIso = ((String) in.readValue((String.class.getClassLoader())));
            instance.sendsOnItsWayNotifications = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.ratingAverage = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.latitude = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.longitude = ((Double) in.readValue((Double.class.getClassLoader())));
            in.readList(instance.tags, (java.lang.Object.class.getClassLoader()));
            instance.id = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.address = ((String) in.readValue((String.class.getClassLoader())));
            instance.postcode = ((String) in.readValue((String.class.getClassLoader())));
            instance.city = ((String) in.readValue((String.class.getClassLoader())));
            in.readList(instance.cuisineTypes, (com.poojanshah.json_fist_application.model.CuisineType.class.getClassLoader()));
            instance.url = ((String) in.readValue((String.class.getClassLoader())));
            instance.isOpenNow = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isPremier = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isSponsored = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.sponsoredPosition = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.isNew = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isTemporarilyOffline = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.reasonWhyTemporarilyOffline = ((String) in.readValue((String.class.getClassLoader())));
            instance.uniqueName = ((String) in.readValue((String.class.getClassLoader())));
            instance.isCloseBy = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isHalal = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isTestRestaurant = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.defaultDisplayRank = ((Double) in.readValue((Double.class.getClassLoader())));
            instance.isOpenNowForDelivery = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.isOpenNowForCollection = ((Boolean) in.readValue((Boolean.class.getClassLoader())));
            instance.ratingStars = ((Double) in.readValue((Double.class.getClassLoader())));
            in.readList(instance.logo, (com.poojanshah.json_fist_application.model.Logo.class.getClassLoader()));
            in.readList(instance.deals, (java.lang.Object.class.getClassLoader()));
            instance.numberOfRatings = ((Double) in.readValue((Double.class.getClassLoader())));
            return instance;
        }

        public Restaurant[] newArray(int size) {
            return (new Restaurant[size]);
        }

    }
            ;

    public List<Object> getBadges() {
        return badges;
    }

    public void setBadges(List<Object> badges) {
        this.badges = badges;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Double getDriveDistance() {
        return driveDistance;
    }

    public void setDriveDistance(Double driveDistance) {
        this.driveDistance = driveDistance;
    }

    public Boolean getDriveInfoCalculated() {
        return driveInfoCalculated;
    }

    public void setDriveInfoCalculated(Boolean driveInfoCalculated) {
        this.driveInfoCalculated = driveInfoCalculated;
    }

    public String getNewnessDate() {
        return newnessDate;
    }

    public void setNewnessDate(String newnessDate) {
        this.newnessDate = newnessDate;
    }

    public Double getDeliveryMenuId() {
        return deliveryMenuId;
    }

    public void setDeliveryMenuId(Double deliveryMenuId) {
        this.deliveryMenuId = deliveryMenuId;
    }

    public String getDeliveryOpeningTime() {
        return deliveryOpeningTime;
    }

    public void setDeliveryOpeningTime(String deliveryOpeningTime) {
        this.deliveryOpeningTime = deliveryOpeningTime;
    }

    public Double getDeliveryCost() {
        return deliveryCost;
    }

    public void setDeliveryCost(Double deliveryCost) {
        this.deliveryCost = deliveryCost;
    }

    public Double getMinimumDeliveryValue() {
        return minimumDeliveryValue;
    }

    public void setMinimumDeliveryValue(Double minimumDeliveryValue) {
        this.minimumDeliveryValue = minimumDeliveryValue;
    }

    public Object getDeliveryTimeMinutes() {
        return deliveryTimeMinutes;
    }

    public void setDeliveryTimeMinutes(Object deliveryTimeMinutes) {
        this.deliveryTimeMinutes = deliveryTimeMinutes;
    }

    public Double getDeliveryWorkingTimeMinutes() {
        return deliveryWorkingTimeMinutes;
    }

    public void setDeliveryWorkingTimeMinutes(Double deliveryWorkingTimeMinutes) {
        this.deliveryWorkingTimeMinutes = deliveryWorkingTimeMinutes;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getOpeningTimeIso() {
        return openingTimeIso;
    }

    public void setOpeningTimeIso(String openingTimeIso) {
        this.openingTimeIso = openingTimeIso;
    }

    public Boolean getSendsOnItsWayNotifications() {
        return sendsOnItsWayNotifications;
    }

    public void setSendsOnItsWayNotifications(Boolean sendsOnItsWayNotifications) {
        this.sendsOnItsWayNotifications = sendsOnItsWayNotifications;
    }

    public Double getRatingAverage() {
        return ratingAverage;
    }

    public void setRatingAverage(Double ratingAverage) {
        this.ratingAverage = ratingAverage;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<Object> getTags() {
        return tags;
    }

    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    public Double getId() {
        return id;
    }

    public void setId(Double id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<CuisineType> getCuisineTypes() {
        return cuisineTypes;
    }

    public void setCuisineTypes(List<CuisineType> cuisineTypes) {
        this.cuisineTypes = cuisineTypes;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getIsOpenNow() {
        return isOpenNow;
    }

    public void setIsOpenNow(Boolean isOpenNow) {
        this.isOpenNow = isOpenNow;
    }

    public Boolean getIsPremier() {
        return isPremier;
    }

    public void setIsPremier(Boolean isPremier) {
        this.isPremier = isPremier;
    }

    public Boolean getIsSponsored() {
        return isSponsored;
    }

    public void setIsSponsored(Boolean isSponsored) {
        this.isSponsored = isSponsored;
    }

    public Double getSponsoredPosition() {
        return sponsoredPosition;
    }

    public void setSponsoredPosition(Double sponsoredPosition) {
        this.sponsoredPosition = sponsoredPosition;
    }

    public Boolean getIsNew() {
        return isNew;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public Boolean getIsTemporarilyOffline() {
        return isTemporarilyOffline;
    }

    public void setIsTemporarilyOffline(Boolean isTemporarilyOffline) {
        this.isTemporarilyOffline = isTemporarilyOffline;
    }

    public String getReasonWhyTemporarilyOffline() {
        return reasonWhyTemporarilyOffline;
    }

    public void setReasonWhyTemporarilyOffline(String reasonWhyTemporarilyOffline) {
        this.reasonWhyTemporarilyOffline = reasonWhyTemporarilyOffline;
    }

    public String getUniqueName() {
        return uniqueName;
    }

    public void setUniqueName(String uniqueName) {
        this.uniqueName = uniqueName;
    }

    public Boolean getIsCloseBy() {
        return isCloseBy;
    }

    public void setIsCloseBy(Boolean isCloseBy) {
        this.isCloseBy = isCloseBy;
    }

    public Boolean getIsHalal() {
        return isHalal;
    }

    public void setIsHalal(Boolean isHalal) {
        this.isHalal = isHalal;
    }

    public Boolean getIsTestRestaurant() {
        return isTestRestaurant;
    }

    public void setIsTestRestaurant(Boolean isTestRestaurant) {
        this.isTestRestaurant = isTestRestaurant;
    }

    public Double getDefaultDisplayRank() {
        return defaultDisplayRank;
    }

    public void setDefaultDisplayRank(Double defaultDisplayRank) {
        this.defaultDisplayRank = defaultDisplayRank;
    }

    public Boolean getIsOpenNowForDelivery() {
        return isOpenNowForDelivery;
    }

    public void setIsOpenNowForDelivery(Boolean isOpenNowForDelivery) {
        this.isOpenNowForDelivery = isOpenNowForDelivery;
    }

    public Boolean getIsOpenNowForCollection() {
        return isOpenNowForCollection;
    }

    public void setIsOpenNowForCollection(Boolean isOpenNowForCollection) {
        this.isOpenNowForCollection = isOpenNowForCollection;
    }

    public Double getRatingStars() {
        return ratingStars;
    }

    public void setRatingStars(Double ratingStars) {
        this.ratingStars = ratingStars;
    }

    public List<Logo> getLogo() {
        return logo;
    }

    public void setLogo(List<Logo> logo) {
        this.logo = logo;
    }

    public List<Object> getDeals() {
        return deals;
    }

    public void setDeals(List<Object> deals) {
        this.deals = deals;
    }

    public Double getNumberOfRatings() {
        return numberOfRatings;
    }

    public void setNumberOfRatings(Double numberOfRatings) {
        this.numberOfRatings = numberOfRatings;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(badges);
        dest.writeValue(score);
        dest.writeValue(driveDistance);
        dest.writeValue(driveInfoCalculated);
        dest.writeValue(newnessDate);
        dest.writeValue(deliveryMenuId);
        dest.writeValue(deliveryOpeningTime);
        dest.writeValue(deliveryCost);
        dest.writeValue(minimumDeliveryValue);
        dest.writeValue(deliveryTimeMinutes);
        dest.writeValue(deliveryWorkingTimeMinutes);
        dest.writeValue(openingTime);
        dest.writeValue(openingTimeIso);
        dest.writeValue(sendsOnItsWayNotifications);
        dest.writeValue(ratingAverage);
        dest.writeValue(latitude);
        dest.writeValue(longitude);
        dest.writeList(tags);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(address);
        dest.writeValue(postcode);
        dest.writeValue(city);
        dest.writeList(cuisineTypes);
        dest.writeValue(url);
        dest.writeValue(isOpenNow);
        dest.writeValue(isPremier);
        dest.writeValue(isSponsored);
        dest.writeValue(sponsoredPosition);
        dest.writeValue(isNew);
        dest.writeValue(isTemporarilyOffline);
        dest.writeValue(reasonWhyTemporarilyOffline);
        dest.writeValue(uniqueName);
        dest.writeValue(isCloseBy);
        dest.writeValue(isHalal);
        dest.writeValue(isTestRestaurant);
        dest.writeValue(defaultDisplayRank);
        dest.writeValue(isOpenNowForDelivery);
        dest.writeValue(isOpenNowForCollection);
        dest.writeValue(ratingStars);
        dest.writeList(logo);
        dest.writeList(deals);
        dest.writeValue(numberOfRatings);
    }

    public int describeContents() {
        return 0;
    }

}