const getUserInfo = async () => {
    const response = await fetch('/api/userinfo');
    const data = await response.json();
    return data;
};

export { getUserInfo };
