import * as fs from "fs";

export function getToken(): string {
    fs.readFile('/Users/aborn/.webx.cfg', function (err, data) {
        if (err) {
            return console.error(err);
        }
        //console.log("Asynchronous read: " + data.toString());
        let lines = data.toString().split('\n');
        for (var i = 0; i < lines.length; i++) {
            let line = lines[i];

            if (line.indexOf('=') >= 0) {
                let parts = line.split('=');
                let key = parts[0].trim();
                let value = parts[1].trim();
                if (key === 'token') {
                    console.log('token=' + value);
                }
            }
        }
    });
    return "";
}

export function setToken(): boolean {
    return true;
}