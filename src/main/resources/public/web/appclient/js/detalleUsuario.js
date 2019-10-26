function cargarTipoIdentificacion(fn) 
{
    httpConnect("/tipid", null, "GET", function (r) 
    {
        var html = "<select id='fkTipoIdentificacion' name='fkTipoIdentificacion' class='form-control' required>";
        html = "<option value=''>[Seleccione opcion]</option>";
        for(var i = 0; i < r.data.length; i++)
        {
            var tid = r.data[i];
            html += "<option value='"+tid.tidId+"'>"+tid.tipNombre+"</option>";
        }
        html += "</select>";
        $("#fkTipoIdentificacion").html(html);
        fn();
    });
}
function cargarDetalle() {
    var id = getParameterByName("id");
    httpConnect("/usuario/" + id, null, "GET",function(r){
        if(r.status!==200){
            alert(r.message);
            window.location.replace("?p=listarUsuario");
        }
        $("#id").val(id);
        $("#usuIdentificacion").val(r.data.usuIdentificacion);
        $("#usuNombres").val(r.data.usuNombres);
        $("#usuGenero").val(r.data.usuGenero);
        $("#usuCorreo").val(r.data.usuCorreo);
        $("#usuTelefono").val(r.data.usuTelefono);
        $("#usuAvatar").val(r.data.usuAvatar);
        $("#fkTipoIdentificacion").val(r.data.fkTipoIdentificacion);
    },function(e){
        alert(e);
        window.location.replace("?p=listarUsuario");
    });
}

$(function () 
{
    cargarDetalle();
    cargarTipoIdentificacion();
    $("#frmUpdate").submit(function()
    {
        var entidad = new Object();
        entidad.usuIdentificacion = $("#usuIdentificacion").val();
        entidad.usuNombres = $("#usuNombres").val();
        entidad.usuGenero = $("input[name='usuGenero']:checked").val();;
        entidad.usuCorreo = $("#usuCorreo").val();
        entidad.usuTelefono = $("#usuTelefono").val();
        entidad.usuAvatar = $("#usuAvatar").val();
        entidad.fkTipoIdentificacion = 
        {
            tidId:$("#fkTipoIdentificacion").val()
        };
        
        var jentidad = JSON.stringify(entidad);
        var id=$("#id").val();
        httpConnect("/usuario/"+id,jentidad,"PUT",function(r)
        {
            alert(r.message);
            window.location.replace("?p=listarUsuario");
        });
        return false;
    });
});

