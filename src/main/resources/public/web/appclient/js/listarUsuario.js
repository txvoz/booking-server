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
            html += "<td>" + usuario.usuId + "</td>";
            html += "<td>" + usuario.usuIdentificacion + "</td>";
            html += "<td>" + usuario.nombre + "</td>";
            html += "<td>" + usuario.genero + "</td>";
            html += "<td>" + usuario.correo + "</td>";
            html += "<td>" + usuario.telefono + "</td>";
            html += "<td>" + usuario.avatarcode + "</td>";
            html += "<td>" + usuario.tipoidentificacion + "</td>";
            html += "<td>";
            html += "<div data-id='" + i + "' class='material-icons delete' style='color:red'>delete</div>";
            html += "<div data-id='" + i + "' class='material-icons edit' style='color:green'>edit</div>";
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