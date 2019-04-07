package es.deusto.spq.server.data.dao;

import es.deusto.spq.server.data.dto.UserDTO;

public interface IDAO {

	public boolean checkAuthorizationIsAdmin(UserDTO authorization);
	
}
