<template>
  <div class="col-container" style="justify-content: center">
    <el-card class="f1" style="max-width: 750px; justify-content: center">
      <template #header>
        <div style="display: flex;justify-content: space-between;align-items: center;">
          <span>{{ report.title }}</span>
          <el-button round type="primary" @click="submitReport" :disabled="loading">提交报告</el-button>
        </div>
      </template>
      <el-form :model="report" label-position="top" :rules="rules" ref="ruleFormRef">
<!--       <el-form label-position="top">-->
        <el-form-item label="报告标题：" prop="title">
          <el-input v-model="report.title" placeholder="请使用一个标题来简明扼要的概括你的测试报告"></el-input>
        </el-form-item>
        <flaw-card v-for="(item, idx) in flawCnt" v-model="report.flaws[idx]" :key="report.flaws[idx].id" :idx="idx" style="margin: 10px">
          <template #header>
            <div style="display: flex;justify-content: space-between;">
              <div style="font-weight: bold">缺陷</div>
              <el-button @click="deleteFlaw(idx)" circle type="danger"  v-if="flawCnt.length > 1">
                <el-icon size="small">
                  <ic-delete/>
                </el-icon>
              </el-button>
            </div>
          </template>
        </flaw-card>
      </el-form>
      <el-card style="margin: 10px; display: flex; justify-content: center;" shadow="hover" @click="addFlaw">
        <el-icon><ic-plus/></el-icon>
      </el-card>
    </el-card>
  </div>

</template>

<script>
import FlawCard from "@/components/FlawCard";
import {postReportDetail} from "@/api/ReportApi"
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {ref} from "vue";
import {storageGet} from "@/utils/util";
export default {
  name: "IssueReportPage",
  components: {FlawCard},
  setup(){
    const validateImages = (rule, value, callback, source) => {
      console.log('lalala')
      console.log(value)
      console.log(source)
      if (!value || value.length === 0){
        callback(new Error('请上传图片'))
      }else {
        callback()
      }
    }
    const rules = {
      title: [
        {
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        }
      ]
    }
    const ruleFormRef = ref(null)
    return {
      rules, ruleFormRef, validateImages
    }
  },
  data: () => {
    return {
      report: {
        taskId: 0,
        userId: 0,
        title: "",
        flaws: [
          {
            pictureURLs: [],
            description: '',
            stepDes: '',
            deviceInfo: ''
          }
        ],
      },
      flawCnt: [0],
      flawId: 0,
      loading: false
    }
  },
  watch: {

  },
  methods: {
    addFlaw(){
      this.report.flaws.push({
          id: this.flawId,
          reportId:0,
          pictureURLs: [],
          description: '',
          stepDes: '',
          deviceInfo: ''
    })
      this.flawId++;
      this.flawCnt.push(0);
    },
    deleteFlaw(idx){
      console.log(idx);
      let newArr = [];
      for(let i = 0; i < this.report.flaws.length; i++){
        if(i !== idx)newArr.push(this.report.flaws[i]);
      }
      console.log(newArr)
      this.report.flaws = newArr;
      this.flawCnt.pop();
    },
    issueReport(){
      const ReportDTO={
        taskId:this.$route.query.taskId,
        userId:Number.parseInt(storageGet('id')),
        title:this.report.title,
        flaws:this.report.flaws
      };
      console.log(JSON.stringify(ReportDTO))
      this.loading = true;
      postReportDetail(ReportDTO).then(res=>{
        if (res.code === SUCCESS) {
            this.$router.push({path:"/worker/modifyReport",query:{reportId:res.data.id}});
        }
        this.$emit(SHOW_ALERT,res);
      }).finally(() => {
        this.loading = false;
      });
    },
    submitReport(){
      this.ruleFormRef.validate((valid) => {
        if (valid) {
          console.log('submit!');
          this.issueReport();
        } else {
          console.log('error submit!')
          return false
        }
      })
    }

  },
}
</script>

<style scoped>

</style>
