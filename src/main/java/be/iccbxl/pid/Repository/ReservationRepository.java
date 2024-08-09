package be.iccbxl.pid.Repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import be.iccbxl.pid.Model.Representation;
import be.iccbxl.pid.Model.Reservation;
import be.iccbxl.pid.Model.User;

public interface ReservationRepository extends CrudRepository<Reservation, Long>{
	
    Reservation findByRepresentation(Representation representation);

	List<Reservation> findReservationsByUser(User currentUser);



}
