pipeline {
    agent any
    environment {
        FLASK_IMAGE = 'mottioh/docker-list'
        NGINX_IMAGE = 'mottioh/nginx-proxy'
        NETWORK_NAME = 'isolatedNetwork'
    }
    stages {
        stage('Create Docker isoloated Network') {
            steps {
                sh "docker network create ${NETWORK_NAME} || true"
            }
        }
        stage('Run Python Flask Container') {
            steps {
                script {
                    
                    sh """
                        docker run -d --rm \
                        --network ${NETWORK_NAME} \
                        --name docker-list \
                        ${FLASK_IMAGE}
                    """
                }
            }
        }
        stage('Run Nginx Container') {
            steps {
                script {
                    sh """
                        docker run -d --rm \
                        --network ${NETWORK_NAME} \
                        -p 9090:80 \
                        --name nginx-proxy \
                        ${NGINX_IMAGE}
                    """
                }
            }
        }
        stage('Test Proxy to Flask') {
            steps {
                script {
                    sleep(time: 5, unit: "SECONDS") // Give containers time to start
                    def response = sh (script: "curl -s -o /dev/null -w '%{http_code}' http://127.0.0.1:9090/", returnStdout: true).trim()
                    echo "HTTP Response Code: ${response}"
                    if (response != "200") {
                        error("Request failed! Nginx or Flask not working as expected.")
                    }
                }
            }
        }
    }
}
