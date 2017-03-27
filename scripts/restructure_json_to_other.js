var input_file = process.argv.slice(2);
var files = require("../" + input_file);

console.log('digraph "shared" { ');

for (var i = 0; i < files.length; i++) {
    var file = files[i].name;
    for (var j = 0; j < files[i].imports.length; j++) {
        var dependency = files[i].imports[j];
        console.log('"' + file + '" -> "' + dependency + '";');
    }
}

console.log("}");