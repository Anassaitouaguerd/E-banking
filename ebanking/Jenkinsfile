pipeline {
    agent any

    environment {
        MAVEN_HOME = tool 'Maven'
    }

    stages {
        stage('Checkout Code') {
            steps {
                git url: 'https://github.com/Anassaitouaguerd/E-banking.git', branch: 'main'
            }
        }
        stage('Build') {
            steps {
                sh 'cd ebanking && ${MAVEN_HOME}/bin/mvn clean package'
            }
        }
        stage('Test') {
            steps {
                sh 'cd ebanking && ${MAVEN_HOME}/bin/mvn test'
            }
        }
        stage('Package') {
            steps {
                echo "Artifact generated: target/*.jar"
            }
        }
        stage('Deploy') {
            steps {
                echo "Deploying the application..."
                // Add deployment steps, e.g., Docker, Kubernetes, or direct server deployment
            }
        }
    }

    post {
        success {
            echo 'Pipeline succeeded!'
        }
        failure {
            echo 'Pipeline failed!'
        }
    }
}
