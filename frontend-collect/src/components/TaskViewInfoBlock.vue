<template>
  <div class="description f8 text-break">{{ taskView.description }}</div>
  <div class="task-info col-container">
    <div class="task-time row-container f6 space-even" :style="{backgroundColor: taskColor}">
      <div class="end-time">{{ endTime }}截止</div>
    </div>
    <div class="worker-num col-container f4 al-it-center" :style="{backgroundColor: taskColor}">
      <div class="centertext card-s" >
        <span>招募{{ `${taskView.pickedWorkerNum} / ${taskView.workerNum}` }}人</span>
      </div>
      <el-progress
          style="border-radius: 20px; border: solid #e5e9f2;"
          type="circle" stroke-linecap="butt" :color="taskColor" :percentage="process" :show-text="false" :width="20" :stroke-width="10">
      </el-progress>
    </div>
  </div>
</template>

<script>
import {TASK_COLOR, TASK_STATE_UNPICKED, TASK_STATES} from "@/const/Task";
import {TaskTags} from "@/const/Tags";
import {formatDate, tagFilter} from "@/utils/util";

export default {
  name: "TaskViewInfoBlock",
  props: ['taskView'],
  computed: {
    taskColor() {
      return TASK_COLOR[this.taskView.type]
    },
    process(){
      return Number((this.taskView.pickedWorkerNum / this.taskView.workerNum) * 100)
    },
    tags(){
      if(this.taskView.tagGroups){
        return this.taskView.tagGroups.map(id => {
          return TaskTags[id]
        }).filter(tagFilter)
      }else{
        return []
      }
    },
    endTime(){
      return formatDate(new Date(this.taskView.endTime))
    },
    showState(){
      return this.taskView.state && this.taskView.state !== TASK_STATE_UNPICKED
    },
    taskState(){
      return TASK_STATES[this.taskView.state]
    }
  }
}
</script>

<style scoped>

.end-time{
  margin-left: 15px;
}
.description{
  margin-bottom: 30px;
  margin-left: 15px;
  margin-right: 15px;
}
.task-time{
  margin-right: 1px;
  padding-top: 10px;
  padding-bottom: 10px
}
.task-info{
  overflow: hidden;
  border-radius: 5px;
  color: white;
}
.worker-num{
  margin-left: 1px;
  justify-content: center;
}

@media (max-width: 580px) {
  .task-info{
    flex-direction: column;
  }
  .task-time{
    margin-right: 0;
    margin-bottom: 1px;
  }
  .worker-num{
    margin-left: 0;
    margin-top: 1px;
  }
  .end-time{
    margin-left: 0;
    text-align: center;

  }
}
</style>
