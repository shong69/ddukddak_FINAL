package com.ddukddak.ecommerce.controller;

import org.springframework.web.bind.annotation.RestController;

import com.ddukddak.common.config.PaymentConfig;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PaymentController {
	
	private final PaymentConfig paymentConfig;
	
	
}


