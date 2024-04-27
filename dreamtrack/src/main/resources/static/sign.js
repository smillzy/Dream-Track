let hostName = window.location.protocol;
let domainName = window.location.hostname;
let port = window.location.port

// Change form
const callSignUpformBtn = document.getElementById("call-sign-up-form");
const callLogInformBtn = document.getElementById("call-log-in-form");
const signUpForm = document.getElementById("sign-up-form");
const logInform = document.getElementById("log-in-form");

const showElement = (showedElement, hideElement) => {
    showedElement.classList.remove("hide");
    hideElement.classList.add("hide");
};

callLogInformBtn.addEventListener("click", () => {
    showElement(logInform, signUpForm);
});

callSignUpformBtn.addEventListener("click", () => {
    showElement(signUpForm, logInform);
});

// Back to home page
const home = document.getElementById("logo");

home.addEventListener("click", () => {
    window.location.href = '/';
})

// submit user info
const logInButton = document.getElementById("log-in-button");

logInButton.addEventListener("click", logIn)

const logInURL = `${hostName}//${domainName}:${port}/api/v1/user/login`;

async function logIn() {
    try {
        const email = document.getElementById('log-in-email').value;
        const password = document.getElementById('log-in-password').value;

        const loginData = {
            email: email,
            password: password
        };

        const response = await fetch(logInURL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }

        const data = await response.json();
        localStorage.setItem('accessToken', data.access_token);
        localStorage.setItem('userId', data.user_id);
        alert('Login successful!');
        window.location.href = "/";
    } catch (error) {
        console.error('Error:', error);
        alert('Error logging in');
    }
}

const singUpButton = document.getElementById("sign-up-button");
singUpButton.addEventListener("click", singUp)

const singUpURL = `${hostName}//${domainName}:${port}/api/v1/user/signup`;

async function singUp() {
    try {
        const name = document.getElementById('sign-up-name').value;
        const email = document.getElementById('sign-up-email').value;
        const password = document.getElementById('sign-up-password').value;

        const singUpData = {
            name: name,
            email: email,
            password: password
        };

        const response = await fetch(singUpURL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(singUpData)
        });

        if (!response.ok) {
            throw new Error('Network response was not ok.');
        }

        const data = await response.json();
        localStorage.setItem('accessToken', data.access_token);
        localStorage.setItem('userId', data.user_id);
        alert('Login successful!');
        window.location.href = "/";
    } catch (error) {
        console.error('Error:', error);
        alert('Error logging in');
    }
}