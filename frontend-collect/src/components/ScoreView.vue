<template>
  <el-rate
      v-if="scoreDisplay!== -1"
      :model-value="scoreDisplay"
      disabled
      show-score
      text-color="#ff9900"
      score-template="{value} 分"
  />
  <span v-else style="font-size: small">
    还没有任何人为它评分~
  </span>
</template>

<script>
export default {
  name: "ScoreView",
  props: {
    score: {
      default: -1
    }
  },
  computed: {
    scoreDisplay (){
      return Math.round(this.score * 100) / 100
    }
  }
}
</script>

<style scoped>

</style>
