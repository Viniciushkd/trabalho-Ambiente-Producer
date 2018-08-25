package br.com.application.activeMQ;

import java.io.Serializable;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

import br.com.application.entity.Person;

public class ProducerRoute {
	
	private static final String ROUTE = "tcp://0.0.0.0:61616";
	
	public String sendMessage(final Serializable message) throws JMSException {
		String msg = "";
		ConnectionFactory factory = new ActiveMQConnectionFactory(ROUTE);
		Connection connection = factory.createConnection();
		connection.start();

		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

		Destination destination = session.createQueue(message instanceof String ? "Message" : "Object");
		
		MessageProducer messageProducer = session.createProducer(destination);
		messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		Message type = null;
		
		if(message instanceof Person) {
			ObjectMessage objectMessage = session.createObjectMessage(message);
			type = objectMessage;
			
		} else if(message instanceof String || message instanceof Integer) {
			TextMessage textMessage = session.createTextMessage(message.toString());
			type = textMessage;
		}
		
		try {
			messageProducer.send(type);
			msg = " [Enviado com Sucesso]";
		} catch (Exception e) {
			msg = " [Erro ao Enviar]";
		}

		session.close();
		messageProducer.close();
		connection.close();
		
		return msg;
	}
}
