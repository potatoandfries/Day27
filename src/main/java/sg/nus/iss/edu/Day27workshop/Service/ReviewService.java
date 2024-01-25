package sg.nus.iss.edu.Day27workshop.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.nus.iss.edu.Day27workshop.Model.Edit;
import sg.nus.iss.edu.Day27workshop.Model.Review;
import sg.nus.iss.edu.Day27workshop.Repo.ReviewRepo;

@Service
public class ReviewService {
    
    @Autowired
    ReviewRepo repo1;

    public boolean createReview(Review review){
        
        if (repo1.gameExists(review.getGid())) {
            repo1.insertReview(review);
            System.out.println("Review created successfully.");
            return true;
        }
        else {
            System.out.println("Game does not exist for the given gid.");
            return false;
        }

    }

    public boolean updateReview(String id, Edit edit){
        return repo1.updateReview(id, edit);
    }

    public Review getReviewById(Integer id){
        return repo1.getReviewById(id);
    }
}
