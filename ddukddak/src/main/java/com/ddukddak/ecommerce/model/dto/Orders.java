package com.ddukddak.ecommerce.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Orders {
	private int orderNo;
	private String orderDate;
	private int totalPrice;
	private String orderDelFl;
	private int memberNo;
	private int deliveryId;
	private String merchantUid;
	private String status; // 결제 상태
	private String failReason; 
	
	
}
