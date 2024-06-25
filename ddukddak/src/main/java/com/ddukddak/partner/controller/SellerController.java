package com.ddukddak.partner.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.dto.ProductImg;
import com.ddukddak.ecommerce.model.dto.ProductOption;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.service.ProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequestMapping("partner/seller")
@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("{loginPartnerMember}")
public class SellerController {
	
	private final ProductService service;
	private final eCommerceService eCommerceService;

	// 재고관리
	@GetMapping("product/create")
	public String ProductCreate(
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			@RequestParam(value="mainSort", required=false, defaultValue="0") int mainSort,
			@RequestParam(value="sort", required=false, defaultValue="0") int sort,
			@SessionAttribute("loginPartnerMember") Partner loginPartnerMember, 
			RedirectAttributes ra,
			Model model) {
		
		if (loginPartnerMember.getPartnerType() != 2) {
            ra.addFlashAttribute("message", "접근 제한된 서비스 입니다");
            return "redirect:/partner/main";
        } 
		
		int memberNo = loginPartnerMember.getPartnerNo();
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = eCommerceService.selectSmallCategory();
		
		model.addAttribute("smallCategoryList", smallCategoryList);
		
		// 재고상품 조회
		Map<String, Object> map = service.selectCreateList(mainSort, sort, cp, memberNo);
		
		model.addAttribute("createList", map.get("createList"));
		model.addAttribute("pagination", map.get("pagination"));

        
        return "partner/seller/product/create";
	}
	
	// 판매등록
	@PutMapping("product/sellApplyProduct")
	@ResponseBody
	public int sellApplyProduct(@RequestBody List<Object> selectedValues) {
		
		int result = service.sellApplyProduct(selectedValues);
		
		return result;
	}
	
	//상품 삭제
	@PostMapping("product/delProduct")
	@ResponseBody
	public int delProduct(@RequestBody int productNo) {
		
		int result = service.delProduct(productNo);
		
		return result;
	}
	
	@PostMapping("product/selectSmallCategory")
	@ResponseBody
	public List<Category> selectSmallCategory(@RequestBody int selectedCategory) {
		
		List<Category> smallCategoryList = service.selectSmallCategory(selectedCategory);
		
		return smallCategoryList;
	}

	// 재고등록
	@PostMapping("product/create")
	@ResponseBody
	public int registMyHouse(@RequestParam ("productName") String productName,
								@RequestParam ("smallCategory") int smallCategory,
								@RequestParam ("productPrice") int productPrice,
								@RequestParam ("thumbnailImg") MultipartFile thumbnailImg,
								@RequestParam (name="subImgs", required = false) List<MultipartFile> subImgs,
								@RequestParam (name = "optionName", required = false) List<String> optionName,
								@RequestParam (name = "optionContent", required = false) List<String> optionContent,
								@RequestParam (name = "optionCount", required = false) List<String> optionCount,
								@SessionAttribute("loginPartnerMember") Partner loginPartnerMember) throws IOException {

		int memberNo = loginPartnerMember.getPartnerNo();
	
		log.info("object : " + productName + smallCategory + productPrice);	
		log.info("thumbnailImg : " + thumbnailImg);	
		log.info("subImgs : " + subImgs);	
		log.info("optionName : " + optionName);
		log.info("optionContent : " + optionContent);
		log.info("optionCount : " + optionCount);

		// PRODUCT 테이블 삽입
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("memberNo", memberNo);
		map.put("productName", productName);
		map.put("smallCategoryNo", smallCategory);
		map.put("productPrice", productPrice);
		
		int result1 = service.registProduct(map);
		

		// UPLOAD_FILE 삽입

		List<MultipartFile> imgList = new ArrayList<>(subImgs);
		imgList.add(0, thumbnailImg); // 다시 mainImg 를 배열 0번째 자리에 추가
		
		log.info("imgList : " + imgList);

		int result2 = service.insertImg(smallCategory, imgList);

		
		
		int result3 = 0;
		// 옵션
		if(optionContent != null) {
			List<List<String>> resultList1 = new ArrayList<>();
			List<String> optionContentList = new ArrayList<>();
			
			// 옵션값 리스트 나누기
			for (String item : optionContent) {
				if ("/".equals(item)) {
					resultList1.add(optionContentList);
					optionContentList = new ArrayList<>();
				} else {
					optionContentList.add(item);
				}
			}
			
			resultList1.add(optionContentList);
			
			// 결과 출력
			for (List<String> list : resultList1) {
				log.info("resultList1 : " + list);
			}
			
			// 옵션재고 리스트 나누기
			List<List<String>> resultList2 = new ArrayList<>();
			List<String> optionCountList = new ArrayList<>();
			
			for (String item : optionCount) {
				if ("/".equals(item)) {
					resultList2.add(optionCountList);
					optionCountList = new ArrayList<>();
				} else {
					optionCountList.add(item);
				}
			}
			
			resultList2.add(optionCountList);
			
			// 결과 출력
			for (List<String> list : resultList2) {
				log.info("resultList2 : " + list);
			}
			
			
			// OPTION 삽입
			
			for (int i = 0; i < resultList1.size(); i ++) {
				if(!resultList1.get(i).isEmpty()) {
					result3 += service.insertOpion(optionName.get(i), resultList1.get(i), resultList2.get(i));
				}
			}
			
		}


		return result1 + result2 + result3;
	}

	

