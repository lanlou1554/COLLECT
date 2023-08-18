<template>
    <el-card>
      <template #header>
        <div style="display: flex;justify-content: space-between;align-items: center; font-size: larger; ">
          <span>{{ report.title }}</span>
          <score-view :score="report.score"></score-view>
        </div>
      </template>
      <report-flaw-item @collect:score-change="loadReportDetail" :is-my-report="isMyReport" @collect:alert="$emit(SHOW_ALERT, $event)" v-for="flaw in report.flaws" :key="flaw.id" :flaw="flaw" style="margin-bottom: 30px"/>
    </el-card>
</template>

<script>
import { getReportDetail } from "@/api/ReportApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SET_MAX_WIDTH, SHOW_ALERT} from "@/const/Events";
import ReportFlawItem from "@/flaw/ReportFlawItem";
import ScoreView from "@/components/ScoreView";
import {storageGet} from "@/utils/util";

export default {
  name: "ReportDetail",
  components: {ScoreView, ReportFlawItem},
  data: () => {
    return {
      report: {
        id:0,
        taskId: 0,
        userId: 0,
        title: "",
        flaws: [],
        score: 1
      },
      SHOW_ALERT
    }
  },
  methods:{
    loadReportDetail(){
      const reportId=this.$route.query.reportId;
      getReportDetail({reportId}).then(res=>{
        if(res.code===SUCCESS){
          //console.log(res)
          this.report=res.data;
          if(this.$route.query.flawId){
            setTimeout(() => {
              document.getElementById(`flaw#${this.$route.query.flawId}`).scrollIntoView({
                behavior: "smooth"
              })
            }, 100)

          }
        }
      })
    }
  },
  mounted(){
    this.$emit(SET_MAX_WIDTH, 1600)
    this.loadReportDetail();
  },
  watch: {
    $route(){
      this.loadReportDetail()
    }
  },
  computed: {
    isMyReport(){
      return this.report.userId === this.userId
    },
    userId(){
      return Number.parseInt(storageGet('id'))
    }
  }
}
</script>

<style scoped>

</style>
