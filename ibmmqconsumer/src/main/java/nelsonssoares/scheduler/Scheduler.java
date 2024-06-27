package nelsonssoares.scheduler;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import nelsonssoares.messaging.MessageSubscriber;

import javax.jms.JMSException;

@ApplicationScoped
public class Scheduler {
    @Inject
    MessageSubscriber subscriber;


    @Transactional
    @Scheduled(every = "8s")
    public void schedule() throws JMSException {
        subscriber.consumeMessage();
    }
}
