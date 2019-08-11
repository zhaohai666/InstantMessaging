package com.zhaohai.cn.mapper;

import com.zhaohai.cn.entity.TbChatRecord;
import com.zhaohai.cn.entity.TbChatRecordExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TbChatRecordMapper {
    long countByExample(TbChatRecordExample example);

    int deleteByExample(TbChatRecordExample example);

    int deleteByPrimaryKey(String id);

    int insert(TbChatRecord record);

    int insertSelective(TbChatRecord record);

    List<TbChatRecord> selectByExample(TbChatRecordExample example);

    TbChatRecord selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") TbChatRecord record, @Param("example") TbChatRecordExample example);

    int updateByExample(@Param("record") TbChatRecord record, @Param("example") TbChatRecordExample example);

    int updateByPrimaryKeySelective(TbChatRecord record);

    int updateByPrimaryKey(TbChatRecord record);
}