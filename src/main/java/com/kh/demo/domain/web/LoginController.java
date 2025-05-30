package com.kh.demo.domain.web;

import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.dao.MemberDAO;
import com.kh.demo.domain.web.api.ApiResponse;
import com.kh.demo.domain.web.api.ApiResponseCode;
import com.kh.demo.domain.web.form.login.LoginForm;
import com.kh.demo.domain.web.form.login.LoginMember;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

  private final MemberDAO memberDAO;

  //로그인화면
  @GetMapping("/login")
  public String loginForm(Model model, @RequestParam(value = "success", required = false) String success) {
    model.addAttribute("loginForm", new LoginForm());

    // 회원가입 성공 메시지
    if ("true".equals(success)) {
      model.addAttribute("msg", "회원가입 완료 로그인 해주세요.");
    }

    return "login/loginForm";
  }

  //로그인처리
  @PostMapping("/login")
  public String login(
      @Valid @ModelAttribute LoginForm loginForm,
      BindingResult bindingResult,
      HttpServletRequest request
  ){
    log.info("loginForm={}", loginForm);
    if (bindingResult.hasErrors()){
      return "login/loginForm";
    }

    //회원존재 유무
    if (!memberDAO.isExist(loginForm.getEmail())){
      bindingResult.rejectValue("email",null,"회원정보가 없습니다.");
      return "login/loginForm";
    }

    //비밀번호 일치여부
    //회원 찾기
    Optional<Member> optionalMember = memberDAO.findByEmail(loginForm.getEmail());
    Member member = optionalMember.get();
    log.info("member={}", member);

    //비밀번호 확인
    if (!loginForm.getPasswd().equals(member.getPasswd())){
      bindingResult.rejectValue("passwd",null,"비밀번호가 다릅니다.");
      return "login/loginForm";
    }

    //세션 생셩
    HttpSession session = request.getSession(true);

    //세션에 회원정보 저장
    LoginMember loginMember = new LoginMember(
        member.getMemberId(),
        member.getEmail(),
        member.getNickname()
    );
    session.setAttribute("loginMember", loginMember);

    return "redirect:/";    // 초기화면
  }

  //로그 아웃
  @DeleteMapping("logout")
  @ResponseBody
  public ResponseEntity<ApiResponse<String>> logout(HttpServletRequest request){

    ResponseEntity<ApiResponse<String>> res = null;

    //1) 세션정보 가져오기
    HttpSession session = request.getSession(false);

    //2) 세션 제거
    if (session != null) {
      session.invalidate();
      ApiResponse<String> stringApiResponse = ApiResponse.of(ApiResponseCode.SUCCESS, "세션 제거됨!");
      res = ResponseEntity.ok(stringApiResponse);
    }
    return res;
  }
}





















