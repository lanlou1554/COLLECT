<template>
  <el-form ref="ruleFormRef" :model="task" :rules="rules" label-width="100px" label-position="top">
    <el-card shadow="never">
      <template #header>
        <div style="display: flex;justify-content: space-between;align-items: center;">
          <span>{{ task.title }}</span>
          <el-button round type="primary" @click="submitTask" :loading="loading">发布测试</el-button>
        </div>

      </template>
      <div style="padding: 20px;">
        <el-row>
          <el-col>
            <el-form-item label="标题:" prop="title">
              <el-input v-model="task.title" placeholder="请输入任务的标题"></el-input>
            </el-form-item>
            <el-form-item label="测试类型:" prop="type">
              <el-select  v-model="task.type" class="m-2" placeholder="请选择测试类型">
                <el-option
                    v-for="item in taskTypes"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="测试所需人数:" prop="workerNum">
              <el-input-number v-model="task.workerNum" :step="1" step-strictly :min="1"/>
            </el-form-item>
            <el-form-item label="测试起止日期:" prop="duration">
              <el-date-picker
                  :span="20"
                  v-model="task.duration"
                  type="daterange"
                  unlink-panels
                  range-separator="To"
                  start-placeholder="Start date"
                  end-placeholder="End date"
                  :disabled-date="disabledDate"
              >
              </el-date-picker>
            </el-form-item>

            <span style="margin-top: 20px">推荐权重:</span>
            <el-row style="justify-content: space-evenly;margin-top: 15px">
              <el-form-item prop="recommendFactor.abilityWeight"
                            label="工人能力权重"
                            :rules="[
                          {
                            required: true,
                            message: '不能为空',
                            trigger: 'blur'
                          },
                        { validator: checkWeight, trigger: 'blur' }
                      ]">
                <el-input style="width: 60px" v-model="task.recommendFactor.abilityWeight"/>
              </el-form-item>
              <el-form-item prop="recommendFactor.activationWeight"
                            label="活跃度权重"
                            :rules="[
                          {
                            required: true,
                            message: '不能为空',
                            trigger: 'blur'
                          },
                        { validator: checkWeight, trigger: 'blur' }
                      ]">
                <el-input style="width: 60px" v-model="task.recommendFactor.activationWeight"  />
              </el-form-item>
              <el-form-item prop="recommendFactor.relevanceWeight"
                            label="相关性权重"
                            :rules="[
                          {
                            required: true,
                            message: '不能为空',
                            trigger: 'blur'
                          },
                        { validator: checkWeight, trigger: 'blur' }
                      ]">
                <el-input style="width: 60px" v-model="task.recommendFactor.relevanceWeight" />
              </el-form-item>
              <el-form-item prop="recommendFactor.diversityWeight"
                            label="多样性权重"
                            :rules="[
                          {
                            required: true,
                            message: '不能为空',
                            trigger: 'blur'
                          },
                        { validator: checkWeight, trigger: 'blur' }
                      ]">
                <el-input style="width: 60px" v-model="task.recommendFactor.diversityWeight" />
              </el-form-item>
              <el-form-item prop="recommendFactor.costWeight"
                            label="开销权重"
                            :rules="[
                          {
                            required: true,
                            message: '不能为空',
                            trigger: 'blur'
                          },
                        { validator: checkWeight, trigger: 'blur' }
                      ]">
                <el-input style="width: 60px" v-model="task.recommendFactor.costWeight" />
              </el-form-item>
            </el-row>

            <el-form-item label="测试描述:" prop="description">
              <el-input
                  v-model="task.description"
                  :autosize="{ minRows: 4, maxRows: 8 }"
                  type="textarea"
                  placeholder="请简单描述测试任务，以帮助众包工人快速了解你的任务，更加详细的测试需求请上传测试文档"
              >
              </el-input>
            </el-form-item>
            <el-form-item>
              <el-checkbox-group v-model="task.tagGroups" size="small">
                <el-space wrap>
                  <el-checkbox border v-for="tag in filteredTags" :label="tag.id" :key="tag.id">
                    {{ tag.name }}
                  </el-checkbox>
                </el-space>
              </el-checkbox-group>
            </el-form-item>
          </el-col>

        </el-row>
        <el-row style="justify-content: space-evenly">

          <el-form-item prop="reqDocURL">
            <file-up-load-component msg="测试文档" v-model="task.reqDocURL"/>
          </el-form-item>
          <el-form-item prop="exeFileURL">
            <file-up-load-component msg="测试软件包" v-model="task.exeFileURL"/>
          </el-form-item>

        </el-row>
      </div>
    </el-card>
  </el-form>
</template>

