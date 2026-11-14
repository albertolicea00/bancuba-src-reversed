//FUNCIONES DE AYUDA
    //Dar formato a los numeros

        var num=0;
        var nls="";
        var rr="\r";
        var s="$";
        var ns="01234567890";
        var cr="";
        var str="";

        function fmtIt(fracc){
         str=""+fracc;
         pos=str.indexOf(".");
         if (pos==0){
          str="0"+str;
          pos++;
          }
         if (pos<0){
          str+=".00";
          pos=str.indexOf(".");
          }
         str+="0000";
         str=str.substring(0,pos+4);
         cr=str.charAt(str.length-1);
         pos=ns.indexOf(cr);
         str=str.substring(0,str.length-1);
         if (pos>5){
          fmtIt1();
         }
         return str;
    }

    function fmtIt1(){
          for (var k=str.length-1;k>-1;k--){
            cr=str.charAt(k);
            posn=ns.indexOf(cr);
            if (posn<0){
                k--;
            }
            else{
                str=str.substring(0,k)+ns.substring(posn+1,posn+2)
                +str.substring(k+1,str.length);
                if (posn!=9){
                  k=-1;
                }
            }
          }
    }