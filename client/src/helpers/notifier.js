import { ToastProgrammatic as $toast } from 'buefy'

export const success = (message) => {
    $toast.open({
        message: message,
        duration: 2000,
        queue: false,
        type: "is-success"
    });
};

export const info = (message) => {
    $toast.open({
        message: message,
        duration: 2000,
        queue: false,
        type: "is-light"
    });
};

export const error = (errors) => {
    $toast.open({
        message: errors,
        duration: 1500,
        queue: false,
        type: "is-danger",
        position: "is-bottom"
    });
};
