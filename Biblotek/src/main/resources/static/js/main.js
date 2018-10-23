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
