function detalle(target){
    var id = $(target).data("id");
    window.location.replace("?p=listarTipoLugar&id="+id);
}

function eliminar(target) {
    var id = $(target).data("id");
    httpConnect("/tipolugar/" + id, null, "DELETE",
            function (r) {
                alert(r.message);
                load();
            });
}

function load() {
    httpConnect("/tipolugar", null, "GET", function (r) {
        var html = "";
        for (var i = 0; i < r.data.length; i++) {
            var tipolugar = r.data[i];
            html += "<tr>";
            html += "<td>" + tipolugar.tluNombre + "</td>";
            html += "</td>";
            html += "</tr>";
        }
        $("tbody").html(html);

        $(".delete").click(function () {
            if (confirm("Conrima la eliminación de este registro?")) {
                eliminar(this);
            }
        });
        $(".edit").click(function () {
            tipolugar(this);
        });

    });
}

$(function () {
    load();
});

