<template>
  <el-card shadow="hover">
    <template #header>
      <el-row style="align-items: center; justify-content: space-between">
        <div style="font-weight: bold">{{ `缺陷#${flaw.id}` }}</div>
        <el-row>
          <slot name="header-right-conner"></slot>
        </el-row>
      </el-row>
    </template>
      <div>缺陷: </div>
      <el-card shadow="never" class="block"> {{ flaw.description }} </el-card>

      <div>复现步骤: </div>
      <el-card shadow="never" class="block"> {{ flaw.stepDes }} </el-card>
      <div>设备信息: </div>
      <el-card shadow="never" class="block"> {{ flaw.deviceInfo }} </el-card>
      <div>缺陷截图: </div>
      <el-image
          class="block"
          v-for="(url, idx) in flaw.pictureURLs"
          :key="idx"
          :preview-src-list="[url]"
          style="width: 100px; height: 100px"
          :src="url"
          fit="cover"
      >
      </el-image>
      <slot name="footer"></slot>
  </el-card>
</template>

<script>
export default {
  name: "FlawCardDisplay",
  props: {
    flaw: {}
  }
}
</script>

<style scoped>
.block{
  margin-bottom: 20px;
  margin-top: 10px;
}
</style>
