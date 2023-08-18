<template>
  <el-card shadow="hover">
    <el-form :model="modelValue" :rules="rules" label-position="top" ref="ruleFormRef">
      <div style="margin-bottom:10px;display: flex;justify-content: space-between;">
        <div style="font-weight: bold">缺陷#{{modelValue.id}}</div>
        <el-button @click="forkFlaw($event)" round type="success">
          提交修改后的缺陷
        </el-button>
      </div>
      <el-form-item label="缺陷描述：" prop="description">
        <el-input type="textarea"  v-model="description" :autosize="{ minRows: 4, maxRows: 8 }"></el-input>
      </el-form-item>
      <el-form-item label="复现步骤：" prop="stepDes">
        <el-input type="textarea"  v-model="stepDes" :autosize="{ minRows: 4, maxRows: 8 }"></el-input>
      </el-form-item>
      <el-form-item label="设备信息：" prop="deviceInfo">
        <el-input type="textarea"  v-model="deviceInfo" :autosize="{ minRows: 4, maxRows: 8 }"></el-input>
      </el-form-item>
      <el-form-item label="缺陷截图：" prop="pictureURLs">
        <image-upload-component v-model="pictureURLs"></image-upload-component>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import ImageUploadComponent from "@/fileComponents/ImageUploadComponent";
import {ref} from "vue";

export default {
  name: "FlawCardModify",
  components: {ImageUploadComponent},
  setup(){
    const validateImages = (rule, value, callback, source) => {

      console.log(value)
      console.log(source)
      if ( !value || value.length === 0){
        callback(new Error('请上传图片'))
      }else {
        callback()
      }
    }
    const ruleFormRef = ref(null)
    const rules = {
      description: [
        {
          required: true,
          message: '请输入缺陷描述',
          trigger: 'blur'
        }
      ],
      stepDes: [
        {
          required: true,
          message: '请输入缺陷复现步骤',
          trigger: 'blur'
        }
      ],
      deviceInfo: [
        {
          required: true,
          message: '请输入设备信息',
          trigger: 'blur'
        }
      ],
      image: [
        {
          required:true ,
          trigger: 'blur',
          validator: validateImages
        }
      ]
    }
    return { rules,ruleFormRef,validateImages }
  },
  props: {
    modelValue: {
      id:0,
      reportId:0,
      pictureURLs:[],
      description: '',
      stepDes: '',
      deviceInfo: ''
    },
    originalPicLen: {
      default: 0,
      type: Number
    },
    fold: {
      default: false,
      type: Boolean
    },
    idx: {
      default: 0,
      type: Number
    }
  },
  emits: [ 'update:modelValue','forkFlaw' ],
  data: () => {
    return {
      id: 0,
      reportId:0,
      pictureURLs: {},
      description: 'description',
      stepDes: 'step description',
      deviceInfo: 'device information'
    }
  },
  watch: {
    pictureURLs(){
      this.dataUpdate();
    },
    description(){
      this.dataUpdate();
    },
    stepDes(){
      this.dataUpdate();
    },
    deviceInfo(){
      this.dataUpdate();
    },
    modelValue(){
      this.loadFromProp()
    }
  },
  methods: {
    dataUpdate(){
      console.log('update:modelValue')
      this.$emit('update:modelValue', {
        id: this.id,
        reportId: this.reportId,
        pictureURLs: this.pictureURLs,
        description: this.description,
        stepDes: this.stepDes,
        deviceInfo: this.deviceInfo
      })
    },
    loadFromProp(){
      this.id = this.modelValue.id;
      this.reportId = this.modelValue.reportId;
      this.pictureURLs = this.modelValue.pictureURLs;
      this.description = this.modelValue.description;
      this.stepDes = this.modelValue.stepDes;
      this.deviceInfo = this.modelValue.deviceInfo;
      // this.modelValue.originalPicLen = this.modelValue.pictureURLs.length;
    },
    forkFlaw(event){
      event.target.blur();
      if(event.target.nodeName == "SPAN") {
        event.target.parentNode.blur();
      }
      this.ruleFormRef.validate((valid) => {
        if (valid) {
          // alert(this.modelValue.description);
          this.$emit('forkFlaw');
        } else {
          alert('error submit!')
          return false
        }
      })
    }
  },
  mounted() {
    this.loadFromProp()
  }

}
</script>

<style scoped>
.flaw-img{
  margin: 3px;
  border-radius: 3px;
}
</style>
