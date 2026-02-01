pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build and deploy'){
            steps {
                script {
                sh """
                   docker compose -f docker-compose.yml build ktor-app
                   docker compose -f docker-compose.yml up -d
                """
               }
            }
        }
    }
}