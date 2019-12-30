def nameByBranch
def nameByCommit

stage('Calculate git-related vars') {
    // http://stackoverflow.com/questions/35554983/git-variables-in-jenkins-workflow-plugin
    // https://issues.jenkins-ci.org/browse/JENKINS-35230
    def gitCommit = sh(returnStdout: true, script: 'git rev-parse HEAD').trim()

    nameByBranch = "mesosphere/vault:${env.BRANCH_NAME}"
    nameByCommit = "mesosphere/vault:${gitCommit}"

    // Some debugging:
    sh 'env | sort '
    echo "Name of the container to be published, by branch: ${nameByBranch}"
    echo "Name of the container to be published, by commit: ${nameByCommit}"
}
stage('bootstrap') {
    sh 'make bootstrap'
}

stage('Test') {
    sh 'make test'
}

stage('Test race') {
    sh 'make testrace'
}
