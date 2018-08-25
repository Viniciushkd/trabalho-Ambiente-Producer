package br.com.application.ws;

import javax.jms.JMSException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.application.activeMQ.ProducerRoute;
import br.com.application.camel.RouteProducer;
import br.com.application.entity.Person;

@RestController
public class ProducerWS {

	private ProducerRoute producer = new ProducerRoute();
	private RouteProducer routeProducer = new RouteProducer();
	private static final String SENDING = "STATUS QUEUE :  ";
	
	@RequestMapping("/")
	public String producer() {
		 return "<h1>Producer Web Service</h1>"
		 		+ "Produzir Queue Objeto:<br>"
		 		+ "<b>WS:</b> \\producerPerson<br>"
		 		+ "<b>Atributo</b>: id, nome <br><br>"
		 		+ "Produzir Queue Texto:<br>"
		 		+ "<b>WS:</b> \\producerMessage<br>"
		 		+ "<b>Atributo:</b> message <br><br>"
		 		+ "Produzir Queue Route File:<br>"
		 		+ "<b>WS:</b> \\startRouteProducer";
	}
	
	@RequestMapping("/producerPerson")
	public String producerPerson(@RequestParam(value="nome") String nome, @RequestParam(value="idade") int idade) {
		Person person = new Person(nome, idade);
		String msg = "";
		try {
			msg = producer.sendMessage(person);
			return SENDING + person.toString() +  msg;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	@RequestMapping("/producerMessage")
	public String producerMessage(@RequestParam(value="message") String message ) {
		String msg = "";
		try {
			msg = producer.sendMessage(message);
		
			return SENDING + message + msg;
		} catch (JMSException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	@RequestMapping("/startRouteProducer")
	public void producerMessageFile() {
		routeProducer.producerQueueFile();
	}
}
