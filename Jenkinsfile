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
                        sh 'pwd'
                        sh 'cd target && nohup java -jar coin.jar'
                    }
         }
    }

}