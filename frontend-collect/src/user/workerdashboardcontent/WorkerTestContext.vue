<template>
  <div v-if="editMode">
    <el-form label-position="left" style="margin-bottom: 10px">
        <test-context-edit v-model="testContext"></test-context-edit>

    </el-form>
    <div>
      <el-button circle size="small" @click="updateTestContext">
        <el-icon>
          <ic-check></ic-check>
        </el-icon>
      </el-button>
        <el-button circle size="small" type="danger" @click="close">
          <el-icon>
            <ic-close></ic-close>
          </el-icon>
        </el-button>
    </div>
  </div>
  <div v-else>
    <test-context-display :test-context="testContext" style="margin-bottom: 10px"/>

    <el-button circle type="primary" size="small" @click="editMode = true">
      <el-icon>
        <ic-edit></ic-edit>
      </el-icon>
    </el-button>
  </div>

</template>

<script>
import {getWorkerTestContext, updateWorkerTestContext} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {storageGet} from "@/utils/util";
import TestContextEdit from "@/components/TestContextEdit";
import TestContextDisplay from "@/components/TestContextDisplay";

export default {
  name: "WorkerTestContext",
  components: {TestContextDisplay, TestContextEdit},
  mounted() {
    this.loadTestContext()
  },
  data(){
    return {
      testContext: {},
      editMode: false
    }
  },
  computed: {
    userId(){
      return Number.parseInt(storageGet('id'))
    }
  },
  methods: {
    loadTestContext(){
      getWorkerTestContext(this.userId)
          .then(res => {
            if(res.code === SUCCESS){
              this.testContext = res.data
            }else{
              this.$emit(SHOW_ALERT, res)
            }
          })
    },
    updateTestContext(){
      updateWorkerTestContext(this.testContext, this.userId)
      .then(res => {
        if(res.code === SUCCESS){
          this.testContext = res.data;
          this.editMode = false;
        }
        this.$emit(SHOW_ALERT, res)

      })
    },
    close(){
      this.editMode = false;
    }

  }
}
</script>

<style scoped>

</style>
