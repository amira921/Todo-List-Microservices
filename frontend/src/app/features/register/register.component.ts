import {Component} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {FormGroup} from '@angular/forms';
import {REGISTRATION_FORM} from "../../statics/forms";
import {User} from "../../core/models/user.interface";
import {ErrorResponse} from "../../core/models/error.interface";
import {Router} from '@angular/router';
import {ValidationService} from "../../core/services/validation.service";

@Component({
    selector: 'app-register',
    templateUrl: './register.component.html',
    styleUrls: ['./register.component.scss'],
})

export class RegisterComponent{
    registrationForm!: FormGroup;
    registrationFields = REGISTRATION_FORM;
    errorMessage: String | null = null;
    apiUrl: string = "http://localhost:8081/auth/register";

    constructor(private http: HttpClient, private router: Router, private validationService: ValidationService) {
        this.registrationForm = this.validationService.createFormGroup(this.registrationFields);
    }

    onSubmit(): void {
        if (!this.registrationForm.valid) return;
        const user: User = this.registrationForm.value;

        this.http.post(this.apiUrl, user, {responseType: 'text'}).subscribe({
            next: () => {
                console.log('Registration successful');
                this.router.navigate(['']);
            },
            error: (error) => {
                if (error.status == 302) {
                    this.errorMessage = 'Email already exists, please try to login.';
                } else if (error.status == 400) {
                    const apiError = JSON.parse(error.error);
                    this.errorMessage = apiError.errors
                        .map((err: ErrorResponse) => `• ${err.message}`)
                        .join('\n\n');
                } else if (error.status == 500) {
                    this.errorMessage = 'Internal server error occurred, try again later';
                }
            }
        });
    }

    getErrorMessage(fieldName: string): string | null {
        const control = this.registrationForm.get(fieldName);
        if (control && control.invalid && (control.dirty || control.touched)) {
            return this.validationService.getErrorMessage(fieldName, control.errors, this.registrationFields);
        }
        return null;
    }
}