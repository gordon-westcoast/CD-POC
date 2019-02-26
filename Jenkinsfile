pipeline {
    agent any 

    stages {
        stage('Build Application') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
        stage('Build Application Container') {
            steps {
                echo 'Starting to build docker image'
                script {
                    def dockerfile = 'Dockerfile'
                    def buildid = '1.0'
                    def imagename = 'gmtest'
                    def customImage = docker.build("${imagename}:${buildid}", "-f ${dockerfile} ./dockerfiles")
                    customImage.push('latest')       
                }
                echo 'Built Container'               
            }
        }
        stage('Deploy Application Container to Test Environment') {
            steps {
                echo 'Deployed container'      
            }
        }
        stage('Test Application') {
            steps {
                echo 'Tested application'
            }
        }
    }
}
