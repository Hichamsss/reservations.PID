package be.iccbxl.pid.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import be.iccbxl.pid.Dto.RepresentationDTO;
import be.iccbxl.pid.Dto.UserDTO;
import be.iccbxl.pid.Model.Representation;
import be.iccbxl.pid.Model.Reservation;
import be.iccbxl.pid.Model.User;
import be.iccbxl.pid.Repository.ReservationRepository;

@Service
public class ReservationService {
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private RepresentationService representationService;
	
	@Autowired
	private UserService userService;
	
	 public List<Reservation> getAll() {
		 
	        List<Reservation> reservations = new ArrayList<>();
	        reservationRepository.findAll().forEach(reservations::add);
	        return reservations;
	    }
	 
	    public Reservation get(Long id) {
	        return reservationRepository.findById(id).orElse(null);
	    }

	    public Reservation save(Reservation reservation) {
	       return reservationRepository.save(reservation);
	    }

	    public void update(Long id, Reservation reservation) {
	        reservationRepository.save(reservation);
	    }

	    public void delete(Long id) {
	        reservationRepository.deleteById(id);
	    }
	    
	    public Reservation createReservation(int nbPlaces, RepresentationDTO representationDTO, UserDTO userDTO) {
	        // Convertir les DTO en entités Representation et User
	        Representation representation = representationService.get(representationDTO.getId());
	        User user = userService.getUser(userDTO.getId());

	        // Créer la réservation
	        Reservation reservation = new Reservation();
	        reservation.setNbPlaces(nbPlaces);
	        reservation.setRepresentation(representation);
	        reservation.setUser(user);
	        reservation.setCreatedAt(LocalDateTime.now());


	        return reservationRepository.save(reservation);
	    }
	    
	    public Reservation getReservationByRepresentation(Representation representation) {
	        return reservationRepository.findByRepresentation(representation);
	    }

		public List<Reservation> getReservationsByUser(User currentUser) {

			return reservationRepository.findReservationsByUser(currentUser);
		}
	   



	 
	 
}

