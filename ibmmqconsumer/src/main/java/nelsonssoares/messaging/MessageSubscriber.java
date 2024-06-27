package nelsonssoares.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import nelsonssoares.commons.constants.MessagingConstants;
import nelsonssoares.domain.dto.UserDTO;

import javax.jms.*;

@ApplicationScoped
public class MessageSubscriber {

    @Inject
    ObjectMapper mapper;

    public void consumeMessage(){
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
            MessageConsumer consumer= session.createConsumer(queue);

            connection.start();

            Message message = consumer.receive(1000);

            TextMessage textMessage = (TextMessage) message;
            String json= textMessage.getText();

            try{
                UserDTO user = mapper.readValue(json, UserDTO.class);
                System.out.println("JSON :" +user);
            } catch (JsonMappingException e) {
                System.out.println("A Mensagem nao e um JSON: " + textMessage);
            } catch (JsonProcessingException e) {
                System.out.println("A Mensagem nao e um JSON: " + textMessage);
            }
            System.out.println("Mensagem de Texto: "+ message);

            consumer.close();
            session.close();
            connection.close();

        }catch(JMSException e){
            System.out.println(e);
        }
    }
}
