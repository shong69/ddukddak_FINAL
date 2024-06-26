package com.ddukddak.partner.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.dto.QNA;

public interface ProductService {

	/** 재고등록 재고상품 조회
	 * @param cp 
	 * @param memberNo 
	 * @param cp2 
	 * @param cp2 
	 * @return
	 */
	Map<String, Object> selectCreateList(int mainSort, int sort, int cp, int memberNo);

	/** 재고상품 판매등록
	 * @param selectedValues
	 * @return
	 */
	int sellApplyProduct(List<Object> selectedValues);
	

	/** 재고상품 삭제
	 * @param productNo
	 * @return
	 */
	int delProduct(int productNo);

	/** 대분류에 따른 소분류 조회
	 * @param selectedCategory
	 * @return
	 */
	List<Category> selectSmallCategory(int selectedCategory);

	/** 상품 재고등록
	 * @param map
	 * @return
	 */
	int registProduct(Map<String, Object> map);

	/** 상품 재고등록 이미지 등록
	 * @param imgList
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int insertImg(int smallCategory, List<MultipartFile> imgList) throws IllegalStateException, IOException;

	/** 상품 재고등록 옵션 등록
	 * @param item1
	 * @param list
	 * @param list2
	 * @return
	 */
	int insertOpion(String item1, List<String> list, List<String> list2);

	/** 판매관리 상품 조회
	 * @param mainSort
	 * @param sort
	 * @param status 
	 * @param cp
	 * @param memberNo 
	 * @return
	 */
	Map<String, Object> selectApplyList(int mainSort, int sort, String status, int cp, int memberNo);

	/** 판매상태 변경
	 * @param map
	 * @return
	 */
	int changeStatus(Map<String, Object> map);

	/** 판매등록 상품조회
	 * @param productNo
	 * @return
	 */
	Product selectOne(int productNo);

	/** 판매등록 상품 이미지 불러오기
	 * @param productNo
	 * @return
	 */
	List<ProductImg> selectImg(int productNo);

	/** 판매등록 상품 옵션명 불러오기
	 * @param productNo
	 * @return
	 */
	List<ProductOption> seletOptionName(int productNo);

	/** 판매등록 상품 옵션 불러오기
	 * @param productNo
	 * @return
	 */
	List<ProductOption> selectOpion(int productNo);

	/** 상세사진 삭제
	 * @param imgNo
	 * @return
	 */
	int delImg(int imgNo);

	/** 상품 판매등록
	 * @param map
	 * @return
	 */
	int updateRegistProduct(Map<String, Object> map);

	/** 이미지 판매등록
	 * @param smallCategory
	 * @param smallCategory2 
	 * @param imgList
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int updateInsertImg(String proudctNo, String smallCategory, List<MultipartFile> imgList) throws IllegalStateException, IOException;
	
	/** 설명사진 판매등록
	 * @param productNo
	 * @param smallCategory
	 * @param explainImgs
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int updateInsertEnplainImg(String productNo, String smallCategory, List<MultipartFile> explainImgs) throws IllegalStateException, IOException;

	/** 대표사진 업데이트
	 * @param thumbnailImg
	 * @param productNo 
	 * @param smallCategory 
	 * @return
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	int updateThumbnailImg(MultipartFile thumbnailImg, String productNo, String smallCategory) throws IllegalStateException, IOException;

	/** 옵션 비우기
	 * @param productNo
	 * @return
	 */
	int delOption(String productNo);

	/** 새 옵션 추가
	 * @param string
	 * @param list
	 * @param list2
	 * @param productNo
	 * @return
	 */
	int insertOpion2(String string, List<String> list, List<String> list2, String productNo);

	/** 수정상품 등록
	 * @param map
	 * @return
	 */
	int modifyRegistProduct(Map<String, Object> map);

	/** 접수관리 조회하기
	 * @param partnerNo
	 * @param mainSort
	 * @param sort
	 * @param status
	 * @param cp
	 * @return
	 */
	Map<String, Object> selectReceiptList(int partnerNo, int mainSort, int sort, String status, int cp);

	/** 문의내역 리스트 가져오기
	 * @return
	 */
	Map<String, Object> selectQna(int cp);

	/** 문의답변 넣기
	 * @param map
	 * @return
	 */
	int insertQnaAnswer(Map<String, Object> obj);

	/** 접수 완료
	 * @param map
	 * @return
	 */
	int acceptReceipt(Map<String, Object> map);

	/** 접수 거절
	 * @param map
	 * @return
	 */
	int rejectReceipt(Map<String, Object> map);

	Map<String, Object> selectShipmentList(int partnerNo, int mainSort, int sort, String status, int cp);

	int acceptShipment(Map<String, Object> map);

	int rejectShipment(Map<String, Object> map);

	Map<String, Object> selectCompleteList(int partnerNo, int mainSort, int sort, String status, int cp);

	/** 설명사진 지우기
	 * @param productNo
	 * @return
	 */
	int deleteExplainImg(String productNo);






}
