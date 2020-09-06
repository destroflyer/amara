node {
    ansiColor('xterm') {
        try {
            stage('Checkout') {
                checkout scm
            }
            stage('Build') {
                sh 'mkdir workspace'
                sh 'echo "../assets/" > workspace/assets.ini'
                sh 'mvn clean install'
            }
        } finally {
            cleanWs()
        }
    }
}