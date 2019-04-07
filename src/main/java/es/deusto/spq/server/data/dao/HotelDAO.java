package es.deusto.spq.server.data.dao;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.Transaction;

import javax.jdo.Extent;

import es.deusto.spq.server.data.MyPersistenceManager;
import es.deusto.spq.server.data.dto.Assembler;
import es.deusto.spq.server.data.dto.HotelDTO;
import es.deusto.spq.server.data.dto.UserDTO;
import es.deusto.spq.server.data.jdo.Hotel;
import es.deusto.spq.server.data.jdo.User;
import es.deusto.spq.server.logger.ServerLogger;

public class HotelDAO implements IDAO, IHotelDAO {

	private PersistenceManager pm;
	private Transaction tx;
	private Assembler assembler;

	public HotelDAO() {
		pm = MyPersistenceManager.getPersistenceManager();
		assembler = new Assembler();
	}

	@Override
	public boolean checkAuthorizationIsAdmin(UserDTO authorization) {
		// TODO
		return true;
	}

	@Override
	public List<HotelDTO> getHotels(UserDTO authorization) {
		if (!checkAuthorizationIsAdmin(authorization))
			return null;

		try {
			pm.getFetchPlan().setMaxFetchDepth(2);
			tx = pm.currentTransaction();
			tx.begin();

			Extent<Hotel> extent = pm.getExtent(Hotel.class, true);
			List<HotelDTO> result = new ArrayList<HotelDTO>();
			for (Hotel hotel : extent)
				result.add(assembler.assembleHotel(hotel));
			tx.commit();

			return result;

		} catch (Exception e) {
			ServerLogger.getLogger().severe("Error retrieving all the hotels");
			e.printStackTrace();

		} finally {
			close();
		}

		return null;
	}

	@Override
	public HotelDTO getHotelbyID(UserDTO authorization, String hotelID) {
		if (!checkAuthorizationIsAdmin(authorization))
			return null;

		try {
			pm.getFetchPlan().setMaxFetchDepth(2); //Default level == 1
			tx = pm.currentTransaction();
			tx.begin();
			
			Query<Hotel> query = pm.newQuery(Hotel.class);
			query.setFilter("hotelId == '" + hotelID + "'");
			@SuppressWarnings("unchecked")
			List<Hotel> result = (List<Hotel>) query.execute();
			tx.commit();
			return result == null || result.isEmpty() || result.size() > 1 ? 
					null : 
					assembler.assembleHotel(result.get(0));
		} catch (Exception e) {
			ServerLogger.getLogger().severe("Error retrieving the hotel with ID: " + hotelID);
			e.printStackTrace();

		} finally {
			close();
		}
		return null;
	}

	@Override
	public HotelDTO createHotel(UserDTO authorization, HotelDTO hotel) {
		if (!checkAuthorizationIsAdmin(authorization))
			return null;

		try {
			tx = pm.currentTransaction();
			tx.begin();

			pm.makePersistent(hotel);

			tx.commit();

			return pm.detachCopy(hotel);

		} catch (Exception e) {
			ServerLogger.getLogger().severe("Error creating the hotel with ID: " + hotel.getHotelId());
			e.printStackTrace();

		} finally {
			close();
		}

		return null;
	}

	@Override
	public boolean deleteHotel(UserDTO authorization, String hotelID) {
		if (!checkAuthorizationIsAdmin(authorization))
			return false;
		
		try {
			tx = pm.currentTransaction();
			tx.begin();

			Query<User> query = pm.newQuery(User.class);
			query.setFilter("hotelID == '" + hotelID + "'");
			@SuppressWarnings("unchecked")
			List<User> queryExecution = (List<User>) query.execute();
			if (queryExecution.isEmpty() || queryExecution.size() > 1)
				return false;
			pm.deletePersistent(queryExecution.get(0));

			tx.commit();

			return true;

		} catch (Exception e) {
			ServerLogger.getLogger().severe("Error deleting the hotel with ID: " + hotelID);
			e.printStackTrace();
		} finally {
			close();
		}
		return false;

	}
	
	@Override
	public HotelDTO editHotel(UserDTO authorization, String hotelID, HotelDTO updates) {
		if(!checkAuthorizationIsAdmin(authorization))
			return null;

		try {
			pm.getFetchPlan().setMaxFetchDepth(2); //Default level == 1
			tx = pm.currentTransaction();
			tx.begin();
			
			Query<Hotel> query = pm.newQuery(Hotel.class);
			query.setFilter("hotelId == '" + hotelID + "'");
			@SuppressWarnings("unchecked")
			List<Hotel> result = (List<Hotel>) query.execute();
			tx.commit();
			if(result == null || result.isEmpty() || result.size() > 1)
				return null;
			Hotel old = result.get(0);
			updateHotelFields(old, updates);
			
			pm.detachCopy(old);
			return assembler.assembleHotel(old);
			
		} catch (Exception e) {
			ServerLogger.getLogger().severe("Error retrieving the hotel with ID: " + hotelID);
			e.printStackTrace();

		} finally {
			close();
		}
		return null;
	}
	
	private void updateHotelFields(Hotel old, HotelDTO current) {
		if(!old.getName().equals(current.getName()))
			old.setName(current.getName());
		if(!old.getLocation().equals(current.getLocation()))
			old.setLocation(current.getLocation());
		if(old.getServices().size() != current.getServices().size())
			old.setServices(current.getServices());
		if(!old.getSeasonStart().equals(current.getSeasonStart()))
			old.setSeasonStart(current.getSeasonStart());
		if(!old.getSeasonEnding().equals(current.getSeasonEnding()))
			old.setSeasonEnding(current.getSeasonEnding());
	}
	
	/**
	 * Closes the transaction if it hasn't been closed before, and makes rollback.
	 */
	private final void close() {
		if (tx != null && tx.isActive())
			tx.rollback();
	}
}