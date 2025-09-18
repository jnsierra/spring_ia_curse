package ec.com.fisa.springai.tools;

import ec.com.fisa.springai.entity.HelpDeskTicket;
import ec.com.fisa.springai.model.TicketRequest;
import ec.com.fisa.springai.service.HelpDeskTicketService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class HelpDeskTools {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelpDeskTools.class);

    private final HelpDeskTicketService service;

    @Tool(name = "createTicket", description = "Create the Support ticket", returnDirect = true)
    String createTicket(@ToolParam(description = "Detail to create a Support ticket") TicketRequest ticketRequest, ToolContext toolContext) {
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Creating support ticket for user: {} with details: {}", username, ticketRequest);
        HelpDeskTicket savedTicket = service.createTicket(ticketRequest, username);
        LOGGER.info("Ticket created successfully. Ticket ID: {}, Username: {}", savedTicket.getId(), savedTicket.getUsername());
        return "Ticket #" + savedTicket.getId() + " created successfully for user " + savedTicket.getUsername();
    }
    @Tool(name = "getTicketsStatus", description = "Fetch the status of the tickets based on a given username")
    List<HelpDeskTicket> getTicketsStatus(ToolContext toolContext){
        String username = (String) toolContext.getContext().get("username");
        LOGGER.info("Fetching open tickets for user: {}", username);
        List<HelpDeskTicket> tickets = service.getTicketsByUsername(username);
        LOGGER.info("Found {} open tickets for user: {}", tickets.size(), username);
        //throw new RuntimeException("Unable to fetch ticket status");
        return tickets;
    }
}