	// 판매관리
	@GetMapping("product/apply")
	public String ProductApply(
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			@RequestParam(value="mainSort", required=false, defaultValue="0") int mainSort,
			@RequestParam(value="sort", required=false, defaultValue="0") int sort,
			@RequestParam(value="status", required=false, defaultValue="A") String status,
			@SessionAttribute("loginPartnerMember") Partner loginPartnerMember,
			Model model
			) {
		
		int memberNo = loginPartnerMember.getPartnerNo();
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = eCommerceService.selectSmallCategory();
		
		model.addAttribute("smallCategoryList", smallCategoryList);
		
		// 재고상품 조회
		Map<String, Object> map = service.selectApplyList(mainSort, sort, status, cp, memberNo);
		
		model.addAttribute("applyList", map.get("applyList"));
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("status", status);
				
		log.info("status : " + status);
				
		return "partner/seller/product/apply";
	}
	
	// 판매상태 변경
	@PostMapping("product/changeStatus")
	@ResponseBody
	public int changeStatus(@RequestBody Map<String, Object> map) {
		
		log.info("map : " + map);
		
		int result = service.changeStatus(map);
		
		return result;
	}
	
	// 판매등록상품
	@PostMapping("product/applyProduct")
	public String ProductApplyProduct(@RequestParam ("productNo") int productNo,
									@RequestParam(value="mainSort", required=false, defaultValue="0") int mainSort,
									@RequestParam(value="sort", required=false, defaultValue="0") int sort,
									@RequestParam("status") String status,
										Model model) {
		

		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		
		// 상품넘버 불러오기
		Product product = service.selectOne(productNo);
		
		model.addAttribute("product", product);
		
		// 옵션 불러오기
		List<ProductOption> optionName = service.seletOptionName(productNo);
		List<ProductOption> options = service.selectOpion(productNo);
		
		model.addAttribute("optionName", optionName);
		model.addAttribute("option", options);
		
		// 이미지 불러오기
		List<ProductImg> imgs = service.selectImg(productNo);
		
		model.addAttribute("img", imgs);
		
		// 수정/등록 상태 보내기
		model.addAttribute("status", status);
		
		
		return "partner/seller/product/applyProduct";
	}
	
	// 상세사진 삭제
	@DeleteMapping("product/delImgs")
	@ResponseBody
	public int delImg(@RequestBody int imgNo) {
		
		return service.delImg(imgNo);
	}
	
