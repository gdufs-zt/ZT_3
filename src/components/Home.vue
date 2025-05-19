<!--http://localhost:8080/#/login-->

<template>
  <!-- 引入container布局 -->
  <el-container class="home-container">
    <!-- 头部 -->
    <el-header>
      <div>
        <img src="../assets/logo_3.png" alt="" />
        <span>个人运动平台</span>
      </div>
      <el-button type="info" @click="logout">安全退出</el-button>
    </el-header>
    <!-- 主体 -->
    <el-container>
      <!-- 侧边栏 -->
      <el-aside width="isCollapse?'64px':'200px'">
        <div class="toggle-button" @click="toggleCollapase">|||</div>
        <el-menu
          background-color="#545c64"
          text-color="#fff"
          active-text-color="#409eff"
          unique-opend
          :collapse="isCollapse"
          :collapse-transition="false"
          :router="true"
          :default-active="activePath"
        >
          <!--一级菜单-->
          <el-submenu
            :index="item.id + ''"
            v-for="item in menuList"
            :key="item.id"
          >
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>{{ item.title }}</span>
            </template>
            <!--二级菜单-->
            <el-menu-item
              :index="it.path"
              v-for="it in item.sList"
              :key="it.id"
              @click="saveNavState(it.path)"
            >
              <template slot="title">
                <i class="el-icon-location"></i>
                <span>{{ it.title }}</span>
              </template></el-menu-item
            >
          </el-submenu>
        </el-menu>
      </el-aside>
      <!-- 主体内容 -->
      <el-main>
        <router-view></router-view>
        <!-- //路由占位符 -->
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      //菜单列表
      menuList: [],
      isCollapse: false, //伸缩
      activePath: "/welcome", //默认路径
    };
  },
  //onload事件，页面一加载就执行，去数据库把侧边栏的菜单找出来
  created() {
    //查询menuList
    this.getMenuList();
    this.activePath = window.sessionStorage.getItem("activePath"); //取出session里的path， 动态修改activePath，刷新页面的时候不会从头开始，而是回到session里的路径（页面记忆功能）
  },
  methods: {
    //安全退出
    logout() {
      window.sessionStorage.clear(); // 清除session
      this.$router.push("/login"); //回到首页
    },
    //获取导航菜单方法
    async getMenuList() {
      //重难点：从后端数据库拿数据
      console.log("getmenulist");
      const { data: res } = await this.$http.get("menus");
      console.log(res);
      if (res.flag != 200) return this.$message.error("获取列表失败"); //访问失败的错误信息
      this.menuList = res.menus; //访问成功的数据回填
    },
    // 控制伸缩
    toggleCollapase() {
      this.isCollapse = !this.isCollapse;
    },
    //保存路径
    saveNavState(activePath) {
      window.sessionStorage.setItem("activePath", activePath); //存放session里面
      this.activePath = activePath;
    },
  },
};
</script>

<style lang="less" scoped>
.home-container {
  //主布局，让内容填充满页面
  height: 100%;
}
.el-header {
  background-color: #373d41;
  display: flex;
  justify-content: space-between; //左右贴边
  padding-left: 0%; //左边界
  align-items: center;
  color: #fff;
  font-size: 20px;
  > div {
    display: flex;
    align-items: center;
    span {
      margin-left: 15px;
    }
  }
}
.el-aside {
  background-color: #333744;
  .el-menu {
    border-right: none;
  }
}
.el-main {
  background-color: #eaedf1;
}
img {
  //只有一张图片可以直接img，如果后面有多张图片冲突的话，可以加上class
  width: 55px;
  height: 55px;
}
//按钮样式
.toggle-button {
  background-color: #4a5064;
  font-size: 10px;
  line-height: 24px;
  color: #fff;
  text-align: center;
  letter-spacing: 0.2em;
  cursor: pointer; //显示小手
}
</style>
