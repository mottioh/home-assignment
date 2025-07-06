def githubRepo = 'git@github.com:mottioh/home-assignment.git'
def credentialsId = 'github-creds'

pipelineJob('Build-Tag-Push-Flask') {
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
            scriptPath('pipelines/btp-flask.groovy')
        }
    }
    description('Builds Tag and push reg list Docker to Docker Hub')
}
pipelineJob('Build-Tag-Push-Nginx') {
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
            scriptPath('pipelines/btp-nginx.groovy')
        }
    }
    description('Builds Tag and pushes a Nginx reverse proxy with source IP header to Docker Hub.')
}