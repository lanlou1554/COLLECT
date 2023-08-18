<template>
  <el-descriptions
      :column="1"
      size="large"

  >
    <el-descriptions-item label="测试设备">{{ testContext.deviceType }}</el-descriptions-item>
    <el-descriptions-item label="操作系统">{{ testContext.osInfo }}</el-descriptions-item>
    <el-descriptions-item label="内存大小">{{ testContext.ramSize }}</el-descriptions-item>
    <el-descriptions-item label="网络环境">{{ testContext.netEnv }}</el-descriptions-item>
  </el-descriptions>
</template>

<script>
export default {
  name: "TestContextDisplay",
  props: ['testContext']
}
</script>

<style scoped>

</style>