<script>
import FileUpLoadComponent from "@/fileComponents/FileUpLoadComponent";
import {postTaskDetail} from "@/api/TaskApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {ref} from "vue";
import {storageGet, tagFilter} from "@/utils/util";
import {TaskTags} from "@/const/Tags";
import {getDefaultRecommendFactor} from "@/api/TaskApi"
import { ElMessageBox } from 'element-plus'
import {TASK_WEIGHT_ABILITY_NAME,TASK_WEIGHT_ACTIVATION_NAME,TASK_WEIGHT_COST_NAME,TASK_WEIGHT_DIVERSITY_NAME,TASK_WEIGHT_RELEVANCE_NAME} from "@/const/Task"
export default {
  name: "PostTaskPage",
  components: {FileUpLoadComponent},
  setup(){
    const disabledDate=(date)=>{
      let now = new Date();
      now.setHours(0)
      now.setMinutes(0)
      now.setSeconds(0)
      now.setMilliseconds(0)
      return date.getTime() < now.getTime();
    }
    const rules = {
      type: [
        {
          required: true,
          message: '请选择任务类型',
          trigger: 'blur'
        }
      ],
      title: [
        {
          required: true,
          message: '请输入任务标题',
          trigger: 'blur'
        }
      ],
      description: [
        {
          required: true,
          message: '请输入任务描述',
          trigger: 'blur'
        }
      ],
      duration: [{
        required: true,
        message: '请选择任务起止时间',
        trigger: 'blur'
      },


      ],
      workerNum: [
        {
          required: true,
          message: '请输入工人数量',
          trigger: 'blur'
        }
      ],
      exeFileURL: [
        {
          required: true,
          message: '请上传待测软件包',
          trigger: 'blur'
        }
      ],
      reqDocURL: [
        {
          required: true,
          message: '请上传测试文档',
          trigger: 'blur'
        }
      ]

    }
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
    const ruleFormRef = ref()
    return {
      rules, ruleFormRef, disabledDate,checkWeight
    }
  },
  data: ()=>{
    return {
     taskTypes: [
       {
         value: 0,
         label: "功能测试"
       },
       {
         value: 1,
         label: "性能测试"
       }
     ],
      task: {
        userId:0,
        type: 0,
        description: "",
        duration: [new Date(), new Date()],
        workerNum: 0,
        exeFileURL: '',
        reqDocURL: '',
        title: '',
        tagGroups: [],
        recommendFactor: {
          abilityWeight: 0.0,
          activationWeight: 0.0,
          relevanceWeight: 0.0,
          diversityWeight: 0.0,
          costWeight: 0.0
        }
      },
      loading: false,
      TaskTags,
      recommendFactor:[]
    }
  },
  methods:{
    postTask(){
      let tagGroups = []
      tagGroups.push(TaskTags[Number.parseInt(this.task.type)].id)
      tagGroups.push(...this.task.tagGroups)
      const recommendFactor = [{"name":TASK_WEIGHT_ABILITY_NAME,"weight":this.task.recommendFactor.abilityWeight},{"name":TASK_WEIGHT_ACTIVATION_NAME,"weight":this.task.recommendFactor.activationWeight},{"name":TASK_WEIGHT_DIVERSITY_NAME,"weight":this.task.recommendFactor.diversityWeight},{"name":TASK_WEIGHT_RELEVANCE_NAME,"weight":this.task.recommendFactor.relevanceWeight},{"name":TASK_WEIGHT_COST_NAME ,"weight":this.task.recommendFactor.costWeight}]
      const TaskDTO={
        userId:this.userId,
        type:this.task.type,
        description:this.task.description,
        startTime:this.task.duration[0],
        endTime:this.task.duration[1],
        workerNum:this.task.workerNum,
        exeFileName:this.task.exeFileURL,
        reqDocName:this.task.reqDocURL,
        title:this.task.title,
        tagGroups: tagGroups,
        recommendFactor: recommendFactor
      };
      console.log(TaskDTO)
      this.loading = true;
      postTaskDetail(TaskDTO).then(res=>{
        if (res.code === SUCCESS) {
          this.$router.push({path:"/employer"});
        }
        this.$emit(SHOW_ALERT,res);
      }).finally(() => {
        this.loading = false;
      })
    },
    submitTask(){

      console.log(this.ruleFormRef)
      if (!this.ruleFormRef) return
      this.ruleFormRef.validate((valid) => {
        if (valid) {
          let totalWeight = parseFloat(this.task.recommendFactor.abilityWeight)+parseFloat(this.task.recommendFactor.costWeight)+parseFloat(this.task.recommendFactor.activationWeight)+parseFloat(this.task.recommendFactor.diversityWeight)+parseFloat(this.task.recommendFactor.relevanceWeight)
          var delta = 1e-5
          if (Math.abs(totalWeight - 1.0) < delta ) {
            console.log('submit!');
            this.postTask();
          }else{
            ElMessageBox.alert('推荐权重和必须为1，请重新设置权重', '权重和不为1', {
              confirmButtonText: 'OK',
            })
          }
        } else {
          console.log('error submit!')
          return false
        }
      })
    }
  },
  computed: {
    userId(){
      return Number.parseInt(storageGet('id'));
    },
    filteredTags(){
      return TaskTags.filter(tagFilter)
    }
  },
  mounted() {
    getDefaultRecommendFactor().then(res=>{
      if (res.code === SUCCESS){
        this.recommendFactor = res.data
        for(var i=0;i<this.recommendFactor.length;i++){
          var weight = this.recommendFactor[i].weight
          switch (this.recommendFactor[i].name){
            case TASK_WEIGHT_ABILITY_NAME:
              this.task.recommendFactor.abilityWeight = weight;
              break;
            case TASK_WEIGHT_ACTIVATION_NAME:
              this.task.recommendFactor.activationWeight = weight;
              break;
            case TASK_WEIGHT_RELEVANCE_NAME:
              this.task.recommendFactor.relevanceWeight = weight;
              break;
            case TASK_WEIGHT_DIVERSITY_NAME:
              this.task.recommendFactor.diversityWeight = weight;
              break;
            case TASK_WEIGHT_COST_NAME:
              this.task.recommendFactor.costWeight = weight;
              break;
          }
        }
      }else{
        this.$emit(SHOW_ALERT,res);
      }
    })
  }
}
</script>

<style scoped>

</style>
