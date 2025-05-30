package com.kh.demo.domain.web;


import com.kh.demo.domain.entity.Member;
import com.kh.demo.domain.member.svc.MemberSVC;
import com.kh.demo.domain.web.form.member.JoinForm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

  private final MemberSVC memberSVC;

  //회원가입
  @GetMapping("/join")
  public String joinForm(Model model){

    model.addAttribute("joinForm", new JoinForm());
    return "member/joinForm";
  }

  //가입처리
  @PostMapping("/join")
  public String join(
      @Valid @ModelAttribute JoinForm joinForm,
      BindingResult bindingResult
  ){
    log.info("joinForm={}", joinForm);

    //유효성 체크
    //1) 필드 오류
    if (bindingResult.hasErrors()){
      log.info("bindingResult={}", bindingResult);
      return "member/joinForm";
    }

    //2)글로벌 오류
    if (!joinForm.getPasswd().equals(joinForm.getPasswdChk())){
      bindingResult.reject("passwdErr","비밀번호와 비밀번호확인 값이 일치 하지 않습니다.");
    }
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);

      return "member/joinForm";
    }
    // 회원가입 정상로직 처리
    Member member = new Member();
    BeanUtils.copyProperties(joinForm,member);

    Member savedMember = memberSVC.join(member);

    return "redirect:/login?success=true";
  }
}









