<template>
  <div v-if="editMode">
      <el-form :inline="true"  ref="ruleFormRef" :model="weights">
        <el-form-item prop="abilityWeight"
                      label="工人能力权重"
                      :rules="[
                        {
                          required: true,
                          message: '不能为空',
                          trigger: 'blur'
                        },
                      { validator: checkWeight, trigger: 'blur' }
                    ]">
          <el-input style="width: 60px" v-model="weights.abilityWeight"/>
        </el-form-item>
        <el-form-item prop="activationWeight"
                      label="活跃度权重"
                      :rules="[
                        {
                          required: true,
                          message: '不能为空',
                          trigger: 'blur'
                        },
                      { validator: checkWeight, trigger: 'blur' }
                    ]">
          <el-input style="width: 60px" v-model="weights.activationWeight"  />
        </el-form-item>
        <el-form-item prop="relevanceWeight"
                      label="相关性权重"
                      :rules="[
                        {
                          required: true,
                          message: '不能为空',
                          trigger: 'blur'
                        },
                      { validator: checkWeight, trigger: 'blur' }
                    ]">
          <el-input style="width: 60px" v-model="weights.relevanceWeight" />
        </el-form-item>
        <el-form-item prop="diversityWeight"
                      label="多样性权重"
                      :rules="[
                        {
                          required: true,
                          message: '不能为空',
                          trigger: 'blur'
                        },
                      { validator: checkWeight, trigger: 'blur' }
                    ]">
          <el-input style="width: 60px" v-model="weights.diversityWeight" />
        </el-form-item>
        <el-form-item prop="costWeight"
                      label="开销权重"
                      :rules="[
                        {
                          required: true,
                          message: '不能为空',
                          trigger: 'blur'
                        },
                      { validator: checkWeight, trigger: 'blur' }
                    ]">
          <el-input style="width: 60px" v-model="weights.costWeight" />
        </el-form-item>
      </el-form>

    <div>
      <el-button circle size="small" @click="updateRecommendFactor" v-if="canEditWeight">
        <el-icon>
          <ic-check></ic-check>
        </el-icon>
      </el-button>
      <el-button circle size="small" type="danger" @click="close">
        <el-icon>
          <ic-close></ic-close>
        </el-icon>
      </el-button>
    </div>
  </div>
  <div v-else>
    <el-descriptions
        :column="5"

    >
      <el-descriptions-item label="工人能力权重">{{orginalWeights.abilityWeight}}</el-descriptions-item>
      <el-descriptions-item label="活跃度权重">{{orginalWeights.activationWeight}}</el-descriptions-item>
      <el-descriptions-item label="相关性权重">{{orginalWeights.relevanceWeight}}</el-descriptions-item>
      <el-descriptions-item label="多样性权重">{{orginalWeights.diversityWeight}}</el-descriptions-item>
      <el-descriptions-item label="开销权重">{{orginalWeights.costWeight}}</el-descriptions-item>
    </el-descriptions>

    <el-button circle type="primary" size="small" @click="editMode = true">
      <el-icon>
        <ic-edit></ic-edit>
      </el-icon>
    </el-button>
    <span style="margin-left: 10px;font-weight: bold;font-size: small">修改权重</span>
  </div>

</template>

