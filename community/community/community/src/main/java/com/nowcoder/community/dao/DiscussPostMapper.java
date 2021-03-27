package com.nowcoder.community.dao;

import com.nowcoder.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DiscussPostMapper {

    List<DiscussPost> selectDiscussPosts(int userId, int offset, int limit);    // userId 用户，offset 起始行号， limit 每页显示多少

    // @Param 用于给参数起别名
    // 如果方法只有一个参数，并且在<if>里使用，则必须加别名
    int selectDiscussPostRows(@Param("userId") int userId); // 查询讨论帖子行数，使用@Param 来起别名（用于动态拼接条件，并且方法只有一个条件）






}
