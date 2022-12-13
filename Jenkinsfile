pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('codescene') {
            steps {
                  codescene analyzeBranchDiff: true, baseRevision: 'origin/master', credentialsId: 'jenkins-bot', deltaAnalysisUrl: 'http://172.17.0.3:3003/projects/1/delta-analysis', failOnDecliningCodeHealth: true, failOnFailedGoal: true, originUrl: '', repository: 'spring-cxf'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}