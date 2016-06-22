/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package in.gov.nvli.harvester.listener.hibernate;

import javax.annotation.PostConstruct;
import org.hibernate.SessionFactory;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.hibernate.event.spi.PostInsertEventListener;
import org.hibernate.internal.SessionFactoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author ankit
 */
@Component
public class HibernateEventWiring {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private PostInsertEventListener postListener;

    @PostConstruct
    public void registerListeners() {
        EventListenerRegistry registry = ((SessionFactoryImpl) sessionFactory).getServiceRegistry().getService(
                EventListenerRegistry.class);
        registry.getEventListenerGroup(EventType.POST_INSERT).appendListener(postListener);

    }
}
