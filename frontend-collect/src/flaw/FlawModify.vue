<template>
  <el-card
      shadow="hover"
      v-loading="loadingSim"
      element-loading-text="正在寻找相似报告，请稍后……"
  >
    <template #header>
      <el-steps :active="active" finish-status="success"  align-center>
        <el-step v-for="(item,idx) in tbaFlawItems" :key="idx" :title="`待改缺陷${idx+1}`" />
      </el-steps>
    </template>
    <el-row>
      <el-card style="min-width: 700px;max-width: 700px">
        <el-tabs v-if="tbaFlawItems.length!==0" v-model="activeName" class="demo-tabs" @tab-click="changeJdx">
          <el-tab-pane v-for="(item,j) in tbaFlawItems[idx].similarFlaws" :key="j" :name="j" :label="`${item.similarity*100}%`" >
            <flaw-card-modify @forkFlaw="forkFlaw" class="f1" v-model="tbaFlawItems[idx].similarFlaws[jdx].flaw" :idx="`${jdx}`"></flaw-card-modify>
            <div style="display: flex; flex-direction: row-reverse">
              <el-button type="text" @click="loadEvaluations(tbaFlawItems[idx].similarFlaws[jdx].flaw.id)">
                <span>查看评论区</span>
                <el-icon size="large">
                  <ic-comment></ic-comment>
                </el-icon>
              </el-button>
            </div>


          </el-tab-pane>
        </el-tabs>

      </el-card>

      <el-card v-if="tbaFlawItems.length!==0" style="min-width: 720px;max-width: 720px;">
        <div v-if="showEvaluation">
          <div style="display: flex; flex-direction: row; margin-bottom: 5px">
            <el-button type="text" circle size="large" @click="showEvaluation=false">
              <el-icon>
                <ic-back></ic-back>
              </el-icon>
            </el-button>
          </div>
          <el-divider></el-divider>
          <el-scrollbar height="500px">
            <flaw-evaluation-item v-for="evaluation in evaluations" :key="evaluation" :flaw-evaluation-vo="evaluation"></flaw-evaluation-item>
            <el-divider></el-divider>
          </el-scrollbar>
        </div>
        <div v-else>
          <div style="font-family:'KaiTi';max-width: 650px;margin-bottom:10px;padding:15px;border-radius: 4px;box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1)">
            右边是您的缺陷，左边是与您的缺陷相似度高的缺陷，标签上的百分比是相似度。<br/>
            如果您发现左边的缺陷与您的缺陷描述的是同一个缺陷，请基于左边的缺陷进一步完善您的缺陷，并点击“提交修改后的缺陷”按钮；反之，请直接点击“无相似缺陷”按钮。
          </div>
          <flaw-card-display class="f1" :flaw="tbaFlawItems[idx].tbaFlaw" :key="tbaFlawItems[idx].tbaFlaw.id">
            <template #header-right-conner>
              <div style="display: flex;justify-content: space-between;">
                <el-button @click="submitOriginalFlaw($event)" round type="success">
                  无相似的缺陷
                </el-button>
              </div>
            </template>
          </flaw-card-display>
        </div>

    </el-card>
    </el-row>
  </el-card>
</template>

<script>
import {SET_MAX_WIDTH, SHOW_ALERT} from "@/const/Events";
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import {forkNoFlaw, getFlawEvaluations} from "@/api/FlawApi";
import {getTbaFlaws} from "@/api/ReportApi";
import {postRefineFlaw} from "@/api/FlawApi";
import {SUCCESS} from "@/const/ResponseStatus";
import FlawCardModify from "@/components/FlawCardModify";
import FlawCardDisplay from "@/components/FlawCardDisplay";
import {ref} from "vue";
import { ElMessageBox } from 'element-plus'
import { ElMessage} from 'element-plus'
import FlawEvaluationItem from "@/flaw/FlawEvaluationItem";
import {storageGet} from "@/utils/util";

