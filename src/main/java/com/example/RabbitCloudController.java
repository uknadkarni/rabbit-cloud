package com.example;

import org.apache.log4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RabbitCloudController {
	
	private static Logger logger = Logger.getLogger(RabbitCloudController.class);

	@Autowired
	RabbitTemplate rabbitTemplate;
	
	public RabbitCloudController() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping(value="/publish", method=RequestMethod.POST)
	public String publish(@RequestParam(value="message", defaultValue="First Data and Pivotal are BFFs") String message){
		rabbitTemplate.convertAndSend(message);
		logger.info("Sent: " + message);
		return message;
	}
	
	@RequestMapping(value="/receive", method=RequestMethod.GET)
	public String receive(){
		String message = (String) rabbitTemplate.receiveAndConvert(RabbitCloudApplication.queueName);
		if(message == null){
			message = "Oh snap! No more messages in the queue";
			logger.error(message);
		}
		else{
			logger.info("Received: " + message);
		}
		return message;
	}
}
