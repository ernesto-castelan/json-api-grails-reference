function() {
    var port = karate.properties['karate.server.port'];
    if (!port) port = 8080;
    var config = { serverUrl:'http://127.0.0.1:' + port };
    return config;
}
