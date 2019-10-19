/**
 * Autor: Andrés Felipe Mera Tróchez
 */

/**
 * 
 * @param {type} target
 * @returns {undefined}
 */
function detalle(target){
    var id = $(target).data("id");
    window.location.replace("?p=detalleUsuario&id="+id);
}

function eliminar(target) {
    var id = $(target).data("id");
    httpConnect("/usuario/" + id, null, "DELETE",
            function (r) {
                alert(r.message);
                cargarDatos();
            });
}

function cargarDatos() {
    httpConnect("/usuario", null, "GET", function (r) {
        var html = "";
        for (var i = 0; i < r.data.length; i++) {
            var usuario = r.data[i];
            html += "<tr>";
            html += "<td>" + usuario.usuIdentificacion + "</td>";
            html += "<td>" + usuario.usuNombres + "</td>";
            html += "<td>" + usuario.usuGenero + "</td>";
            html += "<td>" + usuario.usuCorreo + "</td>";
            html += "<td>" + usuario.usuTelefono + "</td>";
            html += "<td><img src=" + usuario.usuAvatar + " width=80 height=120></td>";
            html += "<td>" + usuario.fkTipoIdentificacion.tipNombre + "</td>";
            html += "<td>";
            html += "<div data-id='" + usuario.usuId + "' class='material-icons delete' style='color:red'>delete</div>";
            html += "<div data-id='" + usuario.usuId + "' class='material-icons edit' style='color:green'>edit</div>";
            html += "</td>";
            html += "</tr>";
        }
        $("tbody").html(html);

        $(".delete").click(function () {
            if (confirm("Desea eliminar el recurso?")) {
                eliminar(this);
            }
        });
        $(".edit").click(function () {
            detalle(this);
        });

    });
}

$(function () {
    cargarDatos();
});

