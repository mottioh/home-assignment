pipeline {
    agent any
    environment {
        IMAGE_NAME = 'mottioh/docker-list'
    }
    stages {
        stage('Build Tag and Push') {
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
                        sh "docker build -t ${imageTag} ./reg-list"
                        sh "docker push ${imageTag}"
                    }
                }
            }
        }
    }
}