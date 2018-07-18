package com.ronnye.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.ronnye.cursomc.domain.Pedido;

public interface EmailService {
	
	void sendOrderConfirmationEmail(Pedido obj);
	
	void sendEmail(SimpleMailMessage msg);

}