	// 판매등록
	@PostMapping("product/applySellProduct")
	@ResponseBody
	public int ProductApplySellProduct(@RequestParam("productName") String productName,
										@RequestParam("productNo") String productNo,
										@RequestParam(name="bigCategory") String bigCategory,
										@RequestParam(name="smallCategory") String smallCategory,
										@RequestParam ("productPrice") int productPrice,
										@RequestParam (name="mainImg", required = false) MultipartFile thumbnailImg,
										@RequestParam (name="subImgs", required = false) List<MultipartFile> subImgs,
										@RequestParam (name = "optionName", required = false) List<String> optionName,
										@RequestParam (name = "optionContent", required = false) List<String> optionContent,
										@RequestParam (name = "optionCount", required = false) List<String> optionCount,
										@SessionAttribute("loginPartnerMember") Partner loginPartnerMember) throws IllegalStateException, IOException  {
		
		int memberNo = loginPartnerMember.getPartnerNo();
		
		log.info("memterNo : " + memberNo);
		
		log.info("productName : " + productName);
		log.info("bigCategory : " + bigCategory);
		log.info("smallCategory : " + smallCategory);
		log.info("productPrice : " + productPrice);
		log.info("optionName : " + optionName);
		log.info("optionContent : " + optionContent);
		log.info("optionCount : " + optionCount);
		
		// PRODUCT 테이블 삽입
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("productNo", productNo);
		map.put("memberNo", memberNo);
		map.put("productName", productName);
		map.put("smallCategoryNo", smallCategory);
		map.put("productPrice", productPrice);
		
		int result1 = service.updateRegistProduct(map);
		

		// UPLOAD_FILE 삽입

		// 대표사진
		int result21 = service.updateThumbnailImg(thumbnailImg, productNo, smallCategory);
		
		// 상세사진
		List<MultipartFile> imgList = new ArrayList<>(subImgs);
		
		log.info("imgList : " + imgList);

		int result2 = service.updateInsertImg(productNo, smallCategory, imgList);

		// 옵션 비우기
		int result4 = service.delOption(productNo);
		
		int result3 = 0;
		// 옵션
		if(optionContent != null) {
			List<List<String>> resultList1 = new ArrayList<>();
			List<String> optionContentList = new ArrayList<>();
			
			// 옵션값 리스트 나누기
			for (String item : optionContent) {
				if ("/".equals(item)) {
					resultList1.add(optionContentList);
					optionContentList = new ArrayList<>();
				} else {
					optionContentList.add(item);
				}
			}
			
			resultList1.add(optionContentList);
			
			// 결과 출력
			for (List<String> list : resultList1) {
				log.info("resultList1 : " + list);
			}
			
			// 옵션재고 리스트 나누기
			List<List<String>> resultList2 = new ArrayList<>();
			List<String> optionCountList = new ArrayList<>();
			
			for (String item : optionCount) {
				if ("/".equals(item)) {
					resultList2.add(optionCountList);
					optionCountList = new ArrayList<>();
				} else {
					optionCountList.add(item);
				}
			}
			
			resultList2.add(optionCountList);
			
			// 결과 출력
			for (List<String> list : resultList2) {
				log.info("resultList2 : " + list);
			}
			
			
			// OPTION 삽입
			
			for (int i = 0; i < resultList1.size(); i ++) {
				if(!resultList1.get(i).isEmpty()) {
					result3 += service.insertOpion2(optionName.get(i), resultList1.get(i), resultList2.get(i), productNo);
				}
			}
			
		}


		return result1;
		
	}
	