<script>
import {getRecommendFactor, setRecommendFactor} from "@/api/TaskApi";
import {SUCCESS} from "@/const/ResponseStatus";
// import TaskWeightDisplay from "@/components/TaskWeightDisplay"
// import TaskWeightEdit from "@/components/TaskWeightEdit"
import { ElMessageBox } from 'element-plus'
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'
import {TASK_WEIGHT_ABILITY_NAME,TASK_WEIGHT_ACTIVATION_NAME,TASK_WEIGHT_COST_NAME,TASK_WEIGHT_DIVERSITY_NAME,TASK_WEIGHT_RELEVANCE_NAME} from "@/const/Task"
import { ref } from 'vue'
import {storageGet} from "@/utils/util";
export default {
  name: "TaskWeightInfo",
  props: ['taskVO'],
  setup(){
    const ruleFormRef = ref(null)
    const isNumber = (val) =>{
      var regPos = /^\d+(\.\d+)?$/
      var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/
      if (regPos.test(val) || regNeg.test(val)) {
        return true
      } else {
        return false
      }
    }
    const checkWeight = (rule, value, callback) => {
      if (!isNumber(value)) {
        callback(new Error('请输入数字'))
      } else {
        if (value < 0 || value > 1) {
          callback(new Error('0-1之间'))
        } else {
          callback()
        }
      }
    }
    return {ruleFormRef,checkWeight}
  },
  mounted() {
    this.loadRecommendFactor()
  },
  computed: {
    taskId(){
      return this.$route.query.taskId
    },
    canEditWeight(){
      return this.taskVO.userId === this.userId
    },
    userId(){
      return Number.parseInt(storageGet('id'));
    },


  },
  data(){
    return {
      weights:{
        abilityWeight: 0.0,
        activationWeight: 0.0,
        relevanceWeight: 0.0,
        diversityWeight: 0.0,
        costWeight: 0.0
      },
      recommendFactor:[],
      orginalWeights:{
        abilityWeight: 0.0,
        activationWeight: 0.0,
        relevanceWeight: 0.0,
        diversityWeight: 0.0,
        costWeight: 0.0
      },
      editMode: false,
      isLoading:false
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
    loadRecommendFactor(){
      this.isLoading = true
      getRecommendFactor(this.taskId).then(res => {
        if(res.code === SUCCESS){
          this.recommendFactor = res.data
          for(var i=0;i<this.recommendFactor.length;i++){
            var weight = this.recommendFactor[i].weight
            switch (this.recommendFactor[i].name){
              case TASK_WEIGHT_ABILITY_NAME:
                this.weights.abilityWeight = weight;
                break;
              case TASK_WEIGHT_ACTIVATION_NAME:
                this.weights.activationWeight = weight;
                break;
              case TASK_WEIGHT_RELEVANCE_NAME:
                this.weights.relevanceWeight = weight;
                break;
              case TASK_WEIGHT_DIVERSITY_NAME:
                this.weights.diversityWeight = weight;
                break;
              case TASK_WEIGHT_COST_NAME:
                this.weights.costWeight = weight;
                break;
            }
          }
        }else{
          this.showAlert(res)
        }
      })
      this.isLoading = false
    },
    updateRecommendFactor(){

      this.ruleFormRef.validate((valid) => {
        if (valid) {
          const RecommendDTO = [{"name":TASK_WEIGHT_ABILITY_NAME,"weight":this.weights.abilityWeight},{"name":TASK_WEIGHT_ACTIVATION_NAME,"weight":this.weights.activationWeight},{"name":TASK_WEIGHT_DIVERSITY_NAME,"weight":this.weights.diversityWeight},{"name":TASK_WEIGHT_RELEVANCE_NAME,"weight":this.weights.relevanceWeight},{"name":TASK_WEIGHT_COST_NAME ,"weight":this.weights.costWeight}]
          var totalWeight = parseFloat(this.weights.abilityWeight) +parseFloat( this.weights.costWeight)+parseFloat(this.weights.relevanceWeight)+parseFloat(this.weights.activationWeight)+parseFloat(this.weights.diversityWeight)
          var delta = 1e-5
          if (Math.abs(totalWeight - 1.0) < delta ){
            setRecommendFactor(RecommendDTO,this.taskId).then(res =>{
              if(res.code === SUCCESS){
                this.recommendFactor = res.data
                this.editMode = false
              }
              this.showAlert(res)
            })
          }else{
            ElMessageBox.alert('权重和必须为1，请重新设置权重', '权重和不为1', {
              confirmButtonText: 'OK',
            })
          }
        } else {
          return false
        }
      })


    },
    close(){
      this.editMode = false
    }
  },
  watch:{
    recommendFactor(){
      for(var i=0;i<this.recommendFactor.length;i++){
        var weight = this.recommendFactor[i].weight
        switch (this.recommendFactor[i].name){
          case TASK_WEIGHT_ABILITY_NAME:
            this.orginalWeights.abilityWeight = weight;
            break;
          case TASK_WEIGHT_ACTIVATION_NAME:
            this.orginalWeights.activationWeight = weight;
            break;
          case TASK_WEIGHT_RELEVANCE_NAME:
            this.orginalWeights.relevanceWeight = weight;
            break;
          case TASK_WEIGHT_DIVERSITY_NAME:
            this.orginalWeights.diversityWeight = weight;
            break;
          case TASK_WEIGHT_COST_NAME:
            this.orginalWeights.costWeight = weight;
            break;
        }
      }
    }
  }

}
</script>

<style scoped>

</style>
