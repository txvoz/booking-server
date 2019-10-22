function cargarAutores() {
    var html = "<option value=''>[SELECCIONAR]</option>";
    httpConnect("/alojamiento", {}, "GET", function (r) {
        for (var i = 0; i < r.data.length; i++) {
            var alojamiento = r.data[i];
            html += "<option value='" + alojamiento.catId + "'>" + alojamiento.catNombre + "</option>";
        }
        $("#aloId").html(html);
    });
}

$(function () {
    //Cargar dependencias de la vista
    cargarAutores();
    //Asiganacion del evento submit para el formulario de crear libro
    $("#frmCrear").submit(function () {

        var entidad = new Object();

        $(".form-control").each(function () {
            var attr = $(this).attr("id");
            entidad[attr] = $(this).val();
        });

        entidad.catId = {
            aloId: $("#aloId").val()
        };

        var jentidad = JSON.stringify(entidad);

        httpConnect("/tipoAlojamiento", jentidad, "POST", function (r) {
            alert(r.message + "-" + r.data.AloNombre);
            alert(r.message + "-" + r.data.Aloopciones);

            $("button[type=reset]").click();
        }, function (r) {
            alert(r.message);
        });

        return false;
    });
});