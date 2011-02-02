package jee.architect.cookbook.glassfish.messaging.client;


import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.Session;
import javax.naming.InitialContext;

import junit.framework.TestCase;

public class JMSTestCase extends TestCase {

    /*
     * Don't forget to create MyQCF and MyQ !! (use admin console)
     */
    public void testJMS() throws Exception {

        InitialContext jndi = new InitialContext();
        // Lookup queue connection factory
        QueueConnectionFactory queueConnectionFactory = (QueueConnectionFactory) jndi.lookup("MyQCF");

        // Lookup queue
        Queue queue = (Queue) jndi.lookup("MyQ");
        // Get queue connection
        QueueConnection qConn = (QueueConnection) queueConnectionFactory.createConnection();

        // Get session
        Session session = qConn.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Set the JMS message
        ObjectMessage msg = session.createObjectMessage();
        msg.setObject("Test");

        // Send JMS message
        session.createProducer(queue).send(msg);
    }
}