import $toast from 'buefy/src/components/toast';
export const success = (message) => {
    $toast.open({
        message: message,
        duration: 5000,
        type: "is-success"
    });
};

export const info = (message) => {
    $toast.open({
        message: message,
        duration: 5000,
        type: "is-info"
    });
};

export const error = (errors) => {
    $toast.open({
        message: errors,
        duration: 5000,
        type: "is-danger",
        position: "is-bottom"
    });
};
