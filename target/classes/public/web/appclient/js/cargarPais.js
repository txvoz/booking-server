function cargarPais() {

    //llama a la tabla en la base  de datos

    var html = "<option id='paisSelect' value=''>Seleccionar</option>";
    httpConnect("/pais", {}, "GET", function (r) {
        for (var i = 0; i < r.data.length; i++) {
            var pais = r.data[i];
            html += "<option value='" + pais.paiId + "'>" + pais.paiNombre + "</option>";
        }
        $("#Pais").html(html);

    });

}


$(function () {
    cargarPais();
});
