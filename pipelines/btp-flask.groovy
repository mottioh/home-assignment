pipeline {
    agent any
    environment {
        IMAGE_NAME = 'mottioh/docker-list'
    }
    stages {
        stage('Build Tag and Push Flask') {
            steps {
                script {
                    def imageTag = "${IMAGE_NAME}"
                    withCredentials([usernamePassword(
                        credentialsId: 'dockerhub-creds',
                        usernameVariable: 'USER',
                        passwordVariable: 'PASSWORD'
                    )]) {
                        sh "docker login -u $USER -p $PASSWORD"
                        sh "docker build -t ${imageTag} python/"
                        sh "docker push ${imageTag}"
                    }
                }
            }
        }
    }
}