export default {
  name: "FlawModify",
  components : {FlawEvaluationItem, FlawCardModify, FlawCardDisplay},
  setup(){
    const active = ref(0)
    return {
      active
    }
  },
  data: () => {
    return {
      tbaFlawItems: [
        {
          tbaFlaw:{
          },
          similarFlaws:[
            {
              similarity:0,
              flaw:{
                id: 1
              }
            }
          ]
        }
      ],
      idx:0,
      jdx:0,
      loading:false,
      loadingSim:false,
      activeName: "0",
      showEvaluation: false,
      evaluations: [],
    }
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
    loadTbaFlawItems(){
      this.loadingSim = true;
      // alert(this.loading)
      const reportId=this.$route.query.reportId;
      getTbaFlaws(reportId).then(res=>{
        if (res.code === SUCCESS){
          if (res.data.length === 0){
            //直接跳转
            this.$router.push({path:"/worker/reportDetail",query:{reportId:reportId}});
          }else if (this.idx === 0){
            ElMessageBox.alert('系统检测到您提交的缺陷和已有缺陷存在相似性，请进行进一步的完善', '存在待完善缺陷', {
              confirmButtonText: 'OK',
            })
          }
          this.tbaFlawItems = res.data;
        }
      }).finally(() => {
        this.loadingSim = false;
      });
    },
    submitOriginalFlaw(event){
      event.target.blur();
      if(event.target.nodeName == "SPAN") {
        event.target.parentNode.blur();
      }
      if (this.loading){
        return;
      }
      const reportId=this.$route.query.reportId;
      this.loading = true;
      forkNoFlaw(this.tbaFlawItems[this.idx].tbaFlaw.id).then(res=>{
        if (res.code === SUCCESS){
          // alert(this.idx)
          if (this.idx === (this.tbaFlawItems.length-1)){
            this.$router.push({path:"/worker/reportDetail",query:{reportId:reportId}});
          }else{
            this.idx = this.idx+1;
            this.jdx = 0;
            this.activeName = "0"
          }
          if (this.active++ > this.tbaFlawItems.length) this.active = 0;
        }
        this.showAlert(res)
      }).finally(() => {
        this.loading = false;
      });
    },
    forkFlaw(){
      // alert(this.jdx)
      // alert(this.tbaFlawItems[this.idx].similarFlaws[this.jdx].flaw.description)
      if (this.loading){
        return;
      }
      const flaw = this.tbaFlawItems[this.idx].similarFlaws[this.jdx].flaw;
      const reportId=this.$route.query.reportId;
      const flawDTO={
        pictureURLs: flaw.pictureURLs,
        description: flaw.description,
        stepDes: flaw.stepDes,
        deviceInfo: flaw.deviceInfo
      };
      // alert(JSON.stringify(flawDTO))
      this.loading = true;
      //alert(flaw.id)
      //alert(this.jdx)
      postRefineFlaw(this.tbaFlawItems[this.idx].tbaFlaw.id,flaw.id,flawDTO).then(res=>{
        if (res.code === SUCCESS){
          if (this.idx === (this.tbaFlawItems.length-1)){
            this.$router.push({path:"/worker/reportDetail",query:{reportId:reportId}});
          }else {
            this.idx = this.idx + 1;
            this.jdx = 0;
            this.activeName = "0"
          }
          if (this.active++ > this.tbaFlawItems.length) this.active = 0;
        }
        this.showAlert(res)
      }).finally(() => {
        this.loading = false;
      });
    },
    changeJdx(tab,event){
      this.showEvaluation = false
      let beforeTabId = event.target.getAttribute('id');
      let tabId = beforeTabId.split('-')[1];
      //alert(tabId)
      this.jdx = tabId
    },
    loadEvaluations(flawId){
      getFlawEvaluations(flawId, this.userId).then(res => {
        if(res.code === SUCCESS){
          this.evaluations = res.data;
          this.showEvaluation = true;
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })
    },
  },
  mounted(){
    this.$emit(SET_MAX_WIDTH, 1600);
    this.loadTbaFlawItems();
  },
  computed: {
    userId(){
      return Number.parseInt(storageGet('id'))
    },
  }

}
</script>

<style scoped>
  .demo-tabs > .el-tabs__content {
    padding: 32px;
    color: #6b778c;
    font-size: 32px;
    font-weight: 600;
  }
</style>
