<template>
  <div style="margin-top: 10px">
    <worker-tags :user-v-o="userVO" @collect:reload-userinfo="$emit('collect:reload-userinfo')"/>
    <el-divider></el-divider>
    <el-tabs tab-position="left" v-model="currentTab">
      <el-tab-pane label="测试环境" name="WorkerTestContext"/>
      <el-tab-pane label="个人能力" name="WorkerAbility"/>
      <el-tab-pane label="个人活跃度" name="WorkerActivation"/>
      <el-tab-pane label="报告关键字" name="WorkerDomainKnowledge"/>
      <div style="margin-left: 10px">
        <router-view @collect:alert="showAlert($event)"/>
      </div>
    </el-tabs>
  </div>

</template>

<script>
import {SHOW_ALERT} from "@/const/Events";
import WorkerTags from "@/user/workerdashboardcontent/WorkerTags";
export default {
  name: "WorkerDashBoard",
  components: {WorkerTags},
  props:['userVO'],
  data(){
    return {
      dialogVisible: false,
      currentTab: ''
    }
  },
  methods: {

    showAlert(e){
      this.$emit(SHOW_ALERT, e)
    }
  },
  mounted() {
    this.currentTab = 'WorkerTestContext'
  },
  watch: {
    currentTab(){
      this.$router.push({
        name: this.currentTab,
      })
    },
    $route(to){
      this.currentTab = to.name
    },

  }
}
</script>

<style scoped>


</style>
