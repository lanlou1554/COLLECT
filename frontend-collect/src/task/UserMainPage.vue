<template>
  <el-tabs v-model="currentTab">
    <el-tab-pane label="任务广场" class="tab-pane" :name="''">
      <task-list  :visable="currentTab === '0'" :scroll="scroll" :load-tasks="loadPlaza"></task-list>
    </el-tab-pane>
    <el-tab-pane label="正在进行" class="tab-pane">
      <task-list :visable="currentTab === '1'" :scroll="scroll" :load-tasks="loadOngoing"></task-list>
    </el-tab-pane>
    <el-tab-pane label="已完成" class="tab-pane">
      <task-list :visable="currentTab === '2'" :scroll="scroll" :load-tasks="loadFinish"></task-list>
    </el-tab-pane>
    <el-tab-pane label="已逾期" class="tab-pane" v-if="isWorker">
      <task-list :visable="currentTab === '3'" :scroll="scroll" :load-tasks="loadLate"></task-list>
    </el-tab-pane>
  </el-tabs>

</template>

<script>
import {getTaskRecruiting} from "@/api/TaskApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {getTaskExpired, getTaskFinished, getTaskUnfinished} from "@/api/UserApi";
import TaskList from "@/components/TaskList";
import {WORKER} from "@/const/UserRole";
import {storageGet} from "@/utils/util";
export default {
  name: "UserMainPage",
  props:{
    scroll:{
      default:1
    }
  },
  setup(){
    const loadPlaza = (pageId) => {
      return getTaskRecruiting({pageId, userId: storageGet('id')}).then(res=>{
        if(res.code === SUCCESS)return res.data;
        else {
          this.$emit(SHOW_ALERT, res);
        }
      })
    }
    const loadOngoing = (pageId) => {
      return getTaskUnfinished({pageId, userId: storageGet('id')}).then(res=>{
        if(res.code === SUCCESS)return res.data;
        else {
          this.$emit(SHOW_ALERT, res);
        }
      })
    }
    const loadFinish = (pageId) => {
      return getTaskFinished({pageId, userId: storageGet('id')}).then(res=>{
        if(res.code === SUCCESS)return res.data;
        else {
          this.$emit(SHOW_ALERT, res);
        }
      })
    }
    const loadLate = (pageId) => {
      return getTaskExpired({pageId, userId: storageGet('id')}).then(res=>{
        if(res.code === SUCCESS)return res.data;
        else {
          this.$emit(SHOW_ALERT, res);
        }
      })
    }
    const isWorker=Number.parseInt(storageGet("type"))===WORKER
    return { loadPlaza, loadOngoing, loadFinish, loadLate ,isWorker}
  },
  components: {TaskList},
  data: ()=>{
    return {
      currentTab: '0',
    }
  },
  watch: {
    currentTab(newVal){
      console.log(typeof newVal)
    }
  }
}
</script>
<style scoped>
.tab-pane{
  padding-bottom: 20px;
}
</style>
