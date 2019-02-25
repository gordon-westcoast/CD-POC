
pipeline {
    agent {
        docker {
            image 'ceddy4395/windows-java'
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
