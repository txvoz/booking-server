$(function(){
    $("#frmCrear").submit(function(){
        var tipolugar = new Object();
        tipolugar.tluNombre = $("#tluNombre").val();
                
        var jtipolugar = JSON.stringify(tipolugar);
        
        httpConnect("/tipolugar",jtipolugar,"POST",function(r){
            alert(r.message+"-"+r.data.tluNombre);
            $("button[type=reset]").click();
        });
        
        return false;
    });
});