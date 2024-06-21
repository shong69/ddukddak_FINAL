package com.ddukddak.payment.model.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PaymentDTO {
    private int paymentNo;
    private int orderNo;
    private int memberNo;
    private String impUid;               // 아임포트 거래 고유번호
    private String merchantUid;          // 가맹점 주문번호
    private String customerUid;          // 구매자 결제수단 식별 고유번호
    private String customerUidUsage;     // 구매자 결제수단 식별 고유번호 사용 구분코드
    private LocalDateTime startedAt;     // 결제 요청 시작 시간
    private String status;               // 결제 상태(예: 'paid', 'failed')
    private String failReason;           // 실패 사유
    private LocalDateTime failedAt;      // 실패 시간
    private LocalDateTime paidAt;        // 결제 시간
    private String cancelReason;         // 취소 사유
    private Integer cancelAmount;        // 취소 금액
    private LocalDateTime cancelledAt;   // 취소 시간
    private Integer amount;              // 결제 금액
    private String currency;             // 결제 통화구분코드(KRW/USD 등)
    private String payMethod;            // 결제 방법(예: 'card')
    private String name;                 // 결제 이름
    private String buyerName;            // 구매자 이름
    private String buyerTel;             // 구매자 전화번호
    private String buyerEmail;           // 구매자 이메일
    private String buyerPostcode;        // 구매자 우편번호
    private String buyerAddr;            // 구매자 주소
    private String pgId;                 // PG사 아이디
    private String pgTid;                // PG사 거래번호
    private String pgProvider;           // PG사 제공자
    private String cardCode;             // 카드 코드
    private String cardName;             // 카드 이름
    private int cardType;            	 // 카드 유형(0: 신용카드, 1: 체크카드)
    private String cardNumber;           // 카드 번호(일부 마스킹됨)
    private Integer cardQuota;           // 할부 개월수
    private String applyNum;             // 승인 번호
    private String customData;           // 고객 추가 정보
    private String escrow;              // 에스크로 결제 여부
    
    

}
