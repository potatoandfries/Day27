package sg.nus.iss.edu.Day27workshop.Repo;

import java.util.Optional;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.client.result.UpdateResult;

import sg.nus.iss.edu.Day27workshop.Model.Edit;
import sg.nus.iss.edu.Day27workshop.Model.Review;

@Repository
public class ReviewRepo {

    
    @Autowired
    private MongoTemplate mongoTemplate;

    // db.game.count({ gid: <your-gid-value> })
    public boolean gameExists(Integer gid) {
        Query q = new Query(Criteria.where("gid").is(gid));
        return mongoTemplate.exists(q, "game");
    }
    

    //why string ? >> you need to see

    //     db.reviews.insertOne(
    //   {
    //     user: "JohnDoe",
    //     rating: 5,
    //     comment: "Great product!",
    //     gid: 123,
    //     posted: ISODate("2024-01-25T00:00:00Z")
    //   }
    // )
    public String insertReview(Review review){
        //you need doc for it to work, mongotemplate only works w doc/ make it a json string. 
        Document reviewDoc = Document.parse(review.toJson().toString());
        Document doc = mongoTemplate.insert(reviewDoc,"reviews");
        return doc.getObjectId("_id").toHexString();
    }
        //if you wanna insert many : db.collectionName.insertMany([ { field1: "value1", field2: "value2" }, { field1: "value3", field2: "value4" }, // Add more documents as needed
            // public List<String> insertManyReviews(List<Review> reviews) {
            // List<String> insertedIds = new ArrayList<>();

            // List<Document> reviewDocuments = reviews.stream()
            //         .map(review -> Document.parse(review.toJson().toString()))
            //         .collect(Collectors.toList());

            // List<Document> insertedDocuments = mongoTemplate.insert(reviewDocuments, "reviews");

            // for (Document doc : insertedDocuments) {
            //     ObjectId objectId = doc.getObjectId("_id");
            //     if (objectId != null) {
            //         insertedIds.add(objectId.toHexString());
            //     }
            // }

            // return insertedIds;

    public boolean updateReview(String id, Edit edit){
        ObjectId gid = new ObjectId(id);
        Query q = new Query(Criteria.where("gid").is(gid));
        Document doc = Document.parse(edit.toJson().toString());
        Update updateops = new Update().set("rating",edit.getRating()).set("comment",edit.getComment()).push("editted",doc);
        UpdateResult result = mongoTemplate.updateMulti(q,updateops,Document.class,"reviews");
        return result.getModifiedCount()==1; //if it changes by 1 
    }

    // my way* same as day 26*
    public Review getReviewById(Integer gid) {
        Query q = new Query();
        q.addCriteria(Criteria.where("gid").is(gid));
        return mongoTemplate.findOne(q, Review.class, "reviews");
    }
}

