<template>
  <el-card
      style="border: unset"
      :shadow="shadow">
    <el-row class="al-it-center">
      <el-row class="col-container al-it-center" style="flex: 2 0 300px; margin-bottom: 2px">
        <el-tag type="info" style="margin-right: 5px;" v-if="showState">
          {{ taskState }}
        </el-tag>
        <div class="text-break title-m col-container al-item-center" style="flex: 8 0 300px">
          {{ taskView.title }}
        </div>
        <!--       <el-button type="primary" @click="directReportIssue">进行中，提交报告</el-button>-->
      </el-row>
      <el-space wrap>
        <el-tag
            :color="taskColor"
            :hit="false"
            type="info"
            style="margin-bottom: 0"
        >
          <span style="color: white">{{ taskType }}</span>
        </el-tag>
        <el-tag
            v-for="tag in tags"
            :key="tag.id"
            type="info"
        >
          {{ tag.name }}
        </el-tag>
      </el-space>
    </el-row>
    <el-divider></el-divider>
    <task-view-info-block v-if="!showDetail" :task-view="taskView"></task-view-info-block>
    <task-detail-info-block v-else :task="taskView"></task-detail-info-block>
    <slot name="footer"></slot>
  </el-card>
</template>

<script>
import {formatDate, tagFilter} from "@/utils/util";
import {TASK_COLOR, TASK_STATE_UNPICKED, TASK_STATES, TASK_TYPE} from "@/const/Task";
import {TaskTags} from "@/const/Tags";
import TaskViewInfoBlock from "@/components/TaskViewInfoBlock";
import TaskDetailInfoBlock from "@/components/TaskDetailInfoBlock";

export default {
  name: "TaskCard",
  components: {TaskDetailInfoBlock, TaskViewInfoBlock},
  props: {
    taskView: {
    },
    showDetail: {
      default: false
    },
    shadow: {
      default: 'hover'
    }
  },
  data(){
    return {

    }
  },
  computed: {
    taskType() {
      return TASK_TYPE[this.taskView.type]
    },
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
  },
  mounted() {


  }
}
</script>

<style scoped>
.task-card{
  min-height: 200px;
  min-width: 300px;
}
.task-card:hover {
  /*opacity: 70%;*/
  /*background-color: #eeeeee;*/
  /*color: rgba(255, 255, 255, 0.85);*/
  box-shadow: rgba(0, 0, 0, 0.1) 0 0 7px 7px;
}
.task-type{
  margin-right: 10px;
  margin-left: 10px;
  color: white;
  font-size: small;
  font-weight: bold;
  width: 70px;
  height: 25px
}
</style>
