<template>
  <!-- 审核文章 -->
  <el-main>
    <el-table v-loading="loading" :data="AuditArticle">
      <el-table-column prop="articleId" label="文章ID" width="180"> </el-table-column>
      <el-table-column prop="title" label="文章标题"> </el-table-column>
      <el-table-column prop="cause" label="举报原因"> </el-table-column>
      <el-table-column label="操作">
        <template v-slot="scope">
          <el-button type="text" @click="open(scope.row.id, 'delete')"
            >文章详细页</el-button
          >
          <el-button type="text" @click="openUpdateState(scope.row.id, -1)"
            >驳回</el-button
          >
          <el-button type="text" @click="openUpdateState(scope.row.id, 1)"
            >通过</el-button
          >
        </template>
      </el-table-column>
    </el-table>
  </el-main>
  <!-- 审核文章END -->
</template>

<script>
import axios from "axios";
import {mapGetters } from 'vuex';
export default {
  data() {
    return {
      loading:true,//是否在加载数据
      AuditArticle:"",//待审核文章
    };
  },
  computed: {
    ...mapGetters(['getRoot']), // 将 Vuex 中的 getter 映射到组件的计算属性中
  },
  methods:{
    //文章审核通过和驳回提示框
    openUpdateState(id, state) {
      this.$confirm(
        state === 1
          ? "此操作将使该文章冻结, 是否继续?"
          : "此操作会将该举报驳回, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(async () => {
          const code = await this.UpdateState(id, state)
          if (code == 0) {
            this.$message({
              type: "success",
              message: state === 1 ? "通过成功!" : "驳回成功!",
            });
            this.getReportArticle();
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
    //修改被举报文章状态
    async UpdateState(id, state1){
      const state = {
        id : id,
        state : state1
      }
      const resp = await axios.post("/api/updateReportState", state);
      return resp.data.code;
    },
    //获取我的全部待管理被举报文章
    async getReportArticle(){
      this.loading = true;
      const Id = {
        id : this.getRoot,
      }
      const resp = await axios.post("/api/getReportArticle", Id);
      this.AuditArticle = resp.data;
      this.loading = false;
    }
  },
  mounted() {
    this.getReportArticle();
  }
};
</script>

<style scoped>
.el-header {
  background-color: #b3c0d1;
  color: #333;
  line-height: 60px;
}

.el-aside {
  color: #333;
}
</style>