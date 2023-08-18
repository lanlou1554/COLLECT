<template>
  <el-card
      v-loading="isLoading"
      element-loading-text="加载需要几秒，请耐心等待………">
    <div id="mywordcloud" :style="{width: '100%', height: '300px',marginTop:'25px'}" :data="worddata"></div>
  </el-card>
</template>

<script>
import * as echarts from "echarts";
import "echarts-wordcloud/dist/echarts-wordcloud";
import "echarts-wordcloud/dist/echarts-wordcloud.min";
import {viewUserWordCloud} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'
import {storageGet} from "@/utils/util";

export default {
  name: "WorkerDomainKnowledge",
  computed: {
    userId(){
      return Number.parseInt(storageGet('id'))
    }
  },
  data () {
    return {
      msg: 'Welcome to Your Vue.js App',
      worddata: [],
      isLoading:false
    }
  },
  mounted(){
    this.isLoading = true
    viewUserWordCloud(this.userId).then(res=>{
      if (res.code === SUCCESS){
        this.worddata = res.data
        this.initChart();
      }else{
        this.showAlert(res)
      }
      this.isLoading = false
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
    initChart() {
      this.chart = echarts.init(document.getElementById("mywordcloud"));
      console.log('lll');
      const option = {
        title: {
          text: "您的报告关键字",
          left: "center",
          textStyle: { // 主标题文本样式{"fontSize": 18,"fontWeight": "bolder","color": "#333"}
            fontSize: 20
          }
        },
        backgroundColor: "#fff",
        // tooltip: {
        //   pointFormat: "{series.name}: <b>{point.percentage:.1f}%</b>"
        // },
        series: [
          {
            type: "wordCloud",
            //用来调整词之间的距离
            gridSize: 20,
            //用来调整字的大小范围
            // Text size range which the value in data will be mapped to.
            // Default to have minimum 12px and maximum 60px size.
            sizeRange: [20, 60],
            // Text rotation range and step in degree. Text will be rotated randomly in range [-90,                                                                             90] by rotationStep 45
            //用来调整词的旋转方向，，[0,0]--代表着没有角度，也就是词为水平方向，需要设置角度参考注释内容
            // rotationRange: [-45, 0, 45, 90],
            // rotationRange: [ 0,90],
            rotationRange: [0, 0],
            //随机生成字体颜色
            // maskImage: maskImage,
            textStyle: {
              color: function() {
                return (
                    "rgb(" +
                    Math.round(Math.random() * 50) +
                    ", " +
                    Math.round(Math.random() * 50) +
                    ", " +
                    Math.round(Math.random() * 160+90) +
                    ")"
                );
              }

            },
            //位置相关设置
            // Folllowing left/top/width/height/right/bottom are used for positioning the word cloud
            // Default to be put in the center and has 75% x 80% size.
            left: "center",
            top: "center",
            right: null,
            bottom: null,
            width: "200%",
            height: "200%",
            //数据
            data: this.worddata
          }
        ]
      };
      this.chart.setOption(option);
    },
  }
}
</script>

<style scoped>

</style>