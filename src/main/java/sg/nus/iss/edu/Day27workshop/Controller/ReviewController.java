package sg.nus.iss.edu.Day27workshop.Controller;

import org.joda.time.DateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import sg.nus.iss.edu.Day27workshop.Model.Edit;
import sg.nus.iss.edu.Day27workshop.Model.Review;
import sg.nus.iss.edu.Day27workshop.Service.ReviewService;

@RestController
@RequestMapping(path={"/"})
public class ReviewController {
    
    @Autowired
    ReviewService svc;

    @PostMapping(path = { "/review"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> postReview(@RequestBody MultiValueMap<String, String> formData) {
    String user = formData.getFirst("user");
    Integer ratings = Integer.parseInt(formData.getFirst("ratings"));
    String comment = formData.getFirst("comment");
    Integer gid = Integer.parseInt(formData.getFirst("gid"));
    DateTime posted = DateTime.now();

    // Create a Review
    Review r = new Review();
    r.setUser(user);
    r.setComment(comment);
    r.setGid(gid);
    r.setRating(ratings);
    r.setPosted(posted);
        System.out.println(r.getGid());
    // Call the service to create the review
    boolean created = svc.createReview(r);

    if (created) {
        return ResponseEntity.ok("Review created successfully.");
    } else {
        return ResponseEntity.badRequest().body("Failed to create review.");
        }
    }

    @PutMapping(path = { "/review/{review_id}"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateReview(
        @PathVariable("review_id") String reviewId,
        @RequestBody MultiValueMap<String, String> formData) {

    String comment = formData.getFirst("comment");
    Integer rating = Integer.parseInt(formData.getFirst("rating"));
    DateTime posted = DateTime.now();

    // Create an Edit
    Edit e = new Edit();
    e.setComment(comment);
    e.setRating(rating);
    e.setPosted(posted);

    if (svc.updateReview(reviewId, e)) {
        return ResponseEntity.ok("Review updated successfully.");
    } else {
        return ResponseEntity.badRequest().body("Failed to update review.");
        }
    }

    @GetMapping(path = { "/review/{review_id}/history" }, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getReviewHistory(@PathVariable("review_id") Integer reviewId) {
    JsonObject result = null;

    // Log the reviewId before calling getReviewById
    System.out.println("Review ID: " + reviewId);
    Review rh = svc.getReviewById(reviewId);
    // Log the result of getReviewById
    System.out.println("Result of getReviewById: " + rh);

    // Convert the DateTime to String
    String postedDateString = rh.getPosted().toString();
    System.out.println(postedDateString);

    JsonObjectBuilder objBuilder = Json.createObjectBuilder();
    objBuilder.add("reviews", rh.toJson());
    result = objBuilder.build();

    return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }


} 
       
