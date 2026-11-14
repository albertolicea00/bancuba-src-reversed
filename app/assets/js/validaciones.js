window.onload = function() {

		var errMsg = { 
		// Checks for when a specified field is required
		 required: { 
		 			msg: "This field is required.", 
		 			test: function(obj) { 
					 // Make sure that there is no text was entered in the field and that 
					 // we aren't checking on page load (showing 'field required' messages 
					 // would be annoying on page load) 
			 			return obj.value.length > obj.defaultValue.length; 
					 } 
				   },
		// Makes sure that the field s a valid email address 
		  email: { 
		  			msg: "Not a valid email address.", 
		  			test: function(obj) { 
		  			// Make sure that something was entered and that it looks like 
		  			// an email address 
		  				return !obj.value || /^[a-z0-9_+.-]+\@([a-z0-9-]+\.)+[a-z0-9]{2,4}$/i.test( obj.value );
		  			} 
		  		},
		// Makes sure the field is a phone number and 
		// auto-formats the number if it is one 
		  phone: { 
		  			msg: "Not a valid phone number.", 
		  			test: function(obj) { 
			  			// Check to see if we have something that looks like 
			  			// a valid phone number 
			  			var m = /(\d{3}).*(\d{3}).*(\d{4})/.exec( obj.value );
						// If it is, seemingly, valid - force it into the specific 
						// format that we desire: (123) 456-7890 
						if ( m ) obj.value = "(" + m[1] + ") " + m[2] + "-" + m[3];
						return !obj.value || m;
					}
				},

		// Makes sure that the field is a valid MM/DD/YYYY date 
		  date: { 
		  			msg: "Not a valid date.",
		  			test: function(obj) { 
		  			// Make sure that something is entered, and that it 
		  			// looks like a valid MM/DD/YYYY date 
		  				return !obj.value || /^\d{2}\/\d{2}\/\d{2,4}$/.test(obj.value); 
		  			} 
		  		},

		// Makes sure that the field is a valid URL 
	      url: { 
	      			msg: "Not a valid URL.", 
	      			test: function(obj) { 
	      			// Make sure that some text was entered, and that it's 
	      			// not the default http:// text 
	      				return !obj.value || obj.value == 'http://' || 
	      				// Make sure that it looks like a valid URL 
	      				/^https?:\/\/([a-z0-9-]+\.)+[a-z0-9]{2,4}.*$/.test( obj.value ); 
	      			} 
	      		},

	    // Makes sure that the field is a integer value
	      int: { 
	      			msg: "No es un n&uacute;mero entero.", 
	      			test: function(obj) { 
	      			// Make sure that some text was entered, and that it's 
	      			// not the default http:// text 
	      				return !obj.value || obj.value == $.isNumeric( obj.value ); 
	      			} 
	      		},

	    // Makes sure that the field is a decimal(2) value
	      dec: { 
	      			msg: "No es un n&uacute;mero decimal.", 
	      			test: function(obj) { 
	      			// Make sure that some text was entered, and that it's 
	      			// not the default http:// text 
	      				return !obj.value || obj.value == parseFloat( obj.value ); 
	      			} 
	      		}	      		

		};

		//NO SE PARA QUE SERVIRIA VALIDAR A LA HORA DE CARGAR	
		// A function for validating all fields within a form. 
		// The form argument should be a reference to a form element 
		// The load argument should be a boolean referring to the fact that 
		// the validation function is being run on page load, versus dynamically 
		function validateForm( form ) { 
			var valid = true;
			// Go through all the field elements in form 
			// form.elements is an array of all fields in a form 
			for ( var i = 0; i < form.elements.length; i++ ) {
				// Hide any error messages, if they're being shown 
				hideErrors( form.elements[i] );
				// Check to see if the field contains valid contents, or not 
				if ( ! validateField( form.elements[i] ) ) 
					valid = false;
			}
			// Return false if a field does not have valid contents 
			// true if all fields are valid 
			return valid;
		}

		// Validate a single field's contents 
		function validateField( elem ) { 
			var errors = [],
			 valid = true;
			// Go through all the possible validation techniques 
			for ( var name in errMsg ) { 
				// See if the field has the class specified by the error type 
				var re = new RegExp("(^|\\s)" + name + "(\\s|$)");
				// Check to see if the element has the class and that it passes the 
				// validation test 
				if ( re.test( elem.className ) && !errMsg[name].test( elem ) ) 
				// If it fails the validation, add the error message to list 
					errors.push( errMsg[name].msg );
			}
			// Show the error messages, if they exist 
			if ( errors.length ) {
				showErrors( elem, errors );
				valid=false;
			}
			// Return false if the field fails any of the validation routines
			 return valid;
		}

		// Hide any validation error messages that are currently shown 
		function hideErrors( elem ) { 
			// Find the next element after the current field 
			var next = elem.nextSibling;
			// If the next element is a ul and has a class of errors 
			if ( next && next.nodeName == "UL" && next.className == "errors" ) 
			// Remove it (which is our means of 'hiding') 
				elem.parentNode.removeChild( next );
		}

		// Show a set of errors messages for a specific field within a form 
		function showErrors( elem, errors ) { 
			// Find the next element after the field 
			var next = elem.nextSibling;
			// If the field isn't one of our special error-holders. 
			if ( next && ( next.nodeName != "UL" || next.className != "errors" ) ) { 
				// We need to make one instead 
				next = document.createElement( "ul" ); next.className = "errors";
				// and then insert into the correct place in the DOM 
				elem.parentNode.insertBefore( next, elem.nextSibling );
			}
			// Now that we have a reference to the error holder UL 
			// We then loop through all the error messages 
			for ( var i = 0; i < errors.length; i++ ) { 
			// Create a new li wrapper for each 
				var li = document.createElement( "li" ); 
				li.innerHTML = errors[i];
				// and insert it into the DOM 
				next.appendChild( li );
			}
		}
 
		//Comprobar errores en los diferentes inputs del form al dar SUBMIT
		$("form").submit(function(){
			var form = document.getElementsByTagName( "form" )[0];
			// make sure that the form's contents validate correctly 
			if( validateForm( form ))
			//url="calcular.html?p1=12&parametro2="+$("#name").val();
			this.action="calcular.html?p1=12&parametro2="+$("#name").val();
		})
		
/*
		//Comprobar errores luego de salir de un input
		// Go through all the field elements in form 
		var form = document.getElementsByTagName( "form" )[0];
		for ( var i = 0; i < form.elements.length; i++ ) {
		// and attach a 'change' event handler (which watches for a user 
		// to lose focus of an input element) 
			//ESTO NO FUNCIONO... addEvent( form.elements[i], 'change',  function(){
		//lo hice asi:
		form.elements[i].onchange = function(){
			// Once the focus has been lost, re-validate the field 
			return validateForm( form );
		}
		}
*/



		// Find all input fields that have been marked as required 
		$(".required").each(function(index, value) {
			// then locate the previous label 
			$(this).prev("label").each(function(index, value) {
				// Change the cursor, over the label, to being more helpful 
				$(this).css("cursor", "help")
				// Make it so that when the user hovers their mouse over, a description 
				// of the * is explained 
				//OJO BUSCAR QUE PROPIEDAD VA AQUI
				//$(this).title	( errMsg.required);
				// Finally, add a * at the end of the label, to signify 
				// the field as being required 
				$(this).append(" <span class='required'>*</span>");
			})
		


		
	})};