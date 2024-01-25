package sg.nus.iss.edu.Day27workshop.Model;

import org.bson.Document;
import org.joda.time.DateTime;


import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
//how you determine this ? looka the requirements*
public class Review {
    
    private String user;
    private Integer rating;
    private String comment;
    private Integer gid;
    private DateTime posted;

    

    public Review() {
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getGid() {
        return gid;
    }
    public void setGid(Integer gid) {
        this.gid = gid;
    }
    public DateTime getPosted() {
        return posted;
    }
    public void setPosted(DateTime posted) {
        this.posted = posted;
    }

    @Override
    public String toString() {
        return "Review [user=" + user + ", rating=" + rating + ", comment=" + comment + ", gid=" + gid + ", posted="
                + posted + "]";
    }

    public JsonObject toJson(){
        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                    .add("gid", getGid())
                    .add("user", getUser())
                    .add("comment", getComment())
                    .add("posted", getPosted().toString())
                    .add("rating", getRating()); //!= null ? getUser_rated() : 0) // if null set 0
                    
                   
        
            return objBuilder.build();
    }

    public static Review fromJSON(Document jsonObject) {
        Review r = new Review(); // Create a new review object
        
        // Set the fields from the JsonObject
        r.setGid(jsonObject.getInteger("gid"));
        r.setUser(jsonObject.getString("user"));
        r.setComment((jsonObject.getString("comment")));
        r.setPosted(DateTime.parse(jsonObject.getString("posted"))); // this is for Date time**
        r.setRating(jsonObject.getInteger("rating"));

        
        return r;
    }
}

