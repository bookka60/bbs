package com.kh.demo.domain.web;

import com.kh.demo.domain.entity.Post;
import com.kh.demo.domain.post.svc.PostSVC;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/bbs")
@RequiredArgsConstructor
public class PostController {

  final private PostSVC postSVC;

  //post 등록
  @GetMapping
  public String save(Post Post){


  }
}
