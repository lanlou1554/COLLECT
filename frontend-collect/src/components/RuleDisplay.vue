<template>
  <el-card shadow="hover" >
    <template #header>
      <div style="text-align: center;font-weight: bolder" >
        现有规则
      </div>
    </template>
    <el-table :data="rules" style="width:100%;display: flex;align-items: center;justify-content: center;" max-height="1200" :row-style="rowClass">
      <el-table-column fixed="left" prop="using" label="" width="40" align="right" >
        <template #default="scope">
          <el-tag
              type="success"
              v-if="scope.row.using"
              disable-transitions
          >√</el-tag
          >
        </template>
      </el-table-column>
      <el-table-column fixed="left" prop="name" label="规则名称" width="120" align="center" ></el-table-column>
      <el-table-column align="center" v-for="(item,idx) in tableHead" :key="idx" :prop="item.property" :label="item.label" width="140" ></el-table-column>
      <el-table-column fixed="right" label="操作" width="140"  align="center">
        <template #default="scope">
          <el-button
              plain
              size="small"
              type="success"
              @click="handlePick($event,scope.$index)"
          >选择</el-button>
          <el-tooltip content="无法删除当前使用中的规则" placement="right" effect="light" :disabled="!scope.row.using">
            <span style="margin-left:10px">
              <el-button
                  plain
                  :disabled="scope.row.using"
                  size="small"
                  type="danger"
                  @click="handleDelete($event,scope.$index)"
              >删除</el-button>
            </span>
          </el-tooltip>

        </template>
      </el-table-column>
    </el-table>
  </el-card>
</template>

<script>
import {deleteRule} from "@/api/AdminApi";
import {pickRule} from "@/api/AdminApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {STATUS_2_TYPE} from "@/const/ResponseStatus";
import { ElMessage } from 'element-plus'
export default {
  name: "RuleDisplay",
  // setup(){
  //   const handleClick = () => {
  //     alert(JSON.stringify(this.rules))
  //   }
  //   return {handleClick}
  // },
  props: {
    modelValue:[]
  },
  data(){
    return{
      rules:[],
      tableHead:[]
    }
  },
  methods:{
    rowClass({rowIndex}){
      if (this.rules[rowIndex].using){
        return {"background-color":"rgba(60, 179, 113,0.1)"}
      }
    },
    showAlert( event ){
      console.log('alert')
      let duration = 1000;
      if(event){
        if(event.alertDuration)duration = event.alertDuration;
        if(event.msg)this.alertMsg = event.msg;
        else if(event.message)this.alertMsg = event.message;
        if(event.type)this.alertType = event.type;
        else if(event.code !== undefined && event.code !== null)this.alertType = STATUS_2_TYPE[event.code];
        console.log(event)
        ElMessage({
          message: this.alertMsg,
          type: this.alertType,
          duration: duration
        })
      }

      this.alert = true;
      setTimeout(() => {
        console.log('-alert')
        this.alert = false;
      }, duration)
    },
    handleDelete(event,index){
      event.target.blur();
      if(event.target.nodeName == "SPAN") {
        event.target.parentNode.blur();
      }
      deleteRule(this.rules[index].id).then(res=>{
        if (res.code === SUCCESS){
          // alert(JSON.stringify(this.rules))
          // alert(this.rules[index].id)
          // alert(this.rules[index].name)
          this.rules.splice(index,1)
        }
        this.showAlert(res);
      })
    },
    handlePick(event,index){
      event.target.blur();
      if(event.target.nodeName == "SPAN") {
        event.target.parentNode.blur();
      }
      pickRule(this.rules[index].id).then(res=>{
        if (res.code === SUCCESS){
          // alert(JSON.stringify(this.rules))
          // alert(this.rules[index].id)
          // alert(this.rules[index].name)
          let tempRule = this.rules[index];
          for(let i=0;i<this.rules.length;i++){
            if (this.rules[i].using){
              this.rules[i].using = false;
            }
          }
          tempRule.using=true;
          this.rules.splice(index,1);
          this.rules.unshift(tempRule);
        }
        // alert(res.message)
        this.showAlert(res)
      })
    }
  },
  watch:{
    //TODO 默认数据中每条factors的元素完全相同
    //TODO getFactor要与未来返回的规则factor中name完全一致
    modelValue() {
      //TODO 将modelValue using的移到最前

      this.tableHead = []
      this.rules = []
      if (this.modelValue.length > 0){
        const modelTemp = this.modelValue[0];
        for(let i=0;i<modelTemp.factors.length;i++){
          this.tableHead.push({ label: modelTemp.factors[i].name+"权重", property: `rule${i}` });
        }
        // alert(JSON.stringify(this.tableHead))
      }
      for(let i=0;i<this.modelValue.length;i++){
        const model = this.modelValue[i];
        var rule = {};
        rule.id = model.id;
        rule.name = model.name;
        rule.using = model.using;
        for(let j=0;j<model.factors.length;j++){
          const factorName = "rule"+ String(j);
          rule[factorName] = model.factors[j].weight;
        }
        // alert(JSON.stringify(rule));
        this.rules.push(rule);
      }
    }
  }
}
</script>

<style scoped>

</style>