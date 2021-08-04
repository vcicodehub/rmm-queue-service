package com.signet.om.controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.signet.om.model.Order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class SqsQueueController {

	@Autowired
	private QueueMessagingTemplate queueMessagingTemplate;

	@Value("${sqs.order.url}")
	private String sqsUrl;

	@GetMapping("/queue/{message}")
	public String send(@PathVariable String message) {

        Order order = new Order();
		order.setId(UUID.randomUUID().toString());
		order.setName("My New Order");
		order.setVendorName("Nice Stuff Inc.");
		order.setComment(message);

		Map<String, Object> headers = new HashMap<>();
		headers.put("message-group-id", "om-order-group-id");
		headers.put("message-deduplication-id", order.getId());
		queueMessagingTemplate.convertAndSend(sqsUrl, order, headers);

		log.info("Sent message {} to queue {}", order.getId(), sqsUrl);

		return "You sent " + message + " to queue " + sqsUrl;
	}

	@SqsListener(value = "${sqs.order.url}", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
	public void queueListener(Order order) throws Exception {
		if (order != null && order.getComment().equalsIgnoreCase("ERROR")) {
			throw new Exception("Unable to process message");
		}
		log.info("NEW ORDER: {} ", order);
	}
}