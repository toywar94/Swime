package com.swime.mapper;

import com.swime.domain.GroupVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndexDataMapper {

    List<GroupVO> popularGroupList();

    List<GroupVO> popularStudyList();

    List<GroupVO> test(@Param("pageNum") int pageNum, @Param("amount") int amount);
}
