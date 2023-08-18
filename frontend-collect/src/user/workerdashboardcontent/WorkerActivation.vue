<template>
  <el-descriptions v-loading="isLoading"
                   element-loading-text="加载需要几秒，请耐心等待……"
                   title="您的活跃度" :column="4" border>
    <el-descriptions-item label="近三天提交报告数"  align="center">{{lastThreeDaySubmitNum}}</el-descriptions-item>
    <el-descriptions-item label="近两周提交报告数" align="center">{{lastTwoWeeksSubmitNum}}</el-descriptions-item>
    <el-descriptions-item label="近一月提交报告数" align="center">{{lastOneMonthSubmitNum}}</el-descriptions-item>
    <el-descriptions-item label="近半年提交报告数" align="center">{{lastHalfYearSubmitNum}}</el-descriptions-item>
    <el-descriptions-item label="最后提交日期" align="center" :contentStyle="{'text-align': 'right'}">{{lastSubmitTimeFormat}}</el-descriptions-item>
    <el-descriptions-item label="综合评分" class-name="my-content" align="center" >{{score}}分</el-descriptions-item>


  </el-descriptions>
</template>

<script>
import {storageGet} from "@/utils/util";
import {formatDate} from "@/utils/util";
import {viewUserActivation} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'

export default {
  name: "WorkerActivation",
  data(){
    return{
      lastSubmitTime: "",
      lastThreeDaySubmitNum: 0,
      lastTwoWeeksSubmitNum: 0,
      lastOneMonthSubmitNum: 0,
      lastHalfYearSubmitNum: 0,
      score: 0,
      isLoading: false
    }
  },
  computed: {
    userId(){
      return Number.parseInt(storageGet('id'))
    },
    lastSubmitTimeFormat(){
      if (this.lastSubmitTime === null || this.lastSubmitTime === ""){
        return "未提交报告"
      }
      return formatDate(new Date(this.lastSubmitTime))
    }
  },
  mounted() {
    this.isLoading = true
    viewUserActivation(this.userId).then(res=>{
      if (res.code === SUCCESS){
        this.lastSubmitTime = res.data.lastSubmitTime;
        this.lastThreeDaySubmitNum = res.data.lastThreeDaySubmitNum;
        this.lastTwoWeeksSubmitNum = res.data.lastTwoWeekSubmitNum;
        this.lastOneMonthSubmitNum = res.data.lastOneMonthSubmitNum;
        this.lastHalfYearSubmitNum = res.data.lastHalfYearSubmitNum;
        this.score = res.data.score
        this.isLoading = false
      }else{
        this.showAlert(res);
      }
    })
  },
  methods:{
    showAlert( event ){
      console.log('alert')
      let duration = 1000;
      if(event){
        if(event.alertDuration)duration = event.alertDuration;
        if(event.msg)this.alertMsg = event.msg;
        else if(event.message)this.alertMsg = event.message;
        if(event.type)this.alertType = event.type;
        else if(event.code !== undefined && event.code !== null)this.alertType = STATUS_2_TYPE[event.code];
        console.log(event)
        ElMessage({
          message: this.alertMsg,
          type: this.alertType,
          duration: duration
        })
      }

      this.alert = true;
      setTimeout(() => {
        console.log('-alert')
        this.alert = false;
      }, duration)
    }
  }
}
</script>

<style>
  .my-content {
    background: #E1F3D8;
  }
</style>
