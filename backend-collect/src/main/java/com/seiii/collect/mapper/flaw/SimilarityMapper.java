package com.seiii.collect.mapper.flaw;

import com.seiii.collect.model.po.flaw.Similarity;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SimilarityMapper {
    int deleteByPrimaryKey(@Param("flawid1") Integer flawid1, @Param("flawid2") Integer flawid2);

    int insert(Similarity record);

    Similarity selectByPrimaryKey(@Param("flawid1") Integer flawid1, @Param("flawid2") Integer flawid2);

    List<Similarity> selectAll();

    int updateByPrimaryKey(Similarity record);

    //用当前flawId查找比此flaw先插入且有相似度的记录
    List<Similarity> selectByFlawIdCompared(@Param("flawid1") Integer flawid1);

    //用当前flawId查找比此flaw后插入且有相似度的记录
    List<Similarity> selectByFlawIdSecondCompared(@Param("flawid2") Integer flawid2);
}