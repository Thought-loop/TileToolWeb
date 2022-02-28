//capture form data on submit, pipe image response into blob and set image source to blob
function send(e,form) {
  e.preventDefault();
  fetch(form.action, {method:'post', body: new FormData(form)})
  .then(response => response.blob())
  .then(imageBlob => {
      // Then create a local URL for that image 
      const imageObjectURL = URL.createObjectURL(imageBlob);
      document.getElementById('img-rendering').setAttribute('src', imageObjectURL);
  });  
}
