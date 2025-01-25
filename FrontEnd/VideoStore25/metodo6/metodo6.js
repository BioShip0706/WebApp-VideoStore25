
document.getElementById("countRentalsBtn").addEventListener("click", function()
{
    countRentals();
});



function countRentals() 
{

    const storeId = document.getElementById("storeId").value
    const dateStart = document.getElementById("dateStart").value
    const dateEnd = document.getElementById("dateEnd").value

    // Verifica che tutti i campi siano compilati
    if (!dateStart || !dateEnd || !storeId ) {
        document.getElementById("result").innerText = "Compila tutti i campi.";
        return;
    }

    if(dateStart < "2023-01-01" || dateStart < "2023-01-01" )
    {
        document.getElementById("result").innerText = "Le date non possono essere inferiori al 01/01/2023";
        return;
    }

   

    const token = localStorage.getItem('jwtToken');

    
              // http://localhost:8080/film/count-rentals-in-date-range-by-store/1?start=2023-01-01&end=2024-01-01
    const url = `http://localhost:8080/film/count-rentals-in-date-range-by-store/${storeId}?start=${dateStart}&end=${dateEnd}`; 

    //GET
    fetch(url, {
        method: "GET",
        headers: {
            "Content-Type": "application/json",
            "Authorization": `Bearer ${token}` 
        },
    })
        .then((response) => {
            if (!response.ok) {
                return response.text();
            }
            return response.text(); 
        })
        .then((data) => {
            document.getElementById("result").innerText = "Non sei un admin / errore del server";
        })
        .catch((error) => {
            document.getElementById("result").innerText = "Non sei un admin / errore del server";
        });
};
