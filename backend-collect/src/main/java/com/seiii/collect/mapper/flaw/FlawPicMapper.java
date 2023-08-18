package com.seiii.collect.mapper.flaw;

import com.seiii.collect.model.po.flaw.FlawPic;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface FlawPicMapper {
    int deleteByPrimaryKey(@Param("flawid") Integer flawid, @Param("pictureurl") String pictureurl);

    int insert(FlawPic record);

    List<FlawPic> selectAll();

    List<String> selectByFlawId(Integer flawid);
}