<template>
  <el-card shadow="hover" v-loading="loading" style="overflow: visible">
    <div :id="randomId" :style="{ width: '100%', height: '500px',marginTop:'25px'}">
      <el-empty description="未提交缺陷" v-if="empty"></el-empty>
    </div>
  </el-card>
</template>

<script>
import * as echarts from 'echarts'
//import {viewUserRadar,viewAvgRadar} from "@/api/UserApi";
//import {SUCCESS} from "@/const/ResponseStatus";
import {getRandomId, storageGet} from "@/utils/util";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {getBugArrivalCurve} from "@/api/TaskApi";
//import {SHOW_ALERT} from "@/const/Events";


export default {
  name: "BugArrivalCurve",
  computed: {
    userId(){
      return Number.parseInt(storageGet('id'))
    },
    empty(){
      return (!this.curve || this.curve.length === 0 ||
          !this.predictedTargetBugs || this.predictedTargetBugs.length === 0) && !this.loading
    }
  },
  data() {
    return {
      curve: [],
      randomId: getRandomId(),
      predictedTargetBugs: [],
      noData: false,
      loading: false
    }
  },
  mounted() {
    this.loading = true
    getBugArrivalCurve(this.$route.query.taskId).then(res=>{


      if (res.code === SUCCESS){
        if(res.data.curves && res.data.curves.length !== 0){
          let lastTime = new Date()
          let lastNum = 0
          this.predictedTargetBugs = []
          this.curve = []
          res.data.curves.forEach(point => {
            this.curve.push([+new Date(point.time), point.num])
            this.predictedTargetBugs.push([+new Date(point.time), res.data.numPredicted])
            lastNum = point.num;
          })
          this.curve.push([+lastTime, lastNum])
          this.predictedTargetBugs.push([+lastTime, res.data.numPredicted])
          this.drawLine()


        }

      }else{
        this.$emit(SHOW_ALERT, res)
      }
    }).finally(() => {
      this.loading = false
    })

  },
  methods: {
    drawLine() {

      // 基于准备好的dom，初始化echarts实例
      let chartDom = document.getElementById(this.randomId);
      let myChart = echarts.init(chartDom);
      let option = {
        tooltip: {
          trigger: 'axis',
          position: function (pt) {
            return [pt[0], '20%'];
          }
        },
        title: {
          left: 'center',
          text: '缺陷发现曲线和预测缺陷总数'
        },
        toolbox: {
          feature: {
/*            dataZoom: {
              yAxisIndex: 'none'
            },*/
            restore: {},
            saveAsImage: {}
          }
        },
        xAxis: {
          type: 'time',
          boundaryGap: false
        },
        yAxis: {
          type: 'value',
          boundaryGap: [0, '100%']
        },
        dataZoom: [
          {
            type: 'inside',
            start: 0,
            zoomLock: false,
            end: 100
          },
          {
            start: 0,
            end: 100,

          }
        ],
 /*       legend: {
          orient: 'vertical',
          data: ['任务当前评分','任务目标评分'],
          left: 'left',
          top: '40px',
          itemGap: 25,
          textStyle: { // 图例文字的样式
            fontSize: 14
          }
        },*/
        series: [
          {
            name: '预测的缺陷总数',
            type: 'line',
            smooth: true,
            symbol: 'none',
            label: {
              position: 'top'
            },
            data: this.predictedTargetBugs,
            connectNulls: true
          },
          {
            name: '缺陷发现曲线',
            type: 'line',
            smooth: true,
            symbol: 'none',
            areaStyle: {},
            data: this.curve,
            connectNulls: true
          },

        ]
      };

      option && myChart.setOption(option);

    },
  },
}
</script>

<style>
</style>
