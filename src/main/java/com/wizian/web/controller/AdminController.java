package com.wizian.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wizian.web.dto.AdminDTO;
import com.wizian.web.dto.BoardDTO;
import com.wizian.web.service.AdminService;
import com.wizian.web.service.BoardService;

@Controller
public class AdminController {
	
	 //private final AdminService service;

		/*
		 * @Autowired public AdminController(AdminService service) { this.service =
		 * service; }
		 */
	    
	@Autowired
	private AdminService adminService;
	
	@GetMapping("/admin/main")
	public String mypage() {
		
		return "admin/main";
	}
	
	@GetMapping("/admin/gcoun")
	public String gcoun(Model model) {
		
		//List<Map<String, Object>> getGcounCslList = adminService.getGcountCslList();
		//model.addAttribute("cslList" ,getGcounCslList);
		
		
		return "admin/gcoun";
	}
	
	@GetMapping("/admin/student")
	public String student() {
		
		
		return "/admin/student";
	}
	
	@GetMapping("/admin/studentList")
	public List<Map<String, Object>> readData() {
		List<Map<String, Object>> studentList = adminService.studentList();
		return studentList;
	}
	
	@ResponseBody
	@PostMapping("/admin/getGcounStudList")
	public List<Map<String, Object>> getGcounStudList(@RequestParam("gcounCd") String gcounCd) {
		
		List<Map<String, Object>> getGcounStudList = adminService.getGcounStudList(gcounCd);
		return getGcounStudList;
	}
	
	@ResponseBody
	@GetMapping("/admin/getGcounList")
	public List<Map<String, Object>> getGcounList() {
		List<Map<String, Object>> getGcounList = adminService.getGcounList();
		
		return getGcounList;
	}
	
	// 취업상담
	@GetMapping("/admin/ecoun")
	public String ecoun() {
		
		return "admin/ecoun";
	}
	
	//개인상담
	@GetMapping("/admin/indicoun")
	public String indicoun() {
		
		return "admin/indicoun";
	
		
		
		
	}
	
	
	@ResponseBody
	@GetMapping("/admin/getEcounList")
	public List<Map<String, Object>> getEcounList() {
		List<Map<String, Object>> getEcounList = adminService.getEcounList();
		System.out.println(getEcounList);
		
		return getEcounList;
	}
	
	
	//개인상담 보드리스트 
	@ResponseBody
    @GetMapping("/admin/boardList")
    public List<Map<String, Object>> getBoardList(@RequestParam("studentNo") String studentNo) {
        List<Map<String, Object>> boardList = adminService.getBoardListByStudentNo(studentNo);
        return boardList;
    }
	
	
	
	
	//개인상담 포스트디테일
	 @ResponseBody
	    @GetMapping("/admin/postDetail")
	    public Map<String, Object> getPostDetail(@RequestParam("postId") int postId) {
	        Map<String, Object> response = new HashMap<>();
	        BoardDTO detail = adminService.getPostDetail(postId);
	        List<BoardDTO> replies = adminService.getReplies(postId);

	        // BoardDTO를 Map으로 변환
	        Map<String, Object> detailMap = new HashMap<>();
	        detailMap.put("PST_SN", detail.getPST_SN());
	        detailMap.put("PST_CAT", detail.getPST_CAT());
	        detailMap.put("PST_TTL", detail.getPST_TTL());
	        detailMap.put("PST_CN", detail.getPST_CN());
	        detailMap.put("WRITER", detail.getWRITER());
	        detailMap.put("PSTG_YMD", detail.getPSTG_YMD());
	        detailMap.put("MDFCN_YMD", detail.getMDFCN_YMD());
	        detailMap.put("PST_COMP", detail.getPST_COMP());
	        detailMap.put("BBS_SN", detail.getBBS_SN());

	        response.put("detail", detailMap);
	        response.put("replies", replies);
	        return response;
	    }
	
	
	
	 @GetMapping("/admin/incompleteConsultCount")
	 public ResponseEntity<Integer> countIncompletePostsByStudentNo(@RequestParam("studentNo") String studentNo) {
	     int count = adminService.getIncompleteConsultCount(studentNo);
	     return ResponseEntity.ok(count);
	 }
	 
	 
	 // 취업 상담
	@ResponseBody
	@PostMapping("/admin/getEcounStudList")
	public List<Map<String, Object>> getEcounStudList(@RequestParam("cslNo") String cslNo) {
		
		List<Map<String, Object>> getEcounStudList = adminService.getEcounStudList(cslNo);
		return getEcounStudList;
	}
	
	@ResponseBody
	@PostMapping("/admin/getEcounStudModify")
	public void getEcounStudModify(@RequestParam("counNum") int counNum,
            @RequestParam(value = "fieldName", required = false) String fieldName,
            @RequestParam(value = "fieldValue", required = false) String fieldValue) {

		
	Map<String, Object> map = new HashMap<>();
	
	if (fieldName.equals("COUN_CN")) {
		map.put("counNum", counNum);
		map.put("fieldValue", fieldValue);
		adminService.updateCounCn(map);
		//System.out.println("삽입성공1");
	}else if(fieldName.equals("EMP_COUN_YMD")) {
		map.put("counNum", counNum);
		map.put("fieldValue", fieldValue);
		adminService.updateCounYmd(map);
		//System.out.println("삽입성공2");
	}else if(fieldName.equals("EMP_COUN_CD")) {
		map.put("counNum", counNum);
		map.put("fieldValue", fieldValue);
		adminService.updateCounCd(map);
		//System.out.println("삽입성공3");
	}else {
		//EMP_STTS_CD
		map.put("counNum", counNum);
		map.put("fieldValue", fieldValue);
		adminService.updateSttsCd(map);
		//System.out.println("삽입성공4");
	}
	}

	//System.out.println(counNum);
	//System.out.println(fieldName);
	//System.out.println(fieldValue);
	
	@ResponseBody
	@PostMapping("/admin/gcounEnroll")
	public int gcounEnroll(AdminDTO adminDTO) {
		System.out.println("컨트롤러 실행");
		int gcounEnroll = adminService.gcounEnroll(adminDTO);
		
		return gcounEnroll;
	}
	
	// 심리상담
	@GetMapping("/admin/pcoun")
	public String pcoun() {
		
		return "admin/pcoun";
	}

	 @PostMapping("/admin/registerEmpCounselor")
	    @ResponseBody
	    public String registerCounselor(@RequestBody Map<String, String> formData) {
	        String cd = formData.get("cd");
	        String cslNo = formData.get("cslNo");
	        String cslNm = formData.get("cslNm");
	        String email = formData.get("email");
	        String mobile = formData.get("mobile");
	        String scsbjtNm = formData.get("scsbjtNm");
	        
	        System.out.println(cd);
	        System.out.println(cslNm);
	        System.out.println(formData);
	        adminService.registerCounselor(formData);
	        adminService.registerEmpCounPro(formData);

	        // 데이터베이스에 상담사 등록 로직 추가
	        	
	        return "success";
	    }
	
}
