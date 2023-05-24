package com.bravo.user.dao.model.mapper;

import com.bravo.user.dao.model.User;
import com.bravo.user.model.dto.UserReadDto;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import ma.glasnost.orika.MapperFacade;
import org.springframework.stereotype.Component;

@Component
public class ResourceMapper {

  private final MapperFacade mapperFacade;

  public ResourceMapper(MapperFacade mapperFacade) {
    this.mapperFacade = mapperFacade;
  }

  public <T extends Collection<User>> List<UserReadDto> convertUsers(final T users){
    return users.stream().map(this::convertUser).collect(Collectors.toList());
  }

  public UserReadDto convertUser(final User user){
    final UserReadDto dto = mapperFacade.map(user, UserReadDto.class);
    dto.setName(
        String.format("%s%s %s",
            user.getFirstName(),
            user.getMiddleName() != null ? String.format(" %s", user.getMiddleName()) : "",
            user.getLastName()
        )
    );
    return dto;
  }
}
