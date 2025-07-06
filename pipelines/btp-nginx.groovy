pipeline {
    agent any
    environment {
        IMAGE_NAME = 'mottioh/nginx-proxy'
    }
    stages {
        stage('Build and Push Nginx Proxy Image') {
            steps {
                script {
                    // Get last 4 characters of Git commit hash, it will be out image tag ver
                    def commit = sh(script: "git rev-parse --short=4 HEAD", returnStdout: true).trim()
                    def imageTag = "${IMAGE_NAME}:${commit}"
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
