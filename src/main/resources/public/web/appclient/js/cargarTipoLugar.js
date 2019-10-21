function cargarTipoLugar() {
    
    //llama a la tabla en la base  de datos
    
    var html = "<option value=''>[Seleccionar]</option>";
    httpConnect("/tipolugar", {}, "GET", function (r) {
        for (var i = 0; i < r.data.length; i++) {
            var tipolugar = r.data[i];
            html += "<option value='" + tipolugar.tluId+ "'>" + tipolugar.tluNombre + "</option>";
        }
        $("#Id_TpLugar").html(html);
        
    });
}


$(function(){
    cargarTipoLugar();
});