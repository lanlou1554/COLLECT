<template>
  <el-card shadow="hover"  v-loading="isLoading"
             element-loading-text="加载需要几秒，请耐心等待………">
    <div :id="randomId" :style="{ width: '700px', height: '600px'}">
      <el-empty v-if="empty" :description="description"></el-empty>
    </div>
  </el-card>
</template>

<script>
import * as echarts from 'echarts'
import {getTargetTaskRadar,getCurrentTaskRadar} from "@/api/TaskApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'
import {getRandomId} from "@/utils/util";


export default {
  name: 'TaskRadar',
  computed: {
    taskId(){
      return this.$route.query.taskId
    },
    empty(){
      return !this.isLoading && this.no_data
    }
  },
  data() {
    return {
      avgAbility: 50.0,
      workerAbility: [50,50,50,50],
      targetAbility: [50,50,50,50],
      targetAvgAbility: 50.0,
      randomId: getRandomId(),
      isLoading: false,
      no_data: false,
      description: ''
    }
  },
  mounted() {
    this.isLoading = true
    getCurrentTaskRadar(this.taskId).then(res=>{
      if (res.code === SUCCESS){
        this.workerAbility[0] = this.cut(Number(res.data.ability))
        this.workerAbility[1] = this.cut(Number(res.data.activation))
        this.workerAbility[2] = this.cut(Number(res.data.revelance))
        this.workerAbility[3] = this.cut(Number(res.data.diversity))
        this.avgAbility = this.average(this.workerAbility);
        return getTargetTaskRadar(this.taskId)
      }else{
        this.description = res.msg
        this.no_data = true

      }
    }).then(res=>{
      if (res.code === SUCCESS){
        this.targetAbility[0] = this.cut(Number(res.data.ability))
        this.targetAbility[1] = this.cut(Number(res.data.activation))
        this.targetAbility[2] = this.cut(Number(res.data.revelance))
        this.targetAbility[3] = this.cut(Number(res.data.diversity))
        this.targetAvgAbility = this.average(this.targetAbility);

        this.isLoading = false
        this.no_data = false
        this.description = ''
        this.drawLine()
      }else{
        this.description = res.msg
        this.no_data = true
      }
    }).finally(() => {
      this.isLoading = false;
    });


  },
  methods: {
    average(l){
      let result = 0;
      l.forEach(i => {
        result += i
      })
      let size = l.length;
      if (size === 0) size++;
      return result / size;
    },
    cut(n){
      if (n > 100)return 100;
      return n;
    },
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
          text: '任务当前均分：'+this.avgAbility+'分  任务目标均分：'+this.targetAvgAbility+'分',
          left: 'center'
        },
        legend: {
          orient: 'vertical',
          data: ['任务当前评分','任务目标评分'],
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
            { name: '工人能力', max: 100 },
            { name: '工人活跃度', max: 100 },
            { name: '工人相关性', max: 100 },
            { name: '工人多样性', max: 100 }
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
                value: this.targetAbility,
                name: '任务目标评分'
              },
              {
                value: this.workerAbility,
                name: '任务当前评分'
              },

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
