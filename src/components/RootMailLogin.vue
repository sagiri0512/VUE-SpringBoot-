<template>
  <div class="container">
    <div class="mail-login-container">
      <h3 style="text-align: center">使用邮箱登录</h3>
      <div class="input-container">
        <input
          type="text"
          placeholder="请输入邮箱"
          v-model.lazy="userMail"
          class="input-field"
        />
      </div>
      <div class="input-container">
        <input
          type="text"
          placeholder="请输入邮箱验证码"
          v-model.lazy="mailCode"
          class="input-field"
        />
        <button
          @click="getMailCode"
          :disabled="isSendingCode"
          class="code-button"
        >
          {{ isSendingCode ? `${remainingTime}s` : "发送验证码" }}
        </button>
      </div>
      <router-link to="RootLogin" style="float: right; height: 30px"
        >使用账号密码登录</router-link
      >
      <button @click="mailLogin" class="login-button">登录</button>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      userMail: "", // 用户邮箱
      mailCode: "", // 输入框内的邮箱验证码
      isSendingCode: false, // 2分钟内不能重复发送验证码
      remainingTime: 0, // 剩余时间
      timer: null, // 定时器
    };
  },
  methods: {
    async getMailCode() {
      if (this.isSendingCode) return;
      try {
        const rootMail = {
          mail: this.userMail,
        };
        const resp = await axios.post("/api/rootMailTo", rootMail); //获取邮箱验证码
        if (resp.data.code == 0) {
          this.isSendingCode = true;
          this.remainingTime = 300; // 设置初始剩余时间为 120 秒
          this.timer = setInterval(() => {
            if (this.remainingTime > 0) {
              this.remainingTime--;
            } else {
              clearInterval(this.timer);
              this.isSendingCode = false;
            }
          }, 1000); // 每秒更新剩余时间
        } else {
          this.$message({
            type: "error",
            message: resp.data.message,
          });
          if (resp.data.message == "重复发送") {
            this.isSendingCode = true;
            this.remainingTime = 300; // 设置初始剩余时间为 300 秒
            this.timer = setInterval(() => {
              if (this.remainingTime > 0) {
                this.remainingTime--;
              } else {
                clearInterval(this.timer);
                this.isSendingCode = false;
              }
            }, 1000); // 每秒更新剩余时间
          }
        }
      } catch (error) {
        console.error("发送验证码失败", error);
      }
    },
    async mailLogin() {
      const rootUpdateMail = {
        //传输登录数据
        id: 0,
        mail: this.userMail,
        code: this.mailCode,
      };
      try {
        const resp = await axios.post("/api/verifyRootMail", rootUpdateMail);
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
        }
      } catch (error) {
        console.error("登录失败", error);
      }
    },
  },
  beforeUnmount() {
    clearInterval(this.timer); // 组件销毁前清除定时器
  },
};
</script>

<style scoped>
.container {
  background-image: url("/public/img/back.jpg"); /* 替换为你的背景图片链接 */
  background-size: cover;
  background-position: center;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 97.8vh;
  /* 让容器铺满整个视口高度 */
}

.mail-login-container {
  border: 1px solid #ccc;
  border-radius: 8px;
  background-color: rgba(255, 255, 255, 0.8); /* 添加透明度 */
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  width: 400px;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
}

.input-container {
  margin-bottom: 10px;
  display: flex;
  align-items: center;
}

.input-container input[type="text"] {
  flex: 1;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
  width: 100%;
  height: 20px;
  /* 设置输入框的高度 */
}

.code-button {
  margin-left: 10px;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  height: 40px;
  /* 设置按钮的高度 */
  width: 86.65px;
}

.code-button:disabled {
  background-color: #ccc;
  cursor: not-allowed;
}

.code-button:hover {
  background-color: #0056b3;
}

.login-button {
  display: block;
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  transition: background-color 0.3s;
  margin-top: 8px;
}

.login-button:hover {
  background-color: #0056b3;
}
</style>