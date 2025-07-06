def githubRepo = 'https://github.com/mottioh/home-assignment.git'
def credentialsId = 'github-creds'

pipelineJob('Build-Tag-Push') {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(githubRepo)
                        credentials(credentialsId)
                    }
                    branches('*/main')
                }
            }
            scriptPath('home-assignment/pipelines/btp.groovy')
        }
    }
    description('Builds Tag and push reg list Docker to Docker Hub')
}

pipelineJob('02-Build-Nginx-Proxy') {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(githubRepo)
                        credentials(credentialsId)
                    }
                    branches('*/main')
                }
            }
            scriptPath('jenkins/pipelines/nginx-proxy.groovy')
        }
    }
    description('Builds Nginx reverse proxy Docker image and pushes it to DockerHub')
}

pipelineJob('03-Run-and-Test-Containers') {
    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(githubRepo)
                        credentials(credentialsId)
                    }
                    branches('*/main')
                }
            }
            scriptPath('jenkins/pipelines/test-runner.groovy')
        }
    }
    description('Runs Flask and Nginx containers locally and performs test via localhost')
}
