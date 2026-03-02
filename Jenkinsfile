pipeline {
    agent any

    environment {
        IMAGE_NAME = "user-registration-app"
        CONTAINER_NAME = "user-registration-container"
    }

    stages {

        stage('Checkout Code') {
           steps {
             git branch: 'main',
             url: 'https://github.com/Mateen-Aws2025/user-registration-cicd.git'
           }
      }
        stage('Build with Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Build Docker Image') {
            steps {
                 sh '''
                docker build -t mateensayyed/user-registration-app:$BUILD_NUMBER .
           '''
       }
    }
     stage('Push to DockerHub') {
        steps {
            withCredentials([usernamePassword(
            credentialsId: 'dockerhub-creds',
            usernameVariable: 'DOCKER_USER',
            passwordVariable: 'DOCKER_PASS'
        )]) {
            sh '''
            echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
            docker push mateensayyed/user-registration-app:$BUILD_NUMBER
            '''
         }
      }
    }
        
    stage('Deploy Container') {
         steps {
            sh '''
            docker stop user-registration-container || true
            docker rm user-registration-container || true
            docker run -d -p 8081:8080 \
            --name user-registration-container \
            mateensayyed/user-registration-app:$BUILD_NUMBER
         '''
        }
      }
    stage('Cleanup Old Images') {
         steps {
           sh '''
           docker images user-registration-app --format "{{.Tag}}" | \
           sort -nr | tail -n +6 | \
           xargs -I {} docker rmi mateensayyed/user-registration-app:{} || true
        '''
    }
   }    
  }
 }
