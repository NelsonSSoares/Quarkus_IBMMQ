package nelsonssoares.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nelsonssoares.commons.constants.MessagingConstants;
import nelsonssoares.domain.dto.UserDTO;

import javax.jms.*;

@ApplicationScoped
public class MessagingProducer {

    @Inject
    ObjectMapper mapper;

    public boolean sendUserMessage(UserDTO user){

        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();

        try{
            factory.setHostName(MessagingConstants.HOST);
            factory.setPort(MessagingConstants.PORT);
            factory.setChannel(MessagingConstants.CHANNEL);
            factory.setQueueManager(MessagingConstants.QMGR);
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

            Connection connection = factory.createConnection(MessagingConstants.APP_USER,MessagingConstants.APP_PASSWORD);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue(MessagingConstants.QUEUE_NAME);
            MessageProducer producer =  session.createProducer(queue);

            String json = mapper.writeValueAsString(user);
            TextMessage message = session.createTextMessage(json);
            producer.send(message);

            System.out.println("OBJECT: " + message);

        }catch(JMSException | JsonProcessingException e){
            System.out.println(e);
            return false;
        }
        return true;
    }

    public boolean sendStringMessage(String message){

        MQQueueConnectionFactory factory = new MQQueueConnectionFactory();

        try {
            factory.setHostName(MessagingConstants.HOST);
            factory.setPort(MessagingConstants.PORT);
            factory.setChannel(MessagingConstants.CHANNEL);
            factory.setQueueManager(MessagingConstants.QMGR);
            factory.setTransportType(WMQConstants.WMQ_CM_CLIENT);

            Connection connection = factory.createConnection(MessagingConstants.APP_USER, MessagingConstants.APP_PASSWORD);
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Queue queue = session.createQueue(MessagingConstants.QUEUE_NAME);
            MessageProducer producer = session.createProducer(queue);

            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
        }catch(JMSException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

}
