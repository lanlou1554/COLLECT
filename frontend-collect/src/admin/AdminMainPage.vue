<template>
  <el-tabs v-model="currentTab">
    <el-tab-pane label="所有任务" class="tab-pane" :name="''">
      <task-list  :visable="currentTab === '0'" :scroll="scroll" :load-tasks="loadAllTasks"></task-list>
    </el-tab-pane>
    <el-tab-pane label="更改推荐规则" class="tab-pane">
      <recommend-edit-component :visable="currentTab === '1'" ></recommend-edit-component>
    </el-tab-pane>
  </el-tabs>
</template>

<script>
import {getAllTasksByAdmin} from "@/api/AdminApi";
import TaskList from "@/components/TaskList";
import RecommendEditComponent from "@/components/RecommendEditComponent";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
export default {
  name: "AdminMainPage",
  components: {TaskList,RecommendEditComponent},
  props:{
    scroll:{
      default:1
    }
  },
  setup(){
    const loadAllTasks = (pageId) => {
      return getAllTasksByAdmin(pageId).then(res=>{
        if(res.code === SUCCESS)return res.data;
        else {
          this.$emit(SHOW_ALERT, res);
        }
      })
    }
    return { loadAllTasks }
  },
  data: ()=>{
    return {
      currentTab: '0',
    }
  }
}
</script>

<style scoped>
.tab-pane{
  padding-bottom: 20px;
}
</style>
