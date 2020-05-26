package com.socialmedia.mailservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.socialmedia.mailservice.MailService;
import com.socialmedia.mailservice.model.NotificationDTO;
import com.socialmedia.mailservice.model.User;

@Component
public class QueueConsumer {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MailService notificationService;

	public void receiveMessage(String message) {
		logger.info("Received (String) " + message);
		processMessage(message);
	}

	public void receiveMessage(byte[] message) {
		String strMessage = new String(message);
		logger.info("Received (No String) " + strMessage);
		processMessage(strMessage);
	}

	private void processMessage(String message) {
		try {
			NotificationDTO dto = new ObjectMapper().readValue(message, NotificationDTO.class);

			logger.info("Received (dto) " + dto.getEvent());
			logger.info("Received (dto) " + dto.getUsername());

			if (dto.getEvent().equals("SendMail")) {

				User user = new User();
				user.setUsername(dto.getUsername());
				send(user);
				logger.info("Received (dto) " + dto.getEvent());
				logger.info("Received (dto) " + dto.getUsername());

			} else {

				logger.error("Received (errot) " + dto.getEvent().equals("SendMail"));
			}

			// mailServiceImpl.sendMail(mailDTO, null);
		} catch (JsonParseException e) {
			logger.warn("Bad JSON in message: " + message);
		} catch (JsonMappingException e) {
			logger.warn("cannot map JSON to NotificationRequest: " + message);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public String send(User user) {
		try {
			notificationService.sendEmail(user);
		} catch (MailException mailException) {
			System.out.println(mailException);
		}
		return "Congratulations! Your mail has been send to the user.";
	}
}