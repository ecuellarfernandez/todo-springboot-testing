package com.todoapp.task.dto;

import java.util.List;

public class TaskReorderDTO {
    private List<String> taskIds;

    public List<String> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<String> taskIds) {
        this.taskIds = taskIds;
    }
}
