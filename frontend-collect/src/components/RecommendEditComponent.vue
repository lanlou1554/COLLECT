<template>
  <rule-card @addRule="addRule" style="margin-bottom: 10px"></rule-card>
  <rule-display v-model="rules"></rule-display>
</template>

<script>
import RuleDisplay from "@/components/RuleDisplay";
import {getRecommendRule} from "@/api/AdminApi";
import RuleCard from "@/components/RuleCard";
export default {
  name: "ManageRecommendRule",
  components:{RuleCard, RuleDisplay},
  props:{
    visable: {}
  },
  data: () => {
    return{
      loading: false,
      rules:[]
    }
  },
  watch: {
    visable(visable){
      if (visable) this.load();
    }
  },
  methods:{
    load(){
      console.log('loading')
      this.loading = true;
      let loadingLayer = this.$loading({
        target: `#${this.id}`,
        background: 'rgba(0,0,0,0)'
      })
      getRecommendRule().then(res=>{
        if (res.data.length !== 0){
          this.rules = res.data;
          // alert(JSON.stringify(this.rules))
        }
      }).finally(()=>{
        this.loading = false;
        loadingLayer.close();
      })
    },
    addRule(newRules){
      // alert(JSON.stringify(newRules))
      this.rules = newRules;
    }
  },
  mounted(){
    if(this.visable) this.load()
  }
}
</script>

<style scoped>

</style>