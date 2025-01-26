


document.getElementById("addFilmToStoreButton").addEventListener("click", metodo3);

function metodo3() {
   
    const filmId = document.getElementById("filmId").value;
    const storeId = document.getElementById("storeId").value;
    const token = localStorage.getItem('jwtToken');

    
    if (!filmId || !storeId) {
        alert("Per favore inserisci sia il Film ID che lo Store ID.");
        return;
    }

    if(!document.getElementById("addFilmStoreForm").checkValidity())
    {
        alert("Inserisci dati validi");
        return;
    }
    
               //http://localhost:8080/film/add-film-to-store/7/1001
    const url = `http://localhost:8080/film/add-film-to-store/${storeId}/${filmId}`;

    
    fetch(url, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            'Authorization': `Bearer ${token}`
        }
    })
    .then(response => {
        if (!response.ok) {
           
            return response.text().then(text => {
                
                throw new Error(text);
            });
        }
        
        return response.text();
    })
    .then(data => {
        
        document.getElementById("result").textContent = data;
        document.getElementById("result").className = "success";
    })
    .catch((error) => {
        document.getElementById("result").innerText = error.message;
    });
}

