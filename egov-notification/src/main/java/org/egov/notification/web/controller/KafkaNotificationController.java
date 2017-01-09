package org.egov.notification.web.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.egov.notification.web.messaging.MessagePriority;
import org.egov.notification.web.messaging.email.EmailService;
import org.egov.notification.web.messaging.sms.SMSService;
import org.egov.notification.web.model.EmailRequest;
import org.egov.notification.web.model.SMSRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

@RestController
@RequestMapping("/notification")
public class KafkaNotificationController {

	@Autowired
	@Qualifier("smsService")
	private SMSService smsService;

	private EmailService emailService;

	public KafkaConsumer<String, String> notifications;

	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public String hi() {

		return "Hi satyam";
	}

	@RequestMapping(value = "/kafka/send", method = RequestMethod.GET)
	public String sender() {

		Properties props = new Properties();
		props.put("bootstrap.servers", "kafka:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 1);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		System.err.println("producer ********************* " + new Date().toString());
		Producer<String, String> producer = new KafkaProducer<>(props);
		Date now = new Date();
		java.util.concurrent.Future<RecordMetadata> future = producer.send(new ProducerRecord<String, String>(
				"egov-notification-sms", "7204695548", "Test Message " + new Date()));
		String logMessage = "*** Wrote to Kafka topic egov-notification-sms at time " + now + ", obtained future "
				+ future.toString();
		System.err.println(logMessage);
		/*
		 * TODO also write to email queue producer.send(new
		 * ProducerRecord<String, String>("egov-notification-email",
		 * "venki@egovernments.org", "Test Email eGov Notification " + new
		 * Date())); System.err.println( "producer 2********************* " +
		 * new Date().toString());
		 */
		producer.close();
		return logMessage;
	}

	@RequestMapping(value = "/kafka/receive", method = RequestMethod.GET)
	public void receiver(@RequestParam(value = "close") String close) {

		Properties props = new Properties();
		props.put("bootstrap.servers", "kafka:9092");
		props.put("group.id", "notifications");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "10000");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		notifications = new KafkaConsumer<>(props);
		notifications.subscribe(Arrays.asList("egov-notification-sms", "egov-notification-email"));
		if (close != null && close.equals("true"))
			notifications.close();
		while (true) {
			ConsumerRecords<String, String> records = notifications.poll(1000);
			System.err.println("******** polling at time " + new Date().toString());
			for (ConsumerRecord<String, String> record : records) {
				if (record.topic().equals("egov-notification-sms")) {
					System.err.println("***** received message [key" + record.key() + "] + value [" + record.value()
							+ "] from topic egov-notification-sms");

					Gson gson = new Gson();
					SMSRequest request = gson.fromJson(record.value(), SMSRequest.class);
					smsService.sendSMS(request.getMobileNo(), request.getMessage(), MessagePriority.HIGH);
				}
				if (record.topic().equals("egov-notification-email")) {
					System.err.println("***** received message [key " + record.key() + "] + value [" + record.value()
							+ "] from topic egov-notification-email");
					Gson gson = new Gson();
					EmailRequest request = gson.fromJson(record.value(), EmailRequest.class); //
					emailService.sendMail(request.getEmail(), //
							request.getSubject(), request.getBody());
				}

			}
		}
	}
}