pipeline {
    agent any
    tools { 
        maven 'maven' 
        jdk 'jdk8' 
        dockerTool 'docker'
    }
    environment {
  VERSION = "env.BUILD_ID"
}

    stages {
        stage ('Initialize') {
            steps {
                sh '''
                    echo "PATH = ${PATH}"
                    echo "M2_HOME = ${M2_HOME}" 
                    
                ''' 
            }
        }

        stage ('Build') {
            steps {
                echo 'This is a minimal pipeline.'
                sh 'mvn -Dmaven.test.failure.ignore=true install' 
                
            }
        }
      
      stage ('docker image ') {
            steps {
                echo 'This is a docker image  pipeline.'
                sh '''
                docker build -t poc/spring-test:${VERSION} .
                
                '''
                
            }
        }
    }
}
