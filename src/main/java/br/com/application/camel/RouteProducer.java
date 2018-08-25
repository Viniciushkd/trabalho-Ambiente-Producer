package br.com.application.camel;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.camel.CamelContext;
import org.apache.camel.component.jms.JmsComponent;
import org.apache.camel.impl.DefaultCamelContext;

public class RouteProducer {

	private static final String ROUTE = "tcp://0.0.0.0:61616";
	
	public void producerQueueFile() {
		RouteBuilder routeBuilder = new RouteBuilder();
		CamelContext ctx = new DefaultCamelContext();

		ConnectionFactory factory = new ActiveMQConnectionFactory(ROUTE);
		ctx.addComponent("jms", JmsComponent.jmsComponentAutoAcknowledge(factory));
		
		try {
			ctx.addRoutes(routeBuilder);
			ctx.start();
			Thread.sleep(5 * 60 * 1000);
			ctx.stop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
