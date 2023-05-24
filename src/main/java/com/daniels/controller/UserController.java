package com.daniels.controller;

import com.daniels.annotation.SwaggerController;
import com.daniels.model.dto.UserReadDto;
import com.daniels.service.UserService;
import com.daniels.utility.PageUtil;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/user")
@SwaggerController
public class UserController {

  private final UserService userService;
  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping(value = "/retrieve")
  @ResponseBody
  public List<UserReadDto> retrieve(
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Integer size,
      final HttpServletResponse httpResponse
  ) {
    final PageRequest pageRequest = PageUtil.createPageRequest(page, size);
    return userService.retrieve(pageRequest, httpResponse);
  }

}
