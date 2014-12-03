var test = true;

$(document).ready(function(){

if(!Modernizr.input.placeholder)
{

    $('[placeholder]').focus(function() {
      var input = $(this);
      if (input.val() == input.attr('placeholder')) {
        input.val('');
        input.removeClass('placeholder');
      }
    }).blur(function() {
      var input = $(this);
      if (input.val() == '' || input.val() == input.attr('placeholder')) {
        input.addClass('placeholder');
        input.val(input.attr('placeholder'));
      }
    }).blur();
    $('[placeholder]').parents('form').submit(function() {
      $(this).find('[placeholder]').each(function() {
        var input = $(this);
        if (input.val() == input.attr('placeholder')) {
          input.val('');
        }
      })
    });

}

if( !Modernizr.input.time || test )
{
    var html = '';
    var val = '';
    //var val;
    
    var value = $('#time input').val();
    
    
    
    for ( var hour = 0; hour < 24 ; hour++)
    {
        for ( var min = 0; min < 60 ; min += 5)
        {
        	val = ( hour < 10 ? '0':'' ) + hour +':'+ ( min < 10 ? '0':'' ) + min;
            html += '<option ' + (val == value ? 'selected="selected"' : '') +' value="' + val  +'">' + ( hour < 10 ? '0':'' ) + hour + 'h' + ( min < 10 ? '0':'' ) + min + '</option>';
        }
    }


    $('#time').html( '<select name="time">' + html +'</select>' );
}

});
