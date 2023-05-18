package com.cts.mba.kafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationReader {
	
	
	
	 
	 
	 @KafkaListener(groupId = "${spring.kafka.consumer.group-id}" , topics = "${spring.kafka.topic.name}")
	 public void receiveMessages(String message) {
		  
		 System.out.println("Notification from Admin Service -> " +message);
		  
	 }

}
