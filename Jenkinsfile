
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
