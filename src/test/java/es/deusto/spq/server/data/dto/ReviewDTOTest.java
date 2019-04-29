package es.deusto.spq.server.data.dto;

import es.deusto.spq.server.data.jdo.Review;
import org.junit.Assert;
import org.junit.Test;

import java.sql.Timestamp;

public class ReviewDTOTest {

    @Test
    public void test_toString() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Review review = new Review("testreview", "meh", 6, timestamp);

        Assert.assertEquals(
                "Review [reviewID=testreview, opinion=meh, score=6, publishDate=" + timestamp.toString() +"]",
                review.toString()
        );
    }

    @Test
    public void test_setters() {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Review review = new Review("testreview", "meh", 6, timestamp);


        review.setReviewID("test2");
        Assert.assertEquals("test2", review.getReviewID());

    }
}