	// 판매상품 수정
	@PostMapping("product/modifySellProduct")
	@ResponseBody
	public int ProductModifySellProduct(@RequestParam("productName") String productName,
			@RequestParam("productNo") String productNo,
			@RequestParam(name="bigCategory") String bigCategory,
			@RequestParam(name="smallCategory") String smallCategory,
			@RequestParam ("productPrice") int productPrice,
			@RequestParam (name="mainImg", required = false) MultipartFile thumbnailImg,
			@RequestParam (name="subImgs", required = false) List<MultipartFile> subImgs,
			@RequestParam (name = "optionName", required = false) List<String> optionName,
			@RequestParam (name = "optionContent", required = false) List<String> optionContent,
			@RequestParam (name = "optionCount", required = false) List<String> optionCount,
			@SessionAttribute("loginPartnerMember") Partner loginPartnerMember) throws IllegalStateException, IOException  {
		
		int memberNo = loginPartnerMember.getPartnerNo();
		
		log.info("memterNo : " + memberNo);
		
		log.info("productName : " + productName);
		log.info("bigCategory : " + bigCategory);
		log.info("smallCategory : " + smallCategory);
		log.info("productPrice : " + productPrice);
		log.info("optionName : " + optionName);
		log.info("optionContent : " + optionContent);
		log.info("optionCount : " + optionCount);
		
		// PRODUCT 테이블 삽입
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("productNo", productNo);
		map.put("memberNo", memberNo);
		map.put("productName", productName);
		map.put("smallCategoryNo", smallCategory);
		map.put("productPrice", productPrice);
		
		int result1 = service.modifyRegistProduct(map);
		
		
		// UPLOAD_FILE 삽입
		
		// 대표사진
		int result21 = service.updateThumbnailImg(thumbnailImg, productNo, smallCategory);
		
		// 상세사진
		List<MultipartFile> imgList = new ArrayList<>(subImgs);
		
		log.info("subImgs : " + subImgs);
		
		int result2 = service.updateInsertImg(productNo, smallCategory, imgList);
		
		// 옵션 비우기
		int result4 = service.delOption(productNo);
		
		int result3 = 0;
		// 옵션
		if(optionContent != null) {
			List<List<String>> resultList1 = new ArrayList<>();
			List<String> optionContentList = new ArrayList<>();
			
			// 옵션값 리스트 나누기
			for (String item : optionContent) {
				if ("/".equals(item)) {
					resultList1.add(optionContentList);
					optionContentList = new ArrayList<>();
				} else {
					optionContentList.add(item);
				}
			}
			
			resultList1.add(optionContentList);
			
			// 결과 출력
			for (List<String> list : resultList1) {
				log.info("resultList1 : " + list);
			}
			
			// 옵션재고 리스트 나누기
			List<List<String>> resultList2 = new ArrayList<>();
			List<String> optionCountList = new ArrayList<>();	
			
			for (String item : optionCount) {
				if ("/".equals(item)) {
					resultList2.add(optionCountList);
					optionCountList = new ArrayList<>();
				} else {
					optionCountList.add(item);
				}
			}
			
			resultList2.add(optionCountList);
			
			// 결과 출력
			for (List<String> list : resultList2) {
				log.info("resultList2 : " + list);
			}
			
			
			// OPTION 삽입
			
			for (int i = 0; i < resultList1.size(); i ++) {
				if(!resultList1.get(i).isEmpty()) {
					result3 += service.insertOpion2(optionName.get(i), resultList1.get(i), resultList2.get(i), productNo);
				}
			}
			
		}
		
		
		return result1;
		
	}
	
	@GetMapping("product/receipt")
	public String ProductReceipt(@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			@RequestParam(value="mainSort", required=false, defaultValue="0") int mainSort,
			@RequestParam(value="sort", required=false, defaultValue="0") int sort,
			@RequestParam(value="status", required=false, defaultValue="A") String status,
			Model model,
			@SessionAttribute("loginPartnerMember") Partner loginPartnerMember) {
		
		int partnerNo = loginPartnerMember.getPartnerNo();
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
//		log.info("categoryList : " + categoryList);
		
		model.addAttribute("categoryList", categoryList);
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = eCommerceService.selectSmallCategory();
		
//		log.info("smallCategoryList : " + smallCategoryList);

		model.addAttribute("smallCategoryList", smallCategoryList);
		
		// 재고상품 조회
		Map<String, Object> map = service.selectReceiptList(partnerNo, mainSort, sort, status, cp);
		
		log.info("map : " + map.toString());
		model.addAttribute("receiptList", map.get("receiptList"));
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("status", status);
		
		return "partner/seller/product/receipt";
	}
	
	@PostMapping("product/receiptAccept")
	@ResponseBody
	public int ProductAcceptReceipt(@RequestBody Map<String, Object> map) {
		
		int result = service.acceptReceipt(map);
		
		log.info("receiptAccept map : " + map.toString());
		
		return result;
		
	}
	
