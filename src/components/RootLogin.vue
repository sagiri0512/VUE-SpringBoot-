<template>
  <div class="background">
    <div class="login-container">
      <h2>管理员登录</h2>
      <div class="form-group">
        <label for="username">账号：</label>
        <input type="text" id="username" v-model.lazy="username" />
      </div>
      <div class="form-group">
        <label for="password">密码：</label>
        <input type="password" id="password" v-model.lazy="password" />
      </div>
      <router-link to="RootMailLogin" style="float: right;">使用邮箱登录</router-link>
      <button class="login-button" @click="login">登录</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";
export default {
  data() {
    return {
      username: "",
      password: "",
    };
  },
  methods: {
    async login() {
      const userInfo = {
        username: this.username,
        password: this.password,
      };
      const resp = await axios.post("/api/rootLogin", userInfo);
      if (resp.data.code === 0) {
        if (resp.data.data === 1) {
          this.$store.commit("updateSuperRoot", 1);
          this.$store.commit("updateRoot", "");
          this.$store.commit("updateUserSetter", "");
          await this.$router.push("/SuperPage");
        } else {
          this.$store.commit("updateSuperRoot", "");
          this.$store.commit("updateRoot", resp.data.data);
          this.$store.commit("updateUserSetter", "");
          await this.$router.push("/RootPage");
        }
      } else {
        this.$message({
          message: resp.data.message,
        });
      }
    },
  },
};
</script>

<style scoped>
.background {
  background-image: url("/public/img/back.jpg"); /* 替换为你的背景图片链接 */
  background-size: cover;
  background-position: center;
  height: 97.8vh;
  display: flex;
  justify-content: center;
  align-items: center;
}

.login-container {
  width: 400px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: rgba(255, 255, 255, 0.8); /* 添加透明度 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
  text-align: center;
  margin-bottom: 20px;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  display: block;
  margin-bottom: 5px;
}

.form-group input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.login-button {
  margin-top: 8px;
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}

.login-button:hover {
  background-color: #0056b3;
}
</style>
