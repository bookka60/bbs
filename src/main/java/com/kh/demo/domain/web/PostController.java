package com.kh.demo.domain.web;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.domain.post.svc.PostSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    model.addAttribute("posts", list); // key 이름은 posts
    return "post/list"; // templates/post/list.html
  }

  // 등록 화면
  @GetMapping("/add")
  public String addForm(Model model) {
    model.addAttribute("post", new Post());
    return "post/add";
  }

  // 등록 처리
  @PostMapping("/add")
  public String add(@ModelAttribute Post post, RedirectAttributes redirectAttributes) {
    post.setCreationDate(LocalDateTime.now());
    post.setModificationDate(LocalDateTime.now());
    Long postId = postSVC.save(post);
    redirectAttributes.addAttribute("id", postId);
    return "redirect:/post/{id}";
  }

  // 단건 조회
  @GetMapping("/{id}")
  public String findById(@PathVariable("id") Long id, Model model) {
    Post post = postSVC.findById(id).orElseThrow();
    model.addAttribute("post", post);
    return "post/detailForm";
  }

  // 수정 화면
  @GetMapping("/{id}/edit")
  public String editForm(@PathVariable("id") Long id, Model model) {
    Post post = postSVC.findById(id).orElseThrow();
    model.addAttribute("post", post);
    return "post/editForm";
  }

  // 수정 처리
  @PostMapping("/{id}/edit")
  public String edit(@PathVariable("id") Long id, @ModelAttribute Post post) {
    post.setModificationDate(LocalDateTime.now());
    postSVC.updateById(id, post);
    return "redirect:/post/{id}";
  }

  // 삭제
  @GetMapping("/{id}/del")
  public String delete(@PathVariable("id") Long id) {
    postSVC.deleteByIds(id);
    return "redirect:/post/list";
  }
}
