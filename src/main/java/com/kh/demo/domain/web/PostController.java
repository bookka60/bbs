package com.kh.demo.domain.web;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.domain.post.svc.PostSVC;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

  private final PostSVC postSVC;

  // 기본 페이지
  @GetMapping("/")
  public String index() {
    return "index";
  }

  // 전체 목록
  @GetMapping("/list")
  public String findAll(Model model) {
    List<Post> list = postSVC.findAll();
    model.addAttribute("posts", list);
    return "post/list";
  }

  // 등록 화면
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("post", new Post());
    return "post/add";
  }

  // 등록 처리
  @PostMapping("/add")
  public String add(
      @Valid @ModelAttribute("post") Post post,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    log.info("post={}", post);

    // 1) 유효성 검사
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "post/add";
    }

    post.setCreationDate(LocalDateTime.now());
    post.setModificationDate(LocalDateTime.now());
    Long postId = postSVC.save(post);
    redirectAttributes.addAttribute("id", postId);
    return "redirect:/post/list";
  }

  // 조회(단건)
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {
    Post post = postSVC.findById(id).orElseThrow();
    model.addAttribute("post", post);
    return "post/detail";
  }

  // 수정 화면
  @GetMapping("/{id}/edit")
  public String updateForm(
      @PathVariable("id") Long id, Model model) {
    Post post = postSVC.findById(id).orElseThrow();
    model.addAttribute("post", post);
    return "post/update";
  }

  // 수정 처리
  @PostMapping("/{id}/update")
  public String update(
      @PathVariable("id") Long id,
      @Valid @ModelAttribute("post") Post post,
      BindingResult bindingResult,
      RedirectAttributes redirectAttributes
  ) {
    log.info("updatePost={}", post);

    // 유효성 검사
    if (bindingResult.hasErrors()) {
      log.info("bindingResult={}", bindingResult);
      return "post/update";
    }

    post.setModificationDate(LocalDateTime.now());
    postSVC.updateById(id, post);

    redirectAttributes.addAttribute("id", id);

    return "redirect:/post/{id}";
  }

  // 삭제(단일)
  @GetMapping("/{id}/del")
  public String delete(
      @PathVariable("id") Long postId) {
    int rowss = postSVC.deleteById(postId);
    return "redirect:/post/list";
  }
  // 삭제(여러건)
  @PostMapping("/del")
  public String deleteByIds(@RequestParam("postIds") List<Long> postIds){
    log.info("postIds={}", postIds);

    int rows = postSVC.deleteByIds(postIds);
    log.info("글목록 {}-건 삭제됨!",rows);
    return "redirect:/post/list";
  }

}
