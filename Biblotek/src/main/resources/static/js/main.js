$(() => {

    $.ajaxSetup({
      beforeSend : (xhr, settings) => {
        if (settings.type == 'POST' || settings.type == 'PUT' || settings.type == 'DELETE') {
          if (!(/^http:.*/.test(settings.url) || /^https:.*/.test(settings.url))) {
            xhr.setRequestHeader("X-XSRF-TOKEN", Cookies.get('XSRF-TOKEN'));
          }
        }
      }
    });

    $('#logout').click(() => {
      $.post(
        {
          url:"/logout",
          success:() => {
            location.reload(true);
          }
        });
    });

});

$('#cookie-popup', () => {
  if(Cookies.get('cookie-consent') != 'true') {
    $('#cookie-popup').modal({
      backdrop: false,
      keyboard: false,
      focus: false,
      show: true
    });
  }
  $('#cookie-accept').on('click', () => {
    Cookies.set('cookie-consent', 'true');
    $('#cookie-popup').modal('hide');
  });
});
