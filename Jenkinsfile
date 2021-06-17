pipeline {
    agent any

    stages{
         stage('Maven Build and Test') {
                    steps{
                        echo 'Test And Build'
                        sh 'mvn clean package jacoco:report -Dmaven.test.failure.ignore=true'
                        jacoco()
                    }
         }

         stage('Deploy'){
                    steps{
                        echo 'COIN Deploy'
                        sh "if(ps -aux | grep target/coin.jar | grep -v grep)then(ps -aux | grep target/coin.jar | grep -v grep | awk {'print \$2'} | xargs kill -9)fi"
                        sh 'pwd'
                        sh 'nohup java -jar target/coin.jar &'
                    }
         }
    }

}