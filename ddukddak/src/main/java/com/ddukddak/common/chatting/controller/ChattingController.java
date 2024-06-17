package com.ddukddak.common.chatting.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ddukddak.common.chatting.model.dto.ChattingRoom;
import com.ddukddak.common.chatting.model.dto.Message;
import com.ddukddak.common.chatting.model.service.ChattingService;
import com.ddukddak.member.model.dto.Member;
import com.ddukddak.partner.model.dto.Partner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("chatting")
@Slf4j
public class ChattingController {

	private final ChattingService service;
	
	//채팅 목록 조회 및 페이지 전환
    @GetMapping("list")
    public String chatting(@SessionAttribute(value="loginMember", required = false) Member loginMember,
    						@SessionAttribute(value="loginPartnerMember", required = false) Partner loginPartner,
    						Model model) {
    	
    	log.info("로그인파트너넘버{}",loginPartner);
    	Map<String, Object> map = new HashMap<>();
    	if(loginMember != null) {
    		int loginMemberNo = loginMember.getMemberNo();
    		map.put("isMember", "MEMBER");
    		map.put("memberNo", loginMemberNo);
            List<ChattingRoom> roomList = service.selectRoomList(map);
            model.addAttribute("roomList", roomList);
            return "chatting/chatting";
    	}else {
    		int loginPartnerNo = loginPartner.getPartnerNo();
    		map.put("isMember", "PARTNER");
    		map.put("memberNo", loginPartnerNo);
            List<ChattingRoom> roomList = service.selectRoomList(map);
            model.addAttribute("roomList", roomList);
            return "partner/interiorChatWihUser";
    	}
    }
    
    // 채팅방 목록 조회 - 비동기
    @GetMapping("roomList")
    @ResponseBody
    public List<ChattingRoom> selectRoomList(@SessionAttribute(value = "loginMember",required=false) Member loginMember,
    										@SessionAttribute(value = "loginPartnerMember", required=false) Partner loginPartner) {
    	
    	Map<String, Object> map = new HashMap<>();
    	
    	if(loginMember != null) {
    		int loginMemberNo = loginMember.getMemberNo();
    		map.put("isMember", "MEMBER");
    		map.put("memberNo", loginMemberNo);
            return service.selectRoomList(map);

    	}else {
    		int loginPartnerNo = loginPartner.getPartnerNo();
    		map.put("isMember", "PARTNER");
    		map.put("memberNo", loginPartnerNo);
    		log.info("채팅목록{}",service.selectRoomList(map));
           	return service.selectRoomList(map);

    	}
    	
    }
    
    //[일반회원]채팅 상대 검색
    @GetMapping("selectTarget")
    @ResponseBody
    public List<Partner> selectTarget(@RequestParam("query") String query,
    								@SessionAttribute("loginMember") Member loginMember){
    	Map<String, Object> map = new HashMap<>();
    	
    	map.put("memberNo", loginMember.getMemberNo());
    	map.put("query", query);
    	
    	return service.selectTarget(map);
    }
    

    
    //[일반회원]채팅방 입장(없으면 생성)
    @GetMapping("enter")
    @ResponseBody
    public int chattingEnter(@RequestParam("targetNo") int targetNo, 
    						@SessionAttribute("loginMember") Member loginMember,
    						RedirectAttributes ra) {
     
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        map.put("targetNo", targetNo);
        map.put("memberNo", loginMember.getMemberNo());
        
        // 채팅방번호 체크 서비스 호출 및 반환(기존 생성된 방이 있는지)
        int chattingNo = service.checkChattingNo(map);
        
        // 반환받은 채팅방번호가 0(없다)이라면 생성하기
        if(chattingNo == 0) {
            chattingNo = service.createChattingRoom(map);
            ra.addFlashAttribute("message", "채팅방을 추가하였습니다.");
        }
        
        return chattingNo;
    }
    
    
    
    //[일반회원]메세지 조회 - 비동기
    @GetMapping("selectMessage")
    @ResponseBody
    public List<Message> selectMessageList(@RequestParam Map<String, Object> paramMap) {
    	//map안에 chattingNo, memberNo 넘어감
    	//messageList를 프론트로 넘길거임
        return service.selectMessageList(paramMap);
    }
    
    
    //[일반회원]채팅 읽음 표시
    @PutMapping("updateReadFlag")
    @ResponseBody
    public int updateReadFlag(@RequestBody Map<String, Object> paramMap) {
        return service.updateReadFlag(paramMap);
    }
    
    
    
    //[시공사]채팅 상대 검색
    @GetMapping("I-selectTarget")
    @ResponseBody
    public List<Member> IselectTarget(@RequestParam("query") String query){
    	Map<String, Object> map = new HashMap<>();

    	map.put("query", query);
    	
    	return service.IselectTarget(map);
    }
    

    //[시공사]채팅방 입장(없으면 생성)
    @GetMapping("I-enter")
    @ResponseBody
    public int IchattingEnter(@RequestParam("targetNo") int targetNo, 
    						@SessionAttribute("loginPartnerMember") Partner loginPartner,
    						RedirectAttributes ra) {
     
        Map<String, Integer> map = new HashMap<String, Integer>();
        
        map.put("targetNo", targetNo); //회원번호
        map.put("memberNo", loginPartner.getPartnerNo());//시공사 번호
        
        // 채팅방번호 체크 서비스 호출 및 반환(기존 생성된 방이 있는지)
        int chattingNo = service.IcheckChattingNo(map);
        
        // 반환받은 채팅방번호가 0(없다)이라면 생성하기
        if(chattingNo == 0) {
            chattingNo = service.IcreateChattingRoom(map);
            ra.addFlashAttribute("message", "채팅방을 추가하였습니다.");
        }
        
        return chattingNo;
    }
 

    
    
    //[시공사]메세지 조회 - 비동기
    @GetMapping("I-selectMessage")
    @ResponseBody
    public List<Message> IselectMessageList(@RequestParam Map<String, Object> paramMap) {
    	//map안에 chattingNo, memberNo 넘어감
    	//messageList를 프론트로 넘길거임

        return service.IselectMessageList(paramMap);
    }
    
    
    //[시공사]채팅 읽음 표시
    @PutMapping("I-updateReadFlag")
    @ResponseBody
    public int IupdateReadFlag(@RequestBody Map<String, Object> paramMap) {
        return service.IupdateReadFlag(paramMap);
    }
    
    
    //---------------------------------------------------------------------------
    //전체 채팅
    
    
}
