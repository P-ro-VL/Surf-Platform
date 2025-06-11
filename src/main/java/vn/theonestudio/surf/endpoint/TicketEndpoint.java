package vn.theonestudio.surf.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.theonestudio.surf.api.ApiCallResult;
import vn.theonestudio.surf.api.ApiExecutorService;
import vn.theonestudio.surf.api.ApiResponse;
import vn.theonestudio.surf.dto.request.CreateTicketRequest;
import vn.theonestudio.surf.dto.request.UpdateTicketRequest;
import vn.theonestudio.surf.dto.response.TicketResponse;
import vn.theonestudio.surf.repository.TicketRepository;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "/v1/ticket", name = "Ticket API")
@AllArgsConstructor
public class TicketEndpoint {

    ApiExecutorService apiExecutorService;

    TicketRepository ticketRepository;


    @PostMapping
    public ResponseEntity<ApiResponse<TicketResponse>> createTicketEndpoint(@RequestBody CreateTicketRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(ticketRepository.createTicket(request)));
    }

    @PutMapping(path = "/{ticketId}")
    public ResponseEntity<ApiResponse<TicketResponse>> updateTicketEndpoint(@PathVariable UUID ticketId, @RequestBody UpdateTicketRequest request, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(ticketRepository.updateTicket(ticketId, request)));
    }

    @GetMapping(path = "/{teamId}")
    public ResponseEntity<ApiResponse<List<TicketResponse>>> getAllTicketsEndpoint(@PathVariable UUID teamId, @RequestParam(required = false) String type, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(ticketRepository.getAllTickets(teamId, type)));
    }

    @DeleteMapping(path = "/{ticketId}")
    public ResponseEntity<ApiResponse<Boolean>> deleteTicketEndpoint(@PathVariable UUID ticketId, HttpServletRequest httpServletRequest) {
        return apiExecutorService.execute(httpServletRequest, () -> new ApiCallResult<>(ticketRepository.deleteTicket(ticketId)));
    }

}
