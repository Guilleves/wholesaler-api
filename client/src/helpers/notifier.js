import $toast from 'buefy/src/components/toast';

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
  if (Array.isArray(errors)) {
    errors.forEach(error => {
      $toast.open({
          message: error,
          duration: 1500,
          queue: false,
          type: "is-danger",
          position: "is-bottom"
      });
    })
  } else
    $toast.open({
        message: errors,
        duration: 1500,
        queue: false,
        type: "is-danger",
        position: "is-bottom"
    });
};
