
SELECT * FROM "MEMBER";

SELECT * FROM PARTNER;


SELECT * FROM EMAIL_AUTH;


-- 닉네임 : 신수민
-- soowagger@gmail.com

SELECT COUNT(*)
FROM "MEMBER"
WHERE MEMBER_NICKNAME = '신수민' 
AND MEMBER_EMAIL = 'soowagger@gmail.com'
AND MEMBER_DEL_FL = 'N';