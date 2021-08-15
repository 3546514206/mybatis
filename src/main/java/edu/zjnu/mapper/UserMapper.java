package edu.zjnu.mapper;

import edu.zjnu.model.User;

/**
 * @author 杨海波
 * @description 用户Mapper
 * @date 2021-02-22 11:15
 */
public interface UserMapper {

    User selectById(Long id);

    User selectAll(Long id);

}
