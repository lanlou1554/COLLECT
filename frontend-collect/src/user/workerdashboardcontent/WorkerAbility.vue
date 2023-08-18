<template>
  <el-card   v-loading="isLoading"
             element-loading-text="加载需要几秒，请耐心等待………">
    <div :id="randomId" :style="{ width: '600px', height: '600px',marginTop:'25px'}"></div>
  </el-card>
</template>

<script>
import * as echarts from 'echarts'
import {viewUserRadar,viewAvgRadar} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'
import {getRandomId, storageGet} from "@/utils/util";


export default {
  name: 'WorkerAbility',
  computed: {
    userId(){
      return Number.parseInt(storageGet('id'))
    }
  },
  data() {
    return {
      avgAbility: 50.0,
      workerAbility: [50,50,50,50,50],
      allWorkerAbility: [50,50,50,50,50],
      allAvgAbility: 50.0,
      randomId: getRandomId(),
      isLoading: false
    }
  },
  mounted() {
    this.isLoading = true
    viewUserRadar(this.userId).then(res=>{
      if (res.code === SUCCESS){
        this.workerAbility[0] = Number(res.data.cooperateAbility)
        this.workerAbility[1] = Number(res.data.reviewAbility)
        this.workerAbility[2] = Number(res.data.creativeAbility)
        this.workerAbility[3] = Number(res.data.findBugAbility)
        this.workerAbility[4] = Number(res.data.languageAbility)
        this.avgAbility = Number(res.data.avgAbility)
        // alert("个人能力ok啦")
      }else{
        this.showAlert(res)
      }
    });
    viewAvgRadar().then(res=>{
      if (res.code === SUCCESS){
        this.allWorkerAbility[0] = Number(res.data.cooperateAbility)
        this.allWorkerAbility[1] = Number(res.data.reviewAbility)
        this.allWorkerAbility[2] = Number(res.data.creativeAbility)
        this.allWorkerAbility[3] = Number(res.data.findBugAbility)
        this.allWorkerAbility[4] = Number(res.data.languageAbility)
        this.allAvgAbility = Number(res.data.avgAbility)
        this.isLoading = false
        // alert("呜呜平均能力才返回 慢慢")
      }else{
        this.showAlert(res)
      }
    }).finally(()=>{
      this.drawLine();
    });

  },
  methods: {
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
    },
    drawLine() {
      // 基于准备好的dom，初始化echarts实例

      let myChart = echarts.init(document.getElementById(this.randomId))
      let option = {
        title: {
          text: '您的综合能力：'+this.avgAbility+'分  所有工人的平均综合能力：'+this.allAvgAbility+'分',
          left: 'center'
        },
        legend: {
          orient: 'vertical',
          data: ['您的能力','所有工人的平均能力'],
          left: 'left',
          top: '40px',
          itemGap: 25,
          textStyle: { // 图例文字的样式
            fontSize: 14
          }
        },
        grid: {
          top: 'bottom',
          position:'center'
        },
        tooltip: {
          trigger: 'axis'
        },
        radar: {

          // shape: 'circle',
          indicator: [
            { name: '报告协作能力', max: 100 },
            { name: '报告审查能力', max: 100 },
            { name: '创新能力', max: 100 },
            { name: '寻找缺陷能力', max: 100 },
            { name: '语言表达能力', max: 100 }
          ]
        },
        series: [
          {
            type: 'radar',
            areaStyle:{},
            tooltip: {
              trigger: 'item'
            },
            data: [
              {
                value: this.workerAbility,
                name: '您的能力'
              },
              {
                value: this.allWorkerAbility,
                name: '所有工人的平均能力'
              }
            ]
          }
        ]
      }
      // 绘制图表
      myChart.setOption(option)
    },
  },
}
</script>

<style>
</style>
