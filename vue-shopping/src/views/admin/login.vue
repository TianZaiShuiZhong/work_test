<template>
  <div class="login-info">
    <template>
      <el-card class="login-info-form">
        
        <div class="login-header">商城后台登录</div>
        <el-form class="form-panel" :model="form" :rules="rules" ref="form">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="用户账号" clearable maxlength="20" show-word-limit></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" placeholder="用户密码" type="password" clearable maxlength="8" show-word-limit></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%;" type="primary" @click="goLogin">登录</el-button>
          </el-form-item>
        </el-form>
      </el-card>
    </template>
    <el-button class="login-back" type="text" @click="goShopping">前往购物商城</el-button>
  </div>
</template>

<script>
import { login, logout } from "../../service/api.js";

export default {
  name: "login",
  data() {
    return {
      form: {
        username: "",
        password: "",
      },
      rules: {
        username: [
          { required: true, message: "请输入用户账号", trigger: "blur" },
        ],
        password: [
          { required: true, message: "请输入用户密码", trigger: "blur" },
        ],
      },
    };
  },
  methods: {
    goShopping() {
      window.location.href = "/shopping/login/logout";
    },
    async goLogin() {
      this.$refs.form.validate(async (valid) => {
        if (valid) {
          try {
            // 登录操作
            const res = await login({
              username: this.form.username,
              password: this.form.password,
            });
            if (res.code === 200) {
              this.$store.commit("setUsername", this.form.username);
              this.$store.commit("setLoginStatus", true);
              this.$store.commit("setToken", res.data);
              this.$store.commit("setLoginType", "admin");
              this.$message.success("登录成功");
              window.location.href = "/admin/list";
            } else {
              this.$message.error(res.msg || "登录失败");
            }
          } catch (error) {
            this.$message.error(error);
          }
        }
      });
    },
  },
  async created() {
    // 获取路由中的参数（路由定义为 /admin/login/:loginstatus，注意小写）
    const loginParam = this.$route.params.loginstatus;
    if (loginParam === "logout") {
      // 请求退出登录
      await logout({});
      // 清除本地存储
      window.localStorage.clear();
      this.$store.commit("setLoginStatus", false);
      return;
    }
    const loginStatus = this.$store.state.loginStatus;
    if (loginStatus === 'true') {
      window.alert("您已经是登录状态");
      window.location.href = "/admin/list";
    }
  },
};
</script>

<style scoped>
.login-info {
  height: 100vh;
  width: 100vw;
  text-align: center;
  background-image: url("@/assets/login/login-bg1.jpg");
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