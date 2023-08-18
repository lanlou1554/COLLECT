<template>
  <div style="background-color: white">
    <el-skeleton animated v-if="loadingTaskCard" :row="8"></el-skeleton>
    <task-card :shadow="'never'" v-else  :task-view="taskVO" :show-detail="true" style="border: unset">
      <template #footer>
        <el-row>
          <el-button style="margin-top: 3px" class="f1" size="large" type="danger" v-if="canStopRecruiting" @click="dialogVisible = true">结束招募</el-button>
        </el-row>
  </template>
    </task-card>
    <div style="padding: 20px" id="employerTaskDetail">
      <el-tabs type="card" v-model="currentTab">
        <el-tab-pane label="缺陷图谱" name="FlawVisualizer"/>
        <el-tab-pane label="报告列表" name="ReportList"/>
        <el-tab-pane label="工人信息" name="TaskWorkerInfo" v-if="taskVO.state !== 5"/>
        <el-tab-pane label="缺陷曲线" name="BugArrivalCurve"/>
      </el-tabs>
      <router-view :scroll="scroll" @collect:alert="$emit(SHOW_ALERT, $event)" :task-v-o="taskVO"></router-view>
    </div>
    <el-dialog
        v-model="dialogVisible"
        title="请确认！"
    >
      <div>确定要结束招募吗？</div>
      <div style="display: flex; flex-direction: row-reverse; margin-top: 10px">
        <el-button @click="stopRecruiting()" type="danger">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import TaskCard from "@/components/TaskCard";
import {storageGet} from "@/utils/util";
import {getTaskDetail, stopRecruiting} from "@/api/TaskApi";
import {SHOW_ALERT} from "@/const/Events";
import {TASK_STATE_DURING_RECRUIT} from "@/const/Task";
import {SUCCESS} from "@/const/ResponseStatus";
export default {
  name: "EmployerReportList",
  components: {TaskCard},
  props: {
    scroll:{
      default:1
    }
  },

  data: () => {
    return {
      taskVO: {
        id:0,//this.$route.query.taskId
        userId: 0,
        type: 1,
        title: "",
        description: "",
        startTime: "",
        endTime: "",
        workerNum: 1,
        reqDocName:"",
        exeFileName:"",
        state:5,
        createTime:"",
      },
      loadingTaskCard: false,
      currentTab: '',
      SHOW_ALERT,
      dialogVisible: false,
      TASK_STATE_DURING_RECRUIT
    }
  },
  methods:{
    loadTaskDetail(){
      this.taskVO.id=this.$route.query.taskId;
      const payload={
        userId:this.userId,
        taskId:this.taskVO.id
      }
      this.loadingTaskCard = true;
      getTaskDetail(payload).then(res=>{
        this.taskVO=res.data;
        this.loadingTaskCard = false;
      })
    },
    stopRecruiting(){
      stopRecruiting(this.taskVO.id).then(res => {
        if(res.code === SUCCESS){
          this.dialogVisible = false;
          this.loadTaskDetail();
        }
        this.$emit(SHOW_ALERT, res)
      })
    }
  },
  computed:{
    userId(){
      return Number.parseInt(storageGet("id"));
    },
    canStopRecruiting(){
      return this.taskVO.state === TASK_STATE_DURING_RECRUIT && this.taskVO.userId === this.userId;
    }
  },
  mounted() {
    this.loadTaskDetail();
    this.currentTab = 'FlawVisualizer'
  },
  watch:{
    currentTab(){
      this.$router.push({
        name: this.currentTab,
        query: {
          taskId:this.$route.query.taskId
        }
      })
    },
    $route(to){
      this.currentTab = to.name
    }
  }
}
</script>

<style scoped>

</style>
