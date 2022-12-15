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
                  codescene analyzeBranchDiff: true, baseRevision: 'origin/master', credentialsId: 'jenkins-bot', deltaAnalysisUrl: 'http://20.100.172.84:3003/projects/2/delta-analysis', failOnDecliningCodeHealth: true, failOnFailedGoal: true, originUrl: '', repository: 'spring-cxf'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying.....'
            }
        }
    }
}
