
pipeline {
    agent {
        docker {
            image 'microsoft/nanoserver'
            args '-v C:/Users/automation.test1/.m2:C:/Users/automation.test1/.m2'
        }
    }

    stages {
        stage('Build') {
            steps {
                sh 'mvn -B -DskipTests clean package'
            }
        }
    }
}
