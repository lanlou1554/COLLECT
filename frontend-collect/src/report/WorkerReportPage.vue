<template>
    <report-card v-for="reportViewVO in reportViewVOList" :key="reportViewVO.id" :report-view="reportViewVO" style="margin: 10px">

    </report-card>
  <el-empty description="没有任何报告，快到任务广场选择适合你的任务！" v-if="empty" @click="$router.push('/')">
  </el-empty>
</template>

<script>
import ReportCard from "@/components/ReportCard";
import {getUserReportViewVO} from "@/api/UserApi";
import {storageGet} from "@/utils/util";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
export default {
  name: "WorkerReportPage",
  components: {ReportCard},
  pros:{
    scroll:{
      default:1
    }
  },
  data: () => {
    return {
      reportViewVOList: [],
      pageId:1
    }
  },
  methods: {
    loadReportViewVO(){
      const payload={
        userId:storageGet('id'),
        pageId:this.pageId
      }
      getUserReportViewVO(payload)
      .then(res => {
        if(res.code === SUCCESS){
          this.reportViewVOList.push(...res.data);
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })
    }
  },
  watch:{
    scroll(){
      this.pageId++;
      this.loadReportViewVO();
    }
  },
  mounted() {
    this.loadReportViewVO();
  },
  computed: {
    empty() {
      return !this.reportViewVOList || this.reportViewVOList.length === 0
    }
  }
}
</script>

<style scoped>

</style>
