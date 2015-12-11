import grails.util.*

description( "Creates an Angular domain" ) {
    usage "grails create-ng-domain [NAME]"
    argument name:'Domain Name', description:"The name of the Angular domain to create", required:true
    flag name:'force', description:"Whether to overwrite existing files"
}

def model = model(args[0])
boolean overwrite = flag('force')

final String modulePath = model.packagePath ?: model.propertyName
final String moduleName = model.packageName ?: model.propertyName

final String basePath = "grails-app/assets/javascripts/${modulePath}"
if (!file("${basePath}/${moduleName}.js").exists()) {
    createNgModule(moduleName)
}

render template: template("tests/NgDomainSpec.js"),
        destination: file("src/test/assets/${modulePath}/domain/${model.className}Spec.js"),
        model: [moduleName: moduleName,
                className: model.className]
        overwrite: overwrite


render template: template("NgDomain.js"),
       destination: file("${basePath}/domain/${model.className}.js"),
       model: [moduleName: moduleName,
               propertyName: model.propertyName,
               className: model.className]
       overwrite: overwrite