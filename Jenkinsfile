pipeline {
    agent {
        docker {
            image 'ceddy4395/windows-java'
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
