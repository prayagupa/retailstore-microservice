def scmUrl = 'https://github.com/prayagupa/retailstore-rest.git'
def branch = "REST-API-load-balancing"

job('retailstore-build') {
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
