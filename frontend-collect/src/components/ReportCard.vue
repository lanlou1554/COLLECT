<template>
  <el-card shadow="hover">
    <template #header>
      <div class="card-header">
        <user-view :user-view-vo="{username: reportView.username}"></user-view>
        <el-button class="button" type="text" @click="checkDetail">查看详情</el-button>
      </div>
    </template>
    <div style="font-size: small">
      <div class="text item">{{ reportView.title }}</div>
      <div>
        <span class="inline-title">平均分数：</span>
        <score-view :score="reportView.score"></score-view>
      </div>
      <div>
        <span class="inline-title">缺陷个数：</span>
        {{ reportView.flawNum }}
      </div>
    </div>
  </el-card>
</template>

<script>
import ScoreView from "@/components/ScoreView";
import UserView from "@/components/UserView";
export default {
  name:"ReportCard",
  components: {UserView, ScoreView},
  props:{
    reportView:{},
  },
  methods:{
    checkDetail(){
      const reportId=this.reportView.id
      this.$router.push({path:"reportDetail",query:{reportId:reportId}})
    }
  }
}
</script>

<style>
.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.text {
  font-size: medium;
  font-weight: bold;
}

.item {
  margin-bottom: 18px;
}



</style>
