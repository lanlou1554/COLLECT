<template>
  <report-card style="margin: 10px 0" v-for="report in reportList" :key="report.id" :report-view="report"></report-card>
  <el-container v-if="loading" v-loading="loading" style="padding: 40px 0; height: 442px"/>
  <el-empty description="没有报告" v-if="empty" style="height: 442px"/>
</template>

<script>
import {getTaskReportView, getUserInfoList} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {storageGet} from "@/utils/util";
import ReportCard from "@/components/ReportCard";

export default {
  name: "ReportList",
  components: {ReportCard},
  props: ['scroll'],
  data(){
    return {
      reportList: [],
      pageId:1,
      loading: false,
    }
  },
  methods: {
    loadTaskReportView(){
      if(!this.loading){
        this.loading = true;
        const payload={
          userId:this.userId,
          taskId:this.taskId,
          pageId:this.pageId
        };
        getTaskReportView(payload).then(res=>{
          if(res.code===SUCCESS){
            if(res.data.length !== 0){

              return res.data;
            }

          }
          else{
            this.$emit(SHOW_ALERT,res);
          }
        }).then(async (reportViewList) => {
          let idList = reportViewList.map(reportView => {return reportView.userId});
          let res =  await getUserInfoList(idList);
          if(res.code === SUCCESS){
            for(let i = 0; i < idList.length; i++){
              reportViewList[i].username = res.data[i].username;
            }
            this.reportList.push(...reportViewList);
            this.pageId++;
          }else{
            this.$emit(SHOW_ALERT, res);
          }

        }).finally(() => {
          this.loading = false;
        });
      }
    },
  },
  computed: {
    taskId(){
      return this.$route.query.taskId
    },
    userId(){
      return Number.parseInt(storageGet("id"));
    },
    empty(){
      return !this.loading && (!this.reportList || this.reportList.length === 0)
    }
  },
  mounted() {
    this.loadTaskReportView()
  },
  watch:{
    scroll(){
      this.loadTaskReportView();
    }
  },
}
</script>

<style scoped>

</style>
