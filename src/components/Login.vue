<template>
  <div class="login_container">
    <div class="login_box">
      <div class="avatar_box">
        <img src="../assets/logo.png" alt />
      </div>
      <el-form
        ref="loginFormRef"
        :rules="loginRules"
        :model="loginForm"
        class="login_form"
        label-width="0"
      >
        <!--model用来绑定数据， class用来绑定样式(外观)-->
        <el-form-item prop="username">
          <el-input
            v-model="loginForm.username"
            prefix-icon="iconfont icon-denglu-copy"
          >
          </el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            prefix-icon="iconfont icon-mima"
            type="password"
          >
          </el-input>
        </el-form-item>
        <el-form-item class="btns">
          <el-button type="primary" @click="login">登录</el-button>
          <el-button type="info" @click="resetLoginForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
export default {
  data() {
    return {
      //表单数据对象
      loginForm: {
        username: "admin",
        password: "123456",
      },
      //验证对象
      loginRules: {
        //校验用户名
        username: [
          { required: true, message: "请输入用户名称", trigger: "blur" }, //必填项验证：是否必填，提示信息，触发方式（失去焦点）
          { min: 3, max: 12, message: "长度在 3 ~ 12 个字符", trigger: "blur" }, //长度验证
        ],
        //校验密码
        password: [
          { required: true, message: "请输入用户密码", trigger: "blur" }, //必填项验证：是否必填，提示信息，触发方式（失去焦点）
          { min: 6, max: 10, message: "长度在 6 ~ 10 个字符", trigger: "blur" }, //长度验证
        ],
      },
    };
  },
  methods: {
    //重置表单内容
    resetLoginForm() {
      this.$refs.loginFormRef.resetFields();
    },
    //登录方法
    login() {
      //验证校验规则
      this.$refs.loginFormRef.validate(async (valid) => {
        if (!valid) return; //验证失败
        const { data: res } = await this.$http.post("login", this.loginForm); //访问后端控制层的login URL，并将用户数据（loginForm）传递给后端进行封装
        if (res.flag == "ok") {
          this.$message.success("操作成功"); //信息提示
          this.$router.push({ path: "/home" }); //页面路由跳转
          window.sessionStorage.setItem("user", res.user); //存储从后端传过来的useruser对象
        } else {
          this.$message.error("操作失败"); //失败提示
        }
      });
    },
  },
};
</script>

<style lang="less" scoped>
//根节点样式
.login_container {
  background-color: #2b4b6b;
  height: 100%;
}
.login_box {
  width: 450px;
  height: 300px;
  background-color: #fff;
  border-radius: 5px;
  position: absolute; //绝对定位，有了绝对定位后才好向左向右移动
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%); //难点
  .avatar_box {
    //头像在登录区内，所以直接写在了login_box里面
    width: 130px;
    height: 130px;
    border: 1px solid #eee; //一个像素单位的边框，线为实线
    border-radius: 50%;
    padding: 5px; //上下左右边距
    box-shadow: 0 0 2px #ddd; //盒子阴影
    position: absolute;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: #0ee;
    img {
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }
}
.btns {
  display: flex; //流式布局
  justify-content: flex-end;
}
.login_form {
  position: absolute;
  bottom: 0%;
  width: 100%;
  padding: 0 10px;
  box-sizing: border-box;
}
</style>

<!-- 写组件的时候要一层一层写，先写最外层，再往里面写
写样式一样，从外往里 -->

<!-- 校验：
1、添加属性
2、绑定它的内容，编写校验规则
3、要写什么属性 -->
