/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


particlesJS("particles-js", {"particles":{"number":{"value":400, "density":{"enable":true, "value_area":800}}, "color":{"value":"#fff"}, "shape":{"type":"circle", "stroke":{"width":0, "color":"#000000"}, "polygon":{"nb_sides":5}, "image":{"src":"img/github.svg", "width":100, "height":100}}, "opacity":{"value":0.5, "random":true, "anim":{"enable":false, "speed":1, "opacity_min":0.1, "sync":false}}, "size":{"value":10, "random":true, "anim":{"enable":false, "speed":40, "size_min":0.1, "sync":false}}, "line_linked":{"enable":false, "distance":500, "color":"#ffffff", "opacity":0.4, "width":2}, "move":{"enable":true, "speed":6, "direction":"bottom", "random":false, "straight":false, "out_mode":"out", "bounce":false, "attract":{"enable":false, "rotateX":600, "rotateY":1200}}}, "interactivity":{"detect_on":"canvas", "events":{"onhover":{"enable":true, "mode":"bubble"}, "onclick":{"enable":true, "mode":"repulse"}, "resize":true}, "modes":{"grab":{"distance":400, "line_linked":{"opacity":0.5}}, "bubble":{"distance":400, "size":4, "duration":0.3, "opacity":1, "speed":3}, "repulse":{"distance":200, "duration":0.4}, "push":{"particles_nb":4}, "remove":{"particles_nb":2}}}, "retina_detect":true});
        var stats, update; stats = new Stats; stats.setMode(0); stats.domElement.style.position = 'absolute'; stats.domElement.style.left = '0px'; stats.domElement.style.top = '0px'; document.body.appendChild(stats.domElement); update = function() { stats.begin(); stats.end(); if (window.pJSDom[0].pJS.particles && window.pJSDom[0].pJS.particles.array) { } requestAnimationFrame(update); }; requestAnimationFrame(update); ;


$(document).ready(function() {
    // DOM ready
    $('[data-toggle="popover"]').popover(); 
    //Se valida el usuario con el formulario...
    $("#form-signin").validate({
        rules: {
            inputEmail: {
                required: true,
                minlength: 5
            },
            inputPassword: {
                required: true,
                minlength: 5
            }
        },
        messages:{
            inputEmail: {
                required: "Nombre de usuario requerido",
                minlength: "Ingrese al menos 5 caracteres"
            },
            inputPassword: {
                required: "Ingrese una contraseña",
                minlength: "Ya estás mas cerca de entrar."
            }
        },
        submitHandler: function(form) {
            var user = $("#inputEmail").val();
            var pwd = $("#inputPassword").val();
            if(user === "tatich" && pwd === "flaquito"){
                form.submit();
            }
            else{
                alert("Datos incorrectos");
                var user = $("#inputEmail").val("tatich");
                $("#inputPassword").val("");
                $("#inputPassword").focus();
            }
        }
    });
});