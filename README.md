# 商城项目 (Shop Project)

本项目是一个基于 Spring Boot 和 Vue 的简单商城系统。以下指南将帮助您在一个全新的 Windows 环境中从零开始配置并运行本项目。

## 一、 环境准备

在开始之前，请确保您的电脑已经安装以下软件。如果没有，请点击链接下载并安装。

1.  **Git**: [下载 Git for Windows](https://git-scm.com/download/win)
2.  **JDK 17**: [下载 Oracle JDK 17](https://www.oracle.com/java/technologies/downloads/#java17) (或者 OpenJDK 17)
    *   安装后请配置环境变量 `JAVA_HOME` 指向安装目录，并将 `%JAVA_HOME%\bin` 添加到 Path。
3.  **MySQL 8.0**: [下载 MySQL Installer](https://dev.mysql.com/downloads/installer/)
    *   安装时请记住设置的 **root** 用户密码 (本项目默认为 `123456`)。
4.  **Node.js (LTS 版本)**: [下载 Node.js](https://nodejs.org/) (建议版本 v16 或 v18)

---

## 二、 获取代码

打开命令提示符 (CMD) 或 PowerShell，执行以下命令克隆仓库：

```bash
git clone https://github.com/TianZaiShuiZhong/work_test.git
cd work_test
```

---

## 三、 数据库配置

本项目需要 MySQL 数据库支持。请按照以下步骤初始化数据库。

1.  找到数据库脚本文件：
    在项目根目录下找到 SQL 文件。假设位于 `shop-master/商城项目运行资料/shop-20241206.sql` (如果不在请全项目搜索 .sql 文件)。

2.  打开 MySQL 命令行或图形化工具 (如 Navicat, DBeaver, Workbench)。

3.  执行以下 SQL 语句（请逐行执行或运行整个脚本）：

    **第一步：创建数据库**
    ```sql
    -- 创建名为 shop 的数据库
    CREATE DATABASE IF NOT EXISTS shop DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
    
    -- 使用该数据库
    USE shop;
    ```

    **第二步：导入数据**
    
    *   **方式 A (命令行)**:
        ```bash
        mysql -u root -p shop < "完整路径\shop-20241206.sql"
        ```
        (输入密码后回车)
    
    *   **方式 B (图形工具)**:
        选中 `shop` 数据库 -> 右键 "运行 SQL 文件" -> 选择项目中的 `shop-20241206.sql` 文件导入。

4.  **修改数据库连接配置** (如果您的密码不是 `123456`)：
    打开 `src/main/resources/application.yml` 文件：
    ```yaml
    spring:
      datasource:
        url: jdbc:mysql://localhost:3306/shop?useUnicode=true...
        username: root
        password: 123456  <-- 如果您的密码不同，请在这里修改
    ```

---

## 四、 后端启动 (Spring Boot)

1.  回到项目根目录 (包含 `mvnw.cmd` 的目录)。

2.  在终端执行以下命令：

    ```powershell
    # 清理并跳过测试运行
    .\mvnw.cmd clean spring-boot:run -DskipTests
    ```
    *注意：第一次运行需要下载 Maven 依赖，可能需要几分钟，请耐心等待。*

3.  当看到类似以下的日志时，表示后端启动成功：
    ```
    Tomcat started on port(s): 8080 (http) with context path ''
    Started ShopApplication in ... seconds
    ```
    后端服务地址：http://localhost:8080

---

## 五、 前端启动 (Vue)

1.  打开一个新的终端窗口。

2.  进入前端目录：
    ```powershell
    cd vue-shopping
    ```
    *(注：如果你使用的是项目内置的 node 环境，需要先配环境变量。如果已安装全局 Node.js，则直接继续)*

3.  安装依赖：
    ```bash
    npm install
    # 或者使用淘宝镜像 (如果网速慢)
    npm install --registry=https://registry.npmmirror.com
    ```

4.  启动开发服务器：
    ```bash
    npm run serve
    ```

5.  启动成功后，终端会显示访问地址，通常为：
    *   Local:   http://localhost:8081/

---

## 六、 访问项目

打开浏览器访问：

*   **前台商城首页**: [http://localhost:8081/shopping](http://localhost:8081/shopping)
*   **后台管理系统**: [http://localhost:8081/admin](http://localhost:8081/admin)
    *   默认管理员账号：`admin`
    *   默认密码：(请查看数据库 `shop_user` 表或尝试 `123456`)

## 七、 文件上传说明

*   上传的图片文件默认存储在项目根目录下的 `file/` 文件夹中。
*   如果更换设备运行，建议通过 Git 提交该文件夹或手动备份，否则图片资源会丢失。
