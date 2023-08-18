<template>
  <el-card shadow="never">
    <task-weight-info :task-v-o="taskVO"></task-weight-info>
    <task-radar style="margin-top: 20px"></task-radar>
  </el-card>
</template>

<script>
import TaskWeightInfo from '@/task/taskstatistics/TaskWeightInfo'
import TaskRadar from "@/task/taskstatistics/TaskRadar";
export default {
  name: "TaskWorkerInfo",
  props: ['taskVO'],
  components: {TaskRadar, TaskWeightInfo}

}
</script>

<style scoped>

</style>
