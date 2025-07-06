def githubRepo = 'git@github.com:mottioh/home-assignment.git'
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

