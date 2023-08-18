<template>
  <div style="height: 50px; align-content: center; justify-content: center" class="col-container">
    <el-alert :closable="false" effect="dark"  style="height: 40px; max-width: 200px;"  v-if="showDialog" :title="msg" :type="dialogType" />
  </div>

  <el-card>
    <template #header>
      <div style="font-size: x-large; ">注册</div>

    </template>

    <el-form
        style="margin: 20px 40px"
        ref="ruleFormRef"
        :model="ruleForm"
        :rules="rules"
        label-width=80px
    >
      <el-divider>
        基本信息
      </el-divider>
        <el-form-item label="用户名" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input v-model="ruleForm.pass"
                    type="password"
                    autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="checkPass">
          <el-input
              v-model="ruleForm.checkPass"
              type="password"
              autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="电话号码" prop="phone">
          <el-input v-model="ruleForm.phone"></el-input>
        </el-form-item>
        <el-form-item label="电子邮箱" prop="email">
          <el-input v-model="ruleForm.email"></el-input>
        </el-form-item>
        <el-form-item label="选择身份" prop="role">
          <el-radio-group v-model="ruleForm.role">
            <el-radio :label="EMPLOYER">发包方</el-radio>
            <el-radio :label="WORKER">众包工人</el-radio>
          </el-radio-group>
        </el-form-item>
        <div v-if="isWorker">
          <el-form-item >
            <el-checkbox-group v-model="ruleForm.tagGroups" size="small">
              <el-space wrap>
                <el-checkbox border v-for="tag in filteredTags" :label="tag.id" :key="tag.id">
                  {{ tag.name }}
                </el-checkbox>
              </el-space>
            </el-checkbox-group>
          </el-form-item>
<!--          <el-divider>
            测试设备
          </el-divider>
          <el-row>
            <test-context-edit></test-context-edit>
          </el-row>-->

        </div>

        <div style="display: flex; justify-content: center">
          <el-button
              type="primary"
              @click="submitForm()"
              round
          >注册</el-button>
          <el-button
              round
              type="danger"
              @click="resetForm()">重置</el-button>
        </div>




    </el-form>
  </el-card>

</template>

<script>
import {register} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {EMPLOYER, WORKER} from "@/const/UserRole";
import {ref, reactive} from "vue";
import {SHOW_ALERT} from "@/const/Events";
import {EMAIL_FORMAT} from "@/const/InputFormate";
import {UserTags} from "@/const/Tags";
import {tagFilter} from "@/utils/util";
export default {
  name: 'UserRegister',
  components: {},
  data: () => {
    return {
      EMPLOYER,
      WORKER,
      msg: '',
      dialogType: 'success',
      showDialog: false,
      UserTags,
      step: 0
    }
  },
  setup() {
    const ruleFormRef = ref(null)
    const ruleForm = reactive({
      name: '',
          pass:'',
          checkPass:'',
          phone:'',
          email:'',
          role: '',
          tagGroups: []
    })
    const validatePassword = (rule, value, callback) => {
      if (value !== '' && ruleForm.checkPass !== '' && ruleFormRef.value) {
        ruleFormRef.value.validateField('checkPass', () => null)
        callback()
      }else{
        callback()
      }
    }
    const validatePasswordConfirm = (rule, value, callback) => {
      if( value !== ruleForm.pass ){
        callback(new Error('两次输入的密码不一致'))
      }else {
        callback()
      }
    }
    const validateEmail = (rule, value, callback) => {
      if (!EMAIL_FORMAT.test(value)){
        callback(new Error('电子邮箱格式不正确'))
      }else {
        callback()
      }
    }
    const rules = reactive({
           name: [
        {
          required: true,
          message: '请输入用户名',
          trigger: 'blur',
        },
        {
          min: 6,
          max: 12,
          message: 'Length should be 6 to 12',
          trigger: 'blur',
        },
      ],
           pass: [
        {
          required: true,
          message: '请输入密码',
          trigger: 'blur',
        },
        {
          min: 8,
          max: 18,
          message: 'Length should be 8 to 18',
          trigger: 'blur',
        },
        {
          trigger: 'blur',
          validator: validatePassword
        }
      ],
      checkPass: [
        {
          required: true,
          message: '请再次输入密码',
          trigger: 'blur',
        },
        {
          trigger: 'blur',
          validator: validatePasswordConfirm
        }
      ],
          phone: [
        {
          required: true,
          message: '请输入电话号码',
          trigger: 'blur',
        },
        {
          min: 11,
          max: 11,
          message: '应为11位',
          trigger: 'blur',
        },
      ],
          email: [
        {
          required: true,
          message: '请输入电子邮箱',
          trigger: 'blur',
        },
        {
          trigger: 'blur',
          validator: validateEmail
        }
      ],
           role: [
        {
          required: true,
          message: '请选择角色',
          trigger: 'change',
        },
      ],

    })

    return {
      ruleFormRef,
      validatePassword,
      validatePasswordConfirm,
      rules,
      ruleForm,
      validateEmail
    }

  },
  methods: {
    submitForm(){
      if (!this.ruleFormRef) return
      this.ruleFormRef.validate((valid) => {
        if (valid) {
          this.handleRegister()
          console.log('submit!')
        } else {
          console.log('error submit!')
          return false
        }
      })
      //console.log(this.ruleForm)
    },
    resetForm(){
      this.ruleFormRef.resetFields()
    },
    handleRegister() {
      register({
        type: this.ruleForm.role,
        username: this.ruleForm.name,
        password: this.ruleForm.pass,
        email: this.ruleForm.email,
        phone: this.ruleForm.phone,
        tagGroups: this.ruleForm.tagGroups
      }).then(res => {
        if(res.code === SUCCESS){
          setTimeout(() => {
            this.$router.push('/')
          }, 1000);
        }
        this.$emit(SHOW_ALERT, res);
      });
    },

  },
  computed: {
    isWorker(){
      return Number.parseInt(this.ruleForm.role) === WORKER
    },
    filteredTags(){
      return UserTags.filter(tagFilter)
    }
  },
  watch: {
    isWorker(isWorker){
      if(!isWorker)this.ruleForm.tagGroups = []
    }
  }
}
</script>

<style scoped>

</style>
