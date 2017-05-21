def scmUrl = 'https://github.com/prayagupd/eccount-rest.git'
def branch = "REST-API-load-balancing"

job('urayagppd-build') {
    scm {
        git(scmUrl, branch)
    }
    triggers {
        scm('H/15 * * * *')
    }
    steps {
        maven('-e clean test')
    }
}
