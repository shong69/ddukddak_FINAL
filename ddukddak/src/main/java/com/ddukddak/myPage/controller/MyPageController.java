package com.ddukddak.myPage.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
	public String main(@SessionAttribute("loginMember") Member loginMember,
						HttpSession session ) {
        
        // enrollDate가 String일 경우 Date 객체로 변환
        if (loginMember != null && loginMember.getEnrollDate() instanceof String) {
            try {
                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date date = originalFormat.parse((String) loginMember.getEnrollDate());
                String formattedDate = targetFormat.format(date);
                session.setAttribute("enrollDate", formattedDate);
                
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        
		return"myPage/myPageMain";
	}
	//위시리스트 진입
	@GetMapping("wishList")
	public String wishList() {
		
		
		
		return"myPage/wishList";
	}
	
	
	//회원정보 진입
	@GetMapping("memberInfo")
	public String memberInfo() {
		return"myPage/memberInfo";
	}
	
	//--------------------------------------------------------------
	
	/**[회원정보] 프로필 이미지 변경 - 비동기
	 * @param file
	 * @param loginMemebr
	 * @return
	 * @throws Exception
	 */
	@PostMapping("memberInfo/profileImg")
	public String changeProfileImg(
			@RequestParam("profile-image") MultipartFile file,
			@SessionAttribute("loginMember") Member loginMemebr,
			RedirectAttributes ra) throws Exception{
		int result  = infoService.updateImg(file, loginMemebr);
		String message = null;
		
		if(result>0) message = "변경 성공";
		else		 message = "변경 실패";
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
		else 		 message = "비밀번호 변경 실패\n다시 시도해주세요.";
		
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
			@SessionAttribute("loginMember") Member member) {
		int memberNo = member.getMemberNo();
		Map<String, Object> map  = new HashMap<>();
		map.put("memberEmail", memberEmail);
		map.put("memberNo", memberNo);
		
		return infoService.updateEmail(map);
	}
	
	/**[회원정보]닉네임 변경하기
	 * @param memberNickname
	 * @param member
	 * @return
	 */
	@ResponseBody
	@GetMapping("memberInfo/updateMemberNickname")
	public int updateNickname(@RequestParam("memberNickname") String memberNickname,
			@SessionAttribute("loginMember") Member member) {
		int memberNo = member.getMemberNo();
		String oldNickname = member.getMemberNickname();
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("memberNickname", memberNickname);
		map.put("oldNickname", oldNickname);
		
		return infoService.updateNickname(map);
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
			@SessionAttribute("loginMember") Member member) {
		
		int memberNo = member.getMemberNo();
		map.put("memberNo", memberNo);
		return infoService.updatePhoneNum(map);
	}
	
	
	//----------------------------------------------------------------
	
	//장바구니 상품 목록 조회
	@GetMapping("cart")
	public String selectCartList(@SessionAttribute("loginMember") Member loginMember,
			Model model) {
		//로그인한 회원에 알맞는 장바구니 상품 목록 불러오기
		Map<String, Object> map = service.selectCartList(loginMember);
		
		model.addAttribute("cartList", map.get("cartList"));
		
		return"myPage/cart";
	}
	
	//장바구니 추가
	@PostMapping("addCart")
	@ResponseBody
	public int addCart(@RequestBody List<Object> obj,
						@SessionAttribute("loginMember") Member loginMember) {
		log.info("obj : " + obj);
		
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
	
	
	//장바구니 삭제
	@PostMapping("cart/delProduct")
	@ResponseBody
	public int delProduct(@SessionAttribute("loginMember")Member loginMember,
			@RequestBody Map<String, Object> map) {
		
		int memberNo = loginMember.getMemberNo();
		map.put("memberNo", memberNo);
		
		return service.delProduct(map);
		
	}
	
	
	//주문으로 이동
}