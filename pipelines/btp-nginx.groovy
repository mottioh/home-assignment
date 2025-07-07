pipeline {
    agent any
    environment {
        IMAGE_NAME = 'mottioh/nginx-proxy'
    }
    stages {
        stage('Build and Push Nginx Proxy Image') {
            steps {
                script {
                    def imageTag = "${IMAGE_NAME}"
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'USER',
                        passwordVariable: 'PASSWORD'
                    )]) {
                        sh "docker login -u $USER -p $PASSWORD"
                        sh "docker build -t ${imageTag} nginx/"
                        sh "docker push ${imageTag}"
                    }
                }
            }
        }
    }
}
