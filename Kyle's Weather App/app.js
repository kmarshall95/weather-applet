window.addEventListener("load", () => {
    let long;
    let lat;
    let temperatureDescription = document.querySelector(".temperature-desc");
    let temperatureDegree = document.querySelector(".temperature-degree");
    let locationTimeZone = document.querySelector(".location");

    if(navigator.geolocation){
        navigator.geolocation.getCurrentPosition(position =>{
            long = position.coords.longitude;
            lat = position.coords.latitude;

            const api = "https://cors-anywhere.herokuapp.com/https://api.darksky.net/forecast/735162ce30c2b8e2a5ea9e88b12d54e2/" + lat +","+long;

                fetch(api)
                .then(response => {
                    return response.json();
                })
                .then(data => {
                    console.log(data);
                    const {temperature, summary, icon} = data.currently;
                    //Set DOM elements from the API
                    temperatureDegree.textContent = temperature;
                    locationTimeZone.textContent = data.timezone;
                    temperatureDescription.textContent = summary;
                    //Set Icon
                    setIcons(icon, document.querySelector('.icon'));
                });
        });
    }

    function setIcons(icon,iconID){
        const skycons = new Skycons({color:"white"});
        const currentIcons = icon.replace(/-/g, "_").toUpperCase();
        skycons.play();
        return skycons.set(iconID, Skycons[currentIcons]); 
    }
});