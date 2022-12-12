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
                  codescene analyzeBranchDiff: true, baseRevision: 'origin/master', credentialsId: 'jenkins-bot', deltaAnalysisUrl: 'http://172.17.0.2:3003/projects/1/delta-analysis', failOnDecliningCodeHealth: false, failOnFailedGoal: false, originUrl: 'https://github.com/mjhaugsdal/spring-cxf.git', repository: 'spring-cxf'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}