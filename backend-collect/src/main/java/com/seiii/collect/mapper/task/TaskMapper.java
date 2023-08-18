package com.seiii.collect.mapper.task;

import com.seiii.collect.model.po.task.Task;
import com.seiii.collect.model.po.task.TaskView;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Task record);

    Task selectByPrimaryKey(Integer id);

    List<TaskView> selectByUserIdAndAfterEndTime(@Param("userid") Integer userid, @Param("curtime")Date curtime, @Param("start") Integer start, @Param("pagesize") Integer pagesize);

    List<TaskView> selectByUserIdAndBeforeEndTime(@Param("userid") Integer userid, @Param("curtime")Date curtime, @Param("start") Integer start, @Param("pagesize") Integer pagesize);

    List<TaskView> selectBeforeEndTimeAndAfterStartTime(@Param("curtime")Date curtime, @Param("start") Integer start, @Param("pagesize") Integer pagesize);

    List<Task> selectAll();

    int updateByPrimaryKey(Task record);

    //为了减少请求次数
    //TODO ids一定不能为null
    List<TaskView> selectByIds(List<Integer> ids);

    List<TaskView> selectAllByPageId(@Param("start") Integer start, @Param("pagesize") Integer pagesize);

    int updateRecruitStopByPrimaryKey(Integer id);

}