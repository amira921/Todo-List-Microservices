export const REGISTRATION_FORM =[
    {
        type: 'input',
        label: 'Email',
        inputType: 'email',
        controlName: 'email',
        required: true,
        placeholder: 'Enter your email address',
        validations: [
            { name: 'required', validator: 'required', message: 'Email is required' },
            { name: 'pattern', validator: 'pattern', message: 'Enter a valid email', args: [/^\S+@\S+\.\S+$/] },
        ],
    },
    {
        type: 'input',
        label: 'Username',
        inputType: 'text',
        controlName: 'username',
        required: true,
        placeholder: 'Enter your username',
        validations: [{ name: 'required', validator: 'required', message: 'Username is required' }],
    },
    {
        type: 'input',
        label: 'Password',
        inputType: 'password',
        controlName: 'password',
        required: true,
        placeholder: 'Enter your password',
        validations: [
            { name: 'required', validator: 'required', message: 'Password is required' },
            { name: 'pattern', validator: 'pattern', message: 'Password must contain at least one digit, one lowercase letter, one uppercase letter, one special character', args: [/^(?=.*[A-Za-z])(?=.*\d)(?=.*[\W_])[A-Za-z\d\W_]{8,}$/] },
        ],
    },

];
