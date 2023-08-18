<template>
  <div>
    <el-descriptions :column="2" border>
      <el-descriptions-item :span="2" label="任务简介">{{task.description}}</el-descriptions-item>
      <el-descriptions-item label="开始时间">{{ startTime }}</el-descriptions-item>
      <el-descriptions-item label="结束时间">{{ endTime }}</el-descriptions-item>
      <el-descriptions-item label="所需工人">{{task.workerNum}}</el-descriptions-item>
      <el-descriptions-item label="已招募工人">{{ task.pickedWorkerNum }}</el-descriptions-item>
    </el-descriptions>
    <el-row style="margin-top: 30px; border-radius: 5px; overflow: hidden; padding: 0">
      <el-button
          size="large"
          :color="taskColor"
          class="download-btn f1"
          @click="download(task.reqDocName)">
        <el-icon>
          <ic-download/>
        </el-icon>
        <span>
          测试需求描述文件
        </span>

      </el-button>
      <el-button
          size="large"
          :color="taskColor"
          class="download-btn f1"
          @click="download(task.exeFileName)">
        <el-icon>
          <ic-download/>
        </el-icon>
        <span>
          测试待测软件包
        </span>
      </el-button>
    </el-row>
  </div>
</template>

<script>
import {download, formatDate} from "@/utils/util";
import {TASK_COLOR} from "@/const/Task";

export default {
  name: "TaskDetailInfoBlock",
  props: ['task'],
  computed: {
    startTime(){
      return formatDate(new Date(this.task.startTime))
    },
    endTime(){
      return formatDate(new Date(this.task.endTime))
    },
    taskColor() {
      return TASK_COLOR[this.task.type]
    },
  },
  methods: {
    download(url){
      download(url);
    }
  }
}
</script>

<style scoped>
.download-btn{
  color: white;
  border-radius: 5px;
  margin: 1px;
  font-size: medium;
}
</style>
