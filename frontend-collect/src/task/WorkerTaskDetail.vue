<template>
  <div style="background-color: white; padding-bottom: 10px">
    <el-skeleton v-if="loading" :rows="7" animated/>
    <task-card v-else :task-view="task" :show-detail="true" shadow="never">
      <template #footer>
        <el-row style="margin-top: 10px">
          <el-button class="f1 task-btn" size="large" plain type="warning" v-if="task.state===0" @click="undertakeTask">承接任务</el-button>
          <el-button class="f1 task-btn" size="large" plain type="info" round v-if="task.state===1"   @click="directReportIssue">进行中，提交报告</el-button>
          <el-button class="f1 task-btn" size="large" plain type="info" round v-if="task.state===4"   @click="directModifyReport">完善报告</el-button>
        </el-row>
      </template>
    </task-card>
    <div v-if="isTaskFinished" style="padding: 15px">
      <el-divider content-position="left">
        <span class="inline-title">
          我的报告
        </span>
      </el-divider>
      <report-card v-if="!loadingMyReport" :report-view="myReport"></report-card>
      <el-skeleton  v-else :rows="5" animated/>
      <el-divider content-position="left">
        <span class="inline-title">
          所有报告
        </span>
      </el-divider>
      <report-list :scroll="scroll"></report-list>
    </div>

  </div>
</template>

<script>
// import TaskCard from "@/components/components/TaskCard";
import {getTaskDetail, pickTask,getReportId} from "@/api/TaskApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {storageGet} from "@/utils/util";
import ReportList from "@/report/ReportList";
import ReportCard from "@/components/ReportCard";
import {getWorkerReport} from "@/api/UserApi";
import {TASK_COLOR, TASK_STATE_FINISHED} from "@/const/Task";
import TaskCard from "@/components/TaskCard";

export default {
  name: "WorkerTaskDetail",
  components: {TaskCard, ReportCard, ReportList},
  // components: {TaskCard},
  emits: [SHOW_ALERT],
  props: ['scroll'],
  data:()=>{
    return{
      task:{
        id:0,//this.$route.query.taskId
        userId: 0,
        type: 1,
        title: "",
        description: "",
        startTime: "",
        endTime: "",
        workerNum: 0,
        state:-1,
        reqDocName:"",
        exeFileName:"",
      },
      loading: false,
      loadingMyReport: false,
      myReport: {}
    }
  },
  methods:{
    undertakeTask(){
      const payload={
        taskId:this.taskId,
        userId:this.userId
      };
      pickTask(payload).then(res=>{
        if (res.code === SUCCESS) {
          this.task=res.data;
        }
        this.$emit(SHOW_ALERT,res);
      })
    },
    directReportIssue(){
      this.$router.push({
        path: "/worker/issueReport",
        query:{taskId:this.taskId}
      });
    },
    directModifyReport(){
      const payload={
        taskId:this.taskId,
        userId:this.userId
      };
      getReportId(payload).then(res=>{
        if (res.code === SUCCESS) {
          this.$router.push({
            path:"/worker/modifyReport",
            query:{reportId:res.data}
          });
        }else{
          this.$emit(SHOW_ALERT,res);
        }
      })
    },
    loadWorkerTaskDetail(){
      if(!this.loading){
        this.loading = true;
        const payload={
          userId:this.userId,
          taskId:this.taskId,
        }
        getTaskDetail(payload).then(res=>{
          this.task=res.data;
        }).finally(() => {
          this.loading = false;
        })
      }
    },
    loadMyReport(){
      this.loadingMyReport = true
      getWorkerReport(this.userId, this.taskId)
      .then(res => {
        if(res.code === SUCCESS){
          this.myReport = res.data;
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      }).finally(() => {
        this.loadingMyReport = false
      })
    }
  },
  mounted() {
    this.loadWorkerTaskDetail();
  },
  computed: {
    taskId(){
      return this.$route.query.taskId;
    },
    userId(){
      return Number.parseInt(storageGet("id"));
    },
    isTaskFinished(){
      return this.task.state === TASK_STATE_FINISHED;
    },
    taskColor() {
      return TASK_COLOR[this.task.type]
    },
  },
  watch: {
    isTaskFinished(isFinished){
      if(isFinished){
        this.loadMyReport()
      }
    },
    scroll(){
      console.log('scroll')
    }
  }
}
</script>

<style scoped>
.task-btn{
  border-radius: 5px;
}
</style>


<!--<template>-->
<!--  <div style="display: flex;flex-direction: column;align-items: center">-->
<!--    <div style="font-size: xx-large;margin-bottom: 10px">{{reportTitle}}</div>-->
<!--    <flaw-view-card style="margin-top:30px" v-for="flaw in flaws" :flaw-view="flaw" :key="flaw.flawId"></flaw-view-card>-->
<!--  </div>-->
<!--</template>-->

<!--<script>-->
<!--import FlawViewCard from "@/flaws/FlawViewCard";-->
<!--export default {-->
<!--  name: "ReportDetail",-->
<!--  components:{FlawViewCard},-->
<!--  data:()=>{-->
<!--    return{-->
<!--      reportTitle:"报告标题",-->
<!--      flaws:[-->
<!--        {-->
<!--          flawId:1,-->
<!--          description:"这是缺陷描述"-->
<!--        },-->
<!--        {-->
<!--          flawId: 2,-->
<!--          description: "这也是缺陷描述"-->
<!--        },-->
<!--        {-->
<!--          flawId: 3,-->
<!--          description: "这还是缺陷描述"-->
<!--        }-->
<!--      ]-->

<!--    }-->

<!--  }-->
<!--}-->
<!--</script>-->

<!--<style scoped>-->

<!--</style>-->
