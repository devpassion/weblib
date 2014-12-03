


/**
 * Exhibe une carte en se basant sur des coordionnées
 * @param in_lat latitude
 * @param in_lng longitude
 * @param map_id id du div où la carte sera mise
 * @return
 */
function ajust_map( map_id, in_lat, in_lng )
{
    var latlng = new google.maps.LatLng(
        in_lat,
        in_lng
    );

    //alert( latlng );

    show_map(map_id, latlng, '', '', false);
}


/**
 * Exhibe une carte en se basant sur une adresse
 * @param address adresse
 * @param map_id id du div où la carte sera mise
 * @return
 */
function ajust_map_from_addr( map_id, address )
{

    var geocoder = new google.maps.Geocoder();


    geocoder.geocode(
            { address : address	},
            function ( result, status )
            {
                if( status != google.maps.GeocoderStatus.OK )
                {
                    alert ('adresse introuvable !');
                    return;
                }

                latlng = result[0].geometry.location;
                alert (result[0].address_components.locality );
                //if( arguments.lenght == 4 )
                //{
                    //assignLatLng(lat_id, lng_id, latlng);
                //}
                    show_map(map_id, latlng, '', '', false);
            }
        );

    //show_map(map_id, latlng, '', '', false);
}


/**
 * Affiche une carte en fonction d'une addresse
 * @param address_input_id attribut id de la balise input contenant l'addresse
 * @param map_id attribut id de la balise contenant la carte
 * @return
 */
function see_map( address_input_id, map_id, lat_id, lng_id )
{
    var address = window.document.getElementById(address_input_id).value;

    if( address == null || address == '' )
    {
        alert('Vous devez entrer une adresse');
        return;
    }

    //var latlng;

    var geocoder = new google.maps.Geocoder();


    geocoder.geocode(
            { address : address	},
            function ( result, status )
            {
                if( status != google.maps.GeocoderStatus.OK )
                {
                    alert ('adresse introuvable !');
                    return;
                }

                latlng = result[0].geometry.location;
                //if( arguments.lenght == 4 )
                //{
                    assignLatLng(lat_id, lng_id, latlng);
                //}
                show_map(map_id, latlng, lat_id, lng_id);
            }
        );


}


// Variable indiquant qu'une réponse doit être attendue de google map avnt de valider un formulaire
var mustWaitResponse = false;

/**
 * Affiche une carte draggable en fonction d'une addresse
 * @param address_input_id attribut id de la balise input contenant l'addresse
 * @param map_id attribut id de la balise contenant la carte
 *
 * @return
 */
function see_dmap( address_input_id, map_id, city_id, lat_id, lng_id, height )
{
	//window.setTimeout( "see_dmap( address_input_id, map_id, city_id, lat_id, lng_id, height )", 2000 );
//}
//function see_dmap( address_input_id, map_id, city_id, lat_id, lng_id, height )
//{
    var address = window.document.getElementById(address_input_id).value;

    if( address == null || address == '' )
    {
        alert('Vous devez entrer une adresse');
        return;
    }

    // "Réponse à attendre"
    mustWaitResponse = true;
    
    var geocoder = new google.maps.Geocoder();


    geocoder.geocode(
            { address : address	},
            function ( result, status )
            {
                if( status != google.maps.GeocoderStatus.OK )
                {
                    alert ('adresse introuvable !');
                    return;
                }

                latlng = result[0].geometry.location;

                var country = null;

                for (var i=0;i<result[0].address_components.length;i++){
                    for (j=0;j<result[0].address_components[i].types.length;j++){
                       if(result[0].address_components[i].types[j]=="locality")
                          country = result[0].address_components[i].long_name
                    }
                }

                //alert (country );

                var city_input = document.getElementById( city_id );

                if( city_input != null )
                {
                    if( country == null )
                    {
                        alert ('adresse sans ville');
                        return;
                    }

                    city_input.setAttribute( 'value', country);
                }
                
                                
                
                assignLatLng(lat_id, lng_id, latlng);

                show_map(map_id, latlng, lat_id, lng_id, true, height);
                
                // ICI, le formulaire peut être validé
                mustWaitResponse = false;
            }
        );


}






/**
 * Exhibe une carte
 * @param map_id id de la div contenant la carte
 * @param latlng Latitude et longitude du marker
 * @return
 */
function show_map( map_id, latlng, lat_id, lng_id, draggable, height )
{
    var options = {
            center: latlng,
            zoom: 15,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };

    //alert(document.getElementById(map_id));
    document.getElementById(map_id).style.height = height;
    var carte = new google.maps.Map(document.getElementById(map_id), options);

    //création du marqueur
    var marqueur = new google.maps.Marker({
        position: latlng,
        map: carte
        //draggable : true
    });

    if( draggable )
    {
        marqueur.setDraggable(true);

        google.maps.event.addListener( marqueur, 'dragend', function(event) {
            assignLatLng( lat_id, lng_id, event.latLng );

        } );
        $('#cursormove').css( 'display', 'block' );
    }


}

/**
 * Assigne une lattitude et une longitude à un champ input
 * @param lat_id id de l'input contenant la latitude
 * @param lng_id id de l'input contenant la longitude
 * @return
 */
function assignLatLng( lat_id, lng_id, latlng )
{

    //alert ( lat_id  ) ;
    //alert ( lng_id  );
    var latInput =  document.getElementById(lat_id);
    var lngInput =  document.getElementById(lng_id);

    //alert ( latInput  ) ;
    //alert ( lngInput  );
    //latInput.setAttribute( 'value', latlng.lat() );
    //lngInput.setAttribute( 'value', latlng.lng() );


    if( latInput != null && lngInput != null )
    {
        //alert ( "set " + lat_id + ":" + latlng.lat() ) ;
        latInput.setAttribute( 'value', latlng.lat() );
        lngInput.setAttribute( 'value', latlng.lng() );
    }

}



function seemap_ifenter( event, address_input_id, map_id, lat_id, lng_id )
{
    if(!event && window.event) {
        event = window.event;
    }
    // IE
    if(event.keyCode == 13) {
        see_dmap(address_input_id, map_id, lat_id, lng_id);
    }
    // DOM
    if(event.which == 13) {
        see_dmap(address_input_id, map_id, lat_id, lng_id);
    }
}


function refuserToucheEntree(event, address_input_id, map_id, lat_id, lng_id)
{
    // Compatibilité IE / Firefox
    if(!event && window.event) {
        event = window.event;
    }
    // IE
    if(event.keyCode == 13) {
        see_dmap(address_input_id, map_id, lat_id, lng_id);
        event.returnValue = false;
        event.cancelBubble = true;
    }
    // DOM
    if(event.which == 13) {
        see_dmap(address_input_id, map_id, lat_id, lng_id);
        event.preventDefault();
        event.stopPropagation();
    }
}



$(document).ready( function() {
	$("form").submit(function(event){
		if( mustWaitResponse )
		{
			setTimeout( "return true;" ,2000);
		}
		else
		{
			return true;
		}
	});
});



//function init()
//{
//Event.observe(
//'party',
//'submit',
//function(event)
//{Event.stop(event);}
//);
//}
//Event.observe(window, 'load', init)
