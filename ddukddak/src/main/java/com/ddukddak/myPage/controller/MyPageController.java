package com.ddukddak.myPage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.dto.CartItem;
import com.ddukddak.myPage.model.dto.Order;
import com.ddukddak.myPage.model.service.CartAndWishListService;
import com.ddukddak.myPage.model.service.MemberInfoService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("myPage")
@SessionAttributes("loginMember")
@RequiredArgsConstructor
public class MyPageController {
	private final MemberInfoService infoService;
	private final CartAndWishListService service;
	
	//주문내역 진입
	@GetMapping("")
	public String main() {
		return "myPage/myPageMain";
	}
	
	@GetMapping("selectOrderList")
	@ResponseBody
	public List<Order> selectOrderList(@SessionAttribute("loginMember") Member loginMember) {

		return service.selectOrderList(loginMember);
	}



	//[주문상태] 상태 배송완료 -> 구매확정

	@GetMapping("confirmPurchase")
	@ResponseBody
	public int confirmPurchase(@RequestParam("orderItemNo") int orderItemNo,
							@RequestParam("orderStatus") String orderStatus) {

		
		return service.confirmPurchase(orderItemNo,orderStatus);
	}
	
	//[주문상태]주문상태 결제완료 -> 주문취소
	@GetMapping("orderDelete")
	@ResponseBody
	public int orderDelete(@RequestParam("orderItemNo") int orderItemNo,
							@RequestParam("orderStatus") String orderStatus) {
		
		log.info("취소 정보{}", orderStatus);
		return service.orderDelete(orderItemNo,orderStatus);
	}
	
	//[주문상태]리뷰작성 상태 확인
	@GetMapping("checkReviewWrite")
	@ResponseBody
	public int checkReviewWrite(@RequestParam("orderItemNo") int orderItemNo){
		
		return service.checkReviewWrite(orderItemNo);
	}

	
	//[주문상태]리뷰작성 : 카테고리 얻어오기
	@GetMapping("selectCategory")
	@ResponseBody
	public Map<String, Object> selectCategoty(@RequestParam("productNo") int productNo){

//		Product product = service.getProductByNo(productNo);
		Map<String, Object> map = new HashMap<>();
//		map.put("bigCategotyNo", product.getBigCategoryNo());
//		map.put("CategotyNo", product.getCategoryNo());

		log.info("C카테고리 {}",productNo);
		map = service.getProductByNo(productNo);
//		Map<String, Object> map = service.getProductByNo(productNo);

		
		return map;
	}

	
	//회원정보 진입
	@GetMapping("memberInfo")
	public String memberInfo() {
		return"myPage/memberInfo";
	}
	
	//--------------------------------------------------------------
	
	/**[회원정보] 프로필 이미지 변경
	 * @param file
	 * @param loginMemebr
	 * @return
	 * @throws Exception
	 */
	@PostMapping("memberInfo")
	public String changeProfileImg(
			@RequestParam("profile-image") MultipartFile file,
			@SessionAttribute("loginMember") Member loginMember,
			RedirectAttributes ra,
			HttpSession session) throws Exception{
		int result  = infoService.updateImg(file, loginMember);

		String message = null;
		
		if(result>0) {
			message = "변경 성공";
		}
		else {
			message = "변경 실패";
		}
		ra.addFlashAttribute("message", message);
		
		return "myPage/memberInfo";
	}

	
	/**[회원정보]비밀번호 변경 -비동기
	 * @return
	 */
	@ResponseBody
	@PostMapping("memberInfo/password")
	public Map<String, String> changePassword(
			@RequestBody Map<String, String> map,
			@SessionAttribute("loginMember") Member mem) {
		
		int memberNo = mem.getMemberNo();
		
		int result = infoService.changePassword(map, memberNo);
		
		String message=null;
		Map<String, String> response = new HashMap<>();
		if(result>0) message = "비밀번호가 변경되었습니다.";
		else if (result == 0) message = "비밀번호 변경 실패\n다시 시도해주세요.";
		else				message = "이전 비밀번호와 동일합니다";
		
		response.put("message", message);
		return response;
		
	}
	
	/**[회원정보] 이메일 중복 확인
	 * @param memberEmail
	 * @return
	 */
	@ResponseBody
	@GetMapping("memberInfo/emailDup")
	public int checkEmail(@RequestParam("memberEmail") String memberEmail) {
		return infoService.checkEmail(memberEmail);
	}
	
	/**이메일 변경하기
	 * @param memberEmail
	 * @param member
	 * @return
	 */
	@ResponseBody
	@GetMapping("memberInfo/emailUpdate")
	public int updateEmail(@RequestParam("memberEmail") String memberEmail,
			@SessionAttribute("loginMember") Member member,
			HttpSession session) {
		int memberNo = member.getMemberNo();
		Map<String, Object> map  = new HashMap<>();
		map.put("memberEmail", memberEmail);
		map.put("memberNo", memberNo);
		
		int result = infoService.updateEmail(map);
		if(result >0) {
			member.setMemberEmail(memberEmail);
			session.setAttribute("loginMember", member);
		}
		return result;
	}
	
