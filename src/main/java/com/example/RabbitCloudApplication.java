package com.example;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
public class RabbitCloudApplication {

	public static String queueName = "queue";
	private static String exchangeName = "exchange";
	
	public static void main(String[] args) {
		SpringApplication.run(RabbitCloudApplication.class, args);
	}
	
	@Bean
	public Queue queue(){
		return new Queue(queueName);
	}
	
	@Bean
	public TopicExchange exchange(){
		return new TopicExchange(exchangeName);
	}
	
	@Bean
	public Binding binding(Queue queue, TopicExchange exchange){
		return BindingBuilder.bind(queue).to(exchange).with(queueName);
	}
	
	@Profile("dev")
	@Bean
	public ConnectionFactory connectionFactory(){
		return new CachingConnectionFactory("localhost");
	}
	
	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setExchange(exchangeName);
		rabbitTemplate.setRoutingKey(queueName);
		return rabbitTemplate;
	}
	
}
