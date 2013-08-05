grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir	= "target/test-reports"

grails.project.dependency.resolution = {
    inherits( "global" ) {
    }
    log "warn" // log level of Ivy resolver, either 'error', 'warn', 'info', 'debug' or 'verbose'
    repositories {
        grailsPlugins()
        grailsHome()
        grailsCentral()
    }
    dependencies {
    }
    plugins {
        runtime(':asynchronous-mail:1.0-RC3') {
            excludes 'hibernate'
        }
        compile ":fields:1.3"
        runtime ":hibernate:$grailsVersion"
        compile ":mail:1.0.1"
        build(":tomcat:$grailsVersion", ":release:2.0.3", ":rest-client-builder:1.0.2") {
            export = false
        }
    }
}