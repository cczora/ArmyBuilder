function authenticate() {
    let data = {
        username: $("#username").val(),
        password: $("#password").val()
    };

    $.post("/authenticate", data, function (response) {

    });
}