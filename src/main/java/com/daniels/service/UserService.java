package com.daniels.service;

import com.daniels.dao.model.User;
import com.daniels.dao.model.mapper.ResourceMapper;
import com.daniels.dao.repository.UserRepository;
import com.daniels.exception.DataNotFoundException;
import com.daniels.model.dto.UserReadDto;
import com.daniels.utility.PageUtil;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

  private final UserRepository userRepository;
  private final ResourceMapper resourceMapper;

  public UserService(UserRepository userRepository, ResourceMapper resourceMapper) {
    this.userRepository = userRepository;
    this.resourceMapper = resourceMapper;
  }

  public UserReadDto retrieve(final String id){
    Optional<User> optional = userRepository.findById(id);
    User user = getUser(id, optional);

    LOGGER.info("found user '{}'", id);
    return resourceMapper.convertUser(user);
  }

  public List<UserReadDto> retrieve(
      final PageRequest pageRequest,
      final HttpServletResponse httpResponse
  ){
    final Page<User> userPage = userRepository.findAll(pageRequest);
    final List<UserReadDto> users = resourceMapper.convertUsers(userPage.getContent());
    LOGGER.info("found {} user(s)", users.size());

    PageUtil.updatePageHeaders(httpResponse, userPage, pageRequest);
    return users;
  }

  private User getUser(final String id, final Optional<User> user){

    if(user.isEmpty()){
      final String message = String.format("user '%s' doesn't exist", id);
      LOGGER.warn(message);
      throw new DataNotFoundException(message);
    }
    return user.get();
  }
}
