package com.seiii.collect.serviceimpl.recommend.prioritytask;

import com.seiii.collect.model.vo.task.TaskViewVO;

public class PriorityTask implements Comparable<PriorityTask> {
    private double priority;
    private final TaskViewVO task;

    public PriorityTask(TaskViewVO task) {
        priority = 0;
        this.task = task;
    }

    public TaskViewVO getTask() {
        return task;
    }

    public void addPriorityBy(double priority) {
        this.priority += priority;
    }

    @Override
    public int compareTo(PriorityTask o) {
        return Double.compare(o.priority, this.priority);
    }
}
