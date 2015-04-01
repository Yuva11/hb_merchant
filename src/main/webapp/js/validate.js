function validateEmail(email) {
	var reg = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
	return reg.test(email);
}
function checkEmail(elt) {
	console.log("check email...");
	var email = elt.value;
	var id = elt.id;
	if (validateEmail(email)) {
		$("#"+id).last().removeClass("ui-state-error");
	} else {
		$("#"+id).last().addClass("ui-state-error");
	}
}
function checkNum(e){
	 var k;
	    if (e.keyCode == 8||e.keyCode == 9 || e.keyCode == 46 || e.keyCode == 37 || e.keyCode == 39||e.keyCode ==36||e.keyCode ==16) {
					return true;
		}	    
	    document.all ? k = e.keyCode : k = e.which;
	    return (k >= 48 && k <= 57)||(k >= 96 && k <= 105);
}
function checkPrice(e){
	 var k;
	    if (e.keyCode == 8||e.keyCode == 9 || e.keyCode == 46 || e.keyCode == 37 || e.keyCode == 39||e.keyCode ==36||e.keyCode ==16) {
					return true;
		}	    
	    document.all ? k = e.keyCode : k = e.which;
	    return (k >= 48 && k <= 57)||(k >= 96 && k <= 105)||190||110;
}
function ssnCheck(e) {
   return checkNum(e);
}
function ssnCheckVal(elt){
	var id=elt.id;
	var hidenId=id+"hidden";
	elt.value=elt.value.substring(0,9);
	var hiddenVal=$("#"+hidenId).val();
	$("#"+hidenId).val(hiddenVal.substring(0,9));
	var l=elt.value.length;
	if (l==9) {
		$("#"+id).last().removeClass("ui-state-error");
	} else {
		$("#"+id).last().addClass("ui-state-error");
	}
}
function zipCheck(e) {
	return checkNum(e);
}
function zipCheckVal(elt){
	var id=elt.id;
	var l=elt.value.length;
	if (l>=5) {
		$("#"+id).last().removeClass("ui-state-error");
	} else {
		$("#"+id).last().addClass("ui-state-error");
	}
}
function isValidDate(date)
{
	console.log(date);
	var matches = /^(\d{2})[-\/](\d{2})[-\/](\d{4})$/.exec(date);
	if (matches == null) return false;
	console.log(matches);
	var d = matches[2];
    var m = matches[1] - 1;
    var y = matches[3];
    console.log("d "+d+" M "+m+" y "+y)
    var composedDate = new Date(y, m, d);
    return composedDate.getDate() == d &&
            composedDate.getMonth() == m &&
            composedDate.getFullYear() == y;
}
function dobCheck(elt){
	var date=elt.value;
	var id=elt.id;
	console.log(date);
	if (isValidDate(date)) {
		$("#"+id).last().removeClass("ui-state-error");
	} else {
		$("#"+id).last().addClass("ui-state-error");
	}
}
function phoneCheck(elt){
	var id=elt.id;
	var l=elt.value.length;
	console.log(l);
	if (l>=10) {
		$("#"+id).last().removeClass("ui-state-error");
	} else {
		$("#"+id).last().addClass("ui-state-error");
	}
}
function textlength(e) {
    var value = e.target.value;
	var id=e.target.id;
    if (e.keyCode == 8 || e.keyCode == 46 || e.keyCode == 37
      || e.keyCode == 39) {
     return true;
    }
    if (value.length > 99) {
    	 $("#"+id).val(value.substring(0,99))
     return false;
    }
    return true
   }
function validAnswer(f) {
	!(/^[\w ~!@$^=()_{}.,/-:|[\]-]*$/i).test(f.value)?f.value = f.value.replace(/[^\w ~!@$^=()_{}.,/-:|[\]-]/ig,''):null;
	f.value = f.value.replace(/[\\]/ig, '');
	} 
function validQuestion(f) {
	!(/^[\w @=()_{}.?,/-:|[\]]*$/i).test(f.value)?f.value = f.value.replace(/[^\w @=()_{}.?,/-:|[\]]/ig,''):null;
	f.value = f.value.replace(/[\\]/ig, '');
	}
function valid(f) {
	!(/^[A-z0-9@.?' ()]*$/i).test(f.value)?f.value = f.value.replace(/[^A-z0-9@.?' ()]/ig,''):null;
	}
function validVersion(f) {
	!(/^[0-9.]*$/i).test(f.value)?f.value = f.value.replace(/[^0-9.]/ig,''):null;
	}
function deal(e){
	 var k;
	    if (e.keyCode == 8 || e.keyCode == 9 || e.keyCode == 16 || e.keyCode == 17 || e.keyCode == 20 || e.keyCode == 32 || e.keyCode == 46 || e.keyCode == 144 || e.keyCode == 192 ) {
					return true;
		}	    
	    document.all ? k = e.keyCode : k = e.which;
	    return (k >= 35 && k <= 40)||(k >= 48 && k <= 90)||(k >= 48 && k <= 90)||(k >= 93 && k <= 111)||(k >= 186 && k <= 191)||(k >= 219 && k <= 222);
}
function dealNameLengthChk(elt){
	var id=elt.id;
	var l=elt.value.length;
	if (l<4 || l>45) {
		$("#"+id).last().addClass("ui-state-error");
	} else {
		$("#"+id).last().removeClass("ui-state-error"); 	
	}
}
function dealDetailLengthChk(elt){
	var id=elt.id;
	var l=elt.value.length;
	if (l<4 || l>150) {
		$("#"+id).last().addClass("ui-state-error");
	} else {
		$("#"+id).last().removeClass("ui-state-error"); 	
	}
}
function checkDealPrice(elt){ 
	var dealprice =  document.getElementById("deal-price").value;
	var originalprice = document.getElementById("original-price").value;
	var comp = (dealprice  - originalprice >= 0 ) ? alert("Deal Price must "+dealprice+" be less than original price"+originalprice):"" ; 
}