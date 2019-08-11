package com.zhaohai.cn.mapper;

import com.zhaohai.cn.entity.TbFriend;
import com.zhaohai.cn.entity.TbFriendExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TbFriendMapper {
    long countByExample(TbFriendExample example);

    int deleteByExample(TbFriendExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbFriend record);

    int insertSelective(TbFriend record);

    List<TbFriend> selectByExample(TbFriendExample example);

    TbFriend selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbFriend record, @Param("example") TbFriendExample example);

    int updateByExample(@Param("record") TbFriend record, @Param("example") TbFriendExample example);

    int updateByPrimaryKeySelective(TbFriend record);

    int updateByPrimaryKey(TbFriend record);
}