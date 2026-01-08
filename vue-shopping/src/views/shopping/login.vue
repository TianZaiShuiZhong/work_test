<template>
  <div class="login-info">
    <template v-if="login">
      <el-card class="login-info-form">
        
        <div class="login-header">商城用户登录</div>
        <el-form class="form-panel" :model="form" :rules="rules" ref="form">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="用户账号" clearable maxlength="20" show-word-limit></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" placeholder="用户密码" type="password" clearable maxlength="8" show-word-limit></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%;" type="primary" @click="goLogin">登录</el-button>
            <el-button type="text" @click="changeLogin(false, true)">前往注册</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </template>
    <template v-if="register">
      <el-card class="login-info-form">
        
        <div class="login-header">商城用户注册</div>
        <el-form class="form-panel" :model="form" :rules="rules" ref="form">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="用户账号" clearable maxlength="20" show-word-limit></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" placeholder="用户密码" type="password" clearable maxlength="8" show-word-limit></el-input>
          </el-form-item>
          <el-form-item prop="confirmPassword">
            <el-input v-model="form.confirmPassword" placeholder="确认密码" type="password" clearable maxlength="8" show-word-limit></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%;" type="primary" @click="goRegister">注册并登录</el-button>
            <el-button type="text" @click="changeLogin(true, false)">已有账号</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </template>
    <div class="login-back">
      <el-button type="text" @click="goAdmin">前往商城后台</el-button>
    </div>
  </div>
</template>

<script>
import { shoppingLogin, shoppingLogout, register } from "../../service/api.js";

export default {
  name: "login",
  data() {
    return {
      login: true,
      register: false,
      form: {
        username: "",
        password: "",
        confirmPassword: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户账号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入用户密码", trigger: "blur" },
        ],
        confirmPassword: [
          { required: true, message: "请输入确认密码", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    goAdmin() {
      window.location.href = '/admin/login/logout';
    },
    async goRegister() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          if (this.form.password !== this.form.confirmPassword) {
            this.$message.error("密码不一致，请重新输入");
            this.form.password = "";
            this.form.confirmPassword = "";
            return;
          }
          try {
            // 注册操作
            await register({
              username: this.form.username,
              password: this.form.password,
              confirmPassword: this.form.confirmPassword,
            });
            // 登录操作
            const res = await shoppingLogin({
              username: this.form.username,
              password: this.form.password,
            });
            this.$store.commit("setUsername", this.form.username);
            this.$store.commit("setLoginStatus", true);
            this.$store.commit("setToken", res.data);
            this.$store.commit("setLoginType", "shopping");
            this.login = false;
            this.register = false;
            this.$message.success("注册并登录成功");
            window.location.href = "/shopping/list";
          } catch (error) {
            this.$message.error(error);
          }
        }
      });
    },
    changeLogin(b1, b2) {
      this.login = b1;
      this.register = b2;
    },
    async goLogin() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          try {
            // 登录操作
            const res = await shoppingLogin({
              username: this.form.username,
              password: this.form.password,
            });
            this.$store.commit("setUsername", this.form.username);
            this.$store.commit("setLoginStatus", true);
            this.$store.commit("setToken", res.data);
            this.$store.commit("setLoginType", "shopping");
            this.login = false;
            this.register = false;
            this.$message.success("登录成功");
            window.location.href = "/shopping/list";
          } catch (error) {
            this.$message.error(error);
          }
        }
      });
    },
  },
  async created() {
    // 获取路由中的参数(登出参数)
    if (this.$route.params.loginStatus === "logout") {
      // 请求退出登录
      await shoppingLogout({});
      // 清除本地存储
      window.localStorage.clear();
      // 清除 Vuex 中的登录状态/凭证
      this.$store.commit("setLoginStatus", false);
      this.$store.commit("setToken", null);
      this.$store.commit("setLoginType", null);
      this.$store.commit("setUsername", null);
      this.$store.commit("setCartNum", 0);
      return;
    }
    // 初始登出状态
    const loginStatus = this.$store.state.loginStatus;
    if (loginStatus === 'true') {
      this.login = false;
      this.register = false;
      window.alert("您已经是登录状态");
      window.location.href = "/shopping/list";
    }
  },
};
</script>

<style scoped>
.login-info {
  height: 100vh;
  width: 100vw;
  text-align: center;
  background-image: url("@/assets/login/login-bg.jpg");
  background-size: 100% 100%;
}
.shopping-logo {
  width: 128px;
  height: 128px;
}
.login-header {
  font-size: 32px;
  font-weight: 600;
  color: #5c6b77;
  margin-bottom: 20px;
}
.login-info-form {
  width: 500px;
  height: 500px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.form-panel {
  margin-left: 40px;
  margin-right: 40px;
}
.login-back {
  position: absolute;
  bottom: 100px;
  left: 50%;
  transform: translateX(-50%);
}
</style>