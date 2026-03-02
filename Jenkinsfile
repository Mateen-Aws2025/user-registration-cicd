pipeline {
    agent any

    parameters {
        string(
            name: 'ROLLBACK_VERSION',
            defaultValue: '',
            description: 'Enter image version to rollback (leave empty for normal deploy)'
        )
    }

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
         script {
            def version = params.ROLLBACK_VERSION?.trim()

            if (version) {
                echo "Rolling back to version: ${version}"

                sh """
                docker stop user-registration-container || true
                docker rm user-registration-container || true
                docker pull mateensayyed/user-registration-app:${version}
                docker run -d -p 8081:8080 \
                --name user-registration-container \
                mateensayyed/user-registration-app:${version}
                """
            } else {
                echo "Deploying latest build: ${BUILD_NUMBER}"

                sh """
                docker stop user-registration-container || true
                docker rm user-registration-container || true
                docker run -d -p 8081:8080 \
                --name user-registration-container \
                mateensayyed/user-registration-app:${BUILD_NUMBER}
                """
            }
        }
    }
 }
    stage('Cleanup Old Images') {
         steps {
           sh '''
           docker images mateensayyed/user-registration-app --format "{{.Tag}}" | \
           sort -nr | tail -n +6 | \
           xargs -I {} docker rmi mateensayyed/user-registration-app:{} || true
        '''
    }
   }    
  }
 }
