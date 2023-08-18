<template>
  <el-row>
    <div>
      <el-tag v-for=""></el-tag>
    </div>
    <div>

    </div>
  </el-row>
</template>

<script>
export default {
  name: "TagEditComponent",
  props: ['candidateTag', 'modelValue'],
  data(){
    return {
      selectedTag: []
    }
  }

}
</script>

<style scoped>

</style>
