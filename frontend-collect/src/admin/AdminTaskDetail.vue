<template>
  <task-card :task-view="taskVO" :show-detail="true"/>
  <report-card style="margin: 10px 0" v-for="report in reportList" :key="report" :report-view="report"></report-card>
</template>

<script>
import TaskCard from "@/components/TaskCard";
import ReportCard from "@/components/ReportCard";
import {getTaskReportView} from "@/api/UserApi";
import {getTaskDetailByAdmin} from "@/api/AdminApi";
import {storageGet} from "@/utils/util";
export default {
  name: "AdminTaskDetail",
  components: { ReportCard, TaskCard},
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
        workerNum: 0,
        reqDocName:"",
        exeFileName:"",
        state:0,
        createTime:""
      },
      reportList: [],
      pageId:1,
      loading: false
    }
  },
  methods:{
    loadTaskReportView(){
      if(!this.loading){
        this.loading = true;
        const payload={
          userId:this.userId,
          taskId:this.taskVO.id,
          pageId:this.pageId
        };
        getTaskReportView(payload).then(res=>{
          if(res.data.length !== 0){
            this.reportList.push(...res.data);
            this.pageId++
          }

        }).finally(() => {
          this.loading = false;
        });
      }

    },
    loadTaskDetail(){
      this.taskVO.id=this.$route.query.taskId;
      const taskId=this.taskVO.id;
      getTaskDetailByAdmin(taskId).then(res=>{
        this.taskVO=res.data;
      })
    }
  },
  watch:{
    scroll(){
      this.loadTaskReportView();
    }

  },
  computed:{
    userId(){
      return Number.parseInt(storageGet("id"));
    }
  },
  mounted() {
    this.loadTaskDetail();
    this.loadTaskReportView();
  }
}
</script>

<style scoped>

</style>
