function detalle(target) {
    var id = $(target).data("id");
    window.location.replace("?p=detalleTipoAlojamiento&id=" + id);
}

function eliminar(target) {
    var id = $(target).data("id");
    httpConnect("/tipoAlojamiento/" + id, null, "DELETE",
            function (r) {
                alert(r.message);
                cargarDatos();
            });
}

function cargarDatos() {
    httpConnect("/tipoAlojamiento", null, "GET", function (r) {
        var html = "";
        for (var i = 0; i < r.data.length; i++) {
            var e = r.data[i];
            html += "<tr>";
            html += "<td>" + e.talNombre + "</td>";
            html += "<td>";
            html += "<div data-id='" + e.talId + "' class='material-icons delete' style='color:red'>delete</div>";
            html += "<div data-id='" + e.talId + "' class='material-icons edit' style='color:green'>edit</div>";
            html += "</td>";
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



