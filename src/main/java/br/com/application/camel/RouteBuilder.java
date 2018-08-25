package br.com.application.camel;

public class RouteBuilder extends org.apache.camel.builder.RouteBuilder{

	@Override
	public void configure() throws Exception {
		from("file:C:\\route").split().tokenize("\n").to("jms:queue:File");
	}

}