	/**[회원정보]닉네임 변경하기
	 * @param memberNickname
	 * @param member
	 * @return
	 */
	@ResponseBody
	@GetMapping("memberInfo/updateMemberNickname")
	public int updateNickname(@RequestParam("memberNickname") String memberNickname,
			@SessionAttribute("loginMember") Member member,
			HttpSession session) {
		int memberNo = member.getMemberNo();
		String oldNickname = member.getMemberNickname();
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("memberNickname", memberNickname);
		map.put("oldNickname", oldNickname);
		int result = infoService.updateNickname(map);
		if(result > 0) {
			member.setMemberNickname(memberNickname);
			session.setAttribute("loginMember",member);
		}
		return result;
	}

	/**[회원정보]휴대폰 번호 중복 체크
	 * @return
	 */
	@ResponseBody
	@PostMapping("memberInfo/phoneDup")
	public int changePhoneNum(@RequestBody Map<String, String> map) {
		return infoService.checkPhonNum(map.get("phoneNum"));
	}
	
	/**[회원정보]휴대폰 번호 업데이트하기
	 * @param map{updatePhoneNum}
	 * @param member
	 * @return
	 */
	@ResponseBody
	@PostMapping("memberInfo/phoneNumUpdate")
	public int updatePhoneNum(@RequestBody Map<String, Object> map,
			@SessionAttribute("loginMember") Member member,
			HttpSession session) {
		
		int memberNo = member.getMemberNo();
		map.put("memberNo", memberNo);
		int result = infoService.updatePhoneNum(map);
		if(result > 0) {
			member.setMemberTel((String)map.get("updatePhoneNum"));
			session.setAttribute("loginMember", member);
		}
		return result;
	}
	
	/** 주소 업데이트 하기
	 * @param map
	 * @param member
	 * @param session
	 * @return
	 */
	@PostMapping("memberInfo/addressUpdate")
	@ResponseBody
	public int updateAddress(@RequestBody Map<String, Object> map,
			@SessionAttribute("loginMember") Member member,
			HttpSession session) {
		
		int memberNo = member.getMemberNo();
		map.put("memberNo", memberNo);
		int result = infoService.updateAddress(map);
		return result;
	}
	
	//----------------------------------------------------------------
	
	//장바구니 상품 목록 조회
	@GetMapping("cart")
	public String selectCartList(@SessionAttribute("loginMember") Member loginMember,
								Model model) {
		//로그인한 회원에 알맞는 장바구니 상품 목록 불러오기
		List<CartItem> cartItem = service.selectCartList(loginMember);
		
		model.addAttribute("cartList", cartItem);
		
		return"myPage/cart";
	}
	
	//장바구니 추가
	@PostMapping("addCart")
	@ResponseBody
	public int addCart(@RequestBody List<Object> obj,
						@SessionAttribute("loginMember") Member loginMember) {
		
		int result = 0;
		
		for (Object item : obj) {
		    Map<String, Object> map = (Map<String, Object>) item;
		    
		    int productNo = (Integer)map.get("productNo");
		    List<Integer> option = (List<Integer>)map.get("option");
		    int quantity = (Integer)map.get("quantity");
		    
		    result += service.addCart(loginMember, productNo, option, quantity);
		}
		
		return result;
	}
	
	
	//장바구니 수량 수정
	@PutMapping("modifyCount")
	@ResponseBody
	public int modifyCount(@RequestBody Map<String, Object> obj) {
		String cartId = (String)obj.get("cartId");
		int quantity = (Integer)obj.get("quantity");
		
		return service.modifyCount(cartId, quantity);
	}
	
	
	//장바구니 삭제
	@PostMapping("cart/delProduct")
	@ResponseBody
	public int delProduct(@RequestBody int cartId) {
		
		return service.delProduct(cartId);
		
	}
	
	//위시리스트 진입
	@GetMapping("wishList")
	public String wishList(@SessionAttribute("loginMember") Member loginMember,
							@RequestParam(value="cp", required=false, defaultValue="1") int cp,
							Model model) {
		
		Map<String, Object> map = service.selectWishList(loginMember, cp);
		
		model.addAttribute("wishList", map.get("wishList"));
		model.addAttribute("pagination", map.get("pagination"));
		model.addAttribute("loginMember", loginMember);
	
		return"myPage/wishList";
	}
	
	
	//위시리스트 추가
	@PutMapping("addWish")
	@ResponseBody
	public int addWich(@RequestBody Map<String, Object> obj) {
		
		return service.addWish(obj);
	}
	
	
	//위시리스트 삭제
	@PostMapping("delWish")
	@ResponseBody
	public int delWish(@RequestBody Map<String, Object> obj) {
		int result = service.delWish(obj);
		log.info("result2 : " + result);
		return result;
	}
	

}