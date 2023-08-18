<template>
  <div>
    <user-view :user-view-vo="evaluation.user"></user-view>
    <div style="margin-left: 50px; margin-top: 10px">
      {{ evaluation.content }}
    </div>
    <div style="display: flex; flex-direction: row-reverse">
      <el-button :disabled="evaluation.flawEvaLike.status !== 0" type="text" style="color: #2861a1;" @click="dislike"><el-icon>
        <ic-star-filled v-if="evaluation.flawEvaLike.status === 2"></ic-star-filled>
        <ic-star v-else></ic-star>
      </el-icon> <span>点踩({{ evaluation.flawEvaLike.dislikeNum }}) </span></el-button>
      <el-button :disabled="evaluation.flawEvaLike.status !== 0" type="text" style="color: #249ffd;  margin-right: 10px" @click="like">
        <el-icon>
          <ic-star-filled v-if="evaluation.flawEvaLike.status === 1"></ic-star-filled>
          <ic-star v-else></ic-star>
        </el-icon>
        <span>点赞({{ evaluation.flawEvaLike.likeNum }})</span>
      </el-button>
    </div>
  </div>

</template>

<script>
import UserView from "@/components/UserView";
import {storageGet} from "@/utils/util";
import {evaluationDislike, evaluationLike} from "@/api/FlawApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {SHOW_ALERT} from "@/const/Events";
export default {
  name: "FlawEvaluationItem",
  components: {UserView},
  props: {
    flawEvaluationVo: {
      "content": "",
      "evaluationId": 0,
      "flawEvaLike": {
        "dislikeNum": 0,
        "likeNum": 0,
        "status": 0
      },
      "user": {
        "id": 0,
        "type": 0,
        "username": ""
      }
    }
  },
  data(){
    return {
      evaluation: {
        "content": "",
        "evaluationId": 0,
        "flawEvaLike": {
          "dislikeNum": 0,
          "likeNum": 0,
          "status": 0
        },
        "user": {
          "id": 0,
          "type": 0,
          "username": ""
        }
      }
    }
  },
  watch: {
    flawEvaluationVo(newVal){
      this.evaluation = newVal;
      console.log(newVal)
    }
  },
  methods: {
    like(){
      evaluationLike(this.evaluation.evaluationId, this.userId)
          .then(res => {
            if(res.code === SUCCESS){
              this.evaluation= res.data;
            }
            this.$emit(SHOW_ALERT, res)
          })
    },
    dislike()
    {
      evaluationDislike(this.evaluation.evaluationId, this.userId)
          .then(res => {
            if(res.code === SUCCESS){
              this.evaluation= res.data;
            }
            this.$emit(SHOW_ALERT, res)
          })
    }
  },
  computed: {
    userId(){
      return Number.parseInt(storageGet("id"));
    },

  },
  mounted() {
    this.evaluation = this.flawEvaluationVo;
    console.log(this.flawEvaluationVo);
  }
}
</script>

<style scoped>

</style>
