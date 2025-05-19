const getUserInfo = async () => {
    const response = await fetch(`/api/userinfo`);
    const data = await response.json();
    return data;
};

const login = async (user) => {
    const body = {
        'email': user.email,
        'password': user.password,
    }

    try {
        // Should this return only the data or undefined?
        return await fetch(`/api/auth/login`, {
            headers: new Headers({'content-type': 'application/json'}),
            method: 'POST',
            body: JSON.stringify(body),
        })
    } catch (error) {
        console.error('Error:', error);
        throw error;
    }
    // try {
    //     return await fetch(`/api/login`, {
    //         method: 'POST',
    //         body,
    //     })
    // }
    //  .then((response) => {
    //     if (response.status === 200) {
    //         console.log('Logged in successfully');
    //     } else {
    //         console.error('Login failed');
    //     }
    // }).catch((error) => {
    //     console.error('Error:', error);
    // });
}


export { getUserInfo, login };
