// Event listener  bottone
document.getElementById("updateFilmButton").addEventListener("click", () => {
    const filmId = document.getElementById("filmId").value;
    const title = document.getElementById("title").value;
    const description = document.getElementById("description").value;
    const releaseYear = document.getElementById("releaseYear").value;
    const languageId = document.getElementById("languageId").value;
    const genreId = document.getElementById("genreId").value;

    // Nessun campo vuoto
    if (!filmId || !title || !description || !releaseYear || !languageId || !genreId) {
        document.getElementById("result").innerText = "Compila tutti i campi.";
        return;
    }
    
    if(!document.getElementById("updateFilmForm").checkValidity())
    {
        alert("Inserisci dati validi!");
        return;
    }


    // Creo la request come la classe Java
    const filmRequest = {
        title: title,
        description: description,
        releaseYear: parseInt(releaseYear),
        languageId: parseInt(languageId),
        genreId: parseInt(genreId)
    };

    const token = localStorage.getItem('jwtToken');

    // URL dell'API
              // http://localhost:8080/film/update-film/1002
    const url = `http://localhost:8080/film/update-film/${filmId}`; 

    // APi Put
    fetch(url, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` // Token
        },
        body: JSON.stringify(filmRequest)
    })
        .then((response) => {
            if (!response.ok) {
                throw new Error("Errore nell'aggiornamento del film.");
            }
            return response.text(); 
        })
        .then((data) => {
            document.getElementById("result").innerText = data; 
        })
        .catch((error) => {
            document.getElementById("result").innerText = "Non sei un admin / errore del server";
        });
});
