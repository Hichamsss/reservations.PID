package be.iccbxl.pid.Repository;

import be.iccbxl.pid.Model.PaymentSession;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PaymentRepository extends CrudRepository<PaymentSession, Long> {
    // Trouver toutes les sessions de paiement
    List<PaymentSession> findAll();
}
