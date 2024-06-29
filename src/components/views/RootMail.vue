<template>
  <!-- 绑定邮箱 -->
  <div v-if="this.rootMail === null">
    <div class="container">
      <div class="mail-login-container">
        <h3 style="text-align: center">绑定邮箱</h3>
        <div class="input-container">
          <input
            type="text"
            placeholder="请输入邮箱"
            v-model.lazy="newRootMail"
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
        <button @click="rootUpdateMail" class="login-button">确认</button>
      </div>
    </div>
  </div>
  <!-- 绑定邮箱END -->
  <!-- 验证邮箱 -->
  <div v-else>
    <div class="container">
      <div class="mail-login-container">
        <h3 style="text-align: center">修改邮箱前需验证邮箱</h3>
        <div class="input-container">
          <input
            type="text"
            placeholder="请输入邮箱"
            v-model.lazy="oldRootMail"
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
        <button @click="verifyRootMail" class="login-button">确认</button>
      </div>
    </div>
  </div>
  <!-- 验证邮箱END -->
</template>

<script>
import axios from "axios";
import { mapGetters } from "vuex";

export default {
  data() {
    return {
      rootMail: "", //管理员和超级管理员绑定的邮箱邮箱
      newRootMail: "", //新邮箱
      oldRootMail: "", //用户输入的老邮箱
      mailCode: "", //验证码
      isSendingCode: false, // 2分钟内不能重复发送验证码
      remainingTime: 0, // 剩余时间
      timer: null, // 定时器
    };
  },
  computed: {
    ...mapGetters(["getRoot"]), // 将 Vuex 中的 getter 映射到组件的计算属性中
  },
  mounted() {
    this.getRootMail();
  },
  methods: {
    async getMailCode() {
      if (this.rootMail != null) {
        if (this.rootMail != this.oldRootMail) {
          this.$message({
            type: "error",
            message: "输入的邮箱非绑定邮箱",
          });
          return;
        }
      }
      //获取邮箱验证码
      if (this.isSendingCode) return;
      try {
        const rootMail = {
          mail: this.rootMail == null ? this.newRootMail : this.rootMail,
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
    async rootUpdateMail() {
      //修改邮箱
      let rootId = "";
      if (this.getRoot !== "") {
        rootId = this.getRoot;
      } else {
        rootId = 1;
      } //判断是否是超级管理员
      const newMail = {
        id: rootId,
        mail: this.newRootMail,
        code: this.mailCode,
      };
      console.log(newMail.mail);
      const resp = await axios.post("/api/rootUpdateMail", newMail);
      if (resp.data.code == 0) {
        this.isSendingCode = false;
        this.rootMail = this.newRootMail;
        this.newRootMail = "";
        this.mailCode = "";
        this.$message({
          type: "success",
          message: "修改成功",
        });
      } else {
        this.$message({
          type: "error",
          message: resp.data.message,
        });
      }
    },
    async verifyRootMail() {
      //验证邮箱
      if (this.rootMail != null) {
        if (this.rootMail != this.oldRootMail) {
          this.$message({
          type: "error",
          message: "输入的邮箱非绑定邮箱",
        });
        }
      }

      const rootUpdateMail = {
        id: 0,
        mail: this.oldRootMail,
        code: this.mailCode,
      };
      const resp = await axios.post("/api/verifyRootMail", rootUpdateMail);
      if (resp.data.code == 0) {
        this.isSendingCode = false;
        this.rootMail = null;
        this.oldRootMail = "";
        this.mailCode = "";
        this.$message({
          type: "success",
          message: "验证成功",
        });
      } else {
        this.$message({
          type: "error",
          message: resp.data.message,
        });
      }
    },
    beforeUnmount() {
      clearInterval(this.timer); // 组件销毁前清除定时器
    },
    async getRootMail() {
      //获取管理员绑定的邮箱
      let rootId = "";
      if (this.getRoot == "") {
        rootId = 1;
      } else {
        rootId = this.getRoot;
      }
      const Id = {
        id: rootId,
      };
      const resp = await axios.post("/api/getRootMail", Id);
      if (resp.data.code === 0) {
        this.rootMail = resp.data.data;
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
.container {
  display: flex;
  justify-content: center;
  align-items: center;
  /* 让容器铺满整个视口高度 */
}

.mail-login-container {
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
  margin-top: 5px;
}

.login-button:hover {
  background-color: #0056b3;
}
</style>
