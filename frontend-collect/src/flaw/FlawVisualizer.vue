<template>
  <el-card shadow="hover" style="display: flex; flex-direction: row; justify-content: center">
    <flaw-map v-loading="loading"
              element-loading-text="正在加载缺陷图，请稍后……"
              ref="flawMapRef" @collect:select="onMapNodeSelected($event)" :flaw-map-data="flawMapData"></flaw-map>
  </el-card>
  <el-drawer v-model="showDrawer" :size="'90%'">
    <el-card style="margin-bottom: 50px;" shadow="hover">
      <flaw-tree
          :current-flaw="flaw"
          v-loading="loading"
          element-loading-text="正在加载缺陷树，请稍后……"
          :flaw-tree-data="treeData" @select="onTreeNodeSelected($event)"></flaw-tree>
    </el-card>
    <flaw-card-display :flaw="flaw">
      <template #header-right-conner>
        <score-view :score="flaw.score">
        </score-view>
      </template>
    </flaw-card-display>
  </el-drawer>
</template>

<script>
import {ref} from "vue";
import FlawMap from "@/components/FlawMap";
import FlawTree from "@/components/FlawTree";
import FlawCardDisplay from "@/components/FlawCardDisplay";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
import {getFlawMap, getFlawTree} from "@/api/FlawApi";
import ScoreView from "@/components/ScoreView";
export default {
  name: "FlawVisualizer",
  components: {ScoreView, FlawCardDisplay, FlawTree, FlawMap},
  data(){
    return {
      showDrawer: false,
      loading:false,
      treeData: {},
      flawMapData:{},
      flaw: {}
    }
  },
  watch: {
    treeData(){
      this.loadDefaultFlaw();
    }
  },
  methods: {
    onTreeNodeSelected(flawTreeNode){
      this.flaw = flawTreeNode.current;
    },
    loadDefaultFlaw(){
      this.flaw = this.treeData.current;
    },
    onMapNodeSelected(selectedFlawId){
      /*this.showDrawer = true;
      this.treeData = {
        current: e.flaw,
        children: [{
          current: {
            id: 1
          },
          children: []
        }]
      };*/
      this.loading = true;
      getFlawTree(selectedFlawId).then(res => {
        if(res.code === SUCCESS){
          this.flaw = res.data.current;
          this.treeData = res.data
          this.showDrawer = true;
          this.loading = false;
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })

    },
    loadFlawMap(){
      this.loading = true;
      const {taskId} = this.$route.query
      getFlawMap(taskId).then(res => {
        if(res.code === SUCCESS){
          this.flawMapData = res.data
          this.loading = false
        }else{
          this.$emit(SHOW_ALERT, res)
        }
      })

    }
  },
  mounted() {

    this.flawMapRef.initMap();
    this.loadFlawMap();
  },
  setup(){
    const flawMapRef = ref(null)
    return {
      flawMapRef
    }
  }
}
</script>

<style scoped>

</style>
