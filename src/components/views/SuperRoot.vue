<template>
  <div class="rootDiv">
    <div>
      <el-button type="text" @click="openAdd" class="add">添加管理员</el-button>
      <el-input v-model="input" placeholder="输入姓名可进行搜索"></el-input>
    </div>
    <el-table v-loading="loading" :data="rootInfo" style="width: 100%">
      <el-table-column prop="username" label="姓名" width="180">
      </el-table-column>
      <el-table-column prop="remarks" label="备注"> </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="open(scope.row.id, 'delete')"
            >删除</el-button
          >
          <el-button type="text" @click="open(scope.row.id, 'update')"
            >重置密码</el-button
          >
          <el-button type="text" @click="openUpdateRemarks(scope.row.id)"
            >修改备注</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      rootInfo: null, //管理员信息
      loading: true,//是否显示加载中
      input:""
    };
  },
  watch: {
    input(){
      this.getRootInfo();
    }
  },
  methods: {
    openUpdateUserName(){
      
    },
    openUpdatePassword(){

    },
    //修改备注弹窗
    openUpdateRemarks(id) {
      this.$prompt("请输入备注", "修改备注", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
      })
        .then(async ({ value }) => {
          console.log("备注:", value);
          try {
            const code = await this.updateRemarks(id, value);
            if (code === 0) {
              this.$message({
                type: "success",
                message: "修改成功！ ",
              });
              this.getRootInfo();
            } else {
              this.$message({
                type: "error",
                message: "操作失败！服务器错误！",
              });
            }
          } catch (error) {
            console.error("修改备注时出错:", error);
            this.$message({
              type: "error",
              message: "操作失败！服务器错误！",
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消修改!",
          });
        });
    },
    //添加管理员弹框
    openAdd() {
      this.$prompt("请输入管理员姓名，默认密码为123456", "添加管理员", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPattern: /^[\u4e00-\u9fa5]+$/, // 只允许输入中文字符
        inputErrorMessage: "只能输入中文字符",
      })
        .then(async ({ value }) => {
          console.log("管理员姓名:", value);

          try {
            const code = await this.addRoot(value);
            if (code.code === 0) {
              this.$message({
                type: "success",
                message: "成功添加 " + value + " 为管理员！",
              });
              this.getRootInfo();
            } else {
              this.$message({
                type: "error",
                message: code.message,
              });
            }
          } catch (error) {
            console.error("添加管理员时出错:", error);
            this.$message({
              type: "error",
              message: "操作失败！服务器错误！",
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "取消添加!",
          });
        });
    },

    //删除和重置密码提示框
    open(id, action) {
      this.$confirm(
        action === "delete"
          ? "此操作将永久删除该管理员, 是否继续?"
          : "此操作会将密码重置为123456, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(async () => {
          const code =
            action === "delete"
              ? await this.deleteRoot(id)
              : await this.updatePassWord(id);
          if (code == 0) {
            this.$message({
              type: "success",
              message: action === "delete" ? "删除成功!" : "重置成功!",
            });
            this.getRootInfo();
          } else {
            this.$message({
              type: "error",
              message: "操作失败！服务器错误！",
            });
          }
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消操作",
          });
        });
    },
    //添加管理员
    async addRoot(username) {
      const root = {
        username: username,
      };
      const resp = await axios.post("/api/addRoot", root);
      return resp.data;
    },
    // 获取管理员信息
    async getRootInfo() {
      if(this.input === ""){
        let res = await axios.post("/api/getManage");
        this.loading = false;
        this.rootInfo = res.data;
      }else{
        const name = {
          name: this.input,
        };
        let res = await axios.post("/api/getLikeManage", name);
        this.rootInfo = res.data;
      }
    },
    // 重置密码
    async updatePassWord(id) {
      const Id = {
        id: id,
      };
      const resp = await axios.post("/api/updatePassWord", Id);
      return resp.data.code;
    },
    //删除管理员
    async deleteRoot(id) {
      const Id = {
        id: id,
      };
      const resp = await axios.post("/api/deleteRoot", Id);
      return resp.data.code;
    },
    //修改备注
    async updateRemarks(id, remarks) {
      const root = {
        id: id,
        remarks:remarks,
      };
      const resp = await axios.post("/api/updateRemarks", root);
      return resp.data.code;
    },
  },
  mounted() {
    this.getRootInfo();
  },
};
</script>

<style scoped>
.add{
  font-size: 20px;
}
.rootDiv{
  margin-top: 20px;
  margin-left: 2%;
  width: 96%;
}
</style>
