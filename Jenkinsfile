pipeline {
    agent any

    stages{
         stage('Maven Build and Test') {
                    steps{
                        echo 'Test And Build'
                        sh 'mvn clean package -Dmaven.test.failure.ignore=true'
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