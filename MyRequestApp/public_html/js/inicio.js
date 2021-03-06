/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(function () {
    $('.carousel').carousel({
        interval: 5000
    });
    $('#modalPregunta').modal({
        backdrop: 'static',
        keyboard: false,
        show: false
    });

    var modalConfirm = function (callback) {

        $("#modal-btn-si").on("click", function () {
            callback(true);
        });

        $("#modal-btn-no").on("click", function () {
            callback(false);
        });
    };

    modalConfirm(function (confirm) {
        if (confirm) {
            $("#bgRespuesta").css("display", "block");
            $(".container-fluid").css("display", "none");
            var video = document.getElementById("myVideo");
            if (video.paused) {
                video.play();
            }
        } else {
            alert("Respetaré tu respuesta ;)");
        }
    });
});

var myAngularApp = angular.module('MyRequestApp', ['ngRoute']).directive('test', function () {
});

//Información general
myAngularApp.controller('InicioController', function ($scope) {

    $scope.archivoActual = {
        nombre: "Jimbo"
    };

    $scope.fnInitArchivo = function (nombre, tamano, fechaCreacion) {
        if (nombre === "") {
            $scope.archivoActual = null;
            $('#modalSeleccionArchivo').modal('show');
        } else {
            $scope.archivoActual = {
                nombre: nombre,
                tamano: tamano,
                fechaCreacion: fechaCreacion
            };
        }
    };



});