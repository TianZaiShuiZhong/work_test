<template>
  <div class="cart">
    <div class="cart-header">
      <div class="cart-header-line">
        <div class="cart-header-title">用户管理</div>
        <el-button type="primary" size="small" @click="handleCreateItem">添加用户</el-button>
      </div>
    </div>
    <div style="padding: 16px;">
      <el-table :data="list" style="width: 100%" stripe border>
        <el-table-column prop="nickname" label="用户昵称" align="center"></el-table-column>
        <el-table-column prop="username" label="登录账号" align="center"></el-table-column>
        <el-table-column prop="password" label="登录密码" align="center">
          <template slot-scope="scope">
            {{ scope.row.password || '不显示' }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" align="center">
          <template slot-scope="scope">
            <el-button size="mini" @click="handleEditItem(scope.row)">编辑</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!-- 添加/编辑用户对话框 -->
    <el-dialog :title="title + '用户'" :visible.sync="dialogvisible" width="800px" :before-close="close">
      <el-form :model="form" :rules="rules" ref="form" label-width="160px">
        <el-form-item label="用户昵称" prop="nickname">
          <el-input v-model="form.nickname" maxlength="20" show-word-limit clearable></el-input>
        </el-form-item>
        <el-form-item label="登录账号" prop="username">
          <el-input v-model="form.username" maxlength="8" show-word-limit clearable :readonly="title==='编辑'"></el-input>
        </el-form-item>
        <el-form-item label="登录密码" prop="password">
          <el-input v-model="form.password" maxlength="8" show-word-limit clearable></el-input>
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input v-model="form.confirmPassword" maxlength="8" show-word-limit clearable></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取消</el-button>
        <el-button type="primary" v-loading="btnLoading" @click="submitForm('form')">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getUserList, addUser, updateUser, deleteUser } from "../../service/api.js";

export default {
  name: "user",
  data() {
    return {
      // 弹窗相关
      dialogvisible: false,
      btnLoading: false,
      title: "",
      form: {
        nickname: "",
        username: "",
        password: "",
        confirmPassword: ""
      },
      rules: {
        nickname: [{ required: true, message: "请输入用户昵称", trigger: "blur" }],
        username: [{ required: true, message: "请输入登录账号", trigger: "blur" }],
        password: [{ required: true, message: "请输入登录密码", trigger: "blur" }],
        confirmPassword: [{ required: true, message: "请输入确认密码", trigger: "blur" }]
      },
      list: []
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 请求列表
    async getList() {
      try {
        const res = await getUserList({ page: 1, size: 100 });
        if (res.code === 200) {
          this.list = res.data;
        } else {
          this.$message.error(res.msg);
          if (res.msg === 'Token无效' || res.msg === '权限不足') {
             setTimeout(() => {
                this.$router.push('/admin/login/logout');
             }, 1000);
          }
        }
      } catch (err) {
         console.error(err);
      }
    },
    // 添加
    handleCreateItem() {
      this.dialogvisible = true;
      this.title = "添加";
      this.form = {
        nickname: "",
        username: "",
        password: "",
        confirmPassword: ""
      };
    },
    // 编辑
    handleEditItem(row) {
      this.dialogvisible = true;
      this.title = "编辑";
      this.form.id = row.id;
      this.form.nickname = row.nickname;
      this.form.username = row.username;
      this.form.password = row.password;
      this.form.confirmPassword = row.confirmPassword;
    },
    // 删除
    handleDelete(row) {
      this.$confirm("确定删除此条数据?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(async () => {
          // 调用删除接口
          const params = {
            id: row.id
          };
          const res = await deleteUser(params);
          if (res.code === 200) {
            this.$message.success("操作成功");
            this.getList();
          }
        })
        .catch(() => {});
    },
    // 重置表单
    close() {
      this.$refs.form.resetFields();
      this.dialogvisible = false;
    },
    // 表单提交
    submitForm(formName) {
      this.btnLoading = true;
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          if (this.form.id) {
            const res = await updateUser(this.form);
            if (res.code === 200) {
              this.$message.success("操作成功");
              this.btnLoading = false;
              this.close();
              this.getList();
            } else {
              this.$message.error(res.msg);
            }
          } else {
            const res = await addUser(this.form);
            if (res.code === 200) {
              this.$message.success("操作成功");
              this.btnLoading = false;
              this.close();
              this.getList();
            } else {
              this.$message.error(res.msg);
            }
          }
        } else {
          this.btnLoading = false;
        }
      });
    },
  },
};
</script>

<style scoped>
.cart {
  margin: 32px;
  background: #fff;
  border: 1px solid #dddee1;
  border-radius: 10px;
}
.cart-header-line {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding: 16px 32px;
  border-bottom: 1px solid #dddee1;
  border-radius: 10px 10px 0 0;
  background: #f8f8f9;
}
.cart-header-title {
  font-weight: 600;
}
</style>