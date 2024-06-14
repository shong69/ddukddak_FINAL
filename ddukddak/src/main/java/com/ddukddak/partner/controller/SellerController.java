package com.ddukddak.partner.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.board.model.dto.Board;
import com.ddukddak.ecommerce.model.dto.Category;
import com.ddukddak.ecommerce.model.dto.Product;
import com.ddukddak.ecommerce.model.service.eCommerceService;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;
import com.ddukddak.partner.model.service.PartnerService;
import com.ddukddak.partner.model.service.ProductService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
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

	@GetMapping("product/create")
	public String ProductCreate(
			@RequestParam(value="cp", required=false, defaultValue="1") int cp,
			@RequestParam(value="sort", required=false, defaultValue="0") int mainSort,
			@RequestParam(value="sort", required=false, defaultValue="0") int sort,
			@SessionAttribute("loginPartnerMember") Partner loginPartnerMember, 
			RedirectAttributes ra,
			Model model) {
		
		if (loginPartnerMember.getPartnerType() != 2) {
            ra.addFlashAttribute("message", "접근 제한된 서비스 입니다");
            return "redirect:/partner/main";
        } 
		
		// 대분류 카테고리 선택
		List<Category> categoryList = eCommerceService.selectCategory();
		
		model.addAttribute("categoryList", categoryList);
		
		// 소분류 카테고리 선택
		List<Category> smallCategoryList = eCommerceService.selectSmallCategory();
		
		model.addAttribute("smallCategoryList", smallCategoryList);
		
		// 재고상품 조회
		Map<String, Object> map = service.selectCreateList(mainSort, sort, cp);
		
		model.addAttribute("createList", map.get("createList"));
		model.addAttribute("pagination", map.get("pagination"));

        
        return "partner/seller/product/create";
	}
	
	@PutMapping("product/sellApplyProduct")
	@ResponseBody
	public int sellApplyProduct(@RequestBody List<Object> selectedValues) {
		
		int result = service.sellApplyProduct(selectedValues);
		
		return result;
	}
	
	@PostMapping("product/selectSmallCategory")
	@ResponseBody
	public List<Category> selectSmallCategory(@RequestBody int selectedCategory) {
		
		List<Category> smallCategoryList = service.selectSmallCategory(selectedCategory);
		
		return smallCategoryList;
	}
	
	@PostMapping("product/create")
	@ResponseBody
	public int registMyHouse(@RequestParam ("productName") String productName,
								@RequestParam ("smallCategory") int smallCategory,
								@RequestParam ("productPrice") int productPrice,
								@RequestParam ("thumbnailImg") MultipartFile thumbnailImg,
								@RequestParam ("subImgs") List<MultipartFile> subImgs,
								@RequestParam (name = "optionName", required = false) List<String> optionName,
								@RequestParam (name = "optionContent", required = false) List<String> optionContent,
								@RequestParam (name = "optionCount", required = false) List<String> optionCount,
								HttpServletRequest req) throws IOException {
		
		HttpSession session = req.getSession();
		
		Member loginMember = (Member)session.getAttribute("loginMember");
	
		log.info("object : " + productName + smallCategory + productPrice);	
		log.info("thumbnailImg : " + thumbnailImg);	
		log.info("subImgs : " + subImgs);	
		log.info("optionName : " + optionName);
		log.info("optionContent : " + optionContent);
		log.info("optionCount : " + optionCount);

		// PRODUCT 테이블 삽입
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("memberNo", loginMember.getMemberNo());
		map.put("productName", productName);
		map.put("smallCategoryNo", smallCategory);
		map.put("productPrice", productPrice);
		
		int result1 = service.registProduct(map);
		

		// UPLOAD_FILE 삽입

		List<MultipartFile> imgList = new ArrayList<>(subImgs);
		imgList.add(0, thumbnailImg); // 다시 mainImg 를 배열 0번째 자리에 추가

		int result2 = service.insertImg(smallCategory, imgList);

		log.info("imgList : " + imgList);
		
		
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

	
	
	@GetMapping("product/apply")
	public String ProductApply() {
		return "partner/seller/product/apply";
	}
	
	@GetMapping("product/applyProduct")
	public String ProductApplyProduct() {
		return "partner/seller/product/applyProduct";
	}
	
	@GetMapping("product/receipt")
	public String ProductReceipt() {
		return "partner/seller/product/receipt";
	}
	
	@GetMapping("product/shipment")
	public String ProductRelease() {
		return "partner/seller/product/shipment";
	}
	
	@GetMapping("product/complete")
	public String ProductComplete() {
		return "partner/seller/product/complete";
	}
	
	
	
	
	@GetMapping("qna")
	public String ProductQNA() {
		return "partner/seller/qna/qna";
	}
}
