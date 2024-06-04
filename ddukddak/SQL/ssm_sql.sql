
SELECT * FROM "MEMBER";


SELECT * FROM PARTNER;


SELECT * FROM EMAIL_AUTH;

SELECT * FROM SMS_AUTH;

INSERT INTO "MEMBER"
VALUES(1, 'seungjoo', 'rania2002@naver.com', '1234', '이승주', '닉네임이승주', 
'01036812682', '서울시 송파구 석촌동', '3000', DEFAULT, DEFAULT, DEFAULT, 2, DEFAULT);

INSERT INTO "MEMBER"
VALUES(2, 'soyoung', 'shong7576@gmail.com', '1234', '김소영', '닉네임김소영', 
'01032127576', '제주도 제주시', '1000', DEFAULT, DEFAULT, DEFAULT, 2, DEFAULT);


INSERT INTO "MEMBER"
VALUES(3, 'youngmin', 'ssong9214@gmail.com', '1234', '송영민', '닉네임송영민',
'01092467636', '경기도 구리시', '3000', DEFAULT, DEFAULT, DEFAULT, 2, DEFAULT);

INSERT INTO "MEMBER"
VALUES(4, 'saem', 'saem.hong.95@gmail.com', '1234', '홍샘', '닉네임홍샘',
'01049647684', '서울시 성동구', '3000', DEFAULT, DEFAULT, DEFAULT, 2, DEFAULT);

INSERT INTO "MEMBER"
VALUES(5, 'soomin', 'soowagger@gmail.com', '1234', '신수민', '닉네임신수민',
'01031235555', '서울시 동대문구', '3000', DEFAULT, DEFAULT, DEFAULT, 2, DEFAULT);

INSERT INTO "MEMBER"
VALUES(6, 'test1', 'test1@test.com', '1234', '테스트유저1', '닉네임테스트유저1',
'01098765555', '서울시 동대문구', '3000', DEFAULT, DEFAULT, DEFAULT, 1, DEFAULT);

INSERT INTO "MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 'test2', 'test2@test.com', '1234', '테스트유저2', '닉네임테스트유저2',
'01012345555', '서울시 동대문구', '3000', DEFAULT, DEFAULT, DEFAULT, 1, DEFAULT);




-- 닉네임 : 신수민
-- soowagger@gmail.com

SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_NICKNAME = '신수민' 
AND MEMBER_EMAIL = 'soowagger@gmail.com'
AND MEMBER_DEL_FL = 'N';


SELECT MEMBER_ID, TO_CHAR(ENROLL_DATE, 'YY"."MM"."DD') ENROLL_DATE
FROM "MEMBER"
WHERE MEMBER_EMAIL = 'soowagger@gmail.com'
AND MEMBER_DEL_FL = 'N'
AND SOCIAL_LOGIN_TYPE = 'D';

SELECT MEMBER_ID, TO_CHAR(ENROLL_DATE, 'YY"."MM"."DD') ENROLL_DATE
FROM "MEMBER"
WHERE MEMBER_Tel = '01012341234'
AND MEMBER_DEL_FL = 'N'
AND SOCIAL_LOGIN_TYPE = 'D';

