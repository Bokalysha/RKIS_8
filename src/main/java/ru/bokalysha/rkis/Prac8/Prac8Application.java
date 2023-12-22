package ru.bokalysha.rkis.Prac8;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.bokalysha.rkis.Prac8.messaging.MessageReceiver;

@SpringBootApplication
public class Prac8Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Prac8Application.class, args);
		MessageReceiver messageReceiver = context.getBean(MessageReceiver.class);
		messageReceiver.startReceivingMessages();
	}

}
