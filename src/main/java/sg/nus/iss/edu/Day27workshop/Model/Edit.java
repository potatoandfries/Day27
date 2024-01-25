package sg.nus.iss.edu.Day27workshop.Model;

import org.bson.Document;
import org.joda.time.DateTime;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

public class Edit {

    private String comment;
    private Integer rating;
    private DateTime posted;

    

    public Edit() {
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    public DateTime getPosted() {
        return posted;
    }
    public void setPosted(DateTime posted) {
        this.posted = posted;
    }
    
    public JsonObject toJson() {
        JsonObjectBuilder objBuilder = Json.createObjectBuilder()
                .add("comment", getComment())
                .add("rating", getRating())
                .add("posted", getPosted().toString());

        return objBuilder.build();
    }

    public static Edit fromJSON(Document jsonObject) {
        Edit edit = new Edit();

        // Set the fields from the JsonObject
        edit.setComment(jsonObject.getString("comment"));
        edit.setRating(jsonObject.getInteger("rating"));
        edit.setPosted(DateTime.parse(jsonObject.getString("posted")));

        return edit;
    }

    
}
