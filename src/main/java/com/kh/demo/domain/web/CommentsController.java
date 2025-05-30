package com.kh.demo.domain.web;

import com.kh.demo.domain.comments.svc.CommentsSVC;
import com.kh.demo.domain.entity.Comments;
import com.kh.demo.domain.web.form.login.LoginMember;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentsController {

  private final CommentsSVC commentsSVC;

  // 댓글 작성 처리
  @PostMapping("/new")
  public String create(@ModelAttribute Comments comment, HttpSession session) {
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    if (loginMember == null) {
      return "redirect:/login";
    }
    comment.setWriter(loginMember.getNickname());
    commentsSVC.save(comment);
    return "redirect:/post/" + comment.getPostId();  // 게시글 상세 페이지로 리다이렉트
  }

  // 댓글 삭제 처리
  @GetMapping("/{id}/del")
  public String delete(@PathVariable("id") Long id, HttpSession session) {
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    if (loginMember == null) {
      return "redirect:/login";
    }
    Comments comment = commentsSVC.findById(id).orElseThrow();
    if (!comment.getWriter().equals(loginMember.getNickname())) {
      // 본인이 작성한 댓글이 아니면 권한 없음 처리 (간단히 리다이렉트)
      return "redirect:/post/" + comment.getPostId();
    }
    commentsSVC.deleteById(id);
    return "redirect:/post/" + comment.getPostId();
  }

  // 댓글 수정 폼 이동
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, HttpSession session, Model model) {
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    Comments comment = commentsSVC.findById(id).orElseThrow();
    if (loginMember == null || !comment.getWriter().equals(loginMember.getNickname())) {
      return "redirect:/post/" + comment.getPostId();
    }
    model.addAttribute("comment", comment);
    return "comments/edit";  // 댓글 수정 폼 뷰
  }

  // 댓글 수정 처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id, @RequestParam("content") String content, HttpSession session) {
    LoginMember loginMember = (LoginMember) session.getAttribute("loginMember");
    if (loginMember == null) {
      return "redirect:/login";
    }
    Comments comment = commentsSVC.findById(id).orElseThrow();
    if (!comment.getWriter().equals(loginMember.getNickname())) {
      return "redirect:/post/" + comment.getPostId();
    }
    comment.setContent(content);
    int result = commentsSVC.updateById(id,comment);
    return "redirect:/post/" + comment.getPostId();
  }
}
