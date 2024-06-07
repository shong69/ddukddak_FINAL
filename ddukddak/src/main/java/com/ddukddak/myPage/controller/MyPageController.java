package com.ddukddak.myPage.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.ddukddak.member.model.dto.Member;
import com.ddukddak.myPage.model.service.MemberInfoService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("myPage")
@SessionAttributes("loginMember")
@RequiredArgsConstructor
public class MyPageController {
	private final MemberInfoService infoService;
	//주문내역 진입
	@GetMapping("")
	public String main() {
		return"myPage/myPageMain";
	}
	//위시리스트 진입
	@GetMapping("wishList")
	public String wishList() {
		return"myPage/wishList";
	}
	//장바구니 진입
	@GetMapping("cart")
	public String cart() {
		return "myPage/cart";
	}
	//회원정보 진입
	@GetMapping("memberInfo")
	public String memberInfo() {
		return"myPage/memberInfo";
	}
	

	
	/**[회원정보] 프로필 이미지 변경 - 비동기
	 * @param file
	 * @param loginMemebr
	 * @return
	 * @throws Exception
	 */
	@PostMapping("memberInfo/profileImg")
	@ResponseBody
	public ResponseEntity<String> changeProfileImg(
			@RequestParam("profile-image") MultipartFile file,
			@SessionAttribute("loginMember") Member loginMemebr) throws Exception{
		int result  = infoService.updateImg(file, loginMemebr);
		
		if(result >0) return ResponseEntity.ok().build();
		else return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

		
	}
	
	
	
	
	/**[회원정보]비밀번호 변경 -비동기
	 * @return
	 */
	@ResponseBody
	@GetMapping("memberInfo/password")
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
	
	
	
	
	/**닉네임 변경하기
	 * @param memberNickname
	 * @param member
	 * @return
	 */
	@ResponseBody
	@PostMapping("memberInfo/nickname")
	public int updateNickname(@RequestParam("memberNickname") String memberNickname,
			@SessionAttribute("loginMember") Member member) {
		int memberNo = member.getMemberNo();
		Map<String, Object> map = new HashMap<>();
		
		map.put("memberNo", memberNo);
		map.put("memberNickname", memberNickname);
		
		return infoService.updateNickname(map);
	}
	
	
	
	
	
	/** 휴대폰 번호 중복 체크
	 * @return
	 */
	@ResponseBody
	@PostMapping("memberInfo/phoneDup")
	public int changePhoneNum(@RequestBody Map<String, String> map) {
		return infoService.checkPhonNum(map.get("phoneNum"));
	}
	
	/** 휴대폰 번호 업데이트하기
	 * @param map{updatePhoneNum}
	 * @param member
	 * @return
	 */
	@ResponseBody
	@GetMapping("memberInfo/phoneNumUpdate")
	public int updatePhoneNum(@RequestBody Map<String, Object> map,
			@SessionAttribute("loginMember") Member member) {
		
		int memberNo = member.getMemberNo();
		map.put("memberNo", memberNo);
		return infoService.updatePhoneNum(map);
	}
}