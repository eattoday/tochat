package com.dawu.tochat.mapper;

import com.dawu.tochat.domain.SpUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @author guohaotian
 * @description 针对表【sp_user】的数据库操作Mapper
 * @createDate 2021-11-26 11:40:08
 * @Entity com.dawu.tochat.domain.SpUser
 */
@Component
public interface SpUserMapper extends BaseMapper<SpUser> {

}




