document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita il ricaricamento della pagina

    localStorage.clear();
    // Raccogli i dati dal form
    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

    // Request Java
    const signinData = {
        username: username,
        password: password
    };

    // Effettua la richiesta POST per il login (signin)
    fetch('http://localhost:8080/auth/signin', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(signinData)
    })
    .then(response => {
        // Leggi sempre il testo o il JSON restituito dal server
        return response.text().then(text => {
            return {
                ok: response.ok,
                status: response.status,
                body: text
            };
        });
    })
    .then(result => {
        // Controlla se la risposta è ok
        if (!result.ok) {
            // result.body perchè le RE restituiscono messaggi
            const resultMessage = document.getElementById('resultMessage');
            resultMessage.textContent = result.body; // Messaggio della RE
            resultMessage.style.color = 'red';
            return;
        }

        // analizza il JSON e memorizza il token
        const data = JSON.parse(result.body);
        const token = data.token;
        const username = data.username; //BioShip
        const authorities = data.authorities; //ROLE_MEMBER o ROLE_ADMIN o entrambi
        const customerId = data.customerId;
        localStorage.setItem('jwtToken', token); // Salva il token nel localStorage
        localStorage.setItem('username', username);
        localStorage.setItem('authorities', JSON.stringify(authorities));
        localStorage.setItem('customerId', customerId);

        // Reindirizza se tutto è andato bene
        window.location.href = 'chiamateVideoStore.html';

        // Messaggio  di successo (opzionale)
        const resultMessage = document.getElementById('resultMessage');
        resultMessage.textContent = 'Login riuscito!';
        resultMessage.style.color = 'green';
    })
    .catch(error => {
        // Errori
        const resultMessage = document.getElementById('resultMessage');
        resultMessage.textContent = 'Errore: ' + error.message;
        resultMessage.style.color = 'red';
    });
});
