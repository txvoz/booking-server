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
    window.location.replace("?p=detalleAlojamiento&id="+id);
}

function eliminar(target) {
    var id = $(target).data("id");
    httpConnect("/alojamiento/" + id, null, "DELETE",
            function (r) {
                alert(r.message);
                cargarDatos();
            });
}

function cargarDatos() {
    httpConnect("/alojamiento", null, "GET", function (r) {
        var html = "";
        for (var i = 0; i < r.data.length; i++) {
            var entity = r.data[i];
            html += "<tr>";
            html += "<td>" + entity.aloCodigo + "</td>";
            html += "<td>" + entity.aloCapacidad + "</td>";
            html += "<td>" + entity.aloPrecio + "</td>";
            html += "<td>" + entity.aloDescripcion + "</td>";
            html += "<td>" + entity.fkLugar.lugNombre + "</td>";
            html += "<td>" + entity.fkTipoAlojamiento.talNombre + "</td>";
            html += "<td>";
            html += "<div data-id='" + entity.aloId + "' class='material-icons delete' style='color:red'>delete</div>";
            html += "<div data-id='" + entity.aloId + "' class='material-icons edit' style='color:green'>edit</div>";
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