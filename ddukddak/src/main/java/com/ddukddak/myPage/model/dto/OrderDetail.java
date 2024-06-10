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
public class OrderDetail {

	private int orderItemNo;
	private int orderQuantity;
	private int orderPrice;
	private int orderNo;
	private int productNo;
}
