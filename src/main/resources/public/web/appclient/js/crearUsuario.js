function cargarCategorias() {
    httpConnect("/categoria", null, "GET", function (r) {
        var html = "<select id='categoria' name='categoria' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + i + "'>" + o.nombre + "</option>";
        }
        html += "</select>";
        $("#contentCategoria").html(html);
    });
}

/*function cargarGeneros() {
    httpConnect("/usuario", null, "GET", function (r) {
        var html = "<select id='genero' name='genero' class='form-control' required>";
        html += "<option value=''>[SELECCIONAR OPCION]</option>";
        for (var i = 0; i < r.data.length; i++) {
            var o = r.data[i];
            html += "<option value='" + i + "'>" + o.nombre + "</option>";
        }
        html += "</select>";
        $("#contentGenero").html(html);
    });
}*/

$(function(){
    $("#frmCrear").submit(function(){
        var libro = new Object();
        libro.identificacion = $("#identificacion").val();
        libro.nombre = $("#nombre").val();
        libro.genero = $("#genero").val();
        libro.correo = $("#correo").val();
        libro.telefono = $("#telefono").val();
        libro.avatarcode = $("#avatarcode").val();
        libro.tipoidentificacion = $("#tipoidentificacion").val();
        
        var jlibro = JSON.stringify(libro);
        
        httpConnect("/usuario",jlibro,"POST",function(r){
            alert(r.message+"-"+r.data.nombre);
            $("button[type=reset]").click();
        });
        
        return false;
    });
});