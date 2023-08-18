<template>
  <div style="display: flex; justify-content: center;  align-items: center; padding-top: 5px">
    <el-card shadow="hover">
      <template #header>
        <div style="font-size: x-large; margin-bottom: 10px">登录</div>
      </template>
      <el-form
          class="login"
          ref="ruleFormRef"
          :model="ruleForm"
          :rules="rules"
          label-position="top"
      >
        <el-form-item label="用户名" prop="name">
          <el-input v-model="ruleForm.name"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="pass">
          <el-input v-model="ruleForm.pass"
                    type="password"
                    autocomplete="off"></el-input>
        </el-form-item>

        <div style="display: flex;">
          <el-button round type="primary" @click="submitForm()" class="f1"
          >登录</el-button>
<!--          <el-button  type="danger" round @click="resetForm(ruleFormRef)" class="f1">
            重置
          </el-button>-->
        </div>
      </el-form>
      <div style="display: flex; justify-content: center"><router-link to="/register" class="link">没有账号？去注册</router-link></div>
    </el-card>

  </div>
</template>

<script>
import {ref, onMounted} from 'vue'
import {login} from "@/api/UserApi";
import {SUCCESS} from "@/const/ResponseStatus";
import {ROLE_PATH} from "@/const/UserRole";
import {setLoginInfo} from "@/utils/util";
import {SHOW_ALERT} from "@/const/Events";
import {autoLogin} from "@/utils/util";
export default {
  name: 'UserLogin',
  setup() {
    const ruleFormRef = ref(null)

    onMounted(() => {
      // DOM 元素将在初始渲染后分配给 ref
      //console.log(ruleFormRef.value) // <div>This is a root element</div>
    })

    return {
      ruleFormRef
    }
  },
  data: () => {
    return {
      ruleForm: {
        name: '',
        pass:'',
      },
      rules: {
        name: [
          {
            required: true,
            message: '请输入用户名',
            trigger: 'blur',
          },
          {
            min: 5,
            max: 12,
            message: 'Length should be 6 to 12',
            trigger: 'blur',
          },
        ],
        pass:[
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
        ],
      }
    }
  },
  methods: {
    submitForm(){
      console.log(this.ruleFormRef)
      if (!this.ruleFormRef) return
      this.ruleFormRef.validate((valid) => {
        if (valid) {
          console.log('submit!');
          this.handleLogin();
        } else {
          console.log('error submit!')
          return false
        }
      })
    },
    resetForm(){
      this.ruleFormRef.resetFields()
    },
    handleLogin() {
      login({ username: this.ruleForm.name, password: this.ruleForm.pass }).then(res => {
        if (res.code === SUCCESS) {
          setLoginInfo(res.data)
          this.username = res.data.username;
          setTimeout(() => {
            this.$router.push(`${ROLE_PATH[res.data.type]}`);
          }, 1000);
        }
        this.$emit(SHOW_ALERT, res);
      });
    }
  },
  mounted() {
    autoLogin((role) => {
      if(role !== null){
        console.log(role)
        this.$router.push(ROLE_PATH[role])
      }
    })
  }
}
</script>

<style scoped>
.login{
  margin: 20px 100px;
}

@media (max-width: 400px) {
  .login{
    margin: 20px 20px;
  }

}
.link{
  color: darkgrey;
}

.link:hover{
  color: dodgerblue;
  text-underline: dodgerblue;
}
</style>




