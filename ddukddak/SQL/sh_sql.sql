SELECT * FROM MEMBER;

SELECT * FROM PARTNER;

SELECT * FROM PARTNER_TYPE;

INSERT INTO PARTNER VALUES (SEQ_PARTNER_NO.NEXTVAL, 'sh', 1234, '010-1111-2222', 'sh', '홍샘', 12345678, 1);

COMMIT;

SELECT * FROM BIG_CATEGORY;


SELECT PARTNER_NO, PARTNER_ID, PARTNER_PW, PARTNER_TEL, PARTNER_BUSINESS_NAME, PARTNER_CEO_NAME, PARTNER_BUSINESS_NUM, PARTNER_TYPE FROM PARTNER;

SELECT MEMBER_NO, MEMBER_ID, MEMBER_EMAIL, MEMBER_PW, MEMBER_NAME, MEMBER_NICKNAME, MEMBER_TEL, MEMBER_ADDR, MEMBER_POINT, PROFILE_IMG, ENROLL_DATE, MEMBER_DEL_FL, AUTHORITY, SOCIAL_LOGIN_TYPE FROM "MEMBER";

ALTER TABLE MEMBER RENAME COLUMN MEBMER_ADDR TO MEMBER_ADDR;

COMMIT;

SELECT * FROM COMMUNITY WHERE BOARD_CODE = 2;


SELECT * FROM UPLOAD_FILE ;

SELECT UPLOAD_IMG_PATH, UPLOAD_IMG_OG_NAME, UPLOAD_IMG_RENAME, UPLOAD_IMG_ORDER, CATEGORY
		FROM UPLOAD_FILE
		WHERE CATEGORY = 5
		ORDER BY UPLOAD_IMG_ORDER;


INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/board/', 'boardMainAd.png', 'boardMainAd.png', 1, 5, NULL, NULL, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/board/', 'boardMainAd2.png', 'boardMainAd2.png', 1, 5, NULL, NULL, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/board/', 'boardMainAd3.jpg', 'boardMainAd3.jpg', 1, 5, NULL, NULL, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/board/', 'boardMainAd4.jpg', 'boardMainAd4.jpg', 1, 5, NULL, NULL, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/board/', 'boardMainAd5.jpg', 'boardMainAd5.jpg', 1, 5, NULL, NULL, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/board/', 'boardMainAd6.png', 'boardMainAd6.png', 1, 5, NULL, NULL, NULL, NULL);

INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240608183317_00001.jpg','20240608183317_00001.jpg', 0, 4, NULL, 54, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240608183955_00006.jpeg','20240608183955_00006.jpeg', 0, 4, NULL, 55, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240608190007_00001.jpg','20240608190007_00001.jpg', 0, 4, NULL, 56, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611112017_00001.jpeg','20240611112017_00001.jpeg', 0, 4, NULL, 62, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611145651_00001.png','20240611145651_00001.png', 0, 4, NULL, 63, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611162021_00001.jpg','20240611162021_00001.jpg', 0, 4, NULL, 64, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611164125_00001.jpg','20240611164125_00001.jpg', 0, 4, NULL, 65, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611171325_00001.jpg','20240611171325_00001.jpg', 0, 4, NULL, 66, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240612011440_00001.webp','20240612011440_00001.webp', 0, 4, NULL, 70, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240612145753_00001.jpg','20240612145753_00001.jpg', 0, 4, NULL, 71, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', 'ex1_1.jpg','ex1_1.jpg', 0, 4, NULL, 72, NULL, NULL);

SELECT * FROM BOARD_TYPE;

SELECT * FROM COMMUNITY WHERE BOARD_CODE = 1 ORDER BY BOARD_WRITE_DATE DESC;

SELECT * FROM MEMBER;

SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, READ_COUNT, MEMBER_NO, MEMBER_ID, MEMBER_NICKNAME, UPLOAD_IMG_PATH || UPLOAD_IMG_RENAME AS THUMBNAIL, BOARD_CODE FROM COMMUNITY JOIN MEMBER USING(MEMBER_NO) JOIN UPLOAD_FILE USING(BOARD_NO) WHERE UPLOAD_IMG_ORDER = 0 AND MEMBER_DEL_FL = 'N'ORDER BY BOARD_WRITE_DATE;

SELECT COUNT(*) FROM BOARD_LIKE WHERE BOARD_NO = 1;

