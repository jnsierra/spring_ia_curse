package ec.com.fisa.springai.service;

import ec.com.fisa.springai.entity.HelpDeskTicket;
import ec.com.fisa.springai.model.TicketRequest;
import ec.com.fisa.springai.repository.HelpDeskTicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HelpDeskTicketService {

    private final HelpDeskTicketRepository helpDeskTicketRepository;

    public HelpDeskTicket createTicket(TicketRequest ticketInput, String username){
        HelpDeskTicket ticket = HelpDeskTicket.builder()
                .issue(ticketInput.issue())
                .username(username)
                .status("OPEN")
                .createdAt(LocalDateTime.now())
                .eta(LocalDateTime.now().plusDays(7))
                .build();
        return helpDeskTicketRepository.save(ticket);
    }
    public List<HelpDeskTicket> getTicketsByUsername(String username){
        return helpDeskTicketRepository.findByUsername(username);
    }
}
