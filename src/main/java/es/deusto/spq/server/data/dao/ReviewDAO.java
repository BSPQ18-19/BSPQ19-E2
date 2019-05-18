package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.bloomfilter.SimpleBloomFilter;
import es.deusto.spq.server.data.jdo.Review;
import es.deusto.spq.server.logger.ServerLogger;

/**
 * The DAO class for the Review.
 *
 * @author egoes
 */
public class ReviewDAO implements IReviewDAO {
	/**
	 * The persistance manager variable
	 */
	private final PersistenceManager pm;
	/**
	 * The transaction variable needed to make operations on the DB.
	 */
	private Transaction tx;
	/** The bloom filter. */
	private SimpleBloomFilter<Review> filter;

	/**
	 * The constructor sets the persistence manager to use it later on the methods.
	 */
	public ReviewDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		filter = new SimpleBloomFilter<Review>();
	}

	@Override
	public Review storeReview(Review r, String hotelID, String userID) {
		if (!checkUserReview(hotelID, userID))
			return null;
		tx = pm.currentTransaction();
		try {
			// Begin transaction
			tx.begin();

			// Stores the review on the DB
			pm.makePersistent(r);
			filter.add(r);
			tx.commit();

			// Returns a detachedCopy of the stored review
			Review re = pm.detachCopy(r);
			return re;
		} catch (Exception e) {
			ServerLogger.getLogger().fatal("   $ Error Storing a review: " + e.getMessage());
		} finally {
			close();
		}
		return null;
	}

	@Override
	public boolean deleteReview(String reviewID) {
		Review tmpReview = new Review(reviewID, null, 0, null);
		if(!filter.contains(tmpReview))
			return false;
		
		try {
			tx = pm.currentTransaction();
			tx.begin();

			// Searches for a certain review on the DB
			final Query<Review> query = pm.newQuery(Review.class);
			query.setFilter("reviewID == '" + reviewID + "'");
			@SuppressWarnings("unchecked")
			final List<Review> queryExecution = (List<Review>) query.execute();
			// Checks if the list is null
			if (queryExecution.isEmpty() || queryExecution.size() > 1)
				return false;
			pm.deletePersistent(queryExecution.get(0));

			tx.commit();

			return true;

		} catch (Exception e) {
			ServerLogger.getLogger().fatal("Error in ReviewDAO:deleteReview()");
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	@Override
	public boolean checkUserReview(String hotelID, String userID) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			// Searches for all the reveiws on the DB
			Query<Review> query = pm.newQuery(Review.class);
			@SuppressWarnings("unchecked")
			List<Review> queryExecution = (List<Review>) query.execute();

			tx.commit();
			// Looks if there exist a review of the specified hotel by the specified user
			for (Review r : queryExecution)
				if (r.getHotel().getHotelId().equals(hotelID) && r.getUser().getUserID().equals(userID))
					return false;

			return true;
		} catch (Exception e) {
			ServerLogger.getLogger().fatal("Error in ReviewDAO:checkUserReview()");
			e.printStackTrace();
		} finally {
			close();
		}
		return false;
	}

	@Override
	public List<Review> getReviewsOfHotel(String hotelID) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			// Searches for all the reviews in the DB
			Query<Review> query = pm.newQuery(Review.class);
			@SuppressWarnings("unchecked")
			List<Review> queryExecution = (List<Review>) query.execute();
			List<Review> result = new ArrayList<>();
			// Adds to the result list the reviews from the specified hotel
			for (Review r : queryExecution)
				if (r.getHotel().getHotelId().equals(hotelID))
					result.add(r);

			tx.commit();

			return result;

		} catch (Exception e) {
			ServerLogger.getLogger().fatal("Error in UserDAO:getUsers()");
			e.printStackTrace();

		} finally {
			close();
		}
		return null;
	}

	@Override
	public List<Review> getReviewsByUser(String userID) {
		try {
			tx = pm.currentTransaction();
			tx.begin();

			// Searches for all the reviews
			Query<Review> query = pm.newQuery(Review.class);
			@SuppressWarnings("unchecked")
			List<Review> queryExecution = (List<Review>) query.execute();
			List<Review> result = new ArrayList<>();
			// Adds to the result list the reviews from the specified user
			for (Review r : queryExecution)
				if (r.getUser().getUserID().equals(userID))
					result.add(r);

			tx.commit();

			return result;

		} catch (Exception e) {
			ServerLogger.getLogger().fatal("Error in UserDAO:getUsers()");
			e.printStackTrace();

		} finally {
			close();
		}
		return null;
	}

	/**
	 * Closes the transaction if it hasn't been closed before, and makes rollback.
	 */
	private void close() {
		if (tx != null && tx.isActive())
			tx.rollback();
	}
}