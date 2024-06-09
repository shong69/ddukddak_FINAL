package com.ddukddak.myPage.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	private int orderNo;
	private String orderDate;
	private int totalPrice;
	private String orderStatus;
	private String orderDelFl;
	private String MemberNo;
	private int deliveryId;
}
