package nelsonssoares.rest;


import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import nelsonssoares.domain.dto.UserDTO;
import nelsonssoares.messaging.MessagingProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static nelsonssoares.commons.constants.ControllerConstants.*;

@Path(BASE_PATH)
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProducerController {

    private static final Logger log = LoggerFactory.getLogger(ProducerController.class);

    List<UserDTO> users = new ArrayList<>();

    @Inject
    MessagingProducer producer;

    @POST
    @Transactional
    @Path(USER_PATH)
    public Response sendUserMessage(UserDTO user){
        log.info("Sending user message: {}", user);
        users.add(user);
        return producer.sendUserMessage(user) ? Response.ok(user).build() : Response.status(Response.Status.BAD_REQUEST).build();
    }

    @POST
    @Transactional
    @Path(STRING_PATH)
    public Response sendStringMessage(String message){
        log.info("Sending string message: {}", message);
        return producer.sendStringMessage(message) ? Response.ok(message).build() : Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @GET
    @Path(USER_PATH)
    public Response getUsers(){
        log.info("Getting users: {}", users);
        return Response.ok(users).build();
    }

}
