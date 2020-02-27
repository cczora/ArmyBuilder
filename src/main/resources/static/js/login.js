$(document).ready(function () {

    $("#loginForm").submit(function (event) {
        event.preventDefault();

        $.ajax({ //call to get the JWT token and set it in local storage
            type: 'POST',
            url: '/authenticate',
            data: JSON.stringify({
                username: $("#username").val(),
                password: $("#password").val()
            }),
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json"
            },
            success: function (res) { //redirect to home page
                localStorage.setItem("jwt", res.jwt);
                $.get({
                    url: '/home',
                    headers: {
                        "Authentication": "Bearer " + localStorage.getItem("jwt")
                    },
                    error: function (err) {
                        console.log("Unable to redirect: ", err);
                    }
                });
            },
            error: function (err) {
                console.log(err);
                alert("Error getting token: " + err.message);
                return false;
            }
        });

    });

});