	@PostMapping("product/receiptReject")
	@ResponseBody
	public int ProductDeclineReceipt(@RequestBody Map<String, Object> map) {
		
		int result = service.rejectReceipt(map);
		
//		log.info("reject controller : " + map.toString());
		return result;
	}
	
	
	@GetMapping("product/shipment")
	public String ProductRelease(@RequestParam(value="cp", required=false, defaultValue="1") int cp,
													@RequestParam(value="mainSort", required=false, defaultValue="0") int mainSort,
													@RequestParam(value="sort", required=false, defaultValue="0") int sort,
													@RequestParam(value="status", required=false, defaultValue="A") String status,
													Model model,
													@SessionAttribute("loginPartnerMember") Partner loginPartnerMember) {
		
		int partnerNo = loginPartnerMember.getPartnerNo();
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
//		log.info("categoryList : " + categoryList);
		
		model.addAttribute("categoryList", categoryList);
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = eCommerceService.selectSmallCategory();
		
//		log.info("smallCategoryList : " + smallCategoryList);

		model.addAttribute("smallCategoryList", smallCategoryList);
		
		// 재고상품 조회
		Map<String, Object> map = service.selectShipmentList(partnerNo, mainSort, sort, status, cp);
		
		log.info("map : " + map.toString());
		model.addAttribute("shipmentList", map.get("shipmentList"));
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("status", status);
		
		
		return "partner/seller/product/shipment";
	}
	
	@PostMapping("product/shipmentAccept")
	@ResponseBody
	public int ShippmentAccept(@RequestBody Map<String, Object> map) {
	    int result = service.acceptShipment(map);
	    log.info("shipmentAccept map : " + map.toString());
	    return result;
	}

	@PostMapping("product/shipmentReject")
	@ResponseBody
	public int ShippmentReject(@RequestBody Map<String, Object> map) {
	    int result = service.rejectShipment(map);
	    log.info("shipmentReject map : " + map.toString());
	    return result;
	}

	
	
	
	// 문의내역
	@GetMapping("qna")
	public String ProductQNA(@RequestParam(value="cp", required=false, defaultValue="1") int cp,
							Model model) {
		
		Map<String, Object> map = service.selectQna(cp);
		
		model.addAttribute("qnaList" ,map.get("qnaList"));
		model.addAttribute("pagination" ,map.get("pagination"));
		
		return "partner/seller/qna/qna";
	}
	
	// 문의내역 입력
	@PostMapping("insertQnaAnswer")
	@ResponseBody
	public int insertQnaAnswer(@RequestBody Map<String, Object> obj,
								@SessionAttribute("loginPartnerMember") Partner loginPartnerMember) {
		
		obj.put("partnerNo", loginPartnerMember.getPartnerNo());
		
		return service.insertQnaAnswer(obj);
	}
	
	
	@GetMapping("product/complete")
	public String ProductComplete(@RequestParam(value="cp", required=false, defaultValue="1") int cp,
														@RequestParam(value="mainSort", required=false, defaultValue="0") int mainSort,
														@RequestParam(value="sort", required=false, defaultValue="0") int sort,
														@RequestParam(value="status", required=false, defaultValue="A") String status,
														Model model,
														@SessionAttribute("loginPartnerMember") Partner loginPartnerMember) {
		
		int partnerNo = loginPartnerMember.getPartnerNo();
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
//		log.info("categoryList : " + categoryList);
		
		model.addAttribute("categoryList", categoryList);
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = eCommerceService.selectSmallCategory();
		
//		log.info("smallCategoryList : " + smallCategoryList);

		model.addAttribute("smallCategoryList", smallCategoryList);
		
		// 재고상품 조회
		Map<String, Object> map = service.selectCompleteList(partnerNo, mainSort, sort, status, cp);
		
		log.info("map : " + map.toString());
		model.addAttribute("completeList", map.get("completeList"));
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("status", status);
		
		return "partner/seller/product/complete";
	}
}
