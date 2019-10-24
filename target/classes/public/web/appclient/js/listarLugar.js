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
    window.location.replace("?p=detalleLugar&id="+id);
}

function eliminar(target) {
    var id = $(target).data("id");
    httpConnect("/lugar/" + id, null, "DELETE",
            function (r) {
                alert(r.message);
                cargarDatos();
            });
}

function cargarDatos() {
    httpConnect("/lugar", null, "GET", function (r) {
        var html = "";
        for (var i = 0; i < r.data.length; i++) {
            var lugar = r.data[i];
            html += "<tr>";
            html += "<td>" + lugar.lugNombre + "</td>";
            html += "<td>" + lugar.lugDireccion + "</td>";
            html += "<td>" + lugar.lugTelefono + "</td>";
            html += "<td>" + lugar.lugCorreo + "</td>";
            html += "<td>" + lugar.lugLatitud + "</td>";
            html += "<td>" + lugar.lugLongitud + "</td>";
            html += "<td>" + lugar.lugDescripcion + "</td>";
            html += "<td>" + lugar.fkTipoLugar.tluNombre + "</td>";
            html += "<td>" + lugar.fkMunicipio.munNombre + "</td>";
            html += "<td>";
            html += "<div data-id='" + lugar.lugId + "' class='material-icons delete' style='color:red'>delete</div>";
            html += "<div data-id='" + lugar.lugId + "' class='material-icons edit' style='color:green'>edit</div>";
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

