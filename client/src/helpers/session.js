const SESSION_KEY = "Session";

const store = sessionStorage;

// Context by default is null
let session = null;

export const get = () => {
    if (!session)
        session = JSON.parse(store.getItem(SESSION_KEY))

    return session;
}

export const set = (userObject) => {
    store.setItem(SESSION_KEY, JSON.stringify(userObject));
    session = userObject;
}

export const clear = () => {
    store.removeItem(SESSION_KEY);
}

export const getToken = () => {
    const storage = get();
    return (storage !== null) && storage.token;
}

export const clearToken = () => {
    let storage = get();

    storage.token = null;

    store.setItem(SESSION_KEY, JSON.stringify(storage));
}