INSERT INTO COMMUNITY VALUES (SEQ_BOARD_NO.NEXTVAL, '노하우 테스트1', '노하웉 내용 테스트1', DEFAULT, NULL, DEFAULT, DEFAULT, 4, 2);
INSERT INTO COMMUNITY VALUES (SEQ_BOARD_NO.NEXTVAL, '노하우 테스트2', '노하웉 내용 테스트2', DEFAULT, NULL, DEFAULT, DEFAULT, 4, 2);
INSERT INTO COMMUNITY VALUES (SEQ_BOARD_NO.NEXTVAL, '노하우 테스트3', '노하웉 내용 테스트3', DEFAULT, NULL, DEFAULT, DEFAULT, 4, 2);


SELECT BOARD_NO, BOARD_TITLE, BOARD_CONTENT, READ_COUNT, MEMBER_NO, MEMBER_ID, MEMBER_NICKNAME, UPLOAD_IMG_PATH || UPLOAD_IMG_RENAME AS THUMBNAIL, BOARD_CODE
		FROM COMMUNITY 
		JOIN MEMBER USING(MEMBER_NO) 
		JOIN UPLOAD_FILE USING(BOARD_NO) 
		WHERE UPLOAD_IMG_ORDER = 0 AND MEMBER_DEL_FL = 'N'
		ORDER BY BOARD_WRITE_DATE DESC;
		
	
	
SELECT * FROM BOARD_LIKE WHERE BOARD_NO = 85;

INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611164125_00002.jpg','20240611164125_00002.jpg', 1, 4, NULL, 71, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611164125_00003.jpg','20240611164125_00003.jpg', 2, 4, NULL, 71, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240611164125_00004.jpg','20240611164125_00004.jpg', 3, 4, NULL, 71, NULL, NULL);
INSERT INTO UPLOAD_FILE VALUES (SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/tip/', '20240610174118_00008.jpg','20240610174118_00008.jpg', 1, 4, NULL, 72, NULL, NULL);
SELECT * FROM UPLOAD_FILE WHERE BOARD_NO = 65;

COMMIT;


	
SELECT * FROM UPLOAD_FILE WHERE BOARD_NO = 85;
	
SELECT * FROM "COMMENT";

SELECT C.BOARD_NO, 
           C.BOARD_TITLE, 
           C.BOARD_CONTENT, 
           C.READ_COUNT, 
           M.MEMBER_NO, 
           M.MEMBER_ID, 
           M.MEMBER_NICKNAME, 
           U.UPLOAD_IMG_PATH || U.UPLOAD_IMG_RENAME AS THUMBNAIL, 
           C.BOARD_CODE, 
           (SELECT COUNT(*) 
              FROM BOARD_LIKE L 
             WHERE L.BOARD_NO = C.BOARD_NO) AS LIKE_COUNT 
      FROM COMMUNITY C
      JOIN MEMBER M ON C.MEMBER_NO = M.MEMBER_NO 
      LEFT JOIN UPLOAD_FILE U ON C.BOARD_NO = U.BOARD_NO AND U.UPLOAD_IMG_ORDER = 0 
     WHERE C.BOARD_DEL_FL = 'N' 
       AND M.MEMBER_DEL_FL = 'N' 
     ORDER BY C.BOARD_WRITE_DATE DESC;
     
    
SELECT * FROM COMMUNITY WHERE BOARD_CODE = 1 ORDER BY BOARD_WRITE_DATE DESC;

SELECT * FROM PRODUCT;

SELECT p.PRODUCT_NO, PRODUCT_NAME, PRODUCT_PRICE, TO_CHAR(PRODUCT_CREATE_DATE, 'YYYY-MM-DD') AS PRODUCT_CREATE_DATE, PRODUCT_UPDATE_DATE,
		PRODUCT_FL, CATEGORY_NO, CATEGORY_NAME, PARTNER_NO, UPLOAD_IMG_NO, UPLOAD_IMG_PATH, UPLOAD_IMG_OG_NAME, UPLOAD_IMG_RENAME,
		UPLOAD_IMG_ORDER, BIG_CATEGORY_NO, BIG_CATEGORY_NAME
		FROM PRODUCT p
		JOIN UPLOAD_FILE uf ON(p.PRODUCT_NO = uf.PRODUCT_NO)
		NATURAL JOIN CATEGORY c
		NATURAL JOIN BIG_CATEGORY b
		WHERE UPLOAD_IMG_ORDER = 0
		AND PRODUCT_FL = 'N'
		AND PRODUCT_UPDATE_DATE IS NULL
		AND CATEGORY_NO = 1
		ORDER BY p.PRODUCT_NO DESC;
	
	
-- N -> 상품 진열 / Y -> 상품 대기 / S -> 상품 중지

	
	SELECT p.PRODUCT_NO, PRODUCT_NAME, PRODUCT_PRICE, TO_CHAR(PRODUCT_CREATE_DATE, 'YYYY-MM-DD') AS PRODUCT_CREATE_DATE, PRODUCT_UPDATE_DATE,
		PRODUCT_FL, CATEGORY_NO, CATEGORY_NAME, PARTNER_NO, UPLOAD_IMG_NO, UPLOAD_IMG_PATH, UPLOAD_IMG_OG_NAME, UPLOAD_IMG_RENAME,
		UPLOAD_IMG_ORDER, BIG_CATEGORY_NO, BIG_CATEGORY_NAME
		FROM PRODUCT p
		JOIN UPLOAD_FILE uf ON(p.PRODUCT_NO = uf.PRODUCT_NO)
		NATURAL JOIN CATEGORY c
		NATURAL JOIN BIG_CATEGORY b
		WHERE UPLOAD_IMG_ORDER = 0
		AND PRODUCT_FL = 'N'
		AND PRODUCT_UPDATE_DATE IS NULL
		AND BIG_CATEGORY_NO = 1
		ORDER BY p.PRODUCT_NO DESC;
		
	
	SELECT p.PRODUCT_NO, PRODUCT_NAME, PRODUCT_PRICE, TO_CHAR(PRODUCT_CREATE_DATE, 'YYYY-MM-DD') AS PRODUCT_CREATE_DATE, PRODUCT_UPDATE_DATE,
		PRODUCT_FL, CATEGORY_NO, CATEGORY_NAME, PARTNER_NO, UPLOAD_IMG_NO, UPLOAD_IMG_PATH, UPLOAD_IMG_OG_NAME, UPLOAD_IMG_RENAME,
		UPLOAD_IMG_ORDER, BIG_CATEGORY_NO, BIG_CATEGORY_NAME
		FROM PRODUCT p
		JOIN UPLOAD_FILE uf ON(p.PRODUCT_NO = uf.PRODUCT_NO)
		NATURAL JOIN CATEGORY c
		NATURAL JOIN BIG_CATEGORY b
		WHERE UPLOAD_IMG_ORDER = 0
		AND PRODUCT_FL = 'N'
		AND CATEGORY_NO = 1
		ORDER BY p.PRODUCT_NO DESC;
		
	SELECT COUNT(*)
		FROM PRODUCT
		NATURAL JOIN CATEGORY
		NATURAL JOIN BIG_CATEGORY
		WHERE PRODUCT_FL = 'N'
		AND PRODUCT_UPDATE_DATE IS NULL
		AND BIG_CATEGORY_NO = 1;
		
	SELECT p.PRODUCT_NO, PRODUCT_NAME, PRODUCT_PRICE, TO_CHAR(PRODUCT_CREATE_DATE, 'YYYY-MM-DD') AS PRODUCT_CREATE_DATE, PRODUCT_UPDATE_DATE,
		PRODUCT_FL, CATEGORY_NO, CATEGORY_NAME, PARTNER_NO, UPLOAD_IMG_NO, UPLOAD_IMG_PATH, UPLOAD_IMG_OG_NAME, UPLOAD_IMG_RENAME,
		UPLOAD_IMG_ORDER, BIG_CATEGORY_NO, BIG_CATEGORY_NAME
		FROM PRODUCT p
		JOIN UPLOAD_FILE uf ON(p.PRODUCT_NO = uf.PRODUCT_NO)
		NATURAL JOIN CATEGORY c
		NATURAL JOIN BIG_CATEGORY b
		WHERE UPLOAD_IMG_ORDER = 0
		AND PRODUCT_FL = 'N'
		AND PRODUCT_UPDATE_DATE IS NULL
		ORDER BY p.PRODUCT_NO DESC;
		
SELECT * FROM ORDERS;
	
	SELECT COUNT(*)
		FROM PRODUCT
		WHERE PRODUCT_FL = 'N'
		AND PRODUCT_UPDATE_DATE IS NULL;
		
	
	SELECT p.PRODUCT_NO, PRODUCT_NAME, PRODUCT_PRICE, TO_CHAR(PRODUCT_CREATE_DATE, 'YYYY-MM-DD') AS PRODUCT_CREATE_DATE,
		TO_CHAR(PRODUCT_UPDATE_DATE, 'YYYY-MM-DD') AS PRODUCT_UPDATE_DATE,
		PRODUCT_FL, CATEGORY_NO, CATEGORY_NAME, PARTNER_NO, UPLOAD_IMG_NO, UPLOAD_IMG_PATH, UPLOAD_IMG_OG_NAME, UPLOAD_IMG_RENAME,
		UPLOAD_IMG_ORDER, BIG_CATEGORY_NO, BIG_CATEGORY_NAME
		FROM PRODUCT p
		JOIN UPLOAD_FILE uf ON(p.PRODUCT_NO = uf.PRODUCT_NO)
		NATURAL JOIN CATEGORY c
		NATURAL JOIN BIG_CATEGORY b
		WHERE UPLOAD_IMG_ORDER = 0
		AND PRODUCT_UPDATE_DATE IS NOT NULL
		ORDER BY p.PRODUCT_NO DESC;
		
	
	
SELECT * FROM PARTNER WHERE PARTNER_TYPE = 1;
ROLLBACK;

COMMIT;

INSERT INTO PARTNER VALUES (SEQ_PARTNER_NO.NEXTVAL, 'test1', '$2a$10$YXSsczaCIZZkKbF17AdY1OBukX2ou43eBVGel7WyJD2ex87FeAiSC', '01011112222', '(주)asd', '김테스트', '1678901235', DEFAULT, 'N', 1, 1);
INSERT INTO PARTNER VALUES (SEQ_PARTNER_NO.NEXTVAL, 'test2', '$2a$10$YXSsczaCIZZkKbF17AdY1OBukX2ou43eBVGel7WyJD2ex87FeAiSC', '01011112222', '(주)123124', '김테스트1', '1678901235', DEFAULT, 'N', 1, 1);
INSERT INTO PARTNER VALUES (SEQ_PARTNER_NO.NEXTVAL, 'test3', '$2a$10$YXSsczaCIZZkKbF17AdY1OBukX2ou43eBVGel7WyJD2ex87FeAiSC', '01011112222', '(주)asfag', '김테스트2', '1678901235', DEFAULT, 'N', 1, 1);
INSERT INTO PARTNER VALUES (SEQ_PARTNER_NO.NEXTVAL, 'test4', '$2a$10$YXSsczaCIZZkKbF17AdY1OBukX2ou43eBVGel7WyJD2ex87FeAiSC', '01011112222', '(주)213gsdg', '김테스트3', '1678901235', DEFAULT, 'N', 1, 1);
INSERT INTO PARTNER VALUES (SEQ_PARTNER_NO.NEXTVAL, 'test5', '$2a$10$YXSsczaCIZZkKbF17AdY1OBukX2ou43eBVGel7WyJD2ex87FeAiSC', '01011112222', '(주)1245rtgd', '김테스트4', '1678901235', DEFAULT, 'N', 1, 1);


SELECT * FROM PORTFOLIO
NATURAL JOIN PROJECT
NATURAL JOIN PROJECT_INFO
NATURAL JOIN UPLOAD_FILE
WHERE PORTFOLIO_NO = 1
ORDER BY PROJECT_NO;

SELECT * FROM PORTFOLIO;
SELECT * FROM PROJECT;
SELECT * FROM PROJECT_INFO;

INSERT INTO PORTFOLIO VALUES (SEQ_PORTFOLIO_NO.NEXTVAL, '좋은 가구 팝니다', 'www.goodfurniture.com', 2);

INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, '외국계 글로벌 데비마이어 목동 사옥', 1);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, '서초구 M&C', 1);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, '5MINLAB', 1);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, 'LUYWORK', 1);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, '비스타릿', 1);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, 'IZEX', 1);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, 'ACE PLANET', 1);

