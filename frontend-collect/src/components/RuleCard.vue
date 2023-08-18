<template>
  <el-card shadow="hover">
    <template #header>
      <span style="margin-left:20px;font-weight: bolder" >
        增加规则
      </span>
      <span style="margin-left:20px;font-weight: 300;font-size: small" >
        请输入标题与权重
      </span>
      <el-button style="margin-right:20px;float: right" type="primary" @click="submitRule($event)">提交</el-button>
    </template>
    <el-form :inline="true" :model="rule" :rules="rules" ref="ruleFormRef">
      <el-form-item prop="title" style="width: 740px;margin-left: 10px" label="标题" >
        <el-input v-model="rule.title" />
      </el-form-item>
      <el-form-item v-for="(item,idx) in rule.factors" :key="idx"
                    :prop="`factors.${idx}.weight`"
                    :rules="[
                        {
                          required: true,
                          message: '不能为空',
                          trigger: 'blur'
                        },
                      { validator: checkWeight, trigger: 'blur' }
                    ]"
                    :label="item.name">
        <el-input style="width: 60px" v-model="item.weight" :placeholder="item.weight" />
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import {getFactors} from "@/api/AdminApi";
import {addRule} from "@/api/AdminApi";
import { ref } from 'vue'
import {SUCCESS} from "@/const/ResponseStatus";
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'
export default {
  name: "RuleCard",
  setup(){
    const ruleFormRef = ref(null)
    const rules = {
      title:[
        {
          required: true,
          message: '请输入标题',
          trigger: 'blur'
        },
        {min:1, max: 15, message: '最大长度不超过15个字', trigger: 'blur' },
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
          callback(new Error('必须0-1之间'))
        } else {
          callback()
        }
      }
    }
    return {rules,ruleFormRef,checkWeight}
  },
  emits: ['addRule'],
  data: () => {
    return {
      rule:{
        factors: [
          {
            name:'',
            weight:1
          }
        ],
        title: ''
      },
      loading: false
    }
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
    },
    submitRule(event){
      event.target.blur();
      if(event.target.nodeName == "SPAN") {
        event.target.parentNode.blur();
      }
      this.ruleFormRef.validate((valid) => {
        if (valid) {
          if (this.loading) return;
          const RecommendRuleDTO={
            name: this.rule.title,
            factors: this.rule.factors
          }
          // alert(JSON.stringify(RecommendRuleDTO))
          this.loading = true;
          addRule(RecommendRuleDTO).then(res=>{
            if (res.code === SUCCESS){
              this.$emit('addRule',res.data);
            }
            this.showAlert(res);
          }).finally(()=>{
            this.loading = false;
          });
        } else {
          // alert('error submit!')
          return false
        }
      })
    }
  },
  mounted() {
    getFactors().then(res=>{
      if (res.code === SUCCESS){
        this.rule.factors = res.data;
        // alert(JSON.stringify(this.factors));
      }else{
        this.showAlert(res)
      }
    })
  }
}
</script>

<style scoped>

</style>