<template>
  <div>
    <el-row :id="`flaw#${flaw.id}`" class="flaw-item-container">
      <flaw-card-display class="f1" style="min-width: 400px;" :flaw="flaw" :key="flaw.id">
        <template #header-right-conner>
          <score-view :score="flaw.score"></score-view>
        </template>
        <template #footer>
          <flaw-append-component :read-only="!isMyReport" :model-value="flawAppendContent" @collect:save-append="appendFlaw($event)"/>
        </template>
      </flaw-card-display>
      <el-card class="f1 col" style="min-width: 400px; display: block" shadow="hover">
        <flaw-tree :current-flaw="flaw" class="f1" :flaw-tree-data="flawTree" @select="jumpToFlaw($event.current)"/>
        <el-table class="f1" :data="similarFlaws" height="200" @row-click="jumpToFlaw($event.flaw)">
          <el-table-column prop="flaw.id" label="缺陷ID"/>
          <el-table-column prop="similarity" label="相似度"/>
        </el-table>
        <el-divider></el-divider>
        <el-row class="f1" style="align-items: center; justify-content: space-between" v-if="canScore">
          <el-row style="align-items: center;" v-if="!isMyReport">
            <div style="margin-right: 10px">{{ haveScore ? '我的评分：' : '我要评分' }}</div>
            <el-rate size="30" v-model="flawScore" @change="scoreChange()" :disabled="haveScore"></el-rate>
          </el-row>
          <el-row style="align-items: center;">
            <div style="margin-right: 10px">查看评论区</div>
            <el-button type="text" @click="loadEvaluations()">
              <el-icon size="large">
                <ic-comment></ic-comment>
              </el-icon>
            </el-button>
          </el-row>
        </el-row>
      </el-card>
    </el-row>
    <el-drawer v-model="showEvaluation" size="80%">
      <template #title>
        <div>
          {{ `缺陷#${flaw.id}的评论区` }}
        </div>
      </template>
      <el-space direction="vertical" :spacer="spacer" :fill="true" style="width: 100%">
        <flaw-evaluation-item v-for="evaluation in evaluations" :key="evaluation" :flaw-evaluation-vo="evaluation"></flaw-evaluation-item>

      </el-space>
      <template #footer v-if="canPostEvaluation">
        <el-row >
          <el-input v-model="myEvaluations" class="f1" type="textarea" :rows="5" style="margin-right: 10px">
          </el-input>
          <el-button type="text" @click="postEvaluations" :loading="posting">
            <el-icon size="large">
              <ic-promotion></ic-promotion>
            </el-icon>
          </el-button>
        </el-row>
      </template>

    </el-drawer>
  </div>

</template>

<script>
import FlawCardDisplay from "@/components/FlawCardDisplay";
import FlawTree from "@/components/FlawTree";
import {
  addFlawAppend,
  getFlawEvaluations,
  getFlawTree,
  getMyScore,
  getSimilarFlaws,
  postFlawEvaluations,
  scoreFlaw
} from "@/api/FlawApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {storageGet} from "@/utils/util";
import {EMPLOYER, WORKER} from "@/const/UserRole";
import ScoreView from "@/components/ScoreView";
import FlawAppendComponent from "@/components/FlawAppendComponent";
import FlawEvaluationItem from "@/flaw/FlawEvaluationItem";
import { h } from 'vue';
import { ElDivider } from 'element-plus'
export default {
  name: "ReportFlawItem",
  components: {FlawEvaluationItem, FlawAppendComponent, ScoreView, FlawTree, FlawCardDisplay},
  props: ['flaw', 'isMyReport'],
  emits: [SHOW_ALERT, 'collect:score-change'],
  data(){
    return {
      flawTree:{},
      similarFlaws:[],
      flawScore: 0,
      haveScore: true,
      flawAppendContent: '',
      showEvaluation: false,
      evaluations: [],
      myEvaluations: "",
      posting: false
    }
  },
  methods:{
    loadFlawTree(){
      getFlawTree(this.flaw.id).then(res => {
        if(res.code === SUCCESS){
          this.flawTree = res.data
          //console.log(res.data)
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })
    },
    loadFlawScore(){
      getMyScore(this.userId, this.flaw.id)
      .then(res => {
        if(res.code === SUCCESS){
          if(res.data === -1){
            this.haveScore = false;
          }else{
            this.haveScore = true;
            this.flawScore = res.data;
          }
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })
    },
    loadSimilarFlaws(){
      getSimilarFlaws(this.flaw.id).then(res => {
        if(res.code === SUCCESS){
          this.similarFlaws = res.data
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })
    },
    loadEvaluations(){
      getFlawEvaluations(this.flaw.id, this.userId).then(res => {
        if(res.code === SUCCESS){
          this.evaluations = res.data
          this.showEvaluation = true;
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })
    },
    postEvaluations(){
      if(this.myEvaluations !== ""){
        this.posting = true
        postFlawEvaluations({
          userId: this.userId,
          content: this.myEvaluations
        }, this.flaw.id).then(res => {
          if(res.code === SUCCESS){
            this.myEvaluations = "";
            this.evaluations = res.data;
          }
          this.$emit(SHOW_ALERT, res)
        }).finally(() => {
          this.posting = false;
        })
      }
    },
    jumpToFlaw(flaw){
      console.log(flaw)
      this.$router.push({
        path: 'reportDetail',
        query:{
          reportId: flaw.reportId,
          flawId: flaw.id
        }
      })
    },
    scoreChange(){
      scoreFlaw(this.flawScore, this.flaw.id, this.userId).then(res => {
        //console.log('scorechange')
        this.$emit(SHOW_ALERT, res);
        this.$emit('collect:score-change')
        this.loadFlawScore()
      })
    },
    appendFlaw(content){
      addFlawAppend(this.flaw.id, content)
      .then(res => {
        if(res.code === SUCCESS){
          this.flawAppendContent = content;
        }
        this.$emit(SHOW_ALERT, res)
      })
    }
  },
  mounted() {
    this.loadFlawTree();
    this.loadSimilarFlaws();
    this.loadFlawScore();
    this.flawAppendContent = this.flaw.appendcontent
  },
  computed: {
    canScore(){
      return Number.parseInt(storageGet('type' )) === WORKER
    },
    userId(){
      return Number.parseInt(storageGet('id'))
    },
    canPostEvaluation(){
      return !this.isMyReport && Number.parseInt(storageGet('type' )) !== EMPLOYER
    }
  },
  watch: {
    flaw(newVal){
      console.log(newVal)
      this.flawAppendContent = newVal.appendcontent;
    }
  },
  setup(){
    const spacer = h(ElDivider, { direction: 'horizontal', style: {
      marginTop: '5px',
        marginBottom: '2px'
      }})
    return {
      spacer
    }
  }
}
</script>

<style scoped>

</style>
