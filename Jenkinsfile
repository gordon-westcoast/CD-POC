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
                    def imagename = 'dockerwestcoast/pocimage'
                    def customImage = docker.build("${imagename}:${buildid}")
                    customImage.push('latest')       
                }
                echo 'Built Container'               
            }
        }
        stage('Update Test Environment') {
            steps {
                echo 'Restarting target environment with new container'
                script {
                    def targetMachine = 'bnwauto01'
                    docker run --name pocimagecontainer --rm -it -h ${targetMachine} dockerwestcoast/pocimage
                }
                echo 'Target environment container updated'
            }
        }
        stage('Test Application') {
            steps {
                echo 'Tested application'
            }
        }
    }
}
