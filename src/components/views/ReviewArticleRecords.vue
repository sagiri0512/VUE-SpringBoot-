<template>
  <!-- 审核文章 -->
  <el-main>
    <el-table v-loading="loading" :data="AuditArticle">
      <el-table-column prop="articleId" label="文章ID" width="180"> </el-table-column>
      <el-table-column prop="title" label="文章标题"> </el-table-column>
      <el-table-column label="操作结果">
        <template v-slot="scope">
          {{ scope.row.state === 1 ? "已通过" : "已驳回" }}
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
    //获取我的全部待审核文章
    async getAuditArticle(){
      this.loading = true;
      const Id = {
        id : this.getRoot,
      }
      const resp = await axios.post("/api/Reviewed", Id);
      this.AuditArticle = resp.data;
      this.loading = false;
    }
  },
  mounted() {
    this.getAuditArticle();
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