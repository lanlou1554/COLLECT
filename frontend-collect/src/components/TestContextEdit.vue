<template>

    <el-form-item label="测试设备">
      <el-select placeholder="设备类型" v-model="deviceType">
        <el-option v-for="option in DEVICE_TYPE_OPTIONS" :key="option" :label="option" :value="option"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="操作系统">
      <el-select placeholder="操作系统" v-model="osInfo">
        <el-option v-for="option in OS_INFO_OPTIONS" :key="option" :label="option" :value="option"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="内存大小">
      <el-select placeholder="内存大小" v-model="ramSize">
        <el-option v-for="option in RAM_SIZE_OPTIONS" :key="option" :label="option" :value="option"></el-option>
      </el-select>
    </el-form-item>
    <el-form-item label="网络环境">
      <el-select placeholder="网络环境" v-model="netEnv">
        <el-option v-for="option in NET_ENV_OPTIONS" :key="option" :label="option" :value="option"></el-option>
      </el-select>
    </el-form-item>


</template>

<script>
import {DEVICE_TYPE_OPTIONS, OS_INFO_OPTIONS, RAM_SIZE_OPTIONS, NET_ENV_OPTIONS} from "@/const/TestContext";

export default {
  name: "TestContextEdit",
  props: ['modelValue'],
  emits: [ 'update:modelValue' ],
  data(){
    return {
      deviceType: undefined,
      osInfo: undefined,
      ramSize: undefined,
      netEnv: undefined,
      DEVICE_TYPE_OPTIONS,
      OS_INFO_OPTIONS,
      RAM_SIZE_OPTIONS,
      NET_ENV_OPTIONS
    }
  },
  watch: {
    deviceType(){
      this.infoUpdate()
    },
    osInfo(){
      this.infoUpdate()
    },
    ramSize(){
      this.infoUpdate()
    },
    netEnv(){
      this.infoUpdate()
    },
    modelValue(){
      this.loadInfo()
    }
  },
  methods: {
    infoUpdate(){
      this.$emit('update:modelValue', {
        deviceType: this.deviceType,
        osInfo: this.osInfo,
        ramSize: this.ramSize,
        netEnv: this.netEnv
      })
    },
    loadInfo(){
      this.deviceType = this.modelValue.deviceType
      this.osInfo = this.modelValue.osInfo
      this.ramSize = this.modelValue.ramSize
      this.netEnv = this.modelValue.netEnv
    }
  },
  mounted() {
    this.loadInfo()
  }
}
</script>

<style scoped>
  .el-form-item{
    margin: 5px 0
  }
</style>
