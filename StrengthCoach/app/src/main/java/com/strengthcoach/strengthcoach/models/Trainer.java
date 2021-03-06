package com.strengthcoach.strengthcoach.models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import java.io.Serializable;
import java.util.ArrayList;

@ParseClassName("Trainer")
public class Trainer extends ParseObject implements Serializable {
    /**
     * COLUMN NAMES: Multi-word names should be separated by '_' (underscore)
     * int id
     * String name
     * ArrayList<User> clients
     * String about_me
     * String phone_number
     * int rating
     * String profile_image         // Url of the profile image
     * ArrayList<String> images     // Collection of image url's
     * ArrayList<Review> reviews
     */

    public static String currentTrainerObjectId;
    public static String currentTrainerName;
    public static int currentPrice;

    // Accessors
    public int getId() {
        return getInt("id");
    }

    public String getName() {
        return getString("name");
    }

    public ArrayList<SimpleUser> getClients() {
        return (ArrayList<SimpleUser>) get("clients");
    }

    public String getAboutMe() {
        return getString("about_me");
    }

    public int getPrice() {
        return getInt("price");
    }

    // eg. $49
    public String getPriceFormatted() {
        return "$" +  getPrice();
    }

    public String getPhoneNumber() {
        return getString("phone_number");
    }

    public double getRatings() {
        return getDouble("rating");
    }

    public String getProfileImageUrl() {
        return getString("profile_image");
    }

    public ArrayList<String> getImages() {
        return (ArrayList<String>) get("images");
    }

    public ArrayList<String> getEducationAndCertifications() { return (ArrayList<String>) get("educationAndCertifications"); }

    public ArrayList<String> getInterestsAndAchievements() { return (ArrayList<String>) get("interestsAndAchievements"); }

    public Gym getGym() { return (Gym) get("gym"); }

    public ArrayList<SimpleUser> getFavoritedBy() {
        return (ArrayList<SimpleUser>) get("favorited_by");
    }

    // Modifiers
    public void setId(int id) {
        put("id", id);
    }

    public void setName(String name) {
        put("name", name);
    }

    public void setClients(ArrayList<SimpleUser> clients) {
        put("clients", clients);
    }

    public void setAboutMe(String aboutMe) {
        put("about_me", aboutMe);
    }

    public void setPrice(int price) {
        put("price", price);
    }

    public void setPhoneNumber(String phoneNumber) {
        put("phone_number", phoneNumber);
    }

    public void setRating(double rating) {
        put("rating", rating);
    }

    public void setProfileImageUrl(String url) {
        put("profile_image", url);
    }

    public void setImages(ArrayList<String> imageUrls) {
        put("images", imageUrls);
    }

    public void setEducationAndCertifications(ArrayList<String> educationAndCertifications) { put("educationAndCertifications", educationAndCertifications); }

    public void setInterestsAndAchievements(ArrayList<String> interestsAndAchievements) { put("interestsAndAchievements", interestsAndAchievements); }

    public void setGym(Gym gym) { put("gym", gym); }

    public void setFavoritedBy(ArrayList<SimpleUser> favoritedBy) {
        put("favorited_by", favoritedBy);
    }
    public boolean isFavorite() {
        ArrayList<SimpleUser> favoritedBy = getFavoritedBy();
        if (favoritedBy != null) {
            return favoritedBy.contains(SimpleUser.currentUserObject);
        }
        return false;
    }
}
