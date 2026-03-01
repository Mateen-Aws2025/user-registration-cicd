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
                sh 'docker build -t $IMAGE_NAME .'
            }
        }

        stage('Deploy Container') {
            steps {
                sh '''
                docker stop $CONTAINER_NAME || true
                docker rm $CONTAINER_NAME || true
                docker run -d -p 8080:8080 --name $CONTAINER_NAME $IMAGE_NAME
                '''
            }
        }
    }
}
