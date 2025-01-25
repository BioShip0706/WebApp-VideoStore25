document.getElementById('registerForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Evita che la pagina si ricarichi
    
    localStorage.clear();
    // Raccogli i dati dal form
    const username = document.getElementById('username').value;
    const name = document.getElementById('name').value;
    const surname = document.getElementById('surname').value;
    const email = document.getElementById('email').value;
    const password = document.getElementById('password').value;
    //const confirmPassword = document.getElementById('confirmPassword').value;
    


    // Crea un oggetto come la Request richiesta su Java
    const signupData = 
    {
        username: username,
        name: name,
        surname: surname,
        email: email,
        password: password
    };

    // Invia i dati con una richiesta POST per la registrazione
    fetch('http://localhost:8080/auth/signup', 
    {
        method: 'POST',
        headers: 
        {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(signupData)
    })
    .then(response => {
        // Controlla se la risposta è ok
        if (!response.ok) {
            return response.text().then(text => {
                throw new Error(text);  // Lancia l'errore
            });
        }
        return response.text();  // Restituisce il testo (messaggio di successo)
    })
    .then(data => {
        // Visualizza il messaggio nella pagina
        const resultMessage = document.getElementById('resultMessage');
        resultMessage.textContent = data;  // Mostra il messaggio
        resultMessage.style.color = 'green';  // Colore  messaggio 

        // Login dopo registrazione, conservano i dati
        const signinData = {
            username: signupData.username,
            password: signupData.password
        };

        // Effettua la richiesta POST per il login (signin)
        return fetch('http://localhost:8080/auth/signin', 
        {
            method: 'POST',
            headers: 
            {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(signinData)
        });
    })
    .then(response => {
        // Controlla se la risposta per il login è ok
        if (!response.ok) {
            return response.json().then(data => {
                throw new Error(data.message || 'Errore durante il login');
            });
        }
        return response.json();  // Restituisce la risposta JSON (contenente il token)
    })
    .then(data => {
        // Memorizzo il token JWT
        
        const token = data.token;
        const username = data.username; //BioShip
        const authorities = data.authorities; //ROLE_MEMBER o ROLE_ADMIN o entrambi
        const customerId = data.customerId;
        localStorage.setItem('jwtToken', token); // Salva il token nel localStorage
        localStorage.setItem('username', username);
        localStorage.setItem('authorities', JSON.stringify(authorities));
        localStorage.setItem('customerId', customerId);

        window.location.href = 'chiamateVideoStore.html';
        // Visualizza  messaggio di  login
        const resultMessage = document.getElementById('resultMessage');
        resultMessage.textContent = 'Login riuscito!';  // Mostra il messaggio di successo
        resultMessage.style.color = 'green';  // Colore del messaggio di successo
    })
    .catch(error => {
        // Errori lanciati (sistemati con existsBy)
        const resultMessage = document.getElementById('resultMessage');
        resultMessage.textContent = error.message;  // Mostra il messaggio di errore
        resultMessage.style.color = 'red';  // Colore del messaggio di errore
    });
});
