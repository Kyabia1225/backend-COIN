# Coin hub小组软工III部署文档

## 写在前面的话

<u>这份文档只有当你需要在自己的机子上跑前端后端时才有需要。</u>

## 一、更新记录

| 日期       | 成员   | 日志                         |
| ---------- | ------ | ---------------------------- |
| 2021/06/19 | 朱伟   | 第一次创建文档并记录部署过程 |
| 2021/06/19 | 徐翊亮 | 更新了一些部署信息           |

## 二、部署环境

| #    | 所需环境     | 注意点                                       |
| ---- | ------------ | -------------------------------------------- |
| 1    | JAVA         | JAVA8                                        |
| 2    | SPARK        | 请注意与Hadoop不要产生版本冲突，详见官方文档 |
| 3    | VUE_CLI      |                                              |
| 4    | npm/cnpm     |                                              |
| 5    | MAVEN        | 本项目使用的maven3.8.1                       |
| 6    | Intellj IDEA |                                              |

#### 所需额外文件

##### 自定义字典+训练集

链接：https://pan.baidu.com/s/1NDrxhue9ri2suSBNmKTebg 
提取码：1234 
复制这段内容后打开百度网盘手机App，操作更方便哦

##### HanLP数据集

###### 1、下载jar

 [hanlp.jar](http://nlp.hankcs.com/download.php?file=jar)

本项目因为要使用用户自定义词典所以选择了引入外部jar包，因此在package的时候需要注意（pom.xml中有说明）

###### 2、下载data 

**必要**

|                          数据包                          |               功能 |                          体积（MB）                          |
| :------------------------------------------------------: | -----------------: | :----------------------------------------------------------: |
| [data.zip](http://nlp.hankcs.com/download.php?file=data) | 全部词典，全部模型 | 280（注：分词词典大约40MB，主要是句法分析模型占体积，可以自行删除。） |



## 三、部署过程

### 3.1 后端

#### 3.1.1 关于数据源

代码中数据源已经连接至服务器的MongoDB，不需要再次录入数据。否则录入过程可能需要50分钟左右

#### 3.1.2 需要修改的文件

请将百度云链接中下载的dictionary文件夹中的csv文件全部放入**hanlp字典**的**data/dictionary/custom**文件夹中。



**resources/application.properties**中的**rootDirPath=...**需要修改为：

**上述百度云链接中下载的数据的data文件夹路径**



**hanlp.properties**中的**root=...**需要修改为:

**下载的hanlp字典路径**



**IDEA Project Structure**-->Modules-->Dependencies-->**导入hanlp-1.8.1.jar**

hanlp-1.8.1.jar在src/main/java/com/example/coin/lib中



#### 3.1.3 启动

* 使用源码直接通过IDEA 启动CoinApplication
* mvn clean package 后java -jar coin.jar也可以启动。如果使用的是本项目的pom.xml, 外部引入的jar包应该包含在jar包中BOOT-INF/lib目录下。如果报错Spring无法启动上下文，请仔细检查本项

### 3.2 前端启动

修改src/utils/requests中的baseURL为http://localhost:8088

npm install (最好cnpm换源)

npm run dev

前端地址http://localhost:8081

