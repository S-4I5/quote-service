package xyz.s4i5.quoteservice.mapper;

import org.mapstruct.Mapper;
import xyz.s4i5.quoteservice.model.entity.VkUser;
import xyz.s4i5.quoteservice.model.dto.client.user.VkUserDto;

@Mapper
public interface VkUserMapper {
    VkUser toModel(VkUserDto dto);
}