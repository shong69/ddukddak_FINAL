package com.ddukddak.payment.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ddukddak.ecommerce.model.dto.Orders;
import com.ddukddak.payment.model.dto.PaymentDTO;

@Mapper
public interface PaymentMapper {
	
	/** 주문 번호 구하기
	 * @param merchantUid
	 * @return
	 */
	Orders selectOrder(String merchantUid);
	
	/** 결제 넣기
	 * @param paymentDTO
	 * @return
	 */
	int addPayment(PaymentDTO paymentDTO);

	/** 구매 페이지 값 얻어오기
	 * @param merchantUid
	 * @return
	 */
	PaymentDTO selectPaid(String merchantUid);


	
}
