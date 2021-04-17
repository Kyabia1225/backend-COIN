pipeline {
    agent any
    options {
            timestamps()    //设置在项目打印日志时带上对应时间
            disableConcurrentBuilds()   //不允许同时执行流水线，被用来防止同时访问共享资源等
            timeout(time: 10, unit: 'MINUTES')   // 设置流水线运行超过n分钟，Jenkins将中止流水线
    }

    stages{
         stage('Maven Build and Test') {
                    steps{
                        echo 'Test And Build'
                        sh 'mvn clean package -Dmaven.test.failure.ignore=true'
                        //之后做jacoco测试
                    }
         }

         stage('Deploy'){
                    steps{
                        echo 'COIN Deploy'
                        sh 'pwd'
                        sh 'cd target && nohup java -jar coin.jar'
                    }
         }
    }

}