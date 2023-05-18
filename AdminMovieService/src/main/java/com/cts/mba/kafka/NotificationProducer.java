package com.cts.mba.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.cts.mba.entities.Movie;
import com.cts.mba.entities.Theatre;

@Service
public class NotificationProducer {
	
	 
	 @Value("${spring.kafka.topic.name}")
	 private String topicName;
	 
	 
	 @Autowired
	 private KafkaTemplate<String, String> kafkaTemplate1;
	 
	 
	 
	
	public void sendNotificationMovie(Movie movie) {
		  
		   this.kafkaTemplate1.send(topicName , "{ movieId :" + movie.getId() + " movieName: "+ movie.getMovieName()+" seatsAvailable: " + movie.getSeatsAvailable()+" theatreName " + movie.getTheatreName() + " status : " + movie.getStatus() +" }");
		   
	}
	
	public void sendNotificationTheatre(Theatre theatre) {
		  
		   this.kafkaTemplate1.send(topicName ,  "{ theatreId : " + theatre.getId()+" theatreName " +theatre.getTheatreName() + " }");
		   
	}

}
