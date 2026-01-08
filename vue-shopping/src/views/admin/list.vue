<template>
  <div class="cart">
    <div class="cart-header">
      <div class="cart-header-line">
        <div class="cart-header-title">商品管理</div>
        <el-button type="primary" size="small" @click="handleCreateItem">添加商品</el-button>
      </div>
    </div>
    <div style="padding: 16px;">
      <el-table :data="list" style="width: 100%" stripe border>
        <el-table-column prop="name" label="商品名称" align="center"></el-table-column>
        <el-table-column prop="brand" label="品牌" align="center" width="100"></el-table-column>
        <el-table-column prop="cost" label="价格" align="center" width="100">
           <template slot-scope="scope">¥{{ scope.row.cost }}</template>
        </el-table-column>
        <el-table-column prop="color" label="颜色" align="center" width="80"></el-table-column>
        <el-table-column prop="sales" label="销量" align="center" width="80"></el-table-column>
        <el-table-column prop="state" label="状态" align="center" width="80">
          <template slot-scope="scope">
            <el-tag :type="scope.row.state === 1 ? 'success' : 'info'">
              {{ scope.row.state === 1 ? '上架' : '下架' }}
            </el-tag>
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
    <!-- 添加/编辑商品对话框 -->
    <el-dialog :visible.sync="dialogvisible" :title="title" width="50%" :before-close="close">
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="商品名称" prop="name">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
        <el-form-item label="商品品牌" prop="brand">
          <el-select v-model="form.brand" placeholder="请选择商品品牌">
            <el-option label="品牌1" value="品牌1"></el-option>
            <el-option label="品牌2" value="品牌2"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品价格" prop="cost">
          <el-input v-model="form.cost"></el-input>
        </el-form-item>
        <el-form-item label="商品颜色" prop="color">
          <el-select v-model="form.color" placeholder="请选择商品颜色">
            <el-option label="白色" value="白色"></el-option>
            <el-option label="黑色" value="黑色"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品状态" prop="state">
          <el-select v-model="form.state" placeholder="请选择商品状态">
            <el-option label="上架" :value="1"></el-option>
            <el-option label="下架" :value="0"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商品图片" prop="image">
          <el-upload class="avatar-uploader" action="#" :show-file-list="false" :http-request="handleUploadImage" :before-upload="beforeAvatarUpload">
             <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
        </el-form-item>
        <el-form-item label="商品详情图片" prop="imageDetail">
          <el-upload class="avatar-uploader" action="#" :show-file-list="false" :http-request="handleUploadImageDetail" :before-upload="beforeAvatarUpload1">
             <img v-if="imageDetailUrl" :src="imageDetailUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon"></i>
          </el-upload>
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
import baseURL from '../../config/index.js';
import { getGoodsList, addGoods, updateGoods, deleteGoods, uploadFile } from '../../service/api.js';

export default {
  name: "cart",
  data() {
    return {
      // 弹窗相关
      dialogvisible: false,
      btnLoading: false,
      title: "",
      adminUrl: baseURL.uploadFileUrl,
      imageUrl: "",
      imageDetailUrl: "",
      form: {
        id: "",
        name: "",
        brand: "",
        cost: "",
        color: "",
        state: 1,
        image: "",
        imageDetail: "",
        sales: 0
      },
      rules: {
        name: [{ required: true, message: "请输入商品名称", trigger: "blur" }],
        brand: [{ required: true, message: "请选择商品品牌", trigger: "change" }],
        cost: [{ required: true, message: "请输入商品价格", trigger: "blur" }],
        color: [{ required: true, message: "请选择商品颜色", trigger: "change" }],
        state: [{ required: true, message: "请选择商品状态", trigger: "change" }],
        image: [{ required: true, message: "请上传商品图片", trigger: "change" }],
        imageDetail: [{ required: true, message: "请上传商品详情", trigger: "change" }]
      },
      list: []
    };
  },
  computed: {
     headerObj() {
        return {
           Authorization: `Bearer ${this.$store.state.token}`
        }
     }
  },
  created() {
    this.getList();
  },
  methods: {
    // 图片处理
    geturl(url) {
      return baseURL.baseUrl + '/' + url;
    },
    // 请求列表
    async getList() {
      try {
        const res = await getGoodsList({ page: 1, size: 100 });
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
        id: "",
        name: "",
        brand: "",
        cost: "",
        color: "",
        state: 1,
        image: "",
        imageDetail: "",
        sales: 0
      };
      this.imageUrl = "";
      this.imageDetailUrl = "";
    },
    // 编辑
    handleEditItem(row) {
      this.dialogvisible = true;
      this.title = "编辑";
      this.form.id = row.id;
      this.form.name = row.name;
      this.form.brand = row.brand;
      this.form.state = row.state;
      this.form.cost = row.cost;
      this.form.color = row.color;
      this.form.image = row.image;
      this.form.imageDetail = row.imageDetail;
      this.form.sales = row.sales;
      this.imageUrl = baseURL.baseUrl + '/' + row.image;
      this.imageDetailUrl = baseURL.baseUrl + '/' + row.imageDetail;
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
          const res = await deleteGoods(params);
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
            const res = await updateGoods(this.form);
            if (res.code === 200) {
              this.$message.success("操作成功");
              this.btnLoading = false;
              this.close();
              this.getList();
            } else {
              this.$message.error(res.msg);
              this.btnLoading = false;
            }
          } else {
            const res = await addGoods(this.form);
            if (res.code === 200) {
              this.$message.success("操作成功");
              this.btnLoading = false;
              this.close();
              this.getList();
            } else {
               this.$message.error(res.msg);
               this.btnLoading = false;
            }
          }
        } else {
          this.btnLoading = false;
        }
      });
    },
    // 自定义上传商品图片
    async handleUploadImage(options) {
      const formData = new FormData();
      formData.append('file', options.file);
      try {
        const res = await uploadFile(formData);
        if (res.code === 200) {
          this.form.image = res.data;
          this.imageUrl = URL.createObjectURL(options.file);
          this.$message.success("图片上传成功");
          // 手动清除验证错误
          this.$refs.form.clearValidate('image');
        } else {
          this.$message.error(res.msg || "图片上传失败");
        }
      } catch (error) {
        this.$message.error("图片上传失败");
      }
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt5M = file.size / 1024 / 1024 < 5;
      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
      }
      if (!isLt5M) {
        this.$message.error("上传图片大小不能超过 5MB!");
      }
      return isJPG && isLt5M;
    },
    // 自定义上传商品详情图片
    async handleUploadImageDetail(options) {
      const formData = new FormData();
      formData.append('file', options.file);
      try {
        const res = await uploadFile(formData);
        if (res.code === 200) {
          this.form.imageDetail = res.data;
          this.imageDetailUrl = URL.createObjectURL(options.file);
          this.$message.success("图片上传成功");
          // 手动清除验证错误
          this.$refs.form.clearValidate('imageDetail');
        } else {
          this.$message.error(res.msg || "图片上传失败");
        }
      } catch (error) {
        this.$message.error("图片上传失败");
      }
    },
    beforeAvatarUpload1(file) {
      const isJPG = file.type === "image/jpeg" || file.type === "image/png";
      const isLt5M = file.size / 1024 / 1024 < 5;
      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
      }
      if (!isLt5M) {
        this.$message.error("上传图片大小不能超过 5MB!");
      }
      return isJPG && isLt5M;
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
.avatar-uploader {
  width: 178px;
  height: 178px;
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>