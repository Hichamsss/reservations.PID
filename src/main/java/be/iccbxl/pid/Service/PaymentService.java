package be.iccbxl.pid.Service;

import be.iccbxl.pid.Dto.SessionDTO;
import be.iccbxl.pid.Model.PaymentSession;
import be.iccbxl.pid.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public List<SessionDTO> getAllSessions() {
        List<PaymentSession> sessions = new ArrayList<>();
        paymentRepository.findAll().forEach(sessions::add);

        return sessions.stream().map(session -> new SessionDTO(
                session.getSessionId(),
                session.getProductName(),
                session.getAmount(),
                session.getCurrency(),
                session.getCreatedDate()
        )).collect(Collectors.toList());
    }

    public SessionDTO getSessionById(String id) {
        Long sessionId = Long.parseLong(id);
        Optional<PaymentSession> session = paymentRepository.findById(sessionId);

        return session.isPresent() ? new SessionDTO(
                session.get().getSessionId(),
                session.get().getProductName(),
                session.get().getAmount(),
                session.get().getCurrency(),
                session.get().getCreatedDate()
        ) : null;
    }

    public void addSession(PaymentSession session) {
        paymentRepository.save(session);
    }

    public void updateSession(String id, PaymentSession session) {
        paymentRepository.save(session);
    }

    public void deleteSession(String id) {
        Long sessionId = Long.parseLong(id);
        paymentRepository.deleteById(sessionId);
    }
}