INSERT INTO PROJECT_INFO VALUES (SEQ_PROJECT_INFO_NO.NEXTVAL, '주택', '리모델링', 43, 100000000, '서울시 양천구', '2020', '4인', 'Y', 1);
INSERT INTO PROJECT_INFO VALUES (SEQ_PROJECT_INFO_NO.NEXTVAL, '주택', '리모델링', 55, 250000000, '서울시 서초구', '2021', '5인', 'N', 2);
INSERT INTO PROJECT_INFO VALUES (SEQ_PROJECT_INFO_NO.NEXTVAL, '주택', '리모델링', 32, 130000000, '서울시 서대문구', '2019', '2인', 'N', 3);
INSERT INTO PROJECT_INFO VALUES (SEQ_PROJECT_INFO_NO.NEXTVAL, '주택', '리모델링', 19, 83000000, '안양시 동안구', '2022', '2인', 'N', 4);
INSERT INTO PROJECT_INFO VALUES (SEQ_PROJECT_INFO_NO.NEXTVAL, '주택', '리모델링', 60, 320000000, '안양시 동안구', '2017', '4인', 'N', 5);
INSERT INTO PROJECT_INFO VALUES (SEQ_PROJECT_INFO_NO.NEXTVAL, '주택', '리모델링', 52, 410000000, '안양시 동안구', '2021', '3인', 'N', 6);
INSERT INTO PROJECT_INFO VALUES (SEQ_PROJECT_INFO_NO.NEXTVAL, '주택', '리모델링', 12, 30000000, '안양시 동안구', '2018', '1인', 'N', 7);

INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex_main.jpg', 'portfolio_ex_main.jpg', 0, 2, NULL, NULL, NULL,1);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex_1.jpg', 'portfolio_ex_1.jpg', 1, 2, NULL, NULL, NULL,1);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex_2.jpg', 'portfolio_ex_2.jpg', 2, 2, NULL, NULL, NULL,1);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex_3.jpg', 'portfolio_ex_3.jpg', 3, 2, NULL, NULL, NULL,1);

INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex1_main.jpg', 'portfolio_ex1_main.jpg', 0, 2, NULL, NULL, NULL,2);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex2_1.jpg', 'portfolio_ex2_1.jpg', 1, 2, NULL, NULL, NULL,2);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex2_2.jpg', 'portfolio_ex2_2.jpg', 2, 2, NULL, NULL, NULL,2);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex2_3.jpg', 'portfolio_ex2_3.jpg', 3, 2, NULL, NULL, NULL,2);

INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex3_main.jpg', 'portfolio_ex3_main.jpg', 0, 2, NULL, NULL, NULL,3);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex3_1.jpg', 'portfolio_ex3_1.jpg', 1, 2, NULL, NULL, NULL,2);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex3_2.jpg', 'portfolio_ex3_2.jpg', 2, 2, NULL, NULL, NULL,2);
INSERT INTO UPLOAD_FILE VALUES(SEQ_UPLOAD_IMG_NO.NEXTVAL, '/images/interior/', 'portfolio_ex3_3.jpg', 'portfolio_ex3_3.jpg', 3, 2, NULL, NULL, NULL,2);

INSERT INTO PORTFOLIO VALUES (SEQ_PORTFOLIO_NO.NEXTVAL, '좋은 가구 팝니다', 'www.goodfurniture.com', 2);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, '외국계 글로벌 데비마이어 목동 사옥', 1);
INSERT INTO PROJECT VALUES (SEQ_PROJECT_NO.NEXTVAL, '서초구 M&C', 1);

COMMIT;

SELECT * FROM UPLOAD_FILE;

SELECT PARTNER_NO, PARTNER_TEL, PARTNER_BUSINESS_NAME, PORTFOLIO_NO  FROM PARTNER NATURAL JOIN PORTFOLIO NATURAL JOIN PROJECT_INFO WHERE PARTNER_TYPE = 1 AND MAIN_PROJECT_FL = 'Y';
SELECT PARTNER_NO, PARTNER_TEL, PARTNER_BUSINESS_NAME FROM PARTNER WHERE PARTNER_TYPE = 1;

SELECT PARTNER_NO, PARTNER_TEL, PARTNER_BUSINESS_NAME, PORTFOLIO_NO  FROM PARTNER NATURAL JOIN 
		PORTFOLIO NATURAL JOIN PROJECT_INFO WHERE PARTNER_TYPE = 1 AND MAIN_PROJECT_FL = 'Y';

ALTER TABLE PROJECT_INFO ADD PROJECT_CONTENT NVARCHAR2(200);

SELECT * FROM PARTNER  WHERE PARTNER_TYPE = 1;

SELECT PARTNER_NO, PARTNER_TEL, PARTNER_BUSINESS_NAME, PORTFOLIO_NO, PROJECT_NAME, HOME_LINK, 
PORTFOLIO_DETAIL, HOUSING_TYPE, WORK_FORM, WORK_AREA, 
CONSTRUCTION_COST, REGION, CONSTRUCTION_YEAR, FAMILY_SIZE, 
MAIN_PROJECT_FL FROM PARTNER NATURAL JOIN 
		PORTFOLIO NATURAL JOIN PROJECT_INFO 
		NATURAL JOIN PROJECT
		WHERE PARTNER_TYPE = 1 AND MAIN_PROJECT_FL = 'Y'
		AND PORTFOLIO_NO = 1;
